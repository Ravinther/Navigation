package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.Comparator;
import loquendo.tts.engine.TTSConst;

public class DetectedActivity implements SafeParcelable {
    public static final DetectedActivityCreator CREATOR;
    public static final Comparator<DetectedActivity> zzaBx;
    private final int mVersionCode;
    int zzaBy;
    int zzaBz;

    /* renamed from: com.google.android.gms.location.DetectedActivity.1 */
    static class C09871 implements Comparator<DetectedActivity> {
        C09871() {
        }

        public /* synthetic */ int compare(Object x0, Object x1) {
            return zza((DetectedActivity) x0, (DetectedActivity) x1);
        }

        public int zza(DetectedActivity detectedActivity, DetectedActivity detectedActivity2) {
            int compareTo = Integer.valueOf(detectedActivity2.getConfidence()).compareTo(Integer.valueOf(detectedActivity.getConfidence()));
            return compareTo == 0 ? Integer.valueOf(detectedActivity.getType()).compareTo(Integer.valueOf(detectedActivity2.getType())) : compareTo;
        }
    }

    static {
        zzaBx = new C09871();
        CREATOR = new DetectedActivityCreator();
    }

    public DetectedActivity(int versionCode, int activityType, int confidence) {
        this.mVersionCode = versionCode;
        this.zzaBy = activityType;
        this.zzaBz = confidence;
    }

    private int zzgB(int i) {
        return i > 15 ? 4 : i;
    }

    public static String zzgC(int i) {
        switch (i) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "IN_VEHICLE";
            case TTSConst.TTSMULTILINE /*1*/:
                return "ON_BICYCLE";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "ON_FOOT";
            case TTSConst.TTSUNICODE /*3*/:
                return "STILL";
            case TTSConst.TTSXML /*4*/:
                return "UNKNOWN";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "TILTING";
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return "WALKING";
            case TTSConst.TTSEVT_TAG /*8*/:
                return "RUNNING";
            default:
                return Integer.toString(i);
        }
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
        DetectedActivity detectedActivity = (DetectedActivity) o;
        return this.zzaBy == detectedActivity.zzaBy && this.zzaBz == detectedActivity.zzaBz;
    }

    public int getConfidence() {
        return this.zzaBz;
    }

    public int getType() {
        return zzgB(this.zzaBy);
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.zzaBy), Integer.valueOf(this.zzaBz));
    }

    public String toString() {
        return "DetectedActivity [type=" + zzgC(getType()) + ", confidence=" + this.zzaBz + "]";
    }

    public void writeToParcel(Parcel out, int flags) {
        DetectedActivityCreator.zza(this, out, flags);
    }
}
