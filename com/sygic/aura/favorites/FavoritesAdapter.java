package com.sygic.aura.favorites;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.BookmarksListItem;
import com.sygic.aura.search.model.data.ContactListItem;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.RecentListItem;
import com.sygic.aura.search.model.data.RouteListItem;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import loquendo.tts.engine.TTSConst;

public class FavoritesAdapter extends ArrayAdapter<FavoritesItem> implements SectionIndexer {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final String SORT_PREFFS;
    private long mCoreSearchObjectRef;
    private List<String> mExclusionSearchDataFilter;
    private final List<LoadingStateListener> mFavouritesLoadedListeners;
    private boolean mFirstNonEmptyTickNotified;
    private final LayoutInflater mInflater;
    private int mIntLastCount;
    private boolean mInvalidated;
    private boolean mIsLoadCancelled;
    private boolean mIsLoadRunning;
    private boolean mLoadFinished;
    private final LocationQuery mLocationQuery;
    private final Mode mMode;
    private boolean mRouteFilter;
    private CharSequence mSearchStr;
    private final int mSearchSubType;
    private SparseArray<Section> mSections;
    private final boolean mSort;
    private Thread mSyncThread;

    /* renamed from: com.sygic.aura.favorites.FavoritesAdapter.1 */
    class C11931 implements Runnable {
        C11931() {
        }

        public void run() {
            FavoritesAdapter.this.query("");
        }
    }

    /* renamed from: com.sygic.aura.favorites.FavoritesAdapter.2 */
    class C11942 extends AsyncTask<Void, FavoritesItem, Boolean> {
        private Collection<FavoritesItem> mItems;

        C11942() {
        }

        protected void onPreExecute() {
            FavoritesAdapter.this.mIsLoadCancelled = false;
            FavoritesAdapter.this.mLoadFinished = false;
            FavoritesAdapter.this.mIsLoadRunning = true;
            FavoritesAdapter.this.mIntLastCount = 0;
            this.mItems = FavoritesAdapter.this.mSort ? new TreeSet(FavoritesAdapter.this.getComparator()) : new ArrayList();
            synchronized (FavoritesAdapter.this.mFavouritesLoadedListeners) {
                if (!FavoritesAdapter.this.mFavouritesLoadedListeners.isEmpty()) {
                    for (LoadingStateListener listener : FavoritesAdapter.this.mFavouritesLoadedListeners) {
                        listener.onLoadingStarted();
                    }
                }
            }
        }

