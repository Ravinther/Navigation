package com.sygic.aura.analytics;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ItemBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.HitBuilders.TransactionBuilder;
import com.google.android.gms.analytics.Tracker;
import com.sygic.aura.tracker.TrackerUtils;
import com.sygic.base.C1799R;

public class GoogleAnalyticsLogger implements AnalyticsInterface {
    private static GoogleAnalyticsLogger sInstance;
    private final Tracker mTracker;

    public static GoogleAnalyticsLogger getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new GoogleAnalyticsLogger(context);
        }
        return sInstance;
    }

    public GoogleAnalyticsLogger(Context context) {
        this.mTracker = GoogleAnalytics.getInstance(context).newTracker(C1799R.xml.analytics);
        String deviceId = TrackerUtils.getDeviceId(context);
        if (deviceId != null) {
            this.mTracker.set("&uid", deviceId);
        }
    }

    public Tracker getTracker() {
        return this.mTracker;
    }

    public void logEvent(Bundle params) {
        String strScreenName = Utils.getBundledString(params, "screenName", "");
        String strTransactionId = Utils.getBundledString(params, "transaction_id", "");
        if (!strScreenName.isEmpty()) {
            this.mTracker.setScreenName(strScreenName);
            this.mTracker.send(new ScreenViewBuilder().build());
        } else if (strTransactionId.isEmpty()) {
            this.mTracker.send(new EventBuilder().setCategory(Utils.getBundledString(params, "category", "")).setAction(Utils.getBundledString(params, "eventName", "")).setLabel(Utils.getBundledString(params, "label", "")).build());
        } else {
            this.mTracker.send(new TransactionBuilder().setTransactionId(strTransactionId).setAffiliation(params.getString("affiliation")).setCurrencyCode(params.getString("currency")).setRevenue((double) params.getFloat("price")).setShipping(0.0d).setTax(0.0d).build());
            this.mTracker.send(new ItemBuilder().setTransactionId(strTransactionId).setName(params.getString("name")).setSku(params.getString("sku")).setCurrencyCode(params.getString("currency")).setPrice((double) params.getFloat("price")).setQuantity(1).build());
        }
    }
}
