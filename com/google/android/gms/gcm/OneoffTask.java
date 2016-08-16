package com.google.android.gms.gcm;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class OneoffTask extends Task {
    public static final Creator<OneoffTask> CREATOR;
    private final long zzaAb;
    private final long zzaAc;

    /* renamed from: com.google.android.gms.gcm.OneoffTask.1 */
    static class C08441 implements Creator<OneoffTask> {
        C08441() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return zzeh(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return zzgt(x0);
        }

        public OneoffTask zzeh(Parcel parcel) {
            return new OneoffTask(null);
        }

        public OneoffTask[] zzgt(int i) {
            return new OneoffTask[i];
        }
    }

    static {
        CREATOR = new C08441();
    }

    @Deprecated
    private OneoffTask(Parcel in) {
        super(in);
        this.zzaAb = in.readLong();
        this.zzaAc = in.readLong();
    }

    public long getWindowEnd() {
        return this.zzaAc;
    }

    public long getWindowStart() {
        return this.zzaAb;
    }

    public String toString() {
        return super.toString() + " " + "windowStart=" + getWindowStart() + " " + "windowEnd=" + getWindowEnd();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeLong(this.zzaAb);
        parcel.writeLong(this.zzaAc);
    }
}
