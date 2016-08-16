package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.List;
import java.util.Map;

class zzx extends zzdd {
    private static final String ID;
    private static final String VALUE;
    private static final String zzaPT;
    private final DataLayer zzaOT;

    static {
        ID = zzad.DATA_LAYER_WRITE.toString();
        VALUE = zzae.VALUE.toString();
        zzaPT = zzae.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    }

    public zzx(DataLayer dataLayer) {
        super(ID, VALUE);
        this.zzaOT = dataLayer;
    }

    private void zza(zza com_google_android_gms_internal_zzag_zza) {
        if (com_google_android_gms_internal_zzag_zza != null && com_google_android_gms_internal_zzag_zza != zzdf.zzBa()) {
            String zzg = zzdf.zzg(com_google_android_gms_internal_zzag_zza);
            if (zzg != zzdf.zzBf()) {
                this.zzaOT.zzeC(zzg);
            }
        }
    }

    private void zzb(zza com_google_android_gms_internal_zzag_zza) {
        if (com_google_android_gms_internal_zzag_zza != null && com_google_android_gms_internal_zzag_zza != zzdf.zzBa()) {
            Object zzl = zzdf.zzl(com_google_android_gms_internal_zzag_zza);
            if (zzl instanceof List) {
                for (Object zzl2 : (List) zzl2) {
                    if (zzl2 instanceof Map) {
                        this.zzaOT.push((Map) zzl2);
                    }
                }
            }
        }
    }

    public void zzI(Map<String, zza> map) {
        zzb((zza) map.get(VALUE));
        zza((zza) map.get(zzaPT));
    }
}
