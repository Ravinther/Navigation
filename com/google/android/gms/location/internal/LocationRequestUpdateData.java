package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.zzc;
import com.google.android.gms.location.zzd;
import com.google.android.gms.location.zzd.zza;

public class LocationRequestUpdateData implements SafeParcelable {
    public static final zzl CREATOR;
    PendingIntent mPendingIntent;
    private final int mVersionCode;
    int zzaCZ;
    LocationRequestInternal zzaDa;
    zzd zzaDb;
    zzc zzaDc;

    static {
        CREATOR = new zzl();
    }

    LocationRequestUpdateData(int versionCode, int operation, LocationRequestInternal locationRequest, IBinder locationListenerBinder, PendingIntent pendingIntent, IBinder locationCallbackBinder) {
        zzc com_google_android_gms_location_zzc = null;
        this.mVersionCode = versionCode;
        this.zzaCZ = operation;
        this.zzaDa = locationRequest;
        this.zzaDb = locationListenerBinder == null ? null : zza.zzbX(locationListenerBinder);
        this.mPendingIntent = pendingIntent;
        if (locationCallbackBinder != null) {
            com_google_android_gms_location_zzc = zzc.zza.zzbW(locationCallbackBinder);
        }
        this.zzaDc = com_google_android_gms_location_zzc;
    }

    public static LocationRequestUpdateData zza(zzc com_google_android_gms_location_zzc) {
        return new LocationRequestUpdateData(1, 2, null, null, null, com_google_android_gms_location_zzc.asBinder());
    }

    public static LocationRequestUpdateData zzb(LocationRequestInternal locationRequestInternal, zzd com_google_android_gms_location_zzd) {
        return new LocationRequestUpdateData(1, 1, locationRequestInternal, com_google_android_gms_location_zzd.asBinder(), null, null);
    }

    public static LocationRequestUpdateData zzb(zzd com_google_android_gms_location_zzd) {
        return new LocationRequestUpdateData(1, 2, null, com_google_android_gms_location_zzd.asBinder(), null, null);
    }

    public int describeContents() {
        return 0;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzl.zza(this, parcel, flags);
    }

    IBinder zzvS() {
        return this.zzaDb == null ? null : this.zzaDb.asBinder();
    }

    IBinder zzvT() {
        return this.zzaDc == null ? null : this.zzaDc.asBinder();
    }
}
