package com.vividsolutions.jts.geom;

import android.support.v4.view.PagerAdapter;
import com.sygic.aura.C1090R;
import com.sygic.aura.search.model.data.PoiListItem;
import loquendo.tts.engine.TTSConst;

public class Dimension {
    public static char toDimensionSymbol(int dimensionValue) {
        switch (dimensionValue) {
            case -3:
                return '*';
            case PagerAdapter.POSITION_NONE /*-2*/:
                return 'T';
            case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                return 'F';
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return '0';
            case TTSConst.TTSMULTILINE /*1*/:
                return '1';
            case TTSConst.TTSPARAGRAPH /*2*/:
                return '2';
            default:
                throw new IllegalArgumentException("Unknown dimension value: " + dimensionValue);
        }
    }

    public static int toDimensionValue(char dimensionSymbol) {
        switch (Character.toUpperCase(dimensionSymbol)) {
            case C1090R.styleable.Theme_dialogTheme /*42*/:
                return -3;
            case C1090R.styleable.Theme_homeAsUpIndicator /*48*/:
                return 0;
            case C1090R.styleable.Theme_actionButtonStyle /*49*/:
                return 1;
            case TTSConst.TTSEVT_RESERVED /*50*/:
                return 2;
            case C1090R.styleable.Theme_listPreferredItemHeightLarge /*70*/:
                return -1;
            case C1090R.styleable.Theme_colorControlNormal /*84*/:
                return -2;
            default:
                throw new IllegalArgumentException("Unknown dimension symbol: " + dimensionSymbol);
        }
    }
}
