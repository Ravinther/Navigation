package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public final class WakeLockEvent implements SafeParcelable {
    public static final Creator<WakeLockEvent> CREATOR;
    final int mVersionCode;
    private final String zzafS;
    private final int zzafT;
    private final List<String> zzafU;
    private final String zzafV;
    private int zzafW;
    private final String zzafX;
    private final String zzafY;
    private final float zzafZ;
    private final long zzafj;
    private int zzafk;
    private final long zzafr;
    private long zzaft;

    static {
        CREATOR = new zzg();
    }

    WakeLockEvent(int versionCode, long timeMillis, int eventType, String wakelockName, int wakelockType, List<String> callingPackages, String eventKey, long elapsedRealtime, int deviceState, String secondaryWakeLockName, String hostPackageName, float beginPowerPercentage) {
        this.mVersionCode = versionCode;
        this.zzafj = timeMillis;
        this.zzafk = eventType;
        this.zzafS = wakelockName;
        this.zzafX = secondaryWakeLockName;
        this.zzafT = wakelockType;
        this.zzaft = -1;
        this.zzafU = callingPackages;
        this.zzafV = eventKey;
        this.zzafr = elapsedRealtime;
        this.zzafW = deviceState;
        this.zzafY = hostPackageName;
        this.zzafZ = beginPowerPercentage;
    }

    public WakeLockEvent(long timeMillis, int eventType, String wakelockName, int wakelockType, List<String> callingPackages, String eventKey, long elapsedRealtime, int deviceState, String secondaryWakeLockName, String hostPackageName, float beginPowerPercentage) {
        this(1, timeMillis, eventType, wakelockName, wakelockType, callingPackages, eventKey, elapsedRealtime, deviceState, secondaryWakeLockName, hostPackageName, beginPowerPercentage);
    }

    public int describeContents() {
        return 0;
    }

    public int getEventType() {
        return this.zzafk;
    }

    public long getTimeMillis() {
        return this.zzafj;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzg.zza(this, out, flags);
    }

    public String zzpA() {
        return this.zzafV;
    }

    public long zzpC() {
        return this.zzafr;
    }

    public String zzpE() {
        return this.zzafS;
    }

    public String zzpF() {
        return this.zzafX;
    }

    public int zzpG() {
        return this.zzafT;
    }

    public List<String> zzpH() {
        return this.zzafU;
    }

    public int zzpI() {
        return this.zzafW;
    }

    public String zzpJ() {
        return this.zzafY;
    }

    public float zzpK() {
        return this.zzafZ;
    }
}
