package com.bosch.myspin.serversdk.service.client;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.WindowManager;
import com.bosch.myspin.serversdk.BlockStatusListener;
import com.bosch.myspin.serversdk.MySpinException;
import com.bosch.myspin.serversdk.PhoneCallStateListener;
import com.bosch.myspin.serversdk.activity.C0156a;
import com.bosch.myspin.serversdk.cloud.C0157a;
import com.bosch.myspin.serversdk.compression.C0163a;
import com.bosch.myspin.serversdk.compression.NativeCompressionHandler;
import com.bosch.myspin.serversdk.dialog.C0165a;
import com.bosch.myspin.serversdk.maps.MySpinJavaScriptHandler;
import com.bosch.myspin.serversdk.navigation.C0175b;
import com.bosch.myspin.serversdk.service.C0178a;
import com.bosch.myspin.serversdk.service.C0181b;
import com.bosch.myspin.serversdk.service.client.opengl.C0204d;
import com.bosch.myspin.serversdk.service.client.opengl.MySpinSurfaceViewHandle;
import com.bosch.myspin.serversdk.service.sharedmemory.C0216a;
import com.bosch.myspin.serversdk.service.sharedmemory.C0217b;
import com.bosch.myspin.serversdk.utils.C0231b;
import com.bosch.myspin.serversdk.utils.C0236e;
import com.bosch.myspin.serversdk.utils.C0236e.C0234a;
import com.bosch.myspin.serversdk.utils.C0238h;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.vehicledata.C0244d;
import com.bosch.myspin.serversdk.voicecontrol.C0256b;
import com.bosch.myspin.serversdk.window.C0265a;
import java.io.IOException;
import java.util.Iterator;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.service.client.j */
public class C0194j implements ActivityLifecycleCallbacks, OnHierarchyChangeListener {
    private static Matrix f181O;
    private static final LogComponent f182a;
    private int f183A;
    private final C0265a f184B;
    private final C0156a f185C;
    private final C0188e f186D;
    private final C0215x f187E;
    private final C0165a f188F;
    private C0212w f189G;
    private C0204d f190H;
    private final float f191I;
    private final float f192J;
    private float f193K;
    private final boolean f194L;
    private final boolean f195M;
    private final float f196N;
    private Bundle f197P;
    private PhoneCallStateListener f198Q;
    private BlockStatusListener f199R;
    private C0186c f200S;
    private C0178a f201T;
    private final C0157a f202U;
    private Bundle f203V;
    private boolean f204W;
    private final C0181b f205X;
    private final ServiceConnection f206Y;
    private int f207Z;
    private final int aa;
    private boolean ab;
    private boolean f208b;
    private Context f209c;
    private final String f210d;
    private int f211e;
    private int f212f;
    private int f213g;
    private int f214h;
    private int f215i;
    private Object f216j;
    private Bitmap f217k;
    private Bitmap f218l;
    private Bitmap f219m;
    private Canvas f220n;
    private Canvas f221o;
    private boolean f222p;
    private final Object f223q;
    private boolean f224r;
    private boolean f225s;
    private C0163a f226t;
    private C0217b f227u;
    private volatile boolean f228v;
    private volatile boolean f229w;
    private Handler f230x;
    private C0238h f231y;
    private Activity f232z;

    /* renamed from: com.bosch.myspin.serversdk.service.client.j.a */
    class C0193a implements Callback {
        final /* synthetic */ C0194j f178a;
        private int f179b;
        private int f180c;

        private C0193a(C0194j c0194j) {
            this.f178a = c0194j;
        }

