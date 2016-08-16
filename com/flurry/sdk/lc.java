package com.flurry.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public final class lc {
    private static final String f1108a;

    static {
        f1108a = lc.class.getSimpleName();
    }

    public static void m1264a() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Must be called from the main thread!");
        }
    }

    public static void m1272b() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Must be called from a background thread!");
        }
    }

    public static String m1261a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Uri parse = Uri.parse(str);
        if (parse == null || parse.getScheme() != null) {
            return str;
        }
        return "http://" + str;
    }

    public static String m1270b(String str) {
        return m1262a(str, 255);
    }

    public static String m1262a(String str, int i) {
        if (str == null) {
            return "";
        }
        return str.length() > i ? str.substring(0, i) : str;
    }

    public static String m1273c(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            jq.m1016a(5, f1108a, "Cannot encode '" + str + "'");
            return "";
        }
    }

    public static String m1274d(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            jq.m1016a(5, f1108a, "Cannot decode '" + str + "'");
            return "";
        }
    }

    public static void m1265a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }

    public static byte[] m1275e(String str) {
        byte[] bArr = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                bArr = str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                jq.m1016a(5, f1108a, "Unsupported UTF-8: " + e.getMessage());
            }
        }
        return bArr;
    }

    public static byte[] m1276f(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes(), 0, str.length());
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            jq.m1016a(6, f1108a, "Unsupported SHA1: " + e.getMessage());
            return null;
        }
    }

    public static String m1263a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (byte b : bArr) {
            byte b2 = (byte) (b & 15);
            stringBuilder.append(cArr[(byte) ((b & 240) >> 4)]);
            stringBuilder.append(cArr[b2]);
        }
        return stringBuilder.toString();
    }

    public static String m1271b(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            jq.m1016a(5, f1108a, "Unsupported ISO-8859-1:" + e.getMessage());
            return null;
        }
    }

    public static boolean m1266a(long j) {
        if (j == 0 || System.currentTimeMillis() <= j) {
            return true;
        }
        return false;
    }

    public static boolean m1268a(Intent intent) {
        return jc.m946a().m960e().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static String m1277g(String str) {
        return str.replace("\\b", "").replace("\\n", "").replace("\\r", "").replace("\\t", "").replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
    }

    public static Map<String, String> m1278h(String str) {
        Map<String, String> hashMap = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                if (!split2[0].equals("event")) {
                    hashMap.put(m1274d(split2[0]), m1274d(split2[1]));
                }
            }
        }
        return hashMap;
    }

    public static long m1279i(String str) {
        if (str == null) {
            return 0;
        }
        long j = 1125899906842597L;
        int i = 0;
        while (i < str.length()) {
            long charAt = ((long) str.charAt(i)) + (j * 31);
            i++;
            j = charAt;
        }
        return j;
    }

    public static long m1260a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read < 0) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    public static byte[] m1269a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        m1260a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static double m1259a(double d, int i) {
        return ((double) Math.round(Math.pow(10.0d, (double) i) * d)) / Math.pow(10.0d, (double) i);
    }

    public static boolean m1267a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            jq.m1016a(6, f1108a, "Error occured when checking if app has permission.  Error: " + e.getMessage());
            return false;
        }
    }
}
