package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzbz extends zzak {
    private static final String ID;
    private static final zza zzaQV;

    static {
        ID = zzad.PLATFORM.toString();
        zzaQV = zzdf.zzK("Android");
    }

    public zzbz() {
        super(ID, new String[0]);
    }

    public zza zzG(Map<String, zza> map) {
        return zzaQV;
    }

    public boolean zzzx() {
        return true;
    }
}
