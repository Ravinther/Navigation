package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzq extends zzak {
    private static final String ID;
    private final String zzWs;

    static {
        ID = zzad.CONTAINER_VERSION.toString();
    }

    public zzq(String str) {
        super(ID, new String[0]);
        this.zzWs = str;
    }

    public zza zzG(Map<String, zza> map) {
        return this.zzWs == null ? zzdf.zzBg() : zzdf.zzK(this.zzWs);
    }

    public boolean zzzx() {
        return true;
    }
}
