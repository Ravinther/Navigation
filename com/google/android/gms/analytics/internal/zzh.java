package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzh {
    private final long zzLS;
    private final String zzLT;
    private final boolean zzLU;
    private long zzLV;
    private final String zzLd;
    private final Map<String, String> zzvs;

    public zzh(long j, String str, String str2, boolean z, long j2, Map<String, String> map) {
        zzx.zzcs(str);
        zzx.zzcs(str2);
        this.zzLS = j;
        this.zzLd = str;
        this.zzLT = str2;
        this.zzLU = z;
        this.zzLV = j2;
        if (map != null) {
            this.zzvs = new HashMap(map);
        } else {
            this.zzvs = Collections.emptyMap();
        }
    }

    public String getClientId() {
        return this.zzLd;
    }

    public long zziw() {
        return this.zzLS;
    }

    public String zzix() {
        return this.zzLT;
    }

    public boolean zziy() {
        return this.zzLU;
    }

    public long zziz() {
        return this.zzLV;
    }

    public Map<String, String> zzn() {
        return this.zzvs;
    }

    public void zzn(long j) {
        this.zzLV = j;
    }
}
