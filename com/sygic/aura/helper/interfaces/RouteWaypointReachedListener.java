package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface RouteWaypointReachedListener extends CoreEventListener {
    void onWaypointReached();
}
