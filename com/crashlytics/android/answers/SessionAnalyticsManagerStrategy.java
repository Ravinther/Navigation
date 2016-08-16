package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.events.EventsStrategy;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;

interface SessionAnalyticsManagerStrategy<T> extends EventsStrategy<T> {
    void processEvent(Builder builder);

    void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String str);
}
