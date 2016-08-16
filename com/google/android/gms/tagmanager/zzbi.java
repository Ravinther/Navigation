package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzbi extends zzak {
    private static final String ID;
    private static final String zzaPY;

    static {
        ID = zzad.LOWERCASE_STRING.toString();
        zzaPY = zzae.ARG0.toString();
    }

    public zzbi() {
        super(ID, zzaPY);
    }

    public zza zzG(Map<String, zza> map) {
        return zzdf.zzK(zzdf.zzg((zza) map.get(zzaPY)).toLowerCase());
    }

    public boolean zzzx() {
        return true;
    }
}
