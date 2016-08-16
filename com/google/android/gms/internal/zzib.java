package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzp;

public class zzib {
    private long zzIp;
    private long zzIq;
    private Object zzpc;

    public zzib(long j) {
        this.zzIq = Long.MIN_VALUE;
        this.zzpc = new Object();
        this.zzIp = j;
    }

    public boolean tryAcquire() {
        boolean z;
        synchronized (this.zzpc) {
            long elapsedRealtime = zzp.zzbB().elapsedRealtime();
            if (this.zzIq + this.zzIp > elapsedRealtime) {
                z = false;
            } else {
                this.zzIq = elapsedRealtime;
                z = true;
            }
        }
        return z;
    }
}
