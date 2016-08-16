package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzn extends zzak {
    private static final String ID;
    private static final String VALUE;

    static {
        ID = zzad.CONSTANT.toString();
        VALUE = zzae.VALUE.toString();
    }

    public zzn() {
        super(ID, VALUE);
    }

    public zza zzG(Map<String, zza> map) {
        return (zza) map.get(VALUE);
    }

    public boolean zzzx() {
        return true;
    }
}
