package com.amazon.device.iap.internal;

import android.util.Log;
import com.amazon.device.iap.internal.p000a.SandboxImplementationRegistry;
import com.amazon.device.iap.internal.p001b.KiwiImplementationRegistry;

/* renamed from: com.amazon.device.iap.internal.e */
public final class ImplementationFactory {
    private static final String f50a;
    private static volatile boolean f51b;
    private static volatile boolean f52c;
    private static volatile RequestHandler f53d;
    private static volatile LogHandler f54e;
    private static volatile ImplementationRegistry f55f;

    static {
        f50a = ImplementationFactory.class.getName();
    }

    private static ImplementationRegistry m59d() {
        if (f55f == null) {
            synchronized (ImplementationFactory.class) {
                if (f55f == null) {
                    if (ImplementationFactory.m56a()) {
                        f55f = new SandboxImplementationRegistry();
                    } else {
                        f55f = new KiwiImplementationRegistry();
                    }
                }
            }
        }
        return f55f;
    }

    public static boolean m56a() {
        if (f52c) {
            return f51b;
        }
        synchronized (ImplementationFactory.class) {
            if (f52c) {
                boolean z = f51b;
                return z;
            }
            try {
                ImplementationFactory.class.getClassLoader().loadClass("com.amazon.android.Kiwi");
                f51b = false;
            } catch (Throwable th) {
                f51b = true;
            }
            f52c = true;
            return f51b;
        }
    }

    public static RequestHandler m57b() {
        if (f53d == null) {
            synchronized (ImplementationFactory.class) {
                if (f53d == null) {
                    f53d = (RequestHandler) ImplementationFactory.m55a(RequestHandler.class);
                }
            }
        }
        return f53d;
    }

    public static LogHandler m58c() {
        if (f54e == null) {
            synchronized (ImplementationFactory.class) {
                if (f54e == null) {
                    f54e = (LogHandler) ImplementationFactory.m55a(LogHandler.class);
                }
            }
        }
        return f54e;
    }

    private static <T> T m55a(Class<T> cls) {
        T t = null;
        try {
            t = ImplementationFactory.m59d().m16a(cls).newInstance();
        } catch (Throwable e) {
            Log.e(f50a, "error getting instance for " + cls, e);
        }
        return t;
    }
}
