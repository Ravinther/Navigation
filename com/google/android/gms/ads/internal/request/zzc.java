package com.google.android.gms.ads.internal.request;

import android.content.Context;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhq;

@zzgk
public final class zzc {

    public interface zza {
        void zzb(AdResponseParcel adResponseParcel);
    }

    interface zzb {
        boolean zzd(AdRequestInfoParcel adRequestInfoParcel);
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzc.1 */
    static class C05901 implements zzb {
        final /* synthetic */ Context zzrn;

        C05901(Context context) {
            this.zzrn = context;
        }

        public boolean zzd(AdRequestInfoParcel adRequestInfoParcel) {
            return adRequestInfoParcel.zzqb.zzIC || (GooglePlayServicesUtil.zzag(this.zzrn) && !((Boolean) zzby.zzuw.get()).booleanValue());
        }
    }

    public static zzhq zza(Context context, AdRequestInfoParcel adRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza) {
        return zza(context, adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza, new C05901(context));
    }

    static zzhq zza(Context context, AdRequestInfoParcel adRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza, zzb com_google_android_gms_ads_internal_request_zzc_zzb) {
        return com_google_android_gms_ads_internal_request_zzc_zzb.zzd(adRequestInfoParcel) ? zzb(context, adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza) : zzc(context, adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
    }

    private static zzhq zzb(Context context, AdRequestInfoParcel adRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaC("Fetching ad response from local ad request service.");
        zzhq com_google_android_gms_ads_internal_request_zzd_zza = new com.google.android.gms.ads.internal.request.zzd.zza(context, adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
        com_google_android_gms_ads_internal_request_zzd_zza.zzgn();
        return com_google_android_gms_ads_internal_request_zzd_zza;
    }

    private static zzhq zzc(Context context, AdRequestInfoParcel adRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaC("Fetching ad response from remote ad request service.");
        if (zzk.zzcE().zzR(context)) {
            return new com.google.android.gms.ads.internal.request.zzd.zzb(context, adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaE("Failed to connect to remote ad request service.");
        return null;
    }
}
