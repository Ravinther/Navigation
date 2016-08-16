package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzop extends zzod<zzop> {
    public boolean zzaIE;
    public String zzaoB;

    public String getDescription() {
        return this.zzaoB;
    }

    public void setDescription(String description) {
        this.zzaoB = description;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("description", this.zzaoB);
        hashMap.put("fatal", Boolean.valueOf(this.zzaIE));
        return zzod.zzA(hashMap);
    }

    public void zza(zzop com_google_android_gms_internal_zzop) {
        if (!TextUtils.isEmpty(this.zzaoB)) {
            com_google_android_gms_internal_zzop.setDescription(this.zzaoB);
        }
        if (this.zzaIE) {
            com_google_android_gms_internal_zzop.zzak(this.zzaIE);
        }
    }

    public void zzak(boolean z) {
        this.zzaIE = z;
    }

    public boolean zzxR() {
        return this.zzaIE;
    }
}
