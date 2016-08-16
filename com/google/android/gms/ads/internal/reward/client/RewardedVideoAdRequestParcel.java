package com.google.android.gms.ads.internal.reward.client;

import android.os.Parcel;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzgk;

@zzgk
public final class RewardedVideoAdRequestParcel implements SafeParcelable {
    public static final zzh CREATOR;
    public final int versionCode;
    public final AdRequestParcel zzDy;
    public final String zzpZ;

    static {
        CREATOR = new zzh();
    }

    public RewardedVideoAdRequestParcel(int versionCode, AdRequestParcel adRequest, String adUnitId) {
        this.versionCode = versionCode;
        this.zzDy = adRequest;
        this.zzpZ = adUnitId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzh.zza(this, out, flags);
    }
}
