package com.google.android.gms.ads.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzn;
import com.google.android.gms.ads.internal.client.zzo.zza;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzct;
import com.google.android.gms.internal.zzcu;
import com.google.android.gms.internal.zzcv;
import com.google.android.gms.internal.zzcw;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhu;
import com.google.android.gms.internal.zzlh;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@zzgk
public class zzi extends zza {
    private final Context mContext;
    private final zzn zzoS;
    private final zzct zzoT;
    private final zzcu zzoU;
    private final zzlh<String, zzcw> zzoV;
    private final zzlh<String, zzcv> zzoW;
    private final NativeAdOptionsParcel zzoX;
    private final List<String> zzoY;
    private final String zzoZ;
    private final zzeh zzow;
    private final VersionInfoParcel zzpa;
    private WeakReference<zzn> zzpb;
    private Object zzpc;

    /* renamed from: com.google.android.gms.ads.internal.zzi.1 */
    class C06021 implements Runnable {
        final /* synthetic */ AdRequestParcel zzpd;
        final /* synthetic */ zzi zzpe;

        C06021(zzi com_google_android_gms_ads_internal_zzi, AdRequestParcel adRequestParcel) {
            this.zzpe = com_google_android_gms_ads_internal_zzi;
            this.zzpd = adRequestParcel;
        }

        public void run() {
            synchronized (this.zzpe.zzpc) {
                zzn zzbj = this.zzpe.zzbj();
                this.zzpe.zzpb = new WeakReference(zzbj);
                zzbj.zzb(this.zzpe.zzoT);
                zzbj.zzb(this.zzpe.zzoU);
                zzbj.zza(this.zzpe.zzoV);
                zzbj.zza(this.zzpe.zzoS);
                zzbj.zzb(this.zzpe.zzoW);
                zzbj.zza(this.zzpe.zzbi());
                zzbj.zzb(this.zzpe.zzoX);
                zzbj.zza(this.zzpd);
            }
        }
    }

    zzi(Context context, String str, zzeh com_google_android_gms_internal_zzeh, VersionInfoParcel versionInfoParcel, zzn com_google_android_gms_ads_internal_client_zzn, zzct com_google_android_gms_internal_zzct, zzcu com_google_android_gms_internal_zzcu, zzlh<String, zzcw> com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcw, zzlh<String, zzcv> com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcv, NativeAdOptionsParcel nativeAdOptionsParcel) {
        this.zzpc = new Object();
        this.mContext = context;
        this.zzoZ = str;
        this.zzow = com_google_android_gms_internal_zzeh;
        this.zzpa = versionInfoParcel;
        this.zzoS = com_google_android_gms_ads_internal_client_zzn;
        this.zzoU = com_google_android_gms_internal_zzcu;
        this.zzoT = com_google_android_gms_internal_zzct;
        this.zzoV = com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcw;
        this.zzoW = com_google_android_gms_internal_zzlh_java_lang_String__com_google_android_gms_internal_zzcv;
        this.zzoX = nativeAdOptionsParcel;
        this.zzoY = zzbi();
    }

    private List<String> zzbi() {
        List<String> arrayList = new ArrayList();
        if (this.zzoU != null) {
            arrayList.add("1");
        }
        if (this.zzoT != null) {
            arrayList.add("2");
        }
        if (this.zzoV.size() > 0) {
            arrayList.add("3");
        }
        return arrayList;
    }

    public String getMediationAdapterClassName() {
        synchronized (this.zzpc) {
            if (this.zzpb != null) {
                zzn com_google_android_gms_ads_internal_zzn = (zzn) this.zzpb.get();
                String mediationAdapterClassName = com_google_android_gms_ads_internal_zzn != null ? com_google_android_gms_ads_internal_zzn.getMediationAdapterClassName() : null;
                return mediationAdapterClassName;
            }
            return null;
        }
    }

    public boolean isLoading() {
        synchronized (this.zzpc) {
            if (this.zzpb != null) {
                zzn com_google_android_gms_ads_internal_zzn = (zzn) this.zzpb.get();
                boolean isLoading = com_google_android_gms_ads_internal_zzn != null ? com_google_android_gms_ads_internal_zzn.isLoading() : false;
                return isLoading;
            }
            return false;
        }
    }

    protected void runOnUiThread(Runnable runnable) {
        zzhu.zzHK.post(runnable);
    }

    protected zzn zzbj() {
        return new zzn(this.mContext, AdSizeParcel.zzs(this.mContext), this.zzoZ, this.zzow, this.zzpa);
    }

    public void zze(AdRequestParcel adRequestParcel) {
        runOnUiThread(new C06021(this, adRequestParcel));
    }
}
