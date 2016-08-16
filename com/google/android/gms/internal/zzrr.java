package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzrr<M extends zzrr<M>> extends zzrx {
    protected zzrt zzbca;

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzDl();
    }

    protected int zzB() {
        int i = 0;
        if (this.zzbca == null) {
            return 0;
        }
        int i2 = 0;
        while (i < this.zzbca.size()) {
            i2 += this.zzbca.zzlB(i).zzB();
            i++;
        }
        return i2;
    }

    protected final int zzDk() {
        return (this.zzbca == null || this.zzbca.isEmpty()) ? 0 : this.zzbca.hashCode();
    }

    public M zzDl() throws CloneNotSupportedException {
        zzrr com_google_android_gms_internal_zzrr = (zzrr) super.zzDm();
        zzrv.zza(this, com_google_android_gms_internal_zzrr);
        return com_google_android_gms_internal_zzrr;
    }

    public /* synthetic */ zzrx zzDm() throws CloneNotSupportedException {
        return zzDl();
    }

    public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
        if (this.zzbca != null) {
            for (int i = 0; i < this.zzbca.size(); i++) {
                this.zzbca.zzlB(i).zza(com_google_android_gms_internal_zzrq);
            }
        }
    }

    protected final boolean zza(M m) {
        return (this.zzbca == null || this.zzbca.isEmpty()) ? m.zzbca == null || m.zzbca.isEmpty() : this.zzbca.equals(m.zzbca);
    }
}
