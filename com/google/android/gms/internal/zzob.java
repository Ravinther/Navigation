package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzob {
    private final zzoe zzaHQ;
    private boolean zzaHR;
    private long zzaHS;
    private long zzaHT;
    private long zzaHU;
    private long zzaHV;
    private long zzaHW;
    private boolean zzaHX;
    private final Map<Class<? extends zzod>, zzod> zzaHY;
    private final List<zzoh> zzaHZ;
    private final zzlm zzpO;

    zzob(zzob com_google_android_gms_internal_zzob) {
        this.zzaHQ = com_google_android_gms_internal_zzob.zzaHQ;
        this.zzpO = com_google_android_gms_internal_zzob.zzpO;
        this.zzaHS = com_google_android_gms_internal_zzob.zzaHS;
        this.zzaHT = com_google_android_gms_internal_zzob.zzaHT;
        this.zzaHU = com_google_android_gms_internal_zzob.zzaHU;
        this.zzaHV = com_google_android_gms_internal_zzob.zzaHV;
        this.zzaHW = com_google_android_gms_internal_zzob.zzaHW;
        this.zzaHZ = new ArrayList(com_google_android_gms_internal_zzob.zzaHZ);
        this.zzaHY = new HashMap(com_google_android_gms_internal_zzob.zzaHY.size());
        for (Entry entry : com_google_android_gms_internal_zzob.zzaHY.entrySet()) {
            zzod zzf = zzf((Class) entry.getKey());
            ((zzod) entry.getValue()).zza(zzf);
            this.zzaHY.put(entry.getKey(), zzf);
        }
    }

    zzob(zzoe com_google_android_gms_internal_zzoe, zzlm com_google_android_gms_internal_zzlm) {
        zzx.zzv(com_google_android_gms_internal_zzoe);
        zzx.zzv(com_google_android_gms_internal_zzlm);
        this.zzaHQ = com_google_android_gms_internal_zzoe;
        this.zzpO = com_google_android_gms_internal_zzlm;
        this.zzaHV = 1800000;
        this.zzaHW = 3024000000L;
        this.zzaHY = new HashMap();
        this.zzaHZ = new ArrayList();
    }

    private static <T extends zzod> T zzf(Class<T> cls) {
        try {
            return (zzod) cls.newInstance();
        } catch (Throwable e) {
            throw new IllegalArgumentException("dataType doesn't have default constructor", e);
        } catch (Throwable e2) {
            throw new IllegalArgumentException("dataType default constructor is not accessible", e2);
        }
    }

    public void zzL(long j) {
        this.zzaHT = j;
    }

    public void zzb(zzod com_google_android_gms_internal_zzod) {
        zzx.zzv(com_google_android_gms_internal_zzod);
        Class cls = com_google_android_gms_internal_zzod.getClass();
        if (cls.getSuperclass() != zzod.class) {
            throw new IllegalArgumentException();
        }
        com_google_android_gms_internal_zzod.zza(zze(cls));
    }

    public <T extends zzod> T zzd(Class<T> cls) {
        return (zzod) this.zzaHY.get(cls);
    }

    public <T extends zzod> T zze(Class<T> cls) {
        zzod com_google_android_gms_internal_zzod = (zzod) this.zzaHY.get(cls);
        if (com_google_android_gms_internal_zzod != null) {
            return com_google_android_gms_internal_zzod;
        }
        T zzf = zzf(cls);
        this.zzaHY.put(cls, zzf);
        return zzf;
    }

    public zzob zzxh() {
        return new zzob(this);
    }

    public Collection<zzod> zzxi() {
        return this.zzaHY.values();
    }

    public List<zzoh> zzxj() {
        return this.zzaHZ;
    }

    public long zzxk() {
        return this.zzaHS;
    }

    public void zzxl() {
        zzxp().zze(this);
    }

    public boolean zzxm() {
        return this.zzaHR;
    }

    void zzxn() {
        this.zzaHU = this.zzpO.elapsedRealtime();
        if (this.zzaHT != 0) {
            this.zzaHS = this.zzaHT;
        } else {
            this.zzaHS = this.zzpO.currentTimeMillis();
        }
        this.zzaHR = true;
    }

    zzoe zzxo() {
        return this.zzaHQ;
    }

    zzof zzxp() {
        return this.zzaHQ.zzxp();
    }

    boolean zzxq() {
        return this.zzaHX;
    }

    void zzxr() {
        this.zzaHX = true;
    }
}
