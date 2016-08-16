package com.bosch.myspin.serversdk.service.client;

import android.view.View;
import android.view.ViewGroup;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

/* renamed from: com.bosch.myspin.serversdk.service.client.r */
class C0207r implements Runnable {
    final /* synthetic */ C0194j f274a;

    C0207r(C0194j c0194j) {
        this.f274a = c0194j;
    }

    public void run() {
        this.f274a.f200S.m174a(C0185b.CONNECTED);
        this.f274a.f188F.m107d();
        if (this.f274a.f232z != null) {
            View rootView = this.f274a.f232z.getWindow().getDecorView().getRootView();
            if (!this.f274a.f190H.m293a() && (rootView instanceof ViewGroup)) {
                this.f274a.f190H.m292a((ViewGroup) rootView, this.f274a.f232z);
                return;
            }
            return;
        }
        Logger.logDebug(LogComponent.UI, "MySpinServiceClient/activity is not started yet, GlSurfaceView will be added in onActivityStarted");
    }
}
