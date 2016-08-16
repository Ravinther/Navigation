package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class Scope implements SafeParcelable {
    public static final Creator<Scope> CREATOR;
    final int mVersionCode;
    private final String zzaaC;

    static {
        CREATOR = new zzm();
    }

    Scope(int versionCode, String scopeUri) {
        zzx.zzh(scopeUri, "scopeUri must not be null or empty");
        this.mVersionCode = versionCode;
        this.zzaaC = scopeUri;
    }

    public Scope(String scopeUri) {
        this(1, scopeUri);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return !(o instanceof Scope) ? false : this.zzaaC.equals(((Scope) o).zzaaC);
    }

    public int hashCode() {
        return this.zzaaC.hashCode();
    }

    public String toString() {
        return this.zzaaC;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzm.zza(this, dest, flags);
    }

    public String zznG() {
        return this.zzaaC;
    }
}
