package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.MarketPlaceListener;
import com.sygic.aura.helper.interfaces.StoreBaseListener;
import com.sygic.aura.store.data.ProductDetailEntry;
import com.sygic.aura.store.data.StoreEntry;

public class MarketPlaceEventsReceiver extends StoreBaseEventsReceiver {
    public static void registerEventsListener(MarketPlaceListener listener) {
        NativeMethodsHelper.registerListener(MarketPlaceListener.class, listener);
        NativeMethodsHelper.registerListener(StoreBaseListener.class, listener);
    }

    public static void unregisterEventsListener(MarketPlaceListener listener) {
        NativeMethodsHelper.unregisterListener(MarketPlaceListener.class, listener);
        NativeMethodsHelper.unregisterListener(StoreBaseListener.class, listener);
    }

    protected static void onListLoaded(StoreEntry[] entries, boolean isUpdateRequired) {
        StoreBaseEventsReceiver.onListLoaded(entries, isUpdateRequired);
    }

    protected static void onStoreMessage(String message) {
        StoreBaseEventsReceiver.onStoreMessage(message);
    }

    protected static void onConnectionChanged(boolean connected) {
        StoreBaseEventsReceiver.onConnectionChanged(connected);
    }

    protected static void onProductDetail(ProductDetailEntry product) {
        NativeMethodsHelper.callMethodWhenReady(MarketPlaceListener.class, "onProductDetail", product);
    }

    protected static void onShowError(int iErrorState, String strMessage) {
        if (strMessage == null) {
            strMessage = "";
        }
        NativeMethodsHelper.callMethodWhenReady(MarketPlaceListener.class, "onShowError", Integer.valueOf(iErrorState), strMessage);
    }

    protected static void onEnterActivationCode() {
        NativeMethodsHelper.callMethodWhenReady(MarketPlaceListener.class, "onEnterActivationCode", new Object[0]);
    }

    protected static void onShowComponents(String strId) {
        NativeMethodsHelper.callMethodWhenReady(MarketPlaceListener.class, "onShowComponents", strId);
    }

    protected static void onInstallRequired() {
        NativeMethodsHelper.callMethodWhenReady(MarketPlaceListener.class, "onInstallRequired", new Object[0]);
    }
}
