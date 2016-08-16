package com.flurry.sdk;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class jh<K, V> {
    private final Map<K, List<V>> f920a;
    private int f921b;

    public jh() {
        this.f920a = new HashMap();
    }

    public jh(Map<K, List<V>> map) {
        this.f920a = map;
    }

    public void m982a() {
        this.f920a.clear();
    }

    public List<V> m981a(K k) {
        if (k == null) {
            return Collections.emptyList();
        }
        List<V> a = m980a((Object) k, false);
        if (a == null) {
            return Collections.emptyList();
        }
        return a;
    }

    public void m984a(K k, V v) {
        if (k != null) {
            m980a((Object) k, true).add(v);
        }
    }

    public void m983a(jh<K, V> jhVar) {
        if (jhVar != null) {
            for (Entry entry : jhVar.f920a.entrySet()) {
                m980a(entry.getKey(), true).addAll((Collection) entry.getValue());
            }
        }
    }

    public boolean m987b(K k, V v) {
        boolean z = false;
        if (k != null) {
            List a = m980a((Object) k, false);
            if (a != null) {
                z = a.remove(v);
                if (a.size() == 0) {
                    this.f920a.remove(k);
                }
            }
        }
        return z;
    }

    public boolean m986b(K k) {
        if (k == null) {
            return false;
        }
        return ((List) this.f920a.remove(k)) != null;
    }

    public boolean m989c(K k, V v) {
        if (k == null) {
            return false;
        }
        List a = m980a((Object) k, false);
        if (a != null) {
            return a.contains(v);
        }
        return false;
    }

    public Collection<Entry<K, V>> m985b() {
        Collection arrayList = new ArrayList();
        for (Entry entry : this.f920a.entrySet()) {
            for (Object simpleImmutableEntry : (List) entry.getValue()) {
                arrayList.add(new SimpleImmutableEntry(entry.getKey(), simpleImmutableEntry));
            }
        }
        return arrayList;
    }

    public Set<K> m988c() {
        return this.f920a.keySet();
    }

    public Collection<V> m990d() {
        Collection arrayList = new ArrayList();
        for (Entry value : this.f920a.entrySet()) {
            arrayList.addAll((Collection) value.getValue());
        }
        return arrayList;
    }

    private List<V> m980a(K k, boolean z) {
        List<V> list = (List) this.f920a.get(k);
        if (z && list == null) {
            if (this.f921b > 0) {
                list = new ArrayList(this.f921b);
            } else {
                list = new ArrayList();
            }
            this.f920a.put(k, list);
        }
        return list;
    }
}
