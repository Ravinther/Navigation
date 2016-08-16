package com.sygic.aura.search.model.data;

import android.view.View;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.holder.ViewHolder;
import com.sygic.aura.search.model.holder.ViewHolderCrossing;

public class CrossingSearchItem extends SearchItem {

    public enum IconType {
        ICON_CROSSING,
        ICON_ADDR_POINT,
        ICON_NEARBY_POI
    }

    public CrossingSearchItem(String strIso, String strName, String strExtName, long treeEntry, MapSelection mapSel, int iconType) {
        super(strIso, strName, strExtName, treeEntry, mapSel, iconType);
    }

    public IconType getIcon() {
        return IconType.values()[this.mIconType];
    }

    public SearchType getSearchType() {
        return SearchType.STREET_NUM;
    }

    public ViewHolder newViewHolderInstance(View view) {
        return new ViewHolderCrossing(view);
    }

    public String toString() {
        return getDisplayName();
    }
}
