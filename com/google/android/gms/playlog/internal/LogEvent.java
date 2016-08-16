package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class LogEvent implements SafeParcelable {
    public static final zzc CREATOR;
    public final String tag;
    public final int versionCode;
    public final long zzaKG;
    public final long zzaKH;
    public final byte[] zzaKI;
    public final Bundle zzaKJ;

    static {
        CREATOR = new zzc();
    }

    LogEvent(int versionCode, long eventTime, long eventUptime, String tag, byte[] sourceExtensionBytes, Bundle keyValuePairs) {
        this.versionCode = versionCode;
        this.zzaKG = eventTime;
        this.zzaKH = eventUptime;
        this.tag = tag;
        this.zzaKI = sourceExtensionBytes;
        this.zzaKJ = keyValuePairs;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tag=").append(this.tag).append(",");
        stringBuilder.append("eventTime=").append(this.zzaKG).append(",");
        stringBuilder.append("eventUptime=").append(this.zzaKH).append(",");
        if (!(this.zzaKJ == null || this.zzaKJ.isEmpty())) {
            stringBuilder.append("keyValues=");
            for (String str : this.zzaKJ.keySet()) {
                stringBuilder.append("(").append(str).append(",");
                stringBuilder.append(this.zzaKJ.getString(str)).append(")");
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        zzc.zza(this, out, flags);
    }
}
