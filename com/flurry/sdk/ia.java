package com.flurry.sdk;

import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.hy.C0415a;
import com.flurry.sdk.id.C0431a;
import com.flurry.sdk.jx.C0417a;
import com.flurry.sdk.jy.C0484a;
import com.flurry.sdk.kp.C0522a;
import com.flurry.sdk.ku.C0426a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import loquendo.tts.engine.TTSConst;

public class ia implements C0426a {
    private static final String f645a;
    private static String f646b;
    private final Runnable f647c;
    private final jl<ir> f648d;
    private final jl<kp> f649e;
    private final jl<is> f650f;
    private final jl<iv> f651g;
    private final ju<hp> f652h;
    private final ju<hq> f653i;
    private jj<hy> f654j;
    private jj<List<id>> f655k;
    private final hz f656l;
    private final jh<String, hn> f657m;
    private final List<id> f658n;
    private boolean f659o;
    private String f660p;
    private boolean f661q;
    private long f662r;
    private long f663s;
    private boolean f664t;
    private hq f665u;

    /* renamed from: com.flurry.sdk.ia.1 */
    class C04161 extends le {
        final /* synthetic */ ia f634a;

        C04161(ia iaVar) {
            this.f634a = iaVar;
        }

        public void m557a() {
            this.f634a.m587f();
        }
    }

    /* renamed from: com.flurry.sdk.ia.2 */
    class C04182 implements C0417a<byte[], byte[]> {
        final /* synthetic */ long f635a;
        final /* synthetic */ boolean f636b;
        final /* synthetic */ ia f637c;

        C04182(ia iaVar, long j, boolean z) {
            this.f637c = iaVar;
            this.f635a = j;
            this.f636b = z;
        }

        public void m560a(jx<byte[], byte[]> jxVar, byte[] bArr) {
            int f = jxVar.m1079f();
            jq.m1016a(3, ia.f645a, "Proton config request: HTTP status code is:" + f);
            if (f == 400 || f == 406 || f == 412 || f == 415) {
                this.f637c.f662r = 10000;
                return;
            }
            hq hqVar;
            if (!jxVar.m1077d() || bArr == null) {
                hqVar = null;
            } else {
                this.f637c.m574a(this.f635a, this.f636b, bArr);
                try {
                    hqVar = (hq) this.f637c.f653i.m1044d(bArr);
                } catch (Exception e) {
                    jq.m1016a(5, ia.f645a, "Failed to decode proton config response: " + e);
                    hqVar = null;
                }
                if (hqVar != null) {
                    this.f637c.f662r = 10000;
                    this.f637c.f663s = this.f635a;
                    this.f637c.f664t = this.f636b;
                    this.f637c.f665u = hqVar;
                    this.f637c.m589g();
                }
            }
            if (hqVar == null) {
                long parseLong;
                long f2 = this.f637c.f662r << 1;
                if (f == 429) {
                    List b = jxVar.m1075b("Retry-After");
                    if (!b.isEmpty()) {
                        String str = (String) b.get(0);
                        jq.m1016a(3, ia.f645a, "Server returned retry time: " + str);
                        try {
                            parseLong = Long.parseLong(str) * 1000;
                        } catch (NumberFormatException e2) {
                            jq.m1016a(3, ia.f645a, "Server returned nonsensical retry time");
                        }
                        this.f637c.f662r = parseLong;
                        jq.m1016a(3, ia.f645a, "Proton config request failed, backing off: " + this.f637c.f662r + "ms");
                        jc.m946a().m956b(this.f637c.f647c, this.f637c.f662r);
                    }
                }
                parseLong = f2;
                this.f637c.f662r = parseLong;
                jq.m1016a(3, ia.f645a, "Proton config request failed, backing off: " + this.f637c.f662r + "ms");
                jc.m946a().m956b(this.f637c.f647c, this.f637c.f662r);
            }
        }
    }

    /* renamed from: com.flurry.sdk.ia.3 */
    class C04193 extends le {
        final /* synthetic */ ia f638a;

        C04193(ia iaVar) {
            this.f638a = iaVar;
        }

        public void m561a() {
            this.f638a.m587f();
        }
    }

