package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqd;

public final class AnalyticsService extends Service {
    private static Boolean zzKe;
    private final Handler mHandler;

    /* renamed from: com.google.android.gms.analytics.AnalyticsService.1 */
    class C06091 implements zzw {
        final /* synthetic */ int zzKf;
        final /* synthetic */ zzf zzKg;
        final /* synthetic */ zzaf zzKh;
        final /* synthetic */ AnalyticsService zzKi;

        /* renamed from: com.google.android.gms.analytics.AnalyticsService.1.1 */
        class C06081 implements Runnable {
            final /* synthetic */ C06091 zzKj;

            C06081(C06091 c06091) {
                this.zzKj = c06091;
            }

            public void run() {
                if (!this.zzKj.zzKi.stopSelfResult(this.zzKj.zzKf)) {
                    return;
                }
                if (this.zzKj.zzKg.zzif().zzjk()) {
                    this.zzKj.zzKh.zzaY("Device AnalyticsService processed last dispatch request");
                } else {
                    this.zzKj.zzKh.zzaY("Local AnalyticsService processed last dispatch request");
                }
            }
        }

        C06091(AnalyticsService analyticsService, int i, zzf com_google_android_gms_analytics_internal_zzf, zzaf com_google_android_gms_analytics_internal_zzaf) {
            this.zzKi = analyticsService;
            this.zzKf = i;
            this.zzKg = com_google_android_gms_analytics_internal_zzf;
            this.zzKh = com_google_android_gms_analytics_internal_zzaf;
        }

        public void zzc(Throwable th) {
            this.zzKi.mHandler.post(new C06081(this));
        }
    }

    public AnalyticsService() {
        this.mHandler = new Handler();
    }

    public static boolean zzW(Context context) {
        zzx.zzv(context);
        if (zzKe != null) {
            return zzKe.booleanValue();
        }
        boolean zza = zzam.zza(context, AnalyticsService.class);
        zzKe = Boolean.valueOf(zza);
        return zza;
    }

    private void zzhr() {
        try {
            synchronized (AnalyticsReceiver.zzpm) {
                zzqd com_google_android_gms_internal_zzqd = AnalyticsReceiver.zzKc;
                if (com_google_android_gms_internal_zzqd != null && com_google_android_gms_internal_zzqd.isHeld()) {
                    com_google_android_gms_internal_zzqd.release();
                }
            }
        } catch (SecurityException e) {
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        zzf zzX = zzf.zzX(this);
        zzaf zzie = zzX.zzie();
        if (zzX.zzif().zzjk()) {
            zzie.zzaY("Device AnalyticsService is starting up");
        } else {
            zzie.zzaY("Local AnalyticsService is starting up");
        }
    }

    public void onDestroy() {
        zzf zzX = zzf.zzX(this);
        zzaf zzie = zzX.zzie();
        if (zzX.zzif().zzjk()) {
            zzie.zzaY("Device AnalyticsService is shutting down");
        } else {
            zzie.zzaY("Local AnalyticsService is shutting down");
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        zzhr();
        zzf zzX = zzf.zzX(this);
        zzaf zzie = zzX.zzie();
        String action = intent.getAction();
        if (zzX.zzif().zzjk()) {
            zzie.zza("Device AnalyticsService called. startId, action", Integer.valueOf(startId), action);
        } else {
            zzie.zza("Local AnalyticsService called. startId, action", Integer.valueOf(startId), action);
        }
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            zzX.zzhz().zza(new C06091(this, startId, zzX, zzie));
        }
        return 2;
    }
}
