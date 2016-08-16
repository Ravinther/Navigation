package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class zzap extends zzak {
    private static final String ID;
    private static final String zzaPY;
    private static final String zzaQa;
    private static final String zzaQe;

    static {
        ID = zzad.HASH.toString();
        zzaPY = zzae.ARG0.toString();
        zzaQe = zzae.ALGORITHM.toString();
        zzaQa = zzae.INPUT_FORMAT.toString();
    }

    public zzap() {
        super(ID, zzaPY);
    }

    private byte[] zzd(String str, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(str);
        instance.update(bArr);
        return instance.digest();
    }

    public zza zzG(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaPY);
        if (com_google_android_gms_internal_zzag_zza == null || com_google_android_gms_internal_zzag_zza == zzdf.zzBg()) {
            return zzdf.zzBg();
        }
        byte[] bytes;
        String zzg = zzdf.zzg(com_google_android_gms_internal_zzag_zza);
        com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaQe);
        String zzg2 = com_google_android_gms_internal_zzag_zza == null ? "MD5" : zzdf.zzg(com_google_android_gms_internal_zzag_zza);
        com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaQa);
        String zzg3 = com_google_android_gms_internal_zzag_zza == null ? "text" : zzdf.zzg(com_google_android_gms_internal_zzag_zza);
        if ("text".equals(zzg3)) {
            bytes = zzg.getBytes();
        } else if ("base16".equals(zzg3)) {
            bytes = zzk.zzet(zzg);
        } else {
            zzbg.m1447e("Hash: unknown input format: " + zzg3);
            return zzdf.zzBg();
        }
        try {
            return zzdf.zzK(zzk.zzi(zzd(zzg2, bytes)));
        } catch (NoSuchAlgorithmException e) {
            zzbg.m1447e("Hash: unknown algorithm: " + zzg2);
            return zzdf.zzBg();
        }
    }

    public boolean zzzx() {
        return true;
    }
}