    /* renamed from: com.flurry.sdk.ia.4 */
    static /* synthetic */ class C04204 {
        static final /* synthetic */ int[] f639a;

        static {
            f639a = new int[C0522a.values().length];
            try {
                f639a[C0522a.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f639a[C0522a.START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f639a[C0522a.END.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f639a[C0522a.FINALIZE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.ia.5 */
    class C04215 implements jl<ir> {
        final /* synthetic */ ia f640a;

        C04215(ia iaVar) {
            this.f640a = iaVar;
        }

        public void m562a(ir irVar) {
            this.f640a.m587f();
        }
    }

    /* renamed from: com.flurry.sdk.ia.6 */
    class C04226 implements jl<kp> {
        final /* synthetic */ ia f641a;

        C04226(ia iaVar) {
            this.f641a = iaVar;
        }

        public void m565a(kp kpVar) {
            switch (C04204.f639a[kpVar.f1054c.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    this.f641a.m600b();
                case TTSConst.TTSPARAGRAPH /*2*/:
                    this.f641a.m601c();
                case TTSConst.TTSUNICODE /*3*/:
                    this.f641a.m598a(kpVar.f1055d);
                case TTSConst.TTSXML /*4*/:
                    this.f641a.m602d();
                default:
            }
        }
    }

    /* renamed from: com.flurry.sdk.ia.7 */
    class C04237 implements jl<is> {
        final /* synthetic */ ia f642a;

        C04237(ia iaVar) {
            this.f642a = iaVar;
        }

        public void m566a(is isVar) {
            this.f642a.m587f();
        }
    }

    /* renamed from: com.flurry.sdk.ia.8 */
    class C04248 implements jl<iv> {
        final /* synthetic */ ia f643a;

        C04248(ia iaVar) {
            this.f643a = iaVar;
        }

        public void m568a(iv ivVar) {
            if (ivVar.f853a) {
                this.f643a.m587f();
            }
        }
    }

    /* renamed from: com.flurry.sdk.ia.9 */
    class C04259 implements kn<hy> {
        final /* synthetic */ ia f644a;

        C04259(ia iaVar) {
            this.f644a = iaVar;
        }

        public kk<hy> m570a(int i) {
            return new C0415a();
        }
    }

    static {
        f645a = ia.class.getSimpleName();
        f646b = "https://proton.flurry.com:443/sdk/v1/config";
    }

    public ia() {
        this.f647c = new C04161(this);
        this.f648d = new C04215(this);
        this.f649e = new C04226(this);
        this.f650f = new C04237(this);
        this.f651g = new C04248(this);
        this.f652h = new ju("proton config request", new ib());
        this.f653i = new ju("proton config response", new ic());
        this.f656l = new hz();
        this.f657m = new jh();
        this.f658n = new ArrayList();
        this.f662r = 10000;
        ku a = kt.m1217a();
        this.f659o = ((Boolean) a.m1212a("ProtonEnabled")).booleanValue();
        a.m1213a("ProtonEnabled", (C0426a) this);
        jq.m1016a(4, f645a, "initSettings, protonEnabled = " + this.f659o);
        this.f660p = (String) a.m1212a("ProtonConfigUrl");
        a.m1213a("ProtonConfigUrl", (C0426a) this);
        jq.m1016a(4, f645a, "initSettings, protonConfigUrl = " + this.f660p);
        jm.m997a().m1002a("com.flurry.android.sdk.IdProviderFinishedEvent", this.f648d);
        jm.m997a().m1002a("com.flurry.android.sdk.IdProviderUpdatedAdvertisingId", this.f650f);
        jm.m997a().m1002a("com.flurry.android.sdk.NetworkStateEvent", this.f651g);
        this.f654j = new jj(jc.m946a().m957c().getFileStreamPath(m595m()), ".yflurryprotonconfig.", 1, new C04259(this));
        this.f655k = new jj(jc.m946a().m957c().getFileStreamPath(m596n()), ".yflurryprotonreport.", 1, new kn<List<id>>() {
            final /* synthetic */ ia f631a;

            {
                this.f631a = r1;
            }

            public kk<List<id>> m554a(int i) {
                return new kj(new C0431a());
            }
        });
        jc.m946a().m955b(new le() {
            final /* synthetic */ ia f632a;

            {
                this.f632a = r1;
            }

            public void m555a() {
                this.f632a.m590h();
            }
        });
        jc.m946a().m955b(new le() {
            final /* synthetic */ ia f633a;

            {
                this.f633a = r1;
            }

            public void m556a() {
                this.f633a.m592j();
            }
        });
    }

    public void m597a() {
        jc.m946a().m958c(this.f647c);
        jm.m997a().m1004b("com.flurry.android.sdk.NetworkStateEvent", this.f651g);
        jm.m997a().m1004b("com.flurry.android.sdk.IdProviderUpdatedAdvertisingId", this.f650f);
        jm.m997a().m1004b("com.flurry.android.sdk.IdProviderFinishedEvent", this.f648d);
        kt.m1217a().m1215b("ProtonEnabled", (C0426a) this);
    }

    public void m599a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case 640941243:
                if (str.equals("ProtonEnabled")) {
                    obj2 = null;
                    break;
                }
                break;
            case 1591403975:
                if (str.equals("ProtonConfigUrl")) {
                    obj2 = 1;
                    break;
                }
                break;
        }
        switch (obj2) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.f659o = ((Boolean) obj).booleanValue();
                jq.m1016a(4, f645a, "onSettingUpdate, protonEnabled = " + this.f659o);
            case TTSConst.TTSMULTILINE /*1*/:
                this.f660p = (String) obj;
                jq.m1016a(4, f645a, "onSettingUpdate, protonConfigUrl = " + this.f660p);
            default:
                jq.m1016a(6, f645a, "onSettingUpdate internal error!");
        }
    }

    public synchronized void m600b() {
        if (this.f659o) {
            lc.m1272b();
            m587f();
        }
    }

    public synchronized void m601c() {
        if (this.f659o) {
            lc.m1272b();
            m580b(ip.m818a().m824d());
            m591i();
        }
    }

    public synchronized void m598a(long j) {
        if (this.f659o) {
            lc.m1272b();
            m580b(j);
            m577a("flurry.session_end");
            m593k();
        }
    }

    public synchronized void m602d() {
        if (this.f659o) {
            lc.m1272b();
            m591i();
        }
    }

    private synchronized void m587f() {
        if (this.f659o) {
            if (this.f661q && iq.m834a().m861c()) {
                boolean z;
                long currentTimeMillis = System.currentTimeMillis();
                if (iq.m834a().m863e()) {
                    z = false;
                } else {
                    z = true;
                }
                if (this.f665u != null) {
                    if (this.f664t != z) {
                        jq.m1016a(3, f645a, "Limit ad tracking value has changed, purging");
                        this.f665u = null;
                    } else if (System.currentTimeMillis() < this.f663s + (this.f665u.f598b * 1000)) {
                        jq.m1016a(3, f645a, "Cached Proton config valid, no need to refresh");
                    } else if (System.currentTimeMillis() >= this.f663s + (this.f665u.f599c * 1000)) {
                        jq.m1016a(3, f645a, "Cached Proton config expired, purging");
                        this.f665u = null;
                    }
                }
                ja.m928a().m924a((Object) this);
                jq.m1016a(3, f645a, "Requesting proton config");
                try {
                    Object hpVar = new hp();
                    hpVar.f587a = jc.m946a().m959d();
                    hpVar.f588b = kz.m1236c(jc.m946a().m957c());
                    hpVar.f589c = kz.m1237d(jc.m946a().m957c());
                    hpVar.f590d = jd.m963a();
                    hpVar.f591e = 3;
                    hpVar.f592f = iz.m909a().m913c();
                    hpVar.f593g = z;
                    hpVar.f594h = new hs();
                    hpVar.f594h.f604a = new hm();
                    hpVar.f594h.f604a.f571a = Build.MODEL;
                    hpVar.f594h.f604a.f572b = Build.BRAND;
                    hpVar.f594h.f604a.f573c = Build.ID;
                    hpVar.f594h.f604a.f574d = Build.DEVICE;
                    hpVar.f594h.f604a.f575e = Build.PRODUCT;
                    hpVar.f594h.f604a.f576f = VERSION.RELEASE;
                    hpVar.f595i = new ArrayList();
                    for (Entry entry : iq.m834a().m866h().entrySet()) {
                        hr hrVar = new hr();
                        hrVar.f602a = ((iy) entry.getKey()).f871d;
                        if (((iy) entry.getKey()).f872e) {
                            hrVar.f603b = new String((byte[]) entry.getValue());
                        } else {
                            hrVar.f603b = lc.m1271b((byte[]) entry.getValue());
                        }
                        hpVar.f595i.add(hrVar);
                    }
                    Location e = iu.m877a().m895e();
                    if (e != null) {
                        hpVar.f596j = new hu();
                        hpVar.f596j.f608a = new ht();
                        hpVar.f596j.f608a.f605a = e.getLatitude();
                        hpVar.f596j.f608a.f606b = e.getLongitude();
                        hpVar.f596j.f608a.f607c = e.getAccuracy();
                    }
                    Object a = this.f652h.m1043a(hpVar);
                    lf jxVar = new jx();
                    jxVar.m1071a(TextUtils.isEmpty(this.f660p) ? f646b : this.f660p);
                    jxVar.m1055a(5000);
                    jxVar.m1069a(C0484a.kPost);
                    jxVar.m1072a("Content-Type", "application/x-flurry;version=1");
                    jxVar.m1072a("Accept", "application/x-flurry;version=1");
                    jxVar.m1072a("FM-Checksum", Integer.toString(ju.m1042c(a)));
                    jxVar.m1092a(new kg());
                    jxVar.m1094b(new kg());
                    jxVar.m1093a(a);
                    jxVar.m1091a(new C04182(this, currentTimeMillis, z));
                    ja.m928a().m925a((Object) this, jxVar);
                } catch (Exception e2) {
                    jq.m1016a(5, f645a, "Proton config request failed with exception: " + e2);
                }
            }
        }
    }

    private void m589g() {
        if (this.f665u != null) {
            this.f657m.m982a();
            ho hoVar = this.f665u.f601e;
            if (hoVar != null) {
                List<Object> list = hoVar.f586b;
                if (list != null) {
                    for (Object obj : list) {
                        List<Object> list2 = obj.f578b;
                        if (list2 != null) {
                            for (Object obj2 : list2) {
                                if (!TextUtils.isEmpty(obj2)) {
                                    this.f657m.m984a(obj2, obj);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private synchronized void m577a(String str) {
        if (!TextUtils.isEmpty(str)) {
            jq.m1016a(3, f645a, "Firing Proton callbacks for event: " + str);
            for (hn hnVar : this.f657m.m981a((Object) "flurry.session_end")) {
                if (hx.GET.equals(hnVar.f579c)) {
                    ka idVar = new id(ip.m818a().m824d(), str, this.f656l.m552a(hnVar.f580d), System.currentTimeMillis() + 259200000);
                    if ("flurry.session_end".equals(str)) {
                        this.f658n.add(idVar);
                    } else {
                        hi.m500a().m520e().m652b(idVar);
                    }
                }
            }
        }
    }

    private synchronized void m580b(long j) {
        Iterator it = this.f658n.iterator();
        while (it.hasNext()) {
            if (j == ((id) it.next()).m635a()) {
                it.remove();
            }
        }
    }

    private synchronized void m590h() {
        hy hyVar = (hy) this.f654j.m993a();
        if (hyVar != null) {
            hq hqVar;
            try {
                hqVar = (hq) this.f653i.m1044d(hyVar.m548c());
            } catch (Exception e) {
                jq.m1016a(5, f645a, "Failed to decode saved proton config response: " + e);
                this.f654j.m995b();
                hqVar = null;
            }
            if (hqVar != null) {
                jq.m1016a(4, f645a, "Loaded saved proton config response");
                this.f662r = 10000;
                this.f663s = hyVar.m543a();
                this.f664t = hyVar.m547b();
                this.f665u = hqVar;
                m589g();
            }
        }
        this.f661q = true;
        jc.m946a().m955b(new C04193(this));
    }

    private synchronized void m574a(long j, boolean z, byte[] bArr) {
        if (bArr != null) {
            jq.m1016a(4, f645a, "Saving proton config response");
            hy hyVar = new hy();
            hyVar.m544a(j);
            hyVar.m545a(z);
            hyVar.m546a(bArr);
            this.f654j.m994a(hyVar);
        }
    }

    private synchronized void m591i() {
        jq.m1016a(4, f645a, "Sending " + this.f658n.size() + " queued reports.");
        for (id b : this.f658n) {
            hi.m500a().m520e().m652b((ka) b);
        }
        m594l();
    }

    private synchronized void m592j() {
        jq.m1016a(4, f645a, "Loading queued report data.");
        List list = (List) this.f655k.m993a();
        if (list != null) {
            this.f658n.addAll(list);
        }
    }

    private synchronized void m593k() {
        jq.m1016a(4, f645a, "Saving queued report data.");
        this.f655k.m994a(this.f658n);
    }

    private synchronized void m594l() {
        this.f658n.clear();
        this.f655k.m995b();
    }

    private String m595m() {
        return ".yflurryprotonconfig." + Long.toString(lc.m1279i(jc.m946a().m959d()), 16);
    }

    private String m596n() {
        return ".yflurryprotonreport." + Long.toString(lc.m1279i(jc.m946a().m959d()), 16);
    }
}
