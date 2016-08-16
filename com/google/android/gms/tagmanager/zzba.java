package com.google.android.gms.tagmanager;

import android.util.LruCache;
import com.google.android.gms.tagmanager.zzm.zza;

class zzba<K, V> implements zzl<K, V> {
    private LruCache<K, V> zzaQy;

    /* renamed from: com.google.android.gms.tagmanager.zzba.1 */
    class C10121 extends LruCache<K, V> {
        final /* synthetic */ zzba zzaQA;
        final /* synthetic */ zza zzaQz;

        C10121(zzba com_google_android_gms_tagmanager_zzba, int i, zza com_google_android_gms_tagmanager_zzm_zza) {
            this.zzaQA = com_google_android_gms_tagmanager_zzba;
            this.zzaQz = com_google_android_gms_tagmanager_zzm_zza;
            super(i);
        }

        protected int sizeOf(K key, V value) {
            return this.zzaQz.sizeOf(key, value);
        }
    }

    zzba(int i, zza<K, V> com_google_android_gms_tagmanager_zzm_zza_K__V) {
        this.zzaQy = new C10121(this, i, com_google_android_gms_tagmanager_zzm_zza_K__V);
    }

    public V get(K key) {
        return this.zzaQy.get(key);
    }

    public void zzf(K k, V v) {
        this.zzaQy.put(k, v);
    }
}
