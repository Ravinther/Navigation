package com.amazon.device.iap.internal.util;

/* renamed from: com.amazon.device.iap.internal.util.d */
public class Validator {
    public static void m60a(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(str + " must not be null");
        }
    }

    public static boolean m61a(String str) {
        return str == null || str.trim().length() == 0;
    }
}
