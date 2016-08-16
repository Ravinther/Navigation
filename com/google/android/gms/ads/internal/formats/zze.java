package com.google.android.gms.ads.internal.formats;

import android.os.Bundle;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzcj;
import com.google.android.gms.internal.zzcp.zza;
import com.google.android.gms.internal.zzgk;
import java.util.List;

@zzgk
public class zze extends zza implements zzh.zza {
    private final Bundle mExtras;
    private final Object zzpc;
    private final String zzvK;
    private final List<zzc> zzvL;
    private final String zzvM;
    private final String zzvO;
    private final zza zzvS;
    private zzh zzvT;
    private final zzcj zzvU;
    private final String zzvV;

    public zze(String str, List list, String str2, zzcj com_google_android_gms_internal_zzcj, String str3, String str4, zza com_google_android_gms_ads_internal_formats_zza, Bundle bundle) {
        this.zzpc = new Object();
        this.zzvK = str;
        this.zzvL = list;
        this.zzvM = str2;
        this.zzvU = com_google_android_gms_internal_zzcj;
        this.zzvO = str3;
        this.zzvV = str4;
        this.zzvS = com_google_android_gms_ads_internal_formats_zza;
        this.mExtras = bundle;
    }

    public String getAdvertiser() {
        return this.zzvV;
    }

    public String getBody() {
        return this.zzvM;
    }

    public String getCallToAction() {
        return this.zzvO;
    }

    public String getCustomTemplateId() {
        return "";
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getHeadline() {
        return this.zzvK;
    }

    public List getImages() {
        return this.zzvL;
    }

    public void zza(zzh com_google_android_gms_ads_internal_formats_zzh) {
        synchronized (this.zzpc) {
            this.zzvT = com_google_android_gms_ads_internal_formats_zzh;
        }
    }

    public zzd zzdt() {
        return com.google.android.gms.dynamic.zze.zzx(this.zzvT);
    }

    public String zzdu() {
        return "1";
    }

    public zza zzdv() {
        return this.zzvS;
    }

    public zzcj zzdw() {
        return this.zzvU;
    }
}
