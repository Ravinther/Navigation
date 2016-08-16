package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface NotifyCenterListener extends CoreEventListener {
    void onNotifyCenterChange(Integer num);
}
