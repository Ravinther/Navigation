package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface ConnectionChangeListener extends CoreEventListener {
    void onConnectionChanged(Boolean bool);
}
