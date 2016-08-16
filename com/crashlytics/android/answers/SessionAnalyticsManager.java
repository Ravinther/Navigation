package com.crashlytics.android.answers;

import android.app.Activity;
import android.os.Looper;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;

class SessionAnalyticsManager {
    final AnswersEventsHandler eventsHandler;

    public SessionAnalyticsManager(AnswersEventsHandler eventsHandler) {
        this.eventsHandler = eventsHandler;
    }

    public void onCrash(String sessionId) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("onCrash called from main thread!!!");
        }
        Fabric.getLogger().m1453d("Answers", "Logged crash");
        this.eventsHandler.processEventSync(SessionEvent.crashEventBuilder(sessionId));
    }

    public void onError(String sessionId) {
        Fabric.getLogger().m1453d("Answers", "Logged error");
        this.eventsHandler.processEventAsync(SessionEvent.errorEventBuilder(sessionId));
    }

    public void onInstall() {
        Fabric.getLogger().m1453d("Answers", "Logged install");
        this.eventsHandler.processEventAsyncAndFlush(SessionEvent.installEventBuilder());
    }

    public void onLifecycle(Activity activity, Type type) {
        Fabric.getLogger().m1453d("Answers", "Logged lifecycle event: " + type.name());
        this.eventsHandler.processEventAsync(SessionEvent.lifecycleEventBuilder(type, activity));
    }

    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String protocolAndHostOverride) {
        this.eventsHandler.setAnalyticsSettingsData(analyticsSettingsData, protocolAndHostOverride);
    }

    public void disable() {
        this.eventsHandler.disable();
    }
}
