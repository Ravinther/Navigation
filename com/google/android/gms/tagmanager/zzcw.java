package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzm.zza;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class zzcw<K, V> implements zzl<K, V> {
    private final Map<K, V> zzaSl;
    private final int zzaSm;
    private final zza<K, V> zzaSn;
    private int zzaSo;

    zzcw(int i, zza<K, V> com_google_android_gms_tagmanager_zzm_zza_K__V) {
        this.zzaSl = new HashMap();
        this.zzaSm = i;
        this.zzaSn = com_google_android_gms_tagmanager_zzm_zza_K__V;
    }

    public synchronized V get(K key) {
        return this.zzaSl.get(key);
    }

    public synchronized void zzf(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        this.zzaSo += this.zzaSn.sizeOf(k, v);
        if (this.zzaSo > this.zzaSm) {
            Iterator it = this.zzaSl.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                this.zzaSo -= this.zzaSn.sizeOf(entry.getKey(), entry.getValue());
                it.remove();
                if (this.zzaSo <= this.zzaSm) {
                    break;
                }
            }
        }
        this.zzaSl.put(k, v);
    }
}
