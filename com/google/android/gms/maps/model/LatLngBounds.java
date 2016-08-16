package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;

public final class LatLngBounds implements SafeParcelable {
    public static final zzd CREATOR;
    private final int mVersionCode;
    public final LatLng northeast;
    public final LatLng southwest;

    static {
        CREATOR = new zzd();
    }

    LatLngBounds(int versionCode, LatLng southwest, LatLng northeast) {
        zzx.zzb((Object) southwest, (Object) "null southwest");
        zzx.zzb((Object) northeast, (Object) "null northeast");
        zzx.zzb(northeast.latitude >= southwest.latitude, "southern latitude exceeds northern latitude (%s > %s)", Double.valueOf(southwest.latitude), Double.valueOf(northeast.latitude));
        this.mVersionCode = versionCode;
        this.southwest = southwest;
        this.northeast = northeast;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LatLngBounds)) {
            return false;
        }
        LatLngBounds latLngBounds = (LatLngBounds) o;
        return this.southwest.equals(latLngBounds.southwest) && this.northeast.equals(latLngBounds.northeast);
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return zzw.hashCode(this.southwest, this.northeast);
    }

    public String toString() {
        return zzw.zzu(this).zzg("southwest", this.southwest).zzg("northeast", this.northeast).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        zzd.zza(this, out, flags);
    }
}
