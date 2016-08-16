package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONObject;

public class zzbg implements zzbf {
    private final zzbe zzrA;
    private final HashSet<SimpleEntry<String, zzdg>> zzrB;

    public zzbg(zzbe com_google_android_gms_internal_zzbe) {
        this.zzrA = com_google_android_gms_internal_zzbe;
        this.zzrB = new HashSet();
    }

    public void zza(String str, zzdg com_google_android_gms_internal_zzdg) {
        this.zzrA.zza(str, com_google_android_gms_internal_zzdg);
        this.zzrB.add(new SimpleEntry(str, com_google_android_gms_internal_zzdg));
    }

    public void zza(String str, String str2) {
        this.zzrA.zza(str, str2);
    }

    public void zza(String str, JSONObject jSONObject) {
        this.zzrA.zza(str, jSONObject);
    }

    public void zzb(String str, zzdg com_google_android_gms_internal_zzdg) {
        this.zzrA.zzb(str, com_google_android_gms_internal_zzdg);
        this.zzrB.remove(new SimpleEntry(str, com_google_android_gms_internal_zzdg));
    }

    public void zzb(String str, JSONObject jSONObject) {
        this.zzrA.zzb(str, jSONObject);
    }

    public void zzck() {
        Iterator it = this.zzrB.iterator();
        while (it.hasNext()) {
            SimpleEntry simpleEntry = (SimpleEntry) it.next();
            zzb.m1445v("Unregistering eventhandler: " + ((zzdg) simpleEntry.getValue()).toString());
            this.zzrA.zzb((String) simpleEntry.getKey(), (zzdg) simpleEntry.getValue());
        }
        this.zzrB.clear();
    }
}
