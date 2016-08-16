package com.google.android.gms.appdatasearch;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class Feature implements SafeParcelable {
    public static final zze CREATOR;
    public final int id;
    final int mVersionCode;
    final Bundle zzPi;

    static {
        CREATOR = new zze();
    }

    Feature(int versionCode, int id, Bundle parameters) {
        this.mVersionCode = versionCode;
        this.id = id;
        this.zzPi = parameters;
    }

    public int describeContents() {
        zze com_google_android_gms_appdatasearch_zze = CREATOR;
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zze com_google_android_gms_appdatasearch_zze = CREATOR;
        zze.zza(this, dest, flags);
    }
}
