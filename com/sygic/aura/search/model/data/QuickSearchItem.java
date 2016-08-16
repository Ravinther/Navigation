package com.sygic.aura.search.model.data;

import android.view.View;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.holder.ViewHolder;
import com.sygic.aura.search.model.holder.ViewHolderQuick;

public class QuickSearchItem extends SearchItem {
    private final long mDistance;
    private final int mItemType;
    private final int mPoiIcon;
    private final int mQuickItemType;

    public enum IconType {
        ICON_NONE,
        ICON_COUNTRY,
        ICON_CITY,
        ICON_CITY_BIG,
        ICON_CITY_CAPITAL,
        ICON_STREET,
        ICON_STREET_CITY_CENTER,
        ICON_STREET_CLOSED,
        ICON_CROSSING,
        ICON_NEARBY_POI,
        ICON_FAVORITES,
        ICON_MAP_STREET,
        ICON_GOOGLE,
        ICON_4SQUARE,
        ICON_YELP,
        ICON_VIATOR,
        ICON_GPS_COORDINATES
    }

    public enum ItemType {
        ITEM_NONE,
        ITEM_MEMO,
        ITEM_LOCAL_STREET,
        ITEM_LOCAL_STREET_POS,
        ITEM_GLOBAL_POSTAL,
        ITEM_CITY,
        ITEM_NEARBY_POI,
        ITEM_CITY_POI,
        ITEM_GLOBAL_CITY_POI,
        ITEM_GLOBAL_CITIES,
        ITEM_MAP_STREET,
        ITEM_NEIGHBOUR_STREET,
        ITEM_GOOGLE_SEARCH,
        ITEM_4SQUARE_SEARCH,
        ITEM_YELP_SEARCH,
        ITEM_VIATOR_SEARCH,
        ITEM_GPS_COORDINATES
    }

    public QuickSearchItem(String strIso, String strName, String strExtName, long treeEntry, MapSelection mapSel, long distance, int itemType, int iconType, int poiIcon) {
        super(strIso, strName, strExtName, treeEntry, mapSel, iconType);
        this.mDistance = distance;
        this.mPoiIcon = poiIcon;
        this.mItemType = itemType;
        this.mQuickItemType = itemType;
    }

    public IconType getIcon() {
        return IconType.values()[this.mIconType];
    }

    public String getPoiIcon() {
        if (this.mPoiIcon != 0) {
            return Character.toString((char) this.mPoiIcon);
        }
        return null;
    }

    public boolean checkMapSelection(LocationQuery locationQuery) {
        return isSubmenuItem() || super.checkMapSelection(locationQuery);
    }

    public long getDistance() {
        return this.mDistance;
    }

    public boolean isSubmenuItem() {
        return this.mQuickItemType > 65280;
    }

    public ItemType getQuickItemType() {
        int quickItemType = this.mQuickItemType;
        if (quickItemType > 65280) {
            quickItemType ^= 65280;
        }
        ItemType itemType = ItemType.values()[quickItemType];
        if (itemType.ordinal() == quickItemType) {
            return itemType;
        }
        throw new IllegalStateException("Enum value mismatch");
    }

    public boolean isConnectivityRequired() {
        ItemType itemType = getQuickItemType();
        return ItemType.ITEM_GOOGLE_SEARCH.equals(itemType) || ItemType.ITEM_4SQUARE_SEARCH.equals(itemType) || ItemType.ITEM_YELP_SEARCH.equals(itemType) || ItemType.ITEM_VIATOR_SEARCH.equals(itemType);
    }

    public SearchType getSearchType() {
        return SearchType.QUICK;
    }

    public ViewHolder newViewHolderInstance(View view) {
        return new ViewHolderQuick(view);
    }

    public String toString() {
        return getDisplayName().concat(", ").concat(getExtName());
    }
}
