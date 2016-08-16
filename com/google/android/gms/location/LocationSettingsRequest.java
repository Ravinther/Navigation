package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LocationSettingsRequest implements SafeParcelable {
    public static final Creator<LocationSettingsRequest> CREATOR;
    private final int mVersionCode;
    private final boolean zzaCd;
    private final boolean zzaCe;
    private final boolean zzaCf;
    private final List<LocationRequest> zzaqn;

    public static final class Builder {
        private boolean zzaCd;
        private boolean zzaCe;
        private boolean zzaCf;
        private final ArrayList<LocationRequest> zzaCg;

        public Builder() {
            this.zzaCg = new ArrayList();
            this.zzaCd = false;
            this.zzaCe = false;
            this.zzaCf = false;
        }

        public Builder addLocationRequest(LocationRequest request) {
            this.zzaCg.add(request);
            return this;
        }

        public LocationSettingsRequest build() {
            return new LocationSettingsRequest(this.zzaCd, this.zzaCe, this.zzaCf, null);
        }

        public Builder setAlwaysShow(boolean show) {
            this.zzaCd = show;
            return this;
        }
    }

    static {
        CREATOR = new zzf();
    }

    LocationSettingsRequest(int version, List<LocationRequest> locationRequests, boolean alwaysShow, boolean needBle, boolean optInUserLocationReporting) {
        this.mVersionCode = version;
        this.zzaqn = locationRequests;
        this.zzaCd = alwaysShow;
        this.zzaCe = needBle;
        this.zzaCf = optInUserLocationReporting;
    }

    private LocationSettingsRequest(List<LocationRequest> locationRequests, boolean alwaysShow, boolean needBle, boolean optInUserLocationReporting) {
        this(2, (List) locationRequests, alwaysShow, needBle, optInUserLocationReporting);
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzf.zza(this, dest, flags);
    }

    public List<LocationRequest> zzsr() {
        return Collections.unmodifiableList(this.zzaqn);
    }

    public boolean zzvJ() {
        return this.zzaCd;
    }

    public boolean zzvK() {
        return this.zzaCe;
    }

    public boolean zzvL() {
        return this.zzaCf;
    }
}
