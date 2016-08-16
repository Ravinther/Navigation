package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.WidgetListener;

public class WidgetEventsReceiver extends NativeMethodsHelper {
    public static void registerWidgetListener(WidgetListener listener) {
        NativeMethodsHelper.registerListener(WidgetListener.class, listener);
    }

    public static void unregisterWidgetListener(WidgetListener listener) {
        NativeMethodsHelper.unregisterListener(WidgetListener.class, listener);
    }

    public static void onAddHudPlugin() {
        NativeMethodsHelper.callMethodWhenReady(WidgetListener.class, "onAddHudPlugin", new Object[0]);
    }

    public static void onUpdateWidgets() {
        NativeMethodsHelper.callMethodWhenReady(WidgetListener.class, "onUpdateWidgets", new Object[0]);
    }
}
