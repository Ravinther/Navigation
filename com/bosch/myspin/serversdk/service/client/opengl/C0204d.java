package com.bosch.myspin.serversdk.service.client.opengl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.HashMap;

/* renamed from: com.bosch.myspin.serversdk.service.client.opengl.d */
public class C0204d {
    private static final LogComponent f268a;
    private HashMap<SurfaceView, MySpinSurfaceViewHandle> f269b;
    private Handler f270c;
    private boolean f271d;

    static {
        f268a = LogComponent.UI;
    }

    public C0204d(Handler handler) {
        this.f270c = null;
        this.f270c = handler;
        this.f269b = new HashMap();
    }

    public MySpinSurfaceViewHandle m289a(SurfaceView surfaceView, Context context) {
        Logger.logDebug(f268a, "OpenGlHandler/registerSurfaceView");
        if (surfaceView == null || (surfaceView instanceof GLSurfaceView)) {
            return null;
        }
        MySpinSurfaceViewHandle mySpinSurfaceViewHandle = new MySpinSurfaceViewHandle(context, this.f270c);
        mySpinSurfaceViewHandle.setSurfaceView(surfaceView);
        mySpinSurfaceViewHandle.addGlImageView();
        this.f269b.put(surfaceView, mySpinSurfaceViewHandle);
        return mySpinSurfaceViewHandle;
    }

    public void m290a(SurfaceView surfaceView) {
        Logger.logDebug(f268a, "OpenGlHandler/unregisterSurfaceView");
        if (surfaceView != null && !(surfaceView instanceof GLSurfaceView)) {
            MySpinSurfaceViewHandle mySpinSurfaceViewHandle = (MySpinSurfaceViewHandle) this.f269b.get(surfaceView);
            if (mySpinSurfaceViewHandle != null) {
                mySpinSurfaceViewHandle.removeGlImageView();
                mySpinSurfaceViewHandle.setSurfaceView(null);
                this.f269b.remove(surfaceView);
            }
        }
    }

    public void m292a(ViewGroup viewGroup, Context context) {
        Logger.logDebug(f268a, "OpenGlHandler/addGlSurfaceView");
        this.f271d = true;
        m288b(viewGroup, context);
        this.f271d = false;
    }

    @SuppressLint({"InlinedApi"})
    private void m288b(ViewGroup viewGroup, Context context) {
        Logger.logDebug(f268a, "OpenGlHandler/addGlSurfaceViewRecursive");
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof ViewGroup) {
                    m288b((ViewGroup) childAt, context);
                } else if (childAt instanceof GLSurfaceView) {
                    SurfaceView surfaceView = (SurfaceView) childAt;
                    MySpinSurfaceViewHandle mySpinSurfaceViewHandle = new MySpinSurfaceViewHandle(context, this.f270c);
                    mySpinSurfaceViewHandle.setSurfaceView(surfaceView);
                    mySpinSurfaceViewHandle.addGlImageView(i);
                    this.f269b.put(surfaceView, mySpinSurfaceViewHandle);
                }
            }
            return;
        }
        Logger.logWarning(f268a, "OpenGlHandler/RootView is not a instance of ViewGroup!");
    }

    public void m291a(ViewGroup viewGroup) {
        Logger.logDebug(f268a, "OpenGlHandler/removeGlSurfaceView");
        this.f271d = true;
        m287b(viewGroup);
        this.f271d = false;
    }

    @SuppressLint({"InlinedApi"})
    private void m287b(ViewGroup viewGroup) {
        Logger.logDebug(f268a, "OpenGlHandler/removeGlSurfaceViewRecursive");
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof ViewGroup) {
                    m287b((ViewGroup) childAt);
                } else if (childAt instanceof GLSurfaceView) {
                    GLSurfaceView gLSurfaceView = (GLSurfaceView) childAt;
                    MySpinSurfaceViewHandle mySpinSurfaceViewHandle = (MySpinSurfaceViewHandle) this.f269b.get(gLSurfaceView);
                    if (mySpinSurfaceViewHandle != null) {
                        mySpinSurfaceViewHandle.removeGlImageView();
                        mySpinSurfaceViewHandle.setSurfaceView(null);
                        this.f269b.remove(gLSurfaceView);
                    }
                }
            }
            return;
        }
        Logger.logError(f268a, "OpenGlHandler/RootView is not a instance of ViewGroup!");
    }

    public boolean m293a() {
        return this.f271d;
    }
}
