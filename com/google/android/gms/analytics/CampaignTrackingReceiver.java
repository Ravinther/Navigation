package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqd;

public class CampaignTrackingReceiver extends BroadcastReceiver {
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
        boolean zza = zzam.zza(context, CampaignTrackingReceiver.class, true);
        zzKd = Boolean.valueOf(zza);
        return zza;
    }

    public void onReceive(Context context, Intent intent) {
        zzf zzX = zzf.zzX(context);
        zzaf zzie = zzX.zzie();
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzie.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzie.zzbb("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        boolean zzW = CampaignTrackingService.zzW(context);
        if (!zzW) {
            zzie.zzbb("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzaQ(stringExtra);
        if (zzX.zzif().zzjk()) {
            zzie.zzbc("Received unexpected installation campaign on package side");
            return;
        }
        Class zzht = zzht();
        zzx.zzv(zzht);
        Intent intent2 = new Intent(context, zzht);
        intent2.putExtra("referrer", stringExtra);
        synchronized (zzpm) {
            context.startService(intent2);
            if (zzW) {
                try {
                    if (zzKc == null) {
                        zzKc = new zzqd(context, 1, "Analytics campaign WakeLock");
                        zzKc.setReferenceCounted(false);
                    }
                    zzKc.acquire(1000);
                } catch (SecurityException e) {
                    zzie.zzbb("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                }
                return;
            }
        }
    }

    protected void zzaQ(String str) {
    }

    protected Class<? extends CampaignTrackingService> zzht() {
        return CampaignTrackingService.class;
    }
}
