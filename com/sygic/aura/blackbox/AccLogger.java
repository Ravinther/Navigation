package com.sygic.aura.blackbox;

import java.text.SimpleDateFormat;

public class AccLogger {
    private static SimpleDateFormat mDateFormat;
    private static float mValue;

    static {
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mValue = 0.0f;
    }

    public static void startLogging() {
    }

    public static void log() {
    }

    public static void stopLogging() {
    }

    public static void addValue(float maxAcceleration) {
    }
}
