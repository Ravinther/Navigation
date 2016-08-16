package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.LicenseListener;

public class LicenseEventsReceiver extends NativeMethodsHelper {
    public static void registerLicenseListener(LicenseListener listener) {
        NativeMethodsHelper.registerListener(LicenseListener.class, listener);
    }

    public static void unregisterLicenseListener(LicenseListener listener) {
        NativeMethodsHelper.unregisterListener(LicenseListener.class, listener);
    }

    public static void onLicenseUpdated() {
        NativeMethodsHelper.callMethodWhenReady(LicenseListener.class, "onLicenseUpdated", new Object[0]);
    }
}
