package com.sygic.aura.fragments.interfaces;

import android.content.Context;
import com.sygic.aura.map.data.map_selection.MapSelection;

public interface SearchInterface {
    void coreSearchForQuery(String str);

    Context getContext();

    int[] getStreetHouseNumbersMinMax();

    void initCoreSearch(String str, boolean z);

    void onDelimiterPressed(int i, String str);

    void reinitializeCurrentInput();

    void setRouteReady(boolean z);

    void showResultsList(boolean z);

    void zoomOnAddress(MapSelection mapSelection);
}
