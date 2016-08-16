package com.bosch.myspin.serversdk.service.client;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.bosch.myspin.serversdk.service.C0178a.C0180a;
import com.bosch.myspin.serversdk.utils.Logger;

/* renamed from: com.bosch.myspin.serversdk.service.client.p */
class C0205p implements ServiceConnection {
    final /* synthetic */ C0194j f272a;

    C0205p(C0194j c0194j) {
        this.f272a = c0194j;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            Logger.logDebug(C0194j.f182a, "MySpinServiceClient/onServiceConnected, with null binder");
            return;
        }
        Logger.logDebug(C0194j.f182a, "MySpinServiceClient/onServiceConnected");
        this.f272a.f201T = C0180a.m151a(iBinder);
        if (this.f272a.f232z != null) {
            this.f272a.m263d();
        }
        try {
            if (this.f272a.f201T.m144b()) {
                this.f272a.f200S.m174a(C0185b.CONNECTED);
            } else {
                this.f272a.f200S.m174a(C0185b.DISCONNECTED);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (this.f272a.f204W) {
            this.f272a.f202U.m83a(this.f272a.f232z, true);
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f272a.f201T = null;
        this.f272a.f227u = null;
        if (this.f272a.f208b) {
            this.f272a.f209c.unbindService(this);
            this.f272a.f208b = false;
        }
        this.f272a.f200S.m174a(C0185b.DISCONNECTED);
        this.f272a.m243s();
    }
}
