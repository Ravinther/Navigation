package com.sygic.aura.tracker;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.sygic.aura.SygicPreferences;
import com.sygic.aura.connectivity.ConnectivityChangesManager;
import com.sygic.aura.utils.FileUtils;
import java.util.HashMap;
import java.util.UUID;

public class TrackerUtils {
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }

    public static HashMap<String, Object> getConnectivityMetrics(Context context) {
        HashMap<String, Object> metrics = new HashMap();
        putConnectivityType(context, metrics);
        return metrics;
    }

    public static void putConnectivityType(Context context, HashMap<String, Object> metrics) {
        String connString = getConnectivityType(context);
        if (connString != null) {
            metrics.put("connectivity", connString);
        }
    }

    public static String getConnectivityType(Context context) {
        if (context == null) {
            return null;
        }
        return ConnectivityChangesManager.stringFromNetworkInfo(((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo());
    }

    public static long getFreeDiskSpaceInMegabytes(Context context) {
        if (context == null) {
            return -1;
        }
        return bytesToMegabytes(getDiskSpaceInBytes(SygicPreferences.getSygicDirPath(context), true));
    }

    public static long getTotalDiskSpaceInMegabytes(Context context) {
        if (context == null) {
            return -1;
        }
        return bytesToMegabytes(getDiskSpaceInBytes(SygicPreferences.getSygicDirPath(context), false));
    }

    @TargetApi(18)
    private static long getDiskSpaceInBytes(String strPath, boolean calculateFreeSpace) {
        if (strPath == null || !FileUtils.fileExists(strPath)) {
            strPath = FileUtils.getSDCardPath();
        }
        StatFs stats = new StatFs(strPath);
        if (calculateFreeSpace) {
            if (VERSION.SDK_INT >= 18) {
                return stats.getAvailableBlocksLong() * stats.getBlockSizeLong();
            }
            return ((long) stats.getAvailableBlocks()) * ((long) stats.getBlockSize());
        } else if (VERSION.SDK_INT >= 18) {
            return stats.getBlockCountLong() * stats.getBlockSizeLong();
        } else {
            return ((long) stats.getBlockCount()) * ((long) stats.getBlockSize());
        }
    }

    public static String toSizeBucket(long size) {
        if (size == 0) {
            return "Download skipped";
        }
        if (size < 5242880) {
            return "<5MB";
        }
        if (size < 20971520) {
            return "<20MB";
        }
        if (size < 52428800) {
            return "<50 MB";
        }
        if (size < 104857600) {
            return "<100 MB";
        }
        if (size < 209715200) {
            return "<200 MB";
        }
        if (size < 314572800) {
            return "<300 MB";
        }
        if (size < 524288000) {
            return "<500 MB";
        }
        if (size < 1073741824) {
            return "<1 GB";
        }
        if (size < 2147483648L) {
            return "<2 GB";
        }
        if (size < 3221225472L) {
            return "<3 GB";
        }
        return ">3 GB";
    }

    public static String getDeviceId(Context context) {
        String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        deviceId = getWifiMacAddreass(context);
        if (deviceId != null || !tryEnableWifi(context)) {
            return deviceId;
        }
        deviceId = getWifiMacAddreass(context);
        tryDisableWifi(context);
        return deviceId;
    }

    private static String getWifiMacAddreass(Context context) {
        String strId = null;
        WifiManager wm = (WifiManager) context.getSystemService("wifi");
        if (wm != null) {
            WifiInfo wi = wm.getConnectionInfo();
            if (wi == null) {
                return null;
            }
            strId = wi.getMacAddress();
            if (strId != null) {
                strId = strId.replace(":", "");
            }
        }
        return strId;
    }

    private static boolean tryEnableWifi(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService("wifi");
        if (wm == null || wm.getWifiState() != 1) {
            return false;
        }
        wm.setWifiEnabled(true);
        int i = 0;
        while (true) {
            if (wm.getWifiState() == 3 && i <= 10) {
                return true;
            }
            i++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    private static void tryDisableWifi(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService("wifi");
        if (wm != null) {
            wm.setWifiEnabled(false);
        }
    }

    public static <K, V> V get(HashMap<K, V> map, K key, V defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        V value = map.get(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / 1048576;
    }
}
