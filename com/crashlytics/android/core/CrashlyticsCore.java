package com.crashlytics.android.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.widget.ScrollView;
import android.widget.TextView;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.internal.CrashEventDataProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.Settings.SettingsAccess;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@DependsOn({CrashEventDataProvider.class})
public class CrashlyticsCore extends Kit<Void> {
    private final ConcurrentHashMap<String, String> attributes;
    private String buildId;
    private float delay;
    private boolean disabled;
    private CrashlyticsExecutorServiceWrapper executorServiceWrapper;
    private CrashEventDataProvider externalCrashEventDataProvider;
    private CrashlyticsUncaughtExceptionHandler handler;
    private HttpRequestFactory httpRequestFactory;
    private File initializationMarkerFile;
    private String installerPackageName;
    private CrashlyticsListener listener;
    private String packageName;
    private final PinningInfoProvider pinningInfo;
    private final long startTime;
    private String userEmail;
    private String userId;
    private String userName;
    private String versionCode;
    private String versionName;

    /* renamed from: com.crashlytics.android.core.CrashlyticsCore.1 */
    class C02791 extends PriorityCallable<Void> {
        C02791() {
        }

        public Void call() throws Exception {
            return CrashlyticsCore.this.doInBackground();
        }

        public Priority getPriority() {
            return Priority.IMMEDIATE;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsCore.2 */
    class C02802 implements Callable<Void> {
        C02802() {
        }

        public Void call() throws Exception {
            CrashlyticsCore.this.initializationMarkerFile.createNewFile();
            Fabric.getLogger().m1453d("Fabric", "Initialization marker file created.");
            return null;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsCore.3 */
    class C02813 implements Callable<Boolean> {
        C02813() {
        }

        public Boolean call() throws Exception {
            try {
                boolean removed = CrashlyticsCore.this.initializationMarkerFile.delete();
                Fabric.getLogger().m1453d("Fabric", "Initialization marker file removed: " + removed);
                return Boolean.valueOf(removed);
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Fabric", "Problem encountered deleting Crashlytics initialization marker.", e);
                return Boolean.valueOf(false);
            }
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsCore.4 */
    class C02824 implements Callable<Boolean> {
        C02824() {
        }

        public Boolean call() throws Exception {
            return Boolean.valueOf(CrashlyticsCore.this.initializationMarkerFile.exists());
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsCore.5 */
    class C02835 implements SettingsAccess<Boolean> {
        C02835() {
        }

        public Boolean usingSettings(SettingsData settingsData) {
            boolean z = false;
            if (!settingsData.featuresData.promptEnabled) {
                return Boolean.valueOf(false);
            }
            if (!CrashlyticsCore.this.shouldSendReportsWithoutPrompting()) {
                z = true;
            }
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsCore.6 */
    class C02846 implements SettingsAccess<Boolean> {
        C02846() {
        }

        public Boolean usingSettings(SettingsData settingsData) {
            boolean send = true;
            Activity activity = CrashlyticsCore.this.getFabric().getCurrentActivity();
            if (!(activity == null || activity.isFinishing() || !CrashlyticsCore.this.shouldPromptUserBeforeSendingCrashReports())) {
                send = CrashlyticsCore.this.getSendDecisionFromUser(activity, settingsData.promptData);
            }
            return Boolean.valueOf(send);
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsCore.7 */
    class C02887 implements Runnable {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ OptInLatch val$latch;
        final /* synthetic */ PromptSettingsData val$promptData;
        final /* synthetic */ DialogStringResolver val$stringResolver;

        /* renamed from: com.crashlytics.android.core.CrashlyticsCore.7.1 */
        class C02851 implements OnClickListener {
            C02851() {
            }

            public void onClick(DialogInterface dialog, int which) {
                C02887.this.val$latch.setOptIn(true);
                dialog.dismiss();
            }
        }

        /* renamed from: com.crashlytics.android.core.CrashlyticsCore.7.2 */
        class C02862 implements OnClickListener {
            C02862() {
            }

            public void onClick(DialogInterface dialog, int id) {
                C02887.this.val$latch.setOptIn(false);
                dialog.dismiss();
            }
        }

        /* renamed from: com.crashlytics.android.core.CrashlyticsCore.7.3 */
        class C02873 implements OnClickListener {
            C02873() {
            }

            public void onClick(DialogInterface dialog, int id) {
                CrashlyticsCore.this.setShouldSendUserReportsWithoutPrompting(true);
                C02887.this.val$latch.setOptIn(true);
                dialog.dismiss();
            }
        }

        C02887(Activity activity, OptInLatch optInLatch, DialogStringResolver dialogStringResolver, PromptSettingsData promptSettingsData) {
            this.val$activity = activity;
            this.val$latch = optInLatch;
            this.val$stringResolver = dialogStringResolver;
            this.val$promptData = promptSettingsData;
        }

        public void run() {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.val$activity);
            OnClickListener sendClickListener = new C02851();
            float density = this.val$activity.getResources().getDisplayMetrics().density;
            int textViewPadding = CrashlyticsCore.this.dipsToPixels(density, 5);
            TextView textView = new TextView(this.val$activity);
            textView.setAutoLinkMask(15);
            textView.setText(this.val$stringResolver.getMessage());
            textView.setTextAppearance(this.val$activity, 16973892);
            textView.setPadding(textViewPadding, textViewPadding, textViewPadding, textViewPadding);
            textView.setFocusable(false);
            ScrollView scrollView = new ScrollView(this.val$activity);
            scrollView.setPadding(CrashlyticsCore.this.dipsToPixels(density, 14), CrashlyticsCore.this.dipsToPixels(density, 2), CrashlyticsCore.this.dipsToPixels(density, 10), CrashlyticsCore.this.dipsToPixels(density, 12));
            scrollView.addView(textView);
            builder.setView(scrollView).setTitle(this.val$stringResolver.getTitle()).setCancelable(false).setNeutralButton(this.val$stringResolver.getSendButtonTitle(), sendClickListener);
            if (this.val$promptData.showCancelButton) {
                builder.setNegativeButton(this.val$stringResolver.getCancelButtonTitle(), new C02862());
            }
            if (this.val$promptData.showAlwaysSendButton) {
                builder.setPositiveButton(this.val$stringResolver.getAlwaysSendButtonTitle(), new C02873());
            }
            builder.show();
        }
    }

    public static class Builder {
        private float delay;
        private boolean disabled;
        private CrashlyticsListener listener;
        private PinningInfoProvider pinningInfoProvider;

        public Builder() {
            this.delay = -1.0f;
            this.disabled = false;
        }

        public Builder disabled(boolean isDisabled) {
            this.disabled = isDisabled;
            return this;
        }

        public CrashlyticsCore build() {
            if (this.delay < 0.0f) {
                this.delay = 1.0f;
            }
            return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
        }
    }

    private class OptInLatch {
        private final CountDownLatch latch;
        private boolean send;

        private OptInLatch() {
            this.send = false;
            this.latch = new CountDownLatch(1);
        }

        void setOptIn(boolean optIn) {
            this.send = optIn;
            this.latch.countDown();
        }

        boolean getOptIn() {
            return this.send;
        }

        void await() {
            try {
                this.latch.await();
            } catch (InterruptedException e) {
            }
        }
    }

    public CrashlyticsCore() {
        this(1.0f, null, null, false);
    }

    CrashlyticsCore(float delay, CrashlyticsListener listener, PinningInfoProvider pinningInfo, boolean disabled) {
        this(delay, listener, pinningInfo, disabled, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
    }

    CrashlyticsCore(float delay, CrashlyticsListener listener, PinningInfoProvider pinningInfo, boolean disabled, ExecutorService crashHandlerExecutor) {
        this.userId = null;
        this.userEmail = null;
        this.userName = null;
        this.attributes = new ConcurrentHashMap();
        this.startTime = System.currentTimeMillis();
        this.delay = delay;
        this.listener = listener;
        this.pinningInfo = pinningInfo;
        this.disabled = disabled;
        this.executorServiceWrapper = new CrashlyticsExecutorServiceWrapper(crashHandlerExecutor);
    }

    protected boolean onPreExecute() {
        return onPreExecute(super.getContext());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean onPreExecute(android.content.Context r12) {
        /*
        r11 = this;
        r10 = 0;
        r0 = r11.disabled;
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        r0 = r10;
    L_0x0006:
        return r0;
    L_0x0007:
        r0 = new io.fabric.sdk.android.services.common.ApiKey;
        r0.<init>();
        r7 = r0.getValue(r12);
        if (r7 != 0) goto L_0x0014;
    L_0x0012:
        r0 = r10;
        goto L_0x0006;
    L_0x0014:
        r0 = io.fabric.sdk.android.Fabric.getLogger();
        r1 = "Fabric";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Initializing Crashlytics ";
        r2 = r2.append(r3);
        r3 = r11.getVersion();
        r2 = r2.append(r3);
        r2 = r2.toString();
        r0.m1457i(r1, r2);
        r0 = new java.io.File;
        r1 = r11.getSdkDirectory();
        r2 = "initialization_marker";
        r0.<init>(r1, r2);
        r11.initializationMarkerFile = r0;
        r9 = 0;
        r11.setAndValidateKitProperties(r12, r7);	 Catch:{ CrashlyticsMissingDependencyException -> 0x00b0, Exception -> 0x00ba }
        r5 = new com.crashlytics.android.core.SessionDataWriter;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r0 = r11.getContext();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r1 = r11.buildId;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r2 = r11.getPackageName();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r5.<init>(r0, r1, r2);	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r1 = "Fabric";
        r2 = "Installing exception handler...";
        r0.m1453d(r1, r2);	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r0 = new com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r1 = java.lang.Thread.getDefaultUncaughtExceptionHandler();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r2 = r11.listener;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r3 = r11.executorServiceWrapper;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r4 = r11.getIdManager();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r6 = r11;
        r0.<init>(r1, r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r11.handler = r0;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r9 = r11.didPreviousInitializationComplete();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r0 = r11.handler;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r0.ensureOpenSessionExists();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r0 = r11.handler;	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        java.lang.Thread.setDefaultUncaughtExceptionHandler(r0);	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
        r1 = "Fabric";
        r2 = "Successfully installed exception handler.";
        r0.m1453d(r1, r2);	 Catch:{ Exception -> 0x00a1, CrashlyticsMissingDependencyException -> 0x00b0 }
    L_0x0093:
        if (r9 == 0) goto L_0x00b7;
    L_0x0095:
        r0 = io.fabric.sdk.android.services.common.CommonUtils.canTryConnection(r12);	 Catch:{ CrashlyticsMissingDependencyException -> 0x00b0, Exception -> 0x00ba }
        if (r0 == 0) goto L_0x00b7;
    L_0x009b:
        r11.finishInitSynchronously();	 Catch:{ CrashlyticsMissingDependencyException -> 0x00b0, Exception -> 0x00ba }
        r0 = r10;
        goto L_0x0006;
    L_0x00a1:
        r8 = move-exception;
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ CrashlyticsMissingDependencyException -> 0x00b0, Exception -> 0x00ba }
        r1 = "Fabric";
        r2 = "There was a problem installing the exception handler.";
        r0.m1456e(r1, r2, r8);	 Catch:{ CrashlyticsMissingDependencyException -> 0x00b0, Exception -> 0x00ba }
        goto L_0x0093;
    L_0x00b0:
        r8 = move-exception;
        r0 = new io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
        r0.<init>(r8);
        throw r0;
    L_0x00b7:
        r0 = 1;
        goto L_0x0006;
    L_0x00ba:
        r8 = move-exception;
        r0 = io.fabric.sdk.android.Fabric.getLogger();
        r1 = "Fabric";
        r2 = "Crashlytics was not started due to an exception during initialization";
        r0.m1456e(r1, r2, r8);
        r0 = r10;
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.core.CrashlyticsCore.onPreExecute(android.content.Context):boolean");
    }

    private void setAndValidateKitProperties(Context context, String apiKey) {
        CrashlyticsPinningInfoProvider infoProvider = this.pinningInfo != null ? new CrashlyticsPinningInfoProvider(this.pinningInfo) : null;
        this.httpRequestFactory = new DefaultHttpRequestFactory(Fabric.getLogger());
        this.httpRequestFactory.setPinningInfoProvider(infoProvider);
        try {
            this.packageName = context.getPackageName();
            this.installerPackageName = getIdManager().getInstallerPackageName();
            Fabric.getLogger().m1453d("Fabric", "Installer package name is: " + this.installerPackageName);
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(this.packageName, 0);
            this.versionCode = Integer.toString(packageInfo.versionCode);
            this.versionName = packageInfo.versionName == null ? "0.0" : packageInfo.versionName;
            this.buildId = CommonUtils.resolveBuildId(context);
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Fabric", "Error setting up app properties", e);
        }
        getIdManager().getBluetoothMacAddress();
        getBuildIdValidator(this.buildId, isRequiringBuildId(context)).validate(apiKey, this.packageName);
    }

    protected Void doInBackground() {
        markInitializationStarted();
        this.handler.cleanInvalidTempFiles();
        boolean reportingDisabled = true;
        try {
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            if (settingsData == null) {
                Fabric.getLogger().m1459w("Fabric", "Received null settings, skipping initialization!");
                markInitializationComplete();
                return null;
            }
            if (settingsData.featuresData.collectReports) {
                reportingDisabled = false;
                this.handler.finalizeSessions();
                CreateReportSpiCall call = getCreateReportSpiCall(settingsData);
                if (call != null) {
                    new ReportUploader(call).uploadReports(this.delay);
                } else {
                    Fabric.getLogger().m1459w("Fabric", "Unable to create a call to upload reports.");
                }
            }
            if (reportingDisabled) {
                try {
                    Fabric.getLogger().m1453d("Fabric", "Crash reporting disabled.");
                } catch (Exception e) {
                    Fabric.getLogger().m1456e("Fabric", "Problem encountered during Crashlytics initialization.", e);
                } finally {
                    markInitializationComplete();
                }
            }
            markInitializationComplete();
            return null;
        } catch (Exception e2) {
            Fabric.getLogger().m1456e("Fabric", "Error dealing with settings", e2);
        }
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android.crashlytics-core";
    }

    public String getVersion() {
        return "2.3.3.61";
    }

    public static CrashlyticsCore getInstance() {
        return (CrashlyticsCore) Fabric.getKit(CrashlyticsCore.class);
    }

    public void logException(Throwable throwable) {
        if (this.disabled || !ensureFabricWithCalled("prior to logging exceptions.")) {
            return;
        }
        if (throwable == null) {
            Fabric.getLogger().log(5, "Fabric", "Crashlytics is ignoring a request to log a null exception.");
        } else {
            this.handler.writeNonFatalException(Thread.currentThread(), throwable);
        }
    }

    public void log(String msg) {
        doLog(3, "Fabric", msg);
    }

    private void doLog(int priority, String tag, String msg) {
        if (!this.disabled && ensureFabricWithCalled("prior to logging messages.")) {
            this.handler.writeToLog(System.currentTimeMillis() - this.startTime, formatLogMessage(priority, tag, msg));
        }
    }

    public void log(int priority, String tag, String msg) {
        doLog(priority, tag, msg);
        Fabric.getLogger().log(priority, "" + tag, "" + msg, true);
    }

    public void setString(String key, String value) {
        if (!this.disabled) {
            if (key != null) {
                key = sanitizeAttribute(key);
                if (this.attributes.size() < 64 || this.attributes.containsKey(key)) {
                    this.attributes.put(key, value == null ? "" : sanitizeAttribute(value));
                    this.handler.cacheKeyData(this.attributes);
                    return;
                }
                Fabric.getLogger().m1453d("Fabric", "Exceeded maximum number of custom attributes (64)");
            } else if (getContext() == null || !CommonUtils.isAppDebuggable(getContext())) {
                Fabric.getLogger().m1456e("Fabric", "Attempting to set custom attribute with null key, ignoring.", null);
            } else {
                throw new IllegalArgumentException("Custom attribute key must not be null.");
            }
        }
    }

    public void setBool(String key, boolean value) {
        setString(key, Boolean.toString(value));
    }

    private void finishInitSynchronously() {
        PriorityCallable<Void> callable = new C02791();
        for (Task task : getDependencies()) {
            callable.addDependency(task);
        }
        Future<Void> future = getFabric().getExecutorService().submit(callable);
        Fabric.getLogger().m1453d("Fabric", "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            future.get(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Fabric.getLogger().m1456e("Fabric", "Crashlytics was interrupted during initialization.", e);
        } catch (ExecutionException e2) {
            Fabric.getLogger().m1456e("Fabric", "Problem encountered during Crashlytics initialization.", e2);
        } catch (TimeoutException e3) {
            Fabric.getLogger().m1456e("Fabric", "Crashlytics timed out during initialization.", e3);
        }
    }

    static void recordLoggedExceptionEvent(String sessionId) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers != null) {
            answers.onException(new LoggedException(sessionId));
        }
    }

    static void recordFatalExceptionEvent(String sessionId) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers != null) {
            answers.onException(new FatalException(sessionId));
        }
    }

    Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    BuildIdValidator getBuildIdValidator(String buildId, boolean requireBuildId) {
        return new BuildIdValidator(buildId, requireBuildId);
    }

    String getPackageName() {
        return this.packageName;
    }

    String getInstallerPackageName() {
        return this.installerPackageName;
    }

    String getVersionName() {
        return this.versionName;
    }

    String getVersionCode() {
        return this.versionCode;
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
    }

    CrashlyticsUncaughtExceptionHandler getHandler() {
        return this.handler;
    }

    String getUserIdentifier() {
        return getIdManager().canCollectUserIds() ? this.userId : null;
    }

    String getUserEmail() {
        return getIdManager().canCollectUserIds() ? this.userEmail : null;
    }

    String getUserName() {
        return getIdManager().canCollectUserIds() ? this.userName : null;
    }

    void markInitializationStarted() {
        this.executorServiceWrapper.executeSyncLoggingException(new C02802());
    }

    void markInitializationComplete() {
        this.executorServiceWrapper.executeAsync(new C02813());
    }

    boolean didPreviousInitializationComplete() {
        return ((Boolean) this.executorServiceWrapper.executeSyncLoggingException(new C02824())).booleanValue();
    }

    void setExternalCrashEventDataProvider(CrashEventDataProvider provider) {
        this.externalCrashEventDataProvider = provider;
    }

    SessionEventData getExternalCrashEventData() {
        if (this.externalCrashEventDataProvider != null) {
            return this.externalCrashEventDataProvider.getCrashEventData();
        }
        return null;
    }

    File getSdkDirectory() {
        return new FileStoreImpl(this).getFilesDir();
    }

    boolean shouldPromptUserBeforeSendingCrashReports() {
        return ((Boolean) Settings.getInstance().withSettings(new C02835(), Boolean.valueOf(false))).booleanValue();
    }

    boolean shouldSendReportsWithoutPrompting() {
        return new PreferenceStoreImpl(this).get().getBoolean("always_send_reports_opt_in", false);
    }

    @SuppressLint({"CommitPrefEdits"})
    void setShouldSendUserReportsWithoutPrompting(boolean send) {
        PreferenceStore prefStore = new PreferenceStoreImpl(this);
        prefStore.save(prefStore.edit().putBoolean("always_send_reports_opt_in", send));
    }

    boolean canSendWithUserApproval() {
        return ((Boolean) Settings.getInstance().withSettings(new C02846(), Boolean.valueOf(true))).booleanValue();
    }

    CreateReportSpiCall getCreateReportSpiCall(SettingsData settingsData) {
        if (settingsData != null) {
            return new DefaultCreateReportSpiCall(this, getOverridenSpiEndpoint(), settingsData.appData.reportsUrl, this.httpRequestFactory);
        }
        return null;
    }

    private boolean getSendDecisionFromUser(Activity context, PromptSettingsData promptData) {
        DialogStringResolver stringResolver = new DialogStringResolver(context, promptData);
        OptInLatch latch = new OptInLatch();
        Activity activity = context;
        activity.runOnUiThread(new C02887(activity, latch, stringResolver, promptData));
        Fabric.getLogger().m1453d("Fabric", "Waiting for user opt-in.");
        latch.await();
        return latch.getOptIn();
    }

    SessionSettingsData getSessionSettingsData() {
        SettingsData settingsData = Settings.getInstance().awaitSettingsData();
        return settingsData == null ? null : settingsData.sessionData;
    }

    private boolean isRequiringBuildId(Context context) {
        return CommonUtils.getBooleanResourceValue(context, "com.crashlytics.RequireBuildId", true);
    }

    private static String formatLogMessage(int priority, String tag, String msg) {
        return CommonUtils.logPriorityToString(priority) + "/" + tag + " " + msg;
    }

    private static boolean ensureFabricWithCalled(String msg) {
        CrashlyticsCore instance = getInstance();
        if (instance != null && instance.handler != null) {
            return true;
        }
        Fabric.getLogger().m1456e("Fabric", "Crashlytics must be initialized by calling Fabric.with(Context) " + msg, null);
        return false;
    }

    private static String sanitizeAttribute(String input) {
        if (input == null) {
            return input;
        }
        input = input.trim();
        if (input.length() > 1024) {
            return input.substring(0, 1024);
        }
        return input;
    }

    private int dipsToPixels(float density, int dips) {
        return (int) (((float) dips) * density);
    }
}
