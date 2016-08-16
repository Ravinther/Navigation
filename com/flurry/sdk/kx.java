package com.flurry.sdk;

public class kx {
    private static long f1097a;
    private static kx f1098b;
    private final ky f1099c;

    static {
        f1097a = 100;
        f1098b = null;
    }

    public static synchronized kx m1220a() {
        kx kxVar;
        synchronized (kx.class) {
            if (f1098b == null) {
                f1098b = new kx();
            }
            kxVar = f1098b;
        }
        return kxVar;
    }

    public static synchronized void m1221b() {
        synchronized (kx.class) {
            if (f1098b != null) {
                f1098b.m1224c();
                f1098b = null;
            }
        }
    }

    public kx() {
        this.f1099c = new ky();
        this.f1099c.m1231a(f1097a);
        this.f1099c.m1232a(true);
    }

    public synchronized void m1222a(jl<kw> jlVar) {
        jm.m997a().m1002a("com.flurry.android.sdk.TickEvent", jlVar);
        if (jm.m997a().m1003b("com.flurry.android.sdk.TickEvent") > 0) {
            this.f1099c.m1230a();
        }
    }

    public synchronized void m1223b(jl<kw> jlVar) {
        jm.m997a().m1004b("com.flurry.android.sdk.TickEvent", jlVar);
        if (jm.m997a().m1003b("com.flurry.android.sdk.TickEvent") == 0) {
            this.f1099c.m1233b();
        }
    }

    public synchronized void m1224c() {
        jm.m997a().m1001a("com.flurry.android.sdk.TickEvent");
        this.f1099c.m1233b();
    }
}
