package com.flurry.sdk;

public enum iy {
    DeviceId(0, true),
    Sha1Imei(5, false),
    AndroidAdvertisingId(13, true);
    
    public final int f871d;
    public final boolean f872e;

    private iy(int i, boolean z) {
        this.f871d = i;
        this.f872e = z;
    }
}
