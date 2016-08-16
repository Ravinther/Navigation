package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class zzru implements Cloneable {
    private zzrs<?, ?> zzbch;
    private Object zzbci;
    private List<zzrz> zzbcj;

    zzru() {
        this.zzbcj = new ArrayList();
    }

    private byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzB()];
        zza(zzrq.zzA(bArr));
        return bArr;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzDo();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzru)) {
            return false;
        }
        zzru com_google_android_gms_internal_zzru = (zzru) o;
        if (this.zzbci == null || com_google_android_gms_internal_zzru.zzbci == null) {
            if (this.zzbcj != null && com_google_android_gms_internal_zzru.zzbcj != null) {
                return this.zzbcj.equals(com_google_android_gms_internal_zzru.zzbcj);
            }
            try {
                return Arrays.equals(toByteArray(), com_google_android_gms_internal_zzru.toByteArray());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        } else if (this.zzbch != com_google_android_gms_internal_zzru.zzbch) {
            return false;
        } else {
            if (!this.zzbch.zzbcb.isArray()) {
                return this.zzbci.equals(com_google_android_gms_internal_zzru.zzbci);
            }
            if (this.zzbci instanceof byte[]) {
                return Arrays.equals((byte[]) this.zzbci, (byte[]) com_google_android_gms_internal_zzru.zzbci);
            }
            if (this.zzbci instanceof int[]) {
                return Arrays.equals((int[]) this.zzbci, (int[]) com_google_android_gms_internal_zzru.zzbci);
            }
            if (this.zzbci instanceof long[]) {
                return Arrays.equals((long[]) this.zzbci, (long[]) com_google_android_gms_internal_zzru.zzbci);
            }
            if (this.zzbci instanceof float[]) {
                return Arrays.equals((float[]) this.zzbci, (float[]) com_google_android_gms_internal_zzru.zzbci);
            }
            if (this.zzbci instanceof double[]) {
                return Arrays.equals((double[]) this.zzbci, (double[]) com_google_android_gms_internal_zzru.zzbci);
            }
            return this.zzbci instanceof boolean[] ? Arrays.equals((boolean[]) this.zzbci, (boolean[]) com_google_android_gms_internal_zzru.zzbci) : Arrays.deepEquals((Object[]) this.zzbci, (Object[]) com_google_android_gms_internal_zzru.zzbci);
        }
    }

    public int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    int zzB() {
        if (this.zzbci != null) {
            return this.zzbch.zzS(this.zzbci);
        }
        int i = 0;
        for (zzrz zzB : this.zzbcj) {
            i = zzB.zzB() + i;
        }
        return i;
    }

    public final zzru zzDo() {
        int i = 0;
        zzru com_google_android_gms_internal_zzru = new zzru();
        try {
            com_google_android_gms_internal_zzru.zzbch = this.zzbch;
            if (this.zzbcj == null) {
                com_google_android_gms_internal_zzru.zzbcj = null;
            } else {
                com_google_android_gms_internal_zzru.zzbcj.addAll(this.zzbcj);
            }
            if (this.zzbci != null) {
                if (this.zzbci instanceof zzrx) {
                    com_google_android_gms_internal_zzru.zzbci = ((zzrx) this.zzbci).zzDm();
                } else if (this.zzbci instanceof byte[]) {
                    com_google_android_gms_internal_zzru.zzbci = ((byte[]) this.zzbci).clone();
                } else if (this.zzbci instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.zzbci;
                    Object obj = new byte[bArr.length][];
                    com_google_android_gms_internal_zzru.zzbci = obj;
                    for (int i2 = 0; i2 < bArr.length; i2++) {
                        obj[i2] = (byte[]) bArr[i2].clone();
                    }
                } else if (this.zzbci instanceof boolean[]) {
                    com_google_android_gms_internal_zzru.zzbci = ((boolean[]) this.zzbci).clone();
                } else if (this.zzbci instanceof int[]) {
                    com_google_android_gms_internal_zzru.zzbci = ((int[]) this.zzbci).clone();
                } else if (this.zzbci instanceof long[]) {
                    com_google_android_gms_internal_zzru.zzbci = ((long[]) this.zzbci).clone();
                } else if (this.zzbci instanceof float[]) {
                    com_google_android_gms_internal_zzru.zzbci = ((float[]) this.zzbci).clone();
                } else if (this.zzbci instanceof double[]) {
                    com_google_android_gms_internal_zzru.zzbci = ((double[]) this.zzbci).clone();
                } else if (this.zzbci instanceof zzrx[]) {
                    zzrx[] com_google_android_gms_internal_zzrxArr = (zzrx[]) this.zzbci;
                    Object obj2 = new zzrx[com_google_android_gms_internal_zzrxArr.length];
                    com_google_android_gms_internal_zzru.zzbci = obj2;
                    while (i < com_google_android_gms_internal_zzrxArr.length) {
                        obj2[i] = com_google_android_gms_internal_zzrxArr[i].zzDm();
                        i++;
                    }
                }
            }
            return com_google_android_gms_internal_zzru;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
        if (this.zzbci != null) {
            this.zzbch.zza(this.zzbci, com_google_android_gms_internal_zzrq);
            return;
        }
        for (zzrz zza : this.zzbcj) {
            zza.zza(com_google_android_gms_internal_zzrq);
        }
    }
}
