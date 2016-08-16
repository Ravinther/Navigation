package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@zzgk
public class zzbi {
    private final Object zzpc;
    private int zzrM;
    private List<zzbh> zzrN;

    public zzbi() {
        this.zzpc = new Object();
        this.zzrN = new LinkedList();
    }

    public boolean zza(zzbh com_google_android_gms_internal_zzbh) {
        boolean z;
        synchronized (this.zzpc) {
            if (this.zzrN.contains(com_google_android_gms_internal_zzbh)) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean zzb(zzbh com_google_android_gms_internal_zzbh) {
        boolean z;
        synchronized (this.zzpc) {
            Iterator it = this.zzrN.iterator();
            while (it.hasNext()) {
                zzbh com_google_android_gms_internal_zzbh2 = (zzbh) it.next();
                if (com_google_android_gms_internal_zzbh != com_google_android_gms_internal_zzbh2 && com_google_android_gms_internal_zzbh2.zzcm().equals(com_google_android_gms_internal_zzbh.zzcm())) {
                    it.remove();
                    z = true;
                    break;
                }
            }
            z = false;
        }
        return z;
    }

    public void zzc(zzbh com_google_android_gms_internal_zzbh) {
        synchronized (this.zzpc) {
            if (this.zzrN.size() >= 10) {
                zzb.zzaC("Queue is full, current size = " + this.zzrN.size());
                this.zzrN.remove(0);
            }
            int i = this.zzrM;
            this.zzrM = i + 1;
            com_google_android_gms_internal_zzbh.zzg(i);
            this.zzrN.add(com_google_android_gms_internal_zzbh);
        }
    }

    public zzbh zzcs() {
        zzbh com_google_android_gms_internal_zzbh = null;
        synchronized (this.zzpc) {
            if (this.zzrN.size() == 0) {
                zzb.zzaC("Queue empty");
                return null;
            } else if (this.zzrN.size() >= 2) {
                int i = Integer.MIN_VALUE;
                for (zzbh com_google_android_gms_internal_zzbh2 : this.zzrN) {
                    zzbh com_google_android_gms_internal_zzbh3;
                    int i2;
                    int score = com_google_android_gms_internal_zzbh2.getScore();
                    if (score > i) {
                        int i3 = score;
                        com_google_android_gms_internal_zzbh3 = com_google_android_gms_internal_zzbh2;
                        i2 = i3;
                    } else {
                        i2 = i;
                        com_google_android_gms_internal_zzbh3 = com_google_android_gms_internal_zzbh;
                    }
                    i = i2;
                    com_google_android_gms_internal_zzbh = com_google_android_gms_internal_zzbh3;
                }
                this.zzrN.remove(com_google_android_gms_internal_zzbh);
                return com_google_android_gms_internal_zzbh;
            } else {
                com_google_android_gms_internal_zzbh2 = (zzbh) this.zzrN.get(0);
                com_google_android_gms_internal_zzbh2.zzcn();
                return com_google_android_gms_internal_zzbh2;
            }
        }
    }
}
