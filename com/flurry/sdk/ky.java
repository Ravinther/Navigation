package com.flurry.sdk;

public class ky {
    private static final String f1101a;
    private long f1102b;
    private boolean f1103c;
    private boolean f1104d;
    private le f1105e;

    /* renamed from: com.flurry.sdk.ky.1 */
    class C05291 extends le {
        final /* synthetic */ ky f1100a;

        C05291(ky kyVar) {
            this.f1100a = kyVar;
        }

        public void m1225a() {
            new kw().m868b();
            if (this.f1100a.f1103c && this.f1100a.f1104d) {
                jc.m946a().m956b(this.f1100a.f1105e, this.f1100a.f1102b);
            }
        }
    }

    static {
        f1101a = ky.class.getSimpleName();
    }

    public ky() {
        this.f1102b = 1000;
        this.f1103c = true;
        this.f1104d = false;
        this.f1105e = new C05291(this);
    }

    public void m1231a(long j) {
        this.f1102b = j;
    }

    public void m1232a(boolean z) {
        this.f1103c = z;
    }

    public synchronized void m1230a() {
        if (!this.f1104d) {
            jc.m946a().m956b(this.f1105e, this.f1102b);
            this.f1104d = true;
        }
    }

    public synchronized void m1233b() {
        if (this.f1104d) {
            jc.m946a().m958c(this.f1105e);
            this.f1104d = false;
        }
    }
}
