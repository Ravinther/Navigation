package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

final class zzrz {
    final int tag;
    final byte[] zzbcm;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzrz)) {
            return false;
        }
        zzrz com_google_android_gms_internal_zzrz = (zzrz) o;
        return this.tag == com_google_android_gms_internal_zzrz.tag && Arrays.equals(this.zzbcm, com_google_android_gms_internal_zzrz.zzbcm);
    }

    public int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbcm);
    }

    int zzB() {
        return (0 + zzrq.zzlx(this.tag)) + this.zzbcm.length;
    }

    void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
        com_google_android_gms_internal_zzrq.zzlw(this.tag);
        com_google_android_gms_internal_zzrq.zzD(this.zzbcm);
    }
}
