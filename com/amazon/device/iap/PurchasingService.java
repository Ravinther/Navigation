package com.amazon.device.iap;

import android.content.Context;
import android.util.Log;
import com.amazon.device.iap.internal.ImplementationFactory;
import com.amazon.device.iap.internal.PurchasingManager;
import com.amazon.device.iap.model.RequestId;

public final class PurchasingService {
    public static final boolean IS_SANDBOX_MODE;
    private static final String TAG;

    public PurchasingService() {
        Log.i(TAG, "In-App Purchasing SDK initializing. SDK Version 2.0.0, IS_SANDBOX_MODE: " + IS_SANDBOX_MODE);
    }

    static {
        TAG = PurchasingService.class.getSimpleName();
        IS_SANDBOX_MODE = ImplementationFactory.m56a();
    }

    public static void registerListener(Context context, PurchasingListener purchasingListener) {
        PurchasingManager.m47d().m52a(context, purchasingListener);
    }

    public static RequestId getUserData() {
        return PurchasingManager.m47d().m54c();
    }

    public static RequestId purchase(String str) {
        return PurchasingManager.m47d().m50a(str);
    }

    public static RequestId getPurchaseUpdates(boolean z) {
        return PurchasingManager.m47d().m51a(z);
    }
}
