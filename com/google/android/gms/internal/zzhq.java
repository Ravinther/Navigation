package com.google.android.gms.internal;

import java.util.concurrent.Future;

@zzgk
public abstract class zzhq {
    private volatile Thread zzHt;
    private final Runnable zzx;

    /* renamed from: com.google.android.gms.internal.zzhq.1 */
    class C09401 implements Runnable {
        final /* synthetic */ zzhq zzHu;

        C09401(zzhq com_google_android_gms_internal_zzhq) {
            this.zzHu = com_google_android_gms_internal_zzhq;
        }

        public final void run() {
            this.zzHu.zzHt = Thread.currentThread();
            this.zzHu.zzdG();
        }
    }

    public zzhq() {
        this.zzx = new C09401(this);
    }

    public final void cancel() {
        onStop();
        if (this.zzHt != null) {
            this.zzHt.interrupt();
        }
    }

    public abstract void onStop();

    public abstract void zzdG();

    public final Future zzgn() {
        return zzht.zza(this.zzx);
    }

    public final void zzgo() {
        zzht.zza(1, this.zzx);
    }
}
