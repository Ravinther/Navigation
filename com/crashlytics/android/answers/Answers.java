package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.events.GZIPQueueFileEventStorage;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;

public class Answers extends Kit<Boolean> {
    SessionAnalyticsManager analyticsManager;
    private long installedAt;
    ActivityLifecycleManager lifecycleManager;
    AnswersPreferenceManager preferenceManager;
    private String versionCode;
    private String versionName;

    public void onException(LoggedException exception) {
        if (this.analyticsManager != null) {
            this.analyticsManager.onError(exception.getSessionId());
        }
    }

    public void onException(FatalException exception) {
        if (this.analyticsManager != null) {
            this.analyticsManager.onCrash(exception.getSessionId());
        }
    }

    @SuppressLint({"NewApi"})
    protected boolean onPreExecute() {
        try {
            Context context = getContext();
            this.preferenceManager = new AnswersPreferenceManager(new PreferenceStoreImpl(context, "settings"));
            this.lifecycleManager = new ActivityLifecycleManager(context);
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.versionCode = Integer.toString(packageInfo.versionCode);
            this.versionName = packageInfo.versionName == null ? "0.0" : packageInfo.versionName;
            if (VERSION.SDK_INT >= 9) {
                this.installedAt = packageInfo.firstInstallTime;
            } else {
                this.installedAt = new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir).lastModified();
            }
            initializeSessionAnalytics(context);
            return true;
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Answers", "Error retrieving app properties", e);
            return false;
        }
    }

    protected Boolean doInBackground() {
        try {
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            if (settingsData == null) {
                Fabric.getLogger().m1455e("Answers", "Failed to retrieve settings");
                return Boolean.valueOf(false);
            } else if (settingsData.featuresData.collectAnalytics) {
                Fabric.getLogger().m1453d("Answers", "Analytics collection enabled");
                this.analyticsManager.setAnalyticsSettingsData(settingsData.analyticsSettingsData, getOverridenSpiEndpoint());
                return Boolean.valueOf(true);
            } else {
                Fabric.getLogger().m1453d("Answers", "Analytics collection disabled");
                this.lifecycleManager.resetCallbacks();
                this.analyticsManager.disable();
                return Boolean.valueOf(false);
            }
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Answers", "Error dealing with settings", e);
            return Boolean.valueOf(false);
        }
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:answers";
    }

    public String getVersion() {
        return "1.3.0.68";
    }

    private void initializeSessionAnalytics(Context context) {
        try {
            this.analyticsManager = new SessionAnalyticsManager(new AnswersEventsHandler(this, context, new SessionAnalyticsFilesManager(context, new SessionEventTransform(), new SystemCurrentTimeProvider(), new GZIPQueueFileEventStorage(getContext(), new FileStoreImpl(this).getFilesDir(), "session_analytics.tap", "session_analytics_to_send")), new SessionMetadataCollector(context, getIdManager(), this.versionCode, this.versionName), new DefaultHttpRequestFactory(Fabric.getLogger())));
            this.lifecycleManager.registerCallbacks(new AnswersLifecycleCallbacks(this.analyticsManager));
            if (isFirstLaunch(this.installedAt)) {
                Fabric.getLogger().m1453d("Answers", "New app install detected");
                this.analyticsManager.onInstall();
                this.preferenceManager.setAnalyticsLaunched();
            }
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Answers", "Failed to initialize", e);
        }
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
    }

    boolean isFirstLaunch(long installedAt) {
        return !this.preferenceManager.hasAnalyticsLaunched() && installedRecently(installedAt);
    }

    boolean installedRecently(long installedAt) {
        return System.currentTimeMillis() - installedAt < 3600000;
    }
}
