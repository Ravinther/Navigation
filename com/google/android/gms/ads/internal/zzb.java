package com.google.android.gms.ads.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.purchase.GInAppPurchaseManagerInfoParcel;
import com.google.android.gms.ads.internal.purchase.zzc;
import com.google.android.gms.ads.internal.purchase.zzd;
import com.google.android.gms.ads.internal.purchase.zzf;
import com.google.android.gms.ads.internal.purchase.zzj;
import com.google.android.gms.ads.internal.purchase.zzk;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza;
import com.google.android.gms.ads.internal.request.CapabilityParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzcd;
import com.google.android.gms.internal.zzdi;
import com.google.android.gms.internal.zzeb;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzfj;
import com.google.android.gms.internal.zzfl;
import com.google.android.gms.internal.zzfm;
import com.google.android.gms.internal.zzfq;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhj;
import com.google.android.gms.internal.zzhk;
import com.google.android.gms.internal.zzhu;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@zzgk
public abstract class zzb extends zza implements zzg, zzj, zzdi, zzeb {
    private final Messenger mMessenger;
    protected final zzeh zzow;
    protected transient boolean zzox;

    /* renamed from: com.google.android.gms.ads.internal.zzb.1 */
    class C05961 implements Runnable {
        final /* synthetic */ Intent zzoy;
        final /* synthetic */ zzb zzoz;

        C05961(zzb com_google_android_gms_ads_internal_zzb, Intent intent) {
            this.zzoz = com_google_android_gms_ads_internal_zzb;
            this.zzoy = intent;
        }

        public void run() {
            int zzd = zzp.zzbH().zzd(this.zzoy);
            zzp.zzbH();
            if (!(zzd != 0 || this.zzoz.zzos.zzqg == null || this.zzoz.zzos.zzqg.zzAR == null || this.zzoz.zzos.zzqg.zzAR.zzgQ() == null)) {
                this.zzoz.zzos.zzqg.zzAR.zzgQ().close();
            }
            this.zzoz.zzos.zzqA = false;
        }
    }

    public zzb(Context context, AdSizeParcel adSizeParcel, String str, zzeh com_google_android_gms_internal_zzeh, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        this(new zzq(context, adSizeParcel, str, versionInfoParcel), com_google_android_gms_internal_zzeh, null, com_google_android_gms_ads_internal_zzd);
    }

    zzb(zzq com_google_android_gms_ads_internal_zzq, zzeh com_google_android_gms_internal_zzeh, zzo com_google_android_gms_ads_internal_zzo, zzd com_google_android_gms_ads_internal_zzd) {
        super(com_google_android_gms_ads_internal_zzq, com_google_android_gms_ads_internal_zzo, com_google_android_gms_ads_internal_zzd);
        this.zzow = com_google_android_gms_internal_zzeh;
        this.mMessenger = new Messenger(new zzfj(this.zzos.context));
        this.zzox = false;
    }

    private zza zza(AdRequestParcel adRequestParcel, Bundle bundle) {
        PackageInfo packageInfo;
        int i;
        ApplicationInfo applicationInfo = this.zzos.context.getApplicationInfo();
        try {
            packageInfo = this.zzos.context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
        }
        DisplayMetrics displayMetrics = this.zzos.context.getResources().getDisplayMetrics();
        Bundle bundle2 = null;
        if (!(this.zzos.zzqc == null || this.zzos.zzqc.getParent() == null)) {
            int[] iArr = new int[2];
            this.zzos.zzqc.getLocationOnScreen(iArr);
            int i2 = iArr[0];
            int i3 = iArr[1];
            int width = this.zzos.zzqc.getWidth();
            int height = this.zzos.zzqc.getHeight();
            i = 0;
            if (this.zzos.zzqc.isShown() && i2 + width > 0 && i3 + height > 0 && i2 <= displayMetrics.widthPixels && i3 <= displayMetrics.heightPixels) {
                i = 1;
            }
            bundle2 = new Bundle(5);
            bundle2.putInt("x", i2);
            bundle2.putInt("y", i3);
            bundle2.putInt("width", width);
            bundle2.putInt("height", height);
            bundle2.putInt("visible", i);
        }
        String zzga = zzp.zzbA().zzga();
        this.zzos.zzqi = new zzhk(zzga, this.zzos.zzpZ);
        this.zzos.zzqi.zzh(adRequestParcel);
        String zza = zzp.zzbx().zza(this.zzos.context, this.zzos.zzqc, this.zzos.zzqf);
        int zzbp = zzm.zzq(this.zzos.context).zzbp();
        boolean zzbn = zzm.zzq(this.zzos.context).zzbn();
        long j = 0;
        if (this.zzos.zzqm != null) {
            try {
                j = this.zzos.zzqm.getValue();
            } catch (RemoteException e2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("Cannot get correlation id, default to 0.");
            }
        }
        String uuid = UUID.randomUUID().toString();
        Bundle zza2 = zzp.zzbA().zza(this.zzos.context, this, zzga);
        List arrayList = new ArrayList();
        for (i = 0; i < this.zzos.zzqs.size(); i++) {
            arrayList.add(this.zzos.zzqs.keyAt(i));
        }
        boolean z = this.zzos.zzqn != null;
        boolean z2 = this.zzos.zzqo != null && zzp.zzbA().zzgj();
        return new zza(bundle2, adRequestParcel, this.zzos.zzqf, this.zzos.zzpZ, applicationInfo, packageInfo, zzga, zzp.zzbA().getSessionId(), this.zzos.zzqb, zza2, this.zzos.zzqv, arrayList, bundle, zzp.zzbA().zzge(), this.mMessenger, displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.density, zza, zzbn, zzbp, j, uuid, zzby.zzde(), this.zzos.zzpY, this.zzos.zzqt, new CapabilityParcel(z, z2));
    }

