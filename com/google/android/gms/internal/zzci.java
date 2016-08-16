package com.google.android.gms.internal;

import com.google.android.gms.ads.doubleclick.OnCustomRenderedAdLoadedListener;
import com.google.android.gms.internal.zzch.zza;

@zzgk
public final class zzci extends zza {
    private final OnCustomRenderedAdLoadedListener zztr;

    public zzci(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.zztr = onCustomRenderedAdLoadedListener;
    }

    public void zza(zzcg com_google_android_gms_internal_zzcg) {
        this.zztr.onCustomRenderedAdLoaded(new zzcf(com_google_android_gms_internal_zzcg));
    }
}
