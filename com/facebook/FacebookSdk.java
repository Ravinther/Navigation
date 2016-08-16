package com.facebook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import com.facebook.internal.BoltsMeasurementEventListener;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class FacebookSdk {
    private static final ThreadFactory DEFAULT_THREAD_FACTORY;
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE;
    private static final Object LOCK;
    private static final String TAG;
    private static volatile String appClientToken;
    private static Context applicationContext;
    private static volatile String applicationId;
    private static volatile String applicationName;
    private static File cacheDir;
    private static int callbackRequestCodeOffset;
    private static volatile Executor executor;
    private static volatile String facebookDomain;
    private static volatile boolean isDebugEnabled;
    private static boolean isLegacyTokenUpgradeSupported;
    private static final HashSet<LoggingBehavior> loggingBehaviors;
    private static AtomicLong onProgressThreshold;
    private static Boolean sdkInitialized;
    private static volatile int webDialogTheme;

    /* renamed from: com.facebook.FacebookSdk.1 */
    static class C03111 implements ThreadFactory {
        private final AtomicInteger counter;

        C03111() {
            this.counter = new AtomicInteger(0);
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "FacebookSdk #" + this.counter.incrementAndGet());
        }
    }

    /* renamed from: com.facebook.FacebookSdk.2 */
    static class C03122 implements Callable<Void> {
        final /* synthetic */ InitializeCallback val$callback;

        C03122(InitializeCallback initializeCallback) {
            this.val$callback = initializeCallback;
        }

        public Void call() throws Exception {
            AccessTokenManager.getInstance().loadCurrentAccessToken();
            ProfileManager.getInstance().loadCurrentProfile();
            if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() == null) {
                Profile.fetchProfileForCurrentAccessToken();
            }
            if (this.val$callback != null) {
                this.val$callback.onInitialized();
            }
            return null;
        }
    }

    /* renamed from: com.facebook.FacebookSdk.3 */
    static class C03133 implements Runnable {
        final /* synthetic */ Context val$applicationContext;
        final /* synthetic */ String val$applicationId;

        C03133(Context context, String str) {
            this.val$applicationContext = context;
            this.val$applicationId = str;
        }

        public void run() {
            FacebookSdk.publishInstallAndWaitForResponse(this.val$applicationContext, this.val$applicationId);
        }
    }

    public interface InitializeCallback {
        void onInitialized();
    }

    static {
        TAG = FacebookSdk.class.getCanonicalName();
        loggingBehaviors = new HashSet(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
        facebookDomain = "facebook.com";
        onProgressThreshold = new AtomicLong(65536);
        isDebugEnabled = false;
        isLegacyTokenUpgradeSupported = false;
        callbackRequestCodeOffset = 64206;
        LOCK = new Object();
        DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
        DEFAULT_THREAD_FACTORY = new C03111();
        sdkInitialized = Boolean.valueOf(false);
    }

    public static synchronized void sdkInitialize(Context applicationContext) {
        synchronized (FacebookSdk.class) {
            sdkInitialize(applicationContext, null);
        }
    }

    public static synchronized void sdkInitialize(Context applicationContext, InitializeCallback callback) {
        synchronized (FacebookSdk.class) {
            if (!sdkInitialized.booleanValue()) {
                Validate.notNull(applicationContext, "applicationContext");
                Validate.hasFacebookActivity(applicationContext, false);
                Validate.hasInternetPermissions(applicationContext, false);
                applicationContext = applicationContext.getApplicationContext();
                loadDefaultsFromMetadata(applicationContext);
                Utility.loadAppSettingsAsync(applicationContext, applicationId);
                NativeProtocol.updateAllAvailableProtocolVersionsAsync();
                BoltsMeasurementEventListener.getInstance(applicationContext);
                cacheDir = applicationContext.getCacheDir();
                getExecutor().execute(new FutureTask(new C03122(callback)));
                sdkInitialized = Boolean.valueOf(true);
            } else if (callback != null) {
                callback.onInitialized();
            }
        }
    }

    public static synchronized boolean isInitialized() {
        boolean booleanValue;
        synchronized (FacebookSdk.class) {
            booleanValue = sdkInitialized.booleanValue();
        }
        return booleanValue;
    }

    public static boolean isLoggingBehaviorEnabled(LoggingBehavior behavior) {
        boolean z;
        synchronized (loggingBehaviors) {
            z = isDebugEnabled() && loggingBehaviors.contains(behavior);
        }
        return z;
    }

    public static boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static boolean isLegacyTokenUpgradeSupported() {
        return isLegacyTokenUpgradeSupported;
    }

    public static Executor getExecutor() {
        synchronized (LOCK) {
            if (executor == null) {
                Executor executor = getAsyncTaskExecutor();
                if (executor == null) {
                    executor = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
                }
                executor = executor;
            }
        }
        return executor;
    }

    public static String getFacebookDomain() {
        return facebookDomain;
    }

    public static Context getApplicationContext() {
        Validate.sdkInitialized();
        return applicationContext;
    }

    private static Executor getAsyncTaskExecutor() {
        try {
            try {
                Object executorObject = AsyncTask.class.getField("THREAD_POOL_EXECUTOR").get(null);
                if (executorObject == null) {
                    return null;
                }
                if (executorObject instanceof Executor) {
                    return (Executor) executorObject;
                }
                return null;
            } catch (IllegalAccessException e) {
                return null;
            }
        } catch (NoSuchFieldException e2) {
            return null;
        }
    }

    public static void publishInstallAsync(Context context, String applicationId) {
        getExecutor().execute(new C03133(context.getApplicationContext(), applicationId));
    }

    static com.facebook.GraphResponse publishInstallAndWaitForResponse(android.content.Context r24, java.lang.String r25) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):com.facebook.GraphResponse. bs: [B:2:0x0004, B:10:0x007e]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:57)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        if (r24 == 0) goto L_0x0004;
    L_0x0002:
        if (r25 != 0) goto L_0x002b;
    L_0x0004:
        r19 = new java.lang.IllegalArgumentException;	 Catch:{ Exception -> 0x000d }
        r20 = "Both context and applicationId must be non-null";	 Catch:{ Exception -> 0x000d }
        r19.<init>(r20);	 Catch:{ Exception -> 0x000d }
        throw r19;	 Catch:{ Exception -> 0x000d }
    L_0x000d:
        r4 = move-exception;
        r19 = "Facebook-publish";
        r0 = r19;
        com.facebook.internal.Utility.logd(r0, r4);
        r19 = new com.facebook.GraphResponse;
        r20 = 0;
        r21 = 0;
        r22 = new com.facebook.FacebookRequestError;
        r23 = 0;
        r0 = r22;
        r1 = r23;
        r0.<init>(r1, r4);
        r19.<init>(r20, r21, r22);
    L_0x002a:
        return r19;
    L_0x002b:
        r8 = com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(r24);	 Catch:{ Exception -> 0x000d }
        r19 = "com.facebook.sdk.attributionTracking";	 Catch:{ Exception -> 0x000d }
        r20 = 0;	 Catch:{ Exception -> 0x000d }
        r0 = r24;	 Catch:{ Exception -> 0x000d }
        r1 = r19;	 Catch:{ Exception -> 0x000d }
        r2 = r20;	 Catch:{ Exception -> 0x000d }
        r14 = r0.getSharedPreferences(r1, r2);	 Catch:{ Exception -> 0x000d }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x000d }
        r19.<init>();	 Catch:{ Exception -> 0x000d }
        r0 = r19;	 Catch:{ Exception -> 0x000d }
        r1 = r25;	 Catch:{ Exception -> 0x000d }
        r19 = r0.append(r1);	 Catch:{ Exception -> 0x000d }
        r20 = "ping";	 Catch:{ Exception -> 0x000d }
        r19 = r19.append(r20);	 Catch:{ Exception -> 0x000d }
        r13 = r19.toString();	 Catch:{ Exception -> 0x000d }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x000d }
        r19.<init>();	 Catch:{ Exception -> 0x000d }
        r0 = r19;	 Catch:{ Exception -> 0x000d }
        r1 = r25;	 Catch:{ Exception -> 0x000d }
        r19 = r0.append(r1);	 Catch:{ Exception -> 0x000d }
        r20 = "json";	 Catch:{ Exception -> 0x000d }
        r19 = r19.append(r20);	 Catch:{ Exception -> 0x000d }
        r9 = r19.toString();	 Catch:{ Exception -> 0x000d }
        r20 = 0;	 Catch:{ Exception -> 0x000d }
        r0 = r20;	 Catch:{ Exception -> 0x000d }
        r10 = r14.getLong(r13, r0);	 Catch:{ Exception -> 0x000d }
        r19 = 0;	 Catch:{ Exception -> 0x000d }
        r0 = r19;	 Catch:{ Exception -> 0x000d }
        r12 = r14.getString(r9, r0);	 Catch:{ Exception -> 0x000d }
        r19 = com.facebook.internal.AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT;	 Catch:{ JSONException -> 0x00ea }
        r20 = com.facebook.appevents.AppEventsLogger.getAnonymousAppDeviceGUID(r24);	 Catch:{ JSONException -> 0x00ea }
        r21 = getLimitEventAndDataUsage(r24);	 Catch:{ JSONException -> 0x00ea }
        r0 = r19;	 Catch:{ JSONException -> 0x00ea }
        r1 = r20;	 Catch:{ JSONException -> 0x00ea }
        r2 = r21;	 Catch:{ JSONException -> 0x00ea }
        r3 = r24;	 Catch:{ JSONException -> 0x00ea }
        r15 = com.facebook.internal.AppEventsLoggerUtility.getJSONObjectForGraphAPICall(r0, r8, r1, r2, r3);	 Catch:{ JSONException -> 0x00ea }
        r19 = "%s/activities";	 Catch:{ Exception -> 0x000d }
        r20 = 1;	 Catch:{ Exception -> 0x000d }
        r0 = r20;	 Catch:{ Exception -> 0x000d }
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x000d }
        r20 = r0;	 Catch:{ Exception -> 0x000d }
        r21 = 0;	 Catch:{ Exception -> 0x000d }
        r20[r21] = r25;	 Catch:{ Exception -> 0x000d }
        r18 = java.lang.String.format(r19, r20);	 Catch:{ Exception -> 0x000d }
        r19 = 0;	 Catch:{ Exception -> 0x000d }
        r20 = 0;	 Catch:{ Exception -> 0x000d }
        r0 = r19;	 Catch:{ Exception -> 0x000d }
        r1 = r18;	 Catch:{ Exception -> 0x000d }
        r2 = r20;	 Catch:{ Exception -> 0x000d }
        r16 = com.facebook.GraphRequest.newPostRequest(r0, r1, r15, r2);	 Catch:{ Exception -> 0x000d }
        r20 = 0;
        r19 = (r10 > r20 ? 1 : (r10 == r20 ? 0 : -1));
        if (r19 == 0) goto L_0x010d;
    L_0x00bb:
        r6 = 0;
        if (r12 == 0) goto L_0x00c4;
    L_0x00be:
        r7 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0136 }
        r7.<init>(r12);	 Catch:{ JSONException -> 0x0136 }
        r6 = r7;
    L_0x00c4:
        if (r6 != 0) goto L_0x00f8;
    L_0x00c6:
        r19 = "true";	 Catch:{ Exception -> 0x000d }
        r20 = 0;	 Catch:{ Exception -> 0x000d }
        r21 = new com.facebook.GraphRequestBatch;	 Catch:{ Exception -> 0x000d }
        r22 = 1;	 Catch:{ Exception -> 0x000d }
        r0 = r22;	 Catch:{ Exception -> 0x000d }
        r0 = new com.facebook.GraphRequest[r0];	 Catch:{ Exception -> 0x000d }
        r22 = r0;	 Catch:{ Exception -> 0x000d }
        r23 = 0;	 Catch:{ Exception -> 0x000d }
        r22[r23] = r16;	 Catch:{ Exception -> 0x000d }
        r21.<init>(r22);	 Catch:{ Exception -> 0x000d }
        r19 = com.facebook.GraphResponse.createResponsesFromString(r19, r20, r21);	 Catch:{ Exception -> 0x000d }
        r20 = 0;	 Catch:{ Exception -> 0x000d }
        r19 = r19.get(r20);	 Catch:{ Exception -> 0x000d }
        r19 = (com.facebook.GraphResponse) r19;	 Catch:{ Exception -> 0x000d }
        goto L_0x002a;	 Catch:{ Exception -> 0x000d }
    L_0x00ea:
        r4 = move-exception;	 Catch:{ Exception -> 0x000d }
        r19 = new com.facebook.FacebookException;	 Catch:{ Exception -> 0x000d }
        r20 = "An error occurred while publishing install.";	 Catch:{ Exception -> 0x000d }
        r0 = r19;	 Catch:{ Exception -> 0x000d }
        r1 = r20;	 Catch:{ Exception -> 0x000d }
        r0.<init>(r1, r4);	 Catch:{ Exception -> 0x000d }
        throw r19;	 Catch:{ Exception -> 0x000d }
    L_0x00f8:
        r19 = new com.facebook.GraphResponse;	 Catch:{ Exception -> 0x000d }
        r20 = 0;	 Catch:{ Exception -> 0x000d }
        r21 = 0;	 Catch:{ Exception -> 0x000d }
        r22 = 0;	 Catch:{ Exception -> 0x000d }
        r0 = r19;	 Catch:{ Exception -> 0x000d }
        r1 = r20;	 Catch:{ Exception -> 0x000d }
        r2 = r21;	 Catch:{ Exception -> 0x000d }
        r3 = r22;	 Catch:{ Exception -> 0x000d }
        r0.<init>(r1, r2, r3, r6);	 Catch:{ Exception -> 0x000d }
        goto L_0x002a;	 Catch:{ Exception -> 0x000d }
    L_0x010d:
        r17 = r16.executeAndWait();	 Catch:{ Exception -> 0x000d }
        r5 = r14.edit();	 Catch:{ Exception -> 0x000d }
        r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x000d }
        r5.putLong(r13, r10);	 Catch:{ Exception -> 0x000d }
        r19 = r17.getJSONObject();	 Catch:{ Exception -> 0x000d }
        if (r19 == 0) goto L_0x012f;	 Catch:{ Exception -> 0x000d }
    L_0x0122:
        r19 = r17.getJSONObject();	 Catch:{ Exception -> 0x000d }
        r19 = r19.toString();	 Catch:{ Exception -> 0x000d }
        r0 = r19;	 Catch:{ Exception -> 0x000d }
        r5.putString(r9, r0);	 Catch:{ Exception -> 0x000d }
    L_0x012f:
        r5.apply();	 Catch:{ Exception -> 0x000d }
        r19 = r17;
        goto L_0x002a;
    L_0x0136:
        r19 = move-exception;
        goto L_0x00c4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):com.facebook.GraphResponse");
    }

    public static String getSdkVersion() {
        return "4.5.0";
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        Validate.sdkInitialized();
        return context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).getBoolean("limitEventUsage", false);
    }

    public static long getOnProgressThreshold() {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    static void loadDefaultsFromMetadata(Context context) {
        if (context != null) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (ai != null && ai.metaData != null) {
                    if (applicationId == null) {
                        String appId = ai.metaData.get("com.facebook.sdk.ApplicationId");
                        if (appId instanceof String) {
                            String appIdString = appId;
                            if (appIdString.toLowerCase(Locale.ROOT).startsWith("fb")) {
                                applicationId = appIdString.substring(2);
                            } else {
                                applicationId = appIdString;
                            }
                            applicationId = appId;
                        } else if (appId instanceof Integer) {
                            throw new FacebookException("App Ids cannot be directly placed in the manfiest.They mut be prexied by 'fb' or be placed in the string resource file.");
                        }
                    }
                    if (applicationName == null) {
                        applicationName = ai.metaData.getString("com.facebook.sdk.ApplicationName");
                    }
                    if (appClientToken == null) {
                        appClientToken = ai.metaData.getString("com.facebook.sdk.ClientToken");
                    }
                    if (webDialogTheme == 0) {
                        setWebDialogTheme(ai.metaData.getInt("com.facebook.sdk.WebDialogTheme"));
                    }
                }
            } catch (NameNotFoundException e) {
            }
        }
    }

    public static String getApplicationId() {
        Validate.sdkInitialized();
        return applicationId;
    }

    public static String getApplicationName() {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static String getClientToken() {
        Validate.sdkInitialized();
        return appClientToken;
    }

    public static int getWebDialogTheme() {
        Validate.sdkInitialized();
        return webDialogTheme;
    }

    public static void setWebDialogTheme(int theme) {
        webDialogTheme = theme;
    }

    public static File getCacheDir() {
        Validate.sdkInitialized();
        return cacheDir;
    }

    public static int getCallbackRequestCodeOffset() {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static boolean isFacebookRequestCode(int requestCode) {
        return requestCode >= callbackRequestCodeOffset && requestCode < callbackRequestCodeOffset + 100;
    }
}
