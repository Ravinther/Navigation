package com.flurry.sdk;

import java.util.Locale;

public class jd {
    private static final String f902a;

    static {
        f902a = jd.class.getSimpleName();
    }

    public static int m963a() {
        int intValue = ((Integer) kt.m1217a().m1212a("AgentVersion")).intValue();
        jq.m1016a(4, f902a, "getAgentVersion() = " + intValue);
        return intValue;
    }

    public static int m964b() {
        return ((Integer) kt.m1217a().m1212a("ReleaseMajorVersion")).intValue();
    }

    public static int m965c() {
        return ((Integer) kt.m1217a().m1212a("ReleaseMinorVersion")).intValue();
    }

    public static int m966d() {
        return ((Integer) kt.m1217a().m1212a("ReleasePatchVersion")).intValue();
    }

    public static String m967e() {
        return (String) kt.m1217a().m1212a("ReleaseBetaVersion");
    }

    public static String m968f() {
        String str;
        if (m967e().length() > 0) {
            str = ".";
        } else {
            str = "";
        }
        return String.format(Locale.getDefault(), "Flurry_Android_%d_%d.%d.%d%s%s", new Object[]{Integer.valueOf(m963a()), Integer.valueOf(m964b()), Integer.valueOf(m965c()), Integer.valueOf(m966d()), str, m967e()});
    }
}
