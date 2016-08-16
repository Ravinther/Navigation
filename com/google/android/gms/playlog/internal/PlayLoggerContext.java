package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class PlayLoggerContext implements SafeParcelable {
    public static final zze CREATOR;
    public final String packageName;
    public final int versionCode;
    public final int zzaKR;
    public final int zzaKS;
    public final String zzaKT;
    public final String zzaKU;
    public final boolean zzaKV;
    public final String zzaKW;
    public final boolean zzaKX;
    public final int zzaKY;

    static {
        CREATOR = new zze();
    }

    public PlayLoggerContext(int versionCode, String packageName, int packageVersionCode, int logSource, String uploadAccountName, String loggingId, boolean logAndroidId, String logSourceName, boolean isAnonymous, int qosTier) {
        this.versionCode = versionCode;
        this.packageName = packageName;
        this.zzaKR = packageVersionCode;
        this.zzaKS = logSource;
        this.zzaKT = uploadAccountName;
        this.zzaKU = loggingId;
        this.zzaKV = logAndroidId;
        this.zzaKW = logSourceName;
        this.zzaKX = isAnonymous;
        this.zzaKY = qosTier;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlayLoggerContext)) {
            return false;
        }
        PlayLoggerContext playLoggerContext = (PlayLoggerContext) object;
        return this.versionCode == playLoggerContext.versionCode && this.packageName.equals(playLoggerContext.packageName) && this.zzaKR == playLoggerContext.zzaKR && this.zzaKS == playLoggerContext.zzaKS && zzw.equal(this.zzaKW, playLoggerContext.zzaKW) && zzw.equal(this.zzaKT, playLoggerContext.zzaKT) && zzw.equal(this.zzaKU, playLoggerContext.zzaKU) && this.zzaKV == playLoggerContext.zzaKV && this.zzaKX == playLoggerContext.zzaKX && this.zzaKY == playLoggerContext.zzaKY;
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.versionCode), this.packageName, Integer.valueOf(this.zzaKR), Integer.valueOf(this.zzaKS), this.zzaKW, this.zzaKT, this.zzaKU, Boolean.valueOf(this.zzaKV), Boolean.valueOf(this.zzaKX), Integer.valueOf(this.zzaKY));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PlayLoggerContext[");
        stringBuilder.append("versionCode=").append(this.versionCode).append(',');
        stringBuilder.append("package=").append(this.packageName).append(',');
        stringBuilder.append("packageVersionCode=").append(this.zzaKR).append(',');
        stringBuilder.append("logSource=").append(this.zzaKS).append(',');
        stringBuilder.append("logSourceName=").append(this.zzaKW).append(',');
        stringBuilder.append("uploadAccount=").append(this.zzaKT).append(',');
        stringBuilder.append("loggingId=").append(this.zzaKU).append(',');
        stringBuilder.append("logAndroidId=").append(this.zzaKV).append(',');
        stringBuilder.append("isAnonymous=").append(this.zzaKX).append(',');
        stringBuilder.append("qosTier=").append(this.zzaKY);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        zze.zza(this, out, flags);
    }
}
