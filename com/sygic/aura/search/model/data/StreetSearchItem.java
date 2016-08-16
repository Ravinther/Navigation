package com.sygic.aura.search.model.data;

import android.view.View;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.holder.ViewHolder;
import com.sygic.aura.search.model.holder.ViewHolderStreet;

public class StreetSearchItem extends SearchItem {
    public final boolean mIsCityCenter;

    public enum IconType {
        ICON_STREET,
        ICON_STREET_CITY_CENTER,
        ICON_STREET_CLOSED,
        ICON_NEARBY_POI
    }

    public StreetSearchItem(String strIso, String strName, String strExtName, long treeEntry, MapSelection mapSel, int iconType, boolean isCityCenter) {
        super(strIso, strName, strExtName, treeEntry, mapSel, iconType);
        this.mIsCityCenter = isCityCenter;
    }

    public String getStreetName() {
        return getDisplayName();
    }

    public IconType getIcon() {
        return IconType.values()[this.mIconType];
    }

    public boolean isCityCenter() {
        return this.mIsCityCenter;
    }

    public SearchType getSearchType() {
        return SearchType.STREET;
    }

    public ViewHolder newViewHolderInstance(View view) {
        return new ViewHolderStreet(view);
    }

    public String toString() {
        return getStreetName().concat(", ").concat(getExtName());
    }
}
