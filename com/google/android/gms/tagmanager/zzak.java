package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag.zza;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzak {
    private final Set<String> zzaQc;
    private final String zzaQd;

    public zzak(String str, String... strArr) {
        this.zzaQd = str;
        this.zzaQc = new HashSet(strArr.length);
        for (Object add : strArr) {
            this.zzaQc.add(add);
        }
    }

    public String zzAc() {
        return this.zzaQd;
    }

    public Set<String> zzAd() {
        return this.zzaQc;
    }

    public abstract zza zzG(Map<String, zza> map);

    boolean zzf(Set<String> set) {
        return set.containsAll(this.zzaQc);
    }

    public abstract boolean zzzx();
}
