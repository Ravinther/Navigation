package com.sygic.aura;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceView;
import com.sygic.aura.utils.FileUtils;
import com.sygic.aura.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;

public class SygicNaviThread {
    private static final String TAG;
    private final ArrayList<String> mArgsList;
    private Handler mCoreHandler;
    private volatile boolean mPathsSet;
    private final SygicService mService;
    private String mStrArgs;
    private SurfaceView mSurface;
    private final SygicMain mSygicMain;
    private Thread mSygicThread;
    private boolean mWasStarted;

    /* renamed from: com.sygic.aura.SygicNaviThread.1 */
    class C11171 implements Runnable {
        final /* synthetic */ boolean val$isAsSdk;

        C11171(boolean z) {
            this.val$isAsSdk = z;
        }

        public void run() {
            String strSygicDir = ProjectsConsts.getString(10);
            if (strSygicDir != null) {
                Utils.setSygicDirName(strSygicDir);
            }
            Utils.setSygicDirPath(SygicPreferences.getSygicDirPath(SygicNaviThread.this.mService));
            if (!(this.val$isAsSdk || ProjectsConsts.getString(10) != null || SygicNaviThread.this.mService.getPackageName().equals(Utils.getBaseClassName()))) {
                Utils.setSygicDirName(Utils.getSygicDirName() + SygicNaviThread.this.mService.getPackageName().substring(Utils.getBaseClassName().length()));
            }
            if (!FileUtils.fileExists(Utils.getSygicDirPath())) {
                Utils.setSygicDirPath(null);
                SygicPreferences.setSygicDirPath(SygicNaviThread.this.mService, Utils.getSygicDirPath());
            } else if (SygicPreferences.getSygicDirPath(SygicNaviThread.this.mService) == null) {
                SygicPreferences.setSygicDirPath(SygicNaviThread.this.mService, Utils.getSygicDirPath());
            }
            synchronized (SygicNaviThread.this) {
                SygicNaviThread.this.mPathsSet = true;
                SygicNaviThread.this.notifyAll();
            }
        }
    }

    /* renamed from: com.sygic.aura.SygicNaviThread.2 */
    class C11182 implements Runnable {
        C11182() {
        }

        public void run() {
            SygicNaviThread.this.runSygic();
        }
    }

    /* renamed from: com.sygic.aura.SygicNaviThread.3 */
    class C11193 implements Runnable {
        C11193() {
        }

        public void run() {
            SygicNaviThread.this.runSygic();
        }
    }

    static {
        TAG = SygicNaviThread.class.getCanonicalName();
    }

    public SygicNaviThread(SygicService service) {
        this.mStrArgs = "";
        this.mWasStarted = false;
        this.mPathsSet = false;
        this.mService = service;
        this.mSygicMain = SygicMain.getInstance();
        this.mArgsList = new ArrayList();
    }

    public void setPath(String strPath) {
        Log.d(TAG, "Setting Sygic path to: " + strPath);
        this.mArgsList.add("-cwd=" + strPath + "/" + Utils.getIniFilesDir());
    }

    public void setSygicPaths(boolean isAsSdk) {
        new Thread(new C11171(isAsSdk)).start();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitForPaths() {
        /*
        r2 = this;
        monitor-enter(r2);
    L_0x0001:
        r1 = r2.mPathsSet;	 Catch:{ all -> 0x000e }
        if (r1 != 0) goto L_0x0011;
    L_0x0005:
        r2.wait();	 Catch:{ InterruptedException -> 0x0009 }
        goto L_0x0001;
    L_0x0009:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x000e }
        goto L_0x0001;
    L_0x000e:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x000e }
        throw r1;
    L_0x0011:
        monitor-exit(r2);	 Catch:{ all -> 0x000e }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.SygicNaviThread.waitForPaths():void");
    }

    public void start() {
        if (ProjectsConsts.getBoolean(6)) {
            this.mSygicThread = new HandlerThread("core_thread");
            this.mSygicThread.start();
            SygicMain.setCoreHandler(getCoreHandler());
            this.mCoreHandler.post(new C11182());
        } else {
            this.mSygicThread = new Thread(new C11193());
            this.mSygicThread.start();
        }
        this.mWasStarted = true;
    }

    public void quit() {
        if (this.mSygicThread != null && (this.mSygicThread instanceof HandlerThread)) {
            ((HandlerThread) this.mSygicThread).quit();
            this.mSygicThread = null;
        }
    }

    public void join() {
        if (this.mSygicThread != null) {
            try {
                this.mSygicThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.mWasStarted = false;
    }

    public boolean isAlive() {
        if (this.mSygicThread != null) {
            return this.mSygicThread.isAlive();
        }
        return wasStarted();
    }

    public Handler getCoreHandler() {
        if (ProjectsConsts.getBoolean(6)) {
            if (this.mCoreHandler == null) {
                this.mCoreHandler = new Handler(((HandlerThread) this.mSygicThread).getLooper());
            }
            return this.mCoreHandler;
        }
        throw new IllegalStateException("Not a NATIVE_UI");
    }

    public void runSygic() {
        if (this.mSygicMain == null) {
            this.mService.finishNavi();
            return;
        }
        SygicMain.getInstance().getFeature().getGlFeature().waitForSurfaceCration(200);
        if (SygicPreferences.getGlCapabilities(this.mService) == 0.0f || SygicPreferences.getGlCapabilities(this.mService) == 1.0f) {
            if (checkGlCapabilities()) {
                SygicPreferences.setGlCapabilities(this.mService, 1.1f);
            } else {
                SygicPreferences.setGlCapabilities(this.mService, 1.0f);
            }
        }
        if (SygicPreferences.getGlCapabilities(this.mService) == 1.0f) {
            this.mSygicMain.SetSwRenderer(true);
        }
        if (FileUtils.fileExists(Utils.getSygicDirPath() + "/" + Utils.getIniFilesDir() + "/sw3d.file")) {
            this.mSygicMain.SetSwRenderer(true);
        }
        if (this.mSurface == null) {
            this.mSurface = this.mSygicMain.getFeature().getGlFeature().getSurface();
        }
        this.mStrArgs += " -r" + this.mSurface.getWidth() + "x" + this.mSurface.getHeight();
        Iterator it = this.mArgsList.iterator();
        while (it.hasNext()) {
            this.mStrArgs += " " + ((String) it.next());
        }
        this.mSygicMain.getFeature().getGlFeature().setInBackground(false);
        this.mSygicMain.HelperWinMain(this.mStrArgs);
        if (!ProjectsConsts.getBoolean(6)) {
            finishSygic();
        }
    }

    protected boolean wasStarted() {
        return this.mWasStarted;
    }

    protected void finishSygic() {
        this.mService.finish();
        this.mSygicMain.getFeature().getGlFeature().finish();
    }

    private boolean checkGlCapabilities() {
        return this.mSygicMain.getFeature().getGlFeature().checkGles();
    }

    protected void addArgs(String strArgs) {
        this.mArgsList.add(strArgs);
    }
}
