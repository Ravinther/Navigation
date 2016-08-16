package com.sygic.aura.feature.gl;

import android.content.Context;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.sygic.aura.clazz.EglConfigsArray;
import com.sygic.aura.utils.Utils;
import javax.microedition.khronos.opengles.GL10;

public abstract class LowGlFeature {
    protected static float DRAWER_OPENING_THRESHOLD;
    protected static final Object sLock;
    protected Callback mCallback;
    protected Context mContext;
    protected EglCallback mEglCallback;
    protected EglConfigsArray mEglConfigsArr;
    protected EglHelper mEglHelper;
    protected GL10 mGl;
    protected GlSurfaceListener mGlSurfaceListener;
    protected volatile boolean mHasSurface3D;
    protected volatile boolean mInBackground;
    protected ScreenShotRequestListener mScreenShotListener;
    protected int[] mScreenShotSize;
    protected volatile boolean mSurfWasDestroyed;
    protected SurfaceView mSurface3D;
    protected volatile boolean mSurfaceCreated;
    protected volatile boolean mWasStarted;

    public interface GlSurfaceListener {
        void surfaceCreated();
    }

    public interface EglCallback {
        void eglSwapBuffers();
    }

    public abstract boolean checkGles();

    public abstract GL10 createGlSurface();

    public abstract void destroyGlSurface();

    public abstract void doDraw(int[] iArr, int i, int i2, int i3, int i4);

    public abstract void enableTouchListener(boolean z);

    public abstract int getEglConfigAttr(int i, int i2);

    public abstract Object getEglConfigs(boolean z);

    public abstract float getEglVersion();

    public abstract boolean getInBackground();

    public abstract SurfaceView getSurface();

    public abstract int initEgl(int i);

    public abstract void makeCurrent();

    public abstract void setFixedSize(int i, int i2);

    public abstract void setInBackground(boolean z);

    public abstract void setSurface(SurfaceView surfaceView);

    public abstract void swap3DBuffers();

    static {
        sLock = new Object();
        DRAWER_OPENING_THRESHOLD = 20.0f;
    }

    protected LowGlFeature() {
        this.mHasSurface3D = false;
        this.mInBackground = true;
        this.mSurfaceCreated = false;
        this.mWasStarted = false;
        this.mSurfWasDestroyed = true;
    }

    protected LowGlFeature(Context context) {
        this.mHasSurface3D = false;
        this.mInBackground = true;
        this.mSurfaceCreated = false;
        this.mWasStarted = false;
        this.mSurfWasDestroyed = true;
        this.mEglHelper = new EglHelper();
        this.mContext = context;
    }

    public static LowGlFeature createInstance(Context context) {
        if (Utils.getAndroidVersion() > 5) {
            return new LowGlFeatureEclair(context);
        }
        return null;
    }

    public void finish() {
        this.mEglHelper.finish();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitForSurfaceCration(int r7) {
        /*
        r6 = this;
        if (r7 >= 0) goto L_0x0004;
    L_0x0002:
        r7 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
    L_0x0004:
        r2 = sLock;
        monitor-enter(r2);
    L_0x0007:
        r1 = r6.mSurfaceCreated;	 Catch:{ all -> 0x0017 }
        if (r1 != 0) goto L_0x001a;
    L_0x000b:
        r1 = sLock;	 Catch:{ InterruptedException -> 0x0012 }
        r4 = (long) r7;	 Catch:{ InterruptedException -> 0x0012 }
        r1.wait(r4);	 Catch:{ InterruptedException -> 0x0012 }
        goto L_0x0007;
    L_0x0012:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0017 }
        goto L_0x0007;
    L_0x0017:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0017 }
        throw r1;
    L_0x001a:
        monitor-exit(r2);	 Catch:{ all -> 0x0017 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.gl.LowGlFeature.waitForSurfaceCration(int):void");
    }

    public boolean hasSurface() {
        return this.mHasSurface3D;
    }

    public void registerGlSurfaceListener(GlSurfaceListener listener) {
        this.mGlSurfaceListener = listener;
    }

    public void registerEglCallback(EglCallback callback) {
        this.mEglCallback = callback;
    }
}
