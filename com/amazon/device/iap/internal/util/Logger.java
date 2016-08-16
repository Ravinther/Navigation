package com.amazon.device.iap.internal.util;

import com.amazon.device.iap.internal.ImplementationFactory;

/* renamed from: com.amazon.device.iap.internal.util.e */
public class Logger {
    public static boolean m63a() {
        return ImplementationFactory.m58c().m1a();
    }

    public static boolean m65b() {
        return ImplementationFactory.m58c().m3b();
    }

    public static void m62a(String str, String str2) {
        if (Logger.m63a()) {
            ImplementationFactory.m58c().m0a(str, str2);
        }
    }

    public static void m64b(String str, String str2) {
        if (Logger.m65b()) {
            ImplementationFactory.m58c().m2b(str, str2);
        }
    }
}
