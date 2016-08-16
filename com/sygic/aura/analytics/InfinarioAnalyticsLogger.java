package com.sygic.aura.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.infinario.android.infinariosdk.Infinario;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.c2dm.C2DMessaging;
import com.sygic.aura.tracker.TrackerUtils;
import java.util.HashMap;

public class InfinarioAnalyticsLogger implements AnalyticsInterface {
    private static String mCompanyToken;
    private static InfinarioAnalyticsLogger sInstance;
    private int mAppVersionCode;
    private String mAppVersionName;
    private BackportInterface mBackportInfo;
    private final Context mContext;
    private final Infinario mInfinario;

    public interface BackportInterface {
        String getAccountUserName();

        boolean isLoggedIn();

        boolean isTrial();

        boolean isTrialExpired();

        boolean isValid();
    }

    public static abstract class StartDestInfo {
        private final String mEndCity;
        private final String mEndCountry;
        private final String mEndStreet;
        private final String mStartCity;
        private final String mStartCountry;
        private final String mStartStreet;

        protected StartDestInfo(String startCountry, String startCity, String startStreet, String endCountry, String endCity, String endStreet) {
            this.mStartCountry = startCountry;
            this.mStartCity = startCity;
            this.mStartStreet = startStreet;
            this.mEndCountry = endCountry;
            this.mEndCity = endCity;
            this.mEndStreet = endStreet;
        }

        public void toAttrs(HashMap<String, Object> attrs) {
            attrs.put("start country", this.mStartCountry);
            attrs.put("start city", this.mStartCity);
            attrs.put("start street", this.mStartStreet);
            attrs.put("end country", this.mEndCountry);
            attrs.put("end city", this.mEndCity);
            attrs.put("end street", this.mEndStreet);
        }
    }

    public static InfinarioAnalyticsLogger getInstance(Context context) {
        if (sInstance == null) {
            mCompanyToken = ProjectsConsts.getBoolean(7) ? "29df71a4-71a6-11e5-96f9-44a842249406" : "ea417e9a-718a-11e5-a4f8-44a84224c532";
            sInstance = new InfinarioAnalyticsLogger(context);
        }
        return sInstance;
    }

