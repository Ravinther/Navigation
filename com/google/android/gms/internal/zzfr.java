package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.zzfm.zza;

@zzgk
public final class zzfr extends zza {
    private final InAppPurchaseListener zztp;

    public zzfr(InAppPurchaseListener inAppPurchaseListener) {
        this.zztp = inAppPurchaseListener;
    }

    public void zza(zzfl com_google_android_gms_internal_zzfl) {
        this.zztp.onInAppPurchaseRequested(new zzfu(com_google_android_gms_internal_zzfl));
    }
}
