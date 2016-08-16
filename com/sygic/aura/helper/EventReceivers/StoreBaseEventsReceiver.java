package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.StoreBaseListener;
import com.sygic.aura.store.data.StoreEntry;
import java.util.ArrayList;

public class StoreBaseEventsReceiver extends NativeMethodsHelper {
    protected static void onListLoaded(StoreEntry[] entries, boolean isUpdateRequired) {
        ArrayList<StoreEntry> arr = new ArrayList();
        for (StoreEntry entry : entries) {
            arr.add(entry);
        }
        NativeMethodsHelper.callMethodWhenReady(StoreBaseListener.class, "onListLoaded", arr, Boolean.valueOf(isUpdateRequired));
    }

    protected static void onStoreMessage(String message) {
        NativeMethodsHelper.callMethodWhenReady(StoreBaseListener.class, "onStoreMessage", message);
    }

    protected static void onConnectionChanged(boolean connected) {
        NativeMethodsHelper.callMethodWhenReady(StoreBaseListener.class, "onConnectionChanged", Boolean.valueOf(connected));
    }
}
