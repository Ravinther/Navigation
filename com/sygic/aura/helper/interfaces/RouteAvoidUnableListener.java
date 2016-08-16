package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.route.RouteManager.AvoidType;

public interface RouteAvoidUnableListener extends CoreEventListener {
    void onAvoidUnable(AvoidType avoidType);
}
