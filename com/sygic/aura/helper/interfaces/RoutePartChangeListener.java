package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface RoutePartChangeListener extends CoreEventListener {
    void onRoutePartChange(Integer num);
}
