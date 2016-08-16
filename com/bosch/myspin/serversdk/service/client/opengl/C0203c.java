package com.bosch.myspin.serversdk.service.client.opengl;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;

/* renamed from: com.bosch.myspin.serversdk.service.client.opengl.c */
class C0203c implements Runnable {
    final /* synthetic */ Bitmap f265a;
    final /* synthetic */ GLSurfaceView f266b;
    final /* synthetic */ MySpinSurfaceViewHandle f267c;

    C0203c(MySpinSurfaceViewHandle mySpinSurfaceViewHandle, Bitmap bitmap, GLSurfaceView gLSurfaceView) {
        this.f267c = mySpinSurfaceViewHandle;
        this.f265a = bitmap;
        this.f266b = gLSurfaceView;
    }

    public void run() {
        this.f267c.f256e.m276a(this.f265a);
        if (this.f266b != null) {
            this.f266b.requestRender();
            this.f266b.queueEvent(this.f267c.f260i);
        }
    }
}
