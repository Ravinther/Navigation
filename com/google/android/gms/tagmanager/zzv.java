package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzv extends zzak {
    private static final String ID;
    private static final String NAME;
    private static final String zzaPI;
    private final DataLayer zzaOT;

    static {
        ID = zzad.CUSTOM_VAR.toString();
        NAME = zzae.NAME.toString();
        zzaPI = zzae.DEFAULT_VALUE.toString();
    }

    public zzv(DataLayer dataLayer) {
        super(ID, NAME);
        this.zzaOT = dataLayer;
    }

    public zza zzG(Map<String, zza> map) {
        Object obj = this.zzaOT.get(zzdf.zzg((zza) map.get(NAME)));
        if (obj != null) {
            return zzdf.zzK(obj);
        }
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaPI);
        return com_google_android_gms_internal_zzag_zza != null ? com_google_android_gms_internal_zzag_zza : zzdf.zzBg();
    }

    public boolean zzzx() {
        return false;
    }
}
