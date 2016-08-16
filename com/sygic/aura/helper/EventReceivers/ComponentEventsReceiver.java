package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.ComponentsListener;
import com.sygic.aura.helper.interfaces.MapsAvailabilityListener;
import com.sygic.aura.helper.interfaces.StoreBaseListener;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.store.data.ComponentEntry;

public class ComponentEventsReceiver extends StoreBaseEventsReceiver {
    public static void registerEventsListener(ComponentsListener listener) {
        NativeMethodsHelper.registerListener(ComponentsListener.class, listener);
        NativeMethodsHelper.registerListener(StoreBaseListener.class, listener);
    }

    public static void unregisterEventsListener(ComponentsListener listener) {
        NativeMethodsHelper.unregisterListener(ComponentsListener.class, listener);
        NativeMethodsHelper.unregisterListener(StoreBaseListener.class, listener);
    }

    public static void registerMapsAvailabilityListener(MapsAvailabilityListener listener) {
        NativeMethodsHelper.registerListener(MapsAvailabilityListener.class, listener);
    }

    public static void unregisterMapsAvailabilityListener(MapsAvailabilityListener listener) {
        NativeMethodsHelper.unregisterListener(MapsAvailabilityListener.class, listener);
    }

    protected static void onListLoaded(ComponentEntry[] entries, boolean isUpdateRequired) {
        StoreBaseEventsReceiver.onListLoaded(entries, isUpdateRequired);
    }

    protected static void onStoreMessage(String message) {
        StoreBaseEventsReceiver.onStoreMessage(message);
    }

    protected static void onConnectionChanged(boolean connected) {
        StoreBaseEventsReceiver.onConnectionChanged(connected);
    }

    private static void onInstallFinished(String id) {
        NativeMethodsHelper.callMethodWhenReady(ComponentsListener.class, "onInstallFinished", id);
        NativeMethodsHelper.callMethodWhenReady(MapsAvailabilityListener.class, "onMapsAvailabilityChanged", Boolean.valueOf(true));
    }

    private static void onUninstallFinished(String id) {
        boolean z = true;
        NativeMethodsHelper.callMethodWhenReady(ComponentsListener.class, "onUninstallFinished", id);
        Class cls = MapsAvailabilityListener.class;
        String str = "onMapsAvailabilityChanged";
        Object[] objArr = new Object[1];
        if (ComponentManager.nativeGetInstalledMapCount() <= 0) {
            z = false;
        }
        objArr[0] = Boolean.valueOf(z);
        NativeMethodsHelper.callMethodWhenReady(cls, str, objArr);
    }

    private static void onProgressUpdated(String id, short progress, long total, long downloaded) {
        NativeMethodsHelper.callMethodWhenReady(ComponentsListener.class, "onDownloadProgressUpdated", id, Short.valueOf(progress), Long.valueOf(total), Long.valueOf(downloaded));
    }

    private static void onInstallWaiting(String id) {
        NativeMethodsHelper.callMethodWhenReady(ComponentsListener.class, "onInstallWaiting", id);
    }
}
