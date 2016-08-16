package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface JunctionChangeListener extends CoreEventListener {
    void onJunctionViewChanged(Boolean bool);
}
