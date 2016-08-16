package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.List;

public class AutocompletePredictionEntity implements SafeParcelable {
    public static final Creator<AutocompletePredictionEntity> CREATOR;
    final int mVersionCode;
    final String zzaDH;
    final List<Integer> zzaDi;
    final List<SubstringEntity> zzaEb;
    final int zzaEc;
    final String zzaoB;

    public static class SubstringEntity implements SafeParcelable {
        public static final Creator<SubstringEntity> CREATOR;
        final int mLength;
        final int mOffset;
        final int mVersionCode;

        static {
            CREATOR = new zzu();
        }

        public SubstringEntity(int versionCode, int offset, int length) {
            this.mVersionCode = versionCode;
            this.mOffset = offset;
            this.mLength = length;
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof SubstringEntity)) {
                return false;
            }
            SubstringEntity substringEntity = (SubstringEntity) object;
            return zzw.equal(Integer.valueOf(this.mOffset), Integer.valueOf(substringEntity.mOffset)) && zzw.equal(Integer.valueOf(this.mLength), Integer.valueOf(substringEntity.mLength));
        }

        public int hashCode() {
            return zzw.hashCode(Integer.valueOf(this.mOffset), Integer.valueOf(this.mLength));
        }

        public String toString() {
            return zzw.zzu(this).zzg("offset", Integer.valueOf(this.mOffset)).zzg("length", Integer.valueOf(this.mLength)).toString();
        }

        public void writeToParcel(Parcel parcel, int flags) {
            zzu.zza(this, parcel, flags);
        }
    }

    static {
        CREATOR = new zza();
    }

    AutocompletePredictionEntity(int versionCode, String description, String placeId, List<Integer> placeTypes, List<SubstringEntity> substrings, int personalizationType) {
        this.mVersionCode = versionCode;
        this.zzaoB = description;
        this.zzaDH = placeId;
        this.zzaDi = placeTypes;
        this.zzaEb = substrings;
        this.zzaEc = personalizationType;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AutocompletePredictionEntity)) {
            return false;
        }
        AutocompletePredictionEntity autocompletePredictionEntity = (AutocompletePredictionEntity) object;
        return zzw.equal(this.zzaoB, autocompletePredictionEntity.zzaoB) && zzw.equal(this.zzaDH, autocompletePredictionEntity.zzaDH) && zzw.equal(this.zzaDi, autocompletePredictionEntity.zzaDi) && zzw.equal(this.zzaEb, autocompletePredictionEntity.zzaEb) && zzw.equal(Integer.valueOf(this.zzaEc), Integer.valueOf(autocompletePredictionEntity.zzaEc));
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaoB, this.zzaDH, this.zzaDi, this.zzaEb, Integer.valueOf(this.zzaEc));
    }

    public String toString() {
        return zzw.zzu(this).zzg("description", this.zzaoB).zzg("placeId", this.zzaDH).zzg("placeTypes", this.zzaDi).zzg("substrings", this.zzaEb).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zza.zza(this, parcel, flags);
    }
}