    private InfinarioAnalyticsLogger(Context context) {
        this.mContext = context;
        this.mInfinario = Infinario.getInstance(context, mCompanyToken, "https://sygic-api.infinario.com");
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.mAppVersionCode = pInfo.versionCode;
            this.mAppVersionName = pInfo.versionName;
        } catch (NameNotFoundException e) {
            this.mAppVersionName = "error";
        }
    }

    public void setBackportInfo(BackportInterface backportInfo) {
        this.mBackportInfo = backportInfo;
    }

    public void init() {
        update();
    }

    public void update(String newsletterEmail) {
        HashMap<String, Object> attrs = newUpdateAttributes();
        attrs.put("newsletter email", newsletterEmail);
        attrs.put("email", newsletterEmail);
        this.mInfinario.update(attrs);
    }

    public void update() {
        this.mInfinario.update(newUpdateAttributes());
    }

    public void identify() {
        if (this.mBackportInfo != null && this.mBackportInfo.isLoggedIn()) {
            String username = this.mBackportInfo.getAccountUserName();
            if (!TextUtils.isEmpty(username)) {
                this.mInfinario.identify(username);
            }
        }
    }

    public void startSession() {
        this.mInfinario.trackSessionStart();
    }

    public void endSession() {
        this.mInfinario.trackSessionEnd();
    }

    public void logEvent(Bundle params) {
        String eventName = params.getString("eventName");
        if (!TextUtils.isEmpty(eventName)) {
            this.mInfinario.track(eventName, (HashMap) params.getSerializable("attributes"));
        }
    }

    public void logEvent(AnalyticsEvent event) {
        this.mInfinario.track(event.getName(), event.getParams());
    }

    public void trackFrwWelcome() {
        this.mInfinario.track("FRW Welcome", newFrwAttributes());
    }

    public void trackFrwChooseContinent() {
        this.mInfinario.track("FRW Choose continent", newFrwAttributes());
    }

    public void trackFrwChooseCountry(String continentId) {
        HashMap<String, Object> attrs = newFrwAttributes();
        attrs.put("continent id", continentId);
        this.mInfinario.track("FRW Choose country", attrs);
    }

    public void trackFrwModalMobileNetworkDownload(long totalSize, String[] countryIds) {
        trackFrwModalShown("mobile network download", totalSize, countryIds);
    }

    public void trackFrwModalNotEnoughSpace(long totalSize, String[] countryIds) {
        trackFrwModalShown("not enough space", totalSize, countryIds);
    }

    private void trackFrwModalShown(String type, long totalSize, String[] countryIds) {
        HashMap<String, Object> attrs = newFrwAttributes();
        attrs.put("type", type);
        attrs.put("total download size (mb)", Long.valueOf(TrackerUtils.bytesToMegabytes(totalSize)));
        attrs.put("country ids", countryIds);
        this.mInfinario.track("FRW Modal shown", attrs);
    }

    public void trackFrwEmailShown() {
        HashMap<String, Object> attrs = newFrwAttributes();
        attrs.put("status", "screen shown");
        this.mInfinario.track("FRW Email", attrs);
    }

    public void trackFrwEmailAction(boolean emailEntered) {
        HashMap<String, Object> attrs = newFrwAttributes();
        attrs.put("status", "action taken");
        attrs.put("action", emailEntered ? "email entered" : "skipped");
        this.mInfinario.track("FRW Email", attrs);
    }

    public void trackDownloadingScreen(long totalSize, String[] countryIds, int percentComplete, int screenNumber) {
        HashMap<String, Object> attrs = newFrwAttributes();
        attrs.put("total download size (mb)", Long.valueOf(TrackerUtils.bytesToMegabytes(totalSize)));
        attrs.put("country ids", countryIds);
        attrs.put("percent complete", Integer.valueOf(percentComplete));
        attrs.put("screen number", Integer.valueOf(screenNumber));
        this.mInfinario.track("FRW Downloading screen", attrs);
    }

    public void trackFrwMapDownloadStarted(String[] countryIds, long totalSize) {
        HashMap<String, Object> attrs = newFrwAttributes();
        attrs.put("status", "started");
        attrs.put("total download size (mb)", Long.valueOf(TrackerUtils.bytesToMegabytes(totalSize)));
        attrs.put("country ids", countryIds);
        this.mInfinario.track("FRW Map download", attrs);
    }

    public void trackFrwMapDownloadFinished(String[] countryIds, long totalSize, long startTimestamp) {
        HashMap<String, Object> attrs = newFrwAttributes();
        attrs.put("status", "finished");
        attrs.put("total download size (mb)", Long.valueOf(TrackerUtils.bytesToMegabytes(totalSize)));
        attrs.put("country ids", countryIds);
        attrs.put("duration (s)", Long.valueOf((System.currentTimeMillis() - startTimestamp) / 1000));
        this.mInfinario.track("FRW Map download", attrs);
    }

    public void trackMapView() {
        this.mInfinario.track("FRW Map view", newFrwAttributes());
    }

    public void trackFrwWentToBackground() {
        this.mInfinario.track("FRW Went to background", newFrwAttributes());
    }

    public void trackOfflinePromo(int pageIndex, String source) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("promotion screen index", Integer.valueOf(pageIndex));
        attrs.put("source", source);
        this.mInfinario.track("Offline promo", attrs);
    }

    public void trackOnlinePromoViewed(String promoUrl) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("status", "viewed");
        attrs.put("url", promoUrl);
        this.mInfinario.track("Online promo", attrs);
    }

    public void trackOnlinePromoActionBuy(String promoUrl) {
        trackOnlinePromoAction(promoUrl, "buy");
    }

    public void trackOnlinePromoActionClose(String promoUrl) {
        trackOnlinePromoAction(promoUrl, "close");
    }

    public void trackOnlinePromoAction(String promoUrl, String action) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("status", "action taken");
        attrs.put("url", promoUrl);
        attrs.put("action", action);
        this.mInfinario.track("Online promo", attrs);
    }

    public void trackNightly(int kmWithRoute, int kmWithoutRoute, String[] installedMaps, String[] installedApps) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("total space (mb)", Long.valueOf(TrackerUtils.getTotalDiskSpaceInMegabytes(this.mContext)));
        attrs.put("free space (mb)", Long.valueOf(TrackerUtils.getFreeDiskSpaceInMegabytes(this.mContext)));
        attrs.put("trip with route (km)", Integer.valueOf(kmWithRoute));
        attrs.put("trip without route (km)", Integer.valueOf(kmWithoutRoute));
        attrs.put("installed maps", installedMaps);
        attrs.put("installed apps", installedApps);
        this.mInfinario.track("Nightly", attrs);
    }

    public void trackStoreViewMainList(String source) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("source", source);
        this.mInfinario.track("Store - view main list", attrs);
    }

    public void trackStoreViewProductDetail(String productId) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("id", productId);
        this.mInfinario.track("Store - view product detail", attrs);
    }

    public void trackStoreClickBuyButton(String productId) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("id", productId);
        this.mInfinario.track("Store - click buy button", attrs);
    }

    public void trackPurchaseWebView(String productId, String price, String currency, String email) {
        trackPurchase("webview", productId, price, currency, email);
    }

    public void trackPurchaseIab(String productId, String price, String currency) {
        trackPurchase("iab", productId, price, currency, null);
    }

    public void trackPurchase(String source, String productId, String price, String currency, String email) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("source", source);
        attrs.put("id", productId);
        attrs.put("price", price);
        attrs.put("currency", currency);
        attrs.put("email", email);
        this.mInfinario.track("Purchased", attrs);
    }

    public void trackProductCodeInsert() {
        this.mInfinario.track("Product code insert", newDefaultAttributes());
    }

    public void trackSignInOut(boolean signIn) {
        if (signIn) {
            this.mInfinario.track("Sign in", newDefaultAttributes());
        } else {
            this.mInfinario.track("Sign out", newDefaultAttributes());
        }
    }

    public void trackAppActionOpenSearch() {
        trackAppAction("opened search", newDefaultAttributes());
    }

    public void trackAppActionOpenSettings() {
        trackAppAction("opened settings", newDefaultAttributes());
    }

    public void trackAppActionSearchedAddress() {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("what", "address");
        trackAppAction("searched something", attrs);
    }

    public void trackAppActionSearchedPoi() {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("what", "poi");
        trackAppAction("searched something", attrs);
    }

    private void trackAppAction(String action, HashMap<String, Object> attrs) {
        attrs.put("action name", action);
        this.mInfinario.track("App actions", attrs);
    }

    public void trackJourneyPlanned(StartDestInfo info) {
        doTrackJourney("planned", newDefaultAttributes(), info);
    }

    public void trackJourneyNoGpsModal(StartDestInfo info) {
        doTrackJourney("no gps modal shown", newDefaultAttributes(), info);
    }

    public void trackJourneyStarted(StartDestInfo info, long calculatedDistance, int calculatedTime, boolean wasAlternativeSelected) {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("picked alternative", Boolean.valueOf(wasAlternativeSelected));
        trackJourney("started", attrs, info, calculatedDistance, calculatedTime);
    }

    public void trackJourneyHardEnd(StartDestInfo info, long calculatedDistance, int calculatedTime) {
        trackJourney("hard end", newDefaultAttributes(), info, calculatedDistance, calculatedTime);
    }

    public void trackJourneySoftEnd(StartDestInfo info, long calculatedDistance, int calculatedTime) {
        trackJourney("soft end", newDefaultAttributes(), info, calculatedDistance, calculatedTime);
    }

    private void trackJourney(String status, HashMap<String, Object> attrs, StartDestInfo info, long calculatedDistance, int calculatedTime) {
        attrs.put("calculated distance (m)", Long.valueOf(calculatedDistance));
        attrs.put("calculated duration (s)", Integer.valueOf(calculatedTime));
        doTrackJourney(status, attrs, info);
    }

    private void doTrackJourney(String status, HashMap<String, Object> attrs, StartDestInfo info) {
        attrs.put("status", status);
        info.toAttrs(attrs);
        this.mInfinario.track("Journey", attrs);
    }

    private HashMap<String, Object> newUpdateAttributes() {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("total space (mb)", Long.valueOf(TrackerUtils.getTotalDiskSpaceInMegabytes(this.mContext)));
        attrs.put("free space (mb)", Long.valueOf(TrackerUtils.getFreeDiskSpaceInMegabytes(this.mContext)));
        String pushId = C2DMessaging.getRegistrationId(this.mContext);
        if (!TextUtils.isEmpty(pushId)) {
            attrs.put("google_push_notification_id", pushId);
        }
        if (this.mBackportInfo != null && this.mBackportInfo.isLoggedIn()) {
            String username = this.mBackportInfo.getAccountUserName();
            if (!TextUtils.isEmpty(username)) {
                attrs.put("email", username);
            }
        }
        return attrs;
    }

    private HashMap<String, Object> newFrwAttributes() {
        HashMap<String, Object> attrs = newDefaultAttributes();
        attrs.put("total space (mb)", Long.valueOf(TrackerUtils.getTotalDiskSpaceInMegabytes(this.mContext)));
        attrs.put("free space (mb)", Long.valueOf(TrackerUtils.getFreeDiskSpaceInMegabytes(this.mContext)));
        attrs.put("connectivity", TrackerUtils.getConnectivityType(this.mContext));
        return attrs;
    }

    private HashMap<String, Object> newDefaultAttributes() {
        HashMap<String, Object> attrs = newAttributes();
        String licenseState = getLicenseState();
        attrs.put("license type", licenseState);
        attrs.put("app version code", Integer.valueOf(this.mAppVersionCode));
        attrs.put("app version", this.mAppVersionName);
        PreferenceManager.getDefaultSharedPreferences(this.mContext).edit().putString("key_cached_license", licenseState).apply();
        return attrs;
    }

    private HashMap<String, Object> newAttributes() {
        return new HashMap();
    }

    private String getLicenseState() {
        if (this.mBackportInfo == null || !this.mBackportInfo.isValid()) {
            return PreferenceManager.getDefaultSharedPreferences(this.mContext).getString("key_cached_license", "unknown");
        }
        if (!this.mBackportInfo.isTrial()) {
            return "premium";
        }
        if (this.mBackportInfo.isTrialExpired()) {
            return "expired";
        }
        return "trial";
    }
}
