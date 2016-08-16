package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class UserDataType implements SafeParcelable {
    public static final zzm CREATOR;
    public static final UserDataType zzaDW;
    public static final UserDataType zzaDX;
    public static final UserDataType zzaDY;
    public static final Set<UserDataType> zzaDZ;
    final int mVersionCode;
    final String zzFz;
    final int zzaEa;

    static {
        zzaDW = zzz("test_type", 1);
        zzaDX = zzz("labeled_place", 6);
        zzaDY = zzz("here_content", 7);
        zzaDZ = Collections.unmodifiableSet(new HashSet(Arrays.asList(new UserDataType[]{zzaDW, zzaDX, zzaDY})));
        CREATOR = new zzm();
    }

    UserDataType(int versionCode, String type, int enumValue) {
        zzx.zzcs(type);
        this.mVersionCode = versionCode;
        this.zzFz = type;
        this.zzaEa = enumValue;
    }

    private static UserDataType zzz(String str, int i) {
        return new UserDataType(0, str, i);
    }

    public int describeContents() {
        zzm com_google_android_gms_location_places_zzm = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UserDataType)) {
            return false;
        }
        UserDataType userDataType = (UserDataType) object;
        return this.zzFz.equals(userDataType.zzFz) && this.zzaEa == userDataType.zzaEa;
    }

    public int hashCode() {
        return this.zzFz.hashCode();
    }

    public String toString() {
        return this.zzFz;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzm com_google_android_gms_location_places_zzm = CREATOR;
        zzm.zza(this, parcel, flags);
    }
}
