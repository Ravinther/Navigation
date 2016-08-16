package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzok extends zzod<zzok> {
    private String zzNT;
    private String zzNU;
    private String zzaIs;
    private String zzaIt;

    public void setAppId(String value) {
        this.zzaIs = value;
    }

    public void setAppInstallerId(String value) {
        this.zzaIt = value;
    }

    public void setAppName(String value) {
        this.zzNT = value;
    }

    public void setAppVersion(String value) {
        this.zzNU = value;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("appName", this.zzNT);
        hashMap.put("appVersion", this.zzNU);
        hashMap.put("appId", this.zzaIs);
        hashMap.put("appInstallerId", this.zzaIt);
        return zzod.zzA(hashMap);
    }

    public void zza(zzok com_google_android_gms_internal_zzok) {
        if (!TextUtils.isEmpty(this.zzNT)) {
            com_google_android_gms_internal_zzok.setAppName(this.zzNT);
        }
        if (!TextUtils.isEmpty(this.zzNU)) {
            com_google_android_gms_internal_zzok.setAppVersion(this.zzNU);
        }
        if (!TextUtils.isEmpty(this.zzaIs)) {
            com_google_android_gms_internal_zzok.setAppId(this.zzaIs);
        }
        if (!TextUtils.isEmpty(this.zzaIt)) {
            com_google_android_gms_internal_zzok.setAppInstallerId(this.zzaIt);
        }
    }

    public String zzjZ() {
        return this.zzNT;
    }

    public String zzkb() {
        return this.zzNU;
    }

    public String zztW() {
        return this.zzaIs;
    }

    public String zzxA() {
        return this.zzaIt;
    }
}
