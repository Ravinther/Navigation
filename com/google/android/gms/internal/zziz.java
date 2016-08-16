package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zziz extends zzod<zziz> {
    private Map<Integer, Double> zzLb;

    public zziz() {
        this.zzLb = new HashMap(4);
    }

    public String toString() {
        Map hashMap = new HashMap();
        for (Entry entry : this.zzLb.entrySet()) {
            hashMap.put("metric" + entry.getKey(), entry.getValue());
        }
        return zzod.zzA(hashMap);
    }

    public void zza(zziz com_google_android_gms_internal_zziz) {
        com_google_android_gms_internal_zziz.zzLb.putAll(this.zzLb);
    }

    public Map<Integer, Double> zzhI() {
        return Collections.unmodifiableMap(this.zzLb);
    }
}
