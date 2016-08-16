package com.google.android.gms.gcm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PendingCallback implements Parcelable {
    public static final Creator<PendingCallback> CREATOR;
    final IBinder zzacE;

    /* renamed from: com.google.android.gms.gcm.PendingCallback.1 */
    static class C08451 implements Creator<PendingCallback> {
        C08451() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return zzei(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return zzgu(x0);
        }

        public PendingCallback zzei(Parcel parcel) {
            return new PendingCallback(parcel);
        }

        public PendingCallback[] zzgu(int i) {
            return new PendingCallback[i];
        }
    }

    static {
        CREATOR = new C08451();
    }

    public PendingCallback(Parcel in) {
        this.zzacE = in.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzacE);
    }
}
