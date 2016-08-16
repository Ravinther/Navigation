package com.sygic.aura;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsCore.Builder;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders.AppViewBuilder;
import com.sygic.aura.analytics.GoogleAnalyticsLogger;
import com.sygic.aura.connectivity.ConnectivityChangesManager;
import com.sygic.aura.feature.automotive.BoschMySpin;
import io.fabric.sdk.android.Fabric;

public class SygicNaviActivity extends SygicActivityWrapper {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoschMySpin.INSTANCE.start(this);
        try {
            Intent intent = getIntent();
            if (intent != null) {
                dismissPendingNotification(intent);
                Uri uri = intent.getData();
                if (!(uri == null || uri.getQueryParameter("utm_source") == null)) {
                    GoogleAnalyticsLogger.getInstance(this).getTracker().send(((AppViewBuilder) new AppViewBuilder().setCampaignParamsFromUrl(uri.toString())).build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CrashlyticsCore core = new Builder().disabled(false).build();
            Fabric.with(this, new Crashlytics.Builder().core(core).build(), new CrashlyticsNdk());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        BoschMySpin.INSTANCE.stop();
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.out.println("SYGIC: onLowMemory()");
    }

    protected void onResume() {
        super.onResume();
        try {
            AppEventsLogger.activateApp(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectivityChangesManager.get(this).addForegroundChange(true);
    }

    protected void onPause() {
        super.onPause();
        try {
            AppEventsLogger.deactivateApp(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectivityChangesManager.get(this).addForegroundChange(false);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            dismissPendingNotification(intent);
        }
    }
}
