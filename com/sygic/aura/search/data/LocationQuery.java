package com.sygic.aura.search.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.SearchCountDownTimerExecutor;
import com.sygic.aura.helper.SearchCountDownTimerExecutor.CountDownTimerListenerIF;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup.Category;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.OnlinePoiListItem;
import com.sygic.aura.search.model.data.QuickSearchItem.ItemType;
import com.sygic.aura.search.model.data.SearchItem;
import loquendo.tts.engine.TTSConst;

public class LocationQuery implements Parcelable {
    public static final Creator<LocationQuery> CREATOR;
    public static final int GROUP_ID_ALL_POIS = 0;
    @Deprecated
    public static final long LAST_OBJ = 0;
    private SearchBox mCoreSearchQueryObject;
    private final SearchCountDownTimerExecutor mCountDownTimerExecutor;
    private int mSearchSubtype;

    /* renamed from: com.sygic.aura.search.data.LocationQuery.1 */
    static class C15481 implements Creator<LocationQuery> {
        C15481() {
        }

        public LocationQuery createFromParcel(Parcel in) {
            return new LocationQuery(null);
        }

        public LocationQuery[] newArray(int size) {
            return new LocationQuery[size];
        }
    }

    public LocationQuery() {
        this.mCoreSearchQueryObject = new SearchBox();
        this.mCountDownTimerExecutor = new SearchCountDownTimerExecutor();
        this.mSearchSubtype = -1;
    }

    private LocationQuery(Parcel parcel) {
        this.mCoreSearchQueryObject = new SearchBox();
        this.mCountDownTimerExecutor = new SearchCountDownTimerExecutor();
        this.mSearchSubtype = -1;
        readFromParcel(parcel);
    }

    private static long getItemTreeEntry(SearchItem item) {
        return item == null ? 0 : item.getTreeEntry();
    }

    public long initCoreSearch(int searchSubtype, LongPosition lPosition, int groupId) {
        return initCoreSearch(searchSubtype, null, null, lPosition, groupId);
    }

    public long initCoreSearch(int searchSubtype, SearchItem countryOrCityConstraint, SearchItem streetConstraint, int groupId) {
        return initCoreSearch(searchSubtype, countryOrCityConstraint, streetConstraint, null, groupId);
    }

    public long initCoreSearch(int searchSubtype, SearchItem countryOrCityConstraint, SearchItem streetConstraint) {
        return initCoreSearch(searchSubtype, countryOrCityConstraint, streetConstraint, null, 0);
    }

    public long initAmericanStreetSearch(int searchSubtype, SearchItem countryOrCityConstraint, int houseNumber) {
        this.mSearchSubtype = searchSubtype;
        this.mCoreSearchQueryObject.nativeInitAmericanStreetSearch(countryOrCityConstraint == null ? null : countryOrCityConstraint.getIso(), getItemTreeEntry(countryOrCityConstraint), houseNumber);
        return getSearchObjectRef();
    }

