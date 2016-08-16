package com.flurry.sdk;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class le implements Runnable {
    private static final String f549a;
    PrintStream f550h;
    PrintWriter f551i;

    public abstract void m491a();

    static {
        f549a = le.class.getSimpleName();
    }

    public final void run() {
        try {
            m491a();
        } catch (Throwable th) {
            if (this.f550h != null) {
                th.printStackTrace(this.f550h);
            } else if (this.f551i != null) {
                th.printStackTrace(this.f551i);
            } else {
                th.printStackTrace();
            }
            jq.m1017a(6, f549a, "", th);
        }
    }
}
