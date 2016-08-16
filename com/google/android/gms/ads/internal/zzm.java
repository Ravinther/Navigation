package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.ads.internal.client.MobileAdsSettingsParcel;
import com.google.android.gms.ads.internal.client.zzv.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.purchase.InAppPurchaseActivity;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzoj;
import com.google.android.gms.internal.zzoq;
import com.google.android.gms.internal.zzqf;
import com.google.android.gms.internal.zzqg;
import java.util.regex.Pattern;

@zzgk
public class zzm extends zza implements zzoj.zza, zzqg.zza {
    private static final Object zzpm;
    private static zzm zzpn;
    private final Context mContext;
    zzqf zzpo;
    String zzpp;
    String zzpq;
    private boolean zzpr;
    private boolean zzps;

    static {
        zzpm = new Object();
    }

    zzm(Context context) {
        this.mContext = context;
        this.zzpr = false;
    }

    public static zzm zzq(Context context) {
        zzm com_google_android_gms_ads_internal_zzm;
        synchronized (zzpm) {
            if (zzpn == null) {
                zzpn = new zzm(context.getApplicationContext());
            }
            com_google_android_gms_ads_internal_zzm = zzpn;
        }
        return com_google_android_gms_ads_internal_zzm;
    }

    public String getClientId() {
        String clientId;
        synchronized (zzpm) {
            if (this.zzps) {
                clientId = GoogleAnalytics.getInstance(this.mContext).getClientId();
            } else {
                clientId = null;
            }
        }
        return clientId;
    }

    public void zza(zzoq com_google_android_gms_internal_zzoq) {
    }

    public void zza(zzoq com_google_android_gms_internal_zzoq, Activity activity) {
        if (com_google_android_gms_internal_zzoq != null && activity != null) {
            if (activity instanceof AdActivity) {
                int zzk = zzp.zzbx().zzk(activity);
                if (zzk == 1) {
                    com_google_android_gms_internal_zzoq.zzam(true);
                    com_google_android_gms_internal_zzoq.setScreenName("Interstitial Ad");
                } else if (zzk == 2 || zzk == 3) {
                    com_google_android_gms_internal_zzoq.setScreenName("Expanded Ad");
                } else {
                    com_google_android_gms_internal_zzoq.setScreenName(null);
                }
            } else if (activity instanceof InAppPurchaseActivity) {
                com_google_android_gms_internal_zzoq.setScreenName(null);
            }
        }
    }

    public void zza(String str, MobileAdsSettingsParcel mobileAdsSettingsParcel) {
        synchronized (zzpm) {
            if (this.zzpr) {
                zzb.zzaE("Mobile ads is initialized already.");
            } else if (this.mContext == null) {
                zzb.zzaE("Fail to initialize mobile ads because context is null.");
            } else if (TextUtils.isEmpty(str)) {
                zzb.zzaE("Fail to initialize mobile ads because ApplicationCode is empty.");
            } else {
                this.zzpr = true;
                zzb(str, mobileAdsSettingsParcel);
            }
        }
    }

    void zzb(String str, MobileAdsSettingsParcel mobileAdsSettingsParcel) {
        if (mobileAdsSettingsParcel != null && mobileAdsSettingsParcel.zzty) {
            if (!zzp.zzbx().zza(this.mContext.getPackageManager(), this.mContext.getPackageName(), "android.permission.INTERNET")) {
                zzb.m1444e("Missing permission android.permission.INTERNET");
            } else if (!zzp.zzbx().zza(this.mContext.getPackageManager(), this.mContext.getPackageName(), "android.permission.ACCESS_NETWORK_STATE")) {
                zzb.m1444e("Missing permission android.permission.ACCESS_NETWORK_STATE");
            } else if (Pattern.matches("ca-app-[a-z0-9_-]+~[a-z0-9_-]+", str)) {
                this.zzps = true;
                this.zzpp = str;
                this.zzpq = mobileAdsSettingsParcel.zztz;
                zzqg zzaR = zzqg.zzaR(this.mContext);
                zzqf.zza com_google_android_gms_internal_zzqf_zza = new zzqf.zza(this.zzpp);
                if (!TextUtils.isEmpty(this.zzpq)) {
                    com_google_android_gms_internal_zzqf_zza.zzfh(this.zzpq);
                }
                zzaR.zza(com_google_android_gms_internal_zzqf_zza.zzBm());
                zzaR.zza((zzqg.zza) this);
                zzoj.zzaJ(this.mContext).zza(this);
                zzaR.start();
            } else {
                throw new IllegalArgumentException("Please provide a valid application code");
            }
        }
    }

    public boolean zzbn() {
        boolean z;
        synchronized (zzpm) {
            z = this.zzps;
        }
        return z;
    }

    public void zzbo() {
        this.zzpo = zzqg.zzaR(this.mContext).zzBn();
    }

    public int zzbp() {
        int i = -1;
        synchronized (zzpm) {
            if (this.zzps) {
                zzoq zzxw = zzoj.zzaJ(this.mContext).zzxw();
                if (zzxw != null) {
                    i = zzxw.zzbp();
                }
            }
        }
        return i;
    }
}
