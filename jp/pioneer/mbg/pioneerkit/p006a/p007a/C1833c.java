package jp.pioneer.mbg.pioneerkit.p006a.p007a;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* renamed from: jp.pioneer.mbg.pioneerkit.a.a.c */
public class C1833c implements Parcelable {
    public static final Creator f1307b;
    byte f1308a;
    private double f1309c;
    private double f1310d;
    private boolean f1311e;
    private double f1312f;
    private boolean f1313g;
    private float f1314h;
    private boolean f1315i;
    private float f1316j;
    private boolean f1317k;
    private long f1318l;

    static {
        f1307b = new C1834d();
    }

    public C1833c() {
        this.f1309c = 0.0d;
        this.f1310d = 0.0d;
        this.f1311e = false;
        this.f1312f = 0.0d;
        this.f1313g = false;
        this.f1314h = 0.0f;
        this.f1315i = false;
        this.f1316j = 0.0f;
        this.f1317k = false;
        this.f1318l = 0;
        this.f1308a = (byte) 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.f1309c);
        parcel.writeDouble(this.f1310d);
        parcel.writeDouble(this.f1312f);
        parcel.writeFloat(this.f1316j);
        parcel.writeFloat(this.f1314h);
        parcel.writeByte(this.f1308a);
        parcel.writeLong(this.f1318l);
    }
}
