package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ResolveAccountRequest implements SafeParcelable {
    public static final Creator<ResolveAccountRequest> CREATOR;
    final int mVersionCode;
    private final Account zzOY;
    private final int zzaem;

    static {
        CREATOR = new zzy();
    }

    ResolveAccountRequest(int versionCode, Account account, int sessionId) {
        this.mVersionCode = versionCode;
        this.zzOY = account;
        this.zzaem = sessionId;
    }

    public ResolveAccountRequest(Account account, int sessionId) {
        this(1, account, sessionId);
    }

    public int describeContents() {
        return 0;
    }

    public Account getAccount() {
        return this.zzOY;
    }

    public int getSessionId() {
        return this.zzaem;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzy.zza(this, dest, flags);
    }
}
