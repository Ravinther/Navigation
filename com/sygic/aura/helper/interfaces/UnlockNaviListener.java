package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface UnlockNaviListener extends CoreEventListener {
    void onLockNavi();

    void onUnlockNavi();
}
