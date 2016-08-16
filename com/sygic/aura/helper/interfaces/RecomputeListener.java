package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface RecomputeListener extends CoreEventListener {
    void onRecomputeFinished();

    void onRecomputeStarted();
}
