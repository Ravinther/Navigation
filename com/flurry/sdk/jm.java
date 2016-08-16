package com.flurry.sdk;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class jm {
    private static jm f929a;
    private static final String f930b;
    private final jh<String, jz<jl<?>>> f931c;
    private final jh<jz<jl<?>>, String> f932d;

    /* renamed from: com.flurry.sdk.jm.1 */
    class C04731 extends le {
        final /* synthetic */ jl f926a;
        final /* synthetic */ jk f927b;
        final /* synthetic */ jm f928c;

        C04731(jm jmVar, jl jlVar, jk jkVar) {
            this.f928c = jmVar;
            this.f926a = jlVar;
            this.f927b = jkVar;
        }

        public void m996a() {
            this.f926a.m475a(this.f927b);
        }
    }

    static {
        f929a = null;
        f930b = jm.class.getSimpleName();
    }

    public static synchronized jm m997a() {
        jm jmVar;
        synchronized (jm.class) {
            if (f929a == null) {
                f929a = new jm();
            }
            jmVar = f929a;
        }
        return jmVar;
    }

    public static synchronized void m998b() {
        synchronized (jm.class) {
            if (f929a != null) {
                f929a.m1006c();
                f929a = null;
            }
        }
    }

    private jm() {
        this.f931c = new jh();
        this.f932d = new jh();
    }

    public synchronized void m1002a(String str, jl<?> jlVar) {
        if (!(TextUtils.isEmpty(str) || jlVar == null)) {
            Object jzVar = new jz(jlVar);
            if (!this.f931c.m989c(str, jzVar)) {
                this.f931c.m984a((Object) str, jzVar);
                this.f932d.m984a(jzVar, (Object) str);
            }
        }
    }

    public synchronized void m1004b(String str, jl<?> jlVar) {
        if (!TextUtils.isEmpty(str)) {
            jz jzVar = new jz(jlVar);
            this.f931c.m987b(str, jzVar);
            this.f932d.m987b(jzVar, str);
        }
    }

    public synchronized void m1001a(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (jz b : this.f931c.m981a((Object) str)) {
                this.f932d.m987b(b, str);
            }
            this.f931c.m986b(str);
        }
    }

    public synchronized void m1000a(jl<?> jlVar) {
        if (jlVar != null) {
            Object jzVar = new jz(jlVar);
            for (String b : this.f932d.m981a(jzVar)) {
                this.f931c.m987b(b, jzVar);
            }
            this.f932d.m986b(jzVar);
        }
    }

    public synchronized void m1006c() {
        this.f931c.m982a();
        this.f932d.m982a();
    }

    public synchronized int m1003b(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else {
            i = this.f931c.m981a((Object) str).size();
        }
        return i;
    }

    public synchronized List<jl<?>> m1005c(String str) {
        List<jl<?>> emptyList;
        if (TextUtils.isEmpty(str)) {
            emptyList = Collections.emptyList();
        } else {
            List<jl<?>> arrayList = new ArrayList();
            Iterator it = this.f931c.m981a((Object) str).iterator();
            while (it.hasNext()) {
                jl jlVar = (jl) ((jz) it.next()).get();
                if (jlVar == null) {
                    it.remove();
                } else {
                    arrayList.add(jlVar);
                }
            }
            emptyList = arrayList;
        }
        return emptyList;
    }

    public void m999a(jk jkVar) {
        if (jkVar != null) {
            for (jl c04731 : m1005c(jkVar.m867a())) {
                jc.m946a().m955b(new C04731(this, c04731, jkVar));
            }
        }
    }
}
