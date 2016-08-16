package com.google.ads.mediation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.NativeAppInstallAdMapper;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.ads.mediation.NativeMediationAdRequest;
import com.google.android.gms.internal.zzgk;
import java.util.Date;
import java.util.Set;

@zzgk
public abstract class AbstractAdViewAdapter implements MediationBannerAdapter, MediationInterstitialAdapter, com.google.android.gms.ads.mediation.zza {
    protected AdView zzaK;
    protected InterstitialAd zzaL;
    private AdLoader zzaM;

    static class zza extends NativeAppInstallAdMapper {
        private final NativeAppInstallAd zzaN;

        public zza(NativeAppInstallAd nativeAppInstallAd) {
            this.zzaN = nativeAppInstallAd;
            setHeadline(nativeAppInstallAd.getHeadline().toString());
            setImages(nativeAppInstallAd.getImages());
            setBody(nativeAppInstallAd.getBody().toString());
            setIcon(nativeAppInstallAd.getIcon());
            setCallToAction(nativeAppInstallAd.getCallToAction().toString());
            setStarRating(nativeAppInstallAd.getStarRating().doubleValue());
            setStore(nativeAppInstallAd.getStore().toString());
            setPrice(nativeAppInstallAd.getPrice().toString());
            setOverrideImpressionRecording(true);
            setOverrideClickHandling(true);
        }

        public void trackView(View view) {
            if (view instanceof NativeAdView) {
                ((NativeAdView) view).setNativeAd(this.zzaN);
            }
        }
    }

    static class zzb extends NativeContentAdMapper {
        private final NativeContentAd zzaO;

        public zzb(NativeContentAd nativeContentAd) {
            this.zzaO = nativeContentAd;
            setHeadline(nativeContentAd.getHeadline().toString());
            setImages(nativeContentAd.getImages());
            setBody(nativeContentAd.getBody().toString());
            setLogo(nativeContentAd.getLogo());
            setCallToAction(nativeContentAd.getCallToAction().toString());
            setAdvertiser(nativeContentAd.getAdvertiser().toString());
            setOverrideImpressionRecording(true);
            setOverrideClickHandling(true);
        }

        public void trackView(View view) {
            if (view instanceof NativeAdView) {
                ((NativeAdView) view).setNativeAd(this.zzaO);
            }
        }
    }

    static final class zzc extends AdListener implements com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzaP;
        final MediationBannerListener zzaQ;

        public zzc(AbstractAdViewAdapter abstractAdViewAdapter, MediationBannerListener mediationBannerListener) {
            this.zzaP = abstractAdViewAdapter;
            this.zzaQ = mediationBannerListener;
        }

        public void onAdClicked() {
            this.zzaQ.onAdClicked(this.zzaP);
        }

        public void onAdClosed() {
            this.zzaQ.onAdClosed(this.zzaP);
        }

        public void onAdFailedToLoad(int errorCode) {
            this.zzaQ.onAdFailedToLoad(this.zzaP, errorCode);
        }

        public void onAdLeftApplication() {
            this.zzaQ.onAdLeftApplication(this.zzaP);
        }

        public void onAdLoaded() {
            this.zzaQ.onAdLoaded(this.zzaP);
        }

