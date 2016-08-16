package com.google.android.gms.analytics.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import com.google.android.gms.common.internal.zzx;

class zzag extends BroadcastReceiver {
    static final String zzOo;
    private final zzf zzLy;
    private boolean zzOp;
    private boolean zzOq;

    static {
        zzOo = zzag.class.getName();
    }

    zzag(zzf com_google_android_gms_analytics_internal_zzf) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzf);
        this.zzLy = com_google_android_gms_analytics_internal_zzf;
    }

    private Context getContext() {
        return this.zzLy.getContext();
    }

    private zzb zzhz() {
        return this.zzLy.zzhz();
    }

    private zzaf zzie() {
        return this.zzLy.zzie();
    }

    private void zzks() {
        zzie();
        zzhz();
    }

    public boolean isConnected() {
        if (!this.zzOp) {
            this.zzLy.zzie().zzbb("Connectivity unknown. Receiver not registered");
        }
        return this.zzOq;
    }

    public boolean isRegistered() {
        return this.zzOp;
    }

    public void onReceive(Context ctx, Intent intent) {
        zzks();
        String action = intent.getAction();
        this.zzLy.zzie().zza("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zzku = zzku();
            if (this.zzOq != zzku) {
                this.zzOq = zzku;
                zzhz().zzI(zzku);
            }
        } else if (!"com.google.analytics.RADIO_POWERED".equals(action)) {
            this.zzLy.zzie().zzd("NetworkBroadcastReceiver received unknown action", action);
        } else if (!intent.hasExtra(zzOo)) {
            zzhz().zzhY();
        }
    }

    public void unregister() {
        if (isRegistered()) {
            this.zzLy.zzie().zzaY("Unregistering connectivity change receiver");
            this.zzOp = false;
            this.zzOq = false;
            try {
                getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                zzie().zze("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    public void zzkr() {
        zzks();
        if (!this.zzOp) {
            Context context = getContext();
            context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            IntentFilter intentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
            intentFilter.addCategory(context.getPackageName());
            context.registerReceiver(this, intentFilter);
            this.zzOq = zzku();
            this.zzLy.zzie().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzOq));
            this.zzOp = true;
        }
    }

    public void zzkt() {
        if (VERSION.SDK_INT > 10) {
            Context context = getContext();
            Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
            intent.addCategory(context.getPackageName());
            intent.putExtra(zzOo, true);
            context.sendOrderedBroadcast(intent, null);
        }
    }

    protected boolean zzku() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (SecurityException e) {
            return false;
        }
    }
}
