package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public final class LocationAvailability implements SafeParcelable {
    public static final LocationAvailabilityCreator CREATOR;
    private final int mVersionCode;
    int zzaBS;
    int zzaBT;
    long zzaBU;
    int zzaBV;

    static {
        CREATOR = new LocationAvailabilityCreator();
    }

    LocationAvailability(int versionCode, int locationStatus, int cellStatus, int wifiStatus, long elapsedRealtimeNs) {
        this.mVersionCode = versionCode;
        this.zzaBV = locationStatus;
        this.zzaBS = cellStatus;
        this.zzaBT = wifiStatus;
        this.zzaBU = elapsedRealtimeNs;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof LocationAvailability)) {
            return false;
        }
        LocationAvailability locationAvailability = (LocationAvailability) other;
        return this.zzaBV == locationAvailability.zzaBV && this.zzaBS == locationAvailability.zzaBS && this.zzaBT == locationAvailability.zzaBT && this.zzaBU == locationAvailability.zzaBU;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.zzaBV), Integer.valueOf(this.zzaBS), Integer.valueOf(this.zzaBT), Long.valueOf(this.zzaBU));
    }

    public boolean isLocationAvailable() {
        return this.zzaBV < 1000;
    }

    public String toString() {
        return "LocationAvailability[isLocationAvailable: " + isLocationAvailable() + "]";
    }

    public void writeToParcel(Parcel parcel, int flags) {
        LocationAvailabilityCreator.zza(this, parcel, flags);
    }
}
