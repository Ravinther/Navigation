package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqd;

public class CampaignTrackingService extends Service {
    private static Boolean zzKe;
    private Handler mHandler;

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.1 */
    class C06101 implements Runnable {
        final /* synthetic */ int zzKf;
        final /* synthetic */ zzaf zzKh;
        final /* synthetic */ CampaignTrackingService zzKo;
        final /* synthetic */ Handler zzt;

        C06101(CampaignTrackingService campaignTrackingService, zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
            this.zzKo = campaignTrackingService;
            this.zzKh = com_google_android_gms_analytics_internal_zzaf;
            this.zzt = handler;
            this.zzKf = i;
        }

        public void run() {
            this.zzKo.zza(this.zzKh, this.zzt, this.zzKf);
        }
    }

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.2 */
    class C06112 implements Runnable {
        final /* synthetic */ int zzKf;
        final /* synthetic */ zzaf zzKh;
        final /* synthetic */ CampaignTrackingService zzKo;
        final /* synthetic */ Handler zzt;

        C06112(CampaignTrackingService campaignTrackingService, zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
            this.zzKo = campaignTrackingService;
            this.zzKh = com_google_android_gms_analytics_internal_zzaf;
            this.zzt = handler;
            this.zzKf = i;
        }

        public void run() {
            this.zzKo.zza(this.zzKh, this.zzt, this.zzKf);
        }
    }

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.3 */
    class C06123 implements Runnable {
        final /* synthetic */ int zzKf;
        final /* synthetic */ zzaf zzKh;
        final /* synthetic */ CampaignTrackingService zzKo;

        C06123(CampaignTrackingService campaignTrackingService, int i, zzaf com_google_android_gms_analytics_internal_zzaf) {
            this.zzKo = campaignTrackingService;
            this.zzKf = i;
            this.zzKh = com_google_android_gms_analytics_internal_zzaf;
        }

        public void run() {
            boolean stopSelfResult = this.zzKo.stopSelfResult(this.zzKf);
            if (stopSelfResult) {
                this.zzKh.zza("Install campaign broadcast processed", Boolean.valueOf(stopSelfResult));
            }
        }
    }

    private Handler getHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            return handler;
        }
        handler = new Handler(getMainLooper());
        this.mHandler = handler;
        return handler;
    }

    public static boolean zzW(Context context) {
        zzx.zzv(context);
        if (zzKe != null) {
            return zzKe.booleanValue();
        }
        boolean zza = zzam.zza(context, CampaignTrackingService.class);
        zzKe = Boolean.valueOf(zza);
        return zza;
    }

    private void zzhr() {
        try {
            synchronized (CampaignTrackingReceiver.zzpm) {
                zzqd com_google_android_gms_internal_zzqd = CampaignTrackingReceiver.zzKc;
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
        zzf.zzX(this).zzie().zzaY("CampaignTrackingService is starting up");
    }

    public void onDestroy() {
        zzf.zzX(this).zzie().zzaY("CampaignTrackingService is shutting down");
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        zzhr();
        zzf zzX = zzf.zzX(this);
        zzaf zzie = zzX.zzie();
        String str = null;
        if (zzX.zzif().zzjk()) {
            zzie.zzbc("Unexpected installation campaign (package side)");
        } else {
            str = intent.getStringExtra("referrer");
        }
        Handler handler = getHandler();
        if (TextUtils.isEmpty(str)) {
            if (!zzX.zzif().zzjk()) {
                zzie.zzbb("No campaign found on com.android.vending.INSTALL_REFERRER \"referrer\" extra");
            }
            zzX.zzig().zzf(new C06101(this, zzie, handler, startId));
        } else {
            int zzjo = zzX.zzif().zzjo();
            if (str.length() > zzjo) {
                zzie.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(str.length()), Integer.valueOf(zzjo));
                str = str.substring(0, zzjo);
            }
            zzie.zza("CampaignTrackingService called. startId, campaign", Integer.valueOf(startId), str);
            zzX.zzhz().zza(str, new C06112(this, zzie, handler, startId));
        }
        return 2;
    }

    protected void zza(zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
        handler.post(new C06123(this, i, com_google_android_gms_analytics_internal_zzaf));
    }
}
