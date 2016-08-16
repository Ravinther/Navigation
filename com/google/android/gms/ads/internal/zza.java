package com.google.android.gms.ads.internal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.client.zzt;
import com.google.android.gms.ads.internal.client.zzu;
import com.google.android.gms.ads.internal.overlay.zzn;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzay;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbk;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzcc;
import com.google.android.gms.internal.zzcd;
import com.google.android.gms.internal.zzch;
import com.google.android.gms.internal.zzdd;
import com.google.android.gms.internal.zzfm;
import com.google.android.gms.internal.zzfq;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhj;
import com.google.android.gms.internal.zzhk;
import com.google.android.gms.internal.zzhn;
import com.google.android.gms.internal.zzho;
import java.util.HashSet;

@zzgk
public abstract class zza extends com.google.android.gms.ads.internal.client.zzr.zza implements com.google.android.gms.ads.internal.client.zza, zzn, com.google.android.gms.ads.internal.request.zza.zza, zzdd, com.google.android.gms.internal.zzga.zza, zzhn {
    private zzcd zzon;
    private zzcc zzoo;
    private zzcc zzop;
    boolean zzoq;
    protected final zzo zzor;
    protected final zzq zzos;
    protected transient AdRequestParcel zzot;
    protected final zzay zzou;
    protected final zzd zzov;

    zza(zzq com_google_android_gms_ads_internal_zzq, zzo com_google_android_gms_ads_internal_zzo, zzd com_google_android_gms_ads_internal_zzd) {
        this.zzoq = false;
        this.zzos = com_google_android_gms_ads_internal_zzq;
        if (com_google_android_gms_ads_internal_zzo == null) {
            com_google_android_gms_ads_internal_zzo = new zzo(this);
        }
        this.zzor = com_google_android_gms_ads_internal_zzo;
        this.zzov = com_google_android_gms_ads_internal_zzd;
        zzp.zzbx().zzJ(this.zzos.context);
        zzp.zzbA().zzb(this.zzos.context, this.zzos.zzqb);
        this.zzou = zzp.zzbA().zzgh();
    }

    private boolean zzaR() {
        zzb.zzaD("Ad leaving application.");
        if (this.zzos.zzqk == null) {
            return false;
        }
        try {
            this.zzos.zzqk.onAdLeftApplication();
            return true;
        } catch (Throwable e) {
            zzb.zzd("Could not call AdListener.onAdLeftApplication().", e);
            return false;
        }
    }

    public void destroy() {
        zzx.zzch("destroy must be called on the main UI thread.");
        this.zzor.cancel();
        this.zzou.zzf(this.zzos.zzqg);
        this.zzos.destroy();
    }

    public boolean isLoading() {
        return this.zzoq;
    }

    public boolean isReady() {
        zzx.zzch("isLoaded must be called on the main UI thread.");
        return this.zzos.zzqd == null && this.zzos.zzqe == null && this.zzos.zzqg != null;
    }

    public void onAdClicked() {
        if (this.zzos.zzqg == null) {
            zzb.zzaE("Ad state was null when trying to ping click URLs.");
            return;
        }
        zzb.zzaC("Pinging click URLs.");
        this.zzos.zzqi.zzfU();
        if (this.zzos.zzqg.zzyw != null) {
            zzp.zzbx().zza(this.zzos.context, this.zzos.zzqb.zzIz, zzp.zzbx().zza(this.zzos.context, this.zzos.zzqg.zzyw, this.zzos.zzqg.zzDO));
        }
        if (this.zzos.zzqj != null) {
            try {
                this.zzos.zzqj.onAdClicked();
            } catch (Throwable e) {
                zzb.zzd("Could not notify onAdClicked event.", e);
            }
        }
    }

    public void onAppEvent(String name, String info) {
        if (this.zzos.zzql != null) {
            try {
                this.zzos.zzql.onAppEvent(name, info);
            } catch (Throwable e) {
                zzb.zzd("Could not call the AppEventListener.", e);
            }
        }
    }

    public void pause() {
        zzx.zzch("pause must be called on the main UI thread.");
    }

    protected void recordImpression() {
        zzc(this.zzos.zzqg);
    }

    public void resume() {
        zzx.zzch("resume must be called on the main UI thread.");
    }

