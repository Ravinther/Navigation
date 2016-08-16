package com.sygic.aura.feature.gl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.SygicMain;
import com.sygic.aura.clazz.EglConfigsArray;
import com.sygic.aura.events.touch.SingleTouchService;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;

/* compiled from: LowGlFeature */
class LowGlFeatureEclair extends LowGlFeature {
    private int mLayoutHeight;
    private int mLayoutWidth;

    /* renamed from: com.sygic.aura.feature.gl.LowGlFeatureEclair.1 */
    class LowGlFeature implements Callback {
        final /* synthetic */ Context val$context;

        LowGlFeature(Context context) {
            this.val$context = context;
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d("SurfaceHolder.Callback", "-------surfaceDestroyed");
            LowGlFeatureEclair.this.mSurfaceCreated = false;
            LowGlFeatureEclair.this.mSurfWasDestroyed = true;
            LowGlFeatureEclair.this.mInBackground = true;
            SygicMain.nativeSysSetRunningBackground(true);
            if (!ProjectsConsts.getBoolean(6)) {
                LowGlFeatureEclair.this.enableTouchListener(false);
            }
            LowGlFeatureEclair.this.destroyGlSurface();
        }

        public void surfaceCreated(SurfaceHolder holder) {
            Log.d("SurfaceHolder.Callback", "-------surfaceCreated");
            synchronized (LowGlFeature.sLock) {
                LowGlFeatureEclair.this.mSurfaceCreated = true;
                LowGlFeatureEclair.this.mInBackground = false;
                if (!ProjectsConsts.getBoolean(6)) {
                    LowGlFeatureEclair.this.enableTouchListener(true);
                }
                LowGlFeature.sLock.notifyAll();
                if (LowGlFeatureEclair.this.mGlSurfaceListener != null) {
                    LowGlFeatureEclair.this.mGlSurfaceListener.surfaceCreated();
                }
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d("SurfaceHolder.Callback", "-------surfaceChanged");
            int iOrient = ((WindowManager) this.val$context.getSystemService("window")).getDefaultDisplay().getOrientation();
            if (LowGlFeatureEclair.this.mWasStarted) {
                SygicMain.nativeSurfaceRotate(width, height, iOrient);
                LowGlFeatureEclair.this.setInBackground(false);
                if (LowGlFeatureEclair.this.mSurfWasDestroyed) {
                    SygicMain.getInstance().RequestSurfaceReset();
                }
            } else {
                SygicMain.nativeSurfaceRotate(-1, -1, iOrient);
                SygicMain.nativeSysSetRunningBackground(false);
            }
            LowGlFeatureEclair.this.mSurfWasDestroyed = false;
        }
    }

    /* renamed from: com.sygic.aura.feature.gl.LowGlFeatureEclair.2 */
    class LowGlFeature implements OnTouchListener {
        LowGlFeature() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (((float) ((int) ((event.getX() - 0.5f) / LowGlFeatureEclair.this.mContext.getResources().getDisplayMetrics().density))) >= LowGlFeature.DRAWER_OPENING_THRESHOLD) {
                SygicMain.getInstance().getEventService().onTouchEvent(event);
                SygicMain.notifyTouchListeners(event);
            }
            return true;
        }
    }

    /* renamed from: com.sygic.aura.feature.gl.LowGlFeatureEclair.3 */
    class LowGlFeature implements Runnable {
        final /* synthetic */ int val$h;
        final /* synthetic */ int val$w;

        LowGlFeature(int i, int i2) {
            this.val$w = i;
            this.val$h = i2;
        }

        public void run() {
            if (this.val$w <= 0 || this.val$h <= 0) {
                LowGlFeatureEclair.this.mSurface3D.getHolder().setSizeFromLayout();
                SingleTouchService.mTouchScaleX = 1.0f;
                SingleTouchService.mTouchScaleY = 1.0f;
                LowGlFeatureEclair.this.mSurface3D.getLayoutParams().width = -1;
                LowGlFeatureEclair.this.mSurface3D.getLayoutParams().height = -1;
                return;
            }
            LowGlFeatureEclair.this.mSurface3D.getHolder().setFixedSize(this.val$w, this.val$h);
            SingleTouchService.mTouchScaleX = ((float) this.val$w) / ((float) LowGlFeatureEclair.this.mSurface3D.getWidth());
            SingleTouchService.mTouchScaleY = ((float) this.val$h) / ((float) LowGlFeatureEclair.this.mSurface3D.getHeight());
        }
    }

