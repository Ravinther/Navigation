package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ConnectionEvent implements SafeParcelable {
    public static final Creator<ConnectionEvent> CREATOR;
    final int mVersionCode;
    private final long zzafj;
    private int zzafk;
    private final String zzafl;
    private final String zzafm;
    private final String zzafn;
    private final String zzafo;
    private final String zzafp;
    private final String zzafq;
    private final long zzafr;
    private final long zzafs;
    private long zzaft;

    static {
        CREATOR = new zza();
    }

    ConnectionEvent(int versionCode, long timeMillis, int eventType, String callingProcess, String callingService, String targetProcess, String targetService, String stackTrace, String connKey, long elapsedRealtime, long heapAlloc) {
        this.mVersionCode = versionCode;
        this.zzafj = timeMillis;
        this.zzafk = eventType;
        this.zzafl = callingProcess;
        this.zzafm = callingService;
        this.zzafn = targetProcess;
        this.zzafo = targetService;
        this.zzaft = -1;
        this.zzafp = stackTrace;
        this.zzafq = connKey;
        this.zzafr = elapsedRealtime;
        this.zzafs = heapAlloc;
    }

    public ConnectionEvent(long timeMillis, int eventType, String callingProcess, String callingService, String targetProcess, String targetService, String stackTrace, String connKey, long elapsedRealtime, long heapAlloc) {
        this(1, timeMillis, eventType, callingProcess, callingService, targetProcess, targetService, stackTrace, connKey, elapsedRealtime, heapAlloc);
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
        zza.zza(this, out, flags);
    }

    public String zzpA() {
        return this.zzafq;
    }

    public long zzpB() {
        return this.zzafs;
    }

    public long zzpC() {
        return this.zzafr;
    }

    public String zzpv() {
        return this.zzafl;
    }

    public String zzpw() {
        return this.zzafm;
    }

    public String zzpx() {
        return this.zzafn;
    }

    public String zzpy() {
        return this.zzafo;
    }

    public String zzpz() {
        return this.zzafp;
    }
}
