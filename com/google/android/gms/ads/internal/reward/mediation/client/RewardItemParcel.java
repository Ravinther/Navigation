package com.google.android.gms.ads.internal.reward.mediation.client;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzgk;

@zzgk
public final class RewardItemParcel implements SafeParcelable {
    public static final zzc CREATOR;
    public final String type;
    public final int versionCode;
    public final int zzGE;

    static {
        CREATOR = new zzc();
    }

    public RewardItemParcel(int versionCode, String type, int amount) {
        this.versionCode = versionCode;
        this.type = type;
        this.zzGE = amount;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzc.zza(this, out, flags);
    }
}
