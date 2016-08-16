package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class zzt extends zzak {
    private static final String ID;
    private static final String zzaOL;
    private static final String zzaPx;
    private final zza zzaPy;

    public interface zza {
        Object zzd(String str, Map<String, Object> map);
    }

    static {
        ID = zzad.FUNCTION_CALL.toString();
        zzaPx = zzae.FUNCTION_CALL_NAME.toString();
        zzaOL = zzae.ADDITIONAL_PARAMS.toString();
    }

    public zzt(zza com_google_android_gms_tagmanager_zzt_zza) {
        super(ID, zzaPx);
        this.zzaPy = com_google_android_gms_tagmanager_zzt_zza;
    }

    public com.google.android.gms.internal.zzag.zza zzG(Map<String, com.google.android.gms.internal.zzag.zza> map) {
        String zzg = zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(zzaPx));
        Map hashMap = new HashMap();
        com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza = (com.google.android.gms.internal.zzag.zza) map.get(zzaOL);
        if (com_google_android_gms_internal_zzag_zza != null) {
            Object zzl = zzdf.zzl(com_google_android_gms_internal_zzag_zza);
            if (zzl instanceof Map) {
                for (Entry entry : ((Map) zzl).entrySet()) {
                    hashMap.put(entry.getKey().toString(), entry.getValue());
                }
            } else {
                zzbg.zzaE("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return zzdf.zzBg();
            }
        }
        try {
            return zzdf.zzK(this.zzaPy.zzd(zzg, hashMap));
        } catch (Exception e) {
            zzbg.zzaE("Custom macro/tag " + zzg + " threw exception " + e.getMessage());
            return zzdf.zzBg();
        }
    }

    public boolean zzzx() {
        return false;
    }
}
