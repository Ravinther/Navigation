package com.flurry.sdk;

import android.text.TextUtils;
import android.widget.Toast;
import com.flurry.sdk.id.C0431a;
import com.flurry.sdk.id.C0433b;
import com.flurry.sdk.jx.C0417a;
import com.flurry.sdk.jy.C0484a;
import java.util.List;

public class ie extends kb<id> {
    private static final String f694a;

    /* renamed from: com.flurry.sdk.ie.1 */
    class C04341 implements kn<List<id>> {
        final /* synthetic */ ie f679a;

        C04341(ie ieVar) {
            this.f679a = ieVar;
        }

        public kk<List<id>> m636a(int i) {
            if (i > 1) {
                return new kj(new C0431a());
            }
            return new kj(new C0433b());
        }
    }

    /* renamed from: com.flurry.sdk.ie.2 */
    class C04372 implements C0417a<Void, Void> {
        final /* synthetic */ id f684a;
        final /* synthetic */ ie f685b;

        /* renamed from: com.flurry.sdk.ie.2.1 */
        class C04351 implements Runnable {
            final /* synthetic */ jx f680a;
            final /* synthetic */ C04372 f681b;

            C04351(C04372 c04372, jx jxVar) {
                this.f681b = c04372;
                this.f680a = jxVar;
            }

            public void run() {
                Toast.makeText(jc.m946a().m957c(), "ANL AR HTTP Response Code: " + this.f680a.m1079f() + " for url: " + this.f680a.m1074b(), 1).show();
            }
        }

        /* renamed from: com.flurry.sdk.ie.2.2 */
        class C04362 implements Runnable {
            final /* synthetic */ jx f682a;
            final /* synthetic */ C04372 f683b;

            C04362(C04372 c04372, jx jxVar) {
                this.f683b = c04372;
                this.f682a = jxVar;
            }

            public void run() {
                Toast.makeText(jc.m946a().m957c(), "ANL AR HTTP Response Code: " + this.f682a.m1079f() + " for url: " + this.f682a.m1074b(), 1).show();
            }
        }

        C04372(ie ieVar, id idVar) {
            this.f685b = ieVar;
            this.f684a = idVar;
        }

        public void m638a(jx<Void, Void> jxVar, Void voidR) {
            jq.m1016a(3, ie.f694a, "AsyncReportInfo request: HTTP status code is:" + jxVar.m1079f());
            int f = jxVar.m1079f();
            if (f >= 200 && f < 300) {
                jq.m1016a(3, ie.f694a, "Send report successful to url: " + jxVar.m1074b());
                this.f685b.m654c(this.f684a);
                if (jq.m1025c() <= 3 && jq.m1031d()) {
                    jc.m946a().m953a(new C04351(this, jxVar));
                }
            } else if (f < 300 || f >= 400) {
                jq.m1016a(3, ie.f694a, "Send report failed to url: " + jxVar.m1074b());
                if (lg.m1299h(this.f684a.m633j())) {
                    this.f685b.m656d(this.f684a);
                    return;
                }
                jq.m1016a(3, ie.f694a, "Oops! url: " + jxVar.m1074b() + " is invalid, aborting transmission");
                this.f685b.m654c(this.f684a);
            } else {
                String str = null;
                List b = jxVar.m1075b("Location");
                if (b != null && b.size() > 0) {
                    str = lg.m1293b((String) b.get(0), this.f684a.m633j());
                }
                if (TextUtils.isEmpty(str)) {
                    jq.m1016a(3, ie.f694a, "Send report successful to url: " + jxVar.m1074b());
                    this.f685b.m654c(this.f684a);
                    if (jq.m1025c() <= 3 && jq.m1031d()) {
                        jc.m946a().m953a(new C04362(this, jxVar));
                        return;
                    }
                    return;
                }
                jq.m1016a(3, ie.f694a, "Send report redirecting to url: " + str);
                this.f684a.m627c(str);
                this.f685b.m664a(this.f684a);
            }
        }
    }

    static {
        f694a = ie.class.getSimpleName();
    }

    protected jj<List<id>> m663a() {
        return new jj(jc.m946a().m957c().getFileStreamPath(".yflurryanreporter"), ".yflurryanreporter", 2, new C04341(this));
    }

    protected void m664a(id idVar) {
        jq.m1016a(3, f694a, "Sending next report for original url: " + idVar.m632i() + " to current url:" + idVar.m633j());
        lf jxVar = new jx();
        jxVar.m1071a(idVar.m633j());
        jxVar.m1055a(100000);
        jxVar.m1069a(C0484a.kGet);
        jxVar.m1073a(false);
        jxVar.m1091a(new C04372(this, idVar));
        ja.m928a().m925a((Object) this, jxVar);
    }
}