        public void onAdOpened() {
            this.zzaQ.onAdOpened(this.zzaP);
        }
    }

    static final class zzd extends AdListener implements com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzaP;
        final MediationInterstitialListener zzaR;

        public zzd(AbstractAdViewAdapter abstractAdViewAdapter, MediationInterstitialListener mediationInterstitialListener) {
            this.zzaP = abstractAdViewAdapter;
            this.zzaR = mediationInterstitialListener;
        }

        public void onAdClicked() {
            this.zzaR.onAdClicked(this.zzaP);
        }

        public void onAdClosed() {
            this.zzaR.onAdClosed(this.zzaP);
        }

        public void onAdFailedToLoad(int errorCode) {
            this.zzaR.onAdFailedToLoad(this.zzaP, errorCode);
        }

        public void onAdLeftApplication() {
            this.zzaR.onAdLeftApplication(this.zzaP);
        }

        public void onAdLoaded() {
            this.zzaR.onAdLoaded(this.zzaP);
        }

        public void onAdOpened() {
            this.zzaR.onAdOpened(this.zzaP);
        }
    }

    static final class zze extends AdListener implements OnAppInstallAdLoadedListener, OnContentAdLoadedListener, com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzaP;
        final com.google.android.gms.ads.mediation.zzb zzaS;

        public zze(AbstractAdViewAdapter abstractAdViewAdapter, com.google.android.gms.ads.mediation.zzb com_google_android_gms_ads_mediation_zzb) {
            this.zzaP = abstractAdViewAdapter;
            this.zzaS = com_google_android_gms_ads_mediation_zzb;
        }

        public void onAdClicked() {
            this.zzaS.zzd(this.zzaP);
        }

        public void onAdClosed() {
            this.zzaS.zzb(this.zzaP);
        }

        public void onAdFailedToLoad(int errorCode) {
            this.zzaS.zza(this.zzaP, errorCode);
        }

        public void onAdLeftApplication() {
            this.zzaS.zzc(this.zzaP);
        }

        public void onAdLoaded() {
        }

        public void onAdOpened() {
            this.zzaS.zza(this.zzaP);
        }

        public void onAppInstallAdLoaded(NativeAppInstallAd ad) {
            this.zzaS.zza(this.zzaP, new zza(ad));
        }

        public void onContentAdLoaded(NativeContentAd ad) {
            this.zzaS.zza(this.zzaP, new zzb(ad));
        }
    }

    public String getAdUnitId(Bundle serverParameters) {
        return serverParameters.getString("pubid");
    }

    public View getBannerView() {
        return this.zzaK;
    }

    public void onDestroy() {
        if (this.zzaK != null) {
            this.zzaK.destroy();
            this.zzaK = null;
        }
        if (this.zzaL != null) {
            this.zzaL = null;
        }
        if (this.zzaM != null) {
            this.zzaM = null;
        }
    }

    public void onPause() {
        if (this.zzaK != null) {
            this.zzaK.pause();
        }
    }

    public void onResume() {
        if (this.zzaK != null) {
            this.zzaK.resume();
        }
    }

    public void requestBannerAd(Context context, MediationBannerListener bannerListener, Bundle serverParameters, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle extras) {
        this.zzaK = new AdView(context);
        this.zzaK.setAdSize(new AdSize(adSize.getWidth(), adSize.getHeight()));
        this.zzaK.setAdUnitId(getAdUnitId(serverParameters));
        this.zzaK.setAdListener(new zzc(this, bannerListener));
        this.zzaK.loadAd(zza(context, mediationAdRequest, extras, serverParameters));
    }

    public void requestInterstitialAd(Context context, MediationInterstitialListener interstitialListener, Bundle serverParameters, MediationAdRequest mediationAdRequest, Bundle extras) {
        this.zzaL = new InterstitialAd(context);
        this.zzaL.setAdUnitId(getAdUnitId(serverParameters));
        this.zzaL.setAdListener(new zzd(this, interstitialListener));
        this.zzaL.loadAd(zza(context, mediationAdRequest, extras, serverParameters));
    }

    public void requestNativeAd(Context context, com.google.android.gms.ads.mediation.zzb listener, Bundle serverParameters, NativeMediationAdRequest mediationAdRequest, Bundle extras) {
        OnContentAdLoadedListener com_google_ads_mediation_AbstractAdViewAdapter_zze = new zze(this, listener);
        Builder withAdListener = zza(context, serverParameters.getString("pubid")).withAdListener(com_google_ads_mediation_AbstractAdViewAdapter_zze);
        NativeAdOptions nativeAdOptions = mediationAdRequest.getNativeAdOptions();
        if (nativeAdOptions != null) {
            withAdListener.withNativeAdOptions(nativeAdOptions);
        }
        if (mediationAdRequest.isAppInstallAdRequested()) {
            withAdListener.forAppInstallAd(com_google_ads_mediation_AbstractAdViewAdapter_zze);
        }
        if (mediationAdRequest.isContentAdRequested()) {
            withAdListener.forContentAd(com_google_ads_mediation_AbstractAdViewAdapter_zze);
        }
        this.zzaM = withAdListener.build();
        this.zzaM.loadAd(zza(context, mediationAdRequest, extras, serverParameters));
    }

    public void showInterstitial() {
        this.zzaL.show();
    }

    protected abstract Bundle zza(Bundle bundle, Bundle bundle2);

    Builder zza(Context context, String str) {
        return new Builder(context, str);
    }

    AdRequest zza(Context context, MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle2) {
        AdRequest.Builder builder = new AdRequest.Builder();
        Date birthday = mediationAdRequest.getBirthday();
        if (birthday != null) {
            builder.setBirthday(birthday);
        }
        int gender = mediationAdRequest.getGender();
        if (gender != 0) {
            builder.setGender(gender);
        }
        Set<String> keywords = mediationAdRequest.getKeywords();
        if (keywords != null) {
            for (String addKeyword : keywords) {
                builder.addKeyword(addKeyword);
            }
        }
        Location location = mediationAdRequest.getLocation();
        if (location != null) {
            builder.setLocation(location);
        }
        if (mediationAdRequest.isTesting()) {
            builder.addTestDevice(zzk.zzcE().zzQ(context));
        }
        if (mediationAdRequest.taggedForChildDirectedTreatment() != -1) {
            builder.tagForChildDirectedTreatment(mediationAdRequest.taggedForChildDirectedTreatment() == 1);
        }
        builder.addNetworkExtrasBundle(AdMobAdapter.class, zza(bundle, bundle2));
        return builder.build();
    }
}
