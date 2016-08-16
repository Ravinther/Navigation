package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class StreetViewPanoramaLocation implements SafeParcelable {
    public static final zzl CREATOR;
    public final StreetViewPanoramaLink[] links;
    private final int mVersionCode;
    public final String panoId;
    public final LatLng position;

    static {
        CREATOR = new zzl();
    }

    StreetViewPanoramaLocation(int versionCode, StreetViewPanoramaLink[] links, LatLng position, String panoId) {
        this.mVersionCode = versionCode;
        this.links = links;
        this.position = position;
        this.panoId = panoId;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetViewPanoramaLocation)) {
            return false;
        }
        StreetViewPanoramaLocation streetViewPanoramaLocation = (StreetViewPanoramaLocation) o;
        return this.panoId.equals(streetViewPanoramaLocation.panoId) && this.position.equals(streetViewPanoramaLocation.position);
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return zzw.hashCode(this.position, this.panoId);
    }

    public String toString() {
        return zzw.zzu(this).zzg("panoId", this.panoId).zzg("position", this.position.toString()).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        zzl.zza(this, out, flags);
    }
}
