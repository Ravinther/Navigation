package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface TrafficReceivedListener extends CoreEventListener {
    void onTrafficReceived();
}
