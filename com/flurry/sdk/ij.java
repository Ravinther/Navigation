package com.flurry.sdk;

import android.widget.Toast;
import com.flurry.sdk.jx.C0417a;
import com.flurry.sdk.jy.C0484a;
import com.flurry.sdk.kc.C0502a;
import com.flurry.sdk.ku.C0426a;
import loquendo.tts.engine.TTSConst;

public class ij extends kc implements C0426a {
    private static final String f723e;
    private static String f724f;
    private static String f725g;
    private String f726h;
    private boolean f727i;

    /* renamed from: com.flurry.sdk.ij.1 */
    class C04401 implements C0417a<byte[], Void> {
        final /* synthetic */ String f713a;
        final /* synthetic */ String f714b;
        final /* synthetic */ ij f715c;

        /* renamed from: com.flurry.sdk.ij.1.1 */
        class C04391 implements Runnable {
            final /* synthetic */ int f711a;
            final /* synthetic */ C04401 f712b;

            C04391(C04401 c04401, int i) {
                this.f712b = c04401;
                this.f711a = i;
            }

            public void run() {
                Toast.makeText(jc.m946a().m957c(), "SD HTTP Response Code: " + this.f711a, 0).show();
            }
        }

        C04401(ij ijVar, String str, String str2) {
            this.f715c = ijVar;
            this.f713a = str;
            this.f714b = str2;
        }

        public void m680a(jx<byte[], Void> jxVar, Void voidR) {
            int f = jxVar.m1079f();
            if (f > 0) {
                jq.m1032e(ij.f723e, "Analytics report sent.");
                jq.m1016a(3, ij.f723e, "FlurryDataSender: report " + this.f713a + " sent. HTTP response: " + f);
                if (jq.m1025c() <= 3 && jq.m1031d()) {
                    jc.m946a().m953a(new C04391(this, f));
                }
                this.f715c.m705a(this.f713a, this.f714b, f);
                this.f715c.m695e();
                return;
            }
            this.f715c.m689b(this.f713a, this.f714b);
        }
    }

    /* renamed from: com.flurry.sdk.ij.2 */
    class C04412 extends le {
        final /* synthetic */ int f716a;
        final /* synthetic */ ij f717b;

        C04412(ij ijVar, int i) {
            this.f717b = ijVar;
            this.f716a = i;
        }

        public void m681a() {
            if (this.f716a == 200) {
                hi.m500a().m521f();
            }
        }
    }

    static {
        f723e = ij.class.getSimpleName();
        f724f = "http://data.flurry.com/aap.do";
        f725g = "https://data.flurry.com/aap.do";
    }

    public ij() {
        this(null);
    }

    public ij(C0502a c0502a) {
        super("Analytics", ij.class.getSimpleName());
        this.d = "AnalyticsData_";
        m702h();
        m683a(c0502a);
    }

    private void m702h() {
        ku a = kt.m1217a();
        this.f727i = ((Boolean) a.m1212a("UseHttps")).booleanValue();
        a.m1213a("UseHttps", (C0426a) this);
        jq.m1016a(4, f723e, "initSettings, UseHttps = " + this.f727i);
        String str = (String) a.m1212a("ReportUrl");
        a.m1213a("ReportUrl", (C0426a) this);
        m700b(str);
        jq.m1016a(4, f723e, "initSettings, ReportUrl = " + str);
    }

    public void m703a() {
        kt.m1217a().m1215b("UseHttps", (C0426a) this);
        kt.m1217a().m1215b("ReportUrl", (C0426a) this);
    }

    public void m704a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -239660092:
                if (str.equals("UseHttps")) {
                    obj2 = null;
                    break;
                }
                break;
            case 1650629499:
                if (str.equals("ReportUrl")) {
                    obj2 = 1;
                    break;
                }
                break;
        }
        switch (obj2) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.f727i = ((Boolean) obj).booleanValue();
                jq.m1016a(4, f723e, "onSettingUpdate, UseHttps = " + this.f727i);
            case TTSConst.TTSMULTILINE /*1*/:
                String str2 = (String) obj;
                m700b(str2);
                jq.m1016a(4, f723e, "onSettingUpdate, ReportUrl = " + str2);
            default:
                jq.m1016a(6, f723e, "onSettingUpdate internal error!");
        }
    }

    private void m700b(String str) {
        if (!(str == null || str.endsWith(".do"))) {
            jq.m1016a(5, f723e, "overriding analytics agent report URL without an endpoint, are you sure?");
        }
        this.f726h = str;
    }

    String m707b() {
        if (this.f726h != null) {
            return this.f726h;
        }
        if (this.f727i) {
            return f725g;
        }
        return f724f;
    }

    protected void m706a(byte[] bArr, String str, String str2) {
        String b = m707b();
        jq.m1016a(4, f723e, "FlurryDataSender: start upload data " + bArr + " with id = " + str + " to " + b);
        lf jxVar = new jx();
        jxVar.m1071a(b);
        jxVar.m1055a(100000);
        jxVar.m1069a(C0484a.kPost);
        jxVar.m1072a("Content-Type", "application/octet-stream");
        jxVar.m1092a(new kg());
        jxVar.m1093a((Object) bArr);
        jxVar.m1091a(new C04401(this, str, str2));
        ja.m928a().m925a((Object) this, jxVar);
    }

    protected void m705a(String str, String str2, int i) {
        m684a(new C04412(this, i));
        super.m686a(str, str2, i);
    }
}