        public boolean handleMessage(Message message) {
            synchronized (this.f178a.f223q) {
                if (!this.f178a.f222p) {
                    try {
                        this.f178a.f223q.wait();
                    } catch (Throwable e) {
                        Logger.logWarning(LogComponent.ScreenCapturing, "MySpinServiceClient/Buffer swapping failed", e);
                    }
                }
            }
            synchronized (this.f178a.f216j) {
                if (this.f178a.f219m != null) {
                    try {
                        Bitmap A = this.f178a.f219m;
                        if (this.f178a.f227u != null) {
                            this.f180c = this.f178a.f226t.m94a(A, this.f178a.f227u);
                            this.f179b++;
                            if (this.f179b >= 10) {
                                this.f179b = 0;
                            }
                            this.f178a.m216b(this.f180c);
                            this.f178a.f222p = false;
                        } else {
                            this.f178a.m207a(1, "SharedMemoryFile is null. Compression not started!");
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    this.f178a.m207a(2, "FrontBuffer is null. Compression not started!");
                }
            }
            return true;
        }
    }

    static {
        f182a = LogComponent.SDKMain;
        f181O = new Matrix();
    }

    public C0194j(Application application) throws MySpinException {
        this.f211e = -1;
        this.f212f = -1;
        this.f213g = -1;
        this.f214h = -1;
        this.f215i = -1;
        this.f216j = new Object();
        this.f223q = new Object();
        this.f191I = 1.5f;
        this.f192J = 424.0f;
        this.f193K = 1.0f;
        this.f194L = true;
        this.f195M = false;
        this.f205X = new C0195k(this);
        this.f206Y = new C0205p(this);
        this.aa = 1000;
        if (application == null) {
            throw new IllegalArgumentException("Application context is null!");
        }
        m209a(application);
        this.f200S = new C0186c();
        this.f231y = new C0238h("CompressionBGThread", new C0193a());
        this.f231y.setPriority(10);
        this.f231y.start();
        this.f186D = new C0188e();
        this.f187E = C0215x.m320a();
        this.f187E.m325a((OnHierarchyChangeListener) this);
        this.f188F = new C0165a(this.f212f, this.f211e);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) application.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.f196N = displayMetrics.scaledDensity;
        this.f193K = this.f196N / 1.5f;
        Logger.logDebug(f182a, "MySpinServiceClient/constructor: " + displayMetrics);
        this.f189G = new C0212w(this.f193K);
        this.f190H = new C0204d(this.f230x);
        this.f210d = application.getPackageName();
        this.f185C = new C0156a(-1, -1);
        this.f184B = new C0265a();
        this.f202U = new C0157a(this.f209c);
        if (!m261b()) {
            m217b(application);
            this.f231y.m373a().getLooper().quit();
            this.f231y = null;
            this.f200S.m174a(C0185b.UNDEFINED);
            throw new MySpinException("mySPIN Service not bound! No launcher app installed!");
        }
    }

    public C0186c m258a() {
        return this.f200S;
    }

    private void m205a(int i) {
        if (this.f198Q != null) {
            int i2 = -1;
            switch (i) {
                case TTSConst.TTSMULTILINE /*1*/:
                    i2 = 1;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    i2 = 2;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    i2 = 3;
                    break;
                case TTSConst.TTSXML /*4*/:
                    i2 = 4;
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    i2 = 5;
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    i2 = 6;
                    break;
                default:
                    Logger.logWarning(LogComponent.PhoneCall, "MySpinServiceClient/Received an undefined phone call state: " + i);
                    break;
            }
            this.f198Q.onPhoneCallStateChanged(i2);
        }
    }

