package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.GooglePlayServicesUtil;

@zzgk
public class zzbx {
    private final Object zzpc;
    private boolean zzpr;
    private SharedPreferences zztU;

    public zzbx() {
        this.zzpc = new Object();
        this.zzpr = false;
        this.zztU = null;
    }

    public <T> T zzd(zzbu<T> com_google_android_gms_internal_zzbu_T) {
        synchronized (this.zzpc) {
            if (this.zzpr) {
                return com_google_android_gms_internal_zzbu_T.zza(this.zztU);
            }
            T zzdd = com_google_android_gms_internal_zzbu_T.zzdd();
            return zzdd;
        }
    }

    public void zzw(Context context) {
        synchronized (this.zzpc) {
            if (this.zzpr) {
                return;
            }
            Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
            if (remoteContext == null) {
                return;
            }
            this.zztU = zzp.zzbE().zzv(remoteContext);
            this.zzpr = true;
        }
    }
}
