package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class PlaceLikelihoodEntity implements SafeParcelable {
    public static final Creator<PlaceLikelihoodEntity> CREATOR;
    final int mVersionCode;
    final PlaceImpl zzaEI;
    final float zzaEJ;

    static {
        CREATOR = new zzl();
    }

    PlaceLikelihoodEntity(int versionCode, PlaceImpl place, float likelihood) {
        this.mVersionCode = versionCode;
        this.zzaEI = place;
        this.zzaEJ = likelihood;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlaceLikelihoodEntity)) {
            return false;
        }
        PlaceLikelihoodEntity placeLikelihoodEntity = (PlaceLikelihoodEntity) object;
        return this.zzaEI.equals(placeLikelihoodEntity.zzaEI) && this.zzaEJ == placeLikelihoodEntity.zzaEJ;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaEI, Float.valueOf(this.zzaEJ));
    }

    public String toString() {
        return zzw.zzu(this).zzg("place", this.zzaEI).zzg("likelihood", Float.valueOf(this.zzaEJ)).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzl.zza(this, parcel, flags);
    }
}
