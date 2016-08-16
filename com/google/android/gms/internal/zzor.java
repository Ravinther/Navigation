package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzor extends zzod<zzor> {
    public String zzOj;
    public String zzaIN;
    public String zzaIO;

    public String getAction() {
        return this.zzOj;
    }

    public String getTarget() {
        return this.zzaIO;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("network", this.zzaIN);
        hashMap.put("action", this.zzOj);
        hashMap.put("target", this.zzaIO);
        return zzod.zzA(hashMap);
    }

    public void zza(zzor com_google_android_gms_internal_zzor) {
        if (!TextUtils.isEmpty(this.zzaIN)) {
            com_google_android_gms_internal_zzor.zzdW(this.zzaIN);
        }
        if (!TextUtils.isEmpty(this.zzOj)) {
            com_google_android_gms_internal_zzor.zzdS(this.zzOj);
        }
        if (!TextUtils.isEmpty(this.zzaIO)) {
            com_google_android_gms_internal_zzor.zzdX(this.zzaIO);
        }
    }

    public void zzdS(String str) {
        this.zzOj = str;
    }

    public void zzdW(String str) {
        this.zzaIN = str;
    }

    public void zzdX(String str) {
        this.zzaIO = str;
    }

    public String zzya() {
        return this.zzaIN;
    }
}
