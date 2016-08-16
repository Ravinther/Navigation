package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.concurrent.TimeUnit;

public final class PlaceRequest implements SafeParcelable {
    public static final Creator<PlaceRequest> CREATOR;
    static final long zzaDJ;
    private final int mPriority;
    final int mVersionCode;
    private final long zzaBB;
    private final long zzaBW;
    private final PlaceFilter zzaDK;

    static {
        CREATOR = new zzk();
        zzaDJ = TimeUnit.HOURS.toMillis(1);
    }

    public PlaceRequest(int versionCode, PlaceFilter filter, long interval, int priority, long expireAt) {
        this.mVersionCode = versionCode;
        this.zzaDK = filter;
        this.zzaBW = interval;
        this.mPriority = priority;
        this.zzaBB = expireAt;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlaceRequest)) {
            return false;
        }
        PlaceRequest placeRequest = (PlaceRequest) object;
        return zzw.equal(this.zzaDK, placeRequest.zzaDK) && this.zzaBW == placeRequest.zzaBW && this.mPriority == placeRequest.mPriority && this.zzaBB == placeRequest.zzaBB;
    }

    public long getExpirationTime() {
        return this.zzaBB;
    }

    public long getInterval() {
        return this.zzaBW;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaDK, Long.valueOf(this.zzaBW), Integer.valueOf(this.mPriority), Long.valueOf(this.zzaBB));
    }

    public String toString() {
        return zzw.zzu(this).zzg("filter", this.zzaDK).zzg("interval", Long.valueOf(this.zzaBW)).zzg("priority", Integer.valueOf(this.mPriority)).zzg("expireAt", Long.valueOf(this.zzaBB)).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzk.zza(this, parcel, flags);
    }

    public PlaceFilter zzwa() {
        return this.zzaDK;
    }
}
