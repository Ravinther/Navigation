package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class PlaceAlias implements SafeParcelable {
    public static final zzc CREATOR;
    public static final PlaceAlias zzaFg;
    public static final PlaceAlias zzaFh;
    final int mVersionCode;
    private final String zzaFi;

    static {
        CREATOR = new zzc();
        zzaFg = new PlaceAlias(0, "Home");
        zzaFh = new PlaceAlias(0, "Work");
    }

    PlaceAlias(int versionCode, String alias) {
        this.mVersionCode = versionCode;
        this.zzaFi = alias;
    }

    public int describeContents() {
        zzc com_google_android_gms_location_places_personalized_zzc = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlaceAlias)) {
            return false;
        }
        return zzw.equal(this.zzaFi, ((PlaceAlias) object).zzaFi);
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaFi);
    }

    public String toString() {
        return zzw.zzu(this).zzg("alias", this.zzaFi).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzc com_google_android_gms_location_places_personalized_zzc = CREATOR;
        zzc.zza(this, parcel, flags);
    }

    public String zzwv() {
        return this.zzaFi;
    }
}
