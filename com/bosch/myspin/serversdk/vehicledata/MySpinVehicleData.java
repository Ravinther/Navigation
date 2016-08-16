package com.bosch.myspin.serversdk.vehicledata;

import android.os.Bundle;

public class MySpinVehicleData {
    private long f435a;
    private final Bundle f436b;

    MySpinVehicleData(long j, Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle is null!");
        }
        this.f435a = j;
        this.f436b = bundle;
    }

    public boolean containsKey(String str) {
        return this.f436b.containsKey(str);
    }

    public Object get(String str) {
        return this.f436b.get(str);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Object obj = null;
        for (String str : this.f436b.keySet()) {
            stringBuilder.append(str).append(": ").append(this.f436b.get(str)).append(",");
            int i = 1;
        }
        if (obj != null) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