    public String getMediationAdapterClassName() {
        return this.zzos.zzqg == null ? null : this.zzos.zzqg.zzyS;
    }

    public void onAdClicked() {
        if (this.zzos.zzqg == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Ad state was null when trying to ping click URLs.");
            return;
        }
        if (!(this.zzos.zzqg.zzGG == null || this.zzos.zzqg.zzGG.zzyw == null)) {
            zzp.zzbJ().zza(this.zzos.context, this.zzos.zzqb.zzIz, this.zzos.zzqg, this.zzos.zzpZ, false, zzp.zzbx().zza(this.zzos.context, this.zzos.zzqg.zzGG.zzyw, this.zzos.zzqg.zzDO));
        }
        if (!(this.zzos.zzqg.zzyQ == null || this.zzos.zzqg.zzyQ.zzyp == null)) {
            zzp.zzbJ().zza(this.zzos.context, this.zzos.zzqb.zzIz, this.zzos.zzqg, this.zzos.zzpZ, false, zzp.zzbx().zza(this.zzos.context, this.zzos.zzqg.zzyQ.zzyp, this.zzos.zzqg.zzDO));
        }
        super.onAdClicked();
    }

    public void pause() {
        zzx.zzch("pause must be called on the main UI thread.");
        if (!(this.zzos.zzqg == null || this.zzos.zzqg.zzAR == null || !this.zzos.zzbP())) {
            zzp.zzbz().zza(this.zzos.zzqg.zzAR.getWebView());
        }
        if (!(this.zzos.zzqg == null || this.zzos.zzqg.zzyR == null)) {
            try {
                this.zzos.zzqg.zzyR.pause();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("Could not pause mediation adapter.");
            }
        }
        this.zzou.zzg(this.zzos.zzqg);
        this.zzor.pause();
    }

    public void resume() {
        zzx.zzch("resume must be called on the main UI thread.");
        if (!(this.zzos.zzqg == null || this.zzos.zzqg.zzAR == null || !this.zzos.zzbP())) {
            zzp.zzbz().zzb(this.zzos.zzqg.zzAR.getWebView());
        }
        if (!(this.zzos.zzqg == null || this.zzos.zzqg.zzyR == null)) {
            try {
                this.zzos.zzqg.zzyR.resume();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("Could not resume mediation adapter.");
            }
        }
        this.zzor.resume();
        this.zzou.zzh(this.zzos.zzqg);
    }

    public void showInterstitial() {
        throw new IllegalStateException("showInterstitial is not supported for current ad type");
    }

    public void zza(zzfm com_google_android_gms_internal_zzfm) {
        zzx.zzch("setInAppPurchaseListener must be called on the main UI thread.");
        this.zzos.zzqn = com_google_android_gms_internal_zzfm;
    }

