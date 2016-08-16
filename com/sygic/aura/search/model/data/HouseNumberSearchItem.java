package com.sygic.aura.search.model.data;

import com.sygic.aura.map.data.map_selection.MapSelection;

public class HouseNumberSearchItem extends ListItem {
    public static final int HOUSE_NUMBER_INVALID = -1;

    public HouseNumberSearchItem(String strName, MapSelection mapSel) {
        super(strName, mapSel);
    }

    public int getHouseNumber() {
        return parseNumber(this.mStrDisplayName);
    }

    public static int parseNumber(String textToParse) {
        try {
            return Integer.parseInt(textToParse);
        } catch (NumberFormatException e) {
            return HOUSE_NUMBER_INVALID;
        }
    }
}
