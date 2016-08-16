package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.text.TextUtils;
import com.flurry.android.FlurryEventRecordStatus;
import com.flurry.sdk.ik.C0445a;
import com.flurry.sdk.kp.C0522a;
import com.flurry.sdk.ku.C0426a;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import loquendo.tts.engine.TTSConst;

public class im implements C0426a {
    static int f774a;
    static int f775b;
    static int f776c;
    static int f777d;
    static int f778e;
    private static final String f779f;
    private final List<C0438if> f780A;
    private int f781B;
    private int f782C;
    private final hj f783D;
    private final jl<ir> f784E;
    private final AtomicInteger f785g;
    private final AtomicInteger f786h;
    private final AtomicInteger f787i;
    private final jl<kp> f788j;
    private WeakReference<ko> f789k;
    private File f790l;
    private jj<List<ik>> f791m;
    private boolean f792n;
    private long f793o;
    private boolean f794p;
    private String f795q;
    private byte f796r;
    private Long f797s;
    private final List<ik> f798t;
    private final Map<String, List<String>> f799u;
    private final Map<String, String> f800v;
    private final Map<String, ig> f801w;
    private final List<ih> f802x;
    private boolean f803y;
    private int f804z;

    /* renamed from: com.flurry.sdk.im.1 */
    class C04461 implements jl<kp> {
        final /* synthetic */ im f757a;

        C04461(im imVar) {
            this.f757a = imVar;
        }

        public void m758a(kp kpVar) {
            if (this.f757a.f789k == null || kpVar.f1053b == this.f757a.f789k.get()) {
                switch (C04537.f770a[kpVar.f1054c.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        this.f757a.m794a(kpVar.f1053b, (Context) kpVar.f1052a.get());
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        this.f757a.m793a((Context) kpVar.f1052a.get());
                    case TTSConst.TTSUNICODE /*3*/:
                        this.f757a.m799b((Context) kpVar.f1052a.get());
                    case TTSConst.TTSXML /*4*/:
                        jm.m997a().m1004b("com.flurry.android.sdk.FlurrySessionEvent", this.f757a.f788j);
                        this.f757a.m792a(kpVar.f1055d);
                    default:
                }
            }
        }
    }

    /* renamed from: com.flurry.sdk.im.2 */
    class C04472 extends le {
        final /* synthetic */ long f758a;
        final /* synthetic */ im f759b;

        C04472(im imVar, long j) {
            this.f759b = imVar;
            this.f758a = j;
        }

        public void m759a() {
            hi.m500a().m516c().m598a(this.f758a);
        }
    }

    /* renamed from: com.flurry.sdk.im.3 */
    class C04483 extends le {
        final /* synthetic */ im f760a;

        C04483(im imVar) {
            this.f760a = imVar;
        }

        public void m760a() {
            this.f760a.m785n();
        }
    }

    /* renamed from: com.flurry.sdk.im.4 */
    class C04494 extends le {
        final /* synthetic */ long f761a;
        final /* synthetic */ long f762b;
        final /* synthetic */ long f763c;
        final /* synthetic */ int f764d;
        final /* synthetic */ im f765e;

        C04494(im imVar, long j, long j2, long j3, int i) {
            this.f765e = imVar;
            this.f761a = j;
            this.f762b = j2;
            this.f763c = j3;
            this.f764d = i;
        }

        public void m761a() {
            ik a = this.f765e.m790a(this.f761a, this.f762b, this.f763c, this.f764d);
            this.f765e.f798t.clear();
            this.f765e.f798t.add(a);
            this.f765e.m800c();
        }
    }

    /* renamed from: com.flurry.sdk.im.5 */
    class C04515 extends le {
        final /* synthetic */ im f767a;

        /* renamed from: com.flurry.sdk.im.5.1 */
        class C04501 extends le {
            final /* synthetic */ C04515 f766a;

            C04501(C04515 c04515) {
                this.f766a = c04515;
            }

            public void m762a() {
                hi.m500a().m520e().m655d();
            }
        }

        C04515(im imVar) {
            this.f767a = imVar;
        }