    public void setManualImpressionsEnabled(boolean enabled) {
        throw new UnsupportedOperationException("Attempt to call setManualImpressionsEnabled for an unsupported ad type.");
    }

    public void stopLoading() {
        zzx.zzch("stopLoading must be called on the main UI thread.");
        this.zzoq = false;
        this.zzos.zzf(true);
    }

    Bundle zza(zzbk com_google_android_gms_internal_zzbk) {
        if (com_google_android_gms_internal_zzbk == null) {
            return null;
        }
        String zzcm;
        if (com_google_android_gms_internal_zzbk.zzcx()) {
            com_google_android_gms_internal_zzbk.wakeup();
        }
        zzbh zzcv = com_google_android_gms_internal_zzbk.zzcv();
        if (zzcv != null) {
            zzcm = zzcv.zzcm();
            zzb.zzaC("In AdManger: loadAd, " + zzcv.toString());
        } else {
            zzcm = null;
        }
        if (zzcm == null) {
            return null;
        }
        Bundle bundle = new Bundle(1);
        bundle.putString("fingerprint", zzcm);
        bundle.putInt("v", 1);
        return bundle;
    }

    public void zza(AdSizeParcel adSizeParcel) {
        zzx.zzch("setAdSize must be called on the main UI thread.");
        this.zzos.zzqf = adSizeParcel;
        if (!(this.zzos.zzqg == null || this.zzos.zzqg.zzAR == null || this.zzos.zzqz != 0)) {
            this.zzos.zzqg.zzAR.zza(adSizeParcel);
        }
        if (this.zzos.zzqc != null) {
            if (this.zzos.zzqc.getChildCount() > 1) {
                this.zzos.zzqc.removeView(this.zzos.zzqc.getNextView());
            }
            this.zzos.zzqc.setMinimumWidth(adSizeParcel.widthPixels);
            this.zzos.zzqc.setMinimumHeight(adSizeParcel.heightPixels);
            this.zzos.zzqc.requestLayout();
        }
    }

    public void zza(zzm com_google_android_gms_ads_internal_client_zzm) {
        zzx.zzch("setAdListener must be called on the main UI thread.");
        this.zzos.zzqj = com_google_android_gms_ads_internal_client_zzm;
    }

    public void zza(com.google.android.gms.ads.internal.client.zzn com_google_android_gms_ads_internal_client_zzn) {
        zzx.zzch("setAdListener must be called on the main UI thread.");
        this.zzos.zzqk = com_google_android_gms_ads_internal_client_zzn;
    }

    public void zza(zzt com_google_android_gms_ads_internal_client_zzt) {
        zzx.zzch("setAppEventListener must be called on the main UI thread.");
        this.zzos.zzql = com_google_android_gms_ads_internal_client_zzt;
    }

    public void zza(zzu com_google_android_gms_ads_internal_client_zzu) {
        zzx.zzch("setCorrelationIdProvider must be called on the main UI thread");
        this.zzos.zzqm = com_google_android_gms_ads_internal_client_zzu;
    }

    public void zza(zzch com_google_android_gms_internal_zzch) {
        throw new IllegalStateException("setOnCustomRenderedAdLoadedListener is not supported for current ad type");
    }

    public void zza(zzfm com_google_android_gms_internal_zzfm) {
        throw new IllegalStateException("setInAppPurchaseListener is not supported for current ad type");
    }

    public void zza(zzfq com_google_android_gms_internal_zzfq, String str) {
        throw new IllegalStateException("setPlayStorePurchaseParams is not supported for current ad type");
    }

    public void zza(com.google.android.gms.internal.zzhj.zza com_google_android_gms_internal_zzhj_zza) {
        if (!(com_google_android_gms_internal_zzhj_zza.zzGM.zzEb == -1 || TextUtils.isEmpty(com_google_android_gms_internal_zzhj_zza.zzGM.zzEl))) {
            long zzo = zzo(com_google_android_gms_internal_zzhj_zza.zzGM.zzEl);
            if (zzo != -1) {
                zzcc zzb = this.zzon.zzb(zzo + com_google_android_gms_internal_zzhj_zza.zzGM.zzEb);
                this.zzon.zza(zzb, "stc");
            }
        }
        this.zzon.zzR(com_google_android_gms_internal_zzhj_zza.zzGM.zzEl);
        this.zzon.zza(this.zzoo, "arf");
        this.zzop = this.zzon.zzdl();
        this.zzos.zzqd = null;
        this.zzos.zzqh = com_google_android_gms_internal_zzhj_zza;
        zza(com_google_android_gms_internal_zzhj_zza, this.zzon);
    }

