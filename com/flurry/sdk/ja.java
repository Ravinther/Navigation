package com.flurry.sdk;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class ja extends jp<jy> {
    private static ja f882a;

    static {
        f882a = null;
    }

    public static synchronized ja m928a() {
        ja jaVar;
        synchronized (ja.class) {
            if (f882a == null) {
                f882a = new ja();
            }
            jaVar = f882a;
        }
        return jaVar;
    }

    public static synchronized void m929b() {
        synchronized (ja.class) {
            if (f882a != null) {
                f882a.m927c();
            }
            f882a = null;
        }
    }

    protected ja() {
        super(ja.class.getName(), 0, 5, 5000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(11, new jn()));
    }
}
