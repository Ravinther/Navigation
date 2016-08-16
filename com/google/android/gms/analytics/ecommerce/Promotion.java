package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzod;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Promotion {
    Map<String, String> zzLj;

    public Promotion() {
        this.zzLj = new HashMap();
    }

    void put(String name, String value) {
        zzx.zzb((Object) name, (Object) "Name should be non-null");
        this.zzLj.put(name, value);
    }

    public Promotion setCreative(String value) {
        put("cr", value);
        return this;
    }

    public Promotion setId(String value) {
        put("id", value);
        return this;
    }

    public Promotion setName(String value) {
        put("nm", value);
        return this;
    }

    public Promotion setPosition(String value) {
        put("ps", value);
        return this;
    }

    public String toString() {
        return zzod.zzF(this.zzLj);
    }

    public Map<String, String> zzaV(String str) {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : this.zzLj.entrySet()) {
            hashMap.put(str + ((String) entry.getKey()), entry.getValue());
        }
        return hashMap;
    }
}