    protected LowGlFeatureEclair() {
    }

    protected LowGlFeatureEclair(Context context) {
        super(context);
        this.mCallback = new LowGlFeature(context);
    }

    public void enableTouchListener(boolean bEnable) {
        if (this.mSurface3D == null) {
            return;
        }
        if (bEnable) {
            this.mSurface3D.setOnTouchListener(new LowGlFeature());
        } else {
            this.mSurface3D.setOnTouchListener(null);
        }
    }

    public void setSurface(SurfaceView surface) {
        if (this.mSurface3D == null || !this.mSurface3D.equals(surface)) {
            if (this.mSurface3D != null) {
                this.mSurface3D.getHolder().removeCallback(this.mCallback);
            }
            this.mSurface3D = surface;
            if (this.mSurface3D != null) {
                this.mSurface3D.getHolder().addCallback(this.mCallback);
            }
            if (this.mSurface3D.getHolder().getSurface().isValid()) {
                synchronized (sLock) {
                    sLock.notifyAll();
                }
            }
            this.mSurface3D.dispatchWindowVisibilityChanged(4);
            this.mSurface3D.dispatchWindowVisibilityChanged(0);
        }
    }

    public SurfaceView getSurface() {
        return this.mSurface3D;
    }

    public void doDraw(int[] iArr, int sx, int sy, int sw, int sh) {
        if (!this.mInBackground && iArr != null && sw != -1 && sh != -1) {
            Rect dirty = new Rect(sx, sy, sx + sw, sy + sh);
            Canvas canvas = lockCanvas();
            if (canvas != null) {
                canvas.clipRect(dirty, Op.REPLACE);
                canvas.drawBitmap(iArr, 0, sw, 0, 0, sw, sh, false, null);
                unlockCanvasAndPost(canvas);
            }
        }
    }

    public Object getEglConfigs(boolean bGL2) {
        if (this.mEglHelper == null || !this.mEglHelper.initEgl()) {
            return null;
        }
        this.mEglConfigsArr = new EglConfigsArray(this.mEglHelper.getAllConfigs(bGL2));
        return this.mEglConfigsArr;
    }

    public int getEglConfigAttr(int eglConfig, int nAttr) {
        if (this.mEglHelper == null || !this.mEglHelper.initEgl() || this.mEglConfigsArr == null) {
            return -1;
        }
        EGLConfig config = this.mEglConfigsArr.getConfig(eglConfig);
        if (config != null) {
            return this.mEglHelper.getConfigAttr(config, nAttr);
        }
        return -1;
    }

    public float getEglVersion() {
        if (this.mEglHelper == null || !this.mEglHelper.initEgl()) {
            return 0.0f;
        }
        return this.mEglHelper.getEglVersion();
    }

    public int initEgl(int eglConfig) {
        if (this.mEglHelper == null || !this.mEglHelper.initEgl() || this.mEglConfigsArr == null) {
            return -1;
        }
        EGLConfig config = this.mEglConfigsArr.getConfig(eglConfig);
        if (config == null) {
            return -1;
        }
        EGLContext eglContext = this.mEglHelper.createContext(config);
        if (eglContext == null || eglContext == EGL10.EGL_NO_CONTEXT || this.mEglHelper.createSurface(this.mSurface3D.getHolder()) == null) {
            return -1;
        }
        this.mHasSurface3D = true;
        return 1;
    }

    public void setFixedSize(int w, int h) {
        SygicMain.getActivity().runOnUiThread(new LowGlFeature(w, h));
    }

