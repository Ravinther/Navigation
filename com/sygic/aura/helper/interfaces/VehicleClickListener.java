package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.map_selection.MapSelection;

public interface VehicleClickListener extends CoreEventListener {
    void onVehicleClick(MapSelection mapSelection, String str);
}
