package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.events.EventsStorageListener;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.concurrent.ScheduledExecutorService;

class AnswersEventsHandler implements EventsStorageListener {
    private final Context context;
    final ScheduledExecutorService executor;
    private final SessionAnalyticsFilesManager filesManager;
    private final Kit kit;
    private final SessionMetadataCollector metadataCollector;
    private final HttpRequestFactory requestFactory;
    SessionAnalyticsManagerStrategy strategy;

    /* renamed from: com.crashlytics.android.answers.AnswersEventsHandler.1 */
    class C02691 implements Runnable {
        final /* synthetic */ AnalyticsSettingsData val$analyticsSettingsData;
        final /* synthetic */ String val$protocolAndHostOverride;

        C02691(AnalyticsSettingsData analyticsSettingsData, String str) {
            this.val$analyticsSettingsData = analyticsSettingsData;
            this.val$protocolAndHostOverride = str;
        }

        public void run() {
            try {
                AnswersEventsHandler.this.strategy.setAnalyticsSettingsData(this.val$analyticsSettingsData, this.val$protocolAndHostOverride);
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Answers", "Failed to set analytics settings data", e);
            }
        }
    }

    /* renamed from: com.crashlytics.android.answers.AnswersEventsHandler.2 */
    class C02702 implements Runnable {
        C02702() {
        }

        public void run() {
            try {
                SessionAnalyticsManagerStrategy prevStrategy = AnswersEventsHandler.this.strategy;
                AnswersEventsHandler.this.strategy = new DisabledSessionAnalyticsManagerStrategy();
                prevStrategy.deleteAllEvents();
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Answers", "Failed to disable events", e);
            }
        }
    }

    /* renamed from: com.crashlytics.android.answers.AnswersEventsHandler.3 */
    class C02713 implements Runnable {
        C02713() {
        }

        public void run() {
            try {
                AnswersEventsHandler.this.strategy.sendEvents();
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Answers", "Failed to send events files", e);
            }
        }
    }

    /* renamed from: com.crashlytics.android.answers.AnswersEventsHandler.4 */
    class C02724 implements Runnable {
        C02724() {
        }

        public void run() {
            try {
                SessionEventMetadata metadata = AnswersEventsHandler.this.metadataCollector.getMetadata();
                AnswersEventsHandler.this.strategy = new EnabledSessionAnalyticsManagerStrategy(AnswersEventsHandler.this.kit, AnswersEventsHandler.this.context, AnswersEventsHandler.this.executor, AnswersEventsHandler.this.filesManager, AnswersEventsHandler.this.requestFactory, metadata);
            } catch (Exception e) {
                Fabric.getLogger().m1455e("Answers", "Failed to enable events");
            }
        }
    }

    /* renamed from: com.crashlytics.android.answers.AnswersEventsHandler.5 */
    class C02735 implements Runnable {
        final /* synthetic */ Builder val$eventBuilder;
        final /* synthetic */ boolean val$flush;

        C02735(Builder builder, boolean z) {
            this.val$eventBuilder = builder;
            this.val$flush = z;
        }

        public void run() {
            try {
                AnswersEventsHandler.this.strategy.processEvent(this.val$eventBuilder);
                if (this.val$flush) {
                    AnswersEventsHandler.this.strategy.rollFileOver();
                }
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Answers", "Failed to process event", e);
            }
        }
    }

    public AnswersEventsHandler(Kit kit, Context context, SessionAnalyticsFilesManager filesManager, SessionMetadataCollector metadataCollector, HttpRequestFactory requestFactory) {
        this(kit, context, filesManager, metadataCollector, requestFactory, ExecutorUtils.buildSingleThreadScheduledExecutorService("Answers Events Handler"));
        enable();
        filesManager.registerRollOverListener(this);
    }

    AnswersEventsHandler(Kit kit, Context context, SessionAnalyticsFilesManager filesManager, SessionMetadataCollector metadataCollector, HttpRequestFactory requestFactory, ScheduledExecutorService executor) {
        this.strategy = new DisabledSessionAnalyticsManagerStrategy();
        this.kit = kit;
        this.context = context;
        this.filesManager = filesManager;
        this.metadataCollector = metadataCollector;
        this.requestFactory = requestFactory;
        this.executor = executor;
    }

    public void processEventAsync(Builder eventBuilder) {
        processEvent(eventBuilder, false, false);
    }

    public void processEventAsyncAndFlush(Builder eventBuilder) {
        processEvent(eventBuilder, false, true);
    }

    public void processEventSync(Builder eventBuilder) {
        processEvent(eventBuilder, true, false);
    }

    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String protocolAndHostOverride) {
        executeAsync(new C02691(analyticsSettingsData, protocolAndHostOverride));
    }

    public void disable() {
        executeAsync(new C02702());
    }

    public void onRollOver(String rolledOverFile) {
        executeAsync(new C02713());
    }

    void enable() {
        executeAsync(new C02724());
    }

    void processEvent(Builder eventBuilder, boolean sync, boolean flush) {
        Runnable runnable = new C02735(eventBuilder, flush);
        if (sync) {
            executeSync(runnable);
        } else {
            executeAsync(runnable);
        }
    }

    private void executeSync(Runnable runnable) {
        try {
            this.executor.submit(runnable).get();
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Answers", "Failed to run events task", e);
        }
    }

    private void executeAsync(Runnable runnable) {
        try {
            this.executor.submit(runnable);
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Answers", "Failed to submit events task", e);
        }
    }
}
