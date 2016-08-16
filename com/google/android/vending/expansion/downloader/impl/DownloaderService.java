package com.google.android.vending.expansion.downloader.impl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.util.Log;
import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderService;
import com.google.android.vending.expansion.downloader.IStub;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.APKExpansionPolicy;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import java.io.File;
import loquendo.tts.engine.TTSConst;

public abstract class DownloaderService extends CustomIntentService implements IDownloaderService {
    private static boolean sIsRunning;
    private PendingIntent mAlarmIntent;
    float mAverageDownloadSpeed;
    long mBytesAtSample;
    long mBytesSoFar;
    private Messenger mClientMessenger;
    private BroadcastReceiver mConnReceiver;
    private ConnectivityManager mConnectivityManager;
    private int mControl;
    int mFileCount;
    private boolean mIsAtLeast3G;
    private boolean mIsAtLeast4G;
    private boolean mIsCellularConnection;
    private boolean mIsConnected;
    private boolean mIsFailover;
    private boolean mIsRoaming;
    long mMillisecondsAtSample;
    private DownloadNotification mNotification;
    private PackageInfo mPackageInfo;
    private PendingIntent mPendingIntent;
    private final Messenger mServiceMessenger;
    private final IStub mServiceStub;
    private boolean mStateChanged;
    private int mStatus;
    long mTotalLength;
    private WifiManager mWifiManager;

    public static class GenerateSaveFileError extends Exception {
        String mMessage;
        int mStatus;

        public GenerateSaveFileError(int status, String message) {
            this.mStatus = status;
            this.mMessage = message;
        }
    }

    private class InnerBroadcastReceiver extends BroadcastReceiver {
        final Service mService;

        InnerBroadcastReceiver(Service service) {
            this.mService = service;
        }

        public void onReceive(Context context, Intent intent) {
            DownloaderService.this.pollNetworkState();
            if (DownloaderService.this.mStateChanged && !DownloaderService.isServiceRunning()) {
                Log.d("LVLDL", "InnerBroadcastReceiver Called");
                Intent fileIntent = new Intent(context, this.mService.getClass());
                fileIntent.putExtra("EPI", DownloaderService.this.mPendingIntent);
                context.startService(fileIntent);
            }
        }
    }

    private class LVLRunnable implements Runnable {
        final Context mContext;

        /* renamed from: com.google.android.vending.expansion.downloader.impl.DownloaderService.LVLRunnable.1 */
        class C10371 implements LicenseCheckerCallback {
            final /* synthetic */ APKExpansionPolicy val$aep;

            C10371(APKExpansionPolicy aPKExpansionPolicy) {
                this.val$aep = aPKExpansionPolicy;
            }

            public void allow(int reason) {
                try {
                    int count = this.val$aep.getExpansionURLCount();
                    DownloadsDB db = DownloadsDB.getDB(LVLRunnable.this.mContext);
                    int status = 0;
                    if (count != 0) {
                        for (int i = 0; i < count; i++) {
                            String currentFileName = this.val$aep.getExpansionFileName(i);
                            if (currentFileName != null) {
                                DownloadInfo di = new DownloadInfo(i, currentFileName, LVLRunnable.this.mContext.getPackageName());
                                long fileSize = this.val$aep.getExpansionFileSize(i);
                                if (DownloaderService.this.handleFileUpdated(db, i, currentFileName, fileSize)) {
                                    status |= -1;
                                    di.resetDownload();
                                    di.mUri = this.val$aep.getExpansionURL(i);
                                    di.mTotalBytes = fileSize;
                                    di.mStatus = status;
                                    db.updateDownload(di);
                                } else {
                                    DownloadInfo dbdi = db.getDownloadInfoByFileName(di.mFileName);
                                    if (dbdi == null) {
                                        Log.d("LVLDL", "file " + di.mFileName + " found. Not downloading.");
                                        di.mStatus = 200;
                                        di.mTotalBytes = fileSize;
                                        di.mCurrentBytes = fileSize;
                                        di.mUri = this.val$aep.getExpansionURL(i);
                                        db.updateDownload(di);
                                    } else if (dbdi.mStatus != 200) {
                                        dbdi.mUri = this.val$aep.getExpansionURL(i);
                                        db.updateDownload(dbdi);
                                        status |= -1;
                                    }
                                }
                            }
                        }
                    }
                    db.updateMetadata(LVLRunnable.this.mContext.getPackageManager().getPackageInfo(LVLRunnable.this.mContext.getPackageName(), 0).versionCode, status);
                    switch (DownloaderService.startDownloadServiceIfRequired(LVLRunnable.this.mContext, DownloaderService.this.mPendingIntent, DownloaderService.this.getClass())) {
                        case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                            DownloaderService.this.mNotification.onDownloadStateChanged(5);
                            break;
                        case TTSConst.TTSMULTILINE /*1*/:
                            Log.e("LVLDL", "In LVL checking loop!");
                            DownloaderService.this.mNotification.onDownloadStateChanged(15);
                            throw new RuntimeException("Error with LVL checking and database integrity");
                    }
                    DownloaderService.setServiceRunning(false);
                } catch (NameNotFoundException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException("Error with getting information from package name");
                } catch (Throwable th) {
                    DownloaderService.setServiceRunning(false);
                }
            }

