package jp.pioneer.mbg.pioneerkit.p006a.p007a;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* renamed from: jp.pioneer.mbg.pioneerkit.a.a.d */
class C1834d implements Creator {
    C1834d() {
    }

    public C1833c m1480a(Parcel parcel) {
        C1833c c1833c = new C1833c();
        c1833c.f1309c = parcel.readDouble();
        c1833c.f1310d = parcel.readDouble();
        c1833c.f1312f = parcel.readDouble();
        c1833c.f1316j = parcel.readFloat();
        c1833c.f1314h = parcel.readFloat();
        c1833c.f1308a = parcel.readByte();
        c1833c.f1318l = parcel.readLong();
        return c1833c;
    }

    public C1833c[] m1481a(int i) {
        return new C1833c[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1480a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1481a(i);
    }
}
