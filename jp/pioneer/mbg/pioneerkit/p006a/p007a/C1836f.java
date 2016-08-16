package jp.pioneer.mbg.pioneerkit.p006a.p007a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* renamed from: jp.pioneer.mbg.pioneerkit.a.a.f */
class C1836f implements Creator {
    C1836f() {
    }

    public C1835e m1482a(Parcel parcel) {
        C1835e c1835e = new C1835e();
        c1835e.f1320a = parcel.readInt();
        c1835e.f1321b = parcel.readInt();
        return c1835e;
    }

    public C1835e[] m1483a(int i) {
        return new C1835e[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1482a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1483a(i);
    }
}
