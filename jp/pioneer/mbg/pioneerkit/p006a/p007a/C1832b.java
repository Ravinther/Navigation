package jp.pioneer.mbg.pioneerkit.p006a.p007a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* renamed from: jp.pioneer.mbg.pioneerkit.a.a.b */
class C1832b implements Creator {
    C1832b() {
    }

    public C1831a m1472a(Parcel parcel) {
        C1831a c1831a = new C1831a();
        c1831a.f1302a = parcel.readInt();
        c1831a.f1303b = parcel.readInt();
        c1831a.f1304c = parcel.readInt();
        c1831a.f1305d = parcel.readInt();
        c1831a.f1306f = parcel.readInt();
        return c1831a;
    }

    public C1831a[] m1473a(int i) {
        return new C1831a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1472a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1473a(i);
    }
}
