package com.sygic.aura.downloader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller;
import com.google.android.vending.expansion.downloader.IDownloaderClient;
import com.google.android.vending.expansion.downloader.IDownloaderService;
import com.google.android.vending.expansion.downloader.IStub;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.SygicPreferences;
import com.sygic.aura.utils.FileUtils;
import com.sygic.aura.utils.GuiUtils;
import com.sygic.aura.utils.Utils;
import com.sygic.base.C1799R;
import java.io.File;
import java.io.IOException;
import loquendo.tts.engine.TTSConst;

public class DownloaderActivity extends Activity implements IDownloaderClient {
    private transient AlertDialog mAlertDialog;
    private IStub mDownloaderClientStub;
    private final Handler mHandler;
    private ProgressBarDialog mPbDialog;
    private ProgressBar mProgressBar;
    private IDownloaderService mRemoteService;
    private AsyncTask<Void, Integer, Long> mTaskUnzip;
    private TextView mTvDownloader;
    private volatile boolean mbDownloadCompleted;

    /* renamed from: com.sygic.aura.downloader.DownloaderActivity.1 */
    class C11791 implements Runnable {
        C11791() {
        }

        public void run() {
            DownloaderActivity.this.startDownloader();
        }
    }

    /* renamed from: com.sygic.aura.downloader.DownloaderActivity.2 */
    class C11802 implements Runnable {
        C11802() {
        }

        public void run() {
            String strAppDir;
            String strMsg = Utils.getSygicString(DownloaderActivity.this, C1799R.string.message_not_writable);
            File dir = FileUtils.getExternalStorage(DownloaderActivity.this);
            if (dir != null) {
                strAppDir = dir.getAbsolutePath().concat("/").concat(Utils.getSygicDirName());
            } else {
                strAppDir = null;
            }
            DownloaderActivity.this.showCopyErrorDialog(strMsg, strAppDir);
        }
    }

    /* renamed from: com.sygic.aura.downloader.DownloaderActivity.3 */
    class C11813 implements Runnable {
        C11813() {
        }

        public void run() {
            if (DownloaderActivity.this.mPbDialog != null) {
                DownloaderActivity.this.mPbDialog.disableCancelButton();
            }
        }
    }

    /* renamed from: com.sygic.aura.downloader.DownloaderActivity.4 */
    class C11824 implements OnClickListener {
        final /* synthetic */ String val$strAppDir;

        C11824(String str) {
            this.val$strAppDir = str;
        }

        public void onClick(DialogInterface dialog, int which) {
            DownloaderActivity.this.copySygicDir(this.val$strAppDir);
        }
    }

    /* renamed from: com.sygic.aura.downloader.DownloaderActivity.5 */
    class C11835 implements OnClickListener {
        C11835() {
        }

        public void onClick(DialogInterface dialog, int which) {
            DownloaderActivity.this.finish();
        }
    }

    /* renamed from: com.sygic.aura.downloader.DownloaderActivity.6 */
    class C11876 extends AsyncTask<String, Integer, Boolean> {
        final /* synthetic */ ProgressDialog val$pbDialog;
        final /* synthetic */ String val$strAppDir;

        /* renamed from: com.sygic.aura.downloader.DownloaderActivity.6.1 */
        class C11841 implements Runnable {
            C11841() {
            }

            public void run() {
                DownloaderActivity.this.startDownloader();
            }
        }

        /* renamed from: com.sygic.aura.downloader.DownloaderActivity.6.2 */
        class C11852 implements Runnable {
            C11852() {
            }

            public void run() {
                if (C11876.this.val$pbDialog != null) {
                    C11876.this.val$pbDialog.setProgressStyle(1);
                    C11876.this.val$pbDialog.setCancelable(false);
                    C11876.this.val$pbDialog.setMax(FileUtils.getFileCount(new File(Utils.getSygicDirPath())));
                    C11876.this.val$pbDialog.setTitle(DownloaderActivity.this.getString(C1799R.string.title_copying));
                    C11876.this.val$pbDialog.setMessage(DownloaderActivity.this.getString(C1799R.string.message_pleasewait));
                    C11876.this.val$pbDialog.show();
                }
            }
        }

        /* renamed from: com.sygic.aura.downloader.DownloaderActivity.6.3 */
        class C11863 implements Runnable {
            final /* synthetic */ Boolean val$result;

            C11863(Boolean bool) {
                this.val$result = bool;
            }

            public void run() {
                if (C11876.this.val$pbDialog != null) {
                    C11876.this.val$pbDialog.hide();
                    C11876.this.val$pbDialog.dismiss();
                }
                if (!this.val$result.booleanValue()) {
                    DownloaderActivity.this.showCopyErrorDialog(Utils.getSygicString(DownloaderActivity.this, C1799R.string.message_copy_error), C11876.this.val$strAppDir);
                }
            }
        }

        C11876(ProgressDialog progressDialog, String str) {
            this.val$pbDialog = progressDialog;
            this.val$strAppDir = str;
        }

