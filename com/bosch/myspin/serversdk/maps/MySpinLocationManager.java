package com.bosch.myspin.serversdk.maps;

import android.location.Location;
import com.bosch.myspin.serversdk.MySpinServerSDK;
import com.bosch.myspin.serversdk.VehicleDataListener;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.vehicledata.MySpinVehicleData;
import com.bosch.myspin.serversdk.vehicledata.nmea.MySpinLocationFactory;

public class MySpinLocationManager {
    private static final LogComponent f102a;
    private MySpinMapView f103b;
    private boolean f104c;
    private Location f105d;
    private C0168a f106e;
    private MySpinMapPositionProvider f107f;
    private boolean f108g;

    /* renamed from: com.bosch.myspin.serversdk.maps.MySpinLocationManager.a */
    final class C0168a implements VehicleDataListener {
        final /* synthetic */ MySpinLocationManager f100a;
        private boolean f101b;

        public C0168a(MySpinLocationManager mySpinLocationManager) {
            this.f100a = mySpinLocationManager;
        }

        protected void m116a() {
            this.f101b = true;
        }

        protected void m117b() {
            this.f101b = false;
        }

        private boolean m114c() {
            try {
                Logger.logDebug(MySpinLocationManager.f102a, "MySpinLocationManager/registering location listener");
                MySpinServerSDK.sharedInstance().registerVehicleDataListenerForKey(this, 1);
                return true;
            } catch (Throwable e) {
                Logger.logError(MySpinLocationManager.f102a, "MySpinLocationManager/While registering location listener", e);
                return false;
            }
        }

        private boolean m115d() {
            try {
                MySpinServerSDK.sharedInstance().unregisterVehicleDataListener(this);
                return true;
            } catch (Throwable e) {
                Logger.logError(MySpinLocationManager.f102a, "MySpinLocationManager/While unregistering location listener", e);
                return false;
            }
        }

        public void onVehicleDataUpdate(long j, MySpinVehicleData mySpinVehicleData) {
            Object obj = null;
            if (j != 1 || mySpinVehicleData == null || mySpinVehicleData.containsKey("status")) {
                Logger.logWarning(MySpinLocationManager.f102a, "MySpinLocationManager/onVehicleDataUpdate not valid for key: " + j + " with value: " + mySpinVehicleData + " mIviGpsEnabled: " + this.f101b);
                return;
            }
            try {
                Location parseNmea = MySpinLocationFactory.parseNmea((String) mySpinVehicleData.get("value"));
                Object obj2;
                if (this.f100a.f105d != null) {
                    Object obj3 = ((double) parseNmea.distanceTo(this.f100a.f105d)) > 0.5d ? 1 : null;
                    if (Math.abs(parseNmea.getBearing() - this.f100a.f105d.getBearing()) > 0.5f) {
                        obj2 = 1;
                    } else {
                        obj2 = null;
                    }
                    obj = obj3;
                } else {
                    obj2 = null;
                }
                if (this.f100a.f105d == null || r0 != null || r2 != null) {
                    Logger.logDebug(MySpinLocationManager.f102a, "MySpinLocationManager/receiving new location update: " + mySpinVehicleData);
                    this.f100a.f105d = parseNmea;
                    this.f100a.f103b.onLocationChanged(parseNmea);
                }
            } catch (Throwable e) {
                Logger.logError(MySpinLocationManager.f102a, "MySpinLocationManager/Can't parse NMEA string", e);
            }
        }
    }

    static {
        f102a = LogComponent.Maps;
    }

    protected MySpinLocationManager(MySpinMapView mySpinMapView) {
        this.f103b = mySpinMapView;
    }

    protected void setMyLocationEnabled(boolean z) {
        this.f104c = z;
        if (this.f104c) {
            m122b();
            return;
        }
        m123c();
        MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinRemoveLocation()");
    }

    private void m122b() {
        Logger.logDebug(f102a, "MySpinLocationManager/Start all location providers");
        try {
            this.f108g = MySpinServerSDK.sharedInstance().hasPositionInformationCapability();
        } catch (Throwable e) {
            Logger.logWarning(f102a, "PositionInformation not yet available!", e);
        }
        if (this.f108g) {
            Logger.logDebug(f102a, "MySpinLocationManager/Start IVI location provider");
            if (this.f106e == null) {
                this.f106e = new C0168a(this);
                if (this.f106e.m114c()) {
                    this.f106e.m116a();
                    return;
                } else {
                    Logger.logError(f102a, "MySpinLocationManager/unable to register IVI location listener");
                    return;
                }
            }
            this.f106e.m116a();
            return;
        }
        Logger.logDebug(f102a, "MySpinLocationManager/No IVI position information available! Starting device location provider.");
        if (this.f107f == null) {
            this.f107f = new C0169a(this.f103b);
        }
        this.f107f.start();
    }

    private void m123c() {
        Logger.logDebug(f102a, "MySpinLocationManager/Stop all location providers");
        if (this.f106e != null) {
            this.f106e.m117b();
            if (this.f106e.m115d()) {
                this.f106e.m117b();
            } else {
                Logger.logWarning(f102a, "MySpinLocationManager/not able to unregister IVI location listener");
            }
            this.f106e = null;
        }
        if (this.f107f != null) {
            this.f107f.stop();
        }
    }

    public void setMapLocationProvider(MySpinMapPositionProvider mySpinMapPositionProvider) {
        this.f107f = mySpinMapPositionProvider;
    }
}
