package com.flurry.sdk;

import android.content.Context;
import android.os.SystemClock;
import com.flurry.sdk.kp.C0522a;
import java.lang.ref.WeakReference;
import loquendo.tts.engine.TTSConst;

public class jb {
    private static final String f886a;
    private final jl<kp> f887b;
    private WeakReference<ko> f888c;
    private volatile long f889d;
    private volatile long f890e;
    private volatile long f891f;
    private volatile long f892g;
    private volatile long f893h;

    /* renamed from: com.flurry.sdk.jb.1 */
    class C04681 implements jl<kp> {
        final /* synthetic */ jb f883a;

        C04681(jb jbVar) {
            this.f883a = jbVar;
        }

        public void m931a(kp kpVar) {
            if (this.f883a.f888c == null || kpVar.f1053b == this.f883a.f888c.get()) {
                switch (C04703.f885a[kpVar.f1054c.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        this.f883a.m938a(kpVar.f1053b, (Context) kpVar.f1052a.get());
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        this.f883a.m937a((Context) kpVar.f1052a.get());
                    case TTSConst.TTSUNICODE /*3*/:
                        this.f883a.m940b((Context) kpVar.f1052a.get());
                    case TTSConst.TTSXML /*4*/:
                        jm.m997a().m1004b("com.flurry.android.sdk.FlurrySessionEvent", this.f883a.f887b);
                        this.f883a.m936a();
                    default:
                }
            }
        }
    }

    /* renamed from: com.flurry.sdk.jb.2 */
    class C04692 extends le {
        final /* synthetic */ jb f884a;

        C04692(jb jbVar) {
            this.f884a = jbVar;
        }

        public void m932a() {
            iu.m877a().m893c();
        }
    }

    /* renamed from: com.flurry.sdk.jb.3 */
    static /* synthetic */ class C04703 {
        static final /* synthetic */ int[] f885a;

        static {
            f885a = new int[C0522a.values().length];
            try {
                f885a[C0522a.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f885a[C0522a.START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f885a[C0522a.END.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f885a[C0522a.FINALIZE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static {
        f886a = jb.class.getSimpleName();
    }

    public jb() {
        this.f887b = new C04681(this);
        this.f889d = 0;
        this.f890e = 0;
        this.f891f = -1;
        this.f892g = 0;
        this.f893h = 0;
        jm.m997a().m1002a("com.flurry.android.sdk.FlurrySessionEvent", this.f887b);
    }

    public void m938a(ko koVar, Context context) {
        this.f888c = new WeakReference(koVar);
        this.f889d = System.currentTimeMillis();
        this.f890e = SystemClock.elapsedRealtime();
        m935b(koVar, context);
        jc.m946a().m955b(new C04692(this));
    }

    private void m935b(ko koVar, Context context) {
        if (koVar == null || context == null) {
            jq.m1016a(3, f886a, "Flurry session id cannot be created.");
            return;
        }
        jq.m1016a(3, f886a, "Flurry session id started:" + this.f889d);
        kp kpVar = new kp();
        kpVar.f1052a = new WeakReference(context);
        kpVar.f1053b = koVar;
        kpVar.f1054c = C0522a.SESSION_ID_CREATED;
        kpVar.m868b();
    }

    public void m937a(Context context) {
        long c = kq.m1187a().m1200c();
        if (c > 0) {
            this.f892g = (System.currentTimeMillis() - c) + this.f892g;
        }
    }

    public void m940b(Context context) {
        this.f891f = SystemClock.elapsedRealtime() - this.f890e;
    }

    public void m936a() {
    }

    public String m939b() {
        return Long.toString(this.f889d);
    }

    public long m941c() {
        return this.f889d;
    }

    public long m942d() {
        return this.f890e;
    }

    public long m943e() {
        return this.f891f;
    }

    public long m944f() {
        return this.f892g;
    }

    public synchronized long m945g() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.f890e;
        if (elapsedRealtime <= this.f893h) {
            elapsedRealtime = this.f893h + 1;
            this.f893h = elapsedRealtime;
        }
        this.f893h = elapsedRealtime;
        return this.f893h;
    }
}