            public void dontAllow(int reason) {
                switch (reason) {
                    case 291:
                        DownloaderService.this.mNotification.onDownloadStateChanged(16);
                        break;
                    case 561:
                        try {
                            DownloaderService.this.mNotification.onDownloadStateChanged(15);
                            break;
                        } catch (Throwable th) {
                            DownloaderService.setServiceRunning(false);
                        }
                }
                DownloaderService.setServiceRunning(false);
            }

            public void applicationError(int errorCode) {
                try {
                    DownloaderService.this.mNotification.onDownloadStateChanged(16);
                } finally {
                    DownloaderService.setServiceRunning(false);
                }
            }
        }

        LVLRunnable(Context context, PendingIntent intent) {
            this.mContext = context;
            DownloaderService.this.mPendingIntent = intent;
        }

        public void run() {
            DownloaderService.setServiceRunning(true);
            DownloaderService.this.mNotification.onDownloadStateChanged(2);
            APKExpansionPolicy aep = new APKExpansionPolicy(this.mContext, new AESObfuscator(DownloaderService.this.getSALT(), this.mContext.getPackageName(), Secure.getString(this.mContext.getContentResolver(), "android_id")));
            aep.resetPolicy();
            new LicenseChecker(this.mContext, aep, DownloaderService.this.getPublicKey()).checkAccess(new C10371(aep));
        }
    }

    public abstract String getAlarmReceiverClassName();

    public abstract String getPublicKey();

    public abstract byte[] getSALT();

    public DownloaderService() {
        super("LVLDownloadService");
        this.mServiceStub = DownloaderServiceMarshaller.CreateStub(this);
        this.mServiceMessenger = this.mServiceStub.getMessenger();
    }

    public static boolean isStatusError(int status) {
        return status >= 400 && status < 600;
    }

    public static boolean isStatusCompleted(int status) {
        return (status >= 200 && status < 300) || (status >= 400 && status < 600);
    }

    public IBinder onBind(Intent paramIntent) {
        Log.d("LVLDL", "Service Bound");
        return this.mServiceMessenger.getBinder();
    }

    public boolean isWiFi() {
        return this.mIsConnected && !this.mIsCellularConnection;
    }

