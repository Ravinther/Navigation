package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;

public class PlaceReport implements SafeParcelable {
    public static final Creator<PlaceReport> CREATOR;
    private final String mTag;
    final int mVersionCode;
    private final String zzaDH;
    private final String zzaDI;

    static {
        CREATOR = new zzj();
    }

    PlaceReport(int versionCode, String placeId, String tag, String source) {
        this.mVersionCode = versionCode;
        this.zzaDH = placeId;
        this.mTag = tag;
        this.zzaDI = source;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object that) {
        if (!(that instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) that;
        return zzw.equal(this.zzaDH, placeReport.zzaDH) && zzw.equal(this.mTag, placeReport.mTag) && zzw.equal(this.zzaDI, placeReport.zzaDI);
    }

    public String getPlaceId() {
        return this.zzaDH;
    }

    public String getSource() {
        return this.zzaDI;
    }

    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaDH, this.mTag, this.zzaDI);
    }

    public String toString() {
        zza zzu = zzw.zzu(this);
        zzu.zzg("placeId", this.zzaDH);
        zzu.zzg("tag", this.mTag);
        if (!"unknown".equals(this.zzaDI)) {
            zzu.zzg("source", this.zzaDI);
        }
        return zzu.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        zzj.zza(this, out, flags);
    }
}
