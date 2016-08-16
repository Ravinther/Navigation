package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import java.util.HashMap;
import java.util.Map;

public final class zzjb extends zzod<zzjb> {
    private String zzGh;
    private String zzLc;
    private String zzLd;
    private String zzLe;
    private boolean zzLf;
    private String zzLg;
    private boolean zzLh;
    private double zzLi;

    public String getClientId() {
        return this.zzLd;
    }

    public String getUserId() {
        return this.zzGh;
    }

    public void setClientId(String clientId) {
        this.zzLd = clientId;
    }

    public void setSampleRate(double percentage) {
        boolean z = percentage >= 0.0d && percentage <= 100.0d;
        zzx.zzb(z, (Object) "Sample rate must be between 0% and 100%");
        this.zzLi = percentage;
    }

    public void setUserId(String userId) {
        this.zzGh = userId;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("hitType", this.zzLc);
        hashMap.put("clientId", this.zzLd);
        hashMap.put("userId", this.zzGh);
        hashMap.put("androidAdId", this.zzLe);
        hashMap.put("AdTargetingEnabled", Boolean.valueOf(this.zzLf));
        hashMap.put("sessionControl", this.zzLg);
        hashMap.put("nonInteraction", Boolean.valueOf(this.zzLh));
        hashMap.put("sampleRate", Double.valueOf(this.zzLi));
        return zzod.zzA(hashMap);
    }

    public void zzG(boolean z) {
        this.zzLf = z;
    }

    public void zzH(boolean z) {
        this.zzLh = z;
    }

    public void zza(zzjb com_google_android_gms_internal_zzjb) {
        if (!TextUtils.isEmpty(this.zzLc)) {
            com_google_android_gms_internal_zzjb.zzaS(this.zzLc);
        }
        if (!TextUtils.isEmpty(this.zzLd)) {
            com_google_android_gms_internal_zzjb.setClientId(this.zzLd);
        }
        if (!TextUtils.isEmpty(this.zzGh)) {
            com_google_android_gms_internal_zzjb.setUserId(this.zzGh);
        }
        if (!TextUtils.isEmpty(this.zzLe)) {
            com_google_android_gms_internal_zzjb.zzaT(this.zzLe);
        }
        if (this.zzLf) {
            com_google_android_gms_internal_zzjb.zzG(true);
        }
        if (!TextUtils.isEmpty(this.zzLg)) {
            com_google_android_gms_internal_zzjb.zzaU(this.zzLg);
        }
        if (this.zzLh) {
            com_google_android_gms_internal_zzjb.zzH(this.zzLh);
        }
        if (this.zzLi != 0.0d) {
            com_google_android_gms_internal_zzjb.setSampleRate(this.zzLi);
        }
    }

    public void zzaS(String str) {
        this.zzLc = str;
    }

    public void zzaT(String str) {
        this.zzLe = str;
    }

    public void zzaU(String str) {
        this.zzLg = str;
    }

    public String zzhK() {
        return this.zzLc;
    }

    public String zzhL() {
        return this.zzLe;
    }

    public boolean zzhM() {
        return this.zzLf;
    }

    public String zzhN() {
        return this.zzLg;
    }

    public boolean zzhO() {
        return this.zzLh;
    }

    public double zzhP() {
        return this.zzLi;
    }
}
