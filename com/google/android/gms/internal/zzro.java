package com.google.android.gms.internal;

public class zzro {
    private final byte[] zzbbN;
    private int zzbbO;
    private int zzbbP;

    public zzro(byte[] bArr) {
        int i;
        this.zzbbN = new byte[256];
        for (i = 0; i < 256; i++) {
            this.zzbbN[i] = (byte) i;
        }
        i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            i = ((i + this.zzbbN[i2]) + bArr[i2 % bArr.length]) & 255;
            byte b = this.zzbbN[i2];
            this.zzbbN[i2] = this.zzbbN[i];
            this.zzbbN[i] = b;
        }
        this.zzbbO = 0;
        this.zzbbP = 0;
    }

    public void zzy(byte[] bArr) {
        int i = this.zzbbO;
        int i2 = this.zzbbP;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) & 255;
            i2 = (i2 + this.zzbbN[i]) & 255;
            byte b = this.zzbbN[i];
            this.zzbbN[i] = this.zzbbN[i2];
            this.zzbbN[i2] = b;
            bArr[i3] = (byte) (bArr[i3] ^ this.zzbbN[(this.zzbbN[i] + this.zzbbN[i2]) & 255]);
        }
        this.zzbbO = i;
        this.zzbbP = i2;
    }
}
