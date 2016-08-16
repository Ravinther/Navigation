package io.fabric.sdk.android;

import android.util.Log;

public class DefaultLogger implements Logger {
    private int logLevel;

    public DefaultLogger(int logLevel) {
        this.logLevel = logLevel;
    }

    public DefaultLogger() {
        this.logLevel = 4;
    }

    public boolean isLoggable(String tag, int level) {
        return this.logLevel <= level;
    }

    public void m1462d(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 3)) {
            Log.d(tag, text, throwable);
        }
    }

    public void m1468v(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 2)) {
            Log.v(tag, text, throwable);
        }
    }

    public void m1466i(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 4)) {
            Log.i(tag, text, throwable);
        }
    }

    public void m1470w(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 5)) {
            Log.w(tag, text, throwable);
        }
    }

    public void m1464e(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 6)) {
            Log.e(tag, text, throwable);
        }
    }

    public void m1461d(String tag, String text) {
        m1462d(tag, text, null);
    }

    public void m1467v(String tag, String text) {
        m1468v(tag, text, null);
    }

    public void m1465i(String tag, String text) {
        m1466i(tag, text, null);
    }

    public void m1469w(String tag, String text) {
        m1470w(tag, text, null);
    }

    public void m1463e(String tag, String text) {
        m1464e(tag, text, null);
    }

    public void log(int priority, String tag, String msg) {
        log(priority, tag, msg, false);
    }

    public void log(int priority, String tag, String msg, boolean forceLog) {
        if (forceLog || isLoggable(tag, priority)) {
            Log.println(priority, tag, msg);
        }
    }
}
