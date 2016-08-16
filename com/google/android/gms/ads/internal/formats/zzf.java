package com.google.android.gms.ads.internal.formats;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzcj;
import com.google.android.gms.internal.zzcr.zza;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzlh;
import java.util.Arrays;
import java.util.List;

@zzgk
public class zzf extends zza implements zzh.zza {
    private final Object zzpc;
    private final zza zzvS;
    private zzh zzvT;
    private final String zzvW;
    private final zzlh<String, zzc> zzvX;
    private final zzlh<String, String> zzvY;

    public zzf(String str, zzlh<String, zzc> com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_ads_internal_formats_zzc, zzlh<String, String> com_google_android_gms_internal_zzlh_java_lang_String__java_lang_String, zza com_google_android_gms_ads_internal_formats_zza) {
        this.zzpc = new Object();
        this.zzvW = str;
        this.zzvX = com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_ads_internal_formats_zzc;
        this.zzvY = com_google_android_gms_internal_zzlh_java_lang_String__java_lang_String;
        this.zzvS = com_google_android_gms_ads_internal_formats_zza;
    }

    public List<String> getAvailableAssetNames() {
        int i = 0;
        String[] strArr = new String[(this.zzvX.size() + this.zzvY.size())];
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzvX.size(); i3++) {
            strArr[i2] = (String) this.zzvX.keyAt(i3);
            i2++;
        }
        while (i < this.zzvY.size()) {
            strArr[i2] = (String) this.zzvY.keyAt(i);
            i++;
            i2++;
        }
        return Arrays.asList(strArr);
    }

    public String getCustomTemplateId() {
        return this.zzvW;
    }

    public void performClick(String assetName) {
        synchronized (this.zzpc) {
            if (this.zzvT == null) {
                zzb.m1444e("Attempt to call performClick before ad initialized.");
                return;
            }
            this.zzvT.performClick(assetName);
        }
    }

    public void recordImpression() {
        synchronized (this.zzpc) {
            if (this.zzvT == null) {
                zzb.m1444e("Attempt to perform recordImpression before ad initialized.");
                return;
            }
            this.zzvT.recordImpression();
        }
    }

    public String zzS(String str) {
        return (String) this.zzvY.get(str);
    }

    public zzcj zzT(String str) {
        return (zzcj) this.zzvX.get(str);
    }

    public void zza(zzh com_google_android_gms_ads_internal_formats_zzh) {
        synchronized (this.zzpc) {
            this.zzvT = com_google_android_gms_ads_internal_formats_zzh;
        }
    }

    public String zzdu() {
        return "3";
    }

    public zza zzdv() {
        return this.zzvS;
    }
}
