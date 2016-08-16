package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;
import com.google.android.gms.internal.zzct.zza;

@zzgk
public class zzcy extends zza {
    private final OnAppInstallAdLoadedListener zzwD;

    public zzcy(OnAppInstallAdLoadedListener onAppInstallAdLoadedListener) {
        this.zzwD = onAppInstallAdLoadedListener;
    }

    public void zza(zzcn com_google_android_gms_internal_zzcn) {
        this.zzwD.onAppInstallAdLoaded(zzb(com_google_android_gms_internal_zzcn));
    }

    zzco zzb(zzcn com_google_android_gms_internal_zzcn) {
        return new zzco(com_google_android_gms_internal_zzcn);
    }
}