    private void updateNetworkType(int type, int subType) {
        switch (type) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mIsCellularConnection = true;
                switch (subType) {
                    case TTSConst.TTSMULTILINE /*1*/:
                    case TTSConst.TTSPARAGRAPH /*2*/:
                    case TTSConst.TTSXML /*4*/:
                    case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    case TTSConst.TTSEVT_FREESPACE /*11*/:
                        this.mIsAtLeast3G = false;
                        this.mIsAtLeast4G = false;
                    case TTSConst.TTSUNICODE /*3*/:
                    case TTSConst.TTSEVT_TEXT /*5*/:
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                    case TTSConst.TTSEVT_TAG /*8*/:
                    case TTSConst.TTSEVT_PAUSE /*9*/:
                    case TTSConst.TTSEVT_RESUME /*10*/:
                        this.mIsAtLeast3G = true;
                        this.mIsAtLeast4G = false;
                    case TTSConst.TTSEVT_AUDIO /*13*/:
                    case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                    case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                        this.mIsAtLeast3G = true;
                        this.mIsAtLeast4G = true;
                    default:
                        this.mIsCellularConnection = false;
                        this.mIsAtLeast3G = false;
                        this.mIsAtLeast4G = false;
                }
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
            case TTSConst.TTSEVT_PAUSE /*9*/:
                this.mIsCellularConnection = false;
                this.mIsAtLeast3G = false;
                this.mIsAtLeast4G = false;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                this.mIsCellularConnection = true;
                this.mIsAtLeast3G = true;
                this.mIsAtLeast4G = true;
            default:
        }
    }

    private void updateNetworkState(NetworkInfo info) {
        boolean z = false;
        boolean isConnected = this.mIsConnected;
        boolean isFailover = this.mIsFailover;
        boolean isCellularConnection = this.mIsCellularConnection;
        boolean isRoaming = this.mIsRoaming;
        boolean isAtLeast3G = this.mIsAtLeast3G;
        if (info != null) {
            this.mIsRoaming = info.isRoaming();
            this.mIsFailover = info.isFailover();
            this.mIsConnected = info.isConnected();
            updateNetworkType(info.getType(), info.getSubtype());
        } else {
            this.mIsRoaming = false;
            this.mIsFailover = false;
            this.mIsConnected = false;
            updateNetworkType(-1, -1);
        }
        if (!(!this.mStateChanged && isConnected == this.mIsConnected && isFailover == this.mIsFailover && isCellularConnection == this.mIsCellularConnection && isRoaming == this.mIsRoaming && isAtLeast3G == this.mIsAtLeast3G)) {
            z = true;
        }
        this.mStateChanged = z;
    }

    void pollNetworkState() {
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager = (ConnectivityManager) getSystemService("connectivity");
        }
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) getSystemService("wifi");
        }
        if (this.mConnectivityManager == null) {
            Log.w("LVLDL", "couldn't get connectivity manager to poll network state");
        } else {
            updateNetworkState(this.mConnectivityManager.getActiveNetworkInfo());
        }
    }

    private static boolean isLVLCheckRequired(DownloadsDB db, PackageInfo pi) {
        if (db.mVersionCode != pi.versionCode) {
            return true;
        }
        return false;
    }

    private static synchronized boolean isServiceRunning() {
        boolean z;
        synchronized (DownloaderService.class) {
            z = sIsRunning;
        }
        return z;
    }

    private static synchronized void setServiceRunning(boolean isRunning) {
        synchronized (DownloaderService.class) {
            sIsRunning = isRunning;
        }
    }

    public static int startDownloadServiceIfRequired(Context context, Intent intent, Class<?> serviceClass) throws NameNotFoundException {
        return startDownloadServiceIfRequired(context, (PendingIntent) intent.getParcelableExtra("EPI"), (Class) serviceClass);
    }

    public static int startDownloadServiceIfRequired(Context context, PendingIntent pendingIntent, Class<?> serviceClass) throws NameNotFoundException {
        return startDownloadServiceIfRequired(context, pendingIntent, context.getPackageName(), serviceClass.getName());
    }

    public static int startDownloadServiceIfRequired(Context context, PendingIntent pendingIntent, String classPackage, String className) throws NameNotFoundException {
        int i = 0;
        PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        int status = 0;
        DownloadsDB db = DownloadsDB.getDB(context);
        if (isLVLCheckRequired(db, pi)) {
            status = 1;
        }
        if (db.mStatus == 0) {
            DownloadInfo[] infos = db.getDownloads();
            if (infos != null) {
                int length = infos.length;
                while (i < length) {
                    DownloadInfo info = infos[i];
                    if (!Helpers.doesFileExist(context, info.mFileName, info.mTotalBytes, true)) {
                        status = 2;
                        db.updateStatus(-1);
                        break;
                    }
                    i++;
                }
            }
        } else {
            status = 2;
        }
        switch (status) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                Intent fileIntent = new Intent();
                fileIntent.setClassName(classPackage, className);
                fileIntent.putExtra("EPI", pendingIntent);
                context.startService(fileIntent);
                break;
        }
        return status;
    }

    public void requestAbortDownload() {
        this.mControl = 1;
        this.mStatus = 490;
    }

    public void requestPauseDownload() {
        this.mControl = 1;
        this.mStatus = 193;
    }

    public void setDownloadFlags(int flags) {
        DownloadsDB.getDB(this).updateFlags(flags);
    }

    public void requestContinueDownload() {
        if (this.mControl == 1) {
            this.mControl = 0;
        }
        Intent fileIntent = new Intent(this, getClass());
        fileIntent.putExtra("EPI", this.mPendingIntent);
        startService(fileIntent);
    }

    public void updateLVL(Context context) {
        Context c = context.getApplicationContext();
        new Handler(c.getMainLooper()).post(new LVLRunnable(c, this.mPendingIntent));
    }

    public boolean handleFileUpdated(DownloadsDB db, int index, String filename, long fileSize) {
        boolean z = true;
        DownloadInfo di = db.getDownloadInfoByFileName(filename);
        if (di != null) {
            String oldFile = di.mFileName;
            if (oldFile != null) {
                if (filename.equals(oldFile)) {
                    return false;
                }
                File f = new File(Helpers.generateSaveFileName(this, oldFile));
                if (f.exists()) {
                    f.delete();
                }
            }
        }
        if (Helpers.doesFileExist(this, filename, fileSize, true)) {
            z = false;
        }
        return z;
    }

    private void scheduleAlarm(long wakeUp) {
        AlarmManager alarms = (AlarmManager) getSystemService("alarm");
        if (alarms == null) {
            Log.e("LVLDL", "couldn't get alarm manager");
            return;
        }
        String className = getAlarmReceiverClassName();
        Intent intent = new Intent("android.intent.action.DOWNLOAD_WAKEUP");
        intent.putExtra("EPI", this.mPendingIntent);
        intent.setClassName(getPackageName(), className);
        this.mAlarmIntent = PendingIntent.getBroadcast(this, 0, intent, 1073741824);
        alarms.set(0, System.currentTimeMillis() + wakeUp, this.mAlarmIntent);
    }

    private void cancelAlarms() {
        if (this.mAlarmIntent != null) {
            AlarmManager alarms = (AlarmManager) getSystemService("alarm");
            if (alarms == null) {
                Log.e("LVLDL", "couldn't get alarm manager");
                return;
            }
            alarms.cancel(this.mAlarmIntent);
            this.mAlarmIntent = null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onHandleIntent(android.content.Intent r23) {
        /* JADX: method processing error */
/*
        Error: java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
	at java.util.BitSet.get(BitSet.java:623)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.usedArgAssign(CodeShrinker.java:139)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.access$300(CodeShrinker.java:44)
	at jadx.core.dex.visitors.CodeShrinker.canMoveBetweenBlocks(CodeShrinker.java:306)
	at jadx.core.dex.visitors.CodeShrinker.shrinkBlock(CodeShrinker.java:229)
	at jadx.core.dex.visitors.CodeShrinker.shrinkMethod(CodeShrinker.java:40)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkArrayForEach(LoopRegionVisitor.java:195)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkForIndexedLoop(LoopRegionVisitor.java:118)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:64)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r22 = this;
        r16 = 1;
        setServiceRunning(r16);
        r6 = com.google.android.vending.expansion.downloader.impl.DownloadsDB.getDB(r22);	 Catch:{ all -> 0x005a }
        r16 = "EPI";	 Catch:{ all -> 0x005a }
        r0 = r23;	 Catch:{ all -> 0x005a }
        r1 = r16;	 Catch:{ all -> 0x005a }
        r12 = r0.getParcelableExtra(r1);	 Catch:{ all -> 0x005a }
        r12 = (android.app.PendingIntent) r12;	 Catch:{ all -> 0x005a }
        if (r12 == 0) goto L_0x0042;	 Catch:{ all -> 0x005a }
    L_0x0018:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mNotification;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r0.setClientIntent(r12);	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0.mPendingIntent = r12;	 Catch:{ all -> 0x005a }
    L_0x0027:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mPackageInfo;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r16 = isLVLCheckRequired(r6, r0);	 Catch:{ all -> 0x005a }
        if (r16 == 0) goto L_0x0070;	 Catch:{ all -> 0x005a }
    L_0x0035:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r1 = r22;	 Catch:{ all -> 0x005a }
        r0.updateLVL(r1);	 Catch:{ all -> 0x005a }
        r16 = 0;
        setServiceRunning(r16);
    L_0x0041:
        return;
    L_0x0042:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mPendingIntent;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        if (r16 == 0) goto L_0x0061;	 Catch:{ all -> 0x005a }
    L_0x004a:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mNotification;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mPendingIntent;	 Catch:{ all -> 0x005a }
        r17 = r0;	 Catch:{ all -> 0x005a }
        r16.setClientIntent(r17);	 Catch:{ all -> 0x005a }
        goto L_0x0027;
    L_0x005a:
        r16 = move-exception;
        r17 = 0;
        setServiceRunning(r17);
        throw r16;
    L_0x0061:
        r16 = "LVLDL";	 Catch:{ all -> 0x005a }
        r17 = "Downloader started in bad state without notification intent.";	 Catch:{ all -> 0x005a }
        android.util.Log.e(r16, r17);	 Catch:{ all -> 0x005a }
        r16 = 0;
        setServiceRunning(r16);
        goto L_0x0041;
    L_0x0070:
        r9 = r6.getDownloads();	 Catch:{ all -> 0x005a }
        r16 = 0;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r2 = r22;	 Catch:{ all -> 0x005a }
        r2.mBytesSoFar = r0;	 Catch:{ all -> 0x005a }
        r16 = 0;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r2 = r22;	 Catch:{ all -> 0x005a }
        r2.mTotalLength = r0;	 Catch:{ all -> 0x005a }
        r0 = r9.length;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r1 = r22;	 Catch:{ all -> 0x005a }
        r1.mFileCount = r0;	 Catch:{ all -> 0x005a }
        r0 = r9.length;	 Catch:{ all -> 0x005a }
        r17 = r0;	 Catch:{ all -> 0x005a }
        r16 = 0;	 Catch:{ all -> 0x005a }
    L_0x0092:
        r0 = r16;	 Catch:{ all -> 0x005a }
        r1 = r17;	 Catch:{ all -> 0x005a }
        if (r0 >= r1) goto L_0x00f1;	 Catch:{ all -> 0x005a }
    L_0x0098:
        r8 = r9[r16];	 Catch:{ all -> 0x005a }
        r0 = r8.mStatus;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r19 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r1 = r19;	 Catch:{ all -> 0x005a }
        if (r0 != r1) goto L_0x00ca;	 Catch:{ all -> 0x005a }
    L_0x00a6:
        r0 = r8.mFileName;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r0 = r8.mTotalBytes;	 Catch:{ all -> 0x005a }
        r20 = r0;	 Catch:{ all -> 0x005a }
        r19 = 1;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r1 = r18;	 Catch:{ all -> 0x005a }
        r2 = r20;	 Catch:{ all -> 0x005a }
        r4 = r19;	 Catch:{ all -> 0x005a }
        r18 = com.google.android.vending.expansion.downloader.Helpers.doesFileExist(r0, r1, r2, r4);	 Catch:{ all -> 0x005a }
        if (r18 != 0) goto L_0x00ca;	 Catch:{ all -> 0x005a }
    L_0x00be:
        r18 = 0;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r8.mStatus = r0;	 Catch:{ all -> 0x005a }
        r18 = 0;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r8.mCurrentBytes = r0;	 Catch:{ all -> 0x005a }
    L_0x00ca:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mTotalLength;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r0 = r8.mTotalBytes;	 Catch:{ all -> 0x005a }
        r20 = r0;	 Catch:{ all -> 0x005a }
        r18 = r18 + r20;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r2 = r22;	 Catch:{ all -> 0x005a }
        r2.mTotalLength = r0;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mBytesSoFar;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r0 = r8.mCurrentBytes;	 Catch:{ all -> 0x005a }
        r20 = r0;	 Catch:{ all -> 0x005a }
        r18 = r18 + r20;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r2 = r22;	 Catch:{ all -> 0x005a }
        r2.mBytesSoFar = r0;	 Catch:{ all -> 0x005a }
        r16 = r16 + 1;	 Catch:{ all -> 0x005a }
        goto L_0x0092;	 Catch:{ all -> 0x005a }
    L_0x00f1:
        r22.pollNetworkState();	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mConnReceiver;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        if (r16 != 0) goto L_0x012c;	 Catch:{ all -> 0x005a }
    L_0x00fc:
        r16 = new com.google.android.vending.expansion.downloader.impl.DownloaderService$InnerBroadcastReceiver;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r1 = r22;	 Catch:{ all -> 0x005a }
        r2 = r22;	 Catch:{ all -> 0x005a }
        r0.<init>(r2);	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r1 = r22;	 Catch:{ all -> 0x005a }
        r1.mConnReceiver = r0;	 Catch:{ all -> 0x005a }
        r10 = new android.content.IntentFilter;	 Catch:{ all -> 0x005a }
        r16 = "android.net.conn.CONNECTIVITY_CHANGE";	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r10.<init>(r0);	 Catch:{ all -> 0x005a }
        r16 = "android.net.wifi.WIFI_STATE_CHANGED";	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r10.addAction(r0);	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mConnReceiver;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r1 = r16;	 Catch:{ all -> 0x005a }
        r0.registerReceiver(r1, r10);	 Catch:{ all -> 0x005a }
    L_0x012c:
        r0 = r9.length;	 Catch:{ all -> 0x005a }
        r17 = r0;	 Catch:{ all -> 0x005a }
        r16 = 0;	 Catch:{ all -> 0x005a }
    L_0x0131:
        r0 = r16;	 Catch:{ all -> 0x005a }
        r1 = r17;	 Catch:{ all -> 0x005a }
        if (r0 >= r1) goto L_0x0212;	 Catch:{ all -> 0x005a }
    L_0x0137:
        r8 = r9[r16];	 Catch:{ all -> 0x005a }
        r14 = r8.mCurrentBytes;	 Catch:{ all -> 0x005a }
        r0 = r8.mStatus;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r19 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r1 = r19;	 Catch:{ all -> 0x005a }
        if (r0 == r1) goto L_0x0168;	 Catch:{ all -> 0x005a }
    L_0x0147:
        r7 = new com.google.android.vending.expansion.downloader.impl.DownloadThread;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mNotification;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r1 = r18;	 Catch:{ all -> 0x005a }
        r7.<init>(r8, r0, r1);	 Catch:{ all -> 0x005a }
        r22.cancelAlarms();	 Catch:{ all -> 0x005a }
        r18 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r1 = r18;	 Catch:{ all -> 0x005a }
        r0.scheduleAlarm(r1);	 Catch:{ all -> 0x005a }
        r7.run();	 Catch:{ all -> 0x005a }
        r22.cancelAlarms();	 Catch:{ all -> 0x005a }
    L_0x0168:
        r6.updateFromDb(r8);	 Catch:{ all -> 0x005a }
        r13 = 0;	 Catch:{ all -> 0x005a }
        r0 = r8.mStatus;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        switch(r18) {
            case 193: goto L_0x01db;
            case 194: goto L_0x01dd;
            case 195: goto L_0x01dd;
            case 196: goto L_0x01e0;
            case 197: goto L_0x01e0;
            case 200: goto L_0x01a1;
            case 403: goto L_0x0193;
            case 487: goto L_0x01ce;
            case 490: goto L_0x01fe;
            case 498: goto L_0x0203;
            case 499: goto L_0x0208;
            default: goto L_0x0173;
        };	 Catch:{ all -> 0x005a }
    L_0x0173:
        r11 = 19;	 Catch:{ all -> 0x005a }
    L_0x0175:
        if (r13 == 0) goto L_0x020d;	 Catch:{ all -> 0x005a }
    L_0x0177:
        r16 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r1 = r16;	 Catch:{ all -> 0x005a }
        r0.scheduleAlarm(r1);	 Catch:{ all -> 0x005a }
    L_0x0181:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mNotification;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r0.onDownloadStateChanged(r11);	 Catch:{ all -> 0x005a }
        r16 = 0;
        setServiceRunning(r16);
        goto L_0x0041;
    L_0x0193:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r1 = r22;	 Catch:{ all -> 0x005a }
        r0.updateLVL(r1);	 Catch:{ all -> 0x005a }
        r16 = 0;
        setServiceRunning(r16);
        goto L_0x0041;
    L_0x01a1:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mBytesSoFar;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r0 = r8.mCurrentBytes;	 Catch:{ all -> 0x005a }
        r20 = r0;	 Catch:{ all -> 0x005a }
        r20 = r20 - r14;	 Catch:{ all -> 0x005a }
        r18 = r18 + r20;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r2 = r22;	 Catch:{ all -> 0x005a }
        r2.mBytesSoFar = r0;	 Catch:{ all -> 0x005a }
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mPackageInfo;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r0 = r0.versionCode;	 Catch:{ all -> 0x005a }
        r18 = r0;	 Catch:{ all -> 0x005a }
        r19 = 0;	 Catch:{ all -> 0x005a }
        r0 = r18;	 Catch:{ all -> 0x005a }
        r1 = r19;	 Catch:{ all -> 0x005a }
        r6.updateMetadata(r0, r1);	 Catch:{ all -> 0x005a }
        r16 = r16 + 1;	 Catch:{ all -> 0x005a }
        goto L_0x0131;	 Catch:{ all -> 0x005a }
    L_0x01ce:
        r11 = 13;	 Catch:{ all -> 0x005a }
        r16 = 0;	 Catch:{ all -> 0x005a }
        r0 = r16;	 Catch:{ all -> 0x005a }
        r8.mCurrentBytes = r0;	 Catch:{ all -> 0x005a }
        r6.updateDownload(r8);	 Catch:{ all -> 0x005a }
        r13 = 1;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x01db:
        r11 = 7;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x01dd:
        r11 = 6;	 Catch:{ all -> 0x005a }
        r13 = 1;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x01e0:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mWifiManager;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        if (r16 == 0) goto L_0x01f9;	 Catch:{ all -> 0x005a }
    L_0x01e8:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mWifiManager;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r16 = r16.isWifiEnabled();	 Catch:{ all -> 0x005a }
        if (r16 != 0) goto L_0x01f9;	 Catch:{ all -> 0x005a }
    L_0x01f4:
        r11 = 8;	 Catch:{ all -> 0x005a }
        r13 = 1;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x01f9:
        r11 = 9;	 Catch:{ all -> 0x005a }
        r13 = 1;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x01fe:
        r11 = 18;	 Catch:{ all -> 0x005a }
        r13 = 1;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x0203:
        r11 = 17;	 Catch:{ all -> 0x005a }
        r13 = 1;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x0208:
        r11 = 14;	 Catch:{ all -> 0x005a }
        r13 = 1;	 Catch:{ all -> 0x005a }
        goto L_0x0175;	 Catch:{ all -> 0x005a }
    L_0x020d:
        r22.cancelAlarms();	 Catch:{ all -> 0x005a }
        goto L_0x0181;	 Catch:{ all -> 0x005a }
    L_0x0212:
        r0 = r22;	 Catch:{ all -> 0x005a }
        r0 = r0.mNotification;	 Catch:{ all -> 0x005a }
        r16 = r0;	 Catch:{ all -> 0x005a }
        r17 = 5;	 Catch:{ all -> 0x005a }
        r16.onDownloadStateChanged(r17);	 Catch:{ all -> 0x005a }
        r16 = 0;
        setServiceRunning(r16);
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.vending.expansion.downloader.impl.DownloaderService.onHandleIntent(android.content.Intent):void");
    }

    public void onDestroy() {
        if (this.mConnReceiver != null) {
            unregisterReceiver(this.mConnReceiver);
            this.mConnReceiver = null;
        }
        this.mServiceStub.disconnect(this);
        super.onDestroy();
    }

    public int getNetworkAvailabilityState(DownloadsDB db) {
        if (!this.mIsConnected) {
            return 2;
        }
        if (!this.mIsCellularConnection) {
            return 1;
        }
        int flags = db.mFlags;
        if (this.mIsRoaming) {
            return 5;
        }
        if ((flags & 1) == 0) {
            return 6;
        }
        return 1;
    }

    public void onCreate() {
        super.onCreate();
        try {
            this.mPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            this.mNotification = new DownloadNotification(this, getPackageManager().getApplicationLabel(getApplicationInfo()));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String generateTempSaveFileName(String fileName) {
        return Helpers.getSaveFilePath(this) + File.separator + fileName + ".tmp";
    }

    public String generateSaveFile(String filename, long filesize) throws GenerateSaveFileError {
        String path = generateTempSaveFileName(filename);
        File expPath = new File(path);
        if (!Helpers.isExternalMediaMounted()) {
            Log.d("LVLDL", "External media not mounted: " + path);
            throw new GenerateSaveFileError(499, "external media is not yet mounted");
        } else if (expPath.exists()) {
            Log.d("LVLDL", "File already exists: " + path);
            throw new GenerateSaveFileError(488, "requested destination file already exists");
        } else if (Helpers.getAvailableBytes(Helpers.getFilesystemRoot(path)) >= filesize) {
            return path;
        } else {
            throw new GenerateSaveFileError(498, "insufficient space on external storage");
        }
    }

    public int getControl() {
        return this.mControl;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void notifyUpdateBytes(long totalBytesSoFar) {
        long timeRemaining;
        long currentTime = SystemClock.uptimeMillis();
        if (0 != this.mMillisecondsAtSample) {
            float currentSpeedSample = ((float) (totalBytesSoFar - this.mBytesAtSample)) / ((float) (currentTime - this.mMillisecondsAtSample));
            if (0.0f != this.mAverageDownloadSpeed) {
                this.mAverageDownloadSpeed = (0.005f * currentSpeedSample) + (0.995f * this.mAverageDownloadSpeed);
            } else {
                this.mAverageDownloadSpeed = currentSpeedSample;
            }
            timeRemaining = (long) (((float) (this.mTotalLength - totalBytesSoFar)) / this.mAverageDownloadSpeed);
        } else {
            timeRemaining = -1;
        }
        this.mMillisecondsAtSample = currentTime;
        this.mBytesAtSample = totalBytesSoFar;
        this.mNotification.onDownloadProgress(new DownloadProgressInfo(this.mTotalLength, totalBytesSoFar, timeRemaining, this.mAverageDownloadSpeed));
    }

    protected boolean shouldStop() {
        if (DownloadsDB.getDB(this).mStatus == 0) {
            return true;
        }
        return false;
    }

    public void requestDownloadStatus() {
        this.mNotification.resendState();
    }

    public void onClientUpdated(Messenger clientMessenger) {
        this.mClientMessenger = clientMessenger;
        this.mNotification.setMessenger(this.mClientMessenger);
    }
}
