package com.google.android.gms.analytics.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.common.internal.zzx;

public class zzv extends zzd {
    private boolean zzMV;
    private boolean zzMW;
    private AlarmManager zzMX;

    protected zzv(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzMX = (AlarmManager) getContext().getSystemService("alarm");
    }

    private PendingIntent zzjW() {
        Intent intent = new Intent(getContext(), AnalyticsReceiver.class);
        intent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        return PendingIntent.getBroadcast(getContext(), 0, intent, 0);
    }

    public void cancel() {
        zzio();
        this.zzMW = false;
        this.zzMX.cancel(zzjW());
    }

    public boolean zzbr() {
        return this.zzMW;
    }

    protected void zzhB() {
        try {
            this.zzMX.cancel(zzjW());
            if (zzif().zzjt() > 0) {
                ActivityInfo receiverInfo = getContext().getPackageManager().getReceiverInfo(new ComponentName(getContext(), AnalyticsReceiver.class), 2);
                if (receiverInfo != null && receiverInfo.enabled) {
                    zzaY("Receiver registered. Using alarm for local dispatch.");
                    this.zzMV = true;
                }
            }
        } catch (NameNotFoundException e) {
        }
    }

    public boolean zzjU() {
        return this.zzMV;
    }

    public void zzjV() {
        zzio();
        zzx.zza(zzjU(), "Receiver not registered");
        long zzjt = zzif().zzjt();
        if (zzjt > 0) {
            cancel();
            long elapsedRealtime = zzid().elapsedRealtime() + zzjt;
            this.zzMW = true;
            this.zzMX.setInexactRepeating(2, elapsedRealtime, 0, zzjW());
        }
    }
}
