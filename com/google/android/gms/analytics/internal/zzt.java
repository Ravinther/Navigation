package com.google.android.gms.analytics.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.zzx;

abstract class zzt {
    private static volatile Handler zzMR;
    private final zzf zzLy;
    private volatile long zzMS;
    private boolean zzMT;
    private final Runnable zzx;

    /* renamed from: com.google.android.gms.analytics.internal.zzt.1 */
    class C06321 implements Runnable {
        final /* synthetic */ zzt zzMU;

        C06321(zzt com_google_android_gms_analytics_internal_zzt) {
            this.zzMU = com_google_android_gms_analytics_internal_zzt;
        }

        public void run() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.zzMU.zzLy.zzig().zzf(this);
                return;
            }
            boolean zzbr = this.zzMU.zzbr();
            this.zzMU.zzMS = 0;
            if (zzbr && !this.zzMU.zzMT) {
                this.zzMU.run();
            }
        }
    }

    zzt(zzf com_google_android_gms_analytics_internal_zzf) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzf);
        this.zzLy = com_google_android_gms_analytics_internal_zzf;
        this.zzx = new C06321(this);
    }

    private Handler getHandler() {
        if (zzMR != null) {
            return zzMR;
        }
        Handler handler;
        synchronized (zzt.class) {
            if (zzMR == null) {
                zzMR = new Handler(this.zzLy.getContext().getMainLooper());
            }
            handler = zzMR;
        }
        return handler;
    }

    public void cancel() {
        this.zzMS = 0;
        getHandler().removeCallbacks(this.zzx);
    }

    public abstract void run();

    public boolean zzbr() {
        return this.zzMS != 0;
    }

    public long zzjR() {
        return this.zzMS == 0 ? 0 : Math.abs(this.zzLy.zzid().currentTimeMillis() - this.zzMS);
    }

    public void zzt(long j) {
        cancel();
        if (j >= 0) {
            this.zzMS = this.zzLy.zzid().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzx, j)) {
                this.zzLy.zzie().zze("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public void zzu(long j) {
        long j2 = 0;
        if (!zzbr()) {
            return;
        }
        if (j < 0) {
            cancel();
            return;
        }
        long abs = j - Math.abs(this.zzLy.zzid().currentTimeMillis() - this.zzMS);
        if (abs >= 0) {
            j2 = abs;
        }
        getHandler().removeCallbacks(this.zzx);
        if (!getHandler().postDelayed(this.zzx, j2)) {
            this.zzLy.zzie().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
        }
    }
}
