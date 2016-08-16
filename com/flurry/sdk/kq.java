package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import com.flurry.sdk.jf.C0471a;
import com.flurry.sdk.kp.C0522a;
import com.flurry.sdk.ku.C0426a;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import loquendo.tts.engine.TTSConst;

public class kq implements C0426a {
    private static kq f1062a;
    private static final String f1063b;
    private final Map<Context, ko> f1064c;
    private final kr f1065d;
    private final Object f1066e;
    private long f1067f;
    private long f1068g;
    private ko f1069h;
    private jl<ks> f1070i;
    private jl<jf> f1071j;

    /* renamed from: com.flurry.sdk.kq.1 */
    class C05231 implements jl<ks> {
        final /* synthetic */ kq f1056a;

        C05231(kq kqVar) {
            this.f1056a = kqVar;
        }

        public void m1182a(ks ksVar) {
            this.f1056a.m1196i();
        }
    }

    /* renamed from: com.flurry.sdk.kq.2 */
    class C05242 implements jl<jf> {
        final /* synthetic */ kq f1057a;

        C05242(kq kqVar) {
            this.f1057a = kqVar;
        }

        public void m1183a(jf jfVar) {
            switch (C05275.f1061a[jfVar.f915b.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    jq.m1016a(3, kq.f1063b, "Automatic onStartSession for context:" + jfVar.f914a);
                    this.f1057a.m1194e(jfVar.f914a);
                case TTSConst.TTSPARAGRAPH /*2*/:
                    jq.m1016a(3, kq.f1063b, "Automatic onEndSession for context:" + jfVar.f914a);
                    this.f1057a.m1203d(jfVar.f914a);
                case TTSConst.TTSUNICODE /*3*/:
                    jq.m1016a(3, kq.f1063b, "Automatic onEndSession (destroyed) for context:" + jfVar.f914a);
                    this.f1057a.m1203d(jfVar.f914a);
                default:
            }
        }
    }

    /* renamed from: com.flurry.sdk.kq.3 */
    class C05253 extends le {
        final /* synthetic */ kq f1058a;

        C05253(kq kqVar) {
            this.f1058a = kqVar;
        }

        public void m1185a() {
            this.f1058a.m1196i();
        }
    }

    /* renamed from: com.flurry.sdk.kq.4 */
    class C05264 extends le {
        final /* synthetic */ ko f1059a;
        final /* synthetic */ kq f1060b;

        C05264(kq kqVar, ko koVar) {
            this.f1060b = kqVar;
            this.f1059a = koVar;
        }

        public void m1186a() {
            this.f1060b.m1193b(this.f1059a);
        }
    }

