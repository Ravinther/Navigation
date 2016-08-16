package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AdvertisingInfoProvider {
    private final Context context;
    private final PreferenceStore preferenceStore;

    /* renamed from: io.fabric.sdk.android.services.common.AdvertisingInfoProvider.1 */
    class C18121 extends BackgroundPriorityRunnable {
        final /* synthetic */ AdvertisingInfo val$advertisingInfo;

        C18121(AdvertisingInfo advertisingInfo) {
            this.val$advertisingInfo = advertisingInfo;
        }

        public void onRun() {
            AdvertisingInfo infoToStore = AdvertisingInfoProvider.this.getAdvertisingInfoFromStrategies();
            if (!this.val$advertisingInfo.equals(infoToStore)) {
                Fabric.getLogger().m1453d("Fabric", "Asychronously getting Advertising Info and storing it to preferences");
                AdvertisingInfoProvider.this.storeInfoToPreferences(infoToStore);
            }
        }
    }

    public AdvertisingInfoProvider(Context context) {
        this.context = context.getApplicationContext();
        this.preferenceStore = new PreferenceStoreImpl(context, "TwitterAdvertisingInfoPreferences");
    }

    public AdvertisingInfo getAdvertisingInfo() {
        AdvertisingInfo infoToReturn = getInfoFromPreferences();
        if (isInfoValid(infoToReturn)) {
            Fabric.getLogger().m1453d("Fabric", "Using AdvertisingInfo from Preference Store");
            refreshInfoIfNeededAsync(infoToReturn);
            return infoToReturn;
        }
        infoToReturn = getAdvertisingInfoFromStrategies();
        storeInfoToPreferences(infoToReturn);
        return infoToReturn;
    }

    private void refreshInfoIfNeededAsync(AdvertisingInfo advertisingInfo) {
        new Thread(new C18121(advertisingInfo)).start();
    }

    @SuppressLint({"CommitPrefEdits"})
    private void storeInfoToPreferences(AdvertisingInfo infoToReturn) {
        if (isInfoValid(infoToReturn)) {
            this.preferenceStore.save(this.preferenceStore.edit().putString("advertising_id", infoToReturn.advertisingId).putBoolean("limit_ad_tracking_enabled", infoToReturn.limitAdTrackingEnabled));
        } else {
            this.preferenceStore.save(this.preferenceStore.edit().remove("advertising_id").remove("limit_ad_tracking_enabled"));
        }
    }

    protected AdvertisingInfo getInfoFromPreferences() {
        return new AdvertisingInfo(this.preferenceStore.get().getString("advertising_id", ""), this.preferenceStore.get().getBoolean("limit_ad_tracking_enabled", false));
    }

    public AdvertisingInfoStrategy getReflectionStrategy() {
        return new AdvertisingInfoReflectionStrategy(this.context);
    }

    public AdvertisingInfoStrategy getServiceStrategy() {
        return new AdvertisingInfoServiceStrategy(this.context);
    }

    private boolean isInfoValid(AdvertisingInfo advertisingInfo) {
        return (advertisingInfo == null || TextUtils.isEmpty(advertisingInfo.advertisingId)) ? false : true;
    }

    private AdvertisingInfo getAdvertisingInfoFromStrategies() {
        AdvertisingInfo infoToReturn = getReflectionStrategy().getAdvertisingInfo();
        if (isInfoValid(infoToReturn)) {
            Fabric.getLogger().m1453d("Fabric", "Using AdvertisingInfo from Reflection Provider");
        } else {
            infoToReturn = getServiceStrategy().getAdvertisingInfo();
            if (isInfoValid(infoToReturn)) {
                Fabric.getLogger().m1453d("Fabric", "Using AdvertisingInfo from Service Provider");
            } else {
                Fabric.getLogger().m1453d("Fabric", "AdvertisingInfo not present");
            }
        }
        return infoToReturn;
    }
}
