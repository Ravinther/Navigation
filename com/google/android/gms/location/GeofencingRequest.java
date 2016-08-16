package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.List;

public class GeofencingRequest implements SafeParcelable {
    public static final Creator<GeofencingRequest> CREATOR;
    private final int mVersionCode;
    private final List<ParcelableGeofence> zzaBL;
    private final int zzaBM;

    static {
        CREATOR = new zza();
    }

    GeofencingRequest(int version, List<ParcelableGeofence> geofences, int initialTrigger) {
        this.mVersionCode = version;
        this.zzaBL = geofences;
        this.zzaBM = initialTrigger;
    }

    public int describeContents() {
        return 0;
    }

    public int getInitialTrigger() {
        return this.zzaBM;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zza.zza(this, dest, flags);
    }

    public List<ParcelableGeofence> zzvH() {
        return this.zzaBL;
    }
}
