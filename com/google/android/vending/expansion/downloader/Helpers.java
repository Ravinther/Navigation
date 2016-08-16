package com.google.android.vending.expansion.downloader;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import com.android.vending.expansion.downloader.C0154R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;
import loquendo.tts.engine.TTSConst;

public class Helpers {
    private static final Pattern CONTENT_DISPOSITION_PATTERN;
    public static Random sRandom;

    static {
        sRandom = new Random(SystemClock.uptimeMillis());
        CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*\"([^\"]*)\"");
    }

    public static File getFilesystemRoot(String path) {
        File cache = Environment.getDownloadCacheDirectory();
        if (path.startsWith(cache.getPath())) {
            return cache;
        }
        File external = Environment.getExternalStorageDirectory();
        if (path.startsWith(external.getPath())) {
            return external;
        }
        throw new IllegalArgumentException("Cannot determine filesystem root for " + path);
    }

    public static boolean isExternalMediaMounted() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static long getAvailableBytes(File root) {
        StatFs stat = new StatFs(root.getPath());
        return ((long) stat.getBlockSize()) * (((long) stat.getAvailableBlocks()) - 4);
    }

    public static boolean isFilenameValid(String filename) {
        filename = filename.replaceFirst("/+", "/");
        return filename.startsWith(Environment.getDownloadCacheDirectory().toString()) || filename.startsWith(Environment.getExternalStorageDirectory().toString());
    }

    public static String getDownloadProgressString(long overallProgress, long overallTotal) {
        if (overallTotal == 0) {
            return "";
        }
        return String.format("%.2f", new Object[]{Float.valueOf(((float) overallProgress) / 1048576.0f)}) + "MB /" + String.format("%.2f", new Object[]{Float.valueOf(((float) overallTotal) / 1048576.0f)}) + "MB";
    }

    public static String getDownloadProgressPercent(long overallProgress, long overallTotal) {
        if (overallTotal == 0) {
            return "";
        }
        return Long.toString((100 * overallProgress) / overallTotal) + "%";
    }

    public static String getTimeRemaining(long durationInMilliseconds) {
        SimpleDateFormat sdf;
        if (durationInMilliseconds > 3600000) {
            sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        } else {
            sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        }
        return sdf.format(new Date(durationInMilliseconds - ((long) TimeZone.getDefault().getRawOffset())));
    }

    public static String getExpansionAPKFileName(Context c, boolean mainFile, int versionCode) {
        return (mainFile ? "main." : "patch.") + versionCode + "." + c.getPackageName() + ".obb";
    }

    public static String generateSaveFileName(Context c, String fileName) {
        return getSaveFilePath(c) + File.separator + fileName;
    }

    public static String getSaveFilePath(Context c) {
        return Environment.getExternalStorageDirectory().toString() + Constants.EXP_PATH + c.getPackageName();
    }

    public static boolean doesFileExist(Context c, String fileName, long fileSize, boolean deleteFileOnMismatch) {
        File fileForNewFile = new File(generateSaveFileName(c, fileName));
        if (fileForNewFile.exists()) {
            if (fileForNewFile.length() == fileSize) {
                return true;
            }
            if (deleteFileOnMismatch) {
                fileForNewFile.delete();
            }
        }
        return false;
    }

    public static int getDownloaderStringResourceIDFromState(int state) {
        switch (state) {
            case TTSConst.TTSMULTILINE /*1*/:
                return C0154R.string.state_idle;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return C0154R.string.state_fetching_url;
            case TTSConst.TTSUNICODE /*3*/:
                return C0154R.string.state_connecting;
            case TTSConst.TTSXML /*4*/:
                return C0154R.string.state_downloading;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return C0154R.string.state_completed;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return C0154R.string.state_paused_network_unavailable;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return C0154R.string.state_paused_by_request;
            case TTSConst.TTSEVT_TAG /*8*/:
                return C0154R.string.state_paused_wifi_disabled;
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return C0154R.string.state_paused_wifi_unavailable;
            case TTSConst.TTSEVT_RESUME /*10*/:
                return C0154R.string.state_paused_wifi_disabled;
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return C0154R.string.state_paused_wifi_unavailable;
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                return C0154R.string.state_paused_roaming;
            case TTSConst.TTSEVT_AUDIO /*13*/:
                return C0154R.string.state_paused_network_setup_failure;
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                return C0154R.string.state_paused_sdcard_unavailable;
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                return C0154R.string.state_failed_unlicensed;
            case TTSConst.TTSEVT_ERROR /*16*/:
                return C0154R.string.state_failed_fetching_url;
            case TTSConst.TTSEVT_JUMP /*17*/:
                return C0154R.string.state_failed_sdcard_full;
            case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                return C0154R.string.state_failed_cancelled;
            default:
                return C0154R.string.state_unknown;
        }
    }
}
