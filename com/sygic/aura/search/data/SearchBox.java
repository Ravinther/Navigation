package com.sygic.aura.search.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.SygicProject;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup.Category;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.OnlinePoiListItem.OnlinePoiProviders;
import com.sygic.aura.search.model.data.QuickSearchItem.ItemType;

public class SearchBox implements Parcelable {
    public static final Creator<SearchBox> CREATOR;
    private volatile long mIntSearchObjRef;

    /* renamed from: com.sygic.aura.search.data.SearchBox.14 */
    class AnonymousClass14 implements Callback<Long> {
        final /* synthetic */ LongPosition val$lPos;

        AnonymousClass14(LongPosition longPosition) {
            this.val$lPos = longPosition;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitQuickSearch(this.val$lPos.toNativeLong()));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.15 */
    class AnonymousClass15 implements VoidCallback {
        final /* synthetic */ long val$objRef;

        AnonymousClass15(long j) {
            this.val$objRef = j;
        }

        public void getMethod() {
            SearchBox.this.CancelScheduledSearchTask(this.val$objRef);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.16 */
    class AnonymousClass16 implements VoidCallback {
        final /* synthetic */ long val$searchObjRef;

        AnonymousClass16(long j) {
            this.val$searchObjRef = j;
        }

        public void getMethod() {
            SearchBox.this.Destroy(this.val$searchObjRef);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.17 */
    class AnonymousClass17 implements VoidCallback {
        final /* synthetic */ long val$objRef;
        final /* synthetic */ String val$strInput;

        AnonymousClass17(long j, String str) {
            this.val$objRef = j;
            this.val$strInput = str;
        }

        public void getMethod() {
            SearchBox.this.Search(this.val$objRef, this.val$strInput);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.18 */
    class AnonymousClass18 implements Callback<Boolean> {
        final /* synthetic */ long val$objRef;

        AnonymousClass18(long j) {
            this.val$objRef = j;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(SearchBox.this.IsSearchTaskProcessing(this.val$objRef));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.19 */
    class AnonymousClass19 implements Callback<Integer> {
        final /* synthetic */ long val$objRef;

        AnonymousClass19(long j) {
            this.val$objRef = j;
        }

        public Integer getMethod() {
            return Integer.valueOf(SearchBox.this.GetCount(this.val$objRef));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.1 */
    class C15501 implements Callback<Long> {
        C15501() {
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitCountrySearch());
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.20 */
    class AnonymousClass20 implements Callback<ListItem> {
        final /* synthetic */ int val$index;

        AnonymousClass20(int i) {
            this.val$index = i;
        }

        public ListItem getMethod() {
            return SearchBox.this.GetAt(SearchBox.this.mIntSearchObjRef, this.val$index);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.21 */
    class AnonymousClass21 implements Callback<ListItem[]> {
        final /* synthetic */ int val$count;
        final /* synthetic */ int val$index;
        final /* synthetic */ long val$objRef;

        AnonymousClass21(long j, int i, int i2) {
            this.val$objRef = j;
            this.val$index = i;
            this.val$count = i2;
        }

        public ListItem[] getMethod() {
            return SearchBox.this.GetItems(this.val$objRef, this.val$index, this.val$count);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.23 */
    class AnonymousClass23 implements Callback<Category[]> {
        final /* synthetic */ int val$groupId;

        AnonymousClass23(int i) {
            this.val$groupId = i;
        }

        public Category[] getMethod() {
            return SearchBox.this.GetPoiCategories(this.val$groupId);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.24 */
    class AnonymousClass24 implements VoidCallback {
        final /* synthetic */ long val$searchObjRef;

        AnonymousClass24(long j) {
            this.val$searchObjRef = j;
        }

        public void getMethod() {
            SearchBox.this.StartSearchTask(this.val$searchObjRef);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.25 */
    class AnonymousClass25 implements VoidCallback {
        final /* synthetic */ long val$treeEntry;
        final /* synthetic */ ItemType val$type;

        AnonymousClass25(ItemType itemType, long j) {
            this.val$type = itemType;
            this.val$treeEntry = j;
        }

        public void getMethod() {
            SearchBox.this.SetQuickSearchItem(SearchBox.this.mIntSearchObjRef, this.val$type.ordinal(), this.val$treeEntry);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.26 */
    static class AnonymousClass26 implements VoidCallback {
        final /* synthetic */ MapSelection val$mapSel;

        AnonymousClass26(MapSelection mapSelection) {
            this.val$mapSel = mapSelection;
        }

        public void getMethod() {
            if (this.val$mapSel != null) {
                SearchBox.ShowOnMap(this.val$mapSel.getPosition().toNativeLong(), this.val$mapSel.getNavSelType().getValue(), this.val$mapSel.getData());
            } else {
                CrashlyticsHelper.logException(getClass().getName(), "nativeShowOnMap", new NullPointerException("mapSel is null"));
            }
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.27 */
    class AnonymousClass27 implements Callback<MapSelection> {
        final /* synthetic */ long val$iTreeEntry;

        AnonymousClass27(long j) {
            this.val$iTreeEntry = j;
        }

        public MapSelection getMethod() {
            return SearchBox.this.GetMapSelection(SearchBox.this.mIntSearchObjRef, this.val$iTreeEntry);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.28 */
    class AnonymousClass28 implements Callback<Boolean> {
        final /* synthetic */ long val$iTreeEntry;

        AnonymousClass28(long j) {
            this.val$iTreeEntry = j;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(SearchBox.this.SetStreetEntry(SearchBox.this.mIntSearchObjRef, this.val$iTreeEntry));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.29 */
    class AnonymousClass29 implements Callback<HouseNumberEntry> {
        final /* synthetic */ int val$houseNumber;

        AnonymousClass29(int i) {
            this.val$houseNumber = i;
        }

        public HouseNumberEntry getMethod() {
            return SearchBox.this.GetHouseNumberNavSel(SearchBox.this.mIntSearchObjRef, this.val$houseNumber);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.2 */
    class C15512 implements Callback<Long> {
        final /* synthetic */ String val$strIso;

        C15512(String str) {
            this.val$strIso = str;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitCityPostalSearch(this.val$strIso));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.33 */
    class AnonymousClass33 implements VoidCallback {
        final /* synthetic */ long val$objRef;

        AnonymousClass33(long j) {
            this.val$objRef = j;
        }

        public void getMethod() {
            SearchBox.this.RefreshFavoritesSearch(this.val$objRef);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.34 */
    static class AnonymousClass34 implements Callback<Long> {
        final /* synthetic */ String val$strLat;
        final /* synthetic */ String val$strLon;

        AnonymousClass34(String str, String str2) {
            this.val$strLat = str;
            this.val$strLon = str2;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.GpsCoordsSearch(this.val$strLat, this.val$strLon));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.35 */
    class AnonymousClass35 implements VoidCallback {
        final /* synthetic */ long val$navSel;
        final /* synthetic */ String val$newName;

        AnonymousClass35(long j, String str) {
            this.val$navSel = j;
            this.val$newName = str;
        }

        public void getMethod() {
            SearchBox.this.UpdateNavSelName(this.val$navSel, this.val$newName);
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.3 */
    class C15523 implements Callback<Long> {
        final /* synthetic */ long val$cityEntry;
        final /* synthetic */ String val$strIso;

        C15523(String str, long j) {
            this.val$strIso = str;
            this.val$cityEntry = j;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitStreetSearch(this.val$strIso, this.val$cityEntry));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.4 */
    class C15534 implements Callback<Long> {
        final /* synthetic */ long val$cityEntry;
        final /* synthetic */ int val$houseNumber;
        final /* synthetic */ String val$strIso;

        C15534(String str, long j, int i) {
            this.val$strIso = str;
            this.val$cityEntry = j;
            this.val$houseNumber = i;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitAmericanStreetSearch(this.val$strIso, this.val$cityEntry, this.val$houseNumber));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.5 */
    class C15545 implements Callback<Long> {
        final /* synthetic */ long val$cityEntry;
        final /* synthetic */ long val$streetEntry;

        C15545(long j, long j2) {
            this.val$cityEntry = j;
            this.val$streetEntry = j2;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitCrossingSearch(this.val$cityEntry, this.val$streetEntry));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.6 */
    class C15556 implements Callback<Long> {
        final /* synthetic */ long val$cityEntry;
        final /* synthetic */ int val$groupId;
        final /* synthetic */ long val$streetEntry;

        C15556(long j, long j2, int i) {
            this.val$cityEntry = j;
            this.val$streetEntry = j2;
            this.val$groupId = i;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitNearbyPoiSearch(this.val$cityEntry, this.val$streetEntry, this.val$groupId));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.7 */
    class C15567 implements Callback<Long> {
        final /* synthetic */ int val$groupId;
        final /* synthetic */ long val$lPosition;

        C15567(long j, int i) {
            this.val$lPosition = j;
            this.val$groupId = i;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitNearbyPoiSearchLongPosition(this.val$lPosition, this.val$groupId));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.8 */
    class C15578 implements Callback<Long> {
        final /* synthetic */ long val$cityEntry;
        final /* synthetic */ int val$groupId;
        final /* synthetic */ long val$streetEntry;
        final /* synthetic */ OnlinePoiProviders val$type;

        C15578(OnlinePoiProviders onlinePoiProviders, long j, long j2, int i) {
            this.val$type = onlinePoiProviders;
            this.val$cityEntry = j;
            this.val$streetEntry = j2;
            this.val$groupId = i;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitOnlineSearch(this.val$type.getValue(), this.val$cityEntry, this.val$streetEntry, this.val$groupId));
        }
    }

    /* renamed from: com.sygic.aura.search.data.SearchBox.9 */
    class C15589 implements Callback<Long> {
        final /* synthetic */ int val$groupId;
        final /* synthetic */ long val$lPosition;
        final /* synthetic */ OnlinePoiProviders val$type;

        C15589(OnlinePoiProviders onlinePoiProviders, long j, int i) {
            this.val$type = onlinePoiProviders;
            this.val$lPosition = j;
            this.val$groupId = i;
        }

        public Long getMethod() {
            return Long.valueOf(SearchBox.this.InitOnlineSearchLongPosition(this.val$type.getValue(), this.val$lPosition, this.val$groupId));
        }
    }

    private native void CancelScheduledSearchTask(long j);

    private native void Destroy(long j);

    private native ListItem GetAt(long j, int i);

    private native int GetCount(long j);

    private native int[] GetHouseNumberMinMax(long j);

    private native HouseNumberEntry GetHouseNumberNavSel(long j, int i);

    private native ListItem[] GetItems(long j, int i, int i2);

    private native MapSelection GetMapSelection(long j, long j2);

    private native Category[] GetPoiCategories(int i);

    private native NearbyPoiGroup[] GetPoiGroups();

    private static native long GpsCoordsSearch(String str, String str2);

    private native boolean HasCountry(long j);

    private native boolean HasState(long j);

    private native long InitAmericanStreetSearch(String str, long j, int i);

    private native long InitCityPostalSearch(String str);

    private native long InitContactSearch();

    private native long InitCountrySearch();

    private native long InitCrossingSearch(long j, long j2);

    private native long InitFavouriteSearch();

    private native long InitNearbyPoiSearch(long j, long j2, int i);

    private native long InitNearbyPoiSearchLongPosition(long j, int i);

    private native long InitOnlineSearch(int i, long j, long j2, int i2);

    private native long InitOnlineSearchLongPosition(int i, long j, int i2);

    private native long InitPoiOnRouteSearch();

    private native long InitQuickSearch(long j);

    private native long InitRecentSearch();

    private native long InitStreetSearch(String str, long j);

    private native boolean IsSearchTaskProcessing(long j);

    private native void RefreshFavoritesSearch(long j);

    private native void Search(long j, String str);

    private native void SetQuickSearchItem(long j, int i, long j2);

    private native boolean SetStreetEntry(long j, long j2);

    private static native void ShowOnMap(long j, int i, long j2);

    private native void StartSearchTask(long j);

    private native void UpdateNavSelName(long j, String str);

    public SearchBox() {
        this.mIntSearchObjRef = 0;
    }

    private SearchBox(Parcel parcel) {
        this.mIntSearchObjRef = 0;
        readFromParcel(parcel);
    }

    private long pickObjRef(long searchObjRef) {
        return searchObjRef != 0 ? searchObjRef : this.mIntSearchObjRef;
    }

    protected void nativeInitCountrySearch() {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15501()).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitCityPostalSearch(String strIso) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15512(strIso)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitStreetSearch(String strIso, long cityEntry) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15523(strIso, cityEntry)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    public void nativeInitAmericanStreetSearch(String strIso, long cityEntry, int houseNumber) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15534(strIso, cityEntry, houseNumber)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitCrossingSearch(long cityEntry, long streetEntry) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15545(cityEntry, streetEntry)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitNearbyPoiSearch(long cityEntry, long streetEntry, int groupId) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15556(cityEntry, streetEntry, groupId)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitNearbyPoiSearch(long lPosition, int groupId) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15567(lPosition, groupId)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitOnlineSearch(OnlinePoiProviders type, long cityEntry, long streetEntry, int groupId) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15578(type, cityEntry, streetEntry, groupId)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitOnlineSearch(OnlinePoiProviders type, long lPosition, int groupId) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new C15589(type, lPosition, groupId)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitPoiOnRouteSearch() {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new Callback<Long>() {
                public Long getMethod() {
                    return Long.valueOf(SearchBox.this.InitPoiOnRouteSearch());
                }
            }).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitContactSearch() {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new Callback<Long>() {
                public Long getMethod() {
                    return Long.valueOf(SearchBox.this.InitContactSearch());
                }
            }).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitRecentSearch() {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new Callback<Long>() {
                public Long getMethod() {
                    return Long.valueOf(SearchBox.this.InitRecentSearch());
                }
            }).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitFavouriteSearch() {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new Callback<Long>() {
                public Long getMethod() {
                    return Long.valueOf(SearchBox.this.InitFavouriteSearch());
                }
            }).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeInitQuickSearch(LongPosition lPos) {
        if (!SygicProject.IS_PROTOTYPE) {
            this.mIntSearchObjRef = ((Long) new ObjectHandler(new AnonymousClass14(lPos)).execute().get(Long.valueOf(0))).longValue();
        }
    }

    protected void nativeCancelSearchTask(long searchObjRef) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass15(pickObjRef(searchObjRef)));
        }
    }

    protected void nativeDestroy(long searchObjRef) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass16(searchObjRef));
        }
    }

    protected void nativeDestroy() {
        nativeDestroy(this.mIntSearchObjRef);
    }

    protected void nativeSearch(long searchObjRef, String strInput) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass17(pickObjRef(searchObjRef), strInput));
        }
    }

    protected boolean nativeIsSearchTaskProcessing(long searchObjRef) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass18(pickObjRef(searchObjRef))).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    protected int nativeGetCount(long searchObjRef) {
        if (SygicProject.IS_PROTOTYPE) {
            return 15;
        }
        return ((Integer) new ObjectHandler(new AnonymousClass19(pickObjRef(searchObjRef))).execute().get(Integer.valueOf(0))).intValue();
    }

    protected ListItem nativeGetAt(int index) {
        if (SygicProject.IS_PROTOTYPE) {
            return ListItem.getDummyItem("Favorit " + Integer.toString(index));
        }
        return (ListItem) new ObjectHandler(new AnonymousClass20(index)).execute().get(null);
    }

    protected ListItem[] nativeGetItems(long searchObjRef, int index, int count) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (ListItem[]) new ObjectHandler(new AnonymousClass21(pickObjRef(searchObjRef), index, count)).execute().get(new ListItem[0]);
    }

    protected NearbyPoiGroup[] nativeGetPoiGroups() {
        return (NearbyPoiGroup[]) new ObjectHandler(new Callback<NearbyPoiGroup[]>() {
            public NearbyPoiGroup[] getMethod() {
                return SearchBox.this.GetPoiGroups();
            }
        }).execute().get(null);
    }

    protected Category[] nativeGetPoiCategories(int groupId) {
        return (Category[]) new ObjectHandler(new AnonymousClass23(groupId)).execute().get(null);
    }

    public void nativeStartSearchTask(long searchObjRef) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass24(searchObjRef));
        }
    }

    protected void nativeSetQuickSearchItem(ItemType type, long treeEntry) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass25(type, treeEntry));
        }
    }

    public static void nativeShowOnMap(MapSelection mapSel) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass26(mapSel));
        }
    }

    public MapSelection nativeMapSelection(long iTreeEntry) {
        if (SygicProject.IS_PROTOTYPE) {
            return MapSelection.Empty;
        }
        return (MapSelection) new ObjectHandler(new AnonymousClass27(iTreeEntry)).execute().get(MapSelection.Empty);
    }

    public boolean nativeSetStreetEntry(long iTreeEntry) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass28(iTreeEntry)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public HouseNumberEntry nativeGetHouseNumberNavSel(int houseNumber) {
        if (SygicProject.IS_PROTOTYPE) {
            return new HouseNumberEntry(true, 6, null);
        }
        return (HouseNumberEntry) new ObjectHandler(new AnonymousClass29(houseNumber)).execute().get(null);
    }

    public int[] nativeGetStreetHouseNumbersMinMax() {
        return SygicProject.IS_PROTOTYPE ? new int[]{2, 12} : (int[]) new ObjectHandler(new Callback<int[]>() {
            public int[] getMethod() {
                return SearchBox.this.GetHouseNumberMinMax(SearchBox.this.mIntSearchObjRef);
            }
        }).execute().get(new int[0]);
    }

    public boolean nativeHasCountry() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SearchBox.this.HasCountry(SearchBox.this.mIntSearchObjRef));
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public boolean nativeHasState() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SearchBox.this.HasState(SearchBox.this.mIntSearchObjRef));
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public void nativeRefreshFavoritesSearch(long searchObjRef) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass33(pickObjRef(searchObjRef)));
        }
    }

    public static long nativeGpsCoordsSearch(String strLat, String strLon) {
        if (SygicProject.IS_PROTOTYPE) {
            return LongPosition.InvalidNativeLong;
        }
        return ((Long) new ObjectHandler(new AnonymousClass34(strLat, strLon)).execute().get(Long.valueOf(LongPosition.InvalidNativeLong))).longValue();
    }

    protected void nativeUpdateNavSelName(long navSel, String newName) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass35(navSel, newName));
        }
    }

    public long getSearchObjectRef() {
        return this.mIntSearchObjRef;
    }

    public void setSearchObjectRef(long searchObjectRef) {
        this.mIntSearchObjRef = searchObjectRef;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLongArray(new long[]{this.mIntSearchObjRef});
    }

    private void readFromParcel(Parcel parcel) {
        long[] longArray = parcel.createLongArray();
        if (longArray != null) {
            this.mIntSearchObjRef = longArray[0];
        }
    }

    static {
        CREATOR = new Creator<SearchBox>() {
            public SearchBox createFromParcel(Parcel in) {
                return new SearchBox(null);
            }

            public SearchBox[] newArray(int size) {
                return new SearchBox[size];
            }
        };
    }
}
