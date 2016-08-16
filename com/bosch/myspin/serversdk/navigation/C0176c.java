package com.bosch.myspin.serversdk.navigation;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.bosch.myspin.serversdk.navigation.C0172a.C0174a;
import com.bosch.myspin.serversdk.utils.Logger;

/* renamed from: com.bosch.myspin.serversdk.navigation.c */
class C0176c implements ServiceConnection {
    final /* synthetic */ C0175b f141a;

    C0176c(C0175b c0175b) {
        this.f141a = c0175b;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Logger.logDebug(C0175b.f135a, "MySpinNavigationManager/service is connected");
        this.f141a.f137c = C0174a.m133a(iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Logger.logDebug(C0175b.f135a, "MySpinNavigationManager/service is disconnected");
        this.f141a.f137c = null;
    }
}
