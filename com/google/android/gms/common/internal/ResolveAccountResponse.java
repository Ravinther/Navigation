package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzp.zza;

public class ResolveAccountResponse implements SafeParcelable {
    public static final Creator<ResolveAccountResponse> CREATOR;
    final int mVersionCode;
    private boolean zzZF;
    private ConnectionResult zzaaV;
    IBinder zzacC;
    private boolean zzaen;

    static {
        CREATOR = new zzz();
    }

    public ResolveAccountResponse(int connectionResultStatusCode) {
        this(new ConnectionResult(connectionResultStatusCode, null));
    }

    ResolveAccountResponse(int versionCode, IBinder accountAccessorBinder, ConnectionResult connectionResult, boolean saveDefaultAccount, boolean isFromCrossClientAuth) {
        this.mVersionCode = versionCode;
        this.zzacC = accountAccessorBinder;
        this.zzaaV = connectionResult;
        this.zzZF = saveDefaultAccount;
        this.zzaen = isFromCrossClientAuth;
    }

    public ResolveAccountResponse(ConnectionResult result) {
        this(1, null, result, false, false);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResolveAccountResponse)) {
            return false;
        }
        ResolveAccountResponse resolveAccountResponse = (ResolveAccountResponse) o;
        return this.zzaaV.equals(resolveAccountResponse.zzaaV) && zzoO().equals(resolveAccountResponse.zzoO());
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzz.zza(this, dest, flags);
    }

    public zzp zzoO() {
        return zza.zzaH(this.zzacC);
    }

    public ConnectionResult zzoP() {
        return this.zzaaV;
    }

    public boolean zzoQ() {
        return this.zzZF;
    }

    public boolean zzoR() {
        return this.zzaen;
    }
}
