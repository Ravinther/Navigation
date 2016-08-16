package com.sygic.aura.downloader;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import com.crashlytics.android.Crashlytics;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.SygicMain;
import com.sygic.aura.utils.FileUtils;
import com.sygic.aura.utils.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class Downloader {
    private static final String TAG;
    private static UnzipInfo mUnzipInfo;
    private static boolean mbDownloadCompleted;
    private static boolean mbUnzipingFiles;
    private static final XAPKFile[] xAPKS;

    private static class UnzipInfo {
        public int miAllFiles;
        public int miProcessed;
        public int miProgress;

        private UnzipInfo() {
            this.miProgress = -1;
            this.miAllFiles = 0;
            this.miProcessed = 0;
        }
    }

    private static class XAPKFile {
        public final long mFileSize;
        public final int mFileVersion;
        public final boolean mIsMain;

        XAPKFile(boolean isMain, int fileVersion, long fileSize) {
            this.mIsMain = isMain;
            this.mFileVersion = fileVersion;
            this.mFileSize = fileSize;
        }
    }

    static {
        TAG = Downloader.class.getName();
        mbDownloadCompleted = false;
        mbUnzipingFiles = false;
        xAPKS = new XAPKFile[]{new XAPKFile(true, 225, 31047006), new XAPKFile(false, 0, 0)};
    }

    public static boolean isDownloaderNeeded() {
        if (ProjectsConsts.getBoolean(0) || ProjectsConsts.getBoolean(4)) {
            return true;
        }
        return false;
    }

    private static int getObbVersion(Context context) {
        return 225;
    }

    public static boolean checkDownload(Context context) {
        if (getObbVersion(context) == Utils.getResVersion() && FileUtils.fileExists(Utils.getSygicDirPath() + "/Android") && FileUtils.fileExists(Utils.getSygicDirPath() + "/Res")) {
            return true;
        }
        return false;
    }

    public static boolean unzipMinres(Context context) {
        if (Utils.getSygicDirName().equals("Sygic") && FileUtils.fileExists(Utils.getSygicDirPath().replace("/Sygic", "/Aura")) && !FileUtils.fileExists(Utils.getSygicDirPath())) {
            new File(Utils.getSygicDirPath().replace("Sygic", "Aura")).renameTo(new File(Utils.getSygicDirPath()));
        }
        if (!checkDownload(context)) {
            AssetManager am = context.getAssets();
            ArrayList<String> arrFiles = FileUtils.listAssetDir(am, "resmin");
            if (arrFiles != null) {
                Iterator it = arrFiles.iterator();
                while (it.hasNext()) {
                    String string = (String) it.next();
                    StringBuilder strTo = new StringBuilder(Utils.getSygicDirPath()).append("/").append(string.replace("resmin/", ""));
                    InputStream in = null;
                    boolean bIsDir = false;
                    try {
                        in = am.open(string);
                    } catch (FileNotFoundException e) {
                        bIsDir = true;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return false;
                    }
                    if (bIsDir) {
                        new File(strTo.toString()).mkdirs();
                    } else {
                        try {
                            FileUtils.copyFile(in, strTo.toString());
                        } catch (IOException e22) {
                            e22.printStackTrace();
                            Crashlytics.log("testing freeze at splash screen");
                            Crashlytics.logException(e22);
                            return false;
                        }
                    }
                }
            }
            Utils.setResVersion(Utils.getAppVersion(context));
        }
        return true;
    }

    public static boolean downloadCompleted(Context context) {
        return mbDownloadCompleted;
    }

    public static boolean filesDelivered(Context context) {
        if (!expansionFilesDelivered(context)) {
            return false;
        }
        try {
            SygicMain.getInstance().getFeature().getSystemFeature().logEvent("frw->resdownload_finish", null, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mbDownloadCompleted = true;
        return true;
    }

    public static boolean downloadApkExpansion(Context context) {
        Intent launchIntent = ((DownloaderActivity) context).getIntent();
        Intent intentToLaunchThisActivityFromNotification = new Intent(context, context.getClass());
        intentToLaunchThisActivityFromNotification.setFlags(335544320);
        intentToLaunchThisActivityFromNotification.setAction(launchIntent.getAction());
        if (launchIntent.getCategories() != null) {
            for (String category : launchIntent.getCategories()) {
                intentToLaunchThisActivityFromNotification.addCategory(category);
            }
        }
        try {
            if (DownloaderClientMarshaller.startDownloadServiceIfRequired(context, PendingIntent.getActivity(context, 0, intentToLaunchThisActivityFromNotification, 134217728), SygicDownloaderService.class) == 0) {
                mbDownloadCompleted = true;
                return true;
            }
            try {
                SygicMain.getInstance().getFeature().getSystemFeature().enableEventLogging(true);
                SygicMain.getInstance().getFeature().getSystemFeature().logEvent("frw->resdownload_start", null, null, 0);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        } catch (NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static boolean expansionFilesDelivered(Context context) {
        boolean hasFile = false;
        for (XAPKFile xf : xAPKS) {
            int iFileVersion = xf.mFileVersion;
            if (iFileVersion == -1) {
                iFileVersion = Utils.getAppVersion(context);
            }
            if (!Helpers.doesFileExist(context, Helpers.getExpansionAPKFileName(context, xf.mIsMain, iFileVersion), xf.mFileSize, false)) {
                break;
            }
            hasFile = true;
        }
        return hasFile;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int unzipApkExpansion(android.content.Context r24, boolean r25) {
        /*
        r17 = com.sygic.aura.SygicMain.getInstance();	 Catch:{ Exception -> 0x00cc }
        r17 = r17.getFeature();	 Catch:{ Exception -> 0x00cc }
        r17 = r17.getSystemFeature();	 Catch:{ Exception -> 0x00cc }
        r18 = 1;
        r17.enableEventLogging(r18);	 Catch:{ Exception -> 0x00cc }
        r17 = com.sygic.aura.SygicMain.getInstance();	 Catch:{ Exception -> 0x00cc }
        r17 = r17.getFeature();	 Catch:{ Exception -> 0x00cc }
        r17 = r17.getSystemFeature();	 Catch:{ Exception -> 0x00cc }
        r18 = "frw->resprocess_start";
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r17.logEvent(r18, r19, r20, r21);	 Catch:{ Exception -> 0x00cc }
    L_0x0029:
        r17 = com.sygic.aura.utils.Utils.getSygicDirName();
        r18 = "Sygic";
        r17 = r17.equals(r18);
        if (r17 == 0) goto L_0x0073;
    L_0x0036:
        r17 = com.sygic.aura.utils.Utils.getSygicDirPath();
        r18 = "/Sygic";
        r19 = "/Aura";
        r17 = r17.replace(r18, r19);
        r17 = com.sygic.aura.utils.FileUtils.fileExists(r17);
        if (r17 == 0) goto L_0x0073;
    L_0x004a:
        r17 = com.sygic.aura.utils.Utils.getSygicDirPath();
        r17 = com.sygic.aura.utils.FileUtils.fileExists(r17);
        if (r17 != 0) goto L_0x0073;
    L_0x0054:
        r17 = new java.io.File;
        r18 = com.sygic.aura.utils.Utils.getSygicDirPath();
        r19 = "Sygic";
        r20 = "Aura";
        r18 = r18.replace(r19, r20);
        r17.<init>(r18);
        r18 = new java.io.File;
        r19 = com.sygic.aura.utils.Utils.getSygicDirPath();
        r18.<init>(r19);
        r17.renameTo(r18);
    L_0x0073:
        r17 = com.sygic.aura.utils.Utils.getAppVersion(r24);
        r18 = com.sygic.aura.utils.Utils.getResVersion();
        r0 = r17;
        r1 = r18;
        if (r0 != r1) goto L_0x0083;
    L_0x0081:
        if (r25 == 0) goto L_0x020c;
    L_0x0083:
        r6 = 225; // 0xe1 float:3.15E-43 double:1.11E-321;
        r7 = 0;
        r17 = 1;
        mbUnzipingFiles = r17;
        r0 = r24;
        r5 = com.android.vending.expansion.zipfile.APKExpansionSupport.getAPKExpansionZipFile(r0, r6, r7);	 Catch:{ IOException -> 0x01e3 }
        if (r5 == 0) goto L_0x022a;
    L_0x0092:
        r17 = new com.sygic.aura.downloader.Downloader$UnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r18 = 0;
        r17.<init>();	 Catch:{ IOException -> 0x01e3 }
        mUnzipInfo = r17;	 Catch:{ IOException -> 0x01e3 }
        r2 = r5.getAllEntries();	 Catch:{ IOException -> 0x01e3 }
        r17 = mUnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r18 = 0;
        r0 = r18;
        r1 = r17;
        r1.miProcessed = r0;	 Catch:{ IOException -> 0x01e3 }
        r17 = mUnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r0 = r2.length;	 Catch:{ IOException -> 0x01e3 }
        r18 = r0;
        r0 = r18;
        r1 = r17;
        r1.miAllFiles = r0;	 Catch:{ IOException -> 0x01e3 }
        r10 = 0;
        r0 = r2.length;	 Catch:{ IOException -> 0x01e3 }
        r18 = r0;
        r17 = 0;
    L_0x00bb:
        r0 = r17;
        r1 = r18;
        if (r0 >= r1) goto L_0x00d2;
    L_0x00c1:
        r15 = r2[r17];	 Catch:{ IOException -> 0x01e3 }
        r0 = r15.mUncompressedLength;	 Catch:{ IOException -> 0x01e3 }
        r20 = r0;
        r10 = r10 + r20;
        r17 = r17 + 1;
        goto L_0x00bb;
    L_0x00cc:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0029;
    L_0x00d2:
        r14 = com.sygic.aura.utils.Utils.getSygicDirPath();	 Catch:{ IOException -> 0x01e3 }
        if (r14 != 0) goto L_0x00dc;
    L_0x00d8:
        r14 = com.sygic.aura.utils.FileUtils.getSDCardPath();	 Catch:{ IOException -> 0x01e3 }
    L_0x00dc:
        r17 = com.sygic.aura.utils.FileUtils.fileExists(r14);	 Catch:{ IOException -> 0x01e3 }
        if (r17 != 0) goto L_0x00ed;
    L_0x00e2:
        r17 = new java.io.File;	 Catch:{ IOException -> 0x01e3 }
        r0 = r17;
        r0.<init>(r14);	 Catch:{ IOException -> 0x01e3 }
        r14 = r17.getParent();	 Catch:{ IOException -> 0x01e3 }
    L_0x00ed:
        r12 = new android.os.StatFs;	 Catch:{ IOException -> 0x01e3 }
        r12.<init>(r14);	 Catch:{ IOException -> 0x01e3 }
        r17 = r12.getFreeBlocks();	 Catch:{ IOException -> 0x01e3 }
        r0 = r17;
        r0 = (long) r0;	 Catch:{ IOException -> 0x01e3 }
        r18 = r0;
        r17 = r12.getBlockSize();	 Catch:{ IOException -> 0x01e3 }
        r0 = r17;
        r0 = (long) r0;	 Catch:{ IOException -> 0x01e3 }
        r20 = r0;
        r8 = r18 * r20;
        r17 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r17 >= 0) goto L_0x0119;
    L_0x010a:
        r17 = 0;
        mbUnzipingFiles = r17;	 Catch:{ IOException -> 0x01e3 }
        r17 = TAG;	 Catch:{ IOException -> 0x01e3 }
        r18 = "No space left on device";
        android.util.Log.e(r17, r18);	 Catch:{ IOException -> 0x01e3 }
        r17 = 2;
    L_0x0118:
        return r17;
    L_0x0119:
        r17 = com.sygic.aura.utils.Utils.getSygicDirPath();	 Catch:{ IOException -> 0x01e3 }
        r17 = com.sygic.aura.utils.FileUtils.fileExists(r17);	 Catch:{ IOException -> 0x01e3 }
        if (r17 != 0) goto L_0x012f;
    L_0x0123:
        r17 = new java.io.File;	 Catch:{ IOException -> 0x01e3 }
        r18 = com.sygic.aura.utils.Utils.getSygicDirPath();	 Catch:{ IOException -> 0x01e3 }
        r17.<init>(r18);	 Catch:{ IOException -> 0x01e3 }
        r17.mkdirs();	 Catch:{ IOException -> 0x01e3 }
    L_0x012f:
        r0 = r2.length;	 Catch:{ IOException -> 0x01e3 }
        r18 = r0;
        r17 = 0;
    L_0x0134:
        r0 = r17;
        r1 = r18;
        if (r0 >= r1) goto L_0x0203;
    L_0x013a:
        r16 = r2[r17];	 Catch:{ IOException -> 0x01e3 }
        r0 = r16;
        r0 = r0.mFileName;	 Catch:{ IOException -> 0x01e3 }
        r19 = r0;
        r20 = "/";
        r19 = r19.endsWith(r20);	 Catch:{ IOException -> 0x01e3 }
        if (r19 == 0) goto L_0x0185;
    L_0x014b:
        r0 = r16;
        r0 = r0.mFile;	 Catch:{ IOException -> 0x01e3 }
        r19 = r0;
        r19.mkdirs();	 Catch:{ IOException -> 0x01e3 }
    L_0x0154:
        r19 = mUnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r0 = r19;
        r0 = r0.miAllFiles;	 Catch:{ IOException -> 0x01e3 }
        r19 = r0;
        if (r19 == 0) goto L_0x01f8;
    L_0x015e:
        r19 = mUnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r20 = mUnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r0 = r20;
        r0 = r0.miProcessed;	 Catch:{ IOException -> 0x01e3 }
        r21 = r0;
        r22 = r21 + 1;
        r0 = r22;
        r1 = r20;
        r1.miProcessed = r0;	 Catch:{ IOException -> 0x01e3 }
        r20 = r21 * 100;
        r21 = mUnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r0 = r21;
        r0 = r0.miAllFiles;	 Catch:{ IOException -> 0x01e3 }
        r21 = r0;
        r20 = r20 / r21;
        r0 = r20;
        r1 = r19;
        r1.miProgress = r0;	 Catch:{ IOException -> 0x01e3 }
    L_0x0182:
        r17 = r17 + 1;
        goto L_0x0134;
    L_0x0185:
        r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01e3 }
        r19.<init>();	 Catch:{ IOException -> 0x01e3 }
        r20 = com.sygic.aura.utils.Utils.getSygicDirPath();	 Catch:{ IOException -> 0x01e3 }
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01e3 }
        r20 = "/";
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01e3 }
        r0 = r16;
        r0 = r0.mFileName;	 Catch:{ IOException -> 0x01e3 }
        r20 = r0;
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01e3 }
        r13 = r19.toString();	 Catch:{ IOException -> 0x01e3 }
        r19 = com.sygic.aura.utils.FileUtils.fileExists(r13);	 Catch:{ IOException -> 0x01e3 }
        if (r19 == 0) goto L_0x01d0;
    L_0x01ad:
        r0 = r16;
        r0 = r0.mUncompressedLength;	 Catch:{ IOException -> 0x01e3 }
        r20 = r0;
        r19 = new java.io.File;	 Catch:{ IOException -> 0x01e3 }
        r0 = r19;
        r0.<init>(r13);	 Catch:{ IOException -> 0x01e3 }
        r22 = r19.length();	 Catch:{ IOException -> 0x01e3 }
        r19 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1));
        if (r19 != 0) goto L_0x01d0;
    L_0x01c2:
        r20 = getChecksum(r13);	 Catch:{ IOException -> 0x01e3 }
        r0 = r16;
        r0 = r0.mCRC32;	 Catch:{ IOException -> 0x01e3 }
        r22 = r0;
        r19 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1));
        if (r19 == 0) goto L_0x0154;
    L_0x01d0:
        r0 = r16;
        r0 = r0.mFileName;	 Catch:{ IOException -> 0x01e3 }
        r19 = r0;
        r0 = r19;
        r19 = r5.getInputStream(r0);	 Catch:{ IOException -> 0x01e3 }
        r0 = r19;
        com.sygic.aura.utils.FileUtils.copyFile(r0, r13);	 Catch:{ IOException -> 0x01e3 }
        goto L_0x0154;
    L_0x01e3:
        r3 = move-exception;
        r3.printStackTrace();
        r17 = "testing freeze at splash screen";
        com.crashlytics.android.Crashlytics.log(r17);
        com.crashlytics.android.Crashlytics.logException(r3);
        r17 = 0;
        mbUnzipingFiles = r17;
        r17 = 1;
        goto L_0x0118;
    L_0x01f8:
        r19 = mUnzipInfo;	 Catch:{ IOException -> 0x01e3 }
        r20 = 100;
        r0 = r20;
        r1 = r19;
        r1.miProgress = r0;	 Catch:{ IOException -> 0x01e3 }
        goto L_0x0182;
    L_0x0203:
        if (r7 <= 0) goto L_0x0228;
    L_0x0205:
        com.sygic.aura.utils.Utils.setResVersion(r7);	 Catch:{ IOException -> 0x01e3 }
        r17 = 0;
        mbUnzipingFiles = r17;
    L_0x020c:
        r17 = com.sygic.aura.SygicMain.getInstance();	 Catch:{ Exception -> 0x0236 }
        r17 = r17.getFeature();	 Catch:{ Exception -> 0x0236 }
        r17 = r17.getSystemFeature();	 Catch:{ Exception -> 0x0236 }
        r18 = "frw->resprocess_finish";
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r17.logEvent(r18, r19, r20, r21);	 Catch:{ Exception -> 0x0236 }
    L_0x0224:
        r17 = 0;
        goto L_0x0118;
    L_0x0228:
        r7 = r6;
        goto L_0x0205;
    L_0x022a:
        r17 = TAG;	 Catch:{ IOException -> 0x01e3 }
        r18 = "APK expansion file not found";
        android.util.Log.e(r17, r18);	 Catch:{ IOException -> 0x01e3 }
        r17 = 1;
        goto L_0x0118;
    L_0x0236:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x0224;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.downloader.Downloader.unzipApkExpansion(android.content.Context, boolean):int");
    }

    public static void processRemoves(Context context) {
        try {
            InputStream is = context.getAssets().open("remove.txt");
            if (is != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while (true) {
                    String strLine = br.readLine();
                    if (strLine == null) {
                        break;
                    }
                    FileUtils.deleteFile(Utils.getSygicDirPath() + "/" + strLine.trim());
                }
            }
            is.close();
        } catch (IOException e) {
        }
    }

    private static long getChecksum(String strFileName) {
        CheckedInputStream checkedInputStream;
        long lValue = 0;
        try {
            CheckedInputStream cis = new CheckedInputStream(new FileInputStream(strFileName), new CRC32());
            do {
                try {
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        cis.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    checkedInputStream = cis;
                }
            } while (cis.read(new byte[128]) >= 0);
            lValue = cis.getChecksum().getValue();
            try {
                cis.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            checkedInputStream = cis;
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        }
        return lValue;
    }

    public static int getUnzipProgress() {
        if (mUnzipInfo != null) {
            return mUnzipInfo.miProgress;
        }
        return -1;
    }

    public static int getUnzipFilesCount() {
        if (mUnzipInfo != null) {
            return mUnzipInfo.miAllFiles;
        }
        return 0;
    }

    public static int getUnzippedFiles() {
        if (mUnzipInfo != null) {
            return mUnzipInfo.miProcessed;
        }
        return 0;
    }

    public static long getAesSize() {
        long lSize = 0;
        for (XAPKFile xf : xAPKS) {
            lSize += xf.mFileSize;
        }
        return lSize;
    }
}
