package com.sygic.aura.helper.interfaces;

import com.sygic.aura.map.data.map_selection.MapSelection;

public interface PoiDetailListener {
    void onGetDirections(MapSelection mapSelection);

    void onPassBy(MapSelection mapSelection);

    void onTravelVia(MapSelection mapSelection);
}
