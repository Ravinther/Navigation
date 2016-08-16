package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.ConnectionChangeListener;

public class NetworkEventsReceiver extends NativeMethodsHelper {
    public static void registerConnectionChangeListener(ConnectionChangeListener listener) {
        NativeMethodsHelper.registerListener(ConnectionChangeListener.class, listener);
    }

    public static void unregisterConnectionChangeListener(ConnectionChangeListener listener) {
        NativeMethodsHelper.unregisterListener(ConnectionChangeListener.class, listener);
    }

    private static void onConnectionChanged(boolean bConnection) {
        NativeMethodsHelper.callMethodWhenReady(ConnectionChangeListener.class, "onConnectionChanged", Boolean.valueOf(bConnection));
    }
}
