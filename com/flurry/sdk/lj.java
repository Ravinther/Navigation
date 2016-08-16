package com.flurry.sdk;

public class lj {
    private static final String f1116a;
    private static boolean f1117b;

    static {
        f1116a = lj.class.getSimpleName();
    }

    public static synchronized void m1313a() {
        synchronized (lj.class) {
            if (!f1117b) {
                js.m1035a(ip.class, 10);
                try {
                    js.m1035a(hi.class, 10);
                } catch (NoClassDefFoundError e) {
                    jq.m1016a(3, f1116a, "Analytics module not available");
                }
                try {
                    js.m1035a(lh.class, 10);
                } catch (NoClassDefFoundError e2) {
                    jq.m1016a(3, f1116a, "Crash module not available");
                }
                try {
                    js.m1035a(i.class, 10);
                } catch (NoClassDefFoundError e3) {
                    jq.m1016a(3, f1116a, "Ads module not available");
                }
                f1117b = true;
            }
        }
    }
}
