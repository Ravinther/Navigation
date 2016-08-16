package com.amazon.device.iap.internal.p000a;

import android.util.Log;
import com.amazon.device.iap.internal.LogHandler;

/* renamed from: com.amazon.device.iap.internal.a.a */
public class SandboxLogHandler implements LogHandler {
    public boolean m6a() {
        return true;
    }

    public boolean m8b() {
        return true;
    }

    public void m5a(String str, String str2) {
        Log.d(str, SandboxLogHandler.m4a(str2));
    }

    public void m7b(String str, String str2) {
        Log.e(str, SandboxLogHandler.m4a(str2));
    }

    private static String m4a(String str) {
        return "In App Purchasing SDK - Sandbox Mode: " + str;
    }
}
