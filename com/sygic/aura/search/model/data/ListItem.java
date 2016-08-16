package com.sygic.aura.search.model.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.map.data.map_selection.MapSelection;
import java.util.Comparator;

public class ListItem implements Parcelable {
    public static final int BUBBLE_SELECTED = 1;
    public static final int BUBBLE_UNSELECTED = 0;
    public static final int BUBBLE_UNSELECTED_RED = 2;
    public static final Creator<ListItem> CREATOR;
    public static final int SEARCH_CITY = 1;
    public static final int SEARCH_COUNTRY = 0;
    public static final int SEARCH_DONE = 4;
    public static final int SEARCH_FAVS = 7;
    public static final int SEARCH_FAVS_CONTACTS = 9;
    public static final int SEARCH_FAVS_RECENT = 8;
    public static final int SEARCH_FOURSQUARE = 14;
    public static final int SEARCH_NONE = -1;
    public static final int SEARCH_POI = 5;
    public static final int SEARCH_POI_GROUP = 6;
    public static final int SEARCH_POI_ON_ROUTE = 12;
    public static final int SEARCH_POI_ON_ROUTE_GROUP = 13;
    public static final int SEARCH_QUICK_ALL = 10;
    public static final int SEARCH_QUICK_DETAIL = 11;
    public static final int SEARCH_STREET = 2;
    public static final int SEARCH_STREET_NUM = 3;
    public static final int SEARCH_TRIPADVISOR = 16;
    public static final int SEARCH_VIATOR = 17;
    public static final int SEARCH_YELP = 15;
    private static Comparator<ListItem> mComparator;
    protected int mIconType;
    protected MapSelection mMapSel;
    protected String mStrDisplayName;
    protected String mStrExtName;

    /* renamed from: com.sygic.aura.search.model.data.ListItem.1 */
    static class C15761 implements Comparator<ListItem> {
        C15761() {
        }

        public int compare(ListItem lListItem, ListItem rListItem) {
            return lListItem.getDisplayName().compareToIgnoreCase(rListItem.getDisplayName());
        }
    }

    /* renamed from: com.sygic.aura.search.model.data.ListItem.2 */
    static class C15772 implements Creator<ListItem> {
        C15772() {
        }

        public ListItem createFromParcel(Parcel in) {
            return new ListItem(null);
        }

        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    }

    public static ListItem getDummyItem(String name) {
        return new ListItem(name);
    }

    protected ListItem(String name) {
        this(name, "", MapSelection.Empty, SEARCH_COUNTRY);
    }

    protected ListItem(String name, MapSelection mapSel) {
        this(name, "", mapSel, SEARCH_COUNTRY);
    }

    public ListItem(String strName, String strExtName, MapSelection mapSel, int iconType) {
        this.mStrDisplayName = strName;
        this.mStrExtName = strExtName;
        this.mMapSel = mapSel;
        this.mIconType = iconType;
    }

    public ListItem(ListItem item) {
        this.mStrDisplayName = item.mStrDisplayName;
        this.mStrExtName = item.mStrExtName;
        this.mIconType = item.mIconType;
        this.mMapSel = item.mMapSel;
    }

    private ListItem(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void setDisplayName(String strName) {
        this.mStrDisplayName = strName;
    }

    public String getDisplayName() {
        return this.mStrDisplayName;
    }

    public boolean hasExtName() {
        return (this.mStrExtName == null || TextUtils.isEmpty(this.mStrExtName)) ? false : true;
    }

    public void setExtName(String strExtName) {
        this.mStrExtName = strExtName;
    }

    public String getExtName() {
        return this.mStrExtName;
    }

    public MapSelection getMapSel() {
        return this.mMapSel == null ? MapSelection.Empty : this.mMapSel;
    }

    public LongPosition getLongPosition() {
        return getMapSel().getPosition();
    }

    public static Comparator<? super ListItem> getComparator() {
        if (mComparator == null) {
            mComparator = new C15761();
        }
        return mComparator;
    }

    public int describeContents() {
        return SEARCH_COUNTRY;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        String[] strArr = new String[SEARCH_STREET];
        strArr[SEARCH_COUNTRY] = this.mStrDisplayName;
        strArr[SEARCH_CITY] = this.mStrExtName;
        parcel.writeStringArray(strArr);
        parcel.writeInt(this.mIconType);
        parcel.writeParcelable(this.mMapSel, flags);
    }

    private void readFromParcel(Parcel parcel) {
        String[] stringArray = parcel.createStringArray();
        if (stringArray != null) {
            this.mStrDisplayName = stringArray[SEARCH_COUNTRY];
            this.mStrExtName = stringArray[SEARCH_CITY];
        }
        this.mIconType = parcel.readInt();
        this.mMapSel = (MapSelection) parcel.readParcelable(MapSelection.class.getClassLoader());
    }

    static {
        CREATOR = new C15772();
    }

    public DashboardPlugin createDashPlugin() {
        return null;
    }
}
