package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class PointOfInterest implements SafeParcelable {
    public static final zzg CREATOR;
    private final int mVersionCode;
    public final String name;
    public final LatLng zzaHy;
    public final String zzaHz;

    static {
        CREATOR = new zzg();
    }

    PointOfInterest(int versionCode, LatLng latLng, String placeId, String name) {
        this.mVersionCode = versionCode;
        this.zzaHy = latLng;
        this.zzaHz = placeId;
        this.name = name;
    }

    public int describeContents() {
        return 0;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzg.zza(this, out, flags);
    }
}
