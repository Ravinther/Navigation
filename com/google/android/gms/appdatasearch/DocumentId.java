package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class DocumentId implements SafeParcelable {
    public static final zzc CREATOR;
    final int mVersionCode;
    final String zzOZ;
    final String zzPa;
    final String zzPb;

    static {
        CREATOR = new zzc();
    }

    DocumentId(int versionCode, String packageName, String corpusName, String uri) {
        this.mVersionCode = versionCode;
        this.zzOZ = packageName;
        this.zzPa = corpusName;
        this.zzPb = uri;
    }

    public int describeContents() {
        zzc com_google_android_gms_appdatasearch_zzc = CREATOR;
        return 0;
    }

    public String toString() {
        return String.format("DocumentId[packageName=%s, corpusName=%s, uri=%s]", new Object[]{this.zzOZ, this.zzPa, this.zzPb});
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzc com_google_android_gms_appdatasearch_zzc = CREATOR;
        zzc.zza(this, dest, flags);
    }
}
