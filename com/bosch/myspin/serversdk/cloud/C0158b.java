package com.bosch.myspin.serversdk.cloud;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.bosch.myspin.serversdk.cloud.C0159c.C0161a;
import com.bosch.myspin.serversdk.utils.Logger;
import java.util.Map;

/* renamed from: com.bosch.myspin.serversdk.cloud.b */
class C0158b implements ServiceConnection {
    final /* synthetic */ C0157a f71a;

    C0158b(C0157a c0157a) {
        this.f71a = c0157a;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Logger.logDebug(C0157a.f65a, "CloudServiceClient/service is connected");
        this.f71a.f68d = C0161a.m86a(iBinder);
        if (!this.f71a.f69e.isEmpty()) {
            Logger.logDebug(C0157a.f65a, "CloudServiceClient/sending cached events!");
            for (Map a : this.f71a.f69e) {
                this.f71a.m77a(a);
            }
            this.f71a.f69e.clear();
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Logger.logDebug(C0157a.f65a, "CloudServiceClient/service is disconnected");
        this.f71a.f68d = null;
    }
}
