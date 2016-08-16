package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzjb;
import com.google.android.gms.internal.zzob;
import com.google.android.gms.internal.zzoe;
import com.google.android.gms.internal.zzoh;
import java.util.ListIterator;

public class zza extends zzoe<zza> {
    private final zzf zzKa;
    private boolean zzKb;

    public zza(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf.zzig(), com_google_android_gms_analytics_internal_zzf.zzid());
        this.zzKa = com_google_android_gms_analytics_internal_zzf;
    }

    public void enableAdvertisingIdCollection(boolean enable) {
        this.zzKb = enable;
    }

    protected void zza(zzob com_google_android_gms_internal_zzob) {
        zzjb com_google_android_gms_internal_zzjb = (zzjb) com_google_android_gms_internal_zzob.zze(zzjb.class);
        if (TextUtils.isEmpty(com_google_android_gms_internal_zzjb.getClientId())) {
            com_google_android_gms_internal_zzjb.setClientId(this.zzKa.zziv().zzjd());
        }
        if (this.zzKb && TextUtils.isEmpty(com_google_android_gms_internal_zzjb.zzhL())) {
            com.google.android.gms.analytics.internal.zza zziu = this.zzKa.zziu();
            com_google_android_gms_internal_zzjb.zzaT(zziu.zzhQ());
            com_google_android_gms_internal_zzjb.zzG(zziu.zzhM());
        }
    }

    public void zzaN(String str) {
        zzx.zzcs(str);
        zzaO(str);
        zzxt().add(new zzb(this.zzKa, str));
    }

    public void zzaO(String str) {
        Uri zzaP = zzb.zzaP(str);
        ListIterator listIterator = zzxt().listIterator();
        while (listIterator.hasNext()) {
            if (zzaP.equals(((zzoh) listIterator.next()).zzhs())) {
                listIterator.remove();
            }
        }
    }

    zzf zzhp() {
        return this.zzKa;
    }

    public zzob zzhq() {
        zzob zzxh = zzxs().zzxh();
        zzxh.zzb(this.zzKa.zzil().zziL());
        zzxh.zzb(this.zzKa.zzim().zzjS());
        zzd(zzxh);
        return zzxh;
    }
}
