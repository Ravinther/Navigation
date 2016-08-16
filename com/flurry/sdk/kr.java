package com.flurry.sdk;

import java.util.Timer;
import java.util.TimerTask;

class kr {
    private Timer f1073a;
    private C0528a f1074b;

    /* renamed from: com.flurry.sdk.kr.a */
    class C0528a extends TimerTask {
        final /* synthetic */ kr f1072a;

        C0528a(kr krVar) {
            this.f1072a = krVar;
        }

        public void run() {
            this.f1072a.m1207a();
            new ks().m868b();
        }
    }

    kr() {
    }

    public synchronized void m1208a(long j) {
        if (m1209b()) {
            m1207a();
        }
        this.f1073a = new Timer("FlurrySessionTimer");
        this.f1074b = new C0528a(this);
        this.f1073a.schedule(this.f1074b, j);
    }

    public synchronized void m1207a() {
        if (this.f1073a != null) {
            this.f1073a.cancel();
            this.f1073a = null;
        }
        this.f1074b = null;
    }

    public boolean m1209b() {
        if (this.f1073a != null) {
            return true;
        }
        return false;
    }
}
