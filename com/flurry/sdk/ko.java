package com.flurry.sdk;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ko {
    private static final List<Class> f1043b;
    private final String f1044a;
    private final Map<Class, Object> f1045c;

    static {
        f1043b = new ArrayList();
    }

    public static void m1178a(Class cls) {
        if (cls != null) {
            synchronized (f1043b) {
                f1043b.add(cls);
            }
        }
    }

    public static void m1179b(Class cls) {
        if (cls != null) {
            synchronized (f1043b) {
                f1043b.remove(cls);
            }
        }
    }

    public ko() {
        this.f1044a = ko.class.getSimpleName();
        this.f1045c = new LinkedHashMap();
        synchronized (f1043b) {
            List<Class> arrayList = new ArrayList(f1043b);
        }
        for (Class cls : arrayList) {
            try {
                Object newInstance = cls.newInstance();
                synchronized (this.f1045c) {
                    this.f1045c.put(cls, newInstance);
                }
            } catch (Throwable e) {
                jq.m1017a(5, this.f1044a, "Module data " + cls + " is not available:", e);
            }
        }
    }

    public Object m1180c(Class cls) {
        if (cls == null) {
            return null;
        }
        Object obj;
        synchronized (this.f1045c) {
            obj = this.f1045c.get(cls);
        }
        return obj;
    }
}