    /* renamed from: com.flurry.sdk.kq.5 */
    static /* synthetic */ class C05275 {
        static final /* synthetic */ int[] f1061a;

        static {
            f1061a = new int[C0471a.values().length];
            try {
                f1061a[C0471a.kStarted.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1061a[C0471a.kStopped.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1061a[C0471a.kDestroyed.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static synchronized kq m1187a() {
        kq kqVar;
        synchronized (kq.class) {
            if (f1062a == null) {
                f1062a = new kq();
            }
            kqVar = f1062a;
        }
        return kqVar;
    }

    public static synchronized void m1192b() {
        synchronized (kq.class) {
            if (f1062a != null) {
                jm.m997a().m1000a(f1062a.f1070i);
                jm.m997a().m1000a(f1062a.f1071j);
                kt.m1217a().m1215b("ContinueSessionMillis", f1062a);
            }
            f1062a = null;
        }
    }

    static {
        f1063b = kq.class.getSimpleName();
    }

    private kq() {
        this.f1064c = new WeakHashMap();
        this.f1065d = new kr();
        this.f1066e = new Object();
        this.f1070i = new C05231(this);
        this.f1071j = new C05242(this);
        ku a = kt.m1217a();
        this.f1067f = 0;
        this.f1068g = ((Long) a.m1212a("ContinueSessionMillis")).longValue();
        a.m1213a("ContinueSessionMillis", (C0426a) this);
        jq.m1016a(4, f1063b, "initSettings, ContinueSessionMillis = " + this.f1068g);
        jm.m997a().m1002a("com.flurry.android.sdk.ActivityLifecycleEvent", this.f1071j);
        jm.m997a().m1002a("com.flurry.android.sdk.FlurrySessionTimerEvent", this.f1070i);
    }

    public long m1200c() {
        return this.f1067f;
    }

    public synchronized int m1202d() {
        return this.f1064c.size();
    }

    public ko m1204e() {
        ko koVar;
        synchronized (this.f1066e) {
            koVar = this.f1069h;
        }
        return koVar;
    }

    private void m1188a(ko koVar) {
        synchronized (this.f1066e) {
            this.f1069h = koVar;
        }
    }

    private void m1193b(ko koVar) {
        synchronized (this.f1066e) {
            if (this.f1069h == koVar) {
                this.f1069h = null;
            }
        }
    }

    public synchronized void m1197a(Context context) {
        if (context instanceof Activity) {
            if (jg.m974a().m979c()) {
                jq.m1016a(3, f1063b, "bootstrap for context:" + context);
                m1194e(context);
            }
        }
    }

    public synchronized void m1199b(Context context) {
        if (!(jg.m974a().m979c() && (context instanceof Activity))) {
            jq.m1016a(3, f1063b, "Manual onStartSession for context:" + context);
            m1194e(context);
        }
    }

    public synchronized void m1201c(Context context) {
        if (!(jg.m974a().m979c() && (context instanceof Activity))) {
            jq.m1016a(3, f1063b, "Manual onEndSession for context:" + context);
            m1203d(context);
        }
    }

    public synchronized boolean m1205f() {
        boolean z;
        if (m1204e() == null) {
            jq.m1016a(2, f1063b, "Session not found. No active session");
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public synchronized void m1206g() {
        for (Entry entry : this.f1064c.entrySet()) {
            kp kpVar = new kp();
            kpVar.f1052a = new WeakReference(entry.getKey());
            kpVar.f1053b = (ko) entry.getValue();
            kpVar.f1054c = C0522a.END;
            kpVar.f1055d = ip.m818a().m824d();
            kpVar.m868b();
        }
        this.f1064c.clear();
        jc.m946a().m955b(new C05253(this));
    }

    private synchronized void m1194e(Context context) {
        if (((ko) this.f1064c.get(context)) == null) {
            kp kpVar;
            this.f1065d.m1207a();
            ko e = m1204e();
            if (e == null) {
                e = new ko();
                jq.m1032e(f1063b, "Flurry session started for context:" + context);
                kpVar = new kp();
                kpVar.f1052a = new WeakReference(context);
                kpVar.f1053b = e;
                kpVar.f1054c = C0522a.CREATE;
                kpVar.m868b();
            }
            this.f1064c.put(context, e);
            m1188a(e);
            jq.m1032e(f1063b, "Flurry session resumed for context:" + context);
            kpVar = new kp();
            kpVar.f1052a = new WeakReference(context);
            kpVar.f1053b = e;
            kpVar.f1054c = C0522a.START;
            kpVar.m868b();
            this.f1067f = 0;
        } else if (jg.m974a().m979c()) {
            jq.m1016a(3, f1063b, "Session already started with context:" + context);
        } else {
            jq.m1032e(f1063b, "Session already started with context:" + context);
        }
    }

    synchronized void m1203d(Context context) {
        ko koVar = (ko) this.f1064c.remove(context);
        if (koVar != null) {
            jq.m1032e(f1063b, "Flurry session paused for context:" + context);
            kp kpVar = new kp();
            kpVar.f1052a = new WeakReference(context);
            kpVar.f1053b = koVar;
            kpVar.f1055d = ip.m818a().m824d();
            kpVar.f1054c = C0522a.END;
            kpVar.m868b();
            if (m1202d() == 0) {
                this.f1065d.m1208a(this.f1068g);
                this.f1067f = System.currentTimeMillis();
            } else {
                this.f1067f = 0;
            }
        } else if (jg.m974a().m979c()) {
            jq.m1016a(3, f1063b, "Session cannot be ended, session not found for context:" + context);
        } else {
            jq.m1032e(f1063b, "Session cannot be ended, session not found for context:" + context);
        }
    }

    private synchronized void m1196i() {
        int d = m1202d();
        if (d > 0) {
            jq.m1016a(5, f1063b, "Session cannot be finalized, sessionContextCount:" + d);
        } else {
            ko e = m1204e();
            if (e == null) {
                jq.m1016a(5, f1063b, "Session cannot be finalized, current session not found");
            } else {
                jq.m1032e(f1063b, "Flurry session ended");
                kp kpVar = new kp();
                kpVar.f1053b = e;
                kpVar.f1054c = C0522a.FINALIZE;
                kpVar.f1055d = ip.m818a().m824d();
                kpVar.m868b();
                jc.m946a().m955b(new C05264(this, e));
            }
        }
    }

    public void m1198a(String str, Object obj) {
        if (str.equals("ContinueSessionMillis")) {
            this.f1068g = ((Long) obj).longValue();
            jq.m1016a(4, f1063b, "onSettingUpdate, ContinueSessionMillis = " + this.f1068g);
            return;
        }
        jq.m1016a(6, f1063b, "onSettingUpdate internal error!");
    }
}
