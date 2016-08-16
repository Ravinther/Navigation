package com.vividsolutions.jts.geom;

import com.sygic.aura.search.model.data.PoiListItem;
import loquendo.tts.engine.TTSConst;

public class Location {
    public static char toLocationSymbol(int locationValue) {
        switch (locationValue) {
            case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                return '-';
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return 'i';
            case TTSConst.TTSMULTILINE /*1*/:
                return 'b';
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 'e';
            default:
                throw new IllegalArgumentException("Unknown location value: " + locationValue);
        }
    }
}
