package com.google.android.gms.auth.api.signin;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;

public class FacebookSignInConfig implements SafeParcelable {
    public static final Creator<FacebookSignInConfig> CREATOR;
    private Intent mIntent;
    final int versionCode;
    private final ArrayList<String> zzRR;

    static {
        CREATOR = new zzb();
    }

    public FacebookSignInConfig() {
        this(1, null, new ArrayList());
    }

    FacebookSignInConfig(int versionCode, Intent intent, ArrayList<String> scopes) {
        this.versionCode = versionCode;
        this.mIntent = intent;
        this.zzRR = scopes;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzb.zza(this, out, flags);
    }

    public Intent zzlD() {
        return this.mIntent;
    }

    public ArrayList<String> zzlE() {
        return new ArrayList(this.zzRR);
    }
}
