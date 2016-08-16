package jp.pioneer.mbg.pioneerkit.p006a.p007a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* renamed from: jp.pioneer.mbg.pioneerkit.a.a.e */
public class C1835e implements Parcelable {
    public static final Creator f1319c;
    public int f1320a;
    public int f1321b;

    static {
        f1319c = new C1836f();
    }

    public C1835e() {
        this.f1320a = 0;
        this.f1321b = 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1320a);
        parcel.writeInt(this.f1321b);
    }
}
