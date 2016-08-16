package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.location.places.personalized.internal.TestDataImpl;
import java.util.List;

public class PlaceUserData implements SafeParcelable {
    public static final zze CREATOR;
    final int mVersionCode;
    private final String zzQE;
    private final String zzaDH;
    private final List<TestDataImpl> zzaFj;
    private final List<PlaceAlias> zzaFk;
    private final List<HereContent> zzaFl;

    static {
        CREATOR = new zze();
    }

    PlaceUserData(int versionCode, String accountName, String placeId, List<TestDataImpl> testDataImpls, List<PlaceAlias> placeAliases, List<HereContent> hereContents) {
        this.mVersionCode = versionCode;
        this.zzQE = accountName;
        this.zzaDH = placeId;
        this.zzaFj = testDataImpls;
        this.zzaFk = placeAliases;
        this.zzaFl = hereContents;
    }

    public int describeContents() {
        zze com_google_android_gms_location_places_personalized_zze = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlaceUserData)) {
            return false;
        }
        PlaceUserData placeUserData = (PlaceUserData) object;
        return this.zzQE.equals(placeUserData.zzQE) && this.zzaDH.equals(placeUserData.zzaDH) && this.zzaFj.equals(placeUserData.zzaFj) && this.zzaFk.equals(placeUserData.zzaFk) && this.zzaFl.equals(placeUserData.zzaFl);
    }

    public String getPlaceId() {
        return this.zzaDH;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzQE, this.zzaDH, this.zzaFj, this.zzaFk, this.zzaFl);
    }

    public String toString() {
        return zzw.zzu(this).zzg("accountName", this.zzQE).zzg("placeId", this.zzaDH).zzg("testDataImpls", this.zzaFj).zzg("placeAliases", this.zzaFk).zzg("hereContents", this.zzaFl).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zze com_google_android_gms_location_places_personalized_zze = CREATOR;
        zze.zza(this, parcel, flags);
    }

    public String zzww() {
        return this.zzQE;
    }

    public List<PlaceAlias> zzwx() {
        return this.zzaFk;
    }

    public List<HereContent> zzwy() {
        return this.zzaFl;
    }

    public List<TestDataImpl> zzwz() {
        return this.zzaFj;
    }
}
