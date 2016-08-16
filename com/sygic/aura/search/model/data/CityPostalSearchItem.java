package com.sygic.aura.search.model.data;

import android.view.View;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.holder.ViewHolder;
import com.sygic.aura.search.model.holder.ViewHolderCity;

public class CityPostalSearchItem extends SearchItem {
    private final EPostalMode mPostalMode;

    public enum EPostalMode {
        PMNone,
        PMPosition,
        PMPositionAddress,
        PMAddress,
        PMAddressPoint
    }

    public enum IconType {
        ICON_CITY,
        ICON_CITY_BIG,
        ICON_CITY_CAPITAL
    }

    public CityPostalSearchItem(String strIso, String strName, String strExtName, long treeEntry, MapSelection mapSel, int iconType, int postalMode) {
        super(strIso, strName, strExtName, treeEntry, mapSel, iconType);
        this.mPostalMode = EPostalMode.values()[postalMode];
        if (this.mPostalMode.ordinal() != postalMode) {
            throw new IllegalStateException("Enum value mismatch");
        }
    }

    public String getCityName() {
        return getDisplayName();
    }

    public IconType getIcon() {
        return IconType.values()[this.mIconType];
    }

    public boolean isPostalPositionAddress() {
        return this.mPostalMode == EPostalMode.PMPositionAddress;
    }

    public boolean isPostalAddress() {
        return this.mPostalMode == EPostalMode.PMAddress || this.mPostalMode == EPostalMode.PMAddressPoint;
    }

    public SearchType getSearchType() {
        return SearchType.CITY;
    }

    public ViewHolder newViewHolderInstance(View view) {
        return new ViewHolderCity(view);
    }

    public String toString() {
        return getCityName().concat(", ").concat(getExtName());
    }

    public boolean checkMapSelection(LocationQuery locationQuery) {
        return isPostalAddress() || super.checkMapSelection(locationQuery);
    }
}
