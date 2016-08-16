package com.flurry.sdk;

import com.flurry.sdk.kd.C0506a;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class kc {
    protected final String f718a;
    Set<String> f719b;
    ke f720c;
    protected String f721d;
    private jl<iv> f722e;

    /* renamed from: com.flurry.sdk.kc.1 */
    class C04941 implements jl<iv> {
        final /* synthetic */ kc f999a;

        C04941(kc kcVar) {
            this.f999a = kcVar;
        }

        public void m1107a(iv ivVar) {
            jq.m1016a(4, this.f999a.f718a, "onNetworkStateChanged : isNetworkEnable = " + ivVar.f853a);
            if (ivVar.f853a) {
                this.f999a.m695e();
            }
        }
    }

    /* renamed from: com.flurry.sdk.kc.2 */
    class C04952 extends le {
        final /* synthetic */ String f1000a;
        final /* synthetic */ kc f1001b;

        C04952(kc kcVar, String str) {
            this.f1001b = kcVar;
            this.f1000a = str;
        }

        public void m1109a() {
            this.f1001b.f720c = new ke(this.f1000a);
        }
    }

    /* renamed from: com.flurry.sdk.kc.3 */
    class C04963 extends le {
        final /* synthetic */ byte[] f1002a;
        final /* synthetic */ String f1003b;
        final /* synthetic */ String f1004c;
        final /* synthetic */ kc f1005d;

        C04963(kc kcVar, byte[] bArr, String str, String str2) {
            this.f1005d = kcVar;
            this.f1002a = bArr;
            this.f1003b = str;
            this.f1004c = str2;
        }

        public void m1110a() {
            this.f1005d.m694d(this.f1002a, this.f1003b, this.f1004c);
        }
    }

    /* renamed from: com.flurry.sdk.kc.4 */
    class C04974 extends le {
        final /* synthetic */ C0502a f1006a;
        final /* synthetic */ kc f1007b;

        C04974(kc kcVar, C0502a c0502a) {
            this.f1007b = kcVar;
            this.f1006a = c0502a;
        }

        public void m1111a() {
            this.f1007b.m697g();
            if (this.f1006a != null) {
                this.f1006a.m1116a();
            }
        }
    }

    /* renamed from: com.flurry.sdk.kc.5 */
    class C04985 implements kn<kd> {
        final /* synthetic */ kc f1008a;

        C04985(kc kcVar) {
            this.f1008a = kcVar;
        }

        public kk<kd> m1112a(int i) {
            return new C0506a();
        }
    }

    /* renamed from: com.flurry.sdk.kc.6 */
    class C04996 implements kn<kd> {
        final /* synthetic */ kc f1009a;

        C04996(kc kcVar) {
            this.f1009a = kcVar;
        }

        public kk<kd> m1113a(int i) {
            return new C0506a();
        }
    }

    /* renamed from: com.flurry.sdk.kc.7 */
    class C05007 extends le {
        final /* synthetic */ String f1010a;
        final /* synthetic */ String f1011b;
        final /* synthetic */ kc f1012c;

        C05007(kc kcVar, String str, String str2) {
            this.f1012c = kcVar;
            this.f1010a = str;
            this.f1011b = str2;
        }

        public void m1114a() {
            if (!this.f1012c.f720c.m1148a(this.f1010a, this.f1011b)) {
                jq.m1016a(6, this.f1012c.f718a, "Internal error. Block wasn't deleted with id = " + this.f1010a);
            }
            if (!this.f1012c.f719b.remove(this.f1010a)) {
                jq.m1016a(6, this.f1012c.f718a, "Internal error. Block with id = " + this.f1010a + " was not in progress state");
            }
        }
    }

    /* renamed from: com.flurry.sdk.kc.8 */
    class C05018 extends le {
        final /* synthetic */ String f1013a;
        final /* synthetic */ kc f1014b;

        C05018(kc kcVar, String str) {
            this.f1014b = kcVar;
            this.f1013a = str;
        }

        public void m1115a() {
            if (!this.f1014b.f719b.remove(this.f1013a)) {
                jq.m1016a(6, this.f1014b.f718a, "Internal error. Block with id = " + this.f1013a + " was not in progress state");
            }
        }
    }

    /* renamed from: com.flurry.sdk.kc.a */
    public interface C0502a {
        void m1116a();
    }

    protected abstract void m687a(byte[] bArr, String str, String str2);

    public kc(String str, String str2) {
        this.f719b = new HashSet();
        this.f721d = "defaultDataKey_";
        this.f722e = new C04941(this);
        this.f718a = str2;
        jm.m997a().m1002a("com.flurry.android.sdk.NetworkStateEvent", this.f722e);
        m685a(str);
    }

    protected void m684a(le leVar) {
        jc.m946a().m955b(leVar);
    }

    protected void m685a(String str) {
        m684a(new C04952(this, str));
    }

    public void m690b(byte[] bArr, String str, String str2) {
        m688a(bArr, str, str2, null);
    }

    public void m688a(byte[] bArr, String str, String str2, C0502a c0502a) {
        if (bArr == null || bArr.length == 0) {
            jq.m1016a(6, this.f718a, "Report that has to be sent is EMPTY or NULL");
            return;
        }
        m692c(bArr, str, str2);
        m683a(c0502a);
    }

    public int m693d() {
        return this.f719b.size();
    }

    protected void m692c(byte[] bArr, String str, String str2) {
        m684a(new C04963(this, bArr, str, str2));
    }

    protected void m695e() {
        m683a(null);
    }

    protected void m683a(C0502a c0502a) {
        m684a(new C04974(this, c0502a));
    }

    protected boolean m696f() {
        return m693d() <= 5;
    }

    public String m682a(String str, String str2) {
        return this.f721d + str + "_" + str2;
    }

    protected void m694d(byte[] bArr, String str, String str2) {
        String a = m682a(str, str2);
        kd kdVar = new kd(bArr);
        String a2 = kdVar.m1124a();
        new jj(jc.m946a().m957c().getFileStreamPath(kd.m1121a(a2)), ".yflurrydatasenderblock.", 1, new C04985(this)).m994a(kdVar);
        jq.m1016a(5, this.f718a, "Saving Block File " + a2 + " at " + jc.m946a().m957c().getFileStreamPath(kd.m1121a(a2)));
        this.f720c.m1146a(kdVar, a);
    }

    protected void m697g() {
        if (iw.m897a().m903c()) {
            List<String> a = this.f720c.m1145a();
            if (a == null || a.isEmpty()) {
                jq.m1016a(4, this.f718a, "No more reports to send.");
                return;
            }
            for (String str : a) {
                if (m696f()) {
                    List c = this.f720c.m1151c(str);
                    jq.m1016a(4, this.f718a, "Number of not sent blocks = " + c.size());
                    for (int i = 0; i < c.size(); i++) {
                        String str2 = (String) c.get(i);
                        if (!this.f719b.contains(str2)) {
                            if (!m696f()) {
                                break;
                            }
                            kd kdVar = (kd) new jj(jc.m946a().m957c().getFileStreamPath(kd.m1121a(str2)), ".yflurrydatasenderblock.", 1, new C04996(this)).m993a();
                            if (kdVar == null) {
                                jq.m1016a(6, this.f718a, "Internal ERROR! Cannot read!");
                                this.f720c.m1148a(str2, str);
                            } else {
                                byte[] b = kdVar.m1125b();
                                if (b == null || b.length == 0) {
                                    jq.m1016a(6, this.f718a, "Internal ERROR! Report is empty!");
                                    this.f720c.m1148a(str2, str);
                                } else {
                                    jq.m1016a(5, this.f718a, "Reading block info " + str2);
                                    this.f719b.add(str2);
                                    m687a(b, str2, str);
                                }
                            }
                        }
                    }
                } else {
                    return;
                }
            }
            return;
        }
        jq.m1016a(5, this.f718a, "Reports were not sent! No Internet connection!");
    }

    protected void m686a(String str, String str2, int i) {
        m684a(new C05007(this, str, str2));
    }

    protected void m689b(String str, String str2) {
        m684a(new C05018(this, str));
    }

    protected void m691c(String str, String str2) {
        if (!this.f720c.m1148a(str, str2)) {
            jq.m1016a(6, this.f718a, "Internal error. Block wasn't deleted with id = " + str);
        }
        if (!this.f719b.remove(str)) {
            jq.m1016a(6, this.f718a, "Internal error. Block with id = " + str + " was not in progress state");
        }
    }
}
