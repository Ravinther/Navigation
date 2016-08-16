package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.common.api.Releasable;
import java.util.HashMap;
import java.util.Map;

@zzgk
public abstract class zzdr implements Releasable {
    protected zzip zzoL;

    /* renamed from: com.google.android.gms.internal.zzdr.1 */
    class C08891 implements Runnable {
        final /* synthetic */ zzdr zzxA;
        final /* synthetic */ String zzxv;
        final /* synthetic */ String zzxw;
        final /* synthetic */ int zzxx;
        final /* synthetic */ int zzxy;
        final /* synthetic */ boolean zzxz;

        C08891(zzdr com_google_android_gms_internal_zzdr, String str, String str2, int i, int i2, boolean z) {
            this.zzxA = com_google_android_gms_internal_zzdr;
            this.zzxv = str;
            this.zzxw = str2;
            this.zzxx = i;
            this.zzxy = i2;
            this.zzxz = z;
        }

        public void run() {
            Map hashMap = new HashMap();
            hashMap.put("event", "precacheProgress");
            hashMap.put("src", this.zzxv);
            hashMap.put("cachedSrc", this.zzxw);
            hashMap.put("bytesLoaded", Integer.toString(this.zzxx));
            hashMap.put("totalBytes", Integer.toString(this.zzxy));
            hashMap.put("cacheReady", this.zzxz ? "1" : "0");
            this.zzxA.zzoL.zzc("onPrecacheEvent", hashMap);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdr.2 */
    class C08902 implements Runnable {
        final /* synthetic */ zzdr zzxA;
        final /* synthetic */ String zzxv;
        final /* synthetic */ String zzxw;
        final /* synthetic */ int zzxy;

        C08902(zzdr com_google_android_gms_internal_zzdr, String str, String str2, int i) {
            this.zzxA = com_google_android_gms_internal_zzdr;
            this.zzxv = str;
            this.zzxw = str2;
            this.zzxy = i;
        }

        public void run() {
            Map hashMap = new HashMap();
            hashMap.put("event", "precacheComplete");
            hashMap.put("src", this.zzxv);
            hashMap.put("cachedSrc", this.zzxw);
            hashMap.put("totalBytes", Integer.toString(this.zzxy));
            this.zzxA.zzoL.zzc("onPrecacheEvent", hashMap);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdr.3 */
    class C08913 implements Runnable {
        final /* synthetic */ zzdr zzxA;
        final /* synthetic */ String zzxv;
        final /* synthetic */ String zzxw;

        C08913(zzdr com_google_android_gms_internal_zzdr, String str, String str2) {
            this.zzxA = com_google_android_gms_internal_zzdr;
            this.zzxv = str;
            this.zzxw = str2;
        }

        public void run() {
            Map hashMap = new HashMap();
            hashMap.put("event", "precacheCanceled");
            hashMap.put("src", this.zzxv);
            if (this.zzxw != null) {
                hashMap.put("cachedSrc", this.zzxw);
            }
            this.zzxA.zzoL.zzc("onPrecacheEvent", hashMap);
        }
    }

    public zzdr(zzip com_google_android_gms_internal_zzip) {
        this.zzoL = com_google_android_gms_internal_zzip;
    }

    public abstract void abort();

    public void release() {
    }

    public abstract boolean zzZ(String str);

    protected void zza(String str, String str2, int i) {
        zza.zzIy.post(new C08902(this, str, str2, i));
    }

    protected void zza(String str, String str2, int i, int i2, boolean z) {
        zza.zzIy.post(new C08891(this, str, str2, i, i2, z));
    }

    protected String zzaa(String str) {
        return zzk.zzcE().zzaB(str);
    }

    protected void zzf(String str, String str2) {
        zza.zzIy.post(new C08913(this, str, str2));
    }
}