    private void m211a(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey("com.bosch.myspin.clientdata.KEY_SCREEN_WIDTH") || bundle.containsKey("com.bosch.myspin.clientdata.KEY_SCREEN_HEIGHT") || bundle.containsKey("com.bosch.myspin.clientdata.KEY_COMPRESSIONTYPE") || bundle.containsKey("com.bosch.myspin.clientdata.KEY_PIXELENDIANESS") || bundle.containsKey("com.bosch.myspin.clientdata.KEY_PIXELFORMAT")) {
                m206a(bundle.getInt("com.bosch.myspin.clientdata.KEY_SCREEN_HEIGHT"), bundle.getInt("com.bosch.myspin.clientdata.KEY_SCREEN_WIDTH"), bundle.getInt("com.bosch.myspin.clientdata.KEY_COMPRESSIONTYPE"), bundle.getInt("com.bosch.myspin.clientdata.KEY_PIXELFORMAT"), bundle.getInt("com.bosch.myspin.clientdata.KEY_PIXELENDIANESS"));
            }
        }
    }

    private void m209a(Application application) {
        if (this.f209c != application) {
            this.f209c = application;
            this.f230x = new Handler(this.f209c.getMainLooper());
            application.registerActivityLifecycleCallbacks(this);
            C0184a.m169a().m171a(this.f209c);
        }
    }

    private void m217b(Application application) {
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(this);
            C0184a.m169a().m171a(null);
        }
    }

    public boolean m261b() {
        if (!this.f208b) {
            try {
                this.f208b = this.f209c.bindService(C0236e.m368a(this.f209c, new Intent("com.bosch.myspin.ACTION_BIND_MYSPIN_SERVICE")), this.f206Y, 1);
                Logger.logDebug(f182a, "MySpinServiceClient/mySPIN service is successfully bound.");
            } catch (Throwable e) {
                Logger.logError(f182a, "MySpinServiceClient/Cant bind service, make sure that only one launcher app is installed", e);
            } catch (C0234a e2) {
                Logger.logError(f182a, "MySpinServiceClient/Cant bind mySPIN service, make sure that a launcher app is installed.");
            }
        }
        C0256b.m452a(this.f209c).m460a();
        C0175b.m135a(this.f209c).m138a();
        C0244d.m391a().m395a(this.f209c);
        this.f202U.m82a();
        return this.f208b;
    }

    public void m262c() {
        try {
            if (this.f201T != null) {
                Logger.logDebug(f182a, "MySpinServiceClient/deselected app: " + this.f210d + ")");
                this.f201T.m143a(null, 0, this.f210d, null);
                this.f200S.m174a(C0185b.UNDEFINED);
            }
        } catch (Throwable e) {
            Logger.logError(f182a, "MySpinServiceClient/thisAppDeSelected: ", e);
        }
        if (this.f189G != null) {
            this.f189G.m317a();
        }
    }

    public void m263d() {
        try {
            if (this.f201T != null) {
                String canonicalName = this.f232z.getClass().getCanonicalName();
                Logger.logDebug(f182a, "MySpinServiceClient/selected app" + canonicalName);
                this.f201T.m143a(this.f205X, this.f183A, this.f210d, canonicalName);
                if (this.f201T.m144b()) {
                    this.f200S.m174a(C0185b.CONNECTED);
                } else {
                    this.f200S.m174a(C0185b.DISCONNECTED);
                }
                this.f203V = this.f201T.m145c();
                if (this.f203V != null) {
                    this.f186D.m192a(this.f203V.getStringArrayList("com.bosch.myspin.EXTRA_ALLOWED_KEYBOARDS"));
                }
            }
        } catch (Throwable e) {
            Logger.logError(f182a, "MySpinServiceClient/thisAppSelected: ", e);
        }
    }

    public boolean m264e() {
        boolean z = false;
        try {
            if (this.f201T != null) {
                z = this.f201T.m144b();
            }
        } catch (Throwable e) {
            Logger.logInfo(f182a, "MySpinServiceClient/isConnected: ", e);
        }
        return z;
    }

    public boolean m265g() {
        if (!m264e() || this.f201T == null) {
            return false;
        }
        if (this.f197P == null) {
            try {
                this.f197P = new Bundle(this.f201T.m141a());
            } catch (Throwable e) {
                Logger.logError(f182a, "MySpinServiceClient/hasPositionInformationCapability: ", e);
                return false;
            }
        }
        return this.f197P.getBoolean("com.bosch.myspin.clientdata.KEY_HAS_POSITIONINFORMATION_CAPABILITY", false);
    }

    public MySpinSurfaceViewHandle m259a(SurfaceView surfaceView) {
        return this.f190H.m289a(surfaceView, this.f209c);
    }

    public void m260b(SurfaceView surfaceView) {
        this.f190H.m290a(surfaceView);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/onActivityCreated: " + activity.getLocalClassName());
        }
    }

    public void onActivityStarted(Activity activity) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/onActivityStarted: " + activity.getLocalClassName());
        }
        if (!this.f228v) {
            this.f232z = activity;
            Logger.logDebug(LogComponent.UI, "MySpinServiceClient/Not connected yet, GlSurfaceView will be added in onConnectionEstablished");
        } else if (activity != null) {
            View rootView = activity.getWindow().getDecorView().getRootView();
            if (!this.f190H.m293a() && (rootView instanceof ViewGroup)) {
                this.f190H.m292a((ViewGroup) rootView, (Context) activity);
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/onActivityResumed: " + activity.getLocalClassName());
        }
        m261b();
        MySpinJavaScriptHandler.setActivity(activity);
        if (this.f228v) {
            this.f232z = activity;
            m237p();
        } else {
            this.f197P = null;
            if (activity == this.f232z) {
                m238q();
            } else {
                m208a(activity, false);
            }
            this.f232z = activity;
        }
        this.f183A = this.f232z.hashCode();
        m263d();
        if (this.f228v) {
            this.f202U.m83a(activity, true);
        } else {
            this.f204W = true;
        }
    }

    public void onActivityPaused(Activity activity) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/onActivityPaused: " + activity.getLocalClassName());
        }
        this.f186D.m195b(activity);
        if (activity != null) {
            this.f187E.m327c(activity.getWindow().getDecorView().getRootView());
        }
        C0256b.m452a(this.f209c).m462b();
        if (this.f232z == activity) {
            this.f232z = null;
            m262c();
            this.f229w = false;
        }
        m253x();
        if (this.f228v) {
            this.f202U.m83a(activity, false);
        }
        this.f204W = false;
    }

    public void onActivityStopped(Activity activity) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/onActivityStopped: " + activity.getLocalClassName());
        }
        if (activity != null) {
            View rootView = activity.getWindow().getDecorView().getRootView();
            if (!this.f190H.m293a() && (rootView instanceof ViewGroup)) {
                this.f190H.m291a((ViewGroup) rootView);
            }
        }
        this.f186D.m197c(activity);
        C0231b a = C0231b.m359a((Context) activity);
        C0231b.m360b(activity);
        if (a != null) {
            a.m367d();
            a.m364a();
        }
    }

    public void onActivityDestroyed(Activity activity) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/onActivityDestroyed: " + activity.getLocalClassName());
        }
        this.f184B.m474c(activity.getWindow(), activity.hashCode());
        C0244d.m391a().m397b(this.f209c);
    }

    private void m237p() {
        if (this.f232z != null && this.f197P != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/handleVisibleActivityOnConnection: [mActivity=" + this.f232z.getLocalClassName() + "]");
            C0231b.m359a(this.f232z).m366c();
            m208a(this.f232z, true);
        }
    }

    private void m238q() {
        m253x();
        if (this.f232z != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/handleVisibleActivityOnDisconnection: [mActivity=" + this.f232z.getLocalClassName() + "]");
            m208a(this.f232z, false);
        }
    }

    private void m208a(Activity activity, boolean z) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/performActivityTransformation(" + activity.getLocalClassName() + ", " + z + " )");
        } else {
            Logger.logDebug(f182a, "MySpinServiceClient/performActivityTransformation(null, " + z + " )");
        }
        if (activity == null) {
            return;
        }
        if (z) {
            this.f184B.m471a(activity.getWindow(), activity.hashCode());
            this.f187E.m326b(activity.getWindow().getDecorView().getRootView());
            this.f186D.m190a(activity);
            return;
        }
        C0231b a = C0231b.m359a((Context) activity);
        if (a != null) {
            a.m367d();
        }
        this.f184B.m473b(activity.getWindow(), activity.hashCode());
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        if (activity != null) {
            Logger.logDebug(f182a, "MySpinServiceClient/onActivitySaveInstanceState: " + activity.getLocalClassName());
        }
    }

    private void m206a(int i, int i2, int i3, int i4, int i5) {
        Logger.logDebug(f182a, "MySpinServiceClient/onFrameAttributesChangedImpl(" + i + ", " + i2 + ", " + i3 + ", " + i4 + ", " + i5 + ")");
        if (this.f228v) {
            try {
                this.f227u = C0216a.m328a(this.f201T.asBinder());
                if (i4 != this.f214h || i != this.f211e || i2 != this.f212f || i5 != this.f215i) {
                    this.f193K = this.f196N / ((1.5f * ((float) i)) / 424.0f);
                    this.f189G = new C0212w(this.f193K);
                    Config config = Config.RGB_565;
                    switch (i4) {
                        case TTSConst.TTSUNICODE /*3*/:
                        case TTSConst.TTSXML /*4*/:
                            config = Config.ARGB_8888;
                            break;
                        default:
                            Logger.logWarning(f182a, "MySpinServiceClient/Unknown pixel format: " + i4);
                            break;
                    }
                    if (i3 == 1) {
                        config = Config.ARGB_8888;
                    }
                    this.f219m = Bitmap.createBitmap((int) (((float) i2) * 1.0f), (int) (((float) i) * 1.0f), config);
                    this.f218l = Bitmap.createBitmap((int) (((float) i2) * 1.0f), (int) (1.0f * ((float) i)), config);
                    this.f219m.setDensity(240);
                    this.f218l.setDensity(240);
                    this.f217k = Bitmap.createBitmap((int) (((float) i2) * this.f193K), (int) (((float) i) * this.f193K), config);
                    this.f220n = new Canvas(this.f219m);
                    this.f221o = new Canvas(this.f218l);
                    this.f226t = new C0163a(NativeCompressionHandler.m88a(), i2, i, i3, i4, i5);
                } else if (i3 != this.f213g) {
                    this.f226t.m95a(i3);
                }
                this.f211e = i;
                this.f212f = i2;
                this.f213g = i3;
                this.f214h = i4;
                this.f215i = i5;
                this.f188F.m104a((int) (((float) this.f212f) * this.f193K), (int) (((float) this.f211e) * this.f193K));
                this.f186D.m189a((int) (((float) this.f212f) * this.f193K), (int) (((float) this.f211e) * this.f193K));
                this.f185C.m70a((int) (((float) this.f212f) * this.f193K), (int) (((float) this.f211e) * this.f193K));
                this.f184B.m472a(this.f185C);
                this.f230x.post(new C0206q(this));
            } catch (Throwable e) {
                Logger.logError(f182a, "MySpinServiceClient/Exception while getting shared memory", e);
            }
        }
    }

    private void m240r() {
        Logger.logDebug(f182a, "MySpinServiceClient/onConnectionEstablished()");
        if (!this.f228v) {
            this.f228v = true;
            this.f230x.post(new C0207r(this));
        }
    }

    private synchronized void m243s() {
        Logger.logDebug(f182a, "MySpinServiceClient/onConnectionClosed()");
        if (this.f228v) {
            this.f228v = false;
            this.f197P = null;
            this.f230x.post(new C0208s(this));
        }
    }

    private void m245t() {
        Logger.logDebug(f182a, "MySpinServiceClient/onBackButtonPressedImpl");
        if (this.f232z == null) {
            return;
        }
        if (this.f186D.m193a()) {
            this.f186D.m194b();
        } else if (this.f188F.m106c()) {
            this.f188F.m105b();
        } else {
            this.f232z.onBackPressed();
        }
    }

    private void m246u() {
        Logger.logDebug(f182a, "MySpinServiceClient/onMenuButtonPressedImpl");
        if (this.f232z != null) {
            this.f232z.openOptionsMenu();
        }
    }

    private void m249v() {
        if (!this.f229w) {
            if (this.f228v) {
                this.f229w = true;
                if (this.f219m == null || this.f201T == null) {
                    this.f229w = false;
                    return;
                } else {
                    this.f231y.m373a().sendEmptyMessage(0);
                    return;
                }
            }
            Logger.logWarning(f182a, "MySpinServiceClient/onFrameRequestImpl: compression start failed");
        }
    }

    private void m207a(int i, String str) {
        Logger.logError(f182a, "MySpinServiceClient/onFrameDataRequestError: " + str + " - CODE [" + i + "]");
    }

    private void m216b(int i) {
        this.f229w = false;
        try {
            if (this.f201T != null) {
                this.f201T.m142a(i, this.f183A);
            }
        } catch (Throwable e) {
            Logger.logError(f182a, "MySpinServiceClient/onFrameDataReadyImpl: ", e);
        }
    }

    private synchronized void m250w() {
        Logger.logDebug(f182a, "MySpinServiceClient/startFrameCaptureLoop");
        if (this.f225s) {
            if (!this.f224r) {
                Logger.logDebug(f182a, "MySpinServiceClient/startCaptureLoop called although there is already one started but stop was request");
                this.f224r = true;
            }
            Logger.logDebug(f182a, "MySpinServiceClient/startCaptureLoop called although there is already one started");
        } else {
            if (this.f232z != null) {
                String packageName = this.f232z.getPackageName();
                if (packageName.equals("com.parkopedia") || packageName.equals("com.tomtom.weurope")) {
                    this.ab = true;
                } else {
                    this.ab = false;
                }
            }
            this.f224r = true;
            this.f225s = true;
            m254y();
        }
    }

    private synchronized void m253x() {
        Logger.logDebug(f182a, "MySpinServiceClient/stopFrameCaptureLoop");
        this.f224r = false;
    }

    private void m254y() {
        this.f225s = false;
        if (m257z()) {
            synchronized (this.f216j) {
                Bitmap bitmap = this.f219m;
                this.f219m = this.f218l;
                this.f218l = bitmap;
                Canvas canvas = this.f220n;
                this.f220n = this.f221o;
                this.f221o = canvas;
            }
            synchronized (this.f223q) {
                this.f223q.notifyAll();
                this.f222p = true;
            }
        }
        if (this.f224r) {
            this.f225s = true;
            this.f230x.postDelayed(new C0209t(this), 10);
        }
    }

    private boolean m257z() {
        Canvas canvas = new Canvas(this.f217k);
        Iterator it = this.f187E.f298b.iterator();
        boolean z = false;
        while (it.hasNext()) {
            View view = (View) it.next();
            try {
                view.buildDrawingCache();
                if (this.ab) {
                    Drawable background = view.getBackground();
                    if (background != null) {
                        background.draw(canvas);
                    }
                }
                view.draw(canvas);
            } catch (Throwable e) {
                Logger.logError(f182a, "MySpinServiceClient/Exception while drawing", e);
            }
            z = true;
        }
        m210a(this.f217k, this.f218l, this.f221o, true);
        int i = this.f207Z;
        this.f207Z = i + 1;
        if (i >= 1000) {
            this.f207Z = 0;
        }
        return z;
    }

    private void m210a(Bitmap bitmap, Bitmap bitmap2, Canvas canvas, boolean z) {
        int i = 0;
        Matrix matrix = f181O;
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int width2 = bitmap.getWidth();
        int height2 = bitmap.getHeight();
        if (width < 1 || height < 1 || width2 < 1 || height2 < 1) {
            throw new IllegalArgumentException("width and height of source and destination bitmap must be > 0");
        }
        matrix.setScale(((float) width) / ((float) width2), ((float) height) / ((float) height2));
        Rect rect = new Rect(0, 0, width2, height2);
        RectF rectF = new RectF(0.0f, 0.0f, (float) width2, (float) height2);
        if (!matrix.rectStaysRect()) {
            i = 1;
        }
        canvas.save();
        canvas.concat(matrix);
        Paint paint = new Paint();
        paint.setFilterBitmap(z);
        if (i != 0) {
            paint.setAntiAlias(true);
        }
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        canvas.restore();
    }

    public boolean m266j() {
        return this.f208b;
    }

    public void onChildViewAdded(View view, View view2) {
        Logger.logDebug(LogComponent.UI, "MySpinServiceClient/onChildViewAdded");
        if (this.f186D != null) {
            this.f186D.m191a(view, view2);
        }
        if (this.f232z != null && !this.f190H.m293a() && (view instanceof ViewGroup)) {
            this.f190H.m292a((ViewGroup) view, this.f232z);
        }
    }

    public void onChildViewRemoved(View view, View view2) {
        Logger.logDebug(LogComponent.UI, "MySpinServiceClient/onChildViewRemoved");
        if (this.f232z != null && !this.f190H.m293a() && (view2 instanceof ViewGroup)) {
            this.f190H.m291a((ViewGroup) view2);
        }
    }
}
