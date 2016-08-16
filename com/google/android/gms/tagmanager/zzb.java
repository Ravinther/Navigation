package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzb extends zzak {
    private static final String ID;
    private final zza zzaOI;

    static {
        ID = zzad.ADVERTISER_ID.toString();
    }

    public zzb(Context context) {
        this(zza.zzaL(context));
    }

    zzb(zza com_google_android_gms_tagmanager_zza) {
        super(ID, new String[0]);
        this.zzaOI = com_google_android_gms_tagmanager_zza;
    }

    public zza zzG(Map<String, zza> map) {
        String zzzt = this.zzaOI.zzzt();
        return zzzt == null ? zzdf.zzBg() : zzdf.zzK(zzzt);
    }

    public boolean zzzx() {
        return false;
    }
}