    public void zza(zzfq com_google_android_gms_internal_zzfq, String str) {
        zzx.zzch("setPlayStorePurchaseParams must be called on the main UI thread.");
        this.zzos.zzqw = new zzk(str);
        this.zzos.zzqo = com_google_android_gms_internal_zzfq;
        if (!zzp.zzbA().zzgd() && com_google_android_gms_internal_zzfq != null) {
            new zzc(this.zzos.context, this.zzos.zzqo, this.zzos.zzqw).zzgn();
        }
    }

    protected void zza(zzhj com_google_android_gms_internal_zzhj, boolean z) {
        if (com_google_android_gms_internal_zzhj == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Ad state was null when trying to ping impression URLs.");
            return;
        }
        super.zzc(com_google_android_gms_internal_zzhj);
        if (!(com_google_android_gms_internal_zzhj.zzGG == null || com_google_android_gms_internal_zzhj.zzGG.zzyx == null)) {
            zzp.zzbJ().zza(this.zzos.context, this.zzos.zzqb.zzIz, com_google_android_gms_internal_zzhj, this.zzos.zzpZ, z, zzp.zzbx().zza(this.zzos.context, com_google_android_gms_internal_zzhj.zzGG.zzyx, com_google_android_gms_internal_zzhj.zzDO));
        }
        if (com_google_android_gms_internal_zzhj.zzyQ != null && com_google_android_gms_internal_zzhj.zzyQ.zzyq != null) {
            zzp.zzbJ().zza(this.zzos.context, this.zzos.zzqb.zzIz, com_google_android_gms_internal_zzhj, this.zzos.zzpZ, z, zzp.zzbx().zza(this.zzos.context, com_google_android_gms_internal_zzhj.zzyQ.zzyq, com_google_android_gms_internal_zzhj.zzDO));
        }
    }

