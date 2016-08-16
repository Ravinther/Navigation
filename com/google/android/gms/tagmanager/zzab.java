package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzab extends zzak {
    private static final String ID;

    static {
        ID = zzad.DEVICE_NAME.toString();
    }

    public zzab() {
        super(ID, new String[0]);
    }

    public zza zzG(Map<String, zza> map) {
        String str = Build.MANUFACTURER;
        Object obj = Build.MODEL;
        if (!(obj.startsWith(str) || str.equals("unknown"))) {
            obj = str + " " + obj;
        }
        return zzdf.zzK(obj);
    }

    public boolean zzzx() {
        return true;
    }
}
