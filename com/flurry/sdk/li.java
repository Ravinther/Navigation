package com.flurry.sdk;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class li {
    private static li f1113a;
    private final UncaughtExceptionHandler f1114b;
    private final Map<UncaughtExceptionHandler, Void> f1115c;

    /* renamed from: com.flurry.sdk.li.a */
    final class C0531a implements UncaughtExceptionHandler {
        final /* synthetic */ li f1112a;

        private C0531a(li liVar) {
            this.f1112a = liVar;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            this.f1112a.m1306a(thread, th);
            this.f1112a.m1309b(thread, th);
        }
    }

    public static synchronized li m1304a() {
        li liVar;
        synchronized (li.class) {
            if (f1113a == null) {
                f1113a = new li();
            }
            liVar = f1113a;
        }
        return liVar;
    }

    public static synchronized void m1307b() {
        synchronized (li.class) {
            if (f1113a != null) {
                f1113a.m1311d();
            }
            f1113a = null;
        }
    }

    public void m1312a(UncaughtExceptionHandler uncaughtExceptionHandler) {
        synchronized (this.f1115c) {
            this.f1115c.put(uncaughtExceptionHandler, null);
        }
    }

    private Set<UncaughtExceptionHandler> m1310c() {
        Set<UncaughtExceptionHandler> keySet;
        synchronized (this.f1115c) {
            keySet = this.f1115c.keySet();
        }
        return keySet;
    }

    private li() {
        this.f1115c = new WeakHashMap();
        this.f1114b = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new C0531a());
    }

    private void m1311d() {
        Thread.setDefaultUncaughtExceptionHandler(this.f1114b);
    }

    private void m1306a(Thread thread, Throwable th) {
        for (UncaughtExceptionHandler uncaughtException : m1310c()) {
            try {
                uncaughtException.uncaughtException(thread, th);
            } catch (Throwable th2) {
            }
        }
    }

    private void m1309b(Thread thread, Throwable th) {
        if (this.f1114b != null) {
            try {
                this.f1114b.uncaughtException(thread, th);
            } catch (Throwable th2) {
            }
        }
    }
}
