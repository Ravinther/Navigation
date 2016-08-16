package com.flurry.sdk;

import android.location.Criteria;
import android.location.Location;

public class kt extends ku {
    public static final Integer f1077a;
    public static final Integer f1078b;
    public static final Integer f1079c;
    public static final Integer f1080d;
    public static final String f1081e;
    public static final Boolean f1082f;
    public static final Boolean f1083g;
    public static final String f1084h;
    public static final Boolean f1085i;
    public static final Criteria f1086j;
    public static final Location f1087k;
    public static final Long f1088l;
    public static final Boolean f1089m;
    public static final Long f1090n;
    public static final Byte f1091o;
    public static final Boolean f1092p;
    public static final String f1093q;
    private static kt f1094r;

    public static synchronized kt m1217a() {
        kt ktVar;
        synchronized (kt.class) {
            if (f1094r == null) {
                f1094r = new kt();
            }
            ktVar = f1094r;
        }
        return ktVar;
    }

    public static synchronized void m1218b() {
        synchronized (kt.class) {
            if (f1094r != null) {
                f1094r.m1216d();
            }
            f1094r = null;
        }
    }

    static {
        f1077a = Integer.valueOf(211);
        f1078b = Integer.valueOf(6);
        f1079c = Integer.valueOf(0);
        f1080d = Integer.valueOf(0);
        f1081e = null;
        f1082f = Boolean.valueOf(true);
        f1083g = Boolean.valueOf(true);
        f1084h = null;
        f1085i = Boolean.valueOf(true);
        f1086j = null;
        f1087k = null;
        f1088l = Long.valueOf(10000);
        f1089m = Boolean.valueOf(true);
        f1090n = null;
        f1091o = Byte.valueOf((byte) -1);
        f1092p = Boolean.valueOf(false);
        f1093q = null;
    }

    private kt() {
        m1219c();
    }

    public void m1219c() {
        m1214a("AgentVersion", (Object) f1077a);
        m1214a("ReleaseMajorVersion", (Object) f1078b);
        m1214a("ReleaseMinorVersion", (Object) f1079c);
        m1214a("ReleasePatchVersion", (Object) f1080d);
        m1214a("ReleaseBetaVersion", (Object) "");
        m1214a("VersionName", (Object) f1081e);
        m1214a("CaptureUncaughtExceptions", (Object) f1082f);
        m1214a("UseHttps", (Object) f1083g);
        m1214a("ReportUrl", (Object) f1084h);
        m1214a("ReportLocation", (Object) f1085i);
        m1214a("ExplicitLocation", (Object) f1087k);
        m1214a("ContinueSessionMillis", (Object) f1088l);
        m1214a("LogEvents", (Object) f1089m);
        m1214a("Age", (Object) f1090n);
        m1214a("Gender", (Object) f1091o);
        m1214a("UserId", (Object) "");
        m1214a("ProtonEnabled", (Object) f1092p);
        m1214a("ProtonConfigUrl", (Object) f1093q);
    }
}
