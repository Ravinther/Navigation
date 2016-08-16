package com.google.android.gms.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class GoogleSignInAccount implements SafeParcelable {
    public static final Creator<GoogleSignInAccount> CREATOR;
    final int versionCode;
    private String zzRn;
    private String zzaNX;
    private Uri zzaNY;
    private String zzahh;
    private String zzwj;

    static {
        CREATOR = new zza();
    }

    GoogleSignInAccount(int versionCode, String id, String idToken, String email, String displayName, Uri photoUrl) {
        this.versionCode = versionCode;
        this.zzwj = zzx.zzcs(id);
        this.zzRn = idToken;
        this.zzaNX = email;
        this.zzahh = displayName;
        this.zzaNY = photoUrl;
    }

    public int describeContents() {
        return 0;
    }

    public String getDisplayName() {
        return this.zzahh;
    }

    public String getEmail() {
        return this.zzaNX;
    }

    public String getId() {
        return this.zzwj;
    }

    public void writeToParcel(Parcel out, int flags) {
        zza.zza(this, out, flags);
    }

    public String zzlv() {
        return this.zzRn;
    }

    public Uri zzzm() {
        return this.zzaNY;
    }
}