    public void zza(String str, ArrayList<String> arrayList) {
        zzfl com_google_android_gms_ads_internal_purchase_zzd = new zzd(str, arrayList, this.zzos.context, this.zzos.zzqb.zzIz);
        if (this.zzos.zzqn == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("InAppPurchaseListener is not set. Try to launch default purchase flow.");
            if (!com.google.android.gms.ads.internal.client.zzk.zzcE().zzR(this.zzos.context)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("Google Play Service unavailable, cannot launch default purchase flow.");
                return;
            } else if (this.zzos.zzqo == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("PlayStorePurchaseListener is not set.");
                return;
            } else if (this.zzos.zzqw == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("PlayStorePurchaseVerifier is not initialized.");
                return;
            } else if (this.zzos.zzqA) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("An in-app purchase request is already in progress, abort");
                return;
            } else {
                this.zzos.zzqA = true;
                try {
                    if (this.zzos.zzqo.isValidPurchase(str)) {
                        zzp.zzbH().zza(this.zzos.context, this.zzos.zzqb.zzIC, new GInAppPurchaseManagerInfoParcel(this.zzos.context, this.zzos.zzqw, com_google_android_gms_ads_internal_purchase_zzd, this));
                        return;
                    } else {
                        this.zzos.zzqA = false;
                        return;
                    }
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaE("Could not start In-App purchase.");
                    this.zzos.zzqA = false;
                    return;
                }
            }
        }
        try {
            this.zzos.zzqn.zza(com_google_android_gms_ads_internal_purchase_zzd);
        } catch (RemoteException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Could not start In-App purchase.");
        }
    }

    public void zza(String str, boolean z, int i, Intent intent, zzf com_google_android_gms_ads_internal_purchase_zzf) {
        try {
            if (this.zzos.zzqo != null) {
                this.zzos.zzqo.zza(new com.google.android.gms.ads.internal.purchase.zzg(this.zzos.context, str, z, i, intent, com_google_android_gms_ads_internal_purchase_zzf));
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Fail to invoke PlayStorePurchaseListener.");
        }
        zzhu.zzHK.postDelayed(new C05961(this, intent), 500);
    }

    public boolean zza(AdRequestParcel adRequestParcel, zzcd com_google_android_gms_internal_zzcd) {
        if (!zzaU()) {
            return false;
        }
        Bundle zza = zza(zzp.zzbA().zzF(this.zzos.context));
        this.zzor.cancel();
        this.zzos.zzqz = 0;
        zza zza2 = zza(adRequestParcel, zza);
        com_google_android_gms_internal_zzcd.zzd("seq_num", zza2.zzDB);
        if (zza2.zzDQ != null) {
            com_google_android_gms_internal_zzcd.zzd("request_id", zza2.zzDQ);
        }
        if (zza2.zzDz != null) {
            com_google_android_gms_internal_zzcd.zzd("app_version", String.valueOf(zza2.zzDz.versionCode));
        }
        this.zzos.zzqd = zzp.zzbt().zza(this.zzos.context, zza2, this.zzos.zzqa, this);
        return true;
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzhj com_google_android_gms_internal_zzhj, boolean z) {
        if (!z && this.zzos.zzbP()) {
            if (com_google_android_gms_internal_zzhj.zzyA > 0) {
                this.zzor.zza(adRequestParcel, com_google_android_gms_internal_zzhj.zzyA);
            } else if (com_google_android_gms_internal_zzhj.zzGG != null && com_google_android_gms_internal_zzhj.zzGG.zzyA > 0) {
                this.zzor.zza(adRequestParcel, com_google_android_gms_internal_zzhj.zzGG.zzyA);
            } else if (!com_google_android_gms_internal_zzhj.zzDX && com_google_android_gms_internal_zzhj.errorCode == 2) {
                this.zzor.zzf(adRequestParcel);
            }
        }
        return this.zzor.zzbr();
    }

    boolean zza(zzhj com_google_android_gms_internal_zzhj) {
        AdRequestParcel adRequestParcel;
        boolean z = false;
        if (this.zzot != null) {
            adRequestParcel = this.zzot;
            this.zzot = null;
        } else {
            adRequestParcel = com_google_android_gms_internal_zzhj.zzDy;
            if (adRequestParcel.extras != null) {
                z = adRequestParcel.extras.getBoolean("_noRefresh", false);
            }
        }
        return zza(adRequestParcel, com_google_android_gms_internal_zzhj, z);
    }

    protected boolean zza(zzhj com_google_android_gms_internal_zzhj, zzhj com_google_android_gms_internal_zzhj2) {
        int i;
        int i2 = 0;
        if (!(com_google_android_gms_internal_zzhj == null || com_google_android_gms_internal_zzhj.zzyT == null)) {
            com_google_android_gms_internal_zzhj.zzyT.zza(null);
        }
        if (com_google_android_gms_internal_zzhj2.zzyT != null) {
            com_google_android_gms_internal_zzhj2.zzyT.zza((zzeb) this);
        }
        if (com_google_android_gms_internal_zzhj2.zzGG != null) {
            i = com_google_android_gms_internal_zzhj2.zzGG.zzyD;
            i2 = com_google_android_gms_internal_zzhj2.zzGG.zzyE;
        } else {
            i = 0;
        }
        this.zzos.zzqx.zzf(i, i2);
        return true;
    }

    protected boolean zzaU() {
        return zzp.zzbx().zza(this.zzos.context.getPackageManager(), this.zzos.context.getPackageName(), "android.permission.INTERNET") && zzp.zzbx().zzI(this.zzos.context);
    }

    public void zzaV() {
        this.zzou.zze(this.zzos.zzqg);
        this.zzox = false;
        zzaQ();
        this.zzos.zzqi.zzfV();
    }

    public void zzaW() {
        this.zzox = true;
        zzaS();
    }

    public void zzaX() {
        onAdClicked();
    }

    public void zzaY() {
        zzaV();
    }

    public void zzaZ() {
        zzaO();
    }

    public void zzb(zzhj com_google_android_gms_internal_zzhj) {
        super.zzb(com_google_android_gms_internal_zzhj);
        if (com_google_android_gms_internal_zzhj.errorCode == 3 && com_google_android_gms_internal_zzhj.zzGG != null && com_google_android_gms_internal_zzhj.zzGG.zzyy != null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Pinging no fill URLs.");
            zzp.zzbJ().zza(this.zzos.context, this.zzos.zzqb.zzIz, com_google_android_gms_internal_zzhj, this.zzos.zzpZ, false, com_google_android_gms_internal_zzhj.zzGG.zzyy);
        }
    }

    protected boolean zzb(AdRequestParcel adRequestParcel) {
        return super.zzb(adRequestParcel) && !this.zzox;
    }

    public void zzba() {
        zzaW();
    }

    public void zzbb() {
        if (this.zzos.zzqg != null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Mediation adapter " + this.zzos.zzqg.zzyS + " refreshed, but mediation adapters should never refresh.");
        }
        zza(this.zzos.zzqg, true);
        zzaT();
    }
}
