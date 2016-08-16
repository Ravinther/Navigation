package com.google.android.gms.internal;

import android.os.HandlerThread;
import android.os.Looper;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzx;

public class zzhz {
    private HandlerThread zzIn;
    private int zzIo;
    private final Object zzpc;

    public zzhz() {
        this.zzIn = null;
        this.zzIo = 0;
        this.zzpc = new Object();
    }

    public Looper zzgB() {
        Looper looper;
        synchronized (this.zzpc) {
            if (this.zzIn == null) {
                zzx.zzb(this.zzIo == 0, (Object) "Invalid state: mHandlerThread should already been initialized.");
                zzb.m1445v("Starting the looper provider thread.");
                this.zzIn = new HandlerThread("LooperProvider");
                this.zzIn.start();
            }
            this.zzIo++;
            looper = this.zzIn.getLooper();
        }
        return looper;
    }

    public void zzgC() {
        synchronized (this.zzpc) {
            zzx.zzb(this.zzIo > 0, (Object) "Invalid state: release() called more times than expected.");
            int i = this.zzIo - 1;
            this.zzIo = i;
            if (i == 0) {
                zzb.m1445v("Terminate the looper provider thread.");
                this.zzIn.quit();
                this.zzIn = null;
            }
        }
    }
}
