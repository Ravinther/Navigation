package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.ArrayList;
import java.util.Iterator;

@zzgk
public class zzbh {
    private final Object zzpc;
    private final int zzrC;
    private final int zzrD;
    private final int zzrE;
    private final zzbm zzrF;
    private ArrayList<String> zzrG;
    private int zzrH;
    private int zzrI;
    private int zzrJ;
    private int zzrK;
    private String zzrL;

    public zzbh(int i, int i2, int i3, int i4) {
        this.zzpc = new Object();
        this.zzrG = new ArrayList();
        this.zzrH = 0;
        this.zzrI = 0;
        this.zzrJ = 0;
        this.zzrL = "";
        this.zzrC = i;
        this.zzrD = i2;
        this.zzrE = i3;
        this.zzrF = new zzbm(i4);
    }

    private String zza(ArrayList<String> arrayList, int i) {
        if (arrayList.isEmpty()) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append((String) it.next());
            stringBuffer.append(' ');
            if (stringBuffer.length() > i) {
                break;
            }
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        String stringBuffer2 = stringBuffer.toString();
        return stringBuffer2.length() >= i ? stringBuffer2.substring(0, i) : stringBuffer2;
    }

    private void zzx(String str) {
        if (str != null && str.length() >= this.zzrE) {
            synchronized (this.zzpc) {
                this.zzrG.add(str);
                this.zzrH += str.length();
            }
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzbh)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        zzbh com_google_android_gms_internal_zzbh = (zzbh) obj;
        return com_google_android_gms_internal_zzbh.zzcm() != null && com_google_android_gms_internal_zzbh.zzcm().equals(zzcm());
    }

    public int getScore() {
        return this.zzrK;
    }

    public int hashCode() {
        return zzcm().hashCode();
    }

    public String toString() {
        return "ActivityContent fetchId: " + this.zzrI + " score:" + this.zzrK + " total_length:" + this.zzrH + "\n text: " + zza(this.zzrG, 200) + "\n signture: " + this.zzrL;
    }

    int zza(int i, int i2) {
        return (this.zzrC * i) + (this.zzrD * i2);
    }

    public boolean zzcl() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzrJ == 0;
        }
        return z;
    }

    public String zzcm() {
        return this.zzrL;
    }

    public void zzcn() {
        synchronized (this.zzpc) {
            this.zzrK -= 100;
        }
    }

    public void zzco() {
        synchronized (this.zzpc) {
            this.zzrJ--;
        }
    }

    public void zzcp() {
        synchronized (this.zzpc) {
            this.zzrJ++;
        }
    }

    public void zzcq() {
        synchronized (this.zzpc) {
            int zza = zza(this.zzrH, this.zzrI);
            if (zza > this.zzrK) {
                this.zzrK = zza;
                this.zzrL = this.zzrF.zza(this.zzrG);
            }
        }
    }

    int zzcr() {
        return this.zzrH;
    }

    public void zzg(int i) {
        this.zzrI = i;
    }

    public void zzv(String str) {
        zzx(str);
        synchronized (this.zzpc) {
            if (this.zzrJ < 0) {
                zzb.zzaC("ActivityContent: negative number of WebViews.");
            }
            zzcq();
        }
    }

    public void zzw(String str) {
        zzx(str);
    }
}
