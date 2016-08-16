package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzja extends zzod<zzja> {
    private final Map<String, Object> zzvs;

    public zzja() {
        this.zzvs = new HashMap();
    }

    private String zzaR(String str) {
        zzx.zzcs(str);
        if (str != null && str.startsWith("&")) {
            str = str.substring(1);
        }
        zzx.zzh(str, "Name can not be empty or \"&\"");
        return str;
    }

    public void set(String name, String value) {
        this.zzvs.put(zzaR(name), value);
    }

    public String toString() {
        return zzod.zzA(this.zzvs);
    }

    public void zza(zzja com_google_android_gms_internal_zzja) {
        zzx.zzv(com_google_android_gms_internal_zzja);
        com_google_android_gms_internal_zzja.zzvs.putAll(this.zzvs);
    }

    public Map<String, Object> zzhJ() {
        return Collections.unmodifiableMap(this.zzvs);
    }
}