        public void m763a() {
            hi.m500a().m516c().m602d();
            jc.m946a().m955b(new C04501(this));
        }
    }

    /* renamed from: com.flurry.sdk.im.6 */
    class C04526 extends le {
        final /* synthetic */ long f768a;
        final /* synthetic */ im f769b;

        C04526(im imVar, long j) {
            this.f769b = imVar;
            this.f768a = j;
        }

        public void m764a() {
            this.f769b.m772a(false, this.f768a);
        }
    }

    /* renamed from: com.flurry.sdk.im.7 */
    static /* synthetic */ class C04537 {
        static final /* synthetic */ int[] f770a;

        static {
            f770a = new int[C0522a.values().length];
            try {
                f770a[C0522a.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f770a[C0522a.START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f770a[C0522a.END.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f770a[C0522a.FINALIZE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.im.8 */
    class C04558 implements jl<ir> {
        final /* synthetic */ im f772a;

        /* renamed from: com.flurry.sdk.im.8.1 */
        class C04541 extends le {
            final /* synthetic */ C04558 f771a;

            C04541(C04558 c04558) {
                this.f771a = c04558;
            }

            public void m765a() {
                this.f771a.f772a.m772a(true, ip.m818a().m824d());
            }
        }

        C04558(im imVar) {
            this.f772a = imVar;
        }

        public void m766a(ir irVar) {
            jc.m946a().m955b(new C04541(this));
        }
    }

    /* renamed from: com.flurry.sdk.im.9 */
    class C04569 implements kn<List<ik>> {
        final /* synthetic */ im f773a;

        C04569(im imVar) {
            this.f773a = imVar;
        }

        public kk<List<ik>> m768a(int i) {
            return new kj(new C0445a());
        }
    }

    static {
        f779f = im.class.getSimpleName();
        f774a = 100;
        f775b = 10;
        f776c = 1000;
        f777d = 160000;
        f778e = 50;
    }

    public im() {
        this.f785g = new AtomicInteger(0);
        this.f786h = new AtomicInteger(0);
        this.f787i = new AtomicInteger(0);
        this.f788j = new C04461(this);
        this.f798t = new ArrayList();
        this.f799u = new HashMap();
        this.f800v = new HashMap();
        this.f801w = new HashMap();
        this.f802x = new ArrayList();
        this.f803y = true;
        this.f804z = 0;
        this.f780A = new ArrayList();
        this.f781B = 0;
        this.f782C = 0;
        this.f783D = new hj();
        this.f784E = new C04558(this);
        jm.m997a().m1002a("com.flurry.android.sdk.FlurrySessionEvent", this.f788j);
    }

    public void m794a(ko koVar, Context context) {
        this.f789k = new WeakReference(koVar);
        ku a = kt.m1217a();
        this.f794p = ((Boolean) a.m1212a("LogEvents")).booleanValue();
        a.m1213a("LogEvents", (C0426a) this);
        jq.m1016a(4, f779f, "initSettings, LogEvents = " + this.f794p);
        this.f795q = (String) a.m1212a("UserId");
        a.m1213a("UserId", (C0426a) this);
        jq.m1016a(4, f779f, "initSettings, UserId = " + this.f795q);
        this.f796r = ((Byte) a.m1212a("Gender")).byteValue();
        a.m1213a("Gender", (C0426a) this);
        jq.m1016a(4, f779f, "initSettings, Gender = " + this.f796r);
        this.f797s = (Long) a.m1212a("Age");
        a.m1213a("Age", (C0426a) this);
        jq.m1016a(4, f779f, "initSettings, BirthDate = " + this.f797s);
        this.f790l = context.getFileStreamPath(m782k());
        this.f791m = new jj(context.getFileStreamPath(m783l()), ".yflurryreport.", 1, new C04569(this));
        m775c(context);
        m771a(true);
        jc.m946a().m955b(new le() {
            final /* synthetic */ im f751a;

            {
                this.f751a = r1;
            }

            public void m751a() {
                hi.m500a().m516c().m601c();
            }
        });
        jc.m946a().m955b(new le() {
            final /* synthetic */ im f752a;

            {
                this.f752a = r1;
            }

            public void m752a() {
                this.f752a.m780i();
            }
        });
        jc.m946a().m955b(new le() {
            final /* synthetic */ im f753a;

            {
                this.f753a = r1;
            }

            public void m753a() {
                this.f753a.m784m();
            }
        });
        if (iq.m834a().m861c()) {
            jc.m946a().m955b(new le() {
                final /* synthetic */ im f754a;

                {
                    this.f754a = r1;
                }

                public void m754a() {
                    this.f754a.m772a(true, ip.m818a().m824d());
                }
            });
        } else {
            jm.m997a().m1002a("com.flurry.android.sdk.IdProviderFinishedEvent", this.f784E);
        }
    }

    public synchronized void m793a(Context context) {
        jc.m946a().m955b(new le() {
            final /* synthetic */ im f755a;

            {
                this.f755a = r1;
            }

            public void m755a() {
                hi.m500a().m520e().m657e();
            }
        });
        jc.m946a().m955b(new le() {
            final /* synthetic */ im f756a;

            {
                this.f756a = r1;
            }

            public void m756a() {
                hi.m500a().m516c().m601c();
            }
        });
    }

    public synchronized void m799b(Context context) {
        m771a(false);
        long d = ip.m818a().m824d();
        long f = ip.m818a().m826f();
        long h = ip.m818a().m828h();
        int a = ip.m818a().m829i().m896a();
        m774b(ip.m818a().m826f());
        jc.m946a().m955b(new C04472(this, d));
        jc.m946a().m955b(new C04483(this));
        if (iq.m834a().m861c()) {
            jc.m946a().m955b(new C04494(this, d, f, h, a));
        }
    }

    public synchronized void m792a(long j) {
        jm.m997a().m1000a(this.f784E);
        jc.m946a().m955b(new C04515(this));
        if (iq.m834a().m861c()) {
            jc.m946a().m955b(new C04526(this, j));
        }
        kt.m1217a().m1215b("Gender", (C0426a) this);
        kt.m1217a().m1215b("UserId", (C0426a) this);
        kt.m1217a().m1215b("Age", (C0426a) this);
        kt.m1217a().m1215b("LogEvents", (C0426a) this);
    }

    public void m795a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -1752163738:
                if (str.equals("UserId")) {
                    obj2 = 1;
                    break;
                }
                break;
            case -738063011:
                if (str.equals("LogEvents")) {
                    obj2 = null;
                    break;
                }
                break;
            case 65759:
                if (str.equals("Age")) {
                    obj2 = 3;
                    break;
                }
                break;
            case 2129321697:
                if (str.equals("Gender")) {
                    obj2 = 2;
                    break;
                }
                break;
        }
        switch (obj2) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.f794p = ((Boolean) obj).booleanValue();
                jq.m1016a(4, f779f, "onSettingUpdate, LogEvents = " + this.f794p);
            case TTSConst.TTSMULTILINE /*1*/:
                this.f795q = (String) obj;
                jq.m1016a(4, f779f, "onSettingUpdate, UserId = " + this.f795q);
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.f796r = ((Byte) obj).byteValue();
                jq.m1016a(4, f779f, "onSettingUpdate, Gender = " + this.f796r);
            case TTSConst.TTSUNICODE /*3*/:
                this.f797s = (Long) obj;
                jq.m1016a(4, f779f, "onSettingUpdate, Birthdate = " + this.f797s);
            default:
                jq.m1016a(6, f779f, "onSettingUpdate internal error!");
        }
    }

    public void m791a() {
        this.f792n = true;
    }

    private void m775c(Context context) {
        if (context instanceof Activity) {
            Bundle extras = ((Activity) context).getIntent().getExtras();
            if (extras != null) {
                jq.m1016a(3, f779f, "Launch Options Bundle is present " + extras.toString());
                for (String str : extras.keySet()) {
                    if (str != null) {
                        Object obj = extras.get(str);
                        this.f799u.put(str, new ArrayList(Arrays.asList(new String[]{obj != null ? obj.toString() : "null"})));
                        jq.m1016a(3, f779f, "Launch options Key: " + str + ". Its value: " + r1);
                    }
                }
            }
        }
    }

    @TargetApi(18)
    private void m771a(boolean z) {
        boolean z2;
        int intExtra;
        int i = -1;
        if (z) {
            this.f800v.put("boot.time", Long.toString(System.currentTimeMillis() - SystemClock.elapsedRealtime()));
            StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (VERSION.SDK_INT >= 18) {
                this.f800v.put("disk.size.total.internal", Long.toString(statFs.getAvailableBlocksLong()));
                this.f800v.put("disk.size.available.internal", Long.toString(statFs.getAvailableBlocksLong()));
                this.f800v.put("disk.size.total.external", Long.toString(statFs2.getAvailableBlocksLong()));
                this.f800v.put("disk.size.available.external", Long.toString(statFs2.getAvailableBlocksLong()));
            } else {
                this.f800v.put("disk.size.total.internal", Long.toString((long) statFs.getAvailableBlocks()));
                this.f800v.put("disk.size.available.internal", Long.toString((long) statFs.getAvailableBlocks()));
                this.f800v.put("disk.size.total.external", Long.toString((long) statFs2.getAvailableBlocks()));
                this.f800v.put("disk.size.available.external", Long.toString((long) statFs2.getAvailableBlocks()));
            }
            this.f800v.put("carrier.name", ix.m905a().m907c());
            this.f800v.put("carrier.details", ix.m905a().m908d());
        }
        ActivityManager activityManager = (ActivityManager) jc.m946a().m957c().getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        this.f800v.put("memory.available" + (z ? ".start" : ".end"), Long.toString(memoryInfo.availMem));
        if (VERSION.SDK_INT >= 16) {
            this.f800v.put("memory.total" + (z ? ".start" : ".end"), Long.toString(memoryInfo.availMem));
        }
        Intent registerReceiver = jc.m946a().m957c().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver != null) {
            int intExtra2 = registerReceiver.getIntExtra("status", -1);
            z2 = intExtra2 == 2 || intExtra2 == 5;
            int intExtra3 = registerReceiver.getIntExtra("level", -1);
            intExtra = registerReceiver.getIntExtra("scale", -1);
            i = intExtra3;
        } else {
            z2 = false;
            intExtra = -1;
        }
        float f = ((float) i) / ((float) intExtra);
        this.f800v.put("battery.charging" + (z ? ".start" : ".end"), Boolean.toString(z2));
        this.f800v.put("battery.remaining" + (z ? ".start" : ".end"), Float.toString(f));
    }

    private synchronized void m774b(long j) {
        for (ih ihVar : this.f802x) {
            if (ihVar.m671a() && !ihVar.m674b()) {
                ihVar.m669a(j);
            }
        }
    }

    synchronized ik m790a(long j, long j2, long j3, int i) {
        ik ikVar;
        il ilVar = new il();
        ilVar.m719a(iz.m909a().m915e());
        ilVar.m716a(j);
        ilVar.m725b(j2);
        ilVar.m731c(j3);
        ilVar.m721a(this.f800v);
        ilVar.m726b(it.m869a().m871c());
        ilVar.m732c(it.m869a().m872d());
        ilVar.m715a(i);
        ilVar.m724b(la.m1251j());
        ilVar.m735d(m801d());
        ilVar.m717a(iu.m877a().m895e());
        ilVar.m730c(m805h());
        ilVar.m714a(this.f796r);
        ilVar.m718a(this.f797s);
        ilVar.m728b(m804g());
        ilVar.m720a(m802e());
        ilVar.m722a(this.f803y);
        ilVar.m727b(m803f());
        ilVar.m734d(this.f781B);
        try {
            ikVar = new ik(ilVar);
        } catch (IOException e) {
            jq.m1016a(5, f779f, "Error creating analytics session report: " + e);
            ikVar = null;
        }
        if (ikVar == null) {
            jq.m1032e(f779f, "New session report wasn't created");
        }
        return ikVar;
    }

    public synchronized void m798b() {
        this.f782C++;
    }

    public synchronized FlurryEventRecordStatus m788a(String str, String str2, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus;
        flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (map != null) {
            if (!TextUtils.isEmpty(str2)) {
                map.put("\ue8ffsid+Tumblr", str2);
                flurryEventRecordStatus = m789a(str, (Map) map, false);
                jq.m1016a(5, f779f, "logEvent status for syndication:" + flurryEventRecordStatus);
            }
        }
        return flurryEventRecordStatus;
    }

    public synchronized FlurryEventRecordStatus m789a(String str, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus;
        FlurryEventRecordStatus flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventRecorded;
        long elapsedRealtime = SystemClock.elapsedRealtime() - ip.m818a().m825e();
        String b = lc.m1270b(str);
        if (b.length() == 0) {
            flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        } else {
            ig igVar = (ig) this.f801w.get(b);
            if (igVar != null) {
                igVar.f701a++;
                jq.m1032e(f779f, "Event count incremented: " + b);
                flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventRecorded;
            } else if (this.f801w.size() < f774a) {
                igVar = new ig();
                igVar.f701a = 1;
                this.f801w.put(b, igVar);
                jq.m1032e(f779f, "Event count started: " + b);
                flurryEventRecordStatus = flurryEventRecordStatus2;
            } else {
                jq.m1032e(f779f, "Too many different events. Event not counted: " + b);
                flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventUniqueCountExceeded;
            }
            if (!this.f794p || this.f802x.size() >= f776c || this.f804z >= f777d) {
                this.f803y = false;
            } else {
                Map emptyMap;
                if (map == null) {
                    emptyMap = Collections.emptyMap();
                } else {
                    Map<String, String> map2 = map;
                }
                if (emptyMap.size() > f775b) {
                    jq.m1032e(f779f, "MaxEventParams exceeded: " + emptyMap.size());
                    flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventParamsCountExceeded;
                } else {
                    ih ihVar = new ih(m786o(), b, emptyMap, elapsedRealtime, z);
                    if (ihVar.m676d() + this.f804z <= f777d) {
                        this.f802x.add(ihVar);
                        this.f804z = ihVar.m676d() + this.f804z;
                        flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventRecorded;
                    } else {
                        this.f804z = f777d;
                        this.f803y = false;
                        jq.m1032e(f779f, "Event Log size exceeded. No more event details logged.");
                        flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventLogCountExceeded;
                    }
                }
            }
        }
        return flurryEventRecordStatus;
    }

    public synchronized void m797a(String str, Map<String, String> map) {
        for (ih ihVar : this.f802x) {
            if (ihVar.m672a(str)) {
                long elapsedRealtime = SystemClock.elapsedRealtime() - ip.m818a().m825e();
                if (map != null && map.size() > 0 && this.f804z < f777d) {
                    int d = this.f804z - ihVar.m676d();
                    Map hashMap = new HashMap(ihVar.m675c());
                    ihVar.m670a((Map) map);
                    if (ihVar.m676d() + d > f777d) {
                        ihVar.m673b(hashMap);
                        this.f803y = false;
                        this.f804z = f777d;
                        jq.m1032e(f779f, "Event Log size exceeded. No more event details logged.");
                    } else if (ihVar.m675c().size() > f775b) {
                        jq.m1032e(f779f, "MaxEventParams exceeded on endEvent: " + ihVar.m675c().size());
                        ihVar.m673b(hashMap);
                    } else {
                        this.f804z = d + ihVar.m676d();
                    }
                }
                ihVar.m669a(elapsedRealtime);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void m796a(java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.Throwable r13) {
        /*
        r9 = this;
        r0 = 0;
        monitor-enter(r9);
        if (r10 == 0) goto L_0x005b;
    L_0x0004:
        r1 = "uncaught";
        r1 = r1.equals(r10);	 Catch:{ all -> 0x00a2 }
        if (r1 == 0) goto L_0x005b;
    L_0x000d:
        r1 = 1;
    L_0x000e:
        r2 = r9.f781B;	 Catch:{ all -> 0x00a2 }
        r2 = r2 + 1;
        r9.f781B = r2;	 Catch:{ all -> 0x00a2 }
        r2 = r9.f780A;	 Catch:{ all -> 0x00a2 }
        r2 = r2.size();	 Catch:{ all -> 0x00a2 }
        r3 = f778e;	 Catch:{ all -> 0x00a2 }
        if (r2 >= r3) goto L_0x005d;
    L_0x001e:
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x00a2 }
        r2 = java.lang.Long.valueOf(r0);	 Catch:{ all -> 0x00a2 }
        r0 = new com.flurry.sdk.if;	 Catch:{ all -> 0x00a2 }
        r1 = r9.m787p();	 Catch:{ all -> 0x00a2 }
        r2 = r2.longValue();	 Catch:{ all -> 0x00a2 }
        r4 = r10;
        r5 = r11;
        r6 = r12;
        r7 = r13;
        r0.<init>(r1, r2, r4, r5, r6, r7);	 Catch:{ all -> 0x00a2 }
        r1 = r9.f780A;	 Catch:{ all -> 0x00a2 }
        r1.add(r0);	 Catch:{ all -> 0x00a2 }
        r1 = f779f;	 Catch:{ all -> 0x00a2 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00a2 }
        r2.<init>();	 Catch:{ all -> 0x00a2 }
        r3 = "Error logged: ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00a2 }
        r0 = r0.m668c();	 Catch:{ all -> 0x00a2 }
        r0 = r2.append(r0);	 Catch:{ all -> 0x00a2 }
        r0 = r0.toString();	 Catch:{ all -> 0x00a2 }
        com.flurry.sdk.jq.m1032e(r1, r0);	 Catch:{ all -> 0x00a2 }
    L_0x0059:
        monitor-exit(r9);
        return;
    L_0x005b:
        r1 = r0;
        goto L_0x000e;
    L_0x005d:
        if (r1 == 0) goto L_0x00a9;
    L_0x005f:
        r8 = r0;
    L_0x0060:
        r0 = r9.f780A;	 Catch:{ all -> 0x00a2 }
        r0 = r0.size();	 Catch:{ all -> 0x00a2 }
        if (r8 >= r0) goto L_0x0059;
    L_0x0068:
        r0 = r9.f780A;	 Catch:{ all -> 0x00a2 }
        r0 = r0.get(r8);	 Catch:{ all -> 0x00a2 }
        r0 = (com.flurry.sdk.C0438if) r0;	 Catch:{ all -> 0x00a2 }
        r1 = r0.m668c();	 Catch:{ all -> 0x00a2 }
        if (r1 == 0) goto L_0x00a5;
    L_0x0076:
        r1 = "uncaught";
        r0 = r0.m668c();	 Catch:{ all -> 0x00a2 }
        r0 = r1.equals(r0);	 Catch:{ all -> 0x00a2 }
        if (r0 != 0) goto L_0x00a5;
    L_0x0083:
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x00a2 }
        r2 = java.lang.Long.valueOf(r0);	 Catch:{ all -> 0x00a2 }
        r0 = new com.flurry.sdk.if;	 Catch:{ all -> 0x00a2 }
        r1 = r9.m787p();	 Catch:{ all -> 0x00a2 }
        r2 = r2.longValue();	 Catch:{ all -> 0x00a2 }
        r4 = r10;
        r5 = r11;
        r6 = r12;
        r7 = r13;
        r0.<init>(r1, r2, r4, r5, r6, r7);	 Catch:{ all -> 0x00a2 }
        r1 = r9.f780A;	 Catch:{ all -> 0x00a2 }
        r1.set(r8, r0);	 Catch:{ all -> 0x00a2 }
        goto L_0x0059;
    L_0x00a2:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
    L_0x00a5:
        r0 = r8 + 1;
        r8 = r0;
        goto L_0x0060;
    L_0x00a9:
        r0 = f779f;	 Catch:{ all -> 0x00a2 }
        r1 = "Max errors logged. No more errors logged.";
        com.flurry.sdk.jq.m1032e(r0, r1);	 Catch:{ all -> 0x00a2 }
        goto L_0x0059;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.im.a(java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m772a(boolean r21, long r22) {
        /*
        r20 = this;
        monitor-enter(r20);
        if (r21 != 0) goto L_0x000f;
    L_0x0003:
        r0 = r20;
        r2 = r0.f798t;	 Catch:{ all -> 0x0078 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0078 }
        if (r2 == 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r20);
        return;
    L_0x000f:
        r2 = 3;
        r3 = f779f;	 Catch:{ all -> 0x0078 }
        r4 = "generating agent report";
        com.flurry.sdk.jq.m1016a(r2, r3, r4);	 Catch:{ all -> 0x0078 }
        r19 = 0;
        r3 = new com.flurry.sdk.ii;	 Catch:{ Exception -> 0x007b }
        r2 = com.flurry.sdk.jc.m946a();	 Catch:{ Exception -> 0x007b }
        r4 = r2.m959d();	 Catch:{ Exception -> 0x007b }
        r2 = com.flurry.sdk.iz.m909a();	 Catch:{ Exception -> 0x007b }
        r5 = r2.m915e();	 Catch:{ Exception -> 0x007b }
        r0 = r20;
        r6 = r0.f792n;	 Catch:{ Exception -> 0x007b }
        r2 = com.flurry.sdk.iq.m834a();	 Catch:{ Exception -> 0x007b }
        r7 = r2.m863e();	 Catch:{ Exception -> 0x007b }
        r0 = r20;
        r8 = r0.f793o;	 Catch:{ Exception -> 0x007b }
        r0 = r20;
        r12 = r0.f798t;	 Catch:{ Exception -> 0x007b }
        r2 = com.flurry.sdk.iq.m834a();	 Catch:{ Exception -> 0x007b }
        r13 = r2.m866h();	 Catch:{ Exception -> 0x007b }
        r0 = r20;
        r2 = r0.f783D;	 Catch:{ Exception -> 0x007b }
        r10 = 0;
        r14 = r2.m526a(r10);	 Catch:{ Exception -> 0x007b }
        r0 = r20;
        r15 = r0.f799u;	 Catch:{ Exception -> 0x007b }
        r2 = com.flurry.sdk.je.m969a();	 Catch:{ Exception -> 0x007b }
        r16 = r2.m972c();	 Catch:{ Exception -> 0x007b }
        r17 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x007b }
        r10 = r22;
        r3.<init>(r4, r5, r6, r7, r8, r10, r12, r13, r14, r15, r16, r17);	 Catch:{ Exception -> 0x007b }
        r2 = r3.m678a();	 Catch:{ Exception -> 0x007b }
    L_0x006a:
        if (r2 != 0) goto L_0x0098;
    L_0x006c:
        r2 = f779f;	 Catch:{ all -> 0x0078 }
        r3 = "Error generating report";
        com.flurry.sdk.jq.m1032e(r2, r3);	 Catch:{ all -> 0x0078 }
    L_0x0074:
        r20.m781j();	 Catch:{ all -> 0x0078 }
        goto L_0x000d;
    L_0x0078:
        r2 = move-exception;
        monitor-exit(r20);
        throw r2;
    L_0x007b:
        r2 = move-exception;
        r3 = f779f;	 Catch:{ all -> 0x0078 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0078 }
        r4.<init>();	 Catch:{ all -> 0x0078 }
        r5 = "Exception while generating report: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0078 }
        r2 = r4.append(r2);	 Catch:{ all -> 0x0078 }
        r2 = r2.toString();	 Catch:{ all -> 0x0078 }
        com.flurry.sdk.jq.m1032e(r3, r2);	 Catch:{ all -> 0x0078 }
        r2 = r19;
        goto L_0x006a;
    L_0x0098:
        r3 = 3;
        r4 = f779f;	 Catch:{ all -> 0x0078 }
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0078 }
        r5.<init>();	 Catch:{ all -> 0x0078 }
        r6 = "generated report of size ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x0078 }
        r6 = r2.length;	 Catch:{ all -> 0x0078 }
        r5 = r5.append(r6);	 Catch:{ all -> 0x0078 }
        r6 = " with ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x0078 }
        r0 = r20;
        r6 = r0.f798t;	 Catch:{ all -> 0x0078 }
        r6 = r6.size();	 Catch:{ all -> 0x0078 }
        r5 = r5.append(r6);	 Catch:{ all -> 0x0078 }
        r6 = " reports.";
        r5 = r5.append(r6);	 Catch:{ all -> 0x0078 }
        r5 = r5.toString();	 Catch:{ all -> 0x0078 }
        com.flurry.sdk.jq.m1016a(r3, r4, r5);	 Catch:{ all -> 0x0078 }
        r3 = com.flurry.sdk.hi.m500a();	 Catch:{ all -> 0x0078 }
        r3 = r3.m519d();	 Catch:{ all -> 0x0078 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0078 }
        r4.<init>();	 Catch:{ all -> 0x0078 }
        r5 = "";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0078 }
        r5 = com.flurry.sdk.jd.m963a();	 Catch:{ all -> 0x0078 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0078 }
        r4 = r4.toString();	 Catch:{ all -> 0x0078 }
        r5 = com.flurry.sdk.jc.m946a();	 Catch:{ all -> 0x0078 }
        r5 = r5.m959d();	 Catch:{ all -> 0x0078 }
        r3.m690b(r2, r5, r4);	 Catch:{ all -> 0x0078 }
        goto L_0x0074;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.im.a(boolean, long):void");
    }

    public synchronized void m800c() {
        jq.m1016a(4, f779f, "Saving persistent agent data.");
        this.f791m.m994a(this.f798t);
    }

    private synchronized void m780i() {
        jq.m1016a(4, f779f, "Loading persistent session report data.");
        List list = (List) this.f791m.m993a();
        if (list != null) {
            this.f798t.addAll(list);
        } else if (this.f790l.exists()) {
            jq.m1016a(4, f779f, "Legacy persistent agent data found, converting.");
            in a = hl.m530a(this.f790l);
            if (a != null) {
                boolean a2 = a.m813a();
                long b = a.m814b();
                if (b <= 0) {
                    b = ip.m818a().m824d();
                }
                this.f792n = a2;
                this.f793o = b;
                m785n();
                Collection c = a.m815c();
                if (c != null) {
                    this.f798t.addAll(c);
                }
            }
            this.f790l.delete();
            m800c();
        }
    }

    private void m781j() {
        this.f798t.clear();
        this.f791m.m995b();
    }

    private String m782k() {
        return ".flurryagent." + Integer.toString(jc.m946a().m959d().hashCode(), 16);
    }

    private String m783l() {
        return ".yflurryreport." + Long.toString(lc.m1279i(jc.m946a().m959d()), 16);
    }

    private void m784m() {
        SharedPreferences sharedPreferences = jc.m946a().m957c().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0);
        this.f792n = sharedPreferences.getBoolean("com.flurry.sdk.previous_successful_report", false);
        this.f793o = sharedPreferences.getLong("com.flurry.sdk.initial_run_time", ip.m818a().m824d());
    }

    private void m785n() {
        Editor edit = jc.m946a().m957c().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
        edit.putBoolean("com.flurry.sdk.previous_successful_report", this.f792n);
        edit.putLong("com.flurry.sdk.initial_run_time", this.f793o);
        edit.commit();
    }

    private int m786o() {
        return this.f785g.incrementAndGet();
    }

    private int m787p() {
        return this.f786h.incrementAndGet();
    }

    String m801d() {
        return this.f795q == null ? "" : this.f795q;
    }

    List<ih> m802e() {
        return this.f802x;
    }

    List<C0438if> m803f() {
        return this.f780A;
    }

    Map<String, ig> m804g() {
        return this.f801w;
    }

    int m805h() {
        return this.f782C;
    }
}
