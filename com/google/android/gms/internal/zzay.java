package com.google.android.gms.internal;

import android.content.Context;
import android.view.View;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.WeakHashMap;

@zzgk
public class zzay implements zzba {
    private final VersionInfoParcel zzpa;
    private final Object zzpc;
    private final WeakHashMap<zzhj, zzaz> zzqM;
    private final ArrayList<zzaz> zzqN;
    private final Context zzqO;
    private final zzdv zzqP;

    public zzay(Context context, VersionInfoParcel versionInfoParcel, zzdv com_google_android_gms_internal_zzdv) {
        this.zzpc = new Object();
        this.zzqM = new WeakHashMap();
        this.zzqN = new ArrayList();
        this.zzqO = context.getApplicationContext();
        this.zzpa = versionInfoParcel;
        this.zzqP = com_google_android_gms_internal_zzdv;
    }

    public zzaz zza(AdSizeParcel adSizeParcel, zzhj com_google_android_gms_internal_zzhj) {
        return zza(adSizeParcel, com_google_android_gms_internal_zzhj, com_google_android_gms_internal_zzhj.zzAR.getWebView());
    }

    public zzaz zza(AdSizeParcel adSizeParcel, zzhj com_google_android_gms_internal_zzhj, View view) {
        zzaz com_google_android_gms_internal_zzaz;
        synchronized (this.zzpc) {
            if (zzd(com_google_android_gms_internal_zzhj)) {
                com_google_android_gms_internal_zzaz = (zzaz) this.zzqM.get(com_google_android_gms_internal_zzhj);
            } else {
                com_google_android_gms_internal_zzaz = new zzaz(adSizeParcel, com_google_android_gms_internal_zzhj, this.zzpa, view, this.zzqP);
                com_google_android_gms_internal_zzaz.zza((zzba) this);
                this.zzqM.put(com_google_android_gms_internal_zzhj, com_google_android_gms_internal_zzaz);
                this.zzqN.add(com_google_android_gms_internal_zzaz);
            }
        }
        return com_google_android_gms_internal_zzaz;
    }

    public void zza(zzaz com_google_android_gms_internal_zzaz) {
        synchronized (this.zzpc) {
            if (!com_google_android_gms_internal_zzaz.zzcd()) {
                this.zzqN.remove(com_google_android_gms_internal_zzaz);
                Iterator it = this.zzqM.entrySet().iterator();
                while (it.hasNext()) {
                    if (((Entry) it.next()).getValue() == com_google_android_gms_internal_zzaz) {
                        it.remove();
                    }
                }
            }
        }
    }

    public boolean zzd(zzhj com_google_android_gms_internal_zzhj) {
        boolean z;
        synchronized (this.zzpc) {
            zzaz com_google_android_gms_internal_zzaz = (zzaz) this.zzqM.get(com_google_android_gms_internal_zzhj);
            z = com_google_android_gms_internal_zzaz != null && com_google_android_gms_internal_zzaz.zzcd();
        }
        return z;
    }

    public void zze(zzhj com_google_android_gms_internal_zzhj) {
        synchronized (this.zzpc) {
            zzaz com_google_android_gms_internal_zzaz = (zzaz) this.zzqM.get(com_google_android_gms_internal_zzhj);
            if (com_google_android_gms_internal_zzaz != null) {
                com_google_android_gms_internal_zzaz.zzcb();
            }
        }
    }

    public void zzf(zzhj com_google_android_gms_internal_zzhj) {
        synchronized (this.zzpc) {
            zzaz com_google_android_gms_internal_zzaz = (zzaz) this.zzqM.get(com_google_android_gms_internal_zzhj);
            if (com_google_android_gms_internal_zzaz != null) {
                com_google_android_gms_internal_zzaz.stop();
            }
        }
    }

    public void zzg(zzhj com_google_android_gms_internal_zzhj) {
        synchronized (this.zzpc) {
            zzaz com_google_android_gms_internal_zzaz = (zzaz) this.zzqM.get(com_google_android_gms_internal_zzhj);
            if (com_google_android_gms_internal_zzaz != null) {
                com_google_android_gms_internal_zzaz.pause();
            }
        }
    }

    public void zzh(zzhj com_google_android_gms_internal_zzhj) {
        synchronized (this.zzpc) {
            zzaz com_google_android_gms_internal_zzaz = (zzaz) this.zzqM.get(com_google_android_gms_internal_zzhj);
            if (com_google_android_gms_internal_zzaz != null) {
                com_google_android_gms_internal_zzaz.resume();
            }
        }
    }
}
