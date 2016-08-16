package com.google.ads.conversiontracking;

import android.os.Looper;

/* renamed from: com.google.ads.conversiontracking.p */
public final class C0563p {
    public static void m1430a(String str) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException(str);
        }
    }
}
