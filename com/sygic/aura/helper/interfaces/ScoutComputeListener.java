package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface ScoutComputeListener extends CoreEventListener {
    void onScoutComputeRouteReady(Integer num, Integer num2);
}
