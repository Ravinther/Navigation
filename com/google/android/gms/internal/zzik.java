package com.google.android.gms.internal;

import com.google.android.gms.internal.zzij.zzc;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@zzgk
public class zzik<T> implements zzij<T> {
    protected final BlockingQueue<com.google.android.gms.internal.zzik$com.google.android.gms.internal.zzik.zza> zzIN;
    protected T zzIO;
    private final Object zzpc;
    protected int zzxJ;

    class zza {
        public final zzc<T> zzIP;
        public final com.google.android.gms.internal.zzij.zza zzIQ;
        final /* synthetic */ zzik zzIR;

        public zza(zzik com_google_android_gms_internal_zzik, zzc<T> com_google_android_gms_internal_zzij_zzc_T, com.google.android.gms.internal.zzij.zza com_google_android_gms_internal_zzij_zza) {
            this.zzIR = com_google_android_gms_internal_zzik;
            this.zzIP = com_google_android_gms_internal_zzij_zzc_T;
            this.zzIQ = com_google_android_gms_internal_zzij_zza;
        }
    }

    public zzik() {
        this.zzpc = new Object();
        this.zzxJ = 0;
        this.zzIN = new LinkedBlockingQueue();
    }

    public int getStatus() {
        return this.zzxJ;
    }

    public void reject() {
        synchronized (this.zzpc) {
            if (this.zzxJ != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzxJ = -1;
            for (zza com_google_android_gms_internal_zzik_zza : this.zzIN) {
                com_google_android_gms_internal_zzik_zza.zzIQ.run();
            }
            this.zzIN.clear();
        }
    }

    public void zza(zzc<T> com_google_android_gms_internal_zzij_zzc_T, com.google.android.gms.internal.zzij.zza com_google_android_gms_internal_zzij_zza) {
        synchronized (this.zzpc) {
            if (this.zzxJ == 1) {
                com_google_android_gms_internal_zzij_zzc_T.zzc(this.zzIO);
            } else if (this.zzxJ == -1) {
                com_google_android_gms_internal_zzij_zza.run();
            } else if (this.zzxJ == 0) {
                this.zzIN.add(new zza(this, com_google_android_gms_internal_zzij_zzc_T, com_google_android_gms_internal_zzij_zza));
            }
        }
    }

    public void zzg(T t) {
        synchronized (this.zzpc) {
            if (this.zzxJ != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzIO = t;
            this.zzxJ = 1;
            for (zza com_google_android_gms_internal_zzik_zza : this.zzIN) {
                com_google_android_gms_internal_zzik_zza.zzIP.zzc(t);
            }
            this.zzIN.clear();
        }
    }
}
