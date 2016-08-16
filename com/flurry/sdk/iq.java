package com.flurry.sdk;

import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.flurry.sdk.kp.C0522a;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

public class iq {
    private static final String f825a;
    private static iq f826b;
    private final Set<String> f827c;
    private final Map<iy, byte[]> f828d;
    private final jl<kp> f829e;
    private C0464a f830f;
    private Info f831g;
    private String f832h;
    private byte[] f833i;

    /* renamed from: com.flurry.sdk.iq.1 */
    class C04601 implements jl<kp> {
        final /* synthetic */ iq f813a;

        /* renamed from: com.flurry.sdk.iq.1.1 */
        class C04591 extends le {
            final /* synthetic */ C04601 f812a;

            C04591(C04601 c04601) {
                this.f812a = c04601;
            }

            public void m830a() {
                this.f812a.f813a.m846j();
            }
        }

        C04601(iq iqVar) {
            this.f813a = iqVar;
        }

        public void m832a(kp kpVar) {
            switch (C04634.f816a[kpVar.f1054c.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    if (this.f813a.m861c()) {
                        jc.m946a().m955b(new C04591(this));
                    }
                default:
            }
        }
    }

    /* renamed from: com.flurry.sdk.iq.2 */
    class C04612 extends le {
        final /* synthetic */ iq f814a;

        C04612(iq iqVar) {
            this.f814a = iqVar;
        }

        public void m833a() {
            this.f814a.m845i();
        }
    }

    /* renamed from: com.flurry.sdk.iq.3 */
    class C04623 implements FilenameFilter {
        final /* synthetic */ iq f815a;

        C04623(iq iqVar) {
            this.f815a = iqVar;
        }

        public boolean accept(File file, String str) {
            return str.startsWith(".flurryagent.");
        }
    }