    private long initCoreSearch(int searchSubtype, SearchItem countryOrCityConstraint, SearchItem streetConstraint, LongPosition lPosition, int groupId) {
        this.mSearchSubtype = searchSubtype;
        String countryOrCityConstraintIso = countryOrCityConstraint == null ? null : countryOrCityConstraint.getIso();
        switch (searchSubtype) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mCoreSearchQueryObject.nativeInitCountrySearch();
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                this.mCoreSearchQueryObject.nativeInitCityPostalSearch(countryOrCityConstraintIso);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mCoreSearchQueryObject.nativeInitStreetSearch(countryOrCityConstraintIso, getItemTreeEntry(countryOrCityConstraint));
                break;
            case TTSConst.TTSUNICODE /*3*/:
                this.mCoreSearchQueryObject.nativeInitCrossingSearch(getItemTreeEntry(countryOrCityConstraint), getItemTreeEntry(streetConstraint));
                break;
            case TTSConst.TTSEVT_TEXT /*5*/:
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                if (lPosition == null) {
                    this.mCoreSearchQueryObject.nativeInitNearbyPoiSearch(getItemTreeEntry(countryOrCityConstraint), getItemTreeEntry(streetConstraint), groupId);
                    break;
                }
                this.mCoreSearchQueryObject.nativeInitNearbyPoiSearch(lPosition.toNativeLong(), groupId);
                break;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                this.mCoreSearchQueryObject.nativeInitFavouriteSearch();
                break;
            case TTSConst.TTSEVT_TAG /*8*/:
                this.mCoreSearchQueryObject.nativeInitRecentSearch();
                break;
            case TTSConst.TTSEVT_PAUSE /*9*/:
                this.mCoreSearchQueryObject.nativeInitContactSearch();
                break;
            case TTSConst.TTSEVT_RESUME /*10*/:
                this.mCoreSearchQueryObject.nativeInitQuickSearch(countryOrCityConstraint == null ? LongPosition.Invalid : countryOrCityConstraint.getMapSel().getPosition());
                break;
            case TTSConst.TTSEVT_NOTSENT /*12*/:
            case TTSConst.TTSEVT_AUDIO /*13*/:
                this.mCoreSearchQueryObject.nativeInitPoiOnRouteSearch();
                break;
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
            case TTSConst.TTSEVT_ERROR /*16*/:
            case TTSConst.TTSEVT_JUMP /*17*/:
                if (lPosition == null) {
                    this.mCoreSearchQueryObject.nativeInitOnlineSearch(OnlinePoiListItem.getProvider(searchSubtype), getItemTreeEntry(countryOrCityConstraint), getItemTreeEntry(streetConstraint), groupId);
                    break;
                }
                this.mCoreSearchQueryObject.nativeInitOnlineSearch(OnlinePoiListItem.getProvider(searchSubtype), lPosition.toNativeLong(), groupId);
                break;
        }
        return getSearchObjectRef();
    }

    public long getSearchObjectRef() {
        return this.mCoreSearchQueryObject.getSearchObjectRef();
    }

    public void cancelSearchTask(long searchObjRef) {
        this.mCoreSearchQueryObject.nativeCancelSearchTask(searchObjRef);
    }

    public void destroySearchObjectRef(long searchObjRef) {
        this.mCoreSearchQueryObject.nativeDestroy(searchObjRef);
        this.mSearchSubtype = -1;
    }

    public void setSearchSubType(int searchSubType) {
        this.mSearchSubtype = searchSubType;
    }

    public void setSearchObjectRef(long coreSearchObjectRef, int searchSubType) {
        this.mCoreSearchQueryObject.setSearchObjectRef(coreSearchObjectRef);
        this.mSearchSubtype = searchSubType;
    }

    public boolean isCoreInitialised(int searchSubType) {
        return this.mSearchSubtype == searchSubType;
    }

    public void queryCoreSearch(long searchObjRef, String queryString) {
        this.mCoreSearchQueryObject.nativeSearch(searchObjRef, queryString);
    }

    public boolean cancelSearchInitialization(long ticketNr) {
        return this.mCountDownTimerExecutor.cancel(ticketNr);
    }

    public long doSearchInitialization(CountDownTimerListenerIF searchListener) {
        return doSearchInitialization(searchListener, 900);
    }

    public long doSearchInitialization(CountDownTimerListenerIF searchListener, long countDownInterval) {
        if (searchListener == null) {
            return -1;
        }
        return this.mCountDownTimerExecutor.execute(searchListener, 10 * countDownInterval, countDownInterval);
    }

    public boolean isSearchProcessing(long searchObjRef) {
        return this.mCoreSearchQueryObject.nativeIsSearchTaskProcessing(searchObjRef);
    }

    public int getCoreResultsCount(long searchObjRef) {
        return this.mCoreSearchQueryObject.nativeGetCount(searchObjRef);
    }

    @Deprecated
    public ListItem getCoreItemAt(int resultListPosition) {
        return this.mCoreSearchQueryObject.nativeGetAt(resultListPosition);
    }

    public ListItem[] getCoreItems(long searchObjRef, int resultListPosition, int count) {
        return this.mCoreSearchQueryObject.nativeGetItems(searchObjRef, resultListPosition, count);
    }

    public void startSearch(long searchObjRef) {
        this.mCoreSearchQueryObject.nativeStartSearchTask(searchObjRef);
    }

    public NearbyPoiGroup[] getPoiGroups() {
        return this.mCoreSearchQueryObject.nativeGetPoiGroups();
    }

    public Category[] getPoiCategories(int groupId) {
        return this.mCoreSearchQueryObject.nativeGetPoiCategories(groupId);
    }

    public void setQuickSearchItem(ItemType type, long treeEntry) {
        this.mCoreSearchQueryObject.nativeSetQuickSearchItem(type, treeEntry);
    }

    public MapSelection getMapSelection(long iTreeEntry) {
        return this.mCoreSearchQueryObject.nativeMapSelection(iTreeEntry);
    }

    public void updateNavSelName(long navSel, String newName) {
        this.mCoreSearchQueryObject.nativeUpdateNavSelName(navSel, newName);
    }

    public boolean setStreetEntry(long iTreeEntry) {
        return this.mCoreSearchQueryObject.nativeSetStreetEntry(iTreeEntry);
    }

    public HouseNumberEntry getHouseNumberNavSel(int houseNumber) {
        return this.mCoreSearchQueryObject.nativeGetHouseNumberNavSel(houseNumber);
    }

    public int[] getStreetHouseNumbersMinMax() {
        return this.mCoreSearchQueryObject.nativeGetStreetHouseNumbersMinMax();
    }

    public void refreshFavorites(long searchObjRef) {
        this.mCoreSearchQueryObject.nativeRefreshFavoritesSearch(searchObjRef);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mSearchSubtype);
        parcel.writeParcelable(this.mCoreSearchQueryObject, flags);
    }

    private void readFromParcel(Parcel parcel) {
        this.mSearchSubtype = parcel.readInt();
        this.mCoreSearchQueryObject = (SearchBox) parcel.readParcelable(SearchBox.class.getClassLoader());
    }

    static {
        CREATOR = new C15481();
    }
}
