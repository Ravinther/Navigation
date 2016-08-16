package com.google.android.gms.gcm;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class PeriodicTask extends Task {
    public static final Creator<PeriodicTask> CREATOR;
    protected long mFlexInSeconds;
    protected long mIntervalInSeconds;

    /* renamed from: com.google.android.gms.gcm.PeriodicTask.1 */
    static class C08461 implements Creator<PeriodicTask> {
        C08461() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return zzej(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return zzgv(x0);
        }

        public PeriodicTask zzej(Parcel parcel) {
            return new PeriodicTask(null);
        }

        public PeriodicTask[] zzgv(int i) {
            return new PeriodicTask[i];
        }
    }

    static {
        CREATOR = new C08461();
    }

    @Deprecated
    private PeriodicTask(Parcel in) {
        super(in);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = in.readLong();
        this.mFlexInSeconds = in.readLong();
    }

    public long getFlex() {
        return this.mFlexInSeconds;
    }

    public long getPeriod() {
        return this.mIntervalInSeconds;
    }

    public String toString() {
        return super.toString() + " " + "period=" + getPeriod() + " " + "flex=" + getFlex();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeLong(this.mIntervalInSeconds);
        parcel.writeLong(this.mFlexInSeconds);
    }
}
