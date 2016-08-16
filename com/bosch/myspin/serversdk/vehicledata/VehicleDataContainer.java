package com.bosch.myspin.serversdk.vehicledata;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class VehicleDataContainer implements Parcelable {
    public static final Creator<VehicleDataContainer> CREATOR;
    private boolean f437a;
    private long f438b;

    static {
        CREATOR = new C0242b();
    }

    private VehicleDataContainer(Parcel parcel) {
        boolean[] zArr = new boolean[1];
        parcel.readBooleanArray(zArr);
        this.f437a = zArr[0];
        this.f438b = parcel.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBooleanArray(new boolean[]{this.f437a});
        parcel.writeLong(this.f438b);
    }

    public long m374a() {
        return this.f438b;
    }
}
