package com.google.android.gms.ads.internal.util.client;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzgk;

@zzgk
public final class VersionInfoParcel implements SafeParcelable {
    public static final zzc CREATOR;
    public final int versionCode;
    public int zzIA;
    public int zzIB;
    public boolean zzIC;
    public String zzIz;

    static {
        CREATOR = new zzc();
    }

    public VersionInfoParcel(int buddyApkVersion, int clientJarVersion, boolean isClientJar) {
        this(1, "afma-sdk-a-v" + buddyApkVersion + "." + clientJarVersion + "." + (isClientJar ? "0" : "1"), buddyApkVersion, clientJarVersion, isClientJar);
    }

    VersionInfoParcel(int versionCode, String afmaVersion, int buddyApkVersion, int clientJarVersion, boolean isClientJar) {
        this.versionCode = versionCode;
        this.zzIz = afmaVersion;
        this.zzIA = buddyApkVersion;
        this.zzIB = clientJarVersion;
        this.zzIC = isClientJar;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzc.zza(this, out, flags);
    }
}
