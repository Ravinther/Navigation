package com.sygic.aura.helper;

import android.util.Log;
import com.crashlytics.android.Crashlytics;

public class CrashlyticsHelper {
    public static void logError(String tag, String msg) {
        Log.e(tag, msg);
        Crashlytics.log(6, tag, msg);
    }

    public static void logWarning(String tag, String msg) {
        Log.w(tag, msg);
        Crashlytics.log(5, tag, msg);
    }

    public static void logInfo(String tag, String msg) {
        Log.i(tag, msg);
        Crashlytics.log(4, tag, msg);
    }

    public static void logException(String tag, String msg, Throwable e) {
        logException(tag, msg, e, false);
    }

    public static void logException(String tag, String msg, Throwable e, boolean fatal) {
        if (fatal) {
            Log.e(tag, msg, e);
            Crashlytics.log(6, tag, msg);
        } else {
            Log.w(tag, msg, e);
        }
        Crashlytics.logException(e);
    }

    public static void setCustomKey(String key, boolean value) {
        Crashlytics.setBool(key, value);
    }
}
