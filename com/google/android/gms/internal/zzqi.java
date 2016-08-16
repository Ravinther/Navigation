package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzx;

public class zzqi {
    private final String zzaOS;
    private final String zzaRq;
    private final Integer zzaTv;
    private final String zzaTw;
    private final boolean zzaTx;

    public zzqi(String str, Integer num, String str2, boolean z) {
        this(str, num, str2, z, "");
    }

    public zzqi(String str, Integer num, String str2, boolean z, String str3) {
        zzx.zzv(str);
        zzx.zzv(str3);
        this.zzaOS = str;
        this.zzaTv = num;
        this.zzaTw = str2;
        this.zzaTx = z;
        this.zzaRq = str3;
    }

    public String getContainerId() {
        return this.zzaOS;
    }

    public Integer zzBp() {
        return this.zzaTv;
    }

    public String zzBq() {
        return this.zzaTw;
    }

    public String zzBr() {
        return this.zzaTw != null ? this.zzaTw + "_" + this.zzaOS : this.zzaOS;
    }

    public boolean zzBs() {
        return this.zzaTx;
    }

    public String zzBt() {
        return this.zzaRq;
    }
}
