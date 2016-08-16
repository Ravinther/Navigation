package com.sygic.aura;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup;
import com.crashlytics.android.Crashlytics;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.sygic.aura.downloader.Downloader;
import com.sygic.aura.downloader.DownloaderActivity;
import com.sygic.aura.events.ActivityEventListener;
import com.sygic.aura.events.ActivityUserInteractionListener;
import com.sygic.aura.events.key.KeyEventService;
import com.sygic.aura.feature.automotive.BoschMySpin;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.utils.FileUtils;
import com.sygic.aura.utils.GuiUtils.CustomDialogFragment;
import com.sygic.aura.utils.NotificationReceiver.RetentionNotificationUtils;
import com.sygic.aura.utils.Utils;
import com.sygic.base.C1799R;
import java.io.File;
import java.io.IOException;

public class SygicActivityWrapper extends ActionBarActivity {
    private static final String LOG_TAG;
    private transient AlertDialog mAlertDialog;
    protected transient Intent mBindIntent;
    private final ServiceConnection mConnection;
    private transient SygicActivityWrapper mDelegate;
    private transient boolean mDownloaderFinished;
    private transient IntentFilter mFilter;
    private final Handler mHandler;
    private transient boolean mInBackground;
    private boolean mIsLoaded;
    private boolean mIsRestarting;
    private boolean mIsReturningFromDownloader;
    private transient ServiceReceiver mReceiver;
    private final Runnable mRunAppCycle;
    private transient SygicService mService;
    private transient boolean mServiceConnected;
    private boolean mStartFromCustomUrl;
    private transient SurfaceView mSurface;

    /* renamed from: com.sygic.aura.SygicActivityWrapper.1 */
    class C10931 implements Runnable {

        /* renamed from: com.sygic.aura.SygicActivityWrapper.1.1 */
        class C10921 implements ExecutionOrder {
            C10921() {
            }

            public boolean runningCondition() {
                return SygicMain.isNativeLoopEnabled();
            }

            public boolean onPositive() {
                SygicActivityWrapper.this.mHandler.post(SygicActivityWrapper.this.mRunAppCycle);
                return false;
            }

            public boolean onNegative() {
                return true;
            }
        }

        C10931() {
        }

        public void run() {
            if (!SygicMain.isNativeLoopEnabled()) {
                new RepeatingThread(new C10921(), 200).start();
            } else if (SygicMain.getInstance().DoAppCycle() <= 0) {
                SygicActivityWrapper.this.mService.finishNavi(true);
                SygicMain.getInstance().SetIsRunning(false);
                super.finish();
            } else if (SygicActivityWrapper.this.mService != null) {
                SygicActivityWrapper.this.mService.getCoreHandler().post(SygicActivityWrapper.this.mRunAppCycle);
            }
        }
    }

    /* renamed from: com.sygic.aura.SygicActivityWrapper.2 */
    class C10952 implements ServiceConnection {

        /* renamed from: com.sygic.aura.SygicActivityWrapper.2.1 */
        class C10941 implements Runnable {
            C10941() {
            }

            public void run() {
                SygicActivityWrapper.this.mService.waitForPaths();
                Downloader.processRemoves(SygicActivityWrapper.this);
                if (ProjectsConsts.getBoolean(1)) {
                    SygicActivityWrapper.this.checkLicense();
                } else {
                    SygicActivityWrapper.this.checkDownloader();
                }
            }
        }

