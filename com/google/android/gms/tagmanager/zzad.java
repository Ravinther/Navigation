package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzad extends zzak {
    private static final String ID;
    private static final String zzaPY;
    private static final String zzaPZ;
    private static final String zzaQa;
    private static final String zzaQb;

    static {
        ID = com.google.android.gms.internal.zzad.ENCODE.toString();
        zzaPY = zzae.ARG0.toString();
        zzaPZ = zzae.NO_PADDING.toString();
        zzaQa = zzae.INPUT_FORMAT.toString();
        zzaQb = zzae.OUTPUT_FORMAT.toString();
    }

    public zzad() {
        super(ID, zzaPY);
    }

    public zza zzG(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaPY);
        if (com_google_android_gms_internal_zzag_zza == null || com_google_android_gms_internal_zzag_zza == zzdf.zzBg()) {
            return zzdf.zzBg();
        }
        String zzg = zzdf.zzg(com_google_android_gms_internal_zzag_zza);
        com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaQa);
        String zzg2 = com_google_android_gms_internal_zzag_zza == null ? "text" : zzdf.zzg(com_google_android_gms_internal_zzag_zza);
        com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaQb);
        String zzg3 = com_google_android_gms_internal_zzag_zza == null ? "base16" : zzdf.zzg(com_google_android_gms_internal_zzag_zza);
        com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaPZ);
        int i = (com_google_android_gms_internal_zzag_zza == null || !zzdf.zzk(com_google_android_gms_internal_zzag_zza).booleanValue()) ? 2 : 3;
        try {
            byte[] bytes;
            Object zzi;
            if ("text".equals(zzg2)) {
                bytes = zzg.getBytes();
            } else if ("base16".equals(zzg2)) {
                bytes = zzk.zzet(zzg);
            } else if ("base64".equals(zzg2)) {
                bytes = Base64.decode(zzg, i);
            } else if ("base64url".equals(zzg2)) {
                bytes = Base64.decode(zzg, i | 8);
            } else {
                zzbg.m1447e("Encode: unknown input format: " + zzg2);
                return zzdf.zzBg();
            }
            if ("base16".equals(zzg3)) {
                zzi = zzk.zzi(bytes);
            } else if ("base64".equals(zzg3)) {
                zzi = Base64.encodeToString(bytes, i);
            } else if ("base64url".equals(zzg3)) {
                zzi = Base64.encodeToString(bytes, i | 8);
            } else {
                zzbg.m1447e("Encode: unknown output format: " + zzg3);
                return zzdf.zzBg();
            }
            return zzdf.zzK(zzi);
        } catch (IllegalArgumentException e) {
            zzbg.m1447e("Encode: invalid input:");
            return zzdf.zzBg();
        }
    }

    public boolean zzzx() {
        return true;
    }
}
