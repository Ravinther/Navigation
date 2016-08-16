package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzai extends zzak {
    private static final String ID;
    private final zzcp zzaOU;

    static {
        ID = zzad.EVENT.toString();
    }

    public zzai(zzcp com_google_android_gms_tagmanager_zzcp) {
        super(ID, new String[0]);
        this.zzaOU = com_google_android_gms_tagmanager_zzcp;
    }

    public zza zzG(Map<String, zza> map) {
        String zzAF = this.zzaOU.zzAF();
        return zzAF == null ? zzdf.zzBg() : zzdf.zzK(zzAF);
    }

    public boolean zzzx() {
        return false;
    }
}
