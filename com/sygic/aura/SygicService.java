package com.sygic.aura;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.sygic.aura.feature.gps.LocationService;
import com.sygic.aura.utils.FileUtils;
import com.sygic.aura.utils.GuiUtils;
import com.sygic.aura.utils.Utils;
import com.sygic.aura.view.ExtendedEditText;
import com.sygic.base.C1799R;
import com.sygic.sdk.ApiInitializer;
import com.sygic.sdk.SygicSdkService;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SygicService extends Service {
    private static final String LOG_TAG;
    private ApiInitializer mApiInitializer;
    private transient boolean mEventExit;
    private volatile boolean mIsFinishing;
    private SygicSdkService mRemoteSdkService;
    private transient Runnable mRunAppCycle;
    private ProgressBar mStartProgressBar;
    private SygicMain mSygicMain;
    private SygicNaviThread mThread;

    /* renamed from: com.sygic.aura.SygicService.1 */
    class C11201 implements Runnable {
        C11201() {
        }

        public void run() {
            SygicService.this.startProgress();
        }
    }

    /* renamed from: com.sygic.aura.SygicService.2 */
    class C11222 implements Runnable {

        /* renamed from: com.sygic.aura.SygicService.2.1 */
        class C11211 implements Runnable {
            C11211() {
            }

            public void run() {
                if (SygicService.this.mSygicMain.getFeature().getGpsFeature().isEnabled() || ProjectsConsts.getBoolean(6)) {
                    if (ProjectsConsts.getBoolean(6)) {
                        SygicService.this.startThread();
                    } else {
                        SygicService.this.startThread();
                    }
                    return;
                }
                if (SygicService.isAsSdk(SygicService.this)) {
                    SygicService.this.startThread();
                }
                SygicService.this.sendEvent("com.sygic.intent.action.closedGps", SygicService.this.getString(C1799R.string.title_gps_warning).concat(" ").concat(SygicService.this.getString(C1799R.string.message_gps_warning)));
            }
        }

        C11222() {
        }

        public void run() {
            SygicService.this.waitForPaths();
            Utils.runLogs();
            if (FileUtils.isSDCardPresent()) {
                String strSDRoot = Utils.getSygicDirPath();
                if (strSDRoot != null && FileUtils.fileExists(strSDRoot) && FileUtils.fileExists(strSDRoot.concat(File.separator).concat(Utils.getIniFilesDir()))) {
                    if (SygicService.this.mThread != null) {
                        SygicService.this.mThread.setPath(strSDRoot);
                    }
                    if (FileUtils.checkSdPermissions()) {
                        if (FileUtils.fileExists(Utils.getSygicDirPath().concat("/").concat(Utils.getIniFilesDir()).concat("/sw3d.file"))) {
                            SygicService.this.mSygicMain.SetSwRenderer(true);
                        }
                        SygicMain.getHandler().post(new C11211());
                        return;
                    }
                    SygicService.this.sendEvent("com.sygic.intent.action.notWritable", SygicService.this.getString(C1799R.string.message_not_writable));
                    return;
                }
                SygicService.this.sendEvent("com.sygic.intent.action.noDir", SygicService.this.getString(C1799R.string.message_inifiles_missing));
                return;
            }
            SygicService.this.sendEvent("com.sygic.intent.action.noSdcard", SygicService.this.getString(C1799R.string.message_sdcard_missing));
        }
    }

    /* renamed from: com.sygic.aura.SygicService.3 */
    class C11233 implements Runnable {
        C11233() {
        }

        public void run() {
            SygicService.this.stopProgress();
        }
    }

    public SygicService() {
        this.mEventExit = false;
        this.mIsFinishing = false;
    }

    static {
        LOG_TAG = SygicService.class.getCanonicalName();
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "Sygic service binding...");
        if (intent.getAction() == null || !intent.getAction().equals("com.sygic.intent.action.BIND_REMOTE")) {
            return new LocalBinder(this);
        }
        if (this.mRemoteSdkService == null) {
            this.mRemoteSdkService = new SygicSdkService(this);
        }
        return this.mRemoteSdkService;
    }

    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "Sygic onUnbind...");
        if (intent.getAction() != null && intent.getAction().equals("com.sygic.intent.action.BIND_REMOTE")) {
            this.mRemoteSdkService = null;
        }
        return super.onUnbind(intent);
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Sygic service creating...");
        try {
            this.mSygicMain = SygicMain.getInstance();
            try {
                if (this.mSygicMain == null) {
                    this.mSygicMain = SygicMain.getInstance();
                }
                if (FileUtils.isSDCardPresent()) {
                    if (this.mApiInitializer == null) {
                        this.mApiInitializer = new ApiInitializer(this);
                    }
                    initSdk();
                    setProjectSettings();
                    this.mSygicMain.getFeature().getSystemFeature().setFullscreen(SygicPreferences.getFullscreen(this));
                    this.mSygicMain.getFeature().getSystemFeature().setLibrary(isAsSdk(this));
                    if (ProjectsConsts.getBoolean(5)) {
                        this.mSygicMain.Force3DBlit();
                    }
                    DisplayMetrics dm = new DisplayMetrics();
                    ((WindowManager) getSystemService("window")).getDefaultDisplay().getMetrics(dm);
                    SygicMain.nativeSetDPI(dm.xdpi);
                    this.mSygicMain.getFeature().getCommonFeature().registerBatteryReceiver();
                    if (this.mSygicMain.getFeature().getNetFeature().isConnected()) {
                        LocationService.updateAGPSData(this);
                    }
                    if (!ProjectsConsts.getBoolean(6)) {
                        new Handler().post(new C11201());
                    }
                    this.mSygicMain.delegateActivityMethod(1);
                    return;
                }
                sendEvent("com.sygic.intent.action.noSdcard", getString(C1799R.string.message_sdcard_missing));
            } catch (Exception e) {
                e.printStackTrace();
                stopSelf();
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            stopSelf();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "Sygic service starting...");
        if (this.mSygicMain == null) {
            return 2;
        }
        this.mThread = new SygicNaviThread(this);
        this.mThread.setSygicPaths(isAsSdk(this));
        this.mSygicMain.delegateActivityMethod(3);
        return 1;
    }

    public void onDestroy() {
        Log.d(LOG_TAG, "Sygic service destroyed...");
        sendEvent("com.sygic.intent.action.onDestroy", "Sygic service onDestroy() called");
        super.onDestroy();
    }

    public static void setProjectSettings() {
        if (isAsSdk(SygicMain.getActivity())) {
            ProjectsConsts.setBoolean(4, false);
            ProjectsConsts.setBoolean(0, false);
            ProjectsConsts.setString(10, "SygicExt");
            ProjectsConsts.setBoolean(3, false);
            ProjectsConsts.setBoolean(2, false);
            return;
        }
        try {
            Class<?> c = Class.forName(SygicMain.getActivity().getPackageName() + ".SygicProject");
            if (c != null) {
                Method m = c.getMethod("setConsts", (Class[]) null);
                if (m != null) {
                    m.invoke(null, (Object[]) null);
                }
            }
        } catch (ClassNotFoundException e) {
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
        } catch (IllegalAccessException e5) {
            e5.printStackTrace();
        } catch (InvocationTargetException e6) {
            e6.printStackTrace();
        }
    }

    public void setSurface(SurfaceView surface) {
        SygicMain.setSurface(surface);
    }

    public void setActivity(Activity act) {
        SygicMain.setActivity(act);
        SygicMain.initFeature();
    }

    public void setHandler(Handler handler) {
        SygicMain.setHandler(handler);
    }

    protected void addDummyEdit() {
        Activity act = SygicMain.getActivity();
        ExtendedEditText et = new ExtendedEditText(this);
        et.setImeOptions(268435456);
        et.setVisibility(4);
        act.addContentView(et, new LayoutParams(-2, -2));
        this.mSygicMain.getFeature().getCommonFeature().setEditText(et);
    }

    protected boolean hasRemote() {
        return this.mRemoteSdkService != null;
    }

    protected static boolean isAsSdk(Context context) {
        String packageName = context.getPackageName();
        return (packageName.contains("com.sygic.aura") || packageName.equals("com.sygic.truck") || packageName.equals("com.sygic.gps") || packageName.equals("com.sygic.amazon") || packageName.equals("cz.aponia.bor3") || packageName.equals("com.sygic.incar") || packageName.equals("com.sygic.bike")) ? false : true;
    }

    public int runNavi() {
        Log.d(LOG_TAG, "runNavi()");
        if (this.mThread == null || !this.mThread.isAlive()) {
            new Thread(new C11222()).start();
        }
        return 0;
    }

    protected void startThread() {
        if (!ProjectsConsts.getBoolean(6)) {
            SygicMain.getHandler().post(new C11233());
        }
        this.mThread.start();
        if (ProjectsConsts.getBoolean(6)) {
            this.mThread.getCoreHandler().postDelayed(this.mRunAppCycle, 10);
        }
    }

    protected void finishNavi() {
        finishNavi(false);
    }

    protected void finishNavi(boolean bFromNativeLoop) {
        if (this.mSygicMain != null) {
            if (bFromNativeLoop) {
                if (this.mThread != null) {
                    this.mThread.quit();
                    this.mThread = null;
                }
                if (!this.mIsFinishing) {
                    SygicMain.getActivity().finish();
                    return;
                }
                return;
            }
            this.mIsFinishing = true;
            if (!this.mEventExit) {
                this.mSygicMain.stopCore();
            }
            if (this.mThread != null) {
                this.mThread.join();
            }
            GuiUtils.cancelNotification(this, 1);
            this.mSygicMain.getFeature().getCommonFeature().unregisterBatteryReceiver();
            this.mSygicMain.delegateActivityMethod(7);
            this.mSygicMain.getFeature().getStoreFeature().stopService();
            Utils.stopLogs();
        }
    }

    protected void finish() {
        if (this.mEventExit) {
            this.mApiInitializer.closeApi();
        }
        sendBroadcast(new Intent("com.sygic.intent.action.finish"));
    }

    protected boolean wasNaviStarted() {
        if (this.mThread != null) {
            return this.mThread.wasStarted();
        }
        return false;
    }

    protected void setWebLink() {
        this.mThread.addArgs("-i");
    }

    public void sendEvent(String strIntentEvent, String strExtraMsg) {
        Intent i = new Intent(strIntentEvent);
        i.putExtra("com.sygic.intent.extra.actionMsg", Utils.getSygicString((Context) this, strExtraMsg));
        sendBroadcast(i);
    }

    protected void setNativeRunnable(Runnable run) {
        this.mRunAppCycle = run;
    }

    public boolean isRunning() {
        return this.mThread.isAlive();
    }

    public synchronized void waitForPaths() {
        if (this.mThread != null) {
            this.mThread.waitForPaths();
        } else {
            Log.d(LOG_TAG, "----- Wait thread doesn't exists!!!");
        }
    }

    public Handler getCoreHandler() {
        return this.mThread.getCoreHandler();
    }

    private void startProgress() {
        Activity activity = SygicMain.getActivity();
        if (activity != null) {
            if (this.mStartProgressBar == null) {
                this.mStartProgressBar = new ProgressBar(activity);
                ViewGroup layout = (ViewGroup) activity.findViewById(C1799R.id.surface).getParent();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
                RelativeLayout rl = new RelativeLayout(activity);
                rl.setGravity(17);
                rl.addView(this.mStartProgressBar);
                layout.addView(rl, params);
            }
            this.mStartProgressBar.setVisibility(0);
        }
    }

    private void stopProgress() {
        if (this.mStartProgressBar != null) {
            this.mStartProgressBar.setVisibility(8);
        }
    }

    private void initSdk() {
        boolean bIsApi = new File(this.mSygicMain.getFeature().getCommonFeature().getLibPath() + "/libApplicationAPI.so").exists();
        File driver = new File(this.mSygicMain.getFeature().getCommonFeature().getLibPath() + "/libsdkdriver.so");
        boolean bIsCfi = driver != null && driver.length() < 10000;
        if (bIsApi && !bIsCfi) {
            Log.d(LOG_TAG, "Initializing Sygic SDK");
            this.mApiInitializer.initSdk(hasRemote());
        }
    }
}
