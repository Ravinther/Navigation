package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzol extends zzod<zzol> {
    private String mName;
    private String zzaDI;
    private String zzaIu;
    private String zzaIv;
    private String zzaIw;
    private String zzaIx;
    private String zzaIy;
    private String zzaIz;
    private String zzvx;
    private String zzwj;

    public String getContent() {
        return this.zzvx;
    }

    public String getId() {
        return this.zzwj;
    }

    public String getName() {
        return this.mName;
    }

    public String getSource() {
        return this.zzaDI;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("name", this.mName);
        hashMap.put("source", this.zzaDI);
        hashMap.put("medium", this.zzaIu);
        hashMap.put("keyword", this.zzaIv);
        hashMap.put("content", this.zzvx);
        hashMap.put("id", this.zzwj);
        hashMap.put("adNetworkId", this.zzaIw);
        hashMap.put("gclid", this.zzaIx);
        hashMap.put("dclid", this.zzaIy);
        hashMap.put("aclid", this.zzaIz);
        return zzod.zzA(hashMap);
    }

    public void zza(zzol com_google_android_gms_internal_zzol) {
        if (!TextUtils.isEmpty(this.mName)) {
            com_google_android_gms_internal_zzol.setName(this.mName);
        }
        if (!TextUtils.isEmpty(this.zzaDI)) {
            com_google_android_gms_internal_zzol.zzdI(this.zzaDI);
        }
        if (!TextUtils.isEmpty(this.zzaIu)) {
            com_google_android_gms_internal_zzol.zzdJ(this.zzaIu);
        }
        if (!TextUtils.isEmpty(this.zzaIv)) {
            com_google_android_gms_internal_zzol.zzdK(this.zzaIv);
        }
        if (!TextUtils.isEmpty(this.zzvx)) {
            com_google_android_gms_internal_zzol.zzdL(this.zzvx);
        }
        if (!TextUtils.isEmpty(this.zzwj)) {
            com_google_android_gms_internal_zzol.zzdM(this.zzwj);
        }
        if (!TextUtils.isEmpty(this.zzaIw)) {
            com_google_android_gms_internal_zzol.zzdN(this.zzaIw);
        }
        if (!TextUtils.isEmpty(this.zzaIx)) {
            com_google_android_gms_internal_zzol.zzdO(this.zzaIx);
        }
        if (!TextUtils.isEmpty(this.zzaIy)) {
            com_google_android_gms_internal_zzol.zzdP(this.zzaIy);
        }
        if (!TextUtils.isEmpty(this.zzaIz)) {
            com_google_android_gms_internal_zzol.zzdQ(this.zzaIz);
        }
    }

    public void zzdI(String str) {
        this.zzaDI = str;
    }

    public void zzdJ(String str) {
        this.zzaIu = str;
    }

    public void zzdK(String str) {
        this.zzaIv = str;
    }

    public void zzdL(String str) {
        this.zzvx = str;
    }

    public void zzdM(String str) {
        this.zzwj = str;
    }

    public void zzdN(String str) {
        this.zzaIw = str;
    }

    public void zzdO(String str) {
        this.zzaIx = str;
    }

    public void zzdP(String str) {
        this.zzaIy = str;
    }

    public void zzdQ(String str) {
        this.zzaIz = str;
    }

    public String zzxB() {
        return this.zzaIu;
    }

    public String zzxC() {
        return this.zzaIv;
    }

    public String zzxD() {
        return this.zzaIw;
    }

    public String zzxE() {
        return this.zzaIx;
    }

    public String zzxF() {
        return this.zzaIy;
    }

    public String zzxG() {
        return this.zzaIz;
    }
}
