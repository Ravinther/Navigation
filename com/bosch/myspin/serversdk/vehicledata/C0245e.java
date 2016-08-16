package com.bosch.myspin.serversdk.vehicledata;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.vehicledata.C0239a.C0241a;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.e */
class C0245e implements ServiceConnection {
    final /* synthetic */ C0244d f453a;

    C0245e(C0244d c0244d) {
        this.f453a = c0244d;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Logger.logDebug(C0244d.f446a, "VehicleDataMessengerRegistration/service is connected");
        this.f453a.f449c = C0241a.m379c(iBinder);
        try {
            this.f453a.f449c.m375a(this.f453a.f450d.getBinder());
        } catch (Throwable e) {
            Logger.logWarning(C0244d.f446a, "VehicleDataMessengerRegistration/While register this client as VehicleDataMessengerRegistration", e);
        }
        this.f453a.f451e = true;
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Logger.logDebug(C0244d.f446a, "VehicleDataMessengerRegistration/service is disconnected");
        this.f453a.f449c = null;
        this.f453a.f451e = false;
    }
}
