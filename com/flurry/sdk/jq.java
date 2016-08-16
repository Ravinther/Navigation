package com.flurry.sdk;

import android.text.TextUtils;
import android.util.Log;

public final class jq {
    private static int f946a;
    private static boolean f947b;
    private static int f948c;
    private static boolean f949d;

    static {
        f946a = 4000;
        f947b = false;
        f948c = 5;
        f949d = false;
    }

    public static void m1014a() {
        f947b = true;
    }

    public static void m1021b() {
        f947b = false;
    }

    public static int m1025c() {
        return f948c;
    }

    public static boolean m1031d() {
        return f949d;
    }

    public static void m1015a(int i) {
        f948c = i;
    }

    public static void m1020a(boolean z) {
        f949d = z;
    }

    public static void m1018a(String str, String str2) {
        m1022b(3, str, str2);
    }

    public static void m1019a(String str, String str2, Throwable th) {
        m1023b(6, str, str2, th);
    }

    public static void m1024b(String str, String str2) {
        m1022b(6, str, str2);
    }

    public static void m1028c(String str, String str2) {
        m1022b(4, str, str2);
    }

    public static void m1030d(String str, String str2) {
        m1022b(2, str, str2);
    }

    public static void m1032e(String str, String str2) {
        m1022b(5, str, str2);
    }

    public static void m1017a(int i, String str, String str2, Throwable th) {
        m1027c(i, str, str2, th);
    }

    public static void m1016a(int i, String str, String str2) {
        m1026c(i, str, str2);
    }

    private static void m1023b(int i, String str, String str2, Throwable th) {
        m1022b(i, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    private static void m1022b(int i, String str, String str2) {
        if (!f947b && f948c <= i) {
            m1029d(i, str, str2);
        }
    }

    private static void m1027c(int i, String str, String str2, Throwable th) {
        m1026c(i, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    private static void m1026c(int i, String str, String str2) {
        if (f949d) {
            m1029d(i, str, str2);
        }
    }

    private static void m1029d(int i, String str, String str2) {
        if (!f949d) {
            str = "FlurryAgent";
        }
        int length = TextUtils.isEmpty(str2) ? 0 : str2.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = f946a > length - i2 ? length : f946a + i2;
            if (Log.println(i, str, str2.substring(i2, i3)) > 0) {
                i2 = i3;
            } else {
                return;
            }
        }
    }
}
