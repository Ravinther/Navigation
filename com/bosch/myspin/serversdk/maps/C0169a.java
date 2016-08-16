package com.bosch.myspin.serversdk.maps;

import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

/* renamed from: com.bosch.myspin.serversdk.maps.a */
final class C0169a extends MySpinMapPositionProvider {
    private static final LogComponent f129a;
    private LocationManager f130b;

    static {
        f129a = LogComponent.Maps;
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        Logger.logWarning(f129a, "Device location status changed!");
    }

    public void onProviderEnabled(String str) {
        Logger.logWarning(f129a, "Device location provider available!");
    }

    public void onProviderDisabled(String str) {
        Logger.logWarning(f129a, "No device location provider available!");
    }

    public C0169a(MySpinMapView mySpinMapView) {
        super(mySpinMapView);
        Logger.logDebug(f129a, "Creating default device location provider.");
        this.f130b = (LocationManager) mySpinMapView.getContext().getSystemService("location");
    }

    public void start() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(2);
        if (this.f130b != null) {
            this.f130b.requestLocationUpdates(100, 1.0f, criteria, this, Looper.myLooper());
        }
    }

    public void stop() {
        if (this.f130b != null) {
            this.f130b.removeUpdates(this);
        }
    }
}
