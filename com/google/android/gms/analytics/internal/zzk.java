package com.google.android.gms.analytics.internal;

import com.google.android.gms.internal.zzok;

public class zzk extends zzd {
    private final zzok zzMm;

    zzk(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzMm = new zzok();
    }

    protected void zzhB() {
        zzig().zzxu().zza(this.zzMm);
        zzhw();
    }

    public void zzhw() {
        zzan zzhA = zzhA();
        String zzjZ = zzhA.zzjZ();
        if (zzjZ != null) {
            this.zzMm.setAppName(zzjZ);
        }
        String zzkb = zzhA.zzkb();
        if (zzkb != null) {
            this.zzMm.setAppVersion(zzkb);
        }
    }

    public zzok zziL() {
        zzio();
        return this.zzMm;
    }
}
