package com.bosch.myspin.serversdk.vehicledata;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Messenger;
import android.os.RemoteException;
import com.bosch.myspin.serversdk.utils.C0236e;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.d */
public class C0244d {
    private static final LogComponent f446a;
    private static C0244d f447f;
    private final C0243c f448b;
    private C0239a f449c;
    private final Messenger f450d;
    private boolean f451e;
    private ServiceConnection f452g;

    static {
        f446a = LogComponent.EventListener;
    }

    public static C0244d m391a() {
        if (f447f == null) {
            f447f = new C0244d();
        }
        return f447f;
    }

    private C0244d() {
        this.f452g = new C0245e(this);
        this.f448b = new C0243c();
        this.f450d = new Messenger(this.f448b);
    }

    public void m395a(Context context) {
        if (!this.f451e) {
            try {
                this.f451e = context.bindService(C0236e.m368a(context, new Intent("com.bosch.myspin.ACTION_BIND_VEHICLEDATA_REGISTRATION_V13X")), this.f452g, 1);
            } catch (Throwable e) {
                Logger.logError(f446a, "VehicleDataMessengerRegistration/Cant bind service, make sure that only one launcher app is installed", e);
            } catch (Throwable e2) {
                Logger.logWarning(f446a, "VehicleDataMessengerRegistration/Cant bind mySPIN service, make sure that a launcher app is installed.", e2);
            }
        }
    }

    public void m397b(Context context) {
        if (this.f451e && this.f449c != null) {
            try {
                this.f449c.m376b(this.f450d.getBinder());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            context.unbindService(this.f452g);
            this.f451e = false;
        }
    }

    public C0243c m396b() {
        return this.f448b;
    }
}
