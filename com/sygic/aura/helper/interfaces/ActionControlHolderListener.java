package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.map_selection.MapSelection;

public interface ActionControlHolderListener extends CoreEventListener {
    void onLastMileParkingAvailable(MapSelection mapSelection, String str, String str2);

    void onShowParkingPlaces(MapSelection mapSelection, String str, String str2);
}
