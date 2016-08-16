package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface RouteFinishListener extends CoreEventListener {
    void onRouteFinished();

    void onRouteFinishedSoft();
}
