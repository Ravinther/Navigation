package com.google.android.gms.auth.api.credentials.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class SaveRequest implements SafeParcelable {
    public static final Creator<SaveRequest> CREATOR;
    final int mVersionCode;
    private final Credential zzRx;

    static {
        CREATOR = new zzi();
    }

    SaveRequest(int version, Credential credential) {
        this.mVersionCode = version;
        this.zzRx = credential;
    }

    public int describeContents() {
        return 0;
    }

    public Credential getCredential() {
        return this.zzRx;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzi.zza(this, out, flags);
    }
}
