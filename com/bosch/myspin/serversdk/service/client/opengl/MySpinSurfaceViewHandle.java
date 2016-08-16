package com.bosch.myspin.serversdk.service.client.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

public class MySpinSurfaceViewHandle {
    private static final LogComponent f252a;
    private Context f253b;
    private Handler f254c;
    private SurfaceView f255d;
    private GlImageView f256e;
    private boolean f257f;
    private Bitmap[] f258g;
    private int f259h;
    private Runnable f260i;
    private int f261j;
    private int f262k;

    static {
        f252a = LogComponent.UI;
    }

    protected MySpinSurfaceViewHandle(Context context, Handler handler) {
        this.f254c = null;
        this.f262k = -1;
        this.f253b = context;
        this.f254c = handler;
        this.f259h = 0;
        this.f258g = new Bitmap[3];
    }

    protected void setSurfaceView(SurfaceView surfaceView) {
        this.f255d = surfaceView;
    }

    protected void addGlImageView() {
        addGlImageView(-1);
    }

    protected void addGlImageView(int i) {
        Logger.logDebug(f252a, "MySpinSurfaceViewHandle/addGlImageView");
        if (this.f255d != null) {
            this.f257f = false;
            for (int i2 = 0; i2 < 3; i2++) {
                this.f258g[i2] = Bitmap.createBitmap(100, 100, Config.ARGB_8888);
            }
            this.f256e = new GlImageView(this.f253b, this.f255d);
            this.f256e.setOnViewSizeChangedListener(new C0201a(this));
            ViewParent parent = this.f255d.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                View relativeLayout = new RelativeLayout(this.f253b);
                if (i < 0) {
                    i = viewGroup.indexOfChild(this.f255d);
                }
                this.f262k = i;
                viewGroup.addView(relativeLayout, i, this.f255d.getLayoutParams());
                viewGroup.removeView(this.f255d);
                relativeLayout.addView(this.f255d);
                relativeLayout.addView(this.f256e);
            }
            if (this.f255d instanceof GLSurfaceView) {
                this.f261j = ((GLSurfaceView) this.f255d).getRenderMode();
                ((GLSurfaceView) this.f255d).setRenderMode(0);
                m277a();
            }
        }
    }

    protected void removeGlImageView() {
        Logger.logDebug(f252a, "MySpinSurfaceViewHandle/removeGlImageView");
        if (this.f255d != null) {
            ViewParent parent = this.f255d.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                if (viewGroup.getParent() instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
                    viewGroup.removeView(this.f255d);
                    if (this.f262k < 0) {
                        this.f262k = viewGroup2.indexOfChild(viewGroup);
                    }
                    viewGroup2.addView(this.f255d, this.f262k, viewGroup.getLayoutParams());
                    viewGroup2.removeView(viewGroup);
                }
            }
            if (this.f255d instanceof GLSurfaceView) {
                ((GLSurfaceView) this.f255d).setRenderMode(this.f261j);
            }
        }
    }

    private void m277a() {
        Logger.logDebug(f252a, "MySpinSurfaceViewHandle/startCapture");
        this.f260i = new C0202b(this);
        if (this.f255d != null && (this.f255d instanceof GLSurfaceView)) {
            ((GLSurfaceView) this.f255d).queueEvent(this.f260i);
        }
    }

    private void m279b() {
        int i = 0;
        ViewGroup viewGroup = (ViewGroup) this.f255d.getParent();
        if (viewGroup.getWidth() > 0 && viewGroup.getHeight() > 0) {
            int detectFormat = GlCapture.detectFormat();
            if (detectFormat == 0) {
                while (i < 3) {
                    this.f258g[i] = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Config.ARGB_8888);
                    Logger.logDebug(f252a, "MySpinSurfaceViewHandle/format detected: ARGB_8888, width: " + viewGroup.getWidth() + ", height: " + viewGroup.getHeight());
                    i++;
                }
                this.f257f = true;
            } else if (detectFormat == 1) {
                while (i < 3) {
                    this.f258g[i] = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Config.RGB_565);
                    Logger.logDebug(f252a, "MySpinSurfaceViewHandle/format detected: RGB_565, width: " + viewGroup.getWidth() + ", height: " + viewGroup.getHeight());
                    i++;
                }
                this.f257f = true;
            }
        }
    }

    private void m282c() {
        if (this.f255d != null) {
            GLSurfaceView gLSurfaceView;
            System.currentTimeMillis();
            GlCapture.capture(this.f258g[this.f259h]);
            Bitmap bitmap = this.f258g[this.f259h];
            this.f259h = (this.f259h + 1) % 3;
            if (this.f255d instanceof GLSurfaceView) {
                gLSurfaceView = (GLSurfaceView) this.f255d;
            } else {
                gLSurfaceView = null;
            }
            this.f254c.post(new C0203c(this, bitmap, gLSurfaceView));
        }
    }

    public void captureOpenGl() {
        if (this.f257f) {
            m282c();
        } else {
            m279b();
        }
    }
}
