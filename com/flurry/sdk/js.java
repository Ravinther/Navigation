package com.flurry.sdk;

import android.content.Context;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class js {
    private static final String f952a;
    private static final Map<Class<? extends jt>, jr> f953b;
    private final Map<Class<? extends jt>, jt> f954c;

    static {
        f952a = js.class.getSimpleName();
        f953b = new LinkedHashMap();
    }

    public static void m1035a(Class<? extends jt> cls, int i) {
        if (cls != null) {
            synchronized (f953b) {
                f953b.put(cls, new jr(cls, i));
            }
        }
    }

    public js() {
        this.f954c = new LinkedHashMap();
    }

    public synchronized void m1039a(Context context) {
        if (context == null) {
            jq.m1016a(5, f952a, "Null context.");
        } else {
            synchronized (f953b) {
                List<jr> arrayList = new ArrayList(f953b.values());
            }
            for (jr jrVar : arrayList) {
                try {
                    if (jrVar.m1034b()) {
                        jt jtVar = (jt) jrVar.m1033a().newInstance();
                        jtVar.m498a(context);
                        this.f954c.put(jrVar.m1033a(), jtVar);
                    }
                } catch (Throwable e) {
                    jq.m1017a(5, f952a, "Flurry Module for class " + jrVar.m1033a() + " is not available:", e);
                }
            }
            kq.m1187a().m1197a(context);
            jg.m974a();
        }
    }

    public synchronized void m1038a() {
        jg.m975b();
        kq.m1192b();
        List b = m1036b();
        for (int size = b.size() - 1; size >= 0; size--) {
            try {
                ((jt) this.f954c.remove(((jt) b.get(size)).getClass())).m499b();
            } catch (Throwable e) {
                jq.m1017a(5, f952a, "Error destroying module:", e);
            }
        }
    }

    public jt m1037a(Class<? extends jt> cls) {
        if (cls == null) {
            return null;
        }
        synchronized (this.f954c) {
            jt jtVar = (jt) this.f954c.get(cls);
        }
        if (jtVar != null) {
            return jtVar;
        }
        throw new IllegalStateException("Module was not registered/initialized. " + cls);
    }

    private List<jt> m1036b() {
        List<jt> arrayList = new ArrayList();
        synchronized (this.f954c) {
            arrayList.addAll(this.f954c.values());
        }
        return arrayList;
    }
}
