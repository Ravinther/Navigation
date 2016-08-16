package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils {

    /* renamed from: io.fabric.sdk.android.services.common.ExecutorUtils.1 */
    static class C18161 implements ThreadFactory {
        final /* synthetic */ AtomicLong val$count;
        final /* synthetic */ String val$threadNameTemplate;

        /* renamed from: io.fabric.sdk.android.services.common.ExecutorUtils.1.1 */
        class C18151 extends BackgroundPriorityRunnable {
            final /* synthetic */ Runnable val$runnable;

            C18151(Runnable runnable) {
                this.val$runnable = runnable;
            }

            public void onRun() {
                this.val$runnable.run();
            }
        }

        C18161(String str, AtomicLong atomicLong) {
            this.val$threadNameTemplate = str;
            this.val$count = atomicLong;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = Executors.defaultThreadFactory().newThread(new C18151(runnable));
            thread.setName(this.val$threadNameTemplate + this.val$count.getAndIncrement());
            return thread;
        }
    }

    /* renamed from: io.fabric.sdk.android.services.common.ExecutorUtils.2 */
    static class C18172 extends BackgroundPriorityRunnable {
        final /* synthetic */ ExecutorService val$service;
        final /* synthetic */ String val$serviceName;
        final /* synthetic */ long val$terminationTimeout;
        final /* synthetic */ TimeUnit val$timeUnit;

        C18172(String str, ExecutorService executorService, long j, TimeUnit timeUnit) {
            this.val$serviceName = str;
            this.val$service = executorService;
            this.val$terminationTimeout = j;
            this.val$timeUnit = timeUnit;
        }

        public void onRun() {
            try {
                Fabric.getLogger().m1453d("Fabric", "Executing shutdown hook for " + this.val$serviceName);
                this.val$service.shutdown();
                if (!this.val$service.awaitTermination(this.val$terminationTimeout, this.val$timeUnit)) {
                    Fabric.getLogger().m1453d("Fabric", this.val$serviceName + " did not shut down in the" + " allocated time. Requesting immediate shutdown.");
                    this.val$service.shutdownNow();
                }
            } catch (InterruptedException e) {
                Fabric.getLogger().m1453d("Fabric", String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[]{this.val$serviceName}));
                this.val$service.shutdownNow();
            }
        }
    }

    public static ExecutorService buildSingleThreadExecutorService(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor(getNamedThreadFactory(name));
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String name) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(getNamedThreadFactory(name));
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static final ThreadFactory getNamedThreadFactory(String threadNameTemplate) {
        return new C18161(threadNameTemplate, new AtomicLong(1));
    }

    private static final void addDelayedShutdownHook(String serviceName, ExecutorService service) {
        addDelayedShutdownHook(serviceName, service, 2, TimeUnit.SECONDS);
    }

    public static final void addDelayedShutdownHook(String serviceName, ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
        Runtime.getRuntime().addShutdownHook(new Thread(new C18172(serviceName, service, terminationTimeout, timeUnit), "Crashlytics Shutdown Hook for " + serviceName));
    }
}
