package com.sygic.aura.feature.store;

import android.content.Context;
import android.os.Handler;
import com.sygic.aura.feature.ActivityListener;
import com.sygic.aura.feature.ResultListener;

public abstract class LowStoreFeature implements ActivityListener, ResultListener {
    protected InAppPurchase mInAppPurchase;

    public abstract boolean buyProduct(String str);

    public abstract void checkQueuedTransactions();

    public abstract boolean getProductDetails(String str);

    public abstract boolean isEnabled();

    public abstract boolean isSupported();

    public abstract boolean restorePurchases();

    public abstract void stopService();

    protected LowStoreFeature() {
    }

    protected LowStoreFeature(Context context, Handler handler) {
        this.mInAppPurchase = InAppPurchase.createInstance(context, handler);
    }

    public static LowStoreFeature createInstance(Context context, Handler handler) {
        return new LowStoreFeatureBase(context, handler);
    }
}