        protected Boolean doInBackground(Void... params) {
            if (FavoritesAdapter.this.mCoreSearchObjectRef == 0) {
                FavoritesAdapter.this.mCoreSearchObjectRef = FavoritesAdapter.this.mLocationQuery.initCoreSearch(FavoritesAdapter.this.mSearchSubType, null, null);
            } else {
                if (FavoritesAdapter.this.mLocationQuery.getSearchObjectRef() != FavoritesAdapter.this.mCoreSearchObjectRef) {
                    FavoritesAdapter.this.mLocationQuery.setSearchObjectRef(FavoritesAdapter.this.mCoreSearchObjectRef, FavoritesAdapter.this.mSearchSubType);
                }
                if (FavoritesAdapter.this.mInvalidated) {
                    FavoritesAdapter.this.mInvalidated = false;
                    FavoritesAdapter.this.mLocationQuery.refreshFavorites(FavoritesAdapter.this.mCoreSearchObjectRef);
                }
                FavoritesAdapter.this.mLocationQuery.queryCoreSearch(FavoritesAdapter.this.mCoreSearchObjectRef, FavoritesAdapter.this.mSearchStr.toString());
            }
            int count = 0;
            boolean isProcessing = true;
            while (!FavoritesAdapter.this.mIsLoadCancelled) {
                boolean firstCycle = FavoritesAdapter.this.mIntLastCount == 0;
                if (!firstCycle) {
                    synchronized (this) {
                        try {
                            wait(850);
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            CrashlyticsHelper.logWarning("FAVORITES_ADAPTER", e.getMessage());
                        }
                    }
                }
                isProcessing = FavoritesAdapter.this.mLocationQuery.isSearchProcessing(FavoritesAdapter.this.mCoreSearchObjectRef);
                count = SygicProject.IS_PROTOTYPE ? Math.min(400, FavoritesAdapter.this.mIntLastCount + 100) : FavoritesAdapter.this.mLocationQuery.getCoreResultsCount(FavoritesAdapter.this.mCoreSearchObjectRef);
                synchronized (FavoritesAdapter.this) {
                    boolean isExclusionFilterEnabled = (FavoritesAdapter.this.mExclusionSearchDataFilter == null || FavoritesAdapter.this.mExclusionSearchDataFilter.isEmpty()) ? false : true;
                    List<String> exclusionFilterCopy = isExclusionFilterEnabled ? new ArrayList(FavoritesAdapter.this.mExclusionSearchDataFilter) : null;
                }
                if (count > 0 && count > FavoritesAdapter.this.mIntLastCount && !FavoritesAdapter.this.mIsLoadCancelled) {
                    ListItem[] items = FavoritesAdapter.this.mLocationQuery.getCoreItems(FavoritesAdapter.this.mCoreSearchObjectRef, FavoritesAdapter.this.mIntLastCount, firstCycle ? 5 : 20);
                    if (items != null) {
                        int length = items.length;
                        int i = 0;
                        while (i < length) {
                            ListItem item = items[i];
                            if (FavoritesAdapter.this.mIsLoadCancelled) {
                                break;
                            }
                            FavoritesAdapter.this.mIntLastCount = FavoritesAdapter.this.mIntLastCount + 1;
                            try {
                                FavoritesItem favItem = (FavoritesItem) item;
                                if (!((FavoritesAdapter.this.mRouteFilter && (favItem instanceof RouteListItem)) || (isExclusionFilterEnabled && exclusionFilterCopy.contains(favItem.getDisplayName())))) {
                                    this.mItems.add(favItem);
                                }
                                i++;
                            } catch (ClassCastException e2) {
                                CrashlyticsHelper.logError(getClass().getCanonicalName(), "Cannot cast " + item.getClass().getCanonicalName());
                                throw e2;
                            }
                        }
                    }
                    publishProgress(this.mItems.toArray(new FavoritesItem[this.mItems.size()]));
                } else if (!isProcessing) {
                    FavoritesAdapter.this.cancelFavoritesLoading();
                }
            }
            boolean z = !isProcessing && count <= FavoritesAdapter.this.mIntLastCount;
            return Boolean.valueOf(z);
        }

        protected void onProgressUpdate(FavoritesItem... items) {
            boolean successfulTick;
            if (items.length > 0) {
                successfulTick = true;
            } else {
                successfulTick = false;
            }
            FavoritesAdapter.this.setNotifyOnChange(false);
            FavoritesAdapter.this.clear();
            FavoritesAdapter.this.addAll(items);
            FavoritesAdapter.this.notifyDataSetChanged();
            if (successfulTick && !FavoritesAdapter.this.mFirstNonEmptyTickNotified) {
                synchronized (FavoritesAdapter.this.mFavouritesLoadedListeners) {
                    if (!FavoritesAdapter.this.mFavouritesLoadedListeners.isEmpty()) {
                        for (LoadingStateListener listener : FavoritesAdapter.this.mFavouritesLoadedListeners) {
                            listener.onFirstNonEmptyTick();
                        }
                    }
                }
                FavoritesAdapter.this.mFirstNonEmptyTickNotified = true;
            }
            synchronized (this) {
                notify();
            }
        }

        protected void onPostExecute(Boolean isFinished) {
            FavoritesAdapter.this.mIsLoadRunning = false;
            FavoritesAdapter.this.mLoadFinished = isFinished.booleanValue();
            synchronized (FavoritesAdapter.this.mFavouritesLoadedListeners) {
                if (!FavoritesAdapter.this.mFavouritesLoadedListeners.isEmpty()) {
                    boolean isEmpty = FavoritesAdapter.this.isEmpty();
                    for (LoadingStateListener listener : FavoritesAdapter.this.mFavouritesLoadedListeners) {
                        listener.onLoadingFinished(isEmpty);
                    }
                }
            }
        }
    }

    /* renamed from: com.sygic.aura.favorites.FavoritesAdapter.3 */
    class C11963 implements ExecutionOrder {
        final /* synthetic */ AsyncTask val$loadingTask;

