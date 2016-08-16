package com.sygic.aura.feature.gl;

import android.util.Log;
import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;

public class EglHelper {
    private static final String TAG;
    private EGL10 mEgl;
    private EGLConfig mEglConfig;
    private EGLContext mEglContext;
    private EGLDisplay mEglDisplay;
    private EGLSurface mEglSurface;
    private GL mGl;
    private boolean m_bGL2;
    private boolean m_bInitialized;
    private float m_fEglVersion;

    public EglHelper() {
        this.m_bInitialized = false;
        this.m_fEglVersion = 0.0f;
        this.m_bGL2 = false;
    }

    static {
        TAG = EglHelper.class.getCanonicalName();
    }

    public EGLConfig[] getAllConfigs(boolean bGL2) {
        int[] num_config = new int[1];
        int[] configs = null;
        this.m_bGL2 = bGL2;
        if (bGL2) {
            configs = new int[]{12352, 4, 12344};
        }
        this.mEgl.eglChooseConfig(this.mEglDisplay, configs, null, 0, num_config);
        int nAllConfigs = num_config[0];
        if (nAllConfigs <= 0) {
            throw new IllegalArgumentException("No EGL config found");
        }
        EGLConfig[] arrAllConfigs = new EGLConfig[nAllConfigs];
        this.mEgl.eglChooseConfig(this.mEglDisplay, configs, arrAllConfigs, nAllConfigs, num_config);
        return arrAllConfigs;
    }

    public int getConfigAttr(EGLConfig config, int nAttr) {
        int[] nValue = new int[1];
        if (this.mEgl.eglGetConfigAttrib(this.mEglDisplay, config, nAttr, nValue)) {
            return nValue[0];
        }
        return -1;
    }

