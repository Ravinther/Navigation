package com.google.android.gms.analytics.internal;

public abstract class zzd extends zzc {
    private boolean zzLA;
    private boolean zzLz;

    protected zzd(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
    }

    public boolean isInitialized() {
        return this.zzLz && !this.zzLA;
    }

    public void zza() {
        zzhB();
        this.zzLz = true;
    }

    protected abstract void zzhB();

    protected void zzio() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}
