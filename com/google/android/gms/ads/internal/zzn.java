package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.formats.zzd;
import com.google.android.gms.ads.internal.formats.zze;
import com.google.android.gms.ads.internal.formats.zzf;
import com.google.android.gms.ads.internal.formats.zzg;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzcd;
import com.google.android.gms.internal.zzch;
import com.google.android.gms.internal.zzct;
import com.google.android.gms.internal.zzcu;
import com.google.android.gms.internal.zzcv;
import com.google.android.gms.internal.zzcw;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzek;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzfm;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhj;
import com.google.android.gms.internal.zzhj.zza;
import com.google.android.gms.internal.zzhu;
import com.google.android.gms.internal.zzlh;
import java.util.List;

@zzgk
public class zzn extends zzb {

    /* renamed from: com.google.android.gms.ads.internal.zzn.1 */
    class C06031 implements Runnable {
        final /* synthetic */ zza zzoA;
        final /* synthetic */ zzn zzpt;

        C06031(zzn com_google_android_gms_ads_internal_zzn, zza com_google_android_gms_internal_zzhj_zza) {
            this.zzpt = com_google_android_gms_ads_internal_zzn;
            this.zzoA = com_google_android_gms_internal_zzhj_zza;
        }

        public void run() {
            this.zzpt.zzb(new zzhj(this.zzoA, null, null, null, null, null, null));
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzn.2 */
    class C06042 implements Runnable {
        final /* synthetic */ zzn zzpt;
        final /* synthetic */ zzd zzpu;

        C06042(zzn com_google_android_gms_ads_internal_zzn, zzd com_google_android_gms_ads_internal_formats_zzd) {
            this.zzpt = com_google_android_gms_ads_internal_zzn;
            this.zzpu = com_google_android_gms_ads_internal_formats_zzd;
        }

        public void run() {
            try {
                this.zzpt.zzos.zzqp.zza(this.zzpu);
            } catch (Throwable e) {
                zzb.zzd("Could not call OnAppInstallAdLoadedListener.onAppInstallAdLoaded().", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzn.3 */
    class C06053 implements Runnable {
        final /* synthetic */ zzn zzpt;
        final /* synthetic */ zze zzpv;

        C06053(zzn com_google_android_gms_ads_internal_zzn, zze com_google_android_gms_ads_internal_formats_zze) {
            this.zzpt = com_google_android_gms_ads_internal_zzn;
            this.zzpv = com_google_android_gms_ads_internal_formats_zze;
        }

        public void run() {
            try {
                this.zzpt.zzos.zzqq.zza(this.zzpv);
            } catch (Throwable e) {
                zzb.zzd("Could not call OnContentAdLoadedListener.onContentAdLoaded().", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzn.4 */
    class C06064 implements Runnable {
        final /* synthetic */ zzn zzpt;
        final /* synthetic */ String zzpw;
        final /* synthetic */ zzhj zzpx;

        C06064(zzn com_google_android_gms_ads_internal_zzn, String str, zzhj com_google_android_gms_internal_zzhj) {
            this.zzpt = com_google_android_gms_ads_internal_zzn;
            this.zzpw = str;
            this.zzpx = com_google_android_gms_internal_zzhj;
        }

        public void run() {
            try {
                ((zzcw) this.zzpt.zzos.zzqs.get(this.zzpw)).zza((zzf) this.zzpx.zzGK);
            } catch (Throwable e) {
                zzb.zzd("Could not call onCustomTemplateAdLoadedListener.onCustomTemplateAdLoaded().", e);
            }
        }
    }

    public zzn(Context context, AdSizeParcel adSizeParcel, String str, zzeh com_google_android_gms_internal_zzeh, VersionInfoParcel versionInfoParcel) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzeh, versionInfoParcel, null);
    }

    private static zzd zza(zzek com_google_android_gms_internal_zzek) throws RemoteException {
        return new zzd(com_google_android_gms_internal_zzek.getHeadline(), com_google_android_gms_internal_zzek.getImages(), com_google_android_gms_internal_zzek.getBody(), com_google_android_gms_internal_zzek.zzds() != null ? com_google_android_gms_internal_zzek.zzds() : null, com_google_android_gms_internal_zzek.getCallToAction(), com_google_android_gms_internal_zzek.getStarRating(), com_google_android_gms_internal_zzek.getStore(), com_google_android_gms_internal_zzek.getPrice(), null, com_google_android_gms_internal_zzek.getExtras());
    }

    private static zze zza(zzel com_google_android_gms_internal_zzel) throws RemoteException {
        return new zze(com_google_android_gms_internal_zzel.getHeadline(), com_google_android_gms_internal_zzel.getImages(), com_google_android_gms_internal_zzel.getBody(), com_google_android_gms_internal_zzel.zzdw() != null ? com_google_android_gms_internal_zzel.zzdw() : null, com_google_android_gms_internal_zzel.getCallToAction(), com_google_android_gms_internal_zzel.getAdvertiser(), null, com_google_android_gms_internal_zzel.getExtras());
    }

    private void zza(zzd com_google_android_gms_ads_internal_formats_zzd) {
        zzhu.zzHK.post(new C06042(this, com_google_android_gms_ads_internal_formats_zzd));
    }

    private void zza(zze com_google_android_gms_ads_internal_formats_zze) {
        zzhu.zzHK.post(new C06053(this, com_google_android_gms_ads_internal_formats_zze));
    }

    private void zza(zzhj com_google_android_gms_internal_zzhj, String str) {
        zzhu.zzHK.post(new C06064(this, str, com_google_android_gms_internal_zzhj));
    }

    public void pause() {
        throw new IllegalStateException("Native Ad DOES NOT support pause().");
    }

    public void recordImpression() {
        zza(this.zzos.zzqg, false);
    }

    public void resume() {
        throw new IllegalStateException("Native Ad DOES NOT support resume().");
    }

    public void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by NativeAdManager.");
    }

    public void zza(zzch com_google_android_gms_internal_zzch) {
        throw new IllegalStateException("CustomRendering is NOT supported by NativeAdManager.");
    }

    public void zza(zzfm com_google_android_gms_internal_zzfm) {
        throw new IllegalStateException("In App Purchase is NOT supported by NativeAdManager.");
    }

    public void zza(zza com_google_android_gms_internal_zzhj_zza, zzcd com_google_android_gms_internal_zzcd) {
        if (com_google_android_gms_internal_zzhj_zza.zzqf != null) {
            this.zzos.zzqf = com_google_android_gms_internal_zzhj_zza.zzqf;
        }
        if (com_google_android_gms_internal_zzhj_zza.errorCode != -2) {
            zzhu.zzHK.post(new C06031(this, com_google_android_gms_internal_zzhj_zza));
            return;
        }
        this.zzos.zzqz = 0;
        this.zzos.zzqe = zzp.zzbw().zza(this.zzos.context, this, com_google_android_gms_internal_zzhj_zza, this.zzos.zzqa, null, this.zzow, this, com_google_android_gms_internal_zzcd);
        zzb.zzaC("AdRenderer: " + this.zzos.zzqe.getClass().getName());
    }

    public void zza(zzlh<String, zzcw> com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcw) {
        zzx.zzch("setOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        this.zzos.zzqs = com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcw;
    }

    public void zza(List<String> list) {
        zzx.zzch("setNativeTemplates must be called on the main UI thread.");
        this.zzos.zzqv = list;
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzhj com_google_android_gms_internal_zzhj, boolean z) {
        return this.zzor.zzbr();
    }

    protected boolean zza(zzhj com_google_android_gms_internal_zzhj, zzhj com_google_android_gms_internal_zzhj2) {
        zza(null);
        if (this.zzos.zzbP()) {
            if (com_google_android_gms_internal_zzhj2.zzDX) {
                try {
                    zzek zzdS = com_google_android_gms_internal_zzhj2.zzyR.zzdS();
                    zzel zzdT = com_google_android_gms_internal_zzhj2.zzyR.zzdT();
                    if (zzdS != null) {
                        zzd zza = zza(zzdS);
                        zza.zza(new zzg(this.zzos.context, this, this.zzos.zzqa, zzdS));
                        zza(zza);
                    } else if (zzdT != null) {
                        zze zza2 = zza(zzdT);
                        zza2.zza(new zzg(this.zzos.context, this, this.zzos.zzqa, zzdT));
                        zza(zza2);
                    } else {
                        zzb.zzaE("No matching mapper for retrieved native ad template.");
                        zze(0);
                        return false;
                    }
                } catch (Throwable e) {
                    zzb.zzd("Failed to get native ad mapper", e);
                }
            } else {
                zzh.zza com_google_android_gms_ads_internal_formats_zzh_zza = com_google_android_gms_internal_zzhj2.zzGK;
                if ((com_google_android_gms_ads_internal_formats_zzh_zza instanceof zze) && this.zzos.zzqq != null) {
                    zza((zze) com_google_android_gms_internal_zzhj2.zzGK);
                } else if ((com_google_android_gms_ads_internal_formats_zzh_zza instanceof zzd) && this.zzos.zzqp != null) {
                    zza((zzd) com_google_android_gms_internal_zzhj2.zzGK);
                } else if (!(com_google_android_gms_ads_internal_formats_zzh_zza instanceof zzf) || this.zzos.zzqs == null || this.zzos.zzqs.get(((zzf) com_google_android_gms_ads_internal_formats_zzh_zza).getCustomTemplateId()) == null) {
                    zzb.zzaE("No matching listener for retrieved native ad template.");
                    zze(0);
                    return false;
                } else {
                    zza(com_google_android_gms_internal_zzhj2, ((zzf) com_google_android_gms_ads_internal_formats_zzh_zza).getCustomTemplateId());
                }
            }
            return super.zza(com_google_android_gms_internal_zzhj, com_google_android_gms_internal_zzhj2);
        }
        throw new IllegalStateException("Native ad DOES NOT have custom rendering mode.");
    }

    public void zzb(NativeAdOptionsParcel nativeAdOptionsParcel) {
        zzx.zzch("setNativeAdOptions must be called on the main UI thread.");
        this.zzos.zzqt = nativeAdOptionsParcel;
    }

    public void zzb(zzct com_google_android_gms_internal_zzct) {
        zzx.zzch("setOnAppInstallAdLoadedListener must be called on the main UI thread.");
        this.zzos.zzqp = com_google_android_gms_internal_zzct;
    }

    public void zzb(zzcu com_google_android_gms_internal_zzcu) {
        zzx.zzch("setOnContentAdLoadedListener must be called on the main UI thread.");
        this.zzos.zzqq = com_google_android_gms_internal_zzcu;
    }

    public void zzb(zzlh<String, zzcv> com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcv) {
        zzx.zzch("setOnCustomClickListener must be called on the main UI thread.");
        this.zzos.zzqr = com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcv;
    }

    public zzlh<String, zzcw> zzbq() {
        zzx.zzch("getOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        return this.zzos.zzqs;
    }

    public zzcv zzr(String str) {
        zzx.zzch("getOnCustomClickListener must be called on the main UI thread.");
        return (zzcv) this.zzos.zzqr.get(str);
    }
}
