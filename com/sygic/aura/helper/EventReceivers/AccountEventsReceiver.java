package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.AccountListener;
import com.sygic.aura.network.AccountManager.ELoginStatus;

public class AccountEventsReceiver extends NativeMethodsHelper {
    public static void registerAccountListener(AccountListener listener) {
        NativeMethodsHelper.registerListener(AccountListener.class, listener);
    }

    public static void unregisterAccountListener(AccountListener listener) {
        NativeMethodsHelper.unregisterListener(AccountListener.class, listener);
    }

    private static void displayToast(String message) {
        NativeMethodsHelper.callMethodWhenReady(AccountListener.class, "displayToast", message);
    }

    private static void onLoginFinished(boolean connected, int status) {
        if (ELoginStatus.values()[status].ordinal() != status) {
            throw new IllegalStateException("Enum value mismatch");
        }
        NativeMethodsHelper.callMethodWhenReady(AccountListener.class, "onLoginFinished", Boolean.valueOf(connected), ELoginStatus.values()[status]);
    }

    private static void onDownloadCompleted() {
        NativeMethodsHelper.callMethodWhenReady(AccountListener.class, "onDownloadCompleted", new Object[0]);
    }

    private static void showWaitingDialog() {
        NativeMethodsHelper.callMethodWhenReady(AccountListener.class, "showWaitingDialog", new Object[0]);
    }

    private static void showWaitingDialogMessage(int result) {
        NativeMethodsHelper.callMethodWhenReady(AccountListener.class, "showWaitingDialogMessage", Integer.valueOf(result));
    }
}
