package com.bosch.myspin.serversdk.maps;

import android.location.Location;
import android.location.LocationListener;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

public abstract class MySpinMapPositionProvider implements LocationListener {
    private static final LogComponent f119a;
    private MySpinMapView f120b;

    public abstract void start();

    public abstract void stop();

    static {
        f119a = LogComponent.Maps;
    }

    public void onLocationChanged(Location location) {
        updatePosition(location);
    }

    public MySpinMapPositionProvider(MySpinMapView mySpinMapView) {
        if (mySpinMapView == null) {
            throw new IllegalArgumentException("MySpinMapView must not be null!");
        }
        this.f120b = mySpinMapView;
    }

    public void updatePosition(Location location) {
        Logger.logDebug(f119a, "MySpinMapPositionProvider/updatePosition");
        this.f120b.onLocationChanged(location);
    }
}
