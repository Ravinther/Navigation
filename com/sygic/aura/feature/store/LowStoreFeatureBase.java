package com.sygic.aura.feature.store;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

/* compiled from: LowStoreFeature */
class LowStoreFeatureBase extends LowStoreFeature {
    protected LowStoreFeatureBase() {
    }

    protected LowStoreFeatureBase(Context context, Handler handler) {
        super(context, handler);
    }

    public boolean getProductDetails(String strProductIDs) {
        for (String str : strProductIDs.split("^")) {
        }
        return false;
    }

    public boolean buyProduct(String strProductID) {
        if (this.mInAppPurchase != null) {
            return this.mInAppPurchase.buyProduct(strProductID);
        }
        return false;
    }

    public boolean restorePurchases() {
        if (this.mInAppPurchase != null) {
            return this.mInAppPurchase.restorePurchase();
        }
        return false;
    }

    public void checkQueuedTransactions() {
        if (this.mInAppPurchase != null) {
            this.mInAppPurchase.checkQueuedTransactions();
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isSupported() {
        if (this.mInAppPurchase != null) {
            return this.mInAppPurchase.isBillingSupported();
        }
        return false;
    }

    public void stopService() {
        if (this.mInAppPurchase != null) {
            this.mInAppPurchase.stopService();
        }
    }

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public void onPause() {
        if (this.mInAppPurchase != null) {
            this.mInAppPurchase.onPause();
        }
    }

    public void onResume() {
        if (this.mInAppPurchase != null) {
            this.mInAppPurchase.onResume();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        if (requestCode == 227) {
            this.mInAppPurchase.handleActivityResult(requestCode, resultCode, dataIntent);
        }
    }
}
