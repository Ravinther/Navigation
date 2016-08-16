package com.google.android.gms.ads.internal;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzcd;
import com.google.android.gms.internal.zzce;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzch;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzfc;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhj;
import com.google.android.gms.internal.zzhj.zza;
import com.google.android.gms.internal.zzhu;
import com.google.android.gms.internal.zzip;

@zzgk
public abstract class zzc extends zzb implements zzg, zzfc {

    /* renamed from: com.google.android.gms.ads.internal.zzc.1 */
    class C05971 implements Runnable {
        final /* synthetic */ zza zzoA;
        final /* synthetic */ zzc zzoB;

        C05971(zzc com_google_android_gms_ads_internal_zzc, zza com_google_android_gms_internal_zzhj_zza) {
            this.zzoB = com_google_android_gms_ads_internal_zzc;
            this.zzoA = com_google_android_gms_internal_zzhj_zza;
        }

        public void run() {
            this.zzoB.zzb(new zzhj(this.zzoA, null, null, null, null, null, null));
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzc.2 */
    class C06002 implements Runnable {
        final /* synthetic */ zza zzoA;
        final /* synthetic */ zzc zzoB;
        final /* synthetic */ zzcd zzoC;

        /* renamed from: com.google.android.gms.ads.internal.zzc.2.1 */
        class C05981 implements OnTouchListener {
            final /* synthetic */ zze zzoD;
            final /* synthetic */ C06002 zzoE;

            C05981(C06002 c06002, zze com_google_android_gms_ads_internal_zze) {
                this.zzoE = c06002;
                this.zzoD = com_google_android_gms_ads_internal_zze;
            }

            public boolean onTouch(View v, MotionEvent event) {
                this.zzoD.recordClick();
                return false;
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.zzc.2.2 */
        class C05992 implements OnClickListener {
            final /* synthetic */ zze zzoD;
            final /* synthetic */ C06002 zzoE;

            C05992(C06002 c06002, zze com_google_android_gms_ads_internal_zze) {
                this.zzoE = c06002;
                this.zzoD = com_google_android_gms_ads_internal_zze;
            }

            public void onClick(View v) {
                this.zzoD.recordClick();
            }
        }

        C06002(zzc com_google_android_gms_ads_internal_zzc, zza com_google_android_gms_internal_zzhj_zza, zzcd com_google_android_gms_internal_zzcd) {
            this.zzoB = com_google_android_gms_ads_internal_zzc;
            this.zzoA = com_google_android_gms_internal_zzhj_zza;
            this.zzoC = com_google_android_gms_internal_zzcd;
        }

        public void run() {
            if (this.zzoA.zzGM.zzEg && this.zzoB.zzos.zzqu != null) {
                String str = null;
                if (this.zzoA.zzGM.zzAT != null) {
                    str = zzp.zzbx().zzaw(this.zzoA.zzGM.zzAT);
                }
                zzcg com_google_android_gms_internal_zzce = new zzce(this.zzoB, str, this.zzoA.zzGM.body);
                this.zzoB.zzos.zzqz = 1;
                try {
                    this.zzoB.zzos.zzqu.zza(com_google_android_gms_internal_zzce);
                    return;
                } catch (Throwable e) {
                    zzb.zzd("Could not call the onCustomRenderedAdLoadedListener.", e);
                }
            }
            zze com_google_android_gms_ads_internal_zze = new zze();
            zzip zza = this.zzoB.zza(this.zzoA, com_google_android_gms_ads_internal_zze);
            com_google_android_gms_ads_internal_zze.zza(new zze.zzb(this.zzoA, zza));
            zza.setOnTouchListener(new C05981(this, com_google_android_gms_ads_internal_zze));
            zza.setOnClickListener(new C05992(this, com_google_android_gms_ads_internal_zze));
            this.zzoB.zzos.zzqz = 0;
            this.zzoB.zzos.zzqe = zzp.zzbw().zza(this.zzoB.zzos.context, this.zzoB, this.zzoA, this.zzoB.zzos.zzqa, zza, this.zzoB.zzow, this.zzoB, this.zzoC);
        }
    }

    public zzc(Context context, AdSizeParcel adSizeParcel, String str, zzeh com_google_android_gms_internal_zzeh, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzeh, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
    }

    public void recordClick() {
        onAdClicked();
    }

    public void recordImpression() {
        zza(this.zzos.zzqg, false);
    }

    protected zzip zza(zza com_google_android_gms_internal_zzhj_zza, zze com_google_android_gms_ads_internal_zze) {
        zzip com_google_android_gms_internal_zzip;
        View nextView = this.zzos.zzqc.getNextView();
        zzip com_google_android_gms_internal_zzip2;
        if (nextView instanceof zzip) {
            zzb.zzaC("Reusing webview...");
            com_google_android_gms_internal_zzip2 = (zzip) nextView;
            com_google_android_gms_internal_zzip2.zza(this.zzos.context, this.zzos.zzqf);
            com_google_android_gms_internal_zzip = com_google_android_gms_internal_zzip2;
        } else {
            if (nextView != null) {
                this.zzos.zzqc.removeView(nextView);
            }
            com_google_android_gms_internal_zzip2 = zzp.zzby().zza(this.zzos.context, this.zzos.zzqf, false, false, this.zzos.zzqa, this.zzos.zzqb, this.zzov);
            if (this.zzos.zzqf.zzsI == null) {
                zzb(com_google_android_gms_internal_zzip2.getWebView());
            }
            com_google_android_gms_internal_zzip = com_google_android_gms_internal_zzip2;
        }
        com_google_android_gms_internal_zzip.zzgS().zzb(this, this, this, this, false, this, null, com_google_android_gms_ads_internal_zze, this);
        com_google_android_gms_internal_zzip.zzaG(com_google_android_gms_internal_zzhj_zza.zzGL.zzDQ);
        com_google_android_gms_internal_zzip.zzaH(com_google_android_gms_internal_zzhj_zza.zzGL.zzDO);
        return com_google_android_gms_internal_zzip;
    }

    public void zza(int i, int i2, int i3, int i4) {
        zzaS();
    }

    public void zza(zzch com_google_android_gms_internal_zzch) {
        zzx.zzch("setOnCustomRenderedAdLoadedListener must be called on the main UI thread.");
        this.zzos.zzqu = com_google_android_gms_internal_zzch;
    }

    protected void zza(zza com_google_android_gms_internal_zzhj_zza, zzcd com_google_android_gms_internal_zzcd) {
        if (com_google_android_gms_internal_zzhj_zza.errorCode != -2) {
            zzhu.zzHK.post(new C05971(this, com_google_android_gms_internal_zzhj_zza));
            return;
        }
        if (com_google_android_gms_internal_zzhj_zza.zzqf != null) {
            this.zzos.zzqf = com_google_android_gms_internal_zzhj_zza.zzqf;
        }
        if (com_google_android_gms_internal_zzhj_zza.zzGM.zzDX) {
            this.zzos.zzqz = 0;
            this.zzos.zzqe = zzp.zzbw().zza(this.zzos.context, this, com_google_android_gms_internal_zzhj_zza, this.zzos.zzqa, null, this.zzow, this, com_google_android_gms_internal_zzcd);
            return;
        }
        zzhu.zzHK.post(new C06002(this, com_google_android_gms_internal_zzhj_zza, com_google_android_gms_internal_zzcd));
    }

    protected boolean zza(zzhj com_google_android_gms_internal_zzhj, zzhj com_google_android_gms_internal_zzhj2) {
        if (this.zzos.zzbP() && this.zzos.zzqc != null) {
            this.zzos.zzqc.zzbT().zzaz(com_google_android_gms_internal_zzhj2.zzEc);
        }
        return super.zza(com_google_android_gms_internal_zzhj, com_google_android_gms_internal_zzhj2);
    }

    public void zzbc() {
        zzaQ();
    }

    public void zzc(View view) {
        this.zzos.zzqy = view;
        zzb(new zzhj(this.zzos.zzqh, null, null, null, null, null, null));
    }
}
