package com.sygic.aura.search.model.data;

import com.sygic.aura.data.LongPosition;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.PoiDetailsInfo;

public class RupiPoiListItem extends PoiListItem {
    private final LongPosition longPosition;

    public RupiPoiListItem(String strName, String strExtName, long longPos, MapSelection mapSel, long id, int distance, int groupId, int poiIcon, int poiColor, String strIconFile, String formattedDistance) {
        super(strName, strExtName, mapSel, id, distance, groupId, poiIcon, poiColor, strIconFile, formattedDistance);
        this.longPosition = new LongPosition(longPos);
    }

    protected MapSelection getPoiMapSel(long poiId) {
        return PoiDetailsInfo.nativeGetRupiPoiSelection(poiId, this.longPosition);
    }
}
