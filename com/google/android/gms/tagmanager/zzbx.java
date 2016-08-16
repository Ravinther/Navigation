package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzbx extends zzak {
    private static final String ID;

    static {
        ID = zzad.OS_VERSION.toString();
    }

    public zzbx() {
        super(ID, new String[0]);
    }

    public zza zzG(Map<String, zza> map) {
        return zzdf.zzK(VERSION.RELEASE);
    }

    public boolean zzzx() {
        return true;
    }
}