    protected abstract void zza(com.google.android.gms.internal.zzhj.zza com_google_android_gms_internal_zzhj_zza, zzcd com_google_android_gms_internal_zzcd);

    public void zza(HashSet<zzhk> hashSet) {
        this.zzos.zza(hashSet);
    }

    public boolean zza(AdRequestParcel adRequestParcel) {
        zzx.zzch("loadAd must be called on the main UI thread.");
        if (this.zzos.zzqd == null && this.zzos.zzqe == null) {
            zzb.zzaD("Starting ad request.");
            this.zzoq = true;
            zzaL();
            this.zzoo = this.zzon.zzdl();
            if (!adRequestParcel.zzst) {
                zzb.zzaD("Use AdRequest.Builder.addTestDevice(\"" + zzk.zzcE().zzQ(this.zzos.context) + "\") to get test ads on this device.");
            }
            return zza(adRequestParcel, this.zzon);
        }
        if (this.zzot != null) {
            zzb.zzaE("Aborting last ad request since another ad request is already in progress. The current request object will still be cached for future refreshes.");
        }
        this.zzot = adRequestParcel;
        return false;
    }

    protected abstract boolean zza(AdRequestParcel adRequestParcel, zzcd com_google_android_gms_internal_zzcd);

    boolean zza(zzhj com_google_android_gms_internal_zzhj) {
        return false;
    }

    protected abstract boolean zza(zzhj com_google_android_gms_internal_zzhj, zzhj com_google_android_gms_internal_zzhj2);

    void zzaL() {
        this.zzon = new zzcd(((Boolean) zzby.zzuB.get()).booleanValue(), "load_ad", this.zzos.zzqf.zzsG);
        this.zzoo = new zzcc(-1, null, null);
        this.zzop = new zzcc(-1, null, null);
    }

    public zzd zzaM() {
        zzx.zzch("getAdFrame must be called on the main UI thread.");
        return zze.zzx(this.zzos.zzqc);
    }

    public AdSizeParcel zzaN() {
        zzx.zzch("getAdSize must be called on the main UI thread.");
        return this.zzos.zzqf;
    }

    public void zzaO() {
        zzaR();
    }

    public void zzaP() {
        zzx.zzch("recordManualImpression must be called on the main UI thread.");
        if (this.zzos.zzqg == null) {
            zzb.zzaE("Ad state was null when trying to ping manual tracking URLs.");
            return;
        }
        zzb.zzaC("Pinging manual tracking URLs.");
        if (this.zzos.zzqg.zzDZ != null) {
            zzp.zzbx().zza(this.zzos.context, this.zzos.zzqb.zzIz, zzp.zzbx().zza(this.zzos.context, this.zzos.zzqg.zzDZ, this.zzos.zzqg.zzDO));
        }
    }

    protected boolean zzaQ() {
        zzb.m1445v("Ad closing.");
        if (this.zzos.zzqk == null) {
            return false;
        }
        try {
            this.zzos.zzqk.onAdClosed();
            return true;
        } catch (Throwable e) {
            zzb.zzd("Could not call AdListener.onAdClosed().", e);
            return false;
        }
    }

    protected boolean zzaS() {
        zzb.zzaD("Ad opening.");
        if (this.zzos.zzqk == null) {
            return false;
        }
        try {
            this.zzos.zzqk.onAdOpened();
            return true;
        } catch (Throwable e) {
            zzb.zzd("Could not call AdListener.onAdOpened().", e);
            return false;
        }
    }

    protected boolean zzaT() {
        zzb.zzaD("Ad finished loading.");
        this.zzoq = false;
        if (this.zzos.zzqk == null) {
            return false;
        }
        try {
            this.zzos.zzqk.onAdLoaded();
            return true;
        } catch (Throwable e) {
            zzb.zzd("Could not call AdListener.onAdLoaded().", e);
            return false;
        }
    }