        /* renamed from: com.sygic.aura.favorites.FavoritesAdapter.3.1 */
        class C11951 implements Runnable {
            C11951() {
            }

            public void run() {
                AsyncTaskHelper.execute(C11963.this.val$loadingTask);
            }
        }

        C11963(AsyncTask asyncTask) {
            this.val$loadingTask = asyncTask;
        }

        public boolean runningCondition() {
            return FavoritesAdapter.this.mIsLoadRunning;
        }

        public boolean onPositive() {
            return true;
        }

        public boolean onNegative() {
            synchronized (FavoritesAdapter.this) {
                FavoritesAdapter.this.mSyncThread = null;
            }
            ((Activity) FavoritesAdapter.this.getContext()).runOnUiThread(new C11951());
            return false;
        }
    }

    /* renamed from: com.sygic.aura.favorites.FavoritesAdapter.4 */
    static /* synthetic */ class C11974 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode;

        static {
            $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode = new int[Mode.values().length];
            try {
                $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[Mode.MODE_BOOKMARKS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[Mode.MODE_RECENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[Mode.MODE_CONTACTS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum Mode {
        MODE_BOOKMARKS,
        MODE_RECENT,
        MODE_CONTACTS
    }

    private class Section {
        protected char mInitial;

        private Section(char mInitial) {
            this.mInitial = mInitial;
        }

        public String toString() {
            return Character.toString(this.mInitial);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Section)) {
                return false;
            }
            if (this.mInitial != ((Section) o).mInitial) {
                return false;
            }
            return true;
        }
    }

    private class ViewHolder {
        private final TextView header;
        protected View mRootView;

        private ViewHolder(View rootView) {
            this.mRootView = rootView;
            this.header = (TextView) rootView.findViewById(2131624159);
        }

        public void updateView(FavoritesItem item) {
            this.mRootView.setEnabled(false);
            this.mRootView.setOnClickListener(null);
            this.header.setText(item.getDisplayName());
        }
    }

    private class ViewHolderItem extends ViewHolder {
        private final TextView date;
        private final SImageView dragHandle;
        private final SImageView icon;
        private final TextView summary;
        private final TextView title;

        private ViewHolderItem(View rootView) {
            super(rootView, null);
            this.title = (TextView) rootView.findViewById(16908308);
            this.summary = (TextView) rootView.findViewById(C1799R.id.text2);
            this.icon = (SImageView) rootView.findViewById(C1799R.id.icon);
            this.dragHandle = (SImageView) rootView.findViewById(2131624163);
            this.date = (TextView) rootView.findViewById(2131624162);
        }

        public void updateView(FavoritesItem item) {
            boolean z;
            boolean z2 = true;
            this.title.setText(item.toString());
            this.summary.setText(item.getAddressSummary());
            View view = this.summary;
            if (TextUtils.isEmpty(this.summary.getText())) {
                z = false;
            } else {
                z = true;
            }
            ResourceManager.makeControlVisible(view, z, true);
            view = this.dragHandle;
            if (!FavoritesAdapter.this.mMode.equals(Mode.MODE_BOOKMARKS) || InCarConnection.isInCarConnected()) {
                z = false;
            } else {
                z = true;
            }
            ResourceManager.makeControlVisible(view, z, true);
            Drawable iconDrawable = item.getIcon(this.icon.getResources());
            View view2 = this.icon;
            if (iconDrawable == null) {
                z2 = false;
            }
            ResourceManager.makeControlVisible(view2, z2, Mode.MODE_CONTACTS.equals(FavoritesAdapter.this.mMode));
            this.icon.setImageDrawable(iconDrawable);
            if (FavoritesAdapter.this.mMode.equals(Mode.MODE_RECENT) && this.date != null) {
                this.date.setVisibility(0);
                this.date.setText(((RecentListItem) item).getDate(FavoritesAdapter.this.getContext()));
            }
        }
    }

    static {
        $assertionsDisabled = !FavoritesAdapter.class.desiredAssertionStatus();
    }

    public FavoritesAdapter(Context context, LocationQuery locationQuery, int position) {
        super(context, 2130903095, 16908308);
        this.mExclusionSearchDataFilter = null;
        this.mSearchStr = "";
        this.mIntLastCount = 0;
        this.mCoreSearchObjectRef = 0;
        this.mIsLoadRunning = false;
        this.mInvalidated = false;
        this.mFirstNonEmptyTickNotified = false;
        this.SORT_PREFFS = "action_sort_fullname";
        this.mFavouritesLoadedListeners = Collections.synchronizedList(new LinkedList());
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mLocationQuery = locationQuery;
        this.mSearchSubType = position + 7;
        this.mMode = Mode.values()[position];
        switch (C11974.$SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[this.mMode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                this.mSort = false;
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
                this.mSort = true;
            default:
                if ($assertionsDisabled) {
                    this.mSort = false;
                    return;
                }
                throw new AssertionError();
        }
    }

    public void addAll(FavoritesItem... items) {
        addAll(Arrays.asList(items));
    }

    public void addAll(Collection<? extends FavoritesItem> collection) {
        HashSet<Character> usedHeaders = new HashSet();
        for (FavoritesItem item : collection) {
            if (this.mMode.equals(Mode.MODE_CONTACTS)) {
                char firstChar = ((ContactListItem) item).getInitial();
                if (!usedHeaders.contains(Character.valueOf(firstChar))) {
                    usedHeaders.add(Character.valueOf(firstChar));
                    add(new ContactListItem(String.valueOf(firstChar), true));
                }
            }
            add(item);
        }
    }

    public FavoritesItem getItem(int position) {
        if (!SygicProject.IS_PROTOTYPE) {
            return (FavoritesItem) super.getItem(position);
        }
        switch (C11974.$SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[this.mMode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return new BookmarksListItem("Bookmark ".concat(Integer.toString(position)), "", MapSelection.Empty, 0, 0, 0);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return new RecentListItem("Recent ".concat(Integer.toString(position)), "", "Address", MapSelection.Empty, 0, 0, 0, 0, "");
            case TTSConst.TTSUNICODE /*3*/:
                return new ContactListItem("Contact ".concat(Integer.toString(position)), "", MapSelection.Empty, 0, 0, new String[0], new long[0]);
            default:
                if ($assertionsDisabled) {
                    return null;
                }
                throw new AssertionError();
        }
    }

    public int getViewTypeCount() {
        return this.mMode.equals(Mode.MODE_BOOKMARKS) ? 1 : 2;
    }

    public int getItemViewType(int position) {
        if (!this.mMode.equals(Mode.MODE_CONTACTS)) {
            return 0;
        }
        if (((ContactListItem) getItem(position)).isHeader()) {
            return 1;
        }
        return 0;
    }

    public int getCount() {
        int origCount = super.getCount();
        return (SygicProject.IS_PROTOTYPE && origCount == 0) ? 12 : origCount;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (this.mMode.equals(Mode.MODE_CONTACTS) && ((ContactListItem) getItem(position)).isHeader()) {
                convertView = this.mInflater.inflate(2130903094, parent, false);
                convertView.setTag(new ViewHolder(convertView, null));
            } else {
                convertView = super.getView(position, convertView, parent);
                convertView.setTag(new ViewHolderItem(convertView, null));
            }
            if (InCarConnection.isInCarConnected()) {
                convertView.getLayoutParams().height = InCarConnection.updateViewDrawingDimension(convertView.getLayoutParams().height);
            }
        }
        ((ViewHolder) convertView.getTag()).updateView(getItem(position));
        return convertView;
    }

    public void recreateHeaders() {
        if (this.mMode == Mode.MODE_CONTACTS) {
            Collection itemsWithoutHeaders = new ArrayList(getCount());
            for (int i = 0; i < getCount(); i++) {
                ContactListItem item = (ContactListItem) getItem(i);
                if (!item.isHeader()) {
                    itemsWithoutHeaders.add(item);
                }
            }
            clear();
            addAll(itemsWithoutHeaders);
        }
    }

    public void query(CharSequence query) {
        this.mSearchStr = query;
        doFavoritesLoading();
    }

    public void reinitializeCoreSearch() {
        if (this.mLocationQuery.isCoreInitialised(this.mSearchSubType) && !this.mInvalidated) {
            return;
        }
        if (this.mCoreSearchObjectRef == 0) {
            new Handler().postDelayed(new C11931(), 300);
        } else if (!this.mLoadFinished || this.mInvalidated) {
            this.mLocationQuery.setSearchSubType(this.mSearchSubType);
            doFavoritesLoading();
        } else {
            this.mLocationQuery.setSearchObjectRef(this.mCoreSearchObjectRef, this.mSearchSubType);
        }
    }

    public Object[] getSections() {
        int count = getCount();
        if (count == 0) {
            return new Object[0];
        }
        if (this.mSections == null) {
            this.mSections = new SparseArray();
        } else {
            this.mSections.clear();
        }
        ListItem item = getItem(0);
        if (item instanceof ContactListItem) {
            char lastInitial = ((ContactListItem) item).getInitial();
            this.mSections.put(0, new Section(lastInitial, null));
            for (int pos = 1; pos < count; pos++) {
                char initial = ((ContactListItem) getItem(pos)).getInitial();
                if (initial != lastInitial) {
                    lastInitial = initial;
                    this.mSections.put(pos, new Section(initial, null));
                }
            }
        }
        int size = this.mSections.size();
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = this.mSections.valueAt(i);
        }
        return array;
    }

    public int getPositionForSection(int section) {
        return this.mSections.size() <= 1 ? 0 : this.mSections.keyAt(section);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getSectionForPosition(int r4) {
        /*
        r3 = this;
        r0 = r4;
    L_0x0001:
        r2 = r3.mSections;
        r1 = r2.indexOfKey(r0);
        if (r1 >= 0) goto L_0x000e;
    L_0x0009:
        if (r0 <= 0) goto L_0x000e;
    L_0x000b:
        r0 = r0 + -1;
        goto L_0x0001;
    L_0x000e:
        if (r1 <= 0) goto L_0x0011;
    L_0x0010:
        return r1;
    L_0x0011:
        r1 = 0;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.favorites.FavoritesAdapter.getSectionForPosition(int):int");
    }

    private void doFavoritesLoading() {
        cancelFavoritesLoading();
        AsyncTask<Void, FavoritesItem, Boolean> loadingTask = new C11942();
        if (this.mIsLoadRunning) {
            boolean syncCheck;
            synchronized (this) {
                syncCheck = this.mSyncThread == null;
            }
            if (syncCheck) {
                this.mSyncThread = new RepeatingThread(new C11963(loadingTask), 250);
                this.mSyncThread.start();
                return;
            }
            return;
        }
        AsyncTaskHelper.execute(loadingTask);
    }

    private boolean getSortPrefference() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("action_sort_fullname", true);
    }

    public void saveSortPrefferences(boolean sortFullName) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putBoolean("action_sort_fullname", sortFullName);
        editor.apply();
        if (this.mMode == Mode.MODE_CONTACTS) {
            ContactListItem.setCompareByFullName(sortFullName);
        }
    }

    private Comparator<? super FavoritesItem> getComparator() {
        switch (C11974.$SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[this.mMode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return ListItem.getComparator();
            case TTSConst.TTSPARAGRAPH /*2*/:
                return RecentListItem.getComparator();
            case TTSConst.TTSUNICODE /*3*/:
                return ContactListItem.getComparator(getSortPrefference());
            default:
                return null;
        }
    }

    public void cancelFavoritesLoading() {
        if (this.mIsLoadRunning) {
            this.mIsLoadCancelled = true;
        }
    }

    public void deleteAtPosition(int position) {
        remove(getItem(position));
    }

    public void invalidateDataSet() {
        this.mInvalidated = true;
    }

    public synchronized void setExclusionSearchDataFilter(List<String> exclusionSearchDataFilter) {
        this.mExclusionSearchDataFilter = exclusionSearchDataFilter;
    }

    public void setRouteFilter(boolean doFilter) {
        this.mRouteFilter = doFilter;
    }

    public void destroySearchObjectReference() {
        this.mLocationQuery.destroySearchObjectRef(this.mCoreSearchObjectRef);
    }

    public boolean registerFavouritieLoadedListener(LoadingStateListener favouritiesLoadingFinished) {
        return this.mFavouritesLoadedListeners.add(favouritiesLoadingFinished);
    }

    public boolean unregisterFavouritieLoadedListener(LoadingStateListener favouritiesLoadingFinished) {
        return this.mFavouritesLoadedListeners.remove(favouritiesLoadingFinished);
    }
}
