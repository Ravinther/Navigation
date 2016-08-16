package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public class LocationRequestInternal implements SafeParcelable {
    public static final zzk CREATOR;
    static final List<ClientIdentity> zzaCT;
    final String mTag;
    private final int mVersionCode;
    boolean zzaCU;
    boolean zzaCV;
    boolean zzaCW;
    List<ClientIdentity> zzaCX;
    boolean zzaCY;
    LocationRequest zzaqq;

    static {
        zzaCT = Collections.emptyList();
        CREATOR = new zzk();
    }

    LocationRequestInternal(int versionCode, LocationRequest locationRequest, boolean requestNlpDebugInfo, boolean restorePendingIntentListeners, boolean triggerUpdate, List<ClientIdentity> clients, String tag, boolean hideFromAppOps) {
        this.mVersionCode = versionCode;
        this.zzaqq = locationRequest;
        this.zzaCU = requestNlpDebugInfo;
        this.zzaCV = restorePendingIntentListeners;
        this.zzaCW = triggerUpdate;
        this.zzaCX = clients;
        this.mTag = tag;
        this.zzaCY = hideFromAppOps;
    }

    public static LocationRequestInternal zza(String str, LocationRequest locationRequest) {
        return new LocationRequestInternal(1, locationRequest, false, true, true, zzaCT, str, false);
    }

    @Deprecated
    public static LocationRequestInternal zzb(LocationRequest locationRequest) {
        return zza(null, locationRequest);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof LocationRequestInternal)) {
            return false;
        }
        LocationRequestInternal locationRequestInternal = (LocationRequestInternal) other;
        return zzw.equal(this.zzaqq, locationRequestInternal.zzaqq) && this.zzaCU == locationRequestInternal.zzaCU && this.zzaCV == locationRequestInternal.zzaCV && this.zzaCW == locationRequestInternal.zzaCW && this.zzaCY == locationRequestInternal.zzaCY && zzw.equal(this.zzaCX, locationRequestInternal.zzaCX);
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return this.zzaqq.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.zzaqq.toString());
        stringBuilder.append(" requestNlpDebugInfo=");
        stringBuilder.append(this.zzaCU);
        stringBuilder.append(" restorePendingIntentListeners=");
        stringBuilder.append(this.zzaCV);
        stringBuilder.append(" triggerUpdate=");
        stringBuilder.append(this.zzaCW);
        stringBuilder.append(" hideFromAppOps=");
        stringBuilder.append(this.zzaCY);
        stringBuilder.append(" clients=");
        stringBuilder.append(this.zzaCX);
        if (this.mTag != null) {
            stringBuilder.append(" tag=");
            stringBuilder.append(this.mTag);
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzk.zza(this, parcel, flags);
    }
}
