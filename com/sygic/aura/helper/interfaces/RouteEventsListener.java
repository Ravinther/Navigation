package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface RouteEventsListener extends CoreEventListener {
    void onFinishComputingProgress();

    void onRouteComputeFinishedAll();

    void onStartComputingProgress();
}
