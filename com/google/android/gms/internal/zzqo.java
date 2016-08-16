package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqp.zzc;

public class zzqo implements Result {
    private final zza zzaTI;

    public static class zza {
        private final Status zzQA;
        private final zza zzaTJ;
        private final byte[] zzaTK;
        private final long zzaTL;
        private final zzqi zzaTM;
        private final zzc zzaTN;

        public enum zza {
            NETWORK,
            DISK,
            DEFAULT
        }

        public zza(Status status, zzqi com_google_android_gms_internal_zzqi, zza com_google_android_gms_internal_zzqo_zza_zza) {
            this(status, com_google_android_gms_internal_zzqi, null, null, com_google_android_gms_internal_zzqo_zza_zza, 0);
        }

        public zza(Status status, zzqi com_google_android_gms_internal_zzqi, byte[] bArr, zzc com_google_android_gms_internal_zzqp_zzc, zza com_google_android_gms_internal_zzqo_zza_zza, long j) {
            this.zzQA = status;
            this.zzaTM = com_google_android_gms_internal_zzqi;
            this.zzaTK = bArr;
            this.zzaTN = com_google_android_gms_internal_zzqp_zzc;
            this.zzaTJ = com_google_android_gms_internal_zzqo_zza_zza;
            this.zzaTL = j;
        }

        public Status getStatus() {
            return this.zzQA;
        }

        public zzc zzBA() {
            return this.zzaTN;
        }

        public long zzBB() {
            return this.zzaTL;
        }

        public zza zzBx() {
            return this.zzaTJ;
        }

        public byte[] zzBy() {
            return this.zzaTK;
        }

        public zzqi zzBz() {
            return this.zzaTM;
        }
    }

    public zzqo(zza com_google_android_gms_internal_zzqo_zza) {
        this.zzaTI = com_google_android_gms_internal_zzqo_zza;
    }

    public Status getStatus() {
        return this.zzaTI.getStatus();
    }

    public zza zzBw() {
        return this.zzaTI;
    }
}
