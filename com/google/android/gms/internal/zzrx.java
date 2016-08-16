package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzrx {
    protected volatile int zzbcl;

    public zzrx() {
        this.zzbcl = -1;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzDm();
    }

    public String toString() {
        return zzry.zzg(this);
    }

    protected int zzB() {
        return 0;
    }

    public zzrx zzDm() throws CloneNotSupportedException {
        return (zzrx) super.clone();
    }

    public int zzDw() {
        if (this.zzbcl < 0) {
            zzDx();
        }
        return this.zzbcl;
    }

    public int zzDx() {
        int zzB = zzB();
        this.zzbcl = zzB;
        return zzB;
    }

    public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
    }
}
