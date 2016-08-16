package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.List;

public class ActivityRecognitionResult implements SafeParcelable {
    public static final ActivityRecognitionResultCreator CREATOR;
    private final int mVersionCode;
    List<DetectedActivity> zzaBt;
    long zzaBu;
    long zzaBv;
    int zzaBw;

    static {
        CREATOR = new ActivityRecognitionResultCreator();
    }

    public ActivityRecognitionResult(int versionCode, List<DetectedActivity> probableActivities, long timeMillis, long elapsedRealtimeMillis, int detectorInfoId) {
        this.mVersionCode = versionCode;
        this.zzaBt = probableActivities;
        this.zzaBu = timeMillis;
        this.zzaBv = elapsedRealtimeMillis;
        this.zzaBw = detectorInfoId;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityRecognitionResult activityRecognitionResult = (ActivityRecognitionResult) o;
        return this.zzaBu == activityRecognitionResult.zzaBu && this.zzaBv == activityRecognitionResult.zzaBv && this.zzaBw == activityRecognitionResult.zzaBw && zzw.equal(this.zzaBt, activityRecognitionResult.zzaBt);
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return zzw.hashCode(Long.valueOf(this.zzaBu), Long.valueOf(this.zzaBv), Integer.valueOf(this.zzaBw), this.zzaBt);
    }

    public String toString() {
        return "ActivityRecognitionResult [probableActivities=" + this.zzaBt + ", timeMillis=" + this.zzaBu + ", elapsedRealtimeMillis=" + this.zzaBv + "]";
    }

    public void writeToParcel(Parcel out, int flags) {
        ActivityRecognitionResultCreator.zza(this, out, flags);
    }
}
