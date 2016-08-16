package com.flurry.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class kb<ReportInfo extends ka> {
    private static final String f686a;
    private final jj<List<ReportInfo>> f687b;
    private final List<ReportInfo> f688c;
    private boolean f689d;
    private int f690e;
    private long f691f;
    private final Runnable f692g;
    private final jl<iv> f693h;

    /* renamed from: com.flurry.sdk.kb.1 */
    class C04861 extends le {
        final /* synthetic */ kb f991a;

        C04861(kb kbVar) {
            this.f991a = kbVar;
        }

        public void m1098a() {
            this.f991a.m643b();
        }
    }

    /* renamed from: com.flurry.sdk.kb.2 */
    class C04872 implements jl<iv> {
        final /* synthetic */ kb f992a;

        C04872(kb kbVar) {
            this.f992a = kbVar;
        }

        public void m1099a(iv ivVar) {
            if (ivVar.f853a) {
                this.f992a.m643b();
            }
        }
    }

    /* renamed from: com.flurry.sdk.kb.3 */
    class C04883 extends le {
        final /* synthetic */ kb f993a;

        C04883(kb kbVar) {
            this.f993a = kbVar;
        }

        public void m1101a() {
            this.f993a.m641a(this.f993a.f688c);
            this.f993a.m643b();
        }
    }

    /* renamed from: com.flurry.sdk.kb.4 */
    class C04894 extends le {
        final /* synthetic */ kb f994a;

        C04894(kb kbVar) {
            this.f994a = kbVar;
        }

        public void m1102a() {
            this.f994a.m643b();
        }
    }

    /* renamed from: com.flurry.sdk.kb.5 */
    class C04905 extends le {
        final /* synthetic */ kb f995a;

        C04905(kb kbVar) {
            this.f995a = kbVar;
        }

        public void m1103a() {
            this.f995a.m643b();
        }
    }

    /* renamed from: com.flurry.sdk.kb.6 */
    class C04916 extends le {
        final /* synthetic */ kb f996a;

        C04916(kb kbVar) {
            this.f996a = kbVar;
        }

        public void m1104a() {
            this.f996a.m646f();
        }
    }

    /* renamed from: com.flurry.sdk.kb.7 */
    class C04927 extends le {
        final /* synthetic */ kb f997a;

        C04927(kb kbVar) {
            this.f997a = kbVar;
        }

        public void m1105a() {
            this.f997a.m646f();
        }
    }

    /* renamed from: com.flurry.sdk.kb.8 */
    class C04938 extends le {
        final /* synthetic */ kb f998a;

        C04938(kb kbVar) {
            this.f998a = kbVar;
        }

        public void m1106a() {
            this.f998a.m646f();
        }
    }

    protected abstract jj<List<ReportInfo>> m650a();

    protected abstract void m651a(ReportInfo reportInfo);

    static {
        f686a = kb.class.getSimpleName();
    }

    public kb() {
        this.f688c = new ArrayList();
        this.f692g = new C04861(this);
        this.f693h = new C04872(this);
        jm.m997a().m1002a("com.flurry.android.sdk.NetworkStateEvent", this.f693h);
        this.f687b = m650a();
        this.f691f = 10000;
        this.f690e = -1;
        jc.m946a().m955b(new C04883(this));
    }

    public void m653c() {
        jc.m946a().m958c(this.f692g);
        m649i();
    }

    public void m655d() {
        this.f689d = true;
    }

    public void m657e() {
        this.f689d = false;
        jc.m946a().m955b(new C04894(this));
    }

    public synchronized void m652b(ReportInfo reportInfo) {
        if (reportInfo != null) {
            this.f688c.add(reportInfo);
            jc.m946a().m955b(new C04905(this));
        }
    }

    protected synchronized void m654c(ReportInfo reportInfo) {
        reportInfo.m625a(true);
        jc.m946a().m955b(new C04916(this));
    }

    protected synchronized void m656d(ReportInfo reportInfo) {
        reportInfo.m634k();
        jc.m946a().m955b(new C04927(this));
    }

    private synchronized void m643b() {
        if (!this.f689d) {
            if (this.f690e >= 0) {
                jq.m1016a(3, f686a, "Transmit is in progress");
            } else {
                m648h();
                if (this.f688c.isEmpty()) {
                    this.f691f = 10000;
                    this.f690e = -1;
                } else {
                    this.f690e = 0;
                    jc.m946a().m955b(new C04938(this));
                }
            }
        }
    }

    private synchronized void m646f() {
        ka kaVar;
        lc.m1272b();
        if (iw.m897a().m903c()) {
            while (this.f690e < this.f688c.size()) {
                List list = this.f688c;
                int i = this.f690e;
                this.f690e = i + 1;
                kaVar = (ka) list.get(i);
                if (!kaVar.m630g()) {
                    break;
                }
            }
            kaVar = null;
        } else {
            jq.m1016a(3, f686a, "Network is not available, aborting transmission");
            kaVar = null;
        }
        if (kaVar == null) {
            m647g();
        } else {
            m651a(kaVar);
        }
    }

    private synchronized void m647g() {
        m648h();
        m644b(this.f688c);
        if (this.f689d) {
            jq.m1016a(3, f686a, "Reporter paused");
            this.f691f = 10000;
        } else if (this.f688c.isEmpty()) {
            jq.m1016a(3, f686a, "All reports sent successfully");
            this.f691f = 10000;
        } else {
            this.f691f <<= 1;
            jq.m1016a(3, f686a, "One or more reports failed to send, backing off: " + this.f691f + "ms");
            jc.m946a().m956b(this.f692g, this.f691f);
        }
        this.f690e = -1;
    }

    private synchronized void m641a(List<ReportInfo> list) {
        lc.m1272b();
        List list2 = (List) this.f687b.m993a();
        if (list2 != null) {
            list.addAll(list2);
        }
    }

    private synchronized void m644b(List<ReportInfo> list) {
        lc.m1272b();
        this.f687b.m994a(new ArrayList(list));
    }

    private synchronized void m648h() {
        Iterator it = this.f688c.iterator();
        while (it.hasNext()) {
            ka kaVar = (ka) it.next();
            if (kaVar.m630g()) {
                jq.m1016a(3, f686a, "Expired: Url transmitted - " + kaVar.m632i() + " Attempts: " + kaVar.m631h());
                it.remove();
            } else if (kaVar.m631h() > kaVar.m628d()) {
                jq.m1016a(3, f686a, "Expired: Exceeded max no of attempts - " + kaVar.m632i() + " Attempts: " + kaVar.m631h());
                it.remove();
            } else if (System.currentTimeMillis() > kaVar.m629f() && kaVar.m631h() > 0) {
                jq.m1016a(3, f686a, "Expired: Time expired - " + kaVar.m632i() + " Attempts: " + kaVar.m631h());
                it.remove();
            }
        }
    }

    private void m649i() {
        jm.m997a().m1004b("com.flurry.android.sdk.NetworkStateEvent", this.f693h);
    }
}
