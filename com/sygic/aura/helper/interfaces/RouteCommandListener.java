package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.route.RouteManager.RouteComputeMode;
import java.util.ArrayList;

public interface RouteCommandListener extends CoreEventListener {
    void onComputeAfterStart();

    void onComputeSetRoute();

    void onNavigateThere(ArrayList<MapSelection> arrayList, RouteComputeMode routeComputeMode);
}