        protected Boolean doInBackground(String... params) {
            String strDir = params[0];
            if (!TextUtils.isEmpty(strDir)) {
                try {
                    copyDirs(Utils.getSygicDirPath(), strDir);
                    Utils.setSygicDirPath(strDir);
                    SygicPreferences.setSygicDirPath(DownloaderActivity.this, Utils.getSygicDirPath());
                    new Thread(new C11841()).start();
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
            DownloaderActivity.this.runOnUiThread(new C11852());
        }

        protected void onPostExecute(Boolean result) {
            DownloaderActivity.this.runOnUiThread(new C11863(result));
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
                File[] files = directory.listFiles();
                if (files == null) {
                    throw new IOException(String.format("Error copying files from '%s'. listFiles() returns null.", new Object[]{strFrom}));
                }
                for (File file : files) {
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

    /* renamed from: com.sygic.aura.downloader.DownloaderActivity.7 */
    class C11907 extends AsyncTask<Void, Integer, Long> {
        private transient String mTitle;

        /* renamed from: com.sygic.aura.downloader.DownloaderActivity.7.1 */
        class C11881 implements Runnable {
            C11881() {
            }

            public void run() {
                C11907.this.mTitle = Utils.getSygicString(DownloaderActivity.this, C1799R.string.title_processing);
                DownloaderActivity.this.mTvDownloader.setText(Utils.getSygicString(DownloaderActivity.this, C1799R.string.message_processing));
            }
        }

        /* renamed from: com.sygic.aura.downloader.DownloaderActivity.7.2 */
        class C11892 implements Runnable {
            final /* synthetic */ Integer[] val$values;

            C11892(Integer[] numArr) {
                this.val$values = numArr;
            }

            public void run() {
                if (DownloaderActivity.this.mPbDialog != null) {
                    DownloaderActivity.this.mPbDialog.updateProgressBar(C11907.this.mTitle, this.val$values[0].intValue(), String.format("%d / %d", new Object[]{this.val$values[1], this.val$values[2]}));
                }
            }
        }

        C11907() {
        }

        protected Long doInBackground(Void... params) {
            int iProgress = -1;
            while (iProgress < 100) {
                iProgress = Downloader.getUnzipProgress();
                if (iProgress != -1) {
                    int iProcessed = Downloader.getUnzippedFiles();
                    int iAllFiles = Downloader.getUnzipFilesCount();
                    publishProgress(new Integer[]{Integer.valueOf(iProgress), Integer.valueOf(iProcessed), Integer.valueOf(iAllFiles)});
                }
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e("DownloaderActivity", "async task waiter", e);
                }
            }
            return Long.valueOf((long) iProgress);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            DownloaderActivity.this.runOnUiThread(new C11881());
        }

        protected void onProgressUpdate(Integer... values) {
            DownloaderActivity.this.runOnUiThread(new C11892(values));
        }
    }

    public DownloaderActivity() {
        this.mHandler = new Handler();
        this.mbDownloadCompleted = false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1799R.layout.downloader_main);
        if (ProjectsConsts.getBoolean(6)) {
            findViewById(C1799R.id.downloader_root).setBackgroundColor(-1);
        }
        getWindow().setFlags(128, 128);
        if (ProjectsConsts.getBoolean(4)) {
            this.mDownloaderClientStub = DownloaderClientMarshaller.CreateStub(this, SygicDownloaderService.class);
            if (this.mDownloaderClientStub != null) {
                this.mDownloaderClientStub.connect(this);
            }
            this.mTvDownloader = (TextView) findViewById(C1799R.id.tv_downloader);
            logProgress("PROGRESS BAR CREATE");
            this.mPbDialog = new ProgressBarDialog(this);
            this.mPbDialog.createProgressDialog();
            onDownloadProgress(new DownloadProgressInfo(1, 1, 0, 0.0f));
            this.mTaskUnzip = assignDownloaderTask();
        }
        if (ProjectsConsts.getBoolean(0)) {
            this.mProgressBar = (ProgressBar) findViewById(C1799R.id.progress);
            if (this.mProgressBar != null) {
                this.mProgressBar.setVisibility(0);
            }
            this.mTvDownloader = (TextView) findViewById(C1799R.id.tv_downloader);
            if (this.mTvDownloader != null) {
                this.mTvDownloader.setText(C1799R.string.message_processing);
            }
        }
        new Thread(new C11791()).start();
    }

    private static void logProgress(String logMsg) {
        Log.v("DownloaderActivity", logMsg);
    }

    protected void onStart() {
        if (this.mDownloaderClientStub != null) {
            this.mDownloaderClientStub.connect(this);
        }
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        super.onStart();
    }

    protected void onStop() {
        if (this.mDownloaderClientStub != null) {
            this.mDownloaderClientStub.disconnect(this);
        }
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        super.onStop();
    }

    protected void onDestroy() {
        if (this.mAlertDialog != null) {
            this.mAlertDialog.dismiss();
            this.mAlertDialog = null;
        }
        if (this.mPbDialog != null) {
            this.mPbDialog.dismiss();
            this.mPbDialog = null;
        }
        if (this.mTaskUnzip != null) {
            this.mTaskUnzip.cancel(true);
            this.mTaskUnzip = null;
        }
        super.onDestroy();
    }

    public void onServiceConnected(Messenger m) {
        this.mRemoteService = DownloaderServiceMarshaller.CreateProxy(m);
        this.mRemoteService.onClientUpdated(this.mDownloaderClientStub.getMessenger());
    }

    public void onDownloadStateChanged(int newState) {
        switch (newState) {
            case TTSConst.TTSEVT_TEXT /*5*/:
                synchronized (this) {
                    this.mbDownloadCompleted = true;
                    notify();
                    break;
                }
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
            case TTSConst.TTSEVT_ERROR /*16*/:
            case TTSConst.TTSEVT_PARAGRAPH /*18*/:
            case TTSConst.TTSEVT_TEXTENCODING /*19*/:
                this.mAlertDialog = GuiUtils.showMessage((Activity) this, Utils.getSygicString((Context) this, C1799R.string.message_aes_download_failed));
            case TTSConst.TTSEVT_JUMP /*17*/:
                this.mAlertDialog = GuiUtils.showMessage((Activity) this, Utils.getSygicString((Context) this, C1799R.string.shop_error_notEnoughSpace).replace("%size%", String.valueOf((Downloader.getAesSize() / 1024) / 1024)));
            default:
        }
    }

    public void onDownloadProgress(DownloadProgressInfo progress) {
        this.mTvDownloader.setText(Utils.getSygicString((Context) this, C1799R.string.resources_download_info).replace("%shortproduct%", getString(C1799R.string.app_name)));
        this.mPbDialog.updateProgressBar(Utils.getSygicString((Context) this, C1799R.string.additionalDownload_title), (int) ((progress.mOverallProgress * 100) / progress.mOverallTotal), String.format("%.2fMiB / %.2fMiB", new Object[]{Double.valueOf((((double) progress.mOverallProgress) / 1024.0d) / 1024.0d), Double.valueOf((((double) progress.mOverallTotal) / 1024.0d) / 1024.0d)}));
    }

    private void startDownloader() {
        if (Utils.getAppVersion(this) == Utils.getResVersion()) {
            setResult(-1);
            finish();
        }
        if (!FileUtils.checkSdPermissions()) {
            runOnUiThread(new C11802());
        } else if (!ProjectsConsts.getBoolean(0) || Downloader.unzipMinres(this)) {
            if (ProjectsConsts.getBoolean(4)) {
                if (!ProjectsConsts.getBoolean(0)) {
                    String strAdditional = Utils.getSygicDirPath().concat(File.separator).concat(Utils.getIniFilesDir()).concat("/additional/resources.xml");
                    if (FileUtils.fileExists(strAdditional)) {
                        FileUtils.deleteFile(strAdditional);
                    }
                }
                if (Downloader.filesDelivered(this)) {
                    this.mbDownloadCompleted = true;
                } else if (!Downloader.downloadApkExpansion(this)) {
                    setResult(0);
                    this.mAlertDialog = GuiUtils.showMessage((Activity) this, Utils.getSygicString((Context) this, C1799R.string.message_aes_download_failed));
                    return;
                }
                if (!Downloader.downloadCompleted(this)) {
                    synchronized (this) {
                        try {
                            if (!this.mbDownloadCompleted) {
                                wait();
                            }
                        } catch (InterruptedException e) {
                            Log.e("DownloaderActivity", "wait for complete interrupt", e);
                        }
                    }
                }
                this.mHandler.post(new C11813());
                if (this.mTaskUnzip != null) {
                    this.mTaskUnzip.execute(new Void[0]);
                }
                int iUnzipStatus = Downloader.unzipApkExpansion(this, this.mbDownloadCompleted);
                if (this.mTaskUnzip != null) {
                    this.mTaskUnzip.cancel(false);
                }
                if (iUnzipStatus != 0) {
                    String replace;
                    setResult(0);
                    if (iUnzipStatus == 2) {
                        replace = Utils.getSygicString((Context) this, C1799R.string.shop_error_notEnoughSpace).replace("%size%", String.valueOf((Downloader.getAesSize() / 1024) / 1024));
                    } else {
                        replace = Utils.getSygicString((Context) this, C1799R.string.message_aes_unpack_failed);
                    }
                    this.mAlertDialog = GuiUtils.showMessage((Activity) this, replace);
                    return;
                }
            }
            setResult(-1);
            finish();
        } else {
            setResult(0);
            this.mAlertDialog = GuiUtils.showMessage((Activity) this, Utils.getSygicString((Context) this, C1799R.string.message_minres_failed));
        }
    }

    private void showCopyErrorDialog(String strMsg, String strAppDir) {
        this.mAlertDialog = new Builder(this).setTitle(getResources().getString(C1799R.string.app_name)).setMessage(strMsg).setNegativeButton(C1799R.string.button_cancel, new C11835()).setPositiveButton(C1799R.string.button_ok, new C11824(strAppDir)).create();
        this.mAlertDialog.show();
    }

    private void copySygicDir(String strAppDir) {
        new C11876(new ProgressDialog(this), strAppDir).execute(new String[]{strAppDir});
    }

    private AsyncTask<Void, Integer, Long> assignDownloaderTask() {
        return new C11907();
    }
}
