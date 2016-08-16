package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzez.zza;
import java.util.Map;

@zzgk
public class zzfa extends zzfb implements zzdg {
    private final Context mContext;
    private final zzip zzoL;
    private final WindowManager zzqX;
    private final zzbq zzzN;
    DisplayMetrics zzzO;
    private float zzzP;
    int zzzQ;
    int zzzR;
    private int zzzS;
    int zzzT;
    int zzzU;
    int zzzV;
    int zzzW;

    public zzfa(zzip com_google_android_gms_internal_zzip, Context context, zzbq com_google_android_gms_internal_zzbq) {
        super(com_google_android_gms_internal_zzip);
        this.zzzQ = -1;
        this.zzzR = -1;
        this.zzzT = -1;
        this.zzzU = -1;
        this.zzzV = -1;
        this.zzzW = -1;
        this.zzoL = com_google_android_gms_internal_zzip;
        this.mContext = context;
        this.zzzN = com_google_android_gms_internal_zzbq;
        this.zzqX = (WindowManager) context.getSystemService("window");
    }

    private void zzeb() {
        this.zzzO = new DisplayMetrics();
        Display defaultDisplay = this.zzqX.getDefaultDisplay();
        defaultDisplay.getMetrics(this.zzzO);
        this.zzzP = this.zzzO.density;
        this.zzzS = defaultDisplay.getRotation();
    }

    private void zzeg() {
        int[] iArr = new int[2];
        this.zzoL.getLocationOnScreen(iArr);
        zze(zzk.zzcE().zzc(this.mContext, iArr[0]), zzk.zzcE().zzc(this.mContext, iArr[1]));
    }

    private zzez zzej() {
        return new zza().zzp(this.zzzN.zzcV()).zzo(this.zzzN.zzcW()).zzq(this.zzzN.zzda()).zzr(this.zzzN.zzcX()).zzs(this.zzzN.zzcY()).zzea();
    }

    public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
        zzee();
    }

    public void zze(int i, int i2) {
        zzc(i, i2 - (this.mContext instanceof Activity ? zzp.zzbx().zzj((Activity) this.mContext)[0] : 0), this.zzzV, this.zzzW);
        this.zzoL.zzgS().zzd(i, i2);
    }

    void zzec() {
        this.zzzQ = zzk.zzcE().zzb(this.zzzO, this.zzzO.widthPixels);
        this.zzzR = zzk.zzcE().zzb(this.zzzO, this.zzzO.heightPixels);
        Activity zzgN = this.zzoL.zzgN();
        if (zzgN == null || zzgN.getWindow() == null) {
            this.zzzT = this.zzzQ;
            this.zzzU = this.zzzR;
            return;
        }
        int[] zzg = zzp.zzbx().zzg(zzgN);
        this.zzzT = zzk.zzcE().zzb(this.zzzO, zzg[0]);
        this.zzzU = zzk.zzcE().zzb(this.zzzO, zzg[1]);
    }

    void zzed() {
        if (this.zzoL.zzaN().zzsH) {
            this.zzzV = this.zzzQ;
            this.zzzW = this.zzzR;
            return;
        }
        this.zzoL.measure(0, 0);
        this.zzzV = zzk.zzcE().zzc(this.mContext, this.zzoL.getMeasuredWidth());
        this.zzzW = zzk.zzcE().zzc(this.mContext, this.zzoL.getMeasuredHeight());
    }

    public void zzee() {
        zzeb();
        zzec();
        zzed();
        zzeh();
        zzei();
        zzeg();
        zzef();
    }

    void zzef() {
        if (zzb.zzM(2)) {
            zzb.zzaD("Dispatching Ready Event.");
        }
        zzai(this.zzoL.zzgV().zzIz);
    }

    void zzeh() {
        zza(this.zzzQ, this.zzzR, this.zzzT, this.zzzU, this.zzzP, this.zzzS);
    }

    void zzei() {
        this.zzoL.zzb("onDeviceFeaturesReceived", zzej().toJson());
    }
}
