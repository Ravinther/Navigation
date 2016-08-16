package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzlm;

class zzaj {
    private long zzOC;
    private final zzlm zzpO;

    public zzaj(zzlm com_google_android_gms_internal_zzlm) {
        zzx.zzv(com_google_android_gms_internal_zzlm);
        this.zzpO = com_google_android_gms_internal_zzlm;
    }

    public zzaj(zzlm com_google_android_gms_internal_zzlm, long j) {
        zzx.zzv(com_google_android_gms_internal_zzlm);
        this.zzpO = com_google_android_gms_internal_zzlm;
        this.zzOC = j;
    }

    public void clear() {
        this.zzOC = 0;
    }

    public void start() {
        this.zzOC = this.zzpO.elapsedRealtime();
    }

    public boolean zzv(long j) {
        return this.zzOC == 0 || this.zzpO.elapsedRealtime() - this.zzOC > j;
    }
}
