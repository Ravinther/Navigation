package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.search.model.data.PoiListItem;
import java.util.ArrayList;

public interface PoiOnRouteListener extends CoreEventListener {
    void onPoiOnRoute(ArrayList<PoiListItem> arrayList);
}
