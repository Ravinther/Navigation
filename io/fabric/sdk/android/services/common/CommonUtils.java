package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import loquendo.tts.engine.TTSConst;

public class CommonUtils {
    public static final Comparator<File> FILE_MODIFIED_COMPARATOR;
    private static final char[] HEX_VALUES;
    private static Boolean clsTrace;
    private static Boolean loggingEnabled;
    private static long totalRamInBytes;

    /* renamed from: io.fabric.sdk.android.services.common.CommonUtils.1 */
    static class C18141 implements Comparator<File> {
        C18141() {
        }

        public int compare(File file0, File file1) {
            return (int) (file0.lastModified() - file1.lastModified());
        }
    }

    enum Architecture {
        X86_32,
        X86_64,
        ARM_UNKNOWN,
        PPC,
        PPC64,
        ARMV6,
        ARMV7,
        UNKNOWN,
        ARMV7S,
        ARM64;
        
        private static final Map<String, Architecture> matcher;

        static {
            matcher = new HashMap(4);
            matcher.put("armeabi-v7a", ARMV7);
            matcher.put("armeabi", ARMV6);
            matcher.put("x86", X86_32);
        }

        static Architecture getValue() {
            String arch = Build.CPU_ABI;
            if (TextUtils.isEmpty(arch)) {
                Fabric.getLogger().m1453d("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
                return UNKNOWN;
            }
            Architecture value = (Architecture) matcher.get(arch.toLowerCase(Locale.US));
            if (value == null) {
                return UNKNOWN;
            }
            return value;
        }
    }

