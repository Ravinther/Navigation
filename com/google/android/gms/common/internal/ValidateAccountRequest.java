package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ValidateAccountRequest implements SafeParcelable {
    public static final Creator<ValidateAccountRequest> CREATOR;
    final int mVersionCode;
    final IBinder zzacC;
    private final Scope[] zzacD;
    private final int zzaeq;
    private final Bundle zzaer;
    private final String zzaes;

    static {
        CREATOR = new zzad();
    }

    ValidateAccountRequest(int versionCode, int clientVersion, IBinder accountAccessorBinder, Scope[] scopes, Bundle extraArgs, String callingPackage) {
        this.mVersionCode = versionCode;
        this.zzaeq = clientVersion;
        this.zzacC = accountAccessorBinder;
        this.zzacD = scopes;
        this.zzaer = extraArgs;
        this.zzaes = callingPackage;
    }

    public ValidateAccountRequest(zzp accountAccessor, Scope[] scopes, String callingPackage, Bundle extraArgs) {
        this(1, GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE, accountAccessor == null ? null : accountAccessor.asBinder(), scopes, extraArgs, callingPackage);
    }

    public int describeContents() {
        return 0;
    }

    public String getCallingPackage() {
        return this.zzaes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzad.zza(this, dest, flags);
    }

    public int zzoS() {
        return this.zzaeq;
    }

    public Scope[] zzoT() {
        return this.zzacD;
    }

    public Bundle zzoU() {
        return this.zzaer;
    }
}
