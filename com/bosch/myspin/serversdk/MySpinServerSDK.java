package com.bosch.myspin.serversdk;

import android.app.Application;
import android.os.Build.VERSION;
import android.view.SurfaceView;
import com.bosch.myspin.serversdk.service.client.C0194j;
import com.bosch.myspin.serversdk.service.client.opengl.MySpinSurfaceViewHandle;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.vehicledata.C0244d;

public final class MySpinServerSDK {
    static final LogComponent f59a;
    private static MySpinServerSDK f60b;
    private C0194j f61c;

    public interface ConnectionStateListener {
        void onConnectionStateChanged(boolean z);
    }

    public interface VoiceControlListener {
        void onVoiceControlStateChanged(int i, int i2);
    }

    static {
        f59a = LogComponent.SDKMain;
        try {
            System.loadLibrary("myspinnative");
        } catch (UnsatisfiedLinkError e) {
            if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
                throw e;
            }
        }
    }

    private MySpinServerSDK() {
        String str = "MySpinServerSDK version [1.3.0" + ".86";
        if (!"".equals("")) {
            str = str + "-";
        }
        Logger.logInfo(f59a, "MySpinServerSDK/" + (str + "]"));
    }

    public static MySpinServerSDK sharedInstance() {
        if (f60b != null) {
            return f60b;
        }
        f60b = new MySpinServerSDK();
        return f60b;
    }

    public boolean isConnected() {
        boolean z = false;
        if (this.f61c != null) {
            z = this.f61c.m264e();
        }
        Logger.logDebug(f59a, "MySpinServerSDK/isConnected(): " + z);
        return z;
    }

    @Deprecated
    public void registerApplication(Application application, ConnectionStateListener connectionStateListener) throws MySpinException {
        registerApplication(application);
        if (this.f61c != null && connectionStateListener != null) {
            this.f61c.m258a().m173a(connectionStateListener);
        }
    }

    public void registerApplication(Application application) throws MySpinException {
        if (application == null) {
            throw new IllegalArgumentException("applicationContext must no be null");
        } else if (VERSION.SDK_INT < 14) {
            Logger.logWarning(f59a, "MySpinServerSDK/Application not registered because Android version is not supported.");
        } else {
            Logger.logInfo(f59a, "MySpinServerSDK/registerApplication(" + application.getPackageName() + ")");
            if (this.f61c == null) {
                this.f61c = new C0194j(application);
            }
        }
    }

    public MySpinSurfaceViewHandle registerSurfaceView(SurfaceView surfaceView) {
        Logger.logDebug(f59a, "MySpinServerSDK/registerSurfaceView(" + surfaceView + ")");
        return this.f61c.m259a(surfaceView);
    }

    public void unregisterSurfaceView(SurfaceView surfaceView) {
        Logger.logDebug(f59a, "MySpinServerSDK/unregisterSurfaceView(" + surfaceView + ")");
        this.f61c.m260b(surfaceView);
    }

    public boolean hasPositionInformationCapability() throws MySpinException {
        m66a();
        boolean g = this.f61c.m265g();
        Logger.logDebug(f59a, "MySpinServerSDK/hasPositionInformationCapability(): " + g);
        return g;
    }

    @Deprecated
    public void registerCarDataChangedListener(OnCarDataChangeListener onCarDataChangeListener) throws MySpinException {
        m67a("registerCarDataChangedListener");
        Logger.logDebug(f59a, "MySpinServerSDK/registerCarDataChangedListener(" + onCarDataChangeListener + ")");
        C0244d.m391a().m396b().m384a(onCarDataChangeListener);
    }

    @Deprecated
    public void unregisterCarDataChangedListener(OnCarDataChangeListener onCarDataChangeListener) throws MySpinException {
        m67a("unregisterCarDataChangedListener");
        Logger.logDebug(f59a, "MySpinServerSDK/unregisterCarDataChangedListener(" + onCarDataChangeListener + ")");
        C0244d.m391a().m396b().m387b(onCarDataChangeListener);
    }

    public void registerVehicleDataListenerForKey(VehicleDataListener vehicleDataListener, long j) throws MySpinException {
        m67a("registerVehicleDataListenerForKey");
        Logger.logDebug(f59a, "MySpinServerSDK/registerVehicleDataListenerForKey(" + vehicleDataListener + ", " + j + ")");
        C0244d.m391a().m396b().m386a(vehicleDataListener, j);
    }

    public void unregisterVehicleDataListener(VehicleDataListener vehicleDataListener) throws MySpinException {
        m67a("unregisterVehicleDataListener");
        Logger.logDebug(f59a, "MySpinServerSDK/unregisterVehicleDataListener(" + vehicleDataListener + ")");
        C0244d.m391a().m396b().m385a(vehicleDataListener);
    }

    private void m66a() throws MySpinException {
        if (this.f61c == null || !this.f61c.m266j()) {
            throw new MySpinException("mySPIN Service not bound");
        }
    }

    private void m67a(String str) throws MySpinException {
        if (this.f61c == null) {
            throw new MySpinException("The application must be registered using #registerApplication(Application applicationContext) before calling: " + str);
        }
    }
}
