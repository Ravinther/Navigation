package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.events.EnabledEventsStrategy;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.concurrent.ScheduledExecutorService;

class EnabledSessionAnalyticsManagerStrategy extends EnabledEventsStrategy<SessionEvent> implements SessionAnalyticsManagerStrategy<SessionEvent> {
    ApiKey apiKey;
    boolean customEventsEnabled;
    EventFilter eventFilter;
    FilesSender filesSender;
    private final HttpRequestFactory httpRequestFactory;
    private final Kit kit;
    final SessionEventMetadata metadata;
    boolean predefinedEventsEnabled;

    public EnabledSessionAnalyticsManagerStrategy(Kit kit, Context context, ScheduledExecutorService executor, SessionAnalyticsFilesManager filesManager, HttpRequestFactory httpRequestFactory, SessionEventMetadata metadata) {
        super(context, executor, filesManager);
        this.eventFilter = new KeepAllEventFilter();
        this.apiKey = new ApiKey();
        this.customEventsEnabled = true;
        this.predefinedEventsEnabled = true;
        this.kit = kit;
        this.httpRequestFactory = httpRequestFactory;
        this.metadata = metadata;
    }

    public FilesSender getFilesSender() {
        return this.filesSender;
    }

    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String protocolAndHostOverride) {
        this.filesSender = AnswersRetryFilesSender.build(new SessionAnalyticsFilesSender(this.kit, protocolAndHostOverride, analyticsSettingsData.analyticsURL, this.httpRequestFactory, this.apiKey.getValue(this.context)));
        ((SessionAnalyticsFilesManager) this.filesManager).setAnalyticsSettingsData(analyticsSettingsData);
        this.customEventsEnabled = analyticsSettingsData.trackCustomEvents;
        Fabric.getLogger().m1453d("Answers", "Custom event tracking " + (this.customEventsEnabled ? "enabled" : "disabled"));
        this.predefinedEventsEnabled = analyticsSettingsData.trackPredefinedEvents;
        Fabric.getLogger().m1453d("Answers", "Predefined event tracking " + (this.predefinedEventsEnabled ? "enabled" : "disabled"));
        if (analyticsSettingsData.samplingRate > 1) {
            Fabric.getLogger().m1453d("Answers", "Event sampling enabled");
            this.eventFilter = new SamplingEventFilter(analyticsSettingsData.samplingRate);
        }
        configureRollover(analyticsSettingsData.flushIntervalSeconds);
    }

    public void processEvent(Builder builder) {
        SessionEvent event = builder.build(this.metadata);
        if (!this.customEventsEnabled && Type.CUSTOM.equals(event.type)) {
            Fabric.getLogger().m1453d("Answers", "Custom events tracking disabled - skipping event: " + event);
        } else if (!this.predefinedEventsEnabled && Type.PREDEFINED.equals(event.type)) {
            Fabric.getLogger().m1453d("Answers", "Predefined events tracking disabled - skipping event: " + event);
        } else if (this.eventFilter.skipEvent(event)) {
            Fabric.getLogger().m1453d("Answers", "Skipping filtered event: " + event);
        } else {
            recordEvent(event);
        }
    }
}
