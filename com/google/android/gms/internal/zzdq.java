package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@zzgk
public class zzdq implements Iterable<zzdp> {
    private final List<zzdp> zzxu;

    public zzdq() {
        this.zzxu = new LinkedList();
    }

    private zzdp zzc(zzip com_google_android_gms_internal_zzip) {
        Iterator it = zzp.zzbK().iterator();
        while (it.hasNext()) {
            zzdp com_google_android_gms_internal_zzdp = (zzdp) it.next();
            if (com_google_android_gms_internal_zzdp.zzoL == com_google_android_gms_internal_zzip) {
                return com_google_android_gms_internal_zzdp;
            }
        }
        return null;
    }

    public Iterator<zzdp> iterator() {
        return this.zzxu.iterator();
    }

    public void zza(zzdp com_google_android_gms_internal_zzdp) {
        this.zzxu.add(com_google_android_gms_internal_zzdp);
    }

    public boolean zza(zzip com_google_android_gms_internal_zzip) {
        zzdp zzc = zzc(com_google_android_gms_internal_zzip);
        if (zzc == null) {
            return false;
        }
        zzc.zzxr.abort();
        return true;
    }

    public void zzb(zzdp com_google_android_gms_internal_zzdp) {
        this.zzxu.remove(com_google_android_gms_internal_zzdp);
    }

    public boolean zzb(zzip com_google_android_gms_internal_zzip) {
        return zzc(com_google_android_gms_internal_zzip) != null;
    }
}
