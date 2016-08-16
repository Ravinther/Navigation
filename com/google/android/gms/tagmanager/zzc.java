package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzc extends zzak {
    private static final String ID;
    private final zza zzaOI;

    static {
        ID = zzad.ADVERTISING_TRACKING_ENABLED.toString();
    }

    public zzc(Context context) {
        this(zza.zzaL(context));
    }

    zzc(zza com_google_android_gms_tagmanager_zza) {
        super(ID, new String[0]);
        this.zzaOI = com_google_android_gms_tagmanager_zza;
    }

    public zza zzG(Map<String, zza> map) {
        return zzdf.zzK(Boolean.valueOf(!this.zzaOI.isLimitAdTrackingEnabled()));
    }

    public boolean zzzx() {
        return false;
    }
}
