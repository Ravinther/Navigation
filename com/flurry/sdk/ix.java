package com.flurry.sdk;

import android.telephony.TelephonyManager;

public class ix {
    private static ix f865a;
    private static final String f866b;

    public static synchronized ix m905a() {
        ix ixVar;
        synchronized (ix.class) {
            if (f865a == null) {
                f865a = new ix();
            }
            ixVar = f865a;
        }
        return ixVar;
    }

    public static void m906b() {
        f865a = null;
    }

    static {
        f866b = ix.class.getSimpleName();
    }

    private ix() {
    }

    public String m907c() {
        TelephonyManager telephonyManager = (TelephonyManager) jc.m946a().m957c().getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperatorName();
    }

    public String m908d() {
        TelephonyManager telephonyManager = (TelephonyManager) jc.m946a().m957c().getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperator();
    }
}
