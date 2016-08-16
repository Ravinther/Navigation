package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class UsageInfo implements SafeParcelable {
    public static final zzj CREATOR;
    final int mVersionCode;
    final DocumentId zzPP;
    final long zzPQ;
    int zzPR;
    final DocumentContents zzPS;
    final boolean zzPT;
    int zzPU;
    int zzPV;
    public final String zztM;

    static {
        CREATOR = new zzj();
    }

    UsageInfo(int versionCode, DocumentId documentId, long timestamp, int usageType, String query, DocumentContents document, boolean isDeviceOnly, int taskPosition, int eventStatus) {
        this.mVersionCode = versionCode;
        this.zzPP = documentId;
        this.zzPQ = timestamp;
        this.zzPR = usageType;
        this.zztM = query;
        this.zzPS = document;
        this.zzPT = isDeviceOnly;
        this.zzPU = taskPosition;
        this.zzPV = eventStatus;
    }

    public int describeContents() {
        zzj com_google_android_gms_appdatasearch_zzj = CREATOR;
        return 0;
    }

    public String toString() {
        return String.format("UsageInfo[documentId=%s, timestamp=%d, usageType=%d, status=%d]", new Object[]{this.zzPP, Long.valueOf(this.zzPQ), Integer.valueOf(this.zzPR), Integer.valueOf(this.zzPV)});
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzj com_google_android_gms_appdatasearch_zzj = CREATOR;
        zzj.zza(this, dest, flags);
    }

    public DocumentContents zzle() {
        return this.zzPS;
    }
}
