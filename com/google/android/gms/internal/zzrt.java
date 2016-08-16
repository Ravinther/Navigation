package com.google.android.gms.internal;

class zzrt implements Cloneable {
    private static final zzru zzbcd;
    private int mSize;
    private boolean zzbce;
    private int[] zzbcf;
    private zzru[] zzbcg;

    static {
        zzbcd = new zzru();
    }

    public zzrt() {
        this(10);
    }

    public zzrt(int i) {
        this.zzbce = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzbcf = new int[idealIntArraySize];
        this.zzbcg = new zzru[idealIntArraySize];
        this.mSize = 0;
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.zzbcf;
        zzru[] com_google_android_gms_internal_zzruArr = this.zzbcg;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            zzru com_google_android_gms_internal_zzru = com_google_android_gms_internal_zzruArr[i3];
            if (com_google_android_gms_internal_zzru != zzbcd) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    com_google_android_gms_internal_zzruArr[i2] = com_google_android_gms_internal_zzru;
                    com_google_android_gms_internal_zzruArr[i3] = null;
                }
                i2++;
            }
        }
        this.zzbce = false;
        this.mSize = i2;
    }

    private int idealByteArraySize(int need) {
        for (int i = 4; i < 32; i++) {
            if (need <= (1 << i) - 12) {
                return (1 << i) - 12;
            }
        }
        return need;
    }

    private int idealIntArraySize(int need) {
        return idealByteArraySize(need * 4) / 4;
    }

    private boolean zza(int[] iArr, int[] iArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean zza(zzru[] com_google_android_gms_internal_zzruArr, zzru[] com_google_android_gms_internal_zzruArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!com_google_android_gms_internal_zzruArr[i2].equals(com_google_android_gms_internal_zzruArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzDn();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzrt)) {
            return false;
        }
        zzrt com_google_android_gms_internal_zzrt = (zzrt) o;
        if (size() != com_google_android_gms_internal_zzrt.size()) {
            return false;
        }
        return zza(this.zzbcf, com_google_android_gms_internal_zzrt.zzbcf, this.mSize) && zza(this.zzbcg, com_google_android_gms_internal_zzrt.zzbcg, this.mSize);
    }

    public int hashCode() {
        if (this.zzbce) {
            gc();
        }
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzbcf[i2]) * 31) + this.zzbcg[i2].hashCode();
        }
        return i;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        if (this.zzbce) {
            gc();
        }
        return this.mSize;
    }

    public final zzrt zzDn() {
        int i = 0;
        int size = size();
        zzrt com_google_android_gms_internal_zzrt = new zzrt(size);
        System.arraycopy(this.zzbcf, 0, com_google_android_gms_internal_zzrt.zzbcf, 0, size);
        while (i < size) {
            if (this.zzbcg[i] != null) {
                com_google_android_gms_internal_zzrt.zzbcg[i] = this.zzbcg[i].zzDo();
            }
            i++;
        }
        com_google_android_gms_internal_zzrt.mSize = size;
        return com_google_android_gms_internal_zzrt;
    }

    public zzru zzlB(int i) {
        if (this.zzbce) {
            gc();
        }
        return this.zzbcg[i];
    }
}
