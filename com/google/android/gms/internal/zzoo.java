package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzoo extends zzod<zzoo> {
    private String mCategory;
    private String zzOj;
    private String zzaID;
    private long zzasE;

    public String getAction() {
        return this.zzOj;
    }

    public String getLabel() {
        return this.zzaID;
    }

    public long getValue() {
        return this.zzasE;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("category", this.mCategory);
        hashMap.put("action", this.zzOj);
        hashMap.put("label", this.zzaID);
        hashMap.put("value", Long.valueOf(this.zzasE));
        return zzod.zzA(hashMap);
    }

    public void zzM(long j) {
        this.zzasE = j;
    }

    public void zza(zzoo com_google_android_gms_internal_zzoo) {
        if (!TextUtils.isEmpty(this.mCategory)) {
            com_google_android_gms_internal_zzoo.zzdR(this.mCategory);
        }
        if (!TextUtils.isEmpty(this.zzOj)) {
            com_google_android_gms_internal_zzoo.zzdS(this.zzOj);
        }
        if (!TextUtils.isEmpty(this.zzaID)) {
            com_google_android_gms_internal_zzoo.zzdT(this.zzaID);
        }
        if (this.zzasE != 0) {
            com_google_android_gms_internal_zzoo.zzM(this.zzasE);
        }
    }

    public void zzdR(String str) {
        this.mCategory = str;
    }

    public void zzdS(String str) {
        this.zzOj = str;
    }

    public void zzdT(String str) {
        this.zzaID = str;
    }

    public String zzxQ() {
        return this.mCategory;
    }
}
