package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzfq.zza;

@zzgk
public final class zzfv extends zza {
    private final PlayStorePurchaseListener zztq;

    public zzfv(PlayStorePurchaseListener playStorePurchaseListener) {
        this.zztq = playStorePurchaseListener;
    }

    public boolean isValidPurchase(String productId) {
        return this.zztq.isValidPurchase(productId);
    }

    public void zza(zzfp com_google_android_gms_internal_zzfp) {
        this.zztq.onInAppPurchaseFinished(new zzft(com_google_android_gms_internal_zzfp));
    }
}
