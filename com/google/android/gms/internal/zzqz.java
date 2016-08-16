package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.tagmanager.zzbg;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class zzqz {
    private boolean mClosed;
    private String zzaPw;
    private final ScheduledExecutorService zzaRr;
    private ScheduledFuture<?> zzaRt;

    public zzqz() {
        this(Executors.newSingleThreadScheduledExecutor());
    }

    public zzqz(String str) {
        this(Executors.newSingleThreadScheduledExecutor());
        this.zzaPw = str;
    }

    zzqz(ScheduledExecutorService scheduledExecutorService) {
        this.zzaRt = null;
        this.zzaPw = null;
        this.zzaRr = scheduledExecutorService;
        this.mClosed = false;
    }

    public void zza(Context context, zzqn com_google_android_gms_internal_zzqn, long j, zzqx com_google_android_gms_internal_zzqx) {
        synchronized (this) {
            zzbg.m1448v("ResourceLoaderScheduler: Loading new resource.");
            if (this.zzaRt != null) {
                return;
            }
            this.zzaRt = this.zzaRr.schedule(this.zzaPw != null ? new zzqy(context, com_google_android_gms_internal_zzqn, com_google_android_gms_internal_zzqx, this.zzaPw) : new zzqy(context, com_google_android_gms_internal_zzqn, com_google_android_gms_internal_zzqx), j, TimeUnit.MILLISECONDS);
        }
    }
}
