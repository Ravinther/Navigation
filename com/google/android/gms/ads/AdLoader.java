package com.google.android.gms.ads;

import android.content.Context;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;
import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.ads.internal.client.zzc;
import com.google.android.gms.ads.internal.client.zzd;
import com.google.android.gms.ads.internal.client.zzg;
import com.google.android.gms.ads.internal.client.zzo;
import com.google.android.gms.ads.internal.client.zzp;
import com.google.android.gms.ads.internal.client.zzx;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzcy;
import com.google.android.gms.internal.zzcz;
import com.google.android.gms.internal.zzeg;

public class AdLoader {
    private final Context mContext;
    private final zzg zznK;
    private final zzo zznL;

    public static class Builder {
        private final Context mContext;
        private final zzp zznM;

        Builder(Context context, zzp builder) {
            this.mContext = context;
            this.zznM = builder;
        }

        public Builder(Context context, String adUnitID) {
            this(context, zzd.zza(context, adUnitID, new zzeg()));
        }

        public AdLoader build() {
            try {
                return new AdLoader(this.mContext, this.zznM.zzbk());
            } catch (Throwable e) {
                zzb.zzb("Failed to build AdLoader.", e);
                return null;
            }
        }

        public Builder forAppInstallAd(OnAppInstallAdLoadedListener listener) {
            try {
                this.zznM.zza(new zzcy(listener));
            } catch (Throwable e) {
                zzb.zzd("Failed to add app install ad listener", e);
            }
            return this;
        }

        public Builder forContentAd(OnContentAdLoadedListener listener) {
            try {
                this.zznM.zza(new zzcz(listener));
            } catch (Throwable e) {
                zzb.zzd("Failed to add content ad listener", e);
            }
            return this;
        }

        public Builder withAdListener(AdListener listener) {
            try {
                this.zznM.zzb(new zzc(listener));
            } catch (Throwable e) {
                zzb.zzd("Failed to set AdListener.", e);
            }
            return this;
        }

        public Builder withNativeAdOptions(NativeAdOptions options) {
            try {
                this.zznM.zza(new NativeAdOptionsParcel(options));
            } catch (Throwable e) {
                zzb.zzd("Failed to specify native ad options", e);
            }
            return this;
        }
    }

    AdLoader(Context context, zzo adLoader) {
        this(context, adLoader, zzg.zzcA());
    }

    AdLoader(Context context, zzo adLoader, zzg parcelFactory) {
        this.mContext = context;
        this.zznL = adLoader;
        this.zznK = parcelFactory;
    }

    private void zza(zzx com_google_android_gms_ads_internal_client_zzx) {
        try {
            this.zznL.zze(this.zznK.zza(this.mContext, com_google_android_gms_ads_internal_client_zzx));
        } catch (Throwable e) {
            zzb.zzb("Failed to load ad.", e);
        }
    }

    public void loadAd(AdRequest adRequest) {
        zza(adRequest.zzaF());
    }
}