        C10952() {
        }

        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(SygicActivityWrapper.LOG_TAG, "SygicService connected");
            SygicActivityWrapper.this.mService = (SygicService) ((LocalBinder) service).getService();
            if (FileUtils.isSDCardPresent() && SygicActivityWrapper.this.mService != null && !SygicActivityWrapper.this.mServiceConnected) {
                SygicActivityWrapper.this.mServiceConnected = true;
                SygicActivityWrapper.this.mService.setActivity(SygicActivityWrapper.this);
                SygicActivityWrapper.this.mService.setSurface(SygicActivityWrapper.this.mSurface);
                SygicActivityWrapper.this.mService.setHandler(SygicActivityWrapper.this.mHandler);
                SygicActivityWrapper.this.mService.addDummyEdit();
                SygicActivityWrapper.this.mService.setNativeRunnable(SygicActivityWrapper.this.mRunAppCycle);
                if (SygicMain.getInstance().getFeature().getCommonFeature().handleWebLink(SygicActivityWrapper.this.getIntent())) {
                    SygicActivityWrapper.this.mService.setWebLink();
                    SygicActivityWrapper.this.mStartFromCustomUrl = true;
                }
                if (SygicActivityWrapper.this.mIsReturningFromDownloader) {
                    SygicActivityWrapper.this.mIsReturningFromDownloader = false;
                } else {
                    new Thread(new C10941()).start();
                }
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(SygicActivityWrapper.LOG_TAG, "SygicService disconnected");
            SygicActivityWrapper.this.mService = null;
            SygicActivityWrapper.this.mServiceConnected = false;
        }
    }

    /* renamed from: com.sygic.aura.SygicActivityWrapper.3 */
    class C10963 implements Runnable {
        C10963() {
        }

        public void run() {
            SygicMain.getInstance().RequestSurfaceReset();
        }
    }

    /* renamed from: com.sygic.aura.SygicActivityWrapper.4 */
    class C10974 implements Runnable {
        final /* synthetic */ Intent val$data;
        final /* synthetic */ int val$requestCode;
        final /* synthetic */ int val$resultCode;

        C10974(int i, int i2, Intent intent) {
            this.val$requestCode = i;
            this.val$resultCode = i2;
            this.val$data = intent;
        }

        public void run() {
            SygicActivityWrapper.this.handleOnActivityResult(this.val$requestCode, this.val$resultCode, this.val$data);
        }
    }

    /* renamed from: com.sygic.aura.SygicActivityWrapper.5 */
    class C10995 implements Callback {
        final /* synthetic */ Intent val$data;
        final /* synthetic */ int val$requestCode;
        final /* synthetic */ int val$resultCode;
        final /* synthetic */ SurfaceHolder val$surfaceHolder;

        /* renamed from: com.sygic.aura.SygicActivityWrapper.5.1 */
        class C10981 implements Runnable {
            C10981() {
            }

            public void run() {
                SygicActivityWrapper.this.handleOnActivityResult(C10995.this.val$requestCode, C10995.this.val$resultCode, C10995.this.val$data);
            }
        }

