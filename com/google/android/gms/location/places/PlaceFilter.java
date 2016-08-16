package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class PlaceFilter extends zza implements SafeParcelable {
    public static final zzg CREATOR;
    final int mVersionCode;
    final List<Integer> zzaDm;
    private final Set<Integer> zzaDn;
    final List<String> zzaDo;
    final List<UserDataType> zzaDp;
    private final Set<String> zzaDq;
    private final Set<UserDataType> zzaDr;
    final boolean zzaDz;

    static {
        CREATOR = new zzg();
    }

    public PlaceFilter() {
        this(false, null);
    }

    PlaceFilter(int versionCode, List<Integer> placeTypesList, boolean requireOpenNow, List<String> placeIdsList, List<UserDataType> requestedUserDataTypesList) {
        this.mVersionCode = versionCode;
        this.zzaDm = placeTypesList == null ? Collections.emptyList() : Collections.unmodifiableList(placeTypesList);
        this.zzaDz = requireOpenNow;
        this.zzaDp = requestedUserDataTypesList == null ? Collections.emptyList() : Collections.unmodifiableList(requestedUserDataTypesList);
        this.zzaDo = placeIdsList == null ? Collections.emptyList() : Collections.unmodifiableList(placeIdsList);
        this.zzaDn = zza.zzl(this.zzaDm);
        this.zzaDr = zza.zzl(this.zzaDp);
        this.zzaDq = zza.zzl(this.zzaDo);
    }

    public PlaceFilter(Collection<Integer> restrictToPlaceTypes, boolean requireOpenNow, Collection<String> restrictToPlaceIds, Collection<UserDataType> requestedUserDataTypes) {
        this(0, zza.zzf(restrictToPlaceTypes), requireOpenNow, zza.zzf(restrictToPlaceIds), zza.zzf(requestedUserDataTypes));
    }

    public PlaceFilter(boolean requireOpenNow, Collection<String> restrictToPlaceIds) {
        this(null, requireOpenNow, restrictToPlaceIds, null);
    }

    public int describeContents() {
        zzg com_google_android_gms_location_places_zzg = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlaceFilter)) {
            return false;
        }
        PlaceFilter placeFilter = (PlaceFilter) object;
        return this.zzaDn.equals(placeFilter.zzaDn) && this.zzaDz == placeFilter.zzaDz && this.zzaDr.equals(placeFilter.zzaDr) && this.zzaDq.equals(placeFilter.zzaDq);
    }

    public Set<String> getPlaceIds() {
        return this.zzaDq;
    }

    public Set<Integer> getPlaceTypes() {
        return this.zzaDn;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaDn, Boolean.valueOf(this.zzaDz), this.zzaDr, this.zzaDq);
    }

    public String toString() {
        zza zzu = zzw.zzu(this);
        if (!this.zzaDn.isEmpty()) {
            zzu.zzg("types", this.zzaDn);
        }
        zzu.zzg("requireOpenNow", Boolean.valueOf(this.zzaDz));
        if (!this.zzaDq.isEmpty()) {
            zzu.zzg("placeIds", this.zzaDq);
        }
        if (!this.zzaDr.isEmpty()) {
            zzu.zzg("requestedUserDataTypes", this.zzaDr);
        }
        return zzu.toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzg com_google_android_gms_location_places_zzg = CREATOR;
        zzg.zza(this, parcel, flags);
    }

    public Set<UserDataType> zzwd() {
        return this.zzaDr;
    }
}
