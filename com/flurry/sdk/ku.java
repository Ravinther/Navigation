package com.flurry.sdk;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ku {
    private Map<String, Object> f1075a;
    private Map<String, List<C0426a>> f1076b;

    /* renamed from: com.flurry.sdk.ku.a */
    public interface C0426a {
        void m571a(String str, Object obj);
    }

    public ku() {
        this.f1075a = new HashMap();
        this.f1076b = new HashMap();
    }

    public synchronized void m1214a(String str, Object obj) {
        if (!TextUtils.isEmpty(str)) {
            if (!m1210a(obj, this.f1075a.get(str))) {
                if (obj == null) {
                    this.f1075a.remove(str);
                } else {
                    this.f1075a.put(str, obj);
                }
                m1211b(str, obj);
            }
        }
    }

    private boolean m1210a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private void m1211b(String str, Object obj) {
        if (this.f1076b.get(str) != null) {
            for (C0426a a : (List) this.f1076b.get(str)) {
                a.m571a(str, obj);
            }
        }
    }

    public synchronized Object m1212a(String str) {
        return this.f1075a.get(str);
    }

    public synchronized void m1213a(String str, C0426a c0426a) {
        if (!(TextUtils.isEmpty(str) || c0426a == null)) {
            List list = (List) this.f1076b.get(str);
            if (list == null) {
                list = new LinkedList();
            }
            list.add(c0426a);
            this.f1076b.put(str, list);
        }
    }

    public synchronized boolean m1215b(String str, C0426a c0426a) {
        boolean z;
        if (TextUtils.isEmpty(str)) {
            z = false;
        } else if (c0426a == null) {
            z = false;
        } else {
            List list = (List) this.f1076b.get(str);
            z = list == null ? false : list.remove(c0426a);
        }
        return z;
    }

    public synchronized void m1216d() {
        this.f1076b.clear();
    }
}
