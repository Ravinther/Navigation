package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface RouteCancelListener extends CoreEventListener {
    void onRouteCanceled(Boolean bool);
}
