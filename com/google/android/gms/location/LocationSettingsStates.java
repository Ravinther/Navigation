package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LocationSettingsStates implements SafeParcelable {
    public static final Creator<LocationSettingsStates> CREATOR;
    private final int mVersionCode;
    private final boolean zzaCi;
    private final boolean zzaCj;
    private final boolean zzaCk;
    private final boolean zzaCl;
    private final boolean zzaCm;
    private final boolean zzaCn;
    private final boolean zzaCo;

    static {
        CREATOR = new zzh();
    }

    LocationSettingsStates(int version, boolean gpsUsable, boolean nlpUsable, boolean bleUsable, boolean gpsPresent, boolean nlpPresent, boolean blePresent, boolean userLocationReportingOn) {
        this.mVersionCode = version;
        this.zzaCi = gpsUsable;
        this.zzaCj = nlpUsable;
        this.zzaCk = bleUsable;
        this.zzaCl = gpsPresent;
        this.zzaCm = nlpPresent;
        this.zzaCn = blePresent;
        this.zzaCo = userLocationReportingOn;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public boolean isBlePresent() {
        return this.zzaCn;
    }

    public boolean isBleUsable() {
        return this.zzaCk;
    }

    public boolean isGpsPresent() {
        return this.zzaCl;
    }

    public boolean isGpsUsable() {
        return this.zzaCi;
    }

    public boolean isNetworkLocationPresent() {
        return this.zzaCm;
    }

    public boolean isNetworkLocationUsable() {
        return this.zzaCj;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzh.zza(this, dest, flags);
    }

    public boolean zzvM() {
        return this.zzaCo;
    }
}
