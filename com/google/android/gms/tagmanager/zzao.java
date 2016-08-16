package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzao extends zzak {
    private static final String ID;

    static {
        ID = zzad.GTM_VERSION.toString();
    }

    public zzao() {
        super(ID, new String[0]);
    }

    public zza zzG(Map<String, zza> map) {
        return zzdf.zzK("4.00");
    }

    public boolean zzzx() {
        return true;
    }
}
