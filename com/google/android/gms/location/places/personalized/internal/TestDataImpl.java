package com.google.android.gms.location.places.personalized.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.location.places.personalized.zzf;

public class TestDataImpl extends zzf implements SafeParcelable {
    public static final zza CREATOR;
    final int mVersionCode;
    private final String zzaFm;

    static {
        CREATOR = new zza();
    }

    TestDataImpl(int versionCode, String testName) {
        this.mVersionCode = versionCode;
        this.zzaFm = testName;
    }

    public int describeContents() {
        zza com_google_android_gms_location_places_personalized_internal_zza = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TestDataImpl)) {
            return false;
        }
        return this.zzaFm.equals(((TestDataImpl) object).zzaFm);
    }

    public int hashCode() {
        return this.zzaFm.hashCode();
    }

    public String toString() {
        return zzw.zzu(this).zzg("testName", this.zzaFm).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zza com_google_android_gms_location_places_personalized_internal_zza = CREATOR;
        zza.zza(this, parcel, flags);
    }

    public String zzwA() {
        return this.zzaFm;
    }
}
