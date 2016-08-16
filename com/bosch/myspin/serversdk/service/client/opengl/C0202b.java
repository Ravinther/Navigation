package com.bosch.myspin.serversdk.service.client.opengl;

import android.opengl.GLSurfaceView;

/* renamed from: com.bosch.myspin.serversdk.service.client.opengl.b */
class C0202b implements Runnable {
    final /* synthetic */ MySpinSurfaceViewHandle f264a;

    C0202b(MySpinSurfaceViewHandle mySpinSurfaceViewHandle) {
        this.f264a = mySpinSurfaceViewHandle;
    }

    public void run() {
        if (this.f264a.f257f) {
            this.f264a.m282c();
            return;
        }
        this.f264a.m279b();
        ((GLSurfaceView) this.f264a.f255d).queueEvent(this.f264a.f260i);
    }
}