    protected void zzb(View view) {
        this.zzos.zzqc.addView(view, zzp.zzbz().zzgy());
    }

    public void zzb(zzhj com_google_android_gms_internal_zzhj) {
        this.zzon.zza(this.zzop, "awr");
        this.zzos.zzqe = null;
        if (!(com_google_android_gms_internal_zzhj.errorCode == -2 || com_google_android_gms_internal_zzhj.errorCode == 3)) {
            zzp.zzbA().zzb(this.zzos.zzbL());
        }
        if (com_google_android_gms_internal_zzhj.errorCode == -1) {
            this.zzoq = false;
            return;
        }
        if (zza(com_google_android_gms_internal_zzhj)) {
            zzb.zzaC("Ad refresh scheduled.");
        }
        if (com_google_android_gms_internal_zzhj.errorCode != -2) {
            zze(com_google_android_gms_internal_zzhj.errorCode);
            return;
        }
        if (this.zzos.zzqx == null) {
            this.zzos.zzqx = new zzho(this.zzos.zzpZ);
        }
        this.zzou.zze(this.zzos.zzqg);
        if (zza(this.zzos.zzqg, com_google_android_gms_internal_zzhj)) {
            this.zzos.zzqg = com_google_android_gms_internal_zzhj;
            this.zzos.zzbS();
            this.zzon.zzd("is_mraid", this.zzos.zzqg.zzbY() ? "1" : "0");
            this.zzon.zzd("is_mediation", this.zzos.zzqg.zzDX ? "1" : "0");
            if (!(this.zzos.zzqg.zzAR == null || this.zzos.zzqg.zzAR.zzgS() == null)) {
                this.zzon.zzd("is_video", this.zzos.zzqg.zzAR.zzgS().zzhc() ? "1" : "0");
            }
            this.zzon.zza(this.zzoo, "ttc");
            if (zzp.zzbA().zzgc() != null) {
                zzp.zzbA().zzgc().zza(this.zzon);
            }
            if (this.zzos.zzbP()) {
                zzaT();
            }
        }
    }

    protected boolean zzb(AdRequestParcel adRequestParcel) {
        ViewParent parent = this.zzos.zzqc.getParent();
        return (parent instanceof View) && ((View) parent).isShown() && zzp.zzbx().zzgq();
    }

    public void zzc(AdRequestParcel adRequestParcel) {
        if (zzb(adRequestParcel)) {
            zza(adRequestParcel);
            return;
        }
        zzb.zzaD("Ad is not visible. Not refreshing ad.");
        this.zzor.zzf(adRequestParcel);
    }

    protected void zzc(zzhj com_google_android_gms_internal_zzhj) {
        if (com_google_android_gms_internal_zzhj == null) {
            zzb.zzaE("Ad state was null when trying to ping impression URLs.");
            return;
        }
        zzb.zzaC("Pinging Impression URLs.");
        this.zzos.zzqi.zzfT();
        if (com_google_android_gms_internal_zzhj.zzyx != null) {
            zzp.zzbx().zza(this.zzos.context, this.zzos.zzqb.zzIz, zzp.zzbx().zza(this.zzos.context, com_google_android_gms_internal_zzhj.zzyx, com_google_android_gms_internal_zzhj.zzDO));
        }
    }

    protected boolean zze(int i) {
        zzb.zzaE("Failed to load ad: " + i);
        this.zzoq = false;
        if (this.zzos.zzqk == null) {
            return false;
        }
        try {
            this.zzos.zzqk.onAdFailedToLoad(i);
            return true;
        } catch (Throwable e) {
            zzb.zzd("Could not call AdListener.onAdFailedToLoad().", e);
            return false;
        }
    }

    long zzo(String str) {
        int indexOf = str.indexOf("ufe");
        int indexOf2 = str.indexOf(44, indexOf);
        if (indexOf2 == -1) {
            indexOf2 = str.length();
        }
        try {
            return Long.parseLong(str.substring(indexOf + 4, indexOf2));
        } catch (IndexOutOfBoundsException e) {
            zzb.zzaE("Invalid index for Url fetch time in CSI latency info.");
            return -1;
        } catch (NumberFormatException e2) {
            zzb.zzaE("Cannot find valid format of Url fetch time in CSI latency info.");
            return -1;
        }
    }
}
