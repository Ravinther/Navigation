package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.internal.zzcu.zza;

@zzgk
public class zzcz extends zza {
    private final OnContentAdLoadedListener zzwE;

    public zzcz(OnContentAdLoadedListener onContentAdLoadedListener) {
        this.zzwE = onContentAdLoadedListener;
    }

    public void zza(zzcp com_google_android_gms_internal_zzcp) {
        this.zzwE.onContentAdLoaded(zzb(com_google_android_gms_internal_zzcp));
    }

    zzcq zzb(zzcp com_google_android_gms_internal_zzcp) {
        return new zzcq(com_google_android_gms_internal_zzcp);
    }
}
