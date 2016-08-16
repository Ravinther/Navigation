package com.bosch.myspin.serversdk.maps;

import java.util.ArrayList;

public class MySpinDirectionsOptions {
    private MySpinBitmapDescriptor f95a;
    private ArrayList<MySpinLatLng> f96b;

    public ArrayList<MySpinLatLng> getStopovers() {
        return this.f96b;
    }

    public MySpinBitmapDescriptor getIcon() {
        return this.f95a;
    }
}
