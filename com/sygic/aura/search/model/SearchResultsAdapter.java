package com.sygic.aura.search.model;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.CityPostalSearchItem;
import com.sygic.aura.search.model.data.HouseNumberSearchItem;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchItem;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.data.SpecialSearchItem;
import com.sygic.aura.search.model.data.SpecialSearchItem.ItemType;
import com.sygic.aura.search.model.holder.ViewHolder;
import com.sygic.aura.search.model.holder.ViewHolderSpecial;
import com.sygic.aura.search.results.SearchResultsIF;
import java.util.ArrayList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class SearchResultsAdapter extends ArrayAdapter<SearchItem> {
    private SearchItem mCountryOrCityConstraint;
    private HouseNumberSearchItem mHouseNumberItem;
    private int mIntLoadedCount;
    private int mIntPrototypeCount;
    private int mIntSearchSubtype;
    private boolean mIsLoadCancelled;
    private boolean mIsLoadRunning;
    private long mSearchObjectRef;
    private final LocationQuery mSearchQueryHolder;
    private final SearchResultsIF mSearchResultCallback;
    private String mSearchStr;
    private SearchItem mStreetConstraint;
    private Thread mSyncThread;

    /* renamed from: com.sygic.aura.search.model.SearchResultsAdapter.1 */
    class C15731 implements ExecutionOrder {
        final /* synthetic */ LoadTask val$asyncTask;

        /* renamed from: com.sygic.aura.search.model.SearchResultsAdapter.1.1 */
        class C15721 implements Runnable {
            C15721() {
            }

            public void run() {
                AsyncTaskHelper.execute(C15731.this.val$asyncTask);
            }
        }

        C15731(LoadTask loadTask) {
            this.val$asyncTask = loadTask;
        }

        public boolean runningCondition() {
            return SearchResultsAdapter.this.mIsLoadRunning;
        }

        public boolean onPositive() {
            return true;
        }

        public boolean onNegative() {
            synchronized (SearchResultsAdapter.this) {
                SearchResultsAdapter.this.mSyncThread = null;
            }
            ((Activity) SearchResultsAdapter.this.getContext()).runOnUiThread(new C15721());
            return false;
        }
    }

    /* renamed from: com.sygic.aura.search.model.SearchResultsAdapter.2 */
    static /* synthetic */ class C15742 {
        static final /* synthetic */ int[] f1277xfef9d2f5;

        static {
            f1277xfef9d2f5 = new int[ItemType.values().length];
            try {
                f1277xfef9d2f5[ItemType.ITEM_NEARBY_POI_STREET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1277xfef9d2f5[ItemType.ITEM_NEARBY_POI_CROSSING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1277xfef9d2f5[ItemType.ITEM_HOUSE_NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private class LoadTask extends AsyncTask<Void, SearchItem, Boolean> {
        private boolean mHouseNoPresent;
        private boolean mIsQuickSearch;
        private boolean mNearbyPoiItemAdded;
        private List<SearchItem> mSearchItems;

        private LoadTask() {
        }

        protected void onPreExecute() {
            boolean z = true;
            SearchResultsAdapter.this.mSearchQueryHolder.destroySearchObjectRef(SearchResultsAdapter.this.mSearchObjectRef);
            if (SearchResultsAdapter.this.mHouseNumberItem != null) {
                int houseNumber = SearchResultsAdapter.this.mHouseNumberItem.getHouseNumber();
                SearchResultsAdapter.this.mSearchObjectRef = SearchResultsAdapter.this.mSearchQueryHolder.initAmericanStreetSearch(SearchResultsAdapter.this.mIntSearchSubtype, SearchResultsAdapter.this.mCountryOrCityConstraint, houseNumber);
                if (houseNumber == -1) {
                    SearchResultsAdapter.this.mIsLoadCancelled = true;
                    return;
                }
            }
            SearchResultsAdapter.this.mSearchObjectRef = SearchResultsAdapter.this.mSearchQueryHolder.initCoreSearch(SearchResultsAdapter.this.mIntSearchSubtype, SearchResultsAdapter.this.mCountryOrCityConstraint, SearchResultsAdapter.this.mStreetConstraint);
            SearchResultsAdapter.this.mSearchResultCallback.onStartSearch();
            SearchResultsAdapter.this.mIsLoadRunning = true;
            SearchResultsAdapter.this.mIsLoadCancelled = false;
            SearchResultsAdapter.this.mIntLoadedCount = 0;
            this.mHouseNoPresent = false;
            this.mNearbyPoiItemAdded = false;
            this.mSearchItems = new ArrayList(100);
            if (SearchResultsAdapter.this.mIntSearchSubtype != 10) {
                z = false;
            }
            this.mIsQuickSearch = z;
        }

        protected Boolean doInBackground(Void... params) {
            boolean z;
            synchronized (SearchResultsAdapter.this) {
                SearchResultsAdapter.this.mSearchQueryHolder.queryCoreSearch(SearchResultsAdapter.this.mSearchObjectRef, SearchResultsAdapter.this.mSearchStr);
                SearchResultsAdapter.this.mSearchQueryHolder.startSearch(SearchResultsAdapter.this.mSearchObjectRef);
            }
            int count = 0;
            boolean isProcessing = true;
            while (!SearchResultsAdapter.this.mIsLoadCancelled) {
                boolean firstCycle;
                if (SearchResultsAdapter.this.mIntLoadedCount == 0) {
                    firstCycle = true;
                } else {
                    firstCycle = false;
                }
                isProcessing = SearchResultsAdapter.this.mSearchQueryHolder.isSearchProcessing(SearchResultsAdapter.this.mSearchObjectRef);
                count = SygicProject.IS_PROTOTYPE ? Math.min(400, SearchResultsAdapter.this.mIntLoadedCount + 100) : SearchResultsAdapter.this.mSearchQueryHolder.getCoreResultsCount(SearchResultsAdapter.this.mSearchObjectRef);
                this.mSearchItems.clear();
                if (!this.mIsQuickSearch) {
                    if (firstCycle && SearchResultsAdapter.this.mIntSearchSubtype == 2 && SearchResultsAdapter.this.isNumber(SearchResultsAdapter.this.mSearchStr)) {
                        SearchItem houseNoItem = SearchResultsAdapter.this.getHouseNumberItem();
                        this.mHouseNoPresent = houseNoItem != null;
                        if (this.mHouseNoPresent) {
                            this.mSearchItems.add(houseNoItem);
                        }
                    }
                    if ((!this.mNearbyPoiItemAdded || count == 0) && (count > 0 || this.mHouseNoPresent)) {
                        SearchItem nearByPoiItem = SearchResultsAdapter.this.getNearByPoiItem(SearchResultsAdapter.this.mIntSearchSubtype);
                        if (nearByPoiItem != null) {
                            this.mNearbyPoiItemAdded = true;
                            this.mSearchItems.add(0, nearByPoiItem);
                        }
                    }
                }
                if (count > 0 && !SearchResultsAdapter.this.mIsLoadCancelled && (this.mIsQuickSearch || count > SearchResultsAdapter.this.mIntLoadedCount)) {
                    ListItem[] items = SearchResultsAdapter.this.mSearchQueryHolder.getCoreItems(SearchResultsAdapter.this.mSearchObjectRef, this.mIsQuickSearch ? 0 : SearchResultsAdapter.this.mIntLoadedCount, this.mIsQuickSearch ? count : 100);
                    if (items == null || items.length == 0) {
                        if (!isProcessing) {
                            finishTask(count);
                            break;
                        }
                    }
                    for (ListItem item : items) {
                        if (SearchResultsAdapter.this.mIsLoadCancelled) {
                            break;
                        }
                        if (item != null) {
                            this.mSearchItems.add((SearchItem) item);
                        }
                    }
                    if (SearchResultsAdapter.this.mIntLoadedCount == 0) {
                        publishProgress(new SearchItem[0]);
                    }
                    SearchResultsAdapter.this.mIntLoadedCount = this.mIsQuickSearch ? this.mSearchItems.size() : SearchResultsAdapter.this.mIntLoadedCount + this.mSearchItems.size();
                    publishProgress(this.mSearchItems.toArray(new SearchItem[this.mSearchItems.size()]));
                    if (this.mIsQuickSearch && !isProcessing) {
                        finishTask(count);
                        break;
                    }
                } else if (!isProcessing) {
                    if (!this.mSearchItems.isEmpty()) {
                        publishProgress(new SearchItem[0]);
                        publishProgress(this.mSearchItems.toArray(new SearchItem[this.mSearchItems.size()]));
                    }
                    finishTask(count);
                }
            }
            if (isProcessing || count > SearchResultsAdapter.this.mIntLoadedCount) {
                z = false;
            } else {
                z = true;
            }
            return Boolean.valueOf(z);
        }

        private void finishTask(int count) {
            if (count != 0) {
                publishProgress((SearchItem[]) null);
            } else if (this.mIsQuickSearch) {
                new SpecialSearchItem(ItemType.ITEM_QUICK_NOTHING).setDisplayName(ResourceManager.getCoreString(SearchResultsAdapter.this.getContext(), 2131165678));
                new SpecialSearchItem(ItemType.ITEM_GPS_COORDS).setDisplayName(ResourceManager.getCoreString(SearchResultsAdapter.this.getContext(), 2131165664));
                publishProgress(new SearchItem[]{itemEmpty, itemCoordinates});
            }
        }

        protected void onProgressUpdate(SearchItem... values) {
            int i = 1;
            if (values != null) {
                if (values.length > 0) {
                    SearchResultsAdapter.this.setNotifyOnChange(false);
                    if (this.mIsQuickSearch) {
                        SearchResultsAdapter.this.clear();
                    }
                    SearchResultsAdapter.this.addAll(values);
                    SearchResultsAdapter.this.notifyDataSetChanged();
                    if (!SearchResultsAdapter.this.mIsLoadCancelled && SpecialSearchItem.isSpecialType(values[0], ItemType.ITEM_QUICK_NOTHING)) {
                        SearchResultsAdapter.this.cancelCoreSearchLoading();
                        if (SearchResultsAdapter.this.mSearchResultCallback != null) {
                            SearchResultsAdapter.this.mSearchResultCallback.onLoadingFinished(true);
                        }
                    }
                    if (SearchResultsAdapter.this.mSearchResultCallback != null && !SearchResultsAdapter.this.mIsLoadCancelled) {
                        SearchResultsIF access$1000 = SearchResultsAdapter.this.mSearchResultCallback;
                        if (!((this.mIsQuickSearch || this.mHouseNoPresent) && SearchResultsAdapter.this.mIntLoadedCount == 0)) {
                            i = SearchResultsAdapter.this.mIntLoadedCount;
                        }
                        access$1000.onTickFinished(i);
                        return;
                    }
                    return;
                }
                SearchResultsAdapter.this.clear();
            } else if (!SearchResultsAdapter.this.mIsLoadCancelled) {
                SearchResultsAdapter.this.cancelCoreSearchLoading();
            }
        }

        protected void onPostExecute(Boolean isFinished) {
            boolean z = false;
            SearchResultsAdapter.this.mIsLoadRunning = false;
            if (SearchResultsAdapter.this.mSearchResultCallback != null && !SearchResultsAdapter.this.mIsLoadCancelled) {
                SearchResultsIF access$1000 = SearchResultsAdapter.this.mSearchResultCallback;
                if (this.mHouseNoPresent || SearchResultsAdapter.this.mIntLoadedCount > 0 || (!isFinished.booleanValue() && SearchResultsAdapter.this.isAmericaSearchActive())) {
                    z = true;
                }
                access$1000.onLoadingFinished(z);
            }
        }
    }

    public SearchResultsAdapter(Context context, LocationQuery locationQuery, SearchResultsIF resultsList) {
        super(context, 2130903271, 2131624363);
        this.mIntSearchSubtype = 0;
        this.mIsLoadRunning = false;
        this.mSearchStr = null;
        this.mCountryOrCityConstraint = null;
        this.mStreetConstraint = null;
        this.mHouseNumberItem = null;
        this.mSearchQueryHolder = locationQuery;
        this.mSearchResultCallback = resultsList;
    }

    public synchronized void initAmericanCoreSearch(int bubbleIndex, SearchItem countryOrCityConstraint, HouseNumberSearchItem houseNumberItem) {
        initCoreSearch(bubbleIndex, countryOrCityConstraint, null);
        this.mHouseNumberItem = houseNumberItem;
    }

    public synchronized void initCoreSearch(int bubbleIndex, SearchItem countryOrCityConstraint, SearchItem streetConstraint) {
        this.mHouseNumberItem = null;
        this.mIntSearchSubtype = bubbleIndex;
        this.mCountryOrCityConstraint = countryOrCityConstraint;
        this.mStreetConstraint = streetConstraint;
        if (this.mSearchQueryHolder == null && !SygicProject.IS_PROTOTYPE) {
            CrashlyticsHelper.logError("SearchResultsAdapter", "INIT CORE SEARCH FAILED - QUERY HOLDER NULL");
        }
    }

    public void queryCoreSearch(String searchStr) {
        if (SygicProject.IS_PROTOTYPE) {
            switch (this.mIntSearchSubtype) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    this.mIntPrototypeCount = 5;
                    return;
                case TTSConst.TTSMULTILINE /*1*/:
                    this.mIntPrototypeCount = 256;
                    return;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    this.mIntPrototypeCount = 28;
                    return;
                case TTSConst.TTSUNICODE /*3*/:
                    this.mIntPrototypeCount = 7;
                    return;
                case TTSConst.TTSXML /*4*/:
                    this.mIntPrototypeCount = 0;
                    return;
                default:
                    return;
            }
        }
        this.mSearchStr = searchStr;
        doCoreSearchLoading();
    }

    public synchronized boolean isAmericaSearchActive() {
        return this.mHouseNumberItem != null;
    }

    private SearchItem getNearByPoiItem(int subtype) {
        if (hasNearbyPoiType()) {
            SpecialSearchItem item = null;
            if (subtype == 2) {
                item = new SpecialSearchItem(ItemType.ITEM_NEARBY_POI_STREET);
            } else if (subtype == 3) {
                item = new SpecialSearchItem(ItemType.ITEM_NEARBY_POI_CROSSING);
            }
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    private SearchItem getHouseNumberItem() {
        if (PositionInfo.nativeIsUsaCountry(this.mCountryOrCityConstraint)) {
            return new SpecialSearchItem(ItemType.ITEM_HOUSE_NUMBER);
        }
        return null;
    }

    private void doCoreSearchLoading() {
        cancelCoreSearchLoading();
        executePoiLoading();
    }

    protected void executePoiLoading() {
        LoadTask asyncTask = new LoadTask();
        if (this.mIsLoadRunning) {
            boolean syncCheck;
            synchronized (this) {
                syncCheck = this.mSyncThread == null;
            }
            if (syncCheck) {
                startSyncThread(asyncTask);
                return;
            }
            return;
        }
        AsyncTaskHelper.execute(asyncTask);
    }

    private void startSyncThread(LoadTask asyncTask) {
        this.mSyncThread = new RepeatingThread(new C15731(asyncTask), 250);
        this.mSyncThread.start();
    }

    public void cancelCoreSearchLoading() {
        boolean z = true;
        if (this.mIsLoadRunning) {
            this.mSearchQueryHolder.cancelSearchTask(this.mSearchObjectRef);
            this.mIsLoadCancelled = true;
            SearchResultsIF searchResultsIF = this.mSearchResultCallback;
            if (this.mIsLoadRunning) {
                z = false;
            }
            searchResultsIF.onSearchCanceled(z);
        }
    }

    private boolean hasNearbyPoiType() {
        boolean isPostalAddress = false;
        if (this.mCountryOrCityConstraint != null && (this.mCountryOrCityConstraint instanceof CityPostalSearchItem)) {
            isPostalAddress = ((CityPostalSearchItem) this.mCountryOrCityConstraint).isPostalAddress();
        }
        return !isAmericaSearchActive() && ((this.mIntSearchSubtype == 2 || this.mIntSearchSubtype == 3) && !isPostalAddress);
    }

    public int getCount() {
        if (SygicProject.IS_PROTOTYPE) {
            return this.mIntPrototypeCount;
        }
        return super.getCount();
    }

    public boolean isEnabled(int position) {
        SearchItem item = getItem(position);
        return !(SearchType.SPECIAL.equals(item.getSearchType()) && ItemType.ITEM_QUICK_NOTHING.equals(((SpecialSearchItem) item).getSpecialItemType())) && super.isEnabled(position);
    }

    public SearchItem getItem(int position) {
        if (!SygicProject.IS_PROTOTYPE) {
            return (SearchItem) super.getItem(position);
        }
        String str;
        switch (this.mIntSearchSubtype) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                str = "Country";
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                str = "City";
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                str = "Street";
                break;
            case TTSConst.TTSUNICODE /*3*/:
                str = "Crossing";
                break;
            case TTSConst.TTSXML /*4*/:
                str = "Done";
                break;
            default:
                str = "NULL";
                break;
        }
        return null;
    }

    public int getViewTypeCount() {
        return SearchType.values().length;
    }

    public int getItemViewType(int position) {
        return getItem(position).getSearchType().ordinal();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String strName = null;
        SearchItem item = getItem(position);
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
            convertView.setTag(item.newViewHolderInstance(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        int viewType = getItemViewType(position);
        SearchType eViewType = SearchType.values()[viewType];
        if (eViewType.ordinal() != viewType) {
            throw new IllegalStateException("Enum value mismatch");
        }
        if (eViewType == SearchType.SPECIAL) {
            switch (C15742.f1277xfef9d2f5[((SpecialSearchItem) item).getSpecialItemType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    if (this.mCountryOrCityConstraint != null) {
                        strName = this.mCountryOrCityConstraint.getDisplayName();
                    }
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (this.mStreetConstraint != null) {
                        strName = this.mStreetConstraint.getDisplayName();
                    }
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    strName = this.mSearchStr;
                    break;
                default:
                    strName = null;
                    break;
            }
            ((ViewHolderSpecial) holder).setName(strName);
        }
        holder.updateContent(item, position);
        return convertView;
    }

    private boolean isNumber(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        int i = 0;
        while (i < text.length() && Character.isDigit(text.charAt(i))) {
            i++;
        }
        if (i == text.length()) {
            return true;
        }
        return false;
    }

    public String getLastSearchText() {
        return this.mSearchStr;
    }

    public void destroySearchRef() {
        this.mSearchQueryHolder.destroySearchObjectRef(this.mSearchObjectRef);
    }
}
