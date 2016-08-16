package com.bosch.myspin.serversdk.service.client;

import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.Iterator;
import java.util.LinkedHashMap;

/* renamed from: com.bosch.myspin.serversdk.service.client.v */
public class C0211v implements Iterable<C0210u> {
    private static final LogComponent f283a;
    private LinkedHashMap<Integer, C0210u> f284b;

    public C0211v() {
        this.f284b = new LinkedHashMap();
    }

    static {
        f283a = LogComponent.TouchInjection;
    }

    public void m300a(C0210u c0210u) {
        if (c0210u != null) {
            this.f284b.put(Integer.valueOf(c0210u.m297c()), c0210u);
            Logger.logDebug(f283a, "TouchContainer/Touch cache size:" + this.f284b.size());
        }
    }

    public void m299a() {
        Iterator it = this.f284b.keySet().iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            C0210u c0210u = (C0210u) this.f284b.get(num);
            if ((c0210u != null && 1 == c0210u.m296b()) || 6 == c0210u.m296b() || 3 == c0210u.m296b()) {
                it.remove();
                this.f284b.remove(num);
            }
        }
    }

    public Iterator<C0210u> iterator() {
        return this.f284b.values().iterator();
    }

    public int m301b() {
        return this.f284b.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer num : this.f284b.keySet()) {
            stringBuilder.append("id: ").append(num).append(", action: ").append(((C0210u) this.f284b.get(num)).m298d()).append("\n");
        }
        return stringBuilder.toString();
    }

    public C0210u m302c() {
        if (this.f284b.size() > 0) {
            return (C0210u) this.f284b.get(this.f284b.keySet().iterator().next());
        }
        return null;
    }

    void m303d() {
        this.f284b.clear();
    }
}