    public static java.lang.String getAppIconHashOrNull(android.content.Context r7) {
        /* JADX: method processing error */
/*
        Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
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
        r3 = 0;
        r1 = 0;
        r4 = r7.getResources();	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r5 = getAppIconResourceId(r7);	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r1 = r4.openRawResource(r5);	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r2 = sha1(r1);	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r4 = isNullOrEmpty(r2);	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        if (r4 == 0) goto L_0x0019;
    L_0x0018:
        r2 = r3;
    L_0x0019:
        r3 = "Failed to close icon input stream.";
        closeOrLog(r1, r3);
    L_0x001f:
        return r2;
    L_0x0020:
        r0 = move-exception;
        r4 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r5 = "Fabric";	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r6 = "Could not calculate hash for app icon.";	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r4.m1456e(r5, r6, r0);	 Catch:{ Exception -> 0x0020, all -> 0x0036 }
        r4 = "Failed to close icon input stream.";
        closeOrLog(r1, r4);
        r2 = r3;
        goto L_0x001f;
    L_0x0036:
        r3 = move-exception;
        r4 = "Failed to close icon input stream.";
        closeOrLog(r1, r4);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.common.CommonUtils.getAppIconHashOrNull(android.content.Context):java.lang.String");
    }

    static {
        clsTrace = null;
        HEX_VALUES = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        totalRamInBytes = -1;
        loggingEnabled = null;
        FILE_MODIFIED_COMPARATOR = new C18141();
    }

    public static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences("com.crashlytics.prefs", 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String extractFieldFromSystemFile(java.io.File r11, java.lang.String r12) {
        /*
        r9 = 1;
        r6 = 0;
        r7 = r11.exists();
        if (r7 == 0) goto L_0x003c;
    L_0x0008:
        r0 = 0;
        r1 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x003d }
        r7 = new java.io.FileReader;	 Catch:{ Exception -> 0x003d }
        r7.<init>(r11);	 Catch:{ Exception -> 0x003d }
        r8 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r1.<init>(r7, r8);	 Catch:{ Exception -> 0x003d }
    L_0x0015:
        r3 = r1.readLine();	 Catch:{ Exception -> 0x006e, all -> 0x006b }
        if (r3 == 0) goto L_0x0036;
    L_0x001b:
        r7 = "\\s*:\\s*";
        r4 = java.util.regex.Pattern.compile(r7);	 Catch:{ Exception -> 0x006e, all -> 0x006b }
        r7 = 2;
        r5 = r4.split(r3, r7);	 Catch:{ Exception -> 0x006e, all -> 0x006b }
        r7 = r5.length;	 Catch:{ Exception -> 0x006e, all -> 0x006b }
        if (r7 <= r9) goto L_0x0015;
    L_0x002a:
        r7 = 0;
        r7 = r5[r7];	 Catch:{ Exception -> 0x006e, all -> 0x006b }
        r7 = r7.equals(r12);	 Catch:{ Exception -> 0x006e, all -> 0x006b }
        if (r7 == 0) goto L_0x0015;
    L_0x0033:
        r7 = 1;
        r6 = r5[r7];	 Catch:{ Exception -> 0x006e, all -> 0x006b }
    L_0x0036:
        r7 = "Failed to close system file reader.";
        closeOrLog(r1, r7);
    L_0x003c:
        return r6;
    L_0x003d:
        r2 = move-exception;
    L_0x003e:
        r7 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ all -> 0x0063 }
        r8 = "Fabric";
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0063 }
        r9.<init>();	 Catch:{ all -> 0x0063 }
        r10 = "Error parsing ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x0063 }
        r9 = r9.append(r11);	 Catch:{ all -> 0x0063 }
        r9 = r9.toString();	 Catch:{ all -> 0x0063 }
        r7.m1456e(r8, r9, r2);	 Catch:{ all -> 0x0063 }
        r7 = "Failed to close system file reader.";
        closeOrLog(r0, r7);
        goto L_0x003c;
    L_0x0063:
        r7 = move-exception;
    L_0x0064:
        r8 = "Failed to close system file reader.";
        closeOrLog(r0, r8);
        throw r7;
    L_0x006b:
        r7 = move-exception;
        r0 = r1;
        goto L_0x0064;
    L_0x006e:
        r2 = move-exception;
        r0 = r1;
        goto L_0x003e;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.common.CommonUtils.extractFieldFromSystemFile(java.io.File, java.lang.String):java.lang.String");
    }

    public static int getCpuArchitectureInt() {
        return Architecture.getValue().ordinal();
    }

    public static synchronized long getTotalRamInBytes() {
        long j;
        synchronized (CommonUtils.class) {
            if (totalRamInBytes == -1) {
                long bytes = 0;
                String result = extractFieldFromSystemFile(new File("/proc/meminfo"), "MemTotal");
                if (!TextUtils.isEmpty(result)) {
                    result = result.toUpperCase(Locale.US);
                    try {
                        if (result.endsWith("KB")) {
                            bytes = convertMemInfoToBytes(result, "KB", 1024);
                        } else if (result.endsWith("MB")) {
                            bytes = convertMemInfoToBytes(result, "MB", 1048576);
                        } else if (result.endsWith("GB")) {
                            bytes = convertMemInfoToBytes(result, "GB", 1073741824);
                        } else {
                            Fabric.getLogger().m1453d("Fabric", "Unexpected meminfo format while computing RAM: " + result);
                        }
                    } catch (NumberFormatException e) {
                        Fabric.getLogger().m1456e("Fabric", "Unexpected meminfo format while computing RAM: " + result, e);
                    }
                }
                totalRamInBytes = bytes;
            }
            j = totalRamInBytes;
        }
        return j;
    }

    static long convertMemInfoToBytes(String memInfo, String notation, int notationMultiplier) {
        return Long.parseLong(memInfo.split(notation)[0].trim()) * ((long) notationMultiplier);
    }

    public static RunningAppProcessInfo getAppProcessInfo(String packageName, Context context) {
        List<RunningAppProcessInfo> processes = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (processes == null) {
            return null;
        }
        for (RunningAppProcessInfo info : processes) {
            if (info.processName.equals(packageName)) {
                return info;
            }
        }
        return null;
    }

    public static String streamToString(InputStream is) throws IOException {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String sha1(String source) {
        return hash(source, "SHA-1");
    }

    public static String sha1(InputStream source) {
        return hash(source, "SHA-1");
    }

    private static String hash(InputStream source, String sha1Instance) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[1024];
            while (true) {
                int length = source.read(buffer);
                if (length == -1) {
                    return hexify(digest.digest());
                }
                digest.update(buffer, 0, length);
            }
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Fabric", "Could not calculate hash for app icon.", e);
            return "";
        }
    }

    private static String hash(byte[] bytes, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(bytes);
            return hexify(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            Fabric.getLogger().m1456e("Fabric", "Could not create hashing algorithm: " + algorithm + ", returning empty string.", e);
            return "";
        }
    }

    private static String hash(String s, String algorithm) {
        return hash(s.getBytes(), algorithm);
    }

    public static String createInstanceIdFrom(String... sliceIds) {
        if (sliceIds == null || sliceIds.length == 0) {
            return null;
        }
        List<String> sliceIdList = new ArrayList();
        for (String id : sliceIds) {
            if (id != null) {
                sliceIdList.add(id.replace("-", "").toLowerCase(Locale.US));
            }
        }
        Collections.sort(sliceIdList);
        StringBuilder sb = new StringBuilder();
        for (String id2 : sliceIdList) {
            sb.append(id2);
        }
        String concatValue = sb.toString();
        if (concatValue.length() > 0) {
            return sha1(concatValue);
        }
        return null;
    }

    public static long calculateFreeRamInBytes(Context context) {
        MemoryInfo mi = new MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(mi);
        return mi.availMem;
    }

    public static long calculateUsedDiskSpaceInBytes(String path) {
        StatFs statFs = new StatFs(path);
        long blockSizeBytes = (long) statFs.getBlockSize();
        return (blockSizeBytes * ((long) statFs.getBlockCount())) - (blockSizeBytes * ((long) statFs.getAvailableBlocks()));
    }

    public static float getBatteryLevel(Context context) {
        Intent battery = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return ((float) battery.getIntExtra("level", -1)) / ((float) battery.getIntExtra("scale", -1));
    }

    public static boolean getProximitySensorEnabled(Context context) {
        if (isEmulator(context) || ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8) == null) {
            return false;
        }
        return true;
    }

    public static void logControlled(Context context, String msg) {
        if (isClsTrace(context)) {
            Fabric.getLogger().m1453d("Fabric", msg);
        }
    }

    public static void logControlledError(Context context, String msg, Throwable tr) {
        if (isClsTrace(context)) {
            Fabric.getLogger().m1455e("Fabric", msg);
        }
    }

    public static void logControlled(Context context, int level, String tag, String msg) {
        if (isClsTrace(context)) {
            Fabric.getLogger().log(level, "Fabric", msg);
        }
    }

    public static boolean isClsTrace(Context context) {
        if (clsTrace == null) {
            clsTrace = Boolean.valueOf(getBooleanResourceValue(context, "com.crashlytics.Trace", false));
        }
        return clsTrace.booleanValue();
    }

    public static boolean getBooleanResourceValue(Context context, String key, boolean defaultValue) {
        if (context == null) {
            return defaultValue;
        }
        Resources resources = context.getResources();
        if (resources == null) {
            return defaultValue;
        }
        int id = getResourcesIdentifier(context, key, "bool");
        if (id > 0) {
            return resources.getBoolean(id);
        }
        id = getResourcesIdentifier(context, key, "string");
        if (id > 0) {
            return Boolean.parseBoolean(context.getString(id));
        }
        return defaultValue;
    }

    public static int getResourcesIdentifier(Context context, String key, String resourceType) {
        return context.getResources().getIdentifier(key, resourceType, getResourcePackageName(context));
    }

    public static boolean isEmulator(Context context) {
        return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || Secure.getString(context.getContentResolver(), "android_id") == null;
    }

    public static boolean isRooted(Context context) {
        boolean isEmulator = isEmulator(context);
        String buildTags = Build.TAGS;
        if ((!isEmulator && buildTags != null && buildTags.contains("test-keys")) || new File("/system/app/Superuser.apk").exists()) {
            return true;
        }
        File file = new File("/system/xbin/su");
        if (isEmulator || !file.exists()) {
            return false;
        }
        return true;
    }

    public static boolean isDebuggerAttached() {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }

    public static int getDeviceState(Context context) {
        int deviceState = 0;
        if (isEmulator(context)) {
            deviceState = 0 | 1;
        }
        if (isRooted(context)) {
            deviceState |= 2;
        }
        if (isDebuggerAttached()) {
            return deviceState | 4;
        }
        return deviceState;
    }

    public static int getBatteryVelocity(Context context, boolean powerConnected) {
        float batterLevel = getBatteryLevel(context);
        if (!powerConnected) {
            return 1;
        }
        if (powerConnected && ((double) batterLevel) >= 99.0d) {
            return 3;
        }
        if (!powerConnected || ((double) batterLevel) >= 99.0d) {
            return 0;
        }
        return 2;
    }

    @SuppressLint({"GetInstance"})
    public static Cipher createCipher(int mode, String key) throws InvalidKeyException {
        if (key.length() < 32) {
            throw new InvalidKeyException("Key must be at least 32 bytes.");
        }
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), 0, 32, "AES/ECB/PKCS7Padding");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(mode, secretKey);
            return cipher;
        } catch (GeneralSecurityException e) {
            Fabric.getLogger().m1456e("Fabric", "Could not create Cipher for AES/ECB/PKCS7Padding - should never happen.", e);
            throw new RuntimeException(e);
        }
    }

    public static String hexify(byte[] bytes) {
        char[] hexChars = new char[(bytes.length * 2)];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 255;
            hexChars[i * 2] = HEX_VALUES[v >>> 4];
            hexChars[(i * 2) + 1] = HEX_VALUES[v & 15];
        }
        return new String(hexChars);
    }

    public static boolean isAppDebuggable(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    public static String getStringsFileValue(Context context, String key) {
        int id = getResourcesIdentifier(context, key, "string");
        if (id > 0) {
            return context.getString(id);
        }
        return "";
    }

    public static void closeOrLog(Closeable c, String message) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                Fabric.getLogger().m1456e("Fabric", message, e);
            }
        }
    }

    public static void flushOrLog(Flushable f, String message) {
        if (f != null) {
            try {
                f.flush();
            } catch (IOException e) {
                Fabric.getLogger().m1456e("Fabric", message, e);
            }
        }
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static String padWithZerosToMaxIntWidth(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must be zero or greater");
        }
        return String.format(Locale.US, "%1$10s", new Object[]{Integer.valueOf(value)}).replace(' ', '0');
    }

    public static String getResourcePackageName(Context context) {
        int iconId = context.getApplicationContext().getApplicationInfo().icon;
        if (iconId > 0) {
            return context.getResources().getResourcePackageName(iconId);
        }
        return context.getPackageName();
    }

    public static void copyStream(InputStream is, OutputStream os, byte[] buffer) throws IOException {
        while (true) {
            int count = is.read(buffer);
            if (count != -1) {
                os.write(buffer, 0, count);
            } else {
                return;
            }
        }
    }

    public static String logPriorityToString(int priority) {
        switch (priority) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "V";
            case TTSConst.TTSUNICODE /*3*/:
                return "D";
            case TTSConst.TTSXML /*4*/:
                return "I";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "W";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "E";
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return "A";
            default:
                return "?";
        }
    }

    public static int getAppIconResourceId(Context context) {
        return context.getApplicationContext().getApplicationInfo().icon;
    }

    public static String resolveBuildId(Context context) {
        int id = getResourcesIdentifier(context, "io.fabric.android.build_id", "string");
        if (id == 0) {
            id = getResourcesIdentifier(context, "com.crashlytics.android.build_id", "string");
        }
        if (id == 0) {
            return null;
        }
        String buildId = context.getResources().getString(id);
        Fabric.getLogger().m1453d("Fabric", "Build ID is: " + buildId);
        return buildId;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission) == 0;
    }

    public static boolean canTryConnection(Context context) {
        if (!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return true;
        }
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
}
