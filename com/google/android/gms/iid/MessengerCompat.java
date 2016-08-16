package com.google.android.gms.iid;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.iid.zzb.zza;

public class MessengerCompat implements Parcelable {
    public static final Creator<MessengerCompat> CREATOR;
    Messenger zzaBc;
    zzb zzaBd;

    /* renamed from: com.google.android.gms.iid.MessengerCompat.1 */
    static class C08471 implements Creator<MessengerCompat> {
        C08471() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return zzen(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return zzgA(x0);
        }

        public MessengerCompat zzen(Parcel parcel) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            return readStrongBinder != null ? new MessengerCompat(readStrongBinder) : null;
        }

        public MessengerCompat[] zzgA(int i) {
            return new MessengerCompat[i];
        }
    }

    static {
        CREATOR = new C08471();
    }

    public MessengerCompat(IBinder target) {
        if (VERSION.SDK_INT >= 21) {
            this.zzaBc = new Messenger(target);
        } else {
            this.zzaBd = zza.zzbV(target);
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object otherObj) {
        boolean z = false;
        if (otherObj != null) {
            try {
                z = getBinder().equals(((MessengerCompat) otherObj).getBinder());
            } catch (ClassCastException e) {
            }
        }
        return z;
    }

    public IBinder getBinder() {
        return this.zzaBc != null ? this.zzaBc.getBinder() : this.zzaBd.asBinder();
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        if (this.zzaBc != null) {
            out.writeStrongBinder(this.zzaBc.getBinder());
        } else {
            out.writeStrongBinder(this.zzaBd.asBinder());
        }
    }
}