    public boolean initEgl() {
        if (this.m_bInitialized) {
            return true;
        }
        try {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            int[] version = new int[2];
            this.mEgl.eglInitialize(this.mEglDisplay, version);
            this.mEglSurface = null;
            if (this.m_fEglVersion == 0.0f) {
                this.m_fEglVersion = ((float) version[0]) + (((float) version[1]) / 10.0f);
            }
            this.m_bInitialized = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void finish() {
        destroySurface();
        if (this.mEglContext != null) {
            this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
            this.mEglContext = null;
        }
        if (this.mEglDisplay != null) {
            this.mEgl.eglTerminate(this.mEglDisplay);
            this.mEglDisplay = null;
        }
        this.m_bInitialized = false;
    }

    public float getEglVersion() {
        return this.m_fEglVersion;
    }

    public EGLContext createContext(EGLConfig eglConfig) {
        int[] attrib_list = null;
        if (this.m_bGL2) {
            attrib_list = new int[]{12440, 2, 12344};
        }
        this.mEglConfig = eglConfig;
        this.mEglContext = this.mEgl.eglCreateContext(this.mEglDisplay, this.mEglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
        return this.mEglContext;
    }

    private void destroyContext() {
        this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
        this.mEglContext = null;
        this.mEglConfig = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkEgl(android.view.SurfaceHolder r19) {
        /*
        r18 = this;
        r2 = 0;
        r11 = 0;
        r0 = r18;
        r3 = r0.getAllConfigs(r11);	 Catch:{ Exception -> 0x00be }
        r13 = r3.length;	 Catch:{ Exception -> 0x00be }
        r11 = 0;
        r12 = r11;
    L_0x000b:
        if (r12 >= r13) goto L_0x00c2;
    L_0x000d:
        r5 = r3[r12];	 Catch:{ Exception -> 0x00be }
        r0 = r18;
        r6 = r0.createContext(r5);	 Catch:{ Exception -> 0x00be }
        if (r6 == 0) goto L_0x001b;
    L_0x0017:
        r11 = javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT;	 Catch:{ Exception -> 0x00be }
        if (r6 != r11) goto L_0x001f;
    L_0x001b:
        r11 = r12 + 1;
        r12 = r11;
        goto L_0x000b;
    L_0x001f:
        r7 = r18.createSurface(r19);	 Catch:{ Exception -> 0x00b9 }
        r7 = (javax.microedition.khronos.opengles.GL10) r7;	 Catch:{ Exception -> 0x00b9 }
        r11 = 7938; // 0x1f02 float:1.1124E-41 double:3.922E-320;
        r8 = r7.glGetString(r11);	 Catch:{ Exception -> 0x00b9 }
        r11 = " ";
        r9 = r8.split(r11);	 Catch:{ Exception -> 0x00b9 }
        r14 = r9.length;	 Catch:{ Exception -> 0x00b9 }
        r11 = 0;
    L_0x0034:
        if (r11 >= r14) goto L_0x004b;
    L_0x0036:
        r10 = r9[r11];	 Catch:{ Exception -> 0x00b9 }
        r15 = java.lang.Float.valueOf(r10);	 Catch:{ NumberFormatException -> 0x00b4 }
        r11 = r15.floatValue();	 Catch:{ NumberFormatException -> 0x00b4 }
        r14 = (double) r11;
        r16 = 4607632778762754458; // 0x3ff199999999999a float:-1.5881868E-23 double:1.1;
        r11 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r11 < 0) goto L_0x00b2;
    L_0x004a:
        r2 = 1;
    L_0x004b:
        r11 = TAG;	 Catch:{ Exception -> 0x00b9 }
        r14 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b9 }
        r14.<init>();	 Catch:{ Exception -> 0x00b9 }
        r15 = "GL Renderer: ";
        r14 = r14.append(r15);	 Catch:{ Exception -> 0x00b9 }
        r15 = 7937; // 0x1f01 float:1.1122E-41 double:3.9214E-320;
        r15 = r7.glGetString(r15);	 Catch:{ Exception -> 0x00b9 }
        r14 = r14.append(r15);	 Catch:{ Exception -> 0x00b9 }
        r14 = r14.toString();	 Catch:{ Exception -> 0x00b9 }
        android.util.Log.d(r11, r14);	 Catch:{ Exception -> 0x00b9 }
        r11 = TAG;	 Catch:{ Exception -> 0x00b9 }
        r14 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b9 }
        r14.<init>();	 Catch:{ Exception -> 0x00b9 }
        r15 = "GL Vendor: ";
        r14 = r14.append(r15);	 Catch:{ Exception -> 0x00b9 }
        r15 = 7936; // 0x1f00 float:1.1121E-41 double:3.921E-320;
        r15 = r7.glGetString(r15);	 Catch:{ Exception -> 0x00b9 }
        r14 = r14.append(r15);	 Catch:{ Exception -> 0x00b9 }
        r14 = r14.toString();	 Catch:{ Exception -> 0x00b9 }
        android.util.Log.d(r11, r14);	 Catch:{ Exception -> 0x00b9 }
        r11 = TAG;	 Catch:{ Exception -> 0x00b9 }
        r14 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b9 }
        r14.<init>();	 Catch:{ Exception -> 0x00b9 }
        r15 = "GL Version: ";
        r14 = r14.append(r15);	 Catch:{ Exception -> 0x00b9 }
        r15 = 7938; // 0x1f02 float:1.1124E-41 double:3.922E-320;
        r15 = r7.glGetString(r15);	 Catch:{ Exception -> 0x00b9 }
        r14 = r14.append(r15);	 Catch:{ Exception -> 0x00b9 }
        r14 = r14.toString();	 Catch:{ Exception -> 0x00b9 }
        android.util.Log.d(r11, r14);	 Catch:{ Exception -> 0x00b9 }
        r18.destroySurface();	 Catch:{ Exception -> 0x00b9 }
    L_0x00ab:
        r18.destroyContext();	 Catch:{ Exception -> 0x00be }
        if (r2 == 0) goto L_0x001b;
    L_0x00b0:
        r11 = 1;
    L_0x00b1:
        return r11;
    L_0x00b2:
        r2 = 0;
        goto L_0x004b;
    L_0x00b4:
        r15 = move-exception;
        r11 = r11 + 1;
        goto L_0x0034;
    L_0x00b9:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ Exception -> 0x00be }
        goto L_0x00ab;
    L_0x00be:
        r4 = move-exception;
        r4.printStackTrace();
    L_0x00c2:
        r11 = r2;
        goto L_0x00b1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.gl.EglHelper.checkEgl(android.view.SurfaceHolder):boolean");
    }

    public GL createSurface(SurfaceHolder holder) {
        Log.d(TAG, "createSurface()");
        if (!(this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE)) {
            makeCurrentNull();
            this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
        }
        boolean bContinue;
        do {
            bContinue = true;
            try {
                this.mEglSurface = this.mEgl.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, holder, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
                bContinue = false;
                continue;
            } else {
                try {
                    makeCurrent();
                    continue;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    bContinue = false;
                    continue;
                }
            }
        } while (!bContinue);
        this.mGl = this.mEglContext.getGL();
        return this.mGl;
    }

    public void destroySurface() {
        Log.d(TAG, "destroySurface()");
        if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
            this.mEgl.eglWaitGL();
            makeCurrentNull();
            this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            this.mEglSurface = null;
        }
    }

    public boolean swap() {
        this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
        return this.mEgl.eglGetError() != 12302;
    }

    public void makeCurrentNull() {
        this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
    }

    public void makeCurrent() {
        if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
            throwEglException("eglMakeCurrent");
        }
    }

    private void throwEglException(String function) {
        int error = this.mEgl.eglGetError();
        System.out.println(function + " failed: " + error);
        throw new RuntimeException(function + " failed: " + error);
    }
}
