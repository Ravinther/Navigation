package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzom extends zzod<zzom> {
    private String zzUc;
    public int zzaIA;
    public int zzaIB;
    public int zzaIC;
    public int zzzQ;
    public int zzzR;

    public String getLanguage() {
        return this.zzUc;
    }

    public void setLanguage(String language) {
        this.zzUc = language;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("language", this.zzUc);
        hashMap.put("screenColors", Integer.valueOf(this.zzaIA));
        hashMap.put("screenWidth", Integer.valueOf(this.zzzQ));
        hashMap.put("screenHeight", Integer.valueOf(this.zzzR));
        hashMap.put("viewportWidth", Integer.valueOf(this.zzaIB));
        hashMap.put("viewportHeight", Integer.valueOf(this.zzaIC));
        return zzod.zzA(hashMap);
    }

    public void zza(zzom com_google_android_gms_internal_zzom) {
        if (this.zzaIA != 0) {
            com_google_android_gms_internal_zzom.zzhN(this.zzaIA);
        }
        if (this.zzzQ != 0) {
            com_google_android_gms_internal_zzom.zzhO(this.zzzQ);
        }
        if (this.zzzR != 0) {
            com_google_android_gms_internal_zzom.zzhP(this.zzzR);
        }
        if (this.zzaIB != 0) {
            com_google_android_gms_internal_zzom.zzhQ(this.zzaIB);
        }
        if (this.zzaIC != 0) {
            com_google_android_gms_internal_zzom.zzhR(this.zzaIC);
        }
        if (!TextUtils.isEmpty(this.zzUc)) {
            com_google_android_gms_internal_zzom.setLanguage(this.zzUc);
        }
    }

    public void zzhN(int i) {
        this.zzaIA = i;
    }

    public void zzhO(int i) {
        this.zzzQ = i;
    }

    public void zzhP(int i) {
        this.zzzR = i;
    }

    public void zzhQ(int i) {
        this.zzaIB = i;
    }

    public void zzhR(int i) {
        this.zzaIC = i;
    }

    public int zzxH() {
        return this.zzaIA;
    }

    public int zzxI() {
        return this.zzzQ;
    }

    public int zzxJ() {
        return this.zzzR;
    }

    public int zzxK() {
        return this.zzaIB;
    }

    public int zzxL() {
        return this.zzaIC;
    }
}