        C10995(SurfaceHolder surfaceHolder, int i, int i2, Intent intent) {
            this.val$surfaceHolder = surfaceHolder;
            this.val$requestCode = i;
            this.val$resultCode = i2;
            this.val$data = intent;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        public void surfaceCreated(SurfaceHolder holder) {
            this.val$surfaceHolder.removeCallback(this);
            SygicActivityWrapper.this.mHandler.post(new C10981());
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }

    /* renamed from: com.sygic.aura.SygicActivityWrapper.6 */
    class C11056 implements LicenseCheckerCallback {

        /* renamed from: com.sygic.aura.SygicActivityWrapper.6.1 */
        class C11011 implements OnClickListener {

            /* renamed from: com.sygic.aura.SygicActivityWrapper.6.1.1 */
            class C11001 implements Runnable {
                C11001() {
                }

                public void run() {
                    SygicActivityWrapper.this.checkLicense();
                }
            }

            C11011() {
            }

            public void onClick(DialogInterface dialog, int which) {
                SygicActivityWrapper.this.mHandler.post(new C11001());
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.6.2 */
        class C11022 implements OnClickListener {
            C11022() {
            }

            public void onClick(DialogInterface dialog, int which) {
                SygicActivityWrapper.this.finish();
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.6.3 */
        class C11033 implements OnCancelListener {
            C11033() {
            }

            public void onCancel(DialogInterface dialog) {
                SygicActivityWrapper.this.finish();
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.6.4 */
        class C11044 implements Runnable {
            C11044() {
            }

            public void run() {
                SygicActivityWrapper.this.checkDownloader();
            }
        }

        C11056() {
        }

        public void dontAllow(int reason) {
            if (!SygicActivityWrapper.this.isFinishing()) {
                Log.d(SygicActivityWrapper.LOG_TAG, "LicenseChecker: dontAllow - " + reason);
                CustomDialogFragment dialog = CustomDialogFragment.newInstance(SygicActivityWrapper.this, C1799R.string.title_warning, C1799R.string.message_nolicense, C1799R.string.button_quit, C1799R.string.button_retry);
                dialog.setOnPositiveBtnClick(new C11011());
                dialog.setOnNegativeBtnClick(new C11022());
                dialog.setOnCancelListener(new C11033());
                dialog.showDialog("dialog_license");
            }
        }

        public void applicationError(int errorCode) {
            if (!SygicActivityWrapper.this.isFinishing()) {
                Log.d(SygicActivityWrapper.LOG_TAG, "LicenseChecker: applicationError - " + errorCode);
            }
        }

        public void allow(int reason) {
            if (!SygicActivityWrapper.this.isFinishing()) {
                SygicActivityWrapper.this.mHandler.post(new C11044());
                SygicPreferences.setLicensed(SygicActivityWrapper.this, true);
            }
        }
    }

    /* renamed from: com.sygic.aura.SygicActivityWrapper.7 */
    class C11067 implements OnClickListener {
        C11067() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SygicActivityWrapper.this.finish();
        }
    }

    private class ServiceReceiver extends BroadcastReceiver {

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.1 */
        class C11071 implements OnClickListener {
            C11071() {
            }

            public void onClick(DialogInterface dialog, int which) {
                SygicActivityWrapper.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 211);
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.2 */
        class C11082 implements OnClickListener {
            C11082() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (SygicActivityWrapper.this.mService != null) {
                    SygicActivityWrapper.this.mService.startThread();
                }
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.3 */
        class C11093 implements OnClickListener {
            C11093() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (SygicActivityWrapper.this.mService == null || !SygicService.isAsSdk(SygicActivityWrapper.this)) {
                    SygicActivityWrapper.this.finish();
                }
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.4 */
        class C11104 implements OnClickListener {
            C11104() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (SygicActivityWrapper.this.mService == null || !SygicService.isAsSdk(SygicActivityWrapper.this)) {
                    SygicActivityWrapper.this.finish();
                }
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.5 */
        class C11115 implements OnClickListener {
            C11115() {
            }

            public void onClick(DialogInterface dialog, int which) {
                SygicActivityWrapper.this.startActivity(new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS"));
                SygicActivityWrapper.this.finish();
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.6 */
        class C11126 implements OnClickListener {
            C11126() {
            }

            public void onClick(DialogInterface dialog, int which) {
                SygicActivityWrapper.this.finish();
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.7 */
        class C11137 implements OnClickListener {
            C11137() {
            }

            public void onClick(DialogInterface dialog, int which) {
                SygicActivityWrapper.this.finish();
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.8 */
        class C11148 implements OnClickListener {
            final /* synthetic */ String val$strAppDir;

            C11148(String str) {
                this.val$strAppDir = str;
            }

            public void onClick(DialogInterface dialog, int which) {
                ServiceReceiver.this.copySygicDir(this.val$strAppDir);
            }
        }

        /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.9 */
        class C11169 extends AsyncTask<String, Integer, Boolean> {
            final /* synthetic */ ProgressDialog val$pbDialog;
            final /* synthetic */ String val$strAppDir;

            /* renamed from: com.sygic.aura.SygicActivityWrapper.ServiceReceiver.9.1 */
            class C11151 implements Runnable {
                C11151() {
                }

                public void run() {
                    SygicActivityWrapper.this.mService.runNavi();
                }
            }

            C11169(ProgressDialog progressDialog, String str) {
                this.val$pbDialog = progressDialog;
                this.val$strAppDir = str;
            }

            protected Boolean doInBackground(String... params) {
                String strDir = params[0];
                if (!TextUtils.isEmpty(strDir)) {
                    try {
                        copyDirs(Utils.getSygicDirPath(), strDir);
                        Utils.setSygicDirPath(strDir);
                        SygicPreferences.setSygicDirPath(SygicActivityWrapper.this.mService, Utils.getSygicDirPath());
                        SygicActivityWrapper.this.mHandler.post(new C11151());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Crashlytics.log("testing freeze at splash screen");
                        Crashlytics.logException(e);
                        return Boolean.valueOf(false);
                    }
                }
                return Boolean.valueOf(true);
            }

            protected void onPreExecute() {
                if (this.val$pbDialog != null) {
                    this.val$pbDialog.setProgressStyle(1);
                    this.val$pbDialog.setCancelable(false);
                    this.val$pbDialog.setMax(FileUtils.getFileCount(new File(Utils.getSygicDirPath())));
                    this.val$pbDialog.setTitle(SygicActivityWrapper.this.getString(C1799R.string.title_copying));
                    this.val$pbDialog.setMessage(SygicActivityWrapper.this.getString(C1799R.string.message_pleasewait));
                    this.val$pbDialog.show();
                }
            }

            protected void onPostExecute(Boolean result) {
                if (this.val$pbDialog != null) {
                    this.val$pbDialog.dismiss();
                    if (!result.booleanValue()) {
                        ServiceReceiver.this.showCopyErrorDialog(Utils.getSygicString(SygicActivityWrapper.this, C1799R.string.message_copy_error), this.val$strAppDir);
                    }
                }
            }

            protected void onProgressUpdate(Integer... values) {
                if (this.val$pbDialog != null) {
                    this.val$pbDialog.setProgress(this.val$pbDialog.getProgress() + values[0].intValue());
                }
            }

            private void copyDirs(String strFrom, String strTo) throws IOException {
                File directory = new File(strFrom);
                if (!directory.exists()) {
                    throw new IOException(String.format("Error copying files from '%s'. File does not exist.", new Object[]{strFrom}));
                } else if (directory.isDirectory()) {
                    for (File file : directory.listFiles()) {
                        String strDesc = strTo.concat("/").concat(file.getName());
                        if (file.isFile()) {
                            FileUtils.copyFile(file.getAbsolutePath(), strDesc);
                            publishProgress(new Integer[]{Integer.valueOf(1)});
                        } else if (file.isDirectory()) {
                            copyDirs(file.getAbsolutePath(), strDesc);
                        }
                    }
                } else {
                    throw new IOException(String.format("Error copying files from '%s'. Is not a directory.", new Object[]{strFrom}));
                }
            }
        }

        private ServiceReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Log.d(SygicActivityWrapper.LOG_TAG, "Broadcast received: " + intent.getAction());
            String strAction = intent.getAction();
            CustomDialogFragment dialog;
            if ("com.sygic.intent.action.closedGps".equals(strAction)) {
                if (!BoschMySpin.INSTANCE.isConnected() || SygicActivityWrapper.this.mService == null) {
                    dialog = CustomDialogFragment.newInstance(SygicActivityWrapper.this, C1799R.string.title_gps_warning, C1799R.string.message_gps_warning, C1799R.string.button_cancel, C1799R.string.button_settings);
                    dialog.setOnPositiveBtnClick(new C11071());
                    dialog.setOnNegativeBtnClick(new C11082());
                    dialog.showDialog("dialog_gps");
                    return;
                }
                SygicActivityWrapper.this.mService.startThread();
            } else if ("com.sygic.intent.action.finish".equals(strAction)) {
                SygicActivityWrapper.this.finish();
            } else if ("com.sygic.intent.action.noDir".equals(strAction)) {
                dialog = CustomDialogFragment.newInstance(SygicActivityWrapper.this, Utils.getSygicString(SygicActivityWrapper.this, C1799R.string.message_inifiles_missing), SygicActivityWrapper.this.getString(C1799R.string.button_cancel));
                dialog.setOnPositiveBtnClick(new C11093());
                dialog.showDialog("dialog_no_dir");
            } else if ("com.sygic.intent.action.noSdcard".equals(strAction)) {
                dialog = CustomDialogFragment.newInstance(SygicActivityWrapper.this, Utils.getSygicString(SygicActivityWrapper.this, C1799R.string.message_sdcard_missing), SygicActivityWrapper.this.getString(C1799R.string.button_cancel));
                dialog.setOnPositiveBtnClick(new C11104());
                dialog.showDialog("dialog_no_sdcard");
            } else if ("com.sygic.intent.action.onDestroy".equals(strAction)) {
                SygicActivityWrapper.this.mService = null;
            } else if ("com.sygic.intent.action.notWritable".equals(strAction)) {
                String strAppDir;
                String strMsg = Utils.getSygicString(SygicActivityWrapper.this, C1799R.string.message_not_writable);
                File dir = FileUtils.getExternalStorage(SygicActivityWrapper.this.mService);
                if (dir != null) {
                    strAppDir = dir.getAbsolutePath().concat("/").concat(Utils.getSygicDirName());
                } else {
                    strAppDir = null;
                }
                showCopyErrorDialog(strMsg, strAppDir);
            } else if ("com.sygic.intent.action.hwAccelerated".equals(strAction)) {
                dialog = CustomDialogFragment.newInstance(SygicActivityWrapper.this, C1799R.string.anui_hw_acc_title, C1799R.string.anui_hw_acc_message, C1799R.string.button_quit, C1799R.string.button_settings);
                dialog.setOnPositiveBtnClick(new C11115());
                dialog.setOnNegativeBtnClick(new C11126());
                dialog.showDialog("dialog_hw_accelerated");
            }
        }

        private void showCopyErrorDialog(String strMsg, String strAppDir) {
            CustomDialogFragment dialog = CustomDialogFragment.newInstance(SygicActivityWrapper.this, strMsg, SygicActivityWrapper.this.getString(C1799R.string.button_cancel), SygicActivityWrapper.this.getString(C1799R.string.button_ok));
            dialog.setOnNegativeBtnClick(new C11137());
            dialog.setOnPositiveBtnClick(new C11148(strAppDir));
            dialog.showDialog("dialog_no_writable");
        }

        private void copySygicDir(String strAppDir) {
            new C11169(new ProgressDialog(SygicActivityWrapper.this), strAppDir).execute(new String[]{strAppDir});
        }
    }

    public SygicActivityWrapper() {
        this.mInBackground = true;
        this.mDownloaderFinished = true;
        this.mServiceConnected = false;
        this.mIsLoaded = true;
        this.mStartFromCustomUrl = false;
        this.mIsRestarting = false;
        this.mIsReturningFromDownloader = false;
        this.mHandler = new Handler();
        this.mRunAppCycle = new C10931();
        this.mConnection = new C10952();
    }

    static {
        LOG_TAG = SygicActivityWrapper.class.getCanonicalName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        try {
            super.onCreate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mDelegate == null) {
            this.mDelegate = this;
        }
        try {
            SygicMain.setActivity(this);
            this.mDelegate.setWindowFlags();
            this.mDelegate.setContentView();
            this.mDelegate.getSurfaceView();
            this.mDelegate.setServiceReceiver();
            this.mDelegate.startService(this);
            setVolumeControlStream(ProjectsConsts.getInt(20));
            ActivityEventListener eventListener = SygicMain.getActivityEventListener();
            if (eventListener != null) {
                eventListener.onCreate(savedInstanceState);
            }
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
            loadLibraryFailed();
        } catch (NoClassDefFoundError e3) {
            e3.printStackTrace();
            loadLibraryFailed();
        } catch (ExceptionInInitializerError e4) {
            e4.printStackTrace();
            loadLibraryFailed();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mIsLoaded) {
            ActivityEventListener eventListener = SygicMain.getActivityEventListener();
            if (eventListener != null) {
                eventListener.onConfigChanged(newConfig);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (!this.mIsLoaded) {
            return false;
        }
        if (!SygicMain.getInstance().GetIsRunning()) {
            return super.onCreateOptionsMenu(menu);
        }
        if (menu == null || !super.onCreateOptionsMenu(menu)) {
            return false;
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        ActivityEventListener eventListener = SygicMain.getActivityEventListener();
        if (eventListener != null) {
            return eventListener.onOptionsItemSelected(item);
        }
        return false;
    }

    protected void onStart() {
        Log.d(LOG_TAG, "onStart()");
        super.onStart();
        if (this.mIsLoaded) {
            if (!this.mIsRestarting) {
                SygicMain.initInstance(this, this.mHandler);
            }
            if (!ProjectsConsts.getBoolean(6)) {
                SygicMain.setSurface(this.mSurface);
            }
            if (this.mService != null) {
                SygicMain.getInstance().delegateActivityMethod(3);
            }
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onSaveInstanceState(Bundle outState) {
        Log.d(LOG_TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    protected void onRestart() {
        Log.d(LOG_TAG, "onRestart()");
        super.onRestart();
        this.mIsRestarting = true;
    }

    protected void onResume() {
        Log.d(LOG_TAG, "onResume()");
        super.onResume();
        if (this.mIsLoaded) {
            this.mDelegate.registerReceiver();
            this.mDelegate.bindService();
            this.mInBackground = false;
            if (this.mService != null && this.mService.wasNaviStarted() && SygicMain.hasSurface()) {
                SygicMain.getInstance().getFeature().getGlFeature().setInBackground(false);
                this.mHandler.postDelayed(new C10963(), 200);
            }
            SygicMain.getInstance().delegateActivityMethod(4);
            ActivityEventListener eventListener = SygicMain.getActivityEventListener();
            if (eventListener != null) {
                eventListener.onResume();
            }
        }
    }

    protected void onPause() {
        Log.d(LOG_TAG, "onPause()");
        super.onPause();
        if (this.mIsLoaded) {
            ActivityEventListener eventListener = SygicMain.getActivityEventListener();
            if (eventListener != null) {
                eventListener.onPause();
            }
            if (this.mService != null && this.mService.wasNaviStarted() && SygicMain.hasSurface()) {
                SygicMain.getInstance().delegateActivityMethod(5);
                SygicMain.nativeSysSetRunningBackground(true);
                SygicMain.getInstance().getFeature().getGlFeature().setInBackground(true);
                SygicMain.getInstance().getFeature().getCommonFeature().showKeyboard(false);
                SygicMain.getInstance().getFeature().getCommonFeature().showNotification(getString(C1799R.string.app_name), Utils.getSygicString((Context) this, C1799R.string.message_notification_sygic));
            }
            this.mInBackground = true;
            this.mDelegate.unbindService();
            this.mDelegate.unregisterReceiver();
        }
    }

    protected void onStop() {
        Log.d(LOG_TAG, "onStop()");
        super.onStop();
        if (this.mIsLoaded) {
            SygicMain.getInstance().delegateActivityMethod(6);
        }
    }

    public void finish() {
        if (this.mService == null) {
            super.finish();
            return;
        }
        this.mService.finishNavi();
        if (!this.mService.wasNaviStarted()) {
            super.finish();
        }
    }

    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy()");
        if (this.mAlertDialog != null) {
            this.mAlertDialog.dismiss();
            this.mAlertDialog = null;
        }
        if (!(this.mService == null || this.mService.hasRemote() || !this.mDownloaderFinished)) {
            this.mService.stopSelf();
        }
        this.mServiceConnected = false;
        ActivityEventListener eventListener = SygicMain.getActivityEventListener();
        if (eventListener != null) {
            eventListener.onDestroy();
        }
        super.onDestroy();
        if (!this.mIsLoaded) {
            Process.killProcess(Process.myPid());
        }
    }

    protected void onNewIntent(Intent intent) {
        if (intent != null) {
            if (!SygicMain.getInstance().getFeature().getCommonFeature().handleWebLink(intent)) {
                ActivityEventListener eventListener = SygicMain.getActivityEventListener();
                if (eventListener != null) {
                    eventListener.onNewIntent(intent);
                }
            }
            setIntent(intent);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            SygicMain.nativeSurfaceRotate(-1, -1, getWindowManager().getDefaultDisplay().getOrientation());
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.mInBackground || event.getAction() != 0) {
            return super.onKeyDown(keyCode, event);
        }
        if (ProjectsConsts.getBoolean(6) && keyCode == 4) {
            event.startTracking();
            return true;
        }
        KeyEventService keyListener = SygicMain.getKeyListener();
        if (keyListener != null) {
            return keyListener.onKeyDown(keyCode, event);
        }
        if (ProjectsConsts.getBoolean(6)) {
            return super.onKeyDown(keyCode, event);
        }
        return SygicMain.getInstance().getEventService().onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (this.mInBackground || event.getAction() != 1) {
            return super.onKeyUp(keyCode, event);
        }
        if (ProjectsConsts.getBoolean(6) && keyCode == 4 && event.isTracking() && !event.isCanceled()) {
            onBackPressed();
            return true;
        }
        KeyEventService keyListener = SygicMain.getKeyListener();
        if (keyListener != null) {
            return keyListener.onKeyUp(keyCode, event);
        }
        if (ProjectsConsts.getBoolean(6)) {
            return super.onKeyUp(keyCode, event);
        }
        return SygicMain.getInstance().getEventService().onKeyUp(keyCode, event);
    }

    public void onBackPressed() {
        KeyEventService keyListener = SygicMain.getKeyListener();
        if (keyListener == null || !keyListener.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 211:
                if (this.mService != null) {
                    this.mService.startThread();
                }
            case 215:
                boolean consumed = false;
                if (!(resultCode != -1 || SygicMain.getCoreEventsListener() == null || data == null)) {
                    String position = SygicMain.getCoreEventsListener().onPhotoChosen(data.getData());
                    if (position != null) {
                        SygicMain.getInstance().SetArguments("com.sygic.aura://coordinate|" + position + "|drive");
                    }
                    consumed = true;
                }
                if (!consumed) {
                    SygicMain.getInstance().delegateResult(requestCode, resultCode, data);
                }
            case 222:
                if (resultCode == -1) {
                    if (this.mService != null) {
                        this.mService.runNavi();
                    }
                } else if (this.mService == null || !SygicService.isAsSdk(this)) {
                    finish();
                }
                this.mDownloaderFinished = true;
            default:
                SygicMain.getInstance().delegateResult(requestCode, resultCode, data);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 222:
                this.mIsReturningFromDownloader = true;
                break;
            case 227:
            case 228:
                SygicMain.getInstance().delegateResult(requestCode, resultCode, data);
                break;
        }
        SurfaceView surfaceView = SygicMain.getSurface();
        if (surfaceView == null) {
            surfaceView = this.mSurface;
        }
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (surfaceHolder.getSurface().isValid()) {
            this.mHandler.post(new C10974(requestCode, resultCode, data));
        } else {
            surfaceHolder.addCallback(new C10995(surfaceHolder, requestCode, resultCode, data));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        ActivityUserInteractionListener eventListener = SygicMain.getActivityUserInteractionListener();
        if (eventListener != null) {
            eventListener.onUserInteraction();
        }
    }

    protected void setWindowFlags() {
        getWindow().setFlags(128, 128);
        if (SygicPreferences.getFullscreen(this)) {
            getWindow().setFlags(1024, 1024);
        }
        getWindow().setBackgroundDrawable(null);
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ActivityEventListener eventListener = SygicMain.getActivityEventListener();
        if (eventListener != null) {
            eventListener.onContentViewSet((ViewGroup) getWindow().getDecorView().getRootView());
        }
    }

    protected void setContentView() {
        setContentView(C1799R.layout.main);
    }

    protected void getSurfaceView() {
        this.mSurface = (SurfaceView) findViewById(C1799R.id.surface);
    }

    protected void setServiceReceiver() {
        this.mFilter = new IntentFilter();
        this.mFilter.addAction("com.sygic.intent.action.finish");
        this.mFilter.addAction("com.sygic.intent.action.closedGps");
        this.mFilter.addAction("com.sygic.intent.action.noDir");
        this.mFilter.addAction("com.sygic.intent.action.noSdcard");
        this.mFilter.addAction("com.sygic.intent.action.onDestroy");
        this.mFilter.addAction("com.sygic.intent.action.notWritable");
        this.mFilter.addAction("com.sygic.intent.action.hwAccelerated");
        this.mReceiver = new ServiceReceiver();
    }

    protected void startService(Context context) {
        if (SygicConsts.IS_PROTOTYPE) {
            SygicService.setProjectSettings();
            SygicMain.initInstance(this, this.mHandler);
            SygicMain.setActivity(this);
            SygicMain.initFeature();
            SygicMain.setSurface(this.mSurface);
            SygicMain.setHandler(this.mHandler);
            return;
        }
        this.mBindIntent = new Intent(context, SygicService.class);
        startService(this.mBindIntent);
    }

    protected void registerReceiver() {
        registerReceiver(this.mReceiver, this.mFilter);
    }

    protected void unregisterReceiver() {
        unregisterReceiver(this.mReceiver);
    }

    protected void bindService() {
        if (this.mBindIntent != null && this.mConnection != null) {
            bindService(this.mBindIntent, this.mConnection, 1);
        }
    }

    protected void unbindService() {
        if (this.mService != null) {
            unbindService(this.mConnection);
        }
    }

    private void checkLicense() {
        if (SygicPreferences.isLicensed(this)) {
            checkDownloader();
            return;
        }
        byte[] SALT = new byte[]{(byte) 32, (byte) 93, (byte) -56, (byte) -38, (byte) 94, (byte) 12, (byte) 22, (byte) -72, (byte) -65, (byte) 42, (byte) 92, (byte) -33, (byte) 1, (byte) -22, (byte) 71, (byte) -84, (byte) 19, (byte) 38, (byte) 82, (byte) -19};
        String PUBLIC_KEY = ProjectsConsts.getString(12);
        if (TextUtils.isEmpty(PUBLIC_KEY)) {
            PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgJvPKxtn4Sr0mSCg5w7sCRFKma3Sksv/WDbGT9lDVhYcCXOOvsbw91oSQ2LlEQcNRNQUL9qR2Ekn2AIbyKN6qKrGs7W1g0JIQsrQMGNK9GBPX29PYTAz5zSJy8JBRSLF4VKDb+S3mjazUaxC3AjJm9+JlDynG/y5BEBBQifM5u7G7ADP3IXzEimXaTaPpdBmZ/72dBZ0ksT+fcPjPbIA2weyKIvMgMSk9jyexHccaCDQMnSqE36ngdOuDn7fPOmY0J63Mi9OtN2yVIGYBsEDPKAeALZaE/E4N9U3OedNmGZProf9j+p33rX64HURavmA6N8HZtLVfcTKqhltxgwCTwIDAQAB";
        }
        new LicenseChecker(this, new ServerManagedPolicy(this, new AESObfuscator(SALT, getPackageName(), ((TelephonyManager) getSystemService("phone")).getDeviceId())), PUBLIC_KEY).checkAccess(new C11056());
    }

    private void checkDownloader() {
        if (!Downloader.isDownloaderNeeded() || Downloader.checkDownload(this)) {
            this.mService.runNavi();
            return;
        }
        startActivityForResult(new Intent(getBaseContext(), DownloaderActivity.class), 222);
        this.mDownloaderFinished = false;
    }

    private void loadLibraryFailed() {
        this.mIsLoaded = false;
        CustomDialogFragment dialog = CustomDialogFragment.newInstance(this, Utils.getSygicString((Context) this, C1799R.string.message_reinstall), getString(C1799R.string.button_ok));
        dialog.setOnPositiveBtnClick(new C11067());
        dialog.showDialog(this, "dialog_reinstall");
    }

    protected void dismissPendingNotification(Intent intent) {
        int notifId = intent.getIntExtra("pending_notif_id", -1);
        if (notifId != -1) {
            if (notifId == 3) {
                RetentionNotificationUtils.resetDismissCounter(this);
            }
            NotificationManager notifManager = (NotificationManager) getSystemService("notification");
            if (notifManager != null) {
                notifManager.cancel(notifId);
            }
        }
    }
}
