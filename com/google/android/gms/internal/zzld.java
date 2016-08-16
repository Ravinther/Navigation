package com.google.android.gms.internal;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzld<K, V> extends zzlh<K, V> implements Map<K, V> {
    zzlg<K, V> zzaev;

    /* renamed from: com.google.android.gms.internal.zzld.1 */
    class C09741 extends zzlg<K, V> {
        final /* synthetic */ zzld zzaew;

        C09741(zzld com_google_android_gms_internal_zzld) {
            this.zzaew = com_google_android_gms_internal_zzld;
        }

        protected void colClear() {
            this.zzaew.clear();
        }

        protected Object colGetEntry(int index, int offset) {
            return this.zzaew.mArray[(index << 1) + offset];
        }

        protected Map<K, V> colGetMap() {
            return this.zzaew;
        }

        protected int colGetSize() {
            return this.zzaew.mSize;
        }

        protected int colIndexOfKey(Object key) {
            return key == null ? this.zzaew.indexOfNull() : this.zzaew.indexOf(key, key.hashCode());
        }

        protected int colIndexOfValue(Object value) {
            return this.zzaew.indexOfValue(value);
        }

        protected void colPut(K key, V value) {
            this.zzaew.put(key, value);
        }

        protected void colRemoveAt(int index) {
            this.zzaew.removeAt(index);
        }

        protected V colSetValue(int index, V value) {
            return this.zzaew.setValueAt(index, value);
        }
    }

    private zzlg<K, V> zzoV() {
        if (this.zzaev == null) {
            this.zzaev = new C09741(this);
        }
        return this.zzaev;
    }

    public Set<Entry<K, V>> entrySet() {
        return zzoV().getEntrySet();
    }

    public Set<K> keySet() {
        return zzoV().getKeySet();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(this.mSize + map.size());
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public Collection<V> values() {
        return zzoV().getValues();
    }
}
