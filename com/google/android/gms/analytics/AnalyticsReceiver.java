package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqd;

public final class AnalyticsReceiver extends BroadcastReceiver {
    static zzqd zzKc;
    static Boolean zzKd;
    static Object zzpm;

    static {
        zzpm = new Object();
    }

    public static boolean zzV(Context context) {
        zzx.zzv(context);
        if (zzKd != null) {
            return zzKd.booleanValue();
        }
        boolean zza = zzam.zza(context, AnalyticsReceiver.class, false);
        zzKd = Boolean.valueOf(zza);
        return zza;
    }

    public void onReceive(Context context, Intent intent) {
        zzf zzX = zzf.zzX(context);
        zzaf zzie = zzX.zzie();
        String action = intent.getAction();
        if (zzX.zzif().zzjk()) {
            zzie.zza("Device AnalyticsReceiver got", action);
        } else {
            zzie.zza("Local AnalyticsReceiver got", action);
        }
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zzW = AnalyticsService.zzW(context);
            Intent intent2 = new Intent(context, AnalyticsService.class);
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (zzpm) {
                context.startService(intent2);
                if (zzW) {
                    try {
                        if (zzKc == null) {
                            zzKc = new zzqd(context, 1, "Analytics WakeLock");
                            zzKc.setReferenceCounted(false);
                        }
                        zzKc.acquire(1000);
                    } catch (SecurityException e) {
                        zzie.zzbb("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                    return;
                }
            }
        }
    }
}