    public void destroyGlSurface() {
        this.mEglHelper.destroySurface();
        this.mHasSurface3D = false;
        this.mGl = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public javax.microedition.khronos.opengles.GL10 createGlSurface() {
        /*
        r3 = this;
        r2 = sLock;
        monitor-enter(r2);
    L_0x0003:
        r1 = r3.mSurfaceCreated;	 Catch:{ all -> 0x0012 }
        if (r1 != 0) goto L_0x0015;
    L_0x0007:
        r1 = sLock;	 Catch:{ InterruptedException -> 0x000d }
        r1.wait();	 Catch:{ InterruptedException -> 0x000d }
        goto L_0x0003;
    L_0x000d:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0012 }
        goto L_0x0003;
    L_0x0012:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0012 }
        throw r1;
    L_0x0015:
        monitor-exit(r2);	 Catch:{ all -> 0x0012 }
        r1 = 0;
        com.sygic.aura.SygicMain.nativeSysSetRunningBackground(r1);
        r1 = r3.mHasSurface3D;
        if (r1 == 0) goto L_0x0025;
    L_0x001e:
        r1 = r3.mGl;
        if (r1 == 0) goto L_0x0025;
    L_0x0022:
        r1 = r3.mGl;
    L_0x0024:
        return r1;
    L_0x0025:
        r1 = r3.mEglHelper;
        r2 = r3.mSurface3D;
        r2 = r2.getHolder();
        r1 = r1.createSurface(r2);
        r1 = (javax.microedition.khronos.opengles.GL10) r1;
        r3.mGl = r1;
        r1 = 1;
        r3.mHasSurface3D = r1;
        r1 = r3.mGl;
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.gl.LowGlFeatureEclair.createGlSurface():javax.microedition.khronos.opengles.GL10");
    }

    public void swap3DBuffers() {
        if (!this.mInBackground && this.mHasSurface3D) {
            if (!(this.mScreenShotListener == null || this.mScreenShotSize == null)) {
                this.mScreenShotListener.onScreenShotDone(nativeScreenShot(this.mScreenShotSize[0], this.mScreenShotSize[1]));
                this.mScreenShotListener = null;
            }
            if (this.mEglCallback != null) {
                this.mEglCallback.eglSwapBuffers();
            }
            this.mEglHelper.swap();
        }
    }

    public boolean checkGles() {
        EglHelper eglHelper = new EglHelper();
        return eglHelper.initEgl() && eglHelper.checkEgl(this.mSurface3D.getHolder());
    }

    public void setInBackground(boolean bInBkg) {
        this.mInBackground = bInBkg;
        if (!(this.mWasStarted || bInBkg)) {
            this.mWasStarted = true;
        }
        restoreSurfaceLayout(bInBkg);
    }

    public boolean getInBackground() {
        return this.mInBackground;
    }

    private void restoreSurfaceLayout(boolean bInBkg) {
        if (SingleTouchService.mTouchScaleX != 1.0f || SingleTouchService.mTouchScaleY != 1.0f) {
            if (bInBkg) {
                this.mLayoutWidth = this.mSurface3D.getHolder().getSurfaceFrame().width();
                this.mLayoutHeight = this.mSurface3D.getHolder().getSurfaceFrame().height();
            } else if (this.mLayoutWidth > 0 && this.mLayoutHeight > 0) {
                this.mSurface3D.getLayoutParams().width = this.mLayoutWidth;
                this.mSurface3D.getLayoutParams().height = this.mLayoutHeight;
            }
        }
    }

    public void makeCurrent() {
        this.mEglHelper.makeCurrent();
    }

    protected Canvas lockCanvas() {
        return this.mSurface3D.getHolder().lockCanvas();
    }

    protected void unlockCanvasAndPost(Canvas c) {
        this.mSurface3D.getHolder().unlockCanvasAndPost(c);
    }

    private Bitmap nativeScreenShot(int width, int height) {
        if (this.mGl == null) {
            return null;
        }
        int[] b = new int[(width * height)];
        int[] bt = new int[(width * height)];
        IntBuffer ib = IntBuffer.wrap(b);
        ib.position(0);
        this.mGl.glReadPixels(0, 0, width, height, 6408, 5121, ib);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pix = b[(i * width) + j];
                bt[(((height - i) - 1) * width) + j] = ((-16711936 & pix) | ((pix << 16) & 16711680)) | ((pix >> 16) & 255);
            }
        }
        Bitmap screen = Bitmap.createBitmap(bt, width, height, Config.RGB_565);
        Bitmap copy = screen.copy(Config.ARGB_8888, true);
        screen.recycle();
        return copy;
    }
}
