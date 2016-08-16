package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.ads.internal.zzg;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzcg.zza;

@zzgk
public final class zzce extends zza {
    private final zzg zzvv;
    private final String zzvw;
    private final String zzvx;

    public zzce(zzg com_google_android_gms_ads_internal_zzg, String str, String str2) {
        this.zzvv = com_google_android_gms_ads_internal_zzg;
        this.zzvw = str;
        this.zzvx = str2;
    }

    public String getContent() {
        return this.zzvx;
    }

    public void recordClick() {
        this.zzvv.recordClick();
    }

    public void recordImpression() {
        this.zzvv.recordImpression();
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd) {
        if (com_google_android_gms_dynamic_zzd != null) {
            this.zzvv.zzc((View) zze.zzp(com_google_android_gms_dynamic_zzd));
        }
    }

    public String zzdp() {
        return this.zzvw;
    }
}
