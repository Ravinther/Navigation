package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzn;

@zzgk
public class zzga {

    public interface zza {
        void zzb(zzhj com_google_android_gms_internal_zzhj);
    }

    public zzhq zza(Context context, com.google.android.gms.ads.internal.zza com_google_android_gms_ads_internal_zza, com.google.android.gms.internal.zzhj.zza com_google_android_gms_internal_zzhj_zza, zzan com_google_android_gms_internal_zzan, zzip com_google_android_gms_internal_zzip, zzeh com_google_android_gms_internal_zzeh, zza com_google_android_gms_internal_zzga_zza, zzcd com_google_android_gms_internal_zzcd) {
        zzhq com_google_android_gms_internal_zzgd;
        AdResponseParcel adResponseParcel = com_google_android_gms_internal_zzhj_zza.zzGM;
        if (adResponseParcel.zzDX) {
            com_google_android_gms_internal_zzgd = new zzgd(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzeh, com_google_android_gms_internal_zzga_zza, com_google_android_gms_internal_zzcd);
        } else if (!adResponseParcel.zzsJ) {
            com_google_android_gms_internal_zzgd = adResponseParcel.zzEd ? new zzfy(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzga_zza) : (((Boolean) zzby.zzuM.get()).booleanValue() && zzlv.zzpV() && !zzlv.isAtLeastL() && com_google_android_gms_internal_zzip.zzaN().zzsH) ? new zzgc(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzga_zza) : new zzgb(context, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzga_zza);
        } else if (com_google_android_gms_ads_internal_zza instanceof zzn) {
            com_google_android_gms_internal_zzgd = new zzge(context, (zzn) com_google_android_gms_ads_internal_zza, new zzbc(), com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzan, com_google_android_gms_internal_zzga_zza);
        } else {
            throw new IllegalArgumentException("Invalid NativeAdManager type. Found: " + (com_google_android_gms_ads_internal_zza != null ? com_google_android_gms_ads_internal_zza.getClass().getName() : "null") + "; Required: NativeAdManager.");
        }
        zzb.zzaC("AdRenderer: " + com_google_android_gms_internal_zzgd.getClass().getName());
        com_google_android_gms_internal_zzgd.zzgo();
        return com_google_android_gms_internal_zzgd;
    }
}
