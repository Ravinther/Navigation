package com.bosch.myspin.serversdk.maps;

public class MySpinMapOptions {
    private int f115a;
    private int f116b;
    private int f117c;
    private boolean f118d;

    public MySpinMapOptions() {
        this.f115a = 1;
        this.f118d = true;
        this.f116b = 0;
        this.f117c = 50;
    }

    public int getMapType() {
        return this.f115a;
    }

    public int getMinZoom() {
        return this.f116b;
    }

    public int getMaxZoom() {
        return this.f117c;
    }

    public boolean getZoomControlsEnabled() {
        return this.f118d;
    }

    public String toString() {
        return "MySpinMapOptions{mMapType=" + this.f115a + ", mMinZoom=" + this.f116b + ", mMaxZoom=" + this.f117c + ", mZoomControlsEnabled=" + this.f118d + '}';
    }
}
