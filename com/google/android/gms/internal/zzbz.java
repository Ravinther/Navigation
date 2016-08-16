package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build.VERSION;
import com.google.android.gms.ads.internal.zzp;
import java.util.LinkedHashMap;
import java.util.Map;

public class zzbz {
    private Context mContext;
    private String zzqK;
    private boolean zzvf;
    private String zzvg;
    private Map<String, String> zzvh;

    public zzbz(Context context, String str) {
        this.mContext = null;
        this.zzqK = null;
        this.mContext = context;
        this.zzqK = str;
        this.zzvf = ((Boolean) zzby.zzuB.get()).booleanValue();
        this.zzvg = (String) zzby.zzuC.get();
        this.zzvh = new LinkedHashMap();
        this.zzvh.put("s", "gmob_sdk");
        this.zzvh.put("v", "3");
        this.zzvh.put("os", VERSION.RELEASE);
        this.zzvh.put("sdk", VERSION.SDK);
        this.zzvh.put("device", zzp.zzbx().zzgt());
        this.zzvh.put("ua", zzp.zzbx().zzf(context, str));
        this.zzvh.put("app", context.getApplicationContext() != null ? context.getApplicationContext().getPackageName() : context.getPackageName());
        if (zzp.zzbx().zza(context.getPackageManager(), context.getPackageName(), "android.permission.ACCESS_NETWORK_STATE")) {
            zzgr zzD = zzp.zzbD().zzD(this.mContext);
            this.zzvh.put("network_coarse", Integer.toString(zzD.zzFN));
            this.zzvh.put("network_fine", Integer.toString(zzD.zzFO));
        }
    }

    Context getContext() {
        return this.mContext;
    }

    String zzbV() {
        return this.zzqK;
    }

    boolean zzdf() {
        return this.zzvf;
    }

    String zzdg() {
        return this.zzvg;
    }

    Map<String, String> zzdh() {
        return this.zzvh;
    }
}
