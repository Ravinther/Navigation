package jp.pioneer.mbg.pioneerkit.p006a.p007a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* renamed from: jp.pioneer.mbg.pioneerkit.a.a.a */
public class C1831a implements Parcelable {
    public static final Creator f1301e;
    public int f1302a;
    public int f1303b;
    public int f1304c;
    public int f1305d;
    private int f1306f;

    static {
        f1301e = new C1832b();
    }

    public C1831a() {
        this.f1302a = 0;
        this.f1306f = 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1302a);
        parcel.writeInt(this.f1303b);
        parcel.writeInt(this.f1304c);
        parcel.writeInt(this.f1305d);
        parcel.writeInt(this.f1306f);
    }
}
