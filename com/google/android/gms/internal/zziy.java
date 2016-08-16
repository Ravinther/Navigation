package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zziy extends zzod<zziy> {
    private Map<Integer, String> zzLa;

    public zziy() {
        this.zzLa = new HashMap(4);
    }

    public String toString() {
        Map hashMap = new HashMap();
        for (Entry entry : this.zzLa.entrySet()) {
            hashMap.put("dimension" + entry.getKey(), entry.getValue());
        }
        return zzod.zzA(hashMap);
    }

    public void zza(zziy com_google_android_gms_internal_zziy) {
        com_google_android_gms_internal_zziy.zzLa.putAll(this.zzLa);
    }

    public Map<Integer, String> zzhH() {
        return Collections.unmodifiableMap(this.zzLa);
    }
}
