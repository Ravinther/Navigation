package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;
import java.util.Set;

public abstract class zzca extends zzak {
    private static final String zzaPY;
    private static final String zzaQW;

    static {
        zzaPY = zzae.ARG0.toString();
        zzaQW = zzae.ARG1.toString();
    }

    public zzca(String str) {
        super(str, zzaPY, zzaQW);
    }

    public /* bridge */ /* synthetic */ String zzAc() {
        return super.zzAc();
    }

    public /* bridge */ /* synthetic */ Set zzAd() {
        return super.zzAd();
    }

    public zza zzG(Map<String, zza> map) {
        for (zza com_google_android_gms_internal_zzag_zza : map.values()) {
            if (com_google_android_gms_internal_zzag_zza == zzdf.zzBg()) {
                return zzdf.zzK(Boolean.valueOf(false));
            }
        }
        zza com_google_android_gms_internal_zzag_zza2 = (zza) map.get(zzaPY);
        zza com_google_android_gms_internal_zzag_zza3 = (zza) map.get(zzaQW);
        boolean zza = (com_google_android_gms_internal_zzag_zza2 == null || com_google_android_gms_internal_zzag_zza3 == null) ? false : zza(com_google_android_gms_internal_zzag_zza2, com_google_android_gms_internal_zzag_zza3, map);
        return zzdf.zzK(Boolean.valueOf(zza));
    }

    protected abstract boolean zza(zza com_google_android_gms_internal_zzag_zza, zza com_google_android_gms_internal_zzag_zza2, Map<String, zza> map);

    public boolean zzzx() {
        return true;
    }
}
