package com.amazon.device.iap.internal.p001b;

import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.device.iap.internal.LogHandler;

/* renamed from: com.amazon.device.iap.internal.b.f */
public class KiwiLogHandler implements LogHandler {
    private static KiwiLogger f39a;

    static {
        f39a = new KiwiLogger("In App Purchasing SDK - Production Mode");
    }

    public boolean m40a() {
        return KiwiLogger.TRACE_ON;
    }

    public boolean m42b() {
        return KiwiLogger.ERROR_ON;
    }

    public void m39a(String str, String str2) {
        f39a.trace(KiwiLogHandler.m38c(str, str2));
    }

    public void m41b(String str, String str2) {
        f39a.error(KiwiLogHandler.m38c(str, str2));
    }

    private static String m38c(String str, String str2) {
        return str + ": " + str2;
    }
}
