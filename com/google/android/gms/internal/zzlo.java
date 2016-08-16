package com.google.android.gms.internal;

import android.os.SystemClock;

public final class zzlo implements zzlm {
    private static zzlo zzagd;

    public static synchronized zzlm zzpN() {
        zzlm com_google_android_gms_internal_zzlm;
        synchronized (zzlo.class) {
            if (zzagd == null) {
                zzagd = new zzlo();
            }
            com_google_android_gms_internal_zzlm = zzagd;
        }
        return com_google_android_gms_internal_zzlm;
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public long nanoTime() {
        return System.nanoTime();
    }
}
