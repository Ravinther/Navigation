package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;

public class je {
    private static je f903a;
    private static final String f904b;
    private static final HashMap<String, Map<String, String>> f905c;

    public static synchronized je m969a() {
        je jeVar;
        synchronized (je.class) {
            if (f903a == null) {
                f903a = new je();
            }
            jeVar = f903a;
        }
        return jeVar;
    }

    public static synchronized void m970b() {
        synchronized (je.class) {
            f903a = null;
        }
    }

    static {
        f904b = je.class.getSimpleName();
        f905c = new HashMap();
    }

    public synchronized void m971a(String str, String str2, Map<String, String> map) {
        if (map == null) {
            map = new HashMap();
        }
        if (map.size() >= 10) {
            jq.m1032e(f904b, "MaxOriginParams exceeded: " + map.size());
        } else {
            map.put("flurryOriginVersion", str2);
            synchronized (f905c) {
                if (f905c.size() < 10 || f905c.containsKey(str)) {
                    f905c.put(str, map);
                } else {
                    jq.m1032e(f904b, "MaxOrigins exceeded: " + f905c.size());
                }
            }
        }
    }

    public synchronized HashMap<String, Map<String, String>> m972c() {
        HashMap<String, Map<String, String>> hashMap;
        synchronized (f905c) {
            hashMap = new HashMap(f905c);
        }
        return hashMap;
    }
}
