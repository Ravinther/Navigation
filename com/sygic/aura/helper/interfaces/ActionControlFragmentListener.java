package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.map_selection.MapSelection;

public interface ActionControlFragmentListener extends CoreEventListener {
    void onPoiSelectionChanged(MapSelection mapSelection, String str, String str2);
}
