package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface RouteComputeErrorListener extends CoreEventListener {
    void onRouteComputeError(String str);
}
