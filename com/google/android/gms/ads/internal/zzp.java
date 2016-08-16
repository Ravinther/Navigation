package com.google.android.gms.ads.internal;

import android.os.Build.VERSION;
import com.google.android.gms.ads.internal.overlay.zze;
import com.google.android.gms.ads.internal.purchase.zzi;
import com.google.android.gms.ads.internal.request.zza;
import com.google.android.gms.internal.zzbv;
import com.google.android.gms.internal.zzbw;
import com.google.android.gms.internal.zzbx;
import com.google.android.gms.internal.zzcb;
import com.google.android.gms.internal.zzdq;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.internal.zzga;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzgs;
import com.google.android.gms.internal.zzhl;
import com.google.android.gms.internal.zzhu;
import com.google.android.gms.internal.zzhv;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zzlm;
import com.google.android.gms.internal.zzlo;

@zzgk
public class zzp {
    private static zzp zzpF;
    private static final Object zzpm;
    private final zza zzpG;
    private final com.google.android.gms.ads.internal.overlay.zza zzpH;
    private final zze zzpI;
    private final zzga zzpJ;
    private final zzhu zzpK;
    private final zzir zzpL;
    private final zzhv zzpM;
    private final zzhl zzpN;
    private final zzlm zzpO;
    private final zzcb zzpP;
    private final zzgs zzpQ;
    private final zzbw zzpR;
    private final zzbv zzpS;
    private final zzbx zzpT;
    private final zzi zzpU;
    private final zzhz zzpV;
    private final zzef zzpW;
    private final zzdq zzpX;

    static {
        zzpm = new Object();
        zza(new zzp());
    }

    protected zzp() {
        this.zzpG = new zza();
        this.zzpH = new com.google.android.gms.ads.internal.overlay.zza();
        this.zzpI = new zze();
        this.zzpJ = new zzga();
        this.zzpK = new zzhu();
        this.zzpL = new zzir();
        this.zzpM = zzhv.zzL(VERSION.SDK_INT);
        this.zzpN = new zzhl(this.zzpK);
        this.zzpO = new zzlo();
        this.zzpP = new zzcb();
        this.zzpQ = new zzgs();
        this.zzpR = new zzbw();
        this.zzpS = new zzbv();
        this.zzpT = new zzbx();
        this.zzpU = new zzi();
        this.zzpV = new zzhz();
        this.zzpW = new zzef();
        this.zzpX = new zzdq();
    }

    protected static void zza(zzp com_google_android_gms_ads_internal_zzp) {
        synchronized (zzpm) {
            zzpF = com_google_android_gms_ads_internal_zzp;
        }
    }

    public static zzhl zzbA() {
        return zzbs().zzpN;
    }

    public static zzlm zzbB() {
        return zzbs().zzpO;
    }

    public static zzcb zzbC() {
        return zzbs().zzpP;
    }

    public static zzgs zzbD() {
        return zzbs().zzpQ;
    }

    public static zzbw zzbE() {
        return zzbs().zzpR;
    }

    public static zzbv zzbF() {
        return zzbs().zzpS;
    }

    public static zzbx zzbG() {
        return zzbs().zzpT;
    }

    public static zzi zzbH() {
        return zzbs().zzpU;
    }

    public static zzhz zzbI() {
        return zzbs().zzpV;
    }

    public static zzef zzbJ() {
        return zzbs().zzpW;
    }

    public static zzdq zzbK() {
        return zzbs().zzpX;
    }

    private static zzp zzbs() {
        zzp com_google_android_gms_ads_internal_zzp;
        synchronized (zzpm) {
            com_google_android_gms_ads_internal_zzp = zzpF;
        }
        return com_google_android_gms_ads_internal_zzp;
    }

    public static zza zzbt() {
        return zzbs().zzpG;
    }

    public static com.google.android.gms.ads.internal.overlay.zza zzbu() {
        return zzbs().zzpH;
    }

    public static zze zzbv() {
        return zzbs().zzpI;
    }

    public static zzga zzbw() {
        return zzbs().zzpJ;
    }

    public static zzhu zzbx() {
        return zzbs().zzpK;
    }

    public static zzir zzby() {
        return zzbs().zzpL;
    }

    public static zzhv zzbz() {
        return zzbs().zzpM;
    }
}
