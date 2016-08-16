package com.flurry.sdk;

import java.util.Locale;
import java.util.TimeZone;

public class it {
    private static it f835a;

    public static synchronized it m869a() {
        it itVar;
        synchronized (it.class) {
            if (f835a == null) {
                f835a = new it();
            }
            itVar = f835a;
        }
        return itVar;
    }

    public static void m870b() {
        f835a = null;
    }

    private it() {
    }

    public String m871c() {
        return Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
    }

    public String m872d() {
        return TimeZone.getDefault().getID();
    }
}
