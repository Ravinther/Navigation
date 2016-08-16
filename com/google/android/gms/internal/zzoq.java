package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzx;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class zzoq extends zzod<zzoq> {
    private String zzaIF;
    private int zzaIG;
    private int zzaIH;
    private String zzaII;
    private String zzaIJ;
    private boolean zzaIK;
    private boolean zzaIL;
    private boolean zzaIM;

    public zzoq() {
        this(false);
    }

    public zzoq(boolean z) {
        this(z, zzxS());
    }

    public zzoq(boolean z, int i) {
        zzx.zzbD(i);
        this.zzaIG = i;
        this.zzaIL = z;
    }

    static int zzxS() {
        UUID randomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (randomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits != 0) {
            return leastSignificantBits;
        }
        leastSignificantBits = (int) (randomUUID.getMostSignificantBits() & 2147483647L);
        if (leastSignificantBits != 0) {
            return leastSignificantBits;
        }
        Log.e("GAv4", "UUID.randomUUID() returned 0.");
        return Integer.MAX_VALUE;
    }

    private void zzxZ() {
        if (this.zzaIM) {
            throw new IllegalStateException("ScreenViewInfo is immutable");
        }
    }

    public boolean isMutable() {
        return !this.zzaIM;
    }

    public void setScreenName(String screenName) {
        zzxZ();
        this.zzaIF = screenName;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("screenName", this.zzaIF);
        hashMap.put("interstitial", Boolean.valueOf(this.zzaIK));
        hashMap.put("automatic", Boolean.valueOf(this.zzaIL));
        hashMap.put("screenId", Integer.valueOf(this.zzaIG));
        hashMap.put("referrerScreenId", Integer.valueOf(this.zzaIH));
        hashMap.put("referrerScreenName", this.zzaII);
        hashMap.put("referrerUri", this.zzaIJ);
        return zzod.zzA(hashMap);
    }

    public /* synthetic */ void zza(zzod com_google_android_gms_internal_zzod) {
        zzc((zzoq) com_google_android_gms_internal_zzod);
    }

    public void zzal(boolean z) {
        zzxZ();
        this.zzaIL = z;
    }

    public void zzam(boolean z) {
        zzxZ();
        this.zzaIK = z;
    }

    public int zzbp() {
        return this.zzaIG;
    }

    public void zzc(zzoq com_google_android_gms_internal_zzoq) {
        if (!TextUtils.isEmpty(this.zzaIF)) {
            com_google_android_gms_internal_zzoq.setScreenName(this.zzaIF);
        }
        if (this.zzaIG != 0) {
            com_google_android_gms_internal_zzoq.zzhS(this.zzaIG);
        }
        if (this.zzaIH != 0) {
            com_google_android_gms_internal_zzoq.zzhT(this.zzaIH);
        }
        if (!TextUtils.isEmpty(this.zzaII)) {
            com_google_android_gms_internal_zzoq.zzdU(this.zzaII);
        }
        if (!TextUtils.isEmpty(this.zzaIJ)) {
            com_google_android_gms_internal_zzoq.zzdV(this.zzaIJ);
        }
        if (this.zzaIK) {
            com_google_android_gms_internal_zzoq.zzam(this.zzaIK);
        }
        if (this.zzaIL) {
            com_google_android_gms_internal_zzoq.zzal(this.zzaIL);
        }
    }

    public void zzdU(String str) {
        zzxZ();
        this.zzaII = str;
    }

    public void zzdV(String str) {
        zzxZ();
        if (TextUtils.isEmpty(str)) {
            this.zzaIJ = null;
        } else {
            this.zzaIJ = str;
        }
    }

    public void zzhS(int i) {
        zzxZ();
        this.zzaIG = i;
    }

    public void zzhT(int i) {
        zzxZ();
        this.zzaIH = i;
    }

    public String zzxT() {
        return this.zzaIF;
    }

    public int zzxU() {
        return this.zzaIH;
    }

    public String zzxV() {
        return this.zzaII;
    }

    public String zzxW() {
        return this.zzaIJ;
    }

    public void zzxX() {
        this.zzaIM = true;
    }

    public boolean zzxY() {
        return this.zzaIK;
    }
}
