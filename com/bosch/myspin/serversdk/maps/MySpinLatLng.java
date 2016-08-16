package com.bosch.myspin.serversdk.maps;

import android.location.Location;
import java.io.Serializable;

public class MySpinLatLng implements Serializable {
    private final double f98a;
    private final double f99b;

    public MySpinLatLng(Location location) {
        if (location != null) {
            this.f99b = location.getLatitude();
            this.f98a = location.getLongitude();
            return;
        }
        this.f99b = 0.0d;
        this.f98a = 0.0d;
    }

    public double getLongitude() {
        return this.f98a;
    }

    public double getLatitude() {
        return this.f99b;
    }

    public String toString() {
        return "MySpinLatLng{mLongitude=" + this.f98a + ", mLatitude=" + this.f99b + '}';
    }
}
