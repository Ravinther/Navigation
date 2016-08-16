package com.google.android.gms.internal;

import java.io.IOException;

class zzas implements zzaq {
    private zzrq zznF;
    private byte[] zznG;
    private final int zznH;

    public zzas(int i) {
        this.zznH = i;
        reset();
    }

    public void reset() {
        this.zznG = new byte[this.zznH];
        this.zznF = zzrq.zzA(this.zznG);
    }

    public byte[] zzac() throws IOException {
        int zzDi = this.zznF.zzDi();
        if (zzDi < 0) {
            throw new IOException();
        } else if (zzDi == 0) {
            return this.zznG;
        } else {
            Object obj = new byte[(this.zznG.length - zzDi)];
            System.arraycopy(this.zznG, 0, obj, 0, obj.length);
            return obj;
        }
    }

    public void zzb(int i, long j) throws IOException {
        this.zznF.zzb(i, j);
    }

    public void zzb(int i, String str) throws IOException {
        this.zznF.zzb(i, str);
    }
}
