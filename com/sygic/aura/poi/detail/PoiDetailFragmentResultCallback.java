package com.sygic.aura.poi.detail;

import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.map.data.map_selection.MapSelection;

public interface PoiDetailFragmentResultCallback extends FragmentResultCallback {
    void onPoiDetailFragmentResult(PoiDetailActions poiDetailActions, MapSelection mapSelection);
}
