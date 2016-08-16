package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzos extends zzod<zzos> {
    public String mCategory;
    public String zzaID;
    public String zzaIP;
    public long zzaIQ;

    public String getLabel() {
        return this.zzaID;
    }

    public long getTimeInMillis() {
        return this.zzaIQ;
    }

    public void setTimeInMillis(long milliseconds) {
        this.zzaIQ = milliseconds;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("variableName", this.zzaIP);
        hashMap.put("timeInMillis", Long.valueOf(this.zzaIQ));
        hashMap.put("category", this.mCategory);
        hashMap.put("label", this.zzaID);
        return zzod.zzA(hashMap);
    }

    public void zza(zzos com_google_android_gms_internal_zzos) {
        if (!TextUtils.isEmpty(this.zzaIP)) {
            com_google_android_gms_internal_zzos.zzdY(this.zzaIP);
        }
        if (this.zzaIQ != 0) {
            com_google_android_gms_internal_zzos.setTimeInMillis(this.zzaIQ);
        }
        if (!TextUtils.isEmpty(this.mCategory)) {
            com_google_android_gms_internal_zzos.zzdR(this.mCategory);
        }
        if (!TextUtils.isEmpty(this.zzaID)) {
            com_google_android_gms_internal_zzos.zzdT(this.zzaID);
        }
    }

    public void zzdR(String str) {
        this.mCategory = str;
    }

    public void zzdT(String str) {
        this.zzaID = str;
    }

    public void zzdY(String str) {
        this.zzaIP = str;
    }

    public String zzxQ() {
        return this.mCategory;
    }

    public String zzyb() {
        return this.zzaIP;
    }
}
