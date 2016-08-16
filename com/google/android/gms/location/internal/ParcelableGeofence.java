package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Locale;
import loquendo.tts.engine.TTSConst;

public class ParcelableGeofence implements SafeParcelable {
    public static final zzm CREATOR;
    private final int mVersionCode;
    private final String zzBm;
    private final int zzaBA;
    private final short zzaBC;
    private final double zzaBD;
    private final double zzaBE;
    private final float zzaBF;
    private final int zzaBG;
    private final int zzaBH;
    private final long zzaDd;

    static {
        CREATOR = new zzm();
    }

    public ParcelableGeofence(int version, String requestId, int transitionTypes, short type, double latitude, double longitude, float radius, long expireAt, int notificationResponsiveness, int loiteringDelayMillis) {
        zzdy(requestId);
        zze(radius);
        zza(latitude, longitude);
        transitionTypes = zzgT(transitionTypes);
        this.mVersionCode = version;
        this.zzaBC = type;
        this.zzBm = requestId;
        this.zzaBD = latitude;
        this.zzaBE = longitude;
        this.zzaBF = radius;
        this.zzaDd = expireAt;
        this.zzaBA = transitionTypes;
        this.zzaBG = notificationResponsiveness;
        this.zzaBH = loiteringDelayMillis;
    }

    private static void zza(double d, double d2) {
        if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException("invalid latitude: " + d);
        } else if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException("invalid longitude: " + d2);
        }
    }

    private static void zzdy(String str) {
        if (str == null || str.length() > 100) {
            throw new IllegalArgumentException("requestId is null or too long: " + str);
        }
    }

    private static void zze(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("invalid radius: " + f);
        }
    }

    private static int zzgT(int i) {
        int i2 = i & 7;
        if (i2 != 0) {
            return i2;
        }
        throw new IllegalArgumentException("No supported transition specified: " + i);
    }

    private static String zzgU(int i) {
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
                return "CIRCLE";
            default:
                return null;
        }
    }

    public int describeContents() {
        zzm com_google_android_gms_location_internal_zzm = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ParcelableGeofence)) {
            return false;
        }
        ParcelableGeofence parcelableGeofence = (ParcelableGeofence) obj;
        if (this.zzaBF != parcelableGeofence.zzaBF) {
            return false;
        }
        if (this.zzaBD != parcelableGeofence.zzaBD) {
            return false;
        }
        if (this.zzaBE != parcelableGeofence.zzaBE) {
            return false;
        }
        return this.zzaBC == parcelableGeofence.zzaBC;
    }

    public long getExpirationTime() {
        return this.zzaDd;
    }

    public double getLatitude() {
        return this.zzaBD;
    }

    public double getLongitude() {
        return this.zzaBE;
    }

    public int getNotificationResponsiveness() {
        return this.zzaBG;
    }

    public String getRequestId() {
        return this.zzBm;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzaBD);
        int i = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31;
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzaBE);
        return (((((((i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.zzaBF)) * 31) + this.zzaBC) * 31) + this.zzaBA;
    }

    public String toString() {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[]{zzgU(this.zzaBC), this.zzBm, Integer.valueOf(this.zzaBA), Double.valueOf(this.zzaBD), Double.valueOf(this.zzaBE), Float.valueOf(this.zzaBF), Integer.valueOf(this.zzaBG / 1000), Integer.valueOf(this.zzaBH), Long.valueOf(this.zzaDd)});
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzm com_google_android_gms_location_internal_zzm = CREATOR;
        zzm.zza(this, parcel, flags);
    }

    public short zzvU() {
        return this.zzaBC;
    }

    public float zzvV() {
        return this.zzaBF;
    }

    public int zzvW() {
        return this.zzaBA;
    }

    public int zzvX() {
        return this.zzaBH;
    }
}
