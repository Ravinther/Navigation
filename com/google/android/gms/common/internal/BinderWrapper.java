package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class BinderWrapper implements Parcelable {
    public static final Creator<BinderWrapper> CREATOR;
    private IBinder zzacE;

    /* renamed from: com.google.android.gms.common.internal.BinderWrapper.1 */
    static class C06481 implements Creator<BinderWrapper> {
        C06481() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return zzad(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return zzbx(x0);
        }

        public BinderWrapper zzad(Parcel parcel) {
            return new BinderWrapper(null);
        }

        public BinderWrapper[] zzbx(int i) {
            return new BinderWrapper[i];
        }
    }

    static {
        CREATOR = new C06481();
    }

    public BinderWrapper() {
        this.zzacE = null;
    }

    public BinderWrapper(IBinder binder) {
        this.zzacE = null;
        this.zzacE = binder;
    }

    private BinderWrapper(Parcel in) {
        this.zzacE = null;
        this.zzacE = in.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.zzacE);
    }
}
