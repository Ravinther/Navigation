package com.infinario.android.infinariosdk;

public class Contract {
    public static int FLUSH_MAX_INTERVAL;
    public static int FLUSH_MIN_INTERVAL;

    static {
        FLUSH_MIN_INTERVAL = 1000;
        FLUSH_MAX_INTERVAL = 3600000;
    }
}
