package com.bosch.myspin.serversdk.vehicledata;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.b */
final class C0242b implements Creator<VehicleDataContainer> {
    C0242b() {
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m380a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m381a(i);
    }

    public VehicleDataContainer m380a(Parcel parcel) {
        return new VehicleDataContainer(null);
    }

    public VehicleDataContainer[] m381a(int i) {
        return new VehicleDataContainer[i];
    }
}