    /* renamed from: com.flurry.sdk.iq.4 */
    static /* synthetic */ class C04634 {
        static final /* synthetic */ int[] f816a;
        static final /* synthetic */ int[] f817b;

        static {
            f817b = new int[C0464a.values().length];
            try {
                f817b[C0464a.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f817b[C0464a.ADVERTISING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f817b[C0464a.DEVICE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f817b[C0464a.HASHED_IMEI.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f817b[C0464a.REPORTED_IDS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            f816a = new int[C0522a.values().length];
            try {
                f816a[C0522a.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.iq.a */
    enum C0464a {
        NONE,
        ADVERTISING,
        DEVICE,
        HASHED_IMEI,
        REPORTED_IDS,
        FINISHED
    }

    static {
        f825a = iq.class.getSimpleName();
    }

    public static synchronized iq m834a() {
        iq iqVar;
        synchronized (iq.class) {
            if (f826b == null) {
                f826b = new iq();
            }
            iqVar = f826b;
        }
        return iqVar;
    }

    public static void m841b() {
        f826b = null;
    }

    private iq() {
        this.f827c = m858v();
        this.f828d = new HashMap();
        this.f829e = new C04601(this);
        this.f830f = C0464a.NONE;
        jm.m997a().m1002a("com.flurry.android.sdk.FlurrySessionEvent", this.f829e);
        jc.m946a().m955b(new C04612(this));
    }

    public boolean m861c() {
        return C0464a.FINISHED.equals(this.f830f);
    }

    public String m862d() {
        if (this.f831g == null) {
            return null;
        }
        return this.f831g.getId();
    }

    public boolean m863e() {
        if (this.f831g != null && this.f831g.isLimitAdTrackingEnabled()) {
            return false;
        }
        return true;
    }

    public String m864f() {
        return this.f832h;
    }

    public byte[] m865g() {
        return this.f833i;
    }

    public Map<iy, byte[]> m866h() {
        return Collections.unmodifiableMap(this.f828d);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m845i() {
        /*
        r5 = this;
    L_0x0000:
        r0 = com.flurry.sdk.iq.C0464a.FINISHED;
        r1 = r5.f830f;
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x0077;
    L_0x000a:
        r0 = com.flurry.sdk.iq.C04634.f817b;
        r1 = r5.f830f;
        r1 = r1.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 1: goto L_0x0052;
            case 2: goto L_0x0057;
            case 3: goto L_0x005c;
            case 4: goto L_0x0061;
            case 5: goto L_0x0066;
            default: goto L_0x0017;
        };
    L_0x0017:
        r0 = com.flurry.sdk.iq.C04634.f817b;	 Catch:{ Exception -> 0x0029 }
        r1 = r5.f830f;	 Catch:{ Exception -> 0x0029 }
        r1 = r1.ordinal();	 Catch:{ Exception -> 0x0029 }
        r0 = r0[r1];	 Catch:{ Exception -> 0x0029 }
        switch(r0) {
            case 2: goto L_0x0025;
            case 3: goto L_0x006b;
            case 4: goto L_0x006f;
            case 5: goto L_0x0073;
            default: goto L_0x0024;
        };	 Catch:{ Exception -> 0x0029 }
    L_0x0024:
        goto L_0x0000;
    L_0x0025:
        r5.m846j();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0029:
        r0 = move-exception;
        r1 = 4;
        r2 = f825a;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Exception during id fetch:";
        r3 = r3.append(r4);
        r4 = r5.f830f;
        r3 = r3.append(r4);
        r4 = ", ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        com.flurry.sdk.jq.m1016a(r1, r2, r0);
        goto L_0x0000;
    L_0x0052:
        r0 = com.flurry.sdk.iq.C0464a.ADVERTISING;
        r5.f830f = r0;
        goto L_0x0017;
    L_0x0057:
        r0 = com.flurry.sdk.iq.C0464a.DEVICE;
        r5.f830f = r0;
        goto L_0x0017;
    L_0x005c:
        r0 = com.flurry.sdk.iq.C0464a.HASHED_IMEI;
        r5.f830f = r0;
        goto L_0x0017;
    L_0x0061:
        r0 = com.flurry.sdk.iq.C0464a.REPORTED_IDS;
        r5.f830f = r0;
        goto L_0x0017;
    L_0x0066:
        r0 = com.flurry.sdk.iq.C0464a.FINISHED;
        r5.f830f = r0;
        goto L_0x0017;
    L_0x006b:
        r5.m847k();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x006f:
        r5.m848l();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0073:
        r5.m860x();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0077:
        r0 = new com.flurry.sdk.ir;
        r0.<init>();
        r1 = com.flurry.sdk.jm.m997a();
        r1.m999a(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.iq.i():void");
    }

    private void m846j() {
        lc.m1272b();
        if (m849m()) {
            this.f831g = m850n();
            if (m861c()) {
                m860x();
                jm.m997a().m999a(new is());
            }
        }
    }

    private void m847k() {
        lc.m1272b();
        this.f832h = m851o();
    }

    private void m848l() {
        if (jc.m946a().m957c().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
            m859w();
        }
    }

    private boolean m849m() {
        try {
            int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(jc.m946a().m957c());
            if (isGooglePlayServicesAvailable == 0) {
                return true;
            }
            jq.m1032e(f825a, "Google Play Services not available - connection result: " + isGooglePlayServicesAvailable);
            return false;
        } catch (NoClassDefFoundError e) {
            jq.m1024b(f825a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return false;
        } catch (Exception e2) {
            jq.m1024b(f825a, "GOOGLE PLAY SERVICES EXCEPTION: " + e2.getMessage());
            jq.m1024b(f825a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return false;
        }
    }

    private Info m850n() {
        Info info = null;
        try {
            info = AdvertisingIdClient.getAdvertisingIdInfo(jc.m946a().m957c());
        } catch (Exception e) {
            jq.m1024b(f825a, "GOOGLE PLAY SERVICES ERROR: " + e.getMessage());
            jq.m1024b(f825a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
        }
        return info;
    }

    private String m851o() {
        Object p = m852p();
        return !TextUtils.isEmpty(p) ? p : m853q();
    }

    private String m852p() {
        String string = Secure.getString(jc.m946a().m957c().getContentResolver(), "android_id");
        if (m839a(string)) {
            return "AND" + string;
        }
        return null;
    }

    private String m853q() {
        String s = m855s();
        if (TextUtils.isEmpty(s)) {
            s = m856t();
            if (TextUtils.isEmpty(s)) {
                s = m854r();
            }
            m843b(s);
        }
        return s;
    }

    private boolean m839a(String str) {
        if (TextUtils.isEmpty(str) || m844c(str.toLowerCase(Locale.US))) {
            return false;
        }
        return true;
    }

    private String m854r() {
        return "ID" + Long.toString(Double.doubleToLongBits(Math.random()) + ((System.nanoTime() + (lc.m1279i(kz.m1236c(jc.m946a().m957c())) * 37)) * 37), 16);
    }

    private void m843b(String str) {
        if (!TextUtils.isEmpty(str)) {
            File fileStreamPath = jc.m946a().m957c().getFileStreamPath(m857u());
            if (lb.m1255a(fileStreamPath)) {
                m838a(str, fileStreamPath);
            }
        }
    }

    private void m838a(String str, File file) {
        Closeable dataOutputStream;
        Throwable th;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                m837a(str, (DataOutput) dataOutputStream);
                lc.m1265a(dataOutputStream);
            } catch (Throwable th2) {
                th = th2;
                try {
                    jq.m1017a(6, f825a, "Error when saving deviceId", th);
                    lc.m1265a(dataOutputStream);
                } catch (Throwable th3) {
                    th = th3;
                    lc.m1265a(dataOutputStream);
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            dataOutputStream = null;
            lc.m1265a(dataOutputStream);
            throw th;
        }
    }

    private String m855s() {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        String str = null;
        File fileStreamPath = jc.m946a().m957c().getFileStreamPath(m857u());
        if (fileStreamPath != null && fileStreamPath.exists()) {
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    str = m835a((DataInput) dataInputStream);
                    lc.m1265a(dataInputStream);
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        jq.m1017a(6, f825a, "Error when loading deviceId", th);
                        lc.m1265a(dataInputStream);
                        return str;
                    } catch (Throwable th4) {
                        th2 = th4;
                        lc.m1265a(dataInputStream);
                        throw th2;
                    }
                }
            } catch (Throwable th5) {
                dataInputStream = str;
                th2 = th5;
                lc.m1265a(dataInputStream);
                throw th2;
            }
        }
        return str;
    }

    private String m856t() {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        String str = null;
        File filesDir = jc.m946a().m957c().getFilesDir();
        if (filesDir != null) {
            String[] list = filesDir.list(new C04623(this));
            if (!(list == null || list.length == 0)) {
                filesDir = jc.m946a().m957c().getFileStreamPath(list[0]);
                if (filesDir != null && filesDir.exists()) {
                    try {
                        dataInputStream = new DataInputStream(new FileInputStream(filesDir));
                        try {
                            str = m840b((DataInput) dataInputStream);
                            lc.m1265a(dataInputStream);
                        } catch (Throwable th3) {
                            th = th3;
                            try {
                                jq.m1017a(6, f825a, "Error when loading deviceId", th);
                                lc.m1265a(dataInputStream);
                                return str;
                            } catch (Throwable th4) {
                                th2 = th4;
                                lc.m1265a(dataInputStream);
                                throw th2;
                            }
                        }
                    } catch (Throwable th5) {
                        dataInputStream = str;
                        th2 = th5;
                        lc.m1265a(dataInputStream);
                        throw th2;
                    }
                }
            }
        }
        return str;
    }

    private void m837a(String str, DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(1);
        dataOutput.writeUTF(str);
    }

    private String m835a(DataInput dataInput) throws IOException {
        if (1 != dataInput.readInt()) {
            return null;
        }
        return dataInput.readUTF();
    }

    private String m840b(DataInput dataInput) throws IOException {
        if (46586 != dataInput.readUnsignedShort() || 2 != dataInput.readUnsignedShort()) {
            return null;
        }
        dataInput.readUTF();
        return dataInput.readUTF();
    }

    private String m857u() {
        return ".flurryb.";
    }

    private boolean m844c(String str) {
        return this.f827c.contains(str);
    }

    private Set<String> m858v() {
        Set hashSet = new HashSet();
        hashSet.add("null");
        hashSet.add("9774d56d682e549c");
        hashSet.add("dead00beef");
        return Collections.unmodifiableSet(hashSet);
    }

    private void m859w() {
        TelephonyManager telephonyManager = (TelephonyManager) jc.m946a().m957c().getSystemService("phone");
        if (telephonyManager != null) {
            String deviceId = telephonyManager.getDeviceId();
            if (deviceId != null && deviceId.trim().length() > 0) {
                try {
                    byte[] f = lc.m1276f(deviceId);
                    if (f == null || f.length != 20) {
                        jq.m1016a(6, f825a, "sha1 is not " + 20 + " bytes long: " + Arrays.toString(f));
                    } else {
                        this.f833i = f;
                    }
                } catch (Exception e) {
                    jq.m1016a(6, f825a, "Exception in generateHashedImei()");
                }
            }
        }
    }

    private void m860x() {
        String d = m862d();
        if (d != null) {
            jq.m1016a(3, f825a, "Fetched advertising id");
            this.f828d.put(iy.AndroidAdvertisingId, lc.m1275e(d));
        }
        d = m864f();
        if (d != null) {
            jq.m1016a(3, f825a, "Fetched device id");
            this.f828d.put(iy.DeviceId, lc.m1275e(d));
        }
        Object g = m865g();
        if (g != null) {
            jq.m1016a(3, f825a, "Fetched hashed IMEI");
            this.f828d.put(iy.Sha1Imei, g);
        }
    }
}
