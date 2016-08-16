package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag.zza;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import loquendo.tts.engine.TTSConst;

class zzdj {
    private static zzbw<zza> zza(zzbw<zza> com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza) {
        try {
            return new zzbw(zzdf.zzK(zzff(zzdf.zzg((zza) com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza.getObject()))), com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza.zzAq());
        } catch (Throwable e) {
            zzbg.zzb("Escape URI: unsupported encoding", e);
            return com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza;
        }
    }

    private static zzbw<zza> zza(zzbw<zza> com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza, int i) {
        if (zzn((zza) com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza.getObject())) {
            switch (i) {
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    return zza(com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza);
                default:
                    zzbg.m1447e("Unsupported Value Escaping: " + i);
                    return com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza;
            }
        }
        zzbg.m1447e("Escaping can only be applied to strings.");
        return com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza;
    }

    static zzbw<zza> zza(zzbw<zza> com_google_android_gms_tagmanager_zzbw_com_google_android_gms_internal_zzag_zza, int... iArr) {
        zzbw zza;
        for (int zza2 : iArr) {
            zza = zza(zza, zza2);
        }
        return zza;
    }

    static String zzff(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }

    private static boolean zzn(zza com_google_android_gms_internal_zzag_zza) {
        return zzdf.zzl(com_google_android_gms_internal_zzag_zza) instanceof String;
    }
}
