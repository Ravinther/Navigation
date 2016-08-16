package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zze extends zzak {
    private static final String ID;
    private static final String zzaOJ;
    private static final String zzaOK;
    private final Context context;

    static {
        ID = zzad.ADWORDS_CLICK_REFERRER.toString();
        zzaOJ = zzae.COMPONENT.toString();
        zzaOK = zzae.CONVERSION_ID.toString();
    }

    public zze(Context context) {
        super(ID, zzaOK);
        this.context = context;
    }

    public zza zzG(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaOK);
        if (com_google_android_gms_internal_zzag_zza == null) {
            return zzdf.zzBg();
        }
        String zzg = zzdf.zzg(com_google_android_gms_internal_zzag_zza);
        com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaOJ);
        String zzg2 = zzax.zzg(this.context, zzg, com_google_android_gms_internal_zzag_zza != null ? zzdf.zzg(com_google_android_gms_internal_zzag_zza) : null);
        return zzg2 != null ? zzdf.zzK(zzg2) : zzdf.zzBg();
    }

    public boolean zzzx() {
        return true;
    }
}
