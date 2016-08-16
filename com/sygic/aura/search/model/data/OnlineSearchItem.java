package com.sygic.aura.search.model.data;

import com.sygic.aura.map.data.map_selection.MapSelection;

public class OnlineSearchItem extends QuickSearchItem {
    private final String mStrUrl;

    public OnlineSearchItem(String strIso, String strName, String strExtName, long treeEntry, MapSelection mapSel, long distance, int itemType, int iconType, int poiIcon, String strUrl) {
        super(strIso, strName, strExtName, treeEntry, mapSel, distance, itemType, iconType, poiIcon);
        this.mStrUrl = strUrl;
    }

    public String getLink() {
        return this.mStrUrl;
    }
}
