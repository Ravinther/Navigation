package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.List;

public abstract class zzoe<T extends zzoe> {
    private final zzof zzaIa;
    protected final zzob zzaIb;
    private final List<zzoc> zzaIc;

    protected zzoe(zzof com_google_android_gms_internal_zzof, zzlm com_google_android_gms_internal_zzlm) {
        zzx.zzv(com_google_android_gms_internal_zzof);
        this.zzaIa = com_google_android_gms_internal_zzof;
        this.zzaIc = new ArrayList();
        zzob com_google_android_gms_internal_zzob = new zzob(this, com_google_android_gms_internal_zzlm);
        com_google_android_gms_internal_zzob.zzxr();
        this.zzaIb = com_google_android_gms_internal_zzob;
    }

    protected void zza(zzob com_google_android_gms_internal_zzob) {
    }

    protected void zzd(zzob com_google_android_gms_internal_zzob) {
        for (zzoc zza : this.zzaIc) {
            zza.zza(this, com_google_android_gms_internal_zzob);
        }
    }

    public zzob zzhq() {
        zzob zzxh = this.zzaIb.zzxh();
        zzd(zzxh);
        return zzxh;
    }

    protected zzof zzxp() {
        return this.zzaIa;
    }

    public zzob zzxs() {
        return this.zzaIb;
    }

    public List<zzoh> zzxt() {
        return this.zzaIb.zzxj();
    }
}
