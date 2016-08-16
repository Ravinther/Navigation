package com.sygic.aura.search.data;

import com.sygic.aura.map.data.map_selection.MapSelection;

public class HouseNumberEntry {
    public int mHouseNumber;
    public MapSelection mMapSel;
    private final boolean mResultState;

    public HouseNumberEntry(boolean resultState, int houseNumber, MapSelection mapSel) {
        this.mResultState = resultState;
        this.mHouseNumber = houseNumber;
        this.mMapSel = mapSel;
    }

    public boolean isValid() {
        return this.mResultState;
    }
}
