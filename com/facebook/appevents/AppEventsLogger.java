package com.facebook.appevents;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import bolts.AppLinks;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.internal.AppEventsLoggerUtility;
import com.facebook.internal.AppEventsLoggerUtility.GraphAPIActivityType;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.internal.Validate;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLogger {
    private static final String TAG;
    private static String anonymousAppDeviceGUID;
    private static Context applicationContext;
    private static ScheduledThreadPoolExecutor backgroundExecutor;
    private static FlushBehavior flushBehavior;
    private static boolean isOpenedByApplink;
    private static boolean requestInFlight;
    private static String sourceApplication;
    private static Map<AccessTokenAppIdPair, SessionEventsState> stateMap;
    private static Object staticLock;
    private final AccessTokenAppIdPair accessTokenAppId;
    private final String contextName;

    /* renamed from: com.facebook.appevents.AppEventsLogger.1 */
    static class C03241 implements Runnable {
        final /* synthetic */ long val$eventTime;
        final /* synthetic */ AppEventsLogger val$logger;
        final /* synthetic */ String val$sourceApplicationInfo;

        C03241(AppEventsLogger appEventsLogger, long j, String str) {
            this.val$logger = appEventsLogger;
            this.val$eventTime = j;
            this.val$sourceApplicationInfo = str;
        }

        public void run() {
            this.val$logger.logAppSessionResumeEvent(this.val$eventTime, this.val$sourceApplicationInfo);
        }
    }

    /* renamed from: com.facebook.appevents.AppEventsLogger.2 */
    static class C03252 implements Runnable {
        final /* synthetic */ long val$eventTime;
        final /* synthetic */ AppEventsLogger val$logger;

        C03252(AppEventsLogger appEventsLogger, long j) {
            this.val$logger = appEventsLogger;
            this.val$eventTime = j;
        }

        public void run() {
            this.val$logger.logAppSessionSuspendEvent(this.val$eventTime);
        }
    }

    /* renamed from: com.facebook.appevents.AppEventsLogger.3 */
    static class C03263 implements Runnable {
        C03263() {
        }

        public void run() {
            if (AppEventsLogger.getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
                AppEventsLogger.flushAndWait(FlushReason.TIMER);
            }
        }
    }

    /* renamed from: com.facebook.appevents.AppEventsLogger.4 */
    static class C03274 implements Runnable {
        C03274() {
        }

        public void run() {
            Set<String> applicationIds = new HashSet();
            synchronized (AppEventsLogger.staticLock) {
                for (AccessTokenAppIdPair accessTokenAppId : AppEventsLogger.stateMap.keySet()) {
                    applicationIds.add(accessTokenAppId.getApplicationId());
                }
            }
            for (String applicationId : applicationIds) {
                Utility.queryAppSettings(applicationId, true);
            }
        }
    }

    /* renamed from: com.facebook.appevents.AppEventsLogger.5 */
    static class C03285 implements Runnable {
        final /* synthetic */ AccessTokenAppIdPair val$accessTokenAppId;
        final /* synthetic */ Context val$context;
        final /* synthetic */ AppEvent val$event;

        C03285(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEvent appEvent) {
            this.val$context = context;
            this.val$accessTokenAppId = accessTokenAppIdPair;
            this.val$event = appEvent;
        }

        public void run() {
            AppEventsLogger.getSessionEventsState(this.val$context, this.val$accessTokenAppId).addEvent(this.val$event);
            AppEventsLogger.flushIfNecessary();
        }
    }

    /* renamed from: com.facebook.appevents.AppEventsLogger.6 */
    static class C03296 implements Runnable {
        final /* synthetic */ FlushReason val$reason;

        C03296(FlushReason flushReason) {
            this.val$reason = flushReason;
        }

        public void run() {
            AppEventsLogger.flushAndWait(this.val$reason);
        }
    }

    /* renamed from: com.facebook.appevents.AppEventsLogger.7 */
    static class C03307 implements Callback {
        final /* synthetic */ AccessTokenAppIdPair val$accessTokenAppId;
        final /* synthetic */ FlushStatistics val$flushState;
        final /* synthetic */ GraphRequest val$postRequest;
        final /* synthetic */ SessionEventsState val$sessionEventsState;

        C03307(AccessTokenAppIdPair accessTokenAppIdPair, GraphRequest graphRequest, SessionEventsState sessionEventsState, FlushStatistics flushStatistics) {
            this.val$accessTokenAppId = accessTokenAppIdPair;
            this.val$postRequest = graphRequest;
            this.val$sessionEventsState = sessionEventsState;
            this.val$flushState = flushStatistics;
        }

        public void onCompleted(GraphResponse response) {
            AppEventsLogger.handleResponse(this.val$accessTokenAppId, this.val$postRequest, response, this.val$sessionEventsState, this.val$flushState);
        }
    }

    private static class AccessTokenAppIdPair implements Serializable {
        private final String accessTokenString;
        private final String applicationId;

        AccessTokenAppIdPair(AccessToken accessToken) {
            this(accessToken.getToken(), FacebookSdk.getApplicationId());
        }

        AccessTokenAppIdPair(String accessTokenString, String applicationId) {
            if (Utility.isNullOrEmpty(accessTokenString)) {
                accessTokenString = null;
            }
            this.accessTokenString = accessTokenString;
            this.applicationId = applicationId;
        }

        String getAccessTokenString() {
            return this.accessTokenString;
        }

        String getApplicationId() {
            return this.applicationId;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.accessTokenString == null ? 0 : this.accessTokenString.hashCode();
            if (this.applicationId != null) {
                i = this.applicationId.hashCode();
            }
            return hashCode ^ i;
        }

        public boolean equals(Object o) {
            if (!(o instanceof AccessTokenAppIdPair)) {
                return false;
            }
            AccessTokenAppIdPair p = (AccessTokenAppIdPair) o;
            if (Utility.areObjectsEqual(p.accessTokenString, this.accessTokenString) && Utility.areObjectsEqual(p.applicationId, this.applicationId)) {
                return true;
            }
            return false;
        }
    }

    static class AppEvent implements Serializable {
        private static final HashSet<String> validatedIdentifiers;
        private boolean isImplicit;
        private JSONObject jsonObject;
        private String name;

        static {
            validatedIdentifiers = new HashSet();
        }

        public AppEvent(String contextName, String eventName, Double valueToSum, Bundle parameters, boolean isImplicitlyLogged) {
            try {
                validateIdentifier(eventName);
                this.name = eventName;
                this.isImplicit = isImplicitlyLogged;
                this.jsonObject = new JSONObject();
                this.jsonObject.put("_eventName", eventName);
                this.jsonObject.put("_logTime", System.currentTimeMillis() / 1000);
                this.jsonObject.put("_ui", contextName);
                if (valueToSum != null) {
                    this.jsonObject.put("_valueToSum", valueToSum.doubleValue());
                }
                if (this.isImplicit) {
                    this.jsonObject.put("_implicitlyLogged", "1");
                }
                if (parameters != null) {
                    for (String key : parameters.keySet()) {
                        validateIdentifier(key);
                        Object value = parameters.get(key);
                        if ((value instanceof String) || (value instanceof Number)) {
                            this.jsonObject.put(key, value.toString());
                        } else {
                            throw new FacebookException(String.format("Parameter value '%s' for key '%s' should be a string or a numeric type.", new Object[]{value, key}));
                        }
                    }
                }
                if (!this.isImplicit) {
                    Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Created app event '%s'", this.jsonObject.toString());
                }
            } catch (JSONException jsonException) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "JSON encoding for app event failed: '%s'", jsonException.toString());
                this.jsonObject = null;
            } catch (FacebookException e) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Invalid app event name or parameter:", e.toString());
                this.jsonObject = null;
            }
        }

        public boolean getIsImplicit() {
            return this.isImplicit;
        }

        public JSONObject getJSONObject() {
            return this.jsonObject;
        }

        private void validateIdentifier(String identifier) throws FacebookException {
            String regex = "^[0-9a-zA-Z_]+[0-9a-zA-Z _-]*$";
            if (identifier == null || identifier.length() == 0 || identifier.length() > 40) {
                if (identifier == null) {
                    identifier = "<None Provided>";
                }
                throw new FacebookException(String.format(Locale.ROOT, "Identifier '%s' must be less than %d characters", new Object[]{identifier, Integer.valueOf(40)}));
            }
            synchronized (validatedIdentifiers) {
                boolean alreadyValidated = validatedIdentifiers.contains(identifier);
            }
            if (!alreadyValidated) {
                if (identifier.matches("^[0-9a-zA-Z_]+[0-9a-zA-Z _-]*$")) {
                    synchronized (validatedIdentifiers) {
                        validatedIdentifiers.add(identifier);
                    }
                    return;
                }
                throw new FacebookException(String.format("Skipping event named '%s' due to illegal name - must be under 40 chars and alphanumeric, _, - or space, and not start with a space or hyphen.", new Object[]{identifier}));
            }
        }

        public String toString() {
            return String.format("\"%s\", implicit: %b, json: %s", new Object[]{this.jsonObject.optString("_eventName"), Boolean.valueOf(this.isImplicit), this.jsonObject.toString()});
        }
    }

    public enum FlushBehavior {
        AUTO,
        EXPLICIT_ONLY
    }

    private enum FlushReason {
        EXPLICIT,
        TIMER,
        SESSION_CHANGE,
        PERSISTED_EVENTS,
        EVENT_THRESHOLD,
        EAGER_FLUSHING_EVENT
    }

    private enum FlushResult {
        SUCCESS,
        SERVER_ERROR,
        NO_CONNECTIVITY,
        UNKNOWN_ERROR
    }

    private static class FlushStatistics {
        public int numEvents;
        public FlushResult result;

        private FlushStatistics() {
            this.numEvents = 0;
            this.result = FlushResult.SUCCESS;
        }
    }

    static class PersistedAppSessionInfo {
        private static final Runnable appSessionInfoFlushRunnable;
        private static Map<AccessTokenAppIdPair, FacebookTimeSpentData> appSessionInfoMap;
        private static boolean hasChanges;
        private static boolean isLoaded;
        private static final Object staticLock;

        /* renamed from: com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.1 */
        static class C03311 implements Runnable {
            C03311() {
            }

            public void run() {
                PersistedAppSessionInfo.saveAppSessionInformation(AppEventsLogger.applicationContext);
            }
        }

        static {
            staticLock = new Object();
            hasChanges = false;
            isLoaded = false;
            appSessionInfoFlushRunnable = new C03311();
        }

        private static void restoreAppSessionInformation(Context context) {
            Throwable th;
            Exception e;
            ObjectInputStream ois = null;
            synchronized (staticLock) {
                if (!isLoaded) {
                    try {
                        ObjectInputStream ois2 = new ObjectInputStream(context.openFileInput("AppEventsLogger.persistedsessioninfo"));
                        try {
                            appSessionInfoMap = (HashMap) ois2.readObject();
                            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info loaded");
                            try {
                                Utility.closeQuietly(ois2);
                                context.deleteFile("AppEventsLogger.persistedsessioninfo");
                                if (appSessionInfoMap == null) {
                                    appSessionInfoMap = new HashMap();
                                }
                                isLoaded = true;
                                hasChanges = false;
                                ois = ois2;
                            } catch (Throwable th2) {
                                th = th2;
                                ois = ois2;
                                throw th;
                            }
                        } catch (FileNotFoundException e2) {
                            ois = ois2;
                            Utility.closeQuietly(ois);
                            context.deleteFile("AppEventsLogger.persistedsessioninfo");
                            if (appSessionInfoMap == null) {
                                appSessionInfoMap = new HashMap();
                            }
                            isLoaded = true;
                            hasChanges = false;
                        } catch (Exception e3) {
                            e = e3;
                            ois = ois2;
                            try {
                                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                                Utility.closeQuietly(ois);
                                context.deleteFile("AppEventsLogger.persistedsessioninfo");
                                if (appSessionInfoMap == null) {
                                    appSessionInfoMap = new HashMap();
                                }
                                isLoaded = true;
                                hasChanges = false;
                            } catch (Throwable th3) {
                                th = th3;
                                Utility.closeQuietly(ois);
                                context.deleteFile("AppEventsLogger.persistedsessioninfo");
                                if (appSessionInfoMap == null) {
                                    appSessionInfoMap = new HashMap();
                                }
                                isLoaded = true;
                                hasChanges = false;
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            ois = ois2;
                            Utility.closeQuietly(ois);
                            context.deleteFile("AppEventsLogger.persistedsessioninfo");
                            if (appSessionInfoMap == null) {
                                appSessionInfoMap = new HashMap();
                            }
                            isLoaded = true;
                            hasChanges = false;
                            throw th;
                        }
                    } catch (FileNotFoundException e4) {
                        Utility.closeQuietly(ois);
                        context.deleteFile("AppEventsLogger.persistedsessioninfo");
                        if (appSessionInfoMap == null) {
                            appSessionInfoMap = new HashMap();
                        }
                        isLoaded = true;
                        hasChanges = false;
                    } catch (Exception e5) {
                        e = e5;
                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                        Utility.closeQuietly(ois);
                        context.deleteFile("AppEventsLogger.persistedsessioninfo");
                        if (appSessionInfoMap == null) {
                            appSessionInfoMap = new HashMap();
                        }
                        isLoaded = true;
                        hasChanges = false;
                    } catch (Throwable th5) {
                        th = th5;
                        throw th;
                    }
                }
            }
        }

        static void saveAppSessionInformation(Context context) {
            Throwable th;
            Exception e;
            ObjectOutputStream oos = null;
            synchronized (staticLock) {
                try {
                    if (hasChanges) {
                        try {
                            ObjectOutputStream oos2 = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput("AppEventsLogger.persistedsessioninfo", 0)));
                            try {
                                oos2.writeObject(appSessionInfoMap);
                                hasChanges = false;
                                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info saved");
                                try {
                                    Utility.closeQuietly(oos2);
                                    oos = oos2;
                                } catch (Throwable th2) {
                                    th = th2;
                                    oos = oos2;
                                    throw th;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                oos = oos2;
                                try {
                                    Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                                    Utility.closeQuietly(oos);
                                } catch (Throwable th3) {
                                    th = th3;
                                    Utility.closeQuietly(oos);
                                    throw th;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                oos = oos2;
                                Utility.closeQuietly(oos);
                                throw th;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                            Utility.closeQuietly(oos);
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                    throw th;
                }
            }
        }

        static void onResume(Context context, AccessTokenAppIdPair accessTokenAppId, AppEventsLogger logger, long eventTime, String sourceApplicationInfo) {
            synchronized (staticLock) {
                getTimeSpentData(context, accessTokenAppId).onResume(logger, eventTime, sourceApplicationInfo);
                onTimeSpentDataUpdate();
            }
        }

        static void onSuspend(Context context, AccessTokenAppIdPair accessTokenAppId, AppEventsLogger logger, long eventTime) {
            synchronized (staticLock) {
                getTimeSpentData(context, accessTokenAppId).onSuspend(logger, eventTime);
                onTimeSpentDataUpdate();
            }
        }

        private static FacebookTimeSpentData getTimeSpentData(Context context, AccessTokenAppIdPair accessTokenAppId) {
            restoreAppSessionInformation(context);
            FacebookTimeSpentData result = (FacebookTimeSpentData) appSessionInfoMap.get(accessTokenAppId);
            if (result != null) {
                return result;
            }
            result = new FacebookTimeSpentData();
            appSessionInfoMap.put(accessTokenAppId, result);
            return result;
        }

        private static void onTimeSpentDataUpdate() {
            if (!hasChanges) {
                hasChanges = true;
                AppEventsLogger.backgroundExecutor.schedule(appSessionInfoFlushRunnable, 30, TimeUnit.SECONDS);
            }
        }
    }

    static class PersistedEvents {
        private static Object staticLock;
        private Context context;
        private HashMap<AccessTokenAppIdPair, List<AppEvent>> persistedEvents;

        static {
            staticLock = new Object();
        }

        private PersistedEvents(Context context) {
            this.persistedEvents = new HashMap();
            this.context = context;
        }

        public static PersistedEvents readAndClearStore(Context context) {
            PersistedEvents persistedEvents;
            synchronized (staticLock) {
                persistedEvents = new PersistedEvents(context);
                persistedEvents.readAndClearStore();
            }
            return persistedEvents;
        }

        public static void persistEvents(Context context, AccessTokenAppIdPair accessTokenAppId, SessionEventsState eventsToPersist) {
            Map<AccessTokenAppIdPair, SessionEventsState> map = new HashMap();
            map.put(accessTokenAppId, eventsToPersist);
            persistEvents(context, map);
        }

        public static void persistEvents(Context context, Map<AccessTokenAppIdPair, SessionEventsState> eventsToPersist) {
            synchronized (staticLock) {
                PersistedEvents persistedEvents = readAndClearStore(context);
                for (Entry<AccessTokenAppIdPair, SessionEventsState> entry : eventsToPersist.entrySet()) {
                    List<AppEvent> events = ((SessionEventsState) entry.getValue()).getEventsToPersist();
                    if (events.size() != 0) {
                        persistedEvents.addEvents((AccessTokenAppIdPair) entry.getKey(), events);
                    }
                }
                persistedEvents.write();
            }
        }

        public Set<AccessTokenAppIdPair> keySet() {
            return this.persistedEvents.keySet();
        }

        public List<AppEvent> getEvents(AccessTokenAppIdPair accessTokenAppId) {
            return (List) this.persistedEvents.get(accessTokenAppId);
        }

        private void write() {
            Exception e;
            Throwable th;
            ObjectOutputStream oos = null;
            try {
                ObjectOutputStream oos2 = new ObjectOutputStream(new BufferedOutputStream(this.context.openFileOutput("AppEventsLogger.persistedevents", 0)));
                try {
                    oos2.writeObject(this.persistedEvents);
                    Utility.closeQuietly(oos2);
                    oos = oos2;
                } catch (Exception e2) {
                    e = e2;
                    oos = oos2;
                    try {
                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                        Utility.closeQuietly(oos);
                    } catch (Throwable th2) {
                        th = th2;
                        Utility.closeQuietly(oos);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    oos = oos2;
                    Utility.closeQuietly(oos);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                Utility.closeQuietly(oos);
            }
        }

        private void readAndClearStore() {
            Exception e;
            Throwable th;
            ObjectInputStream ois = null;
            try {
                ObjectInputStream ois2 = new ObjectInputStream(new BufferedInputStream(this.context.openFileInput("AppEventsLogger.persistedevents")));
                try {
                    HashMap<AccessTokenAppIdPair, List<AppEvent>> obj = (HashMap) ois2.readObject();
                    this.context.getFileStreamPath("AppEventsLogger.persistedevents").delete();
                    this.persistedEvents = obj;
                    Utility.closeQuietly(ois2);
                    ois = ois2;
                } catch (FileNotFoundException e2) {
                    ois = ois2;
                    Utility.closeQuietly(ois);
                } catch (Exception e3) {
                    e = e3;
                    ois = ois2;
                    try {
                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                        Utility.closeQuietly(ois);
                    } catch (Throwable th2) {
                        th = th2;
                        Utility.closeQuietly(ois);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    ois = ois2;
                    Utility.closeQuietly(ois);
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                Utility.closeQuietly(ois);
            } catch (Exception e5) {
                e = e5;
                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                Utility.closeQuietly(ois);
            }
        }

        public void addEvents(AccessTokenAppIdPair accessTokenAppId, List<AppEvent> eventsToPersist) {
            if (!this.persistedEvents.containsKey(accessTokenAppId)) {
                this.persistedEvents.put(accessTokenAppId, new ArrayList());
            }
            ((List) this.persistedEvents.get(accessTokenAppId)).addAll(eventsToPersist);
        }
    }

    static class SessionEventsState {
        private final int MAX_ACCUMULATED_LOG_EVENTS;
        private List<AppEvent> accumulatedEvents;
        private String anonymousAppDeviceGUID;
        private AttributionIdentifiers attributionIdentifiers;
        private List<AppEvent> inFlightEvents;
        private int numSkippedEventsDueToFullBuffer;
        private String packageName;

        public SessionEventsState(AttributionIdentifiers identifiers, String packageName, String anonymousGUID) {
            this.accumulatedEvents = new ArrayList();
            this.inFlightEvents = new ArrayList();
            this.MAX_ACCUMULATED_LOG_EVENTS = 1000;
            this.attributionIdentifiers = identifiers;
            this.packageName = packageName;
            this.anonymousAppDeviceGUID = anonymousGUID;
        }

        public synchronized void addEvent(AppEvent event) {
            if (this.accumulatedEvents.size() + this.inFlightEvents.size() >= 1000) {
                this.numSkippedEventsDueToFullBuffer++;
            } else {
                this.accumulatedEvents.add(event);
            }
        }

        public synchronized int getAccumulatedEventCount() {
            return this.accumulatedEvents.size();
        }

        public synchronized void clearInFlightAndStats(boolean moveToAccumulated) {
            if (moveToAccumulated) {
                this.accumulatedEvents.addAll(this.inFlightEvents);
            }
            this.inFlightEvents.clear();
            this.numSkippedEventsDueToFullBuffer = 0;
        }

        public int populateRequest(GraphRequest request, boolean includeImplicitEvents, boolean limitEventUsage) {
            synchronized (this) {
                int numSkipped = this.numSkippedEventsDueToFullBuffer;
                this.inFlightEvents.addAll(this.accumulatedEvents);
                this.accumulatedEvents.clear();
                JSONArray jsonArray = new JSONArray();
                for (AppEvent event : this.inFlightEvents) {
                    if (includeImplicitEvents || !event.getIsImplicit()) {
                        jsonArray.put(event.getJSONObject());
                    }
                }
                if (jsonArray.length() == 0) {
                    return 0;
                }
                populateRequest(request, numSkipped, jsonArray, limitEventUsage);
                return jsonArray.length();
            }
        }

        public synchronized List<AppEvent> getEventsToPersist() {
            List<AppEvent> result;
            result = this.accumulatedEvents;
            this.accumulatedEvents = new ArrayList();
            return result;
        }

        public synchronized void accumulatePersistedEvents(List<AppEvent> events) {
            this.accumulatedEvents.addAll(events);
        }

        private void populateRequest(GraphRequest request, int numSkipped, JSONArray events, boolean limitEventUsage) {
            JSONObject publishParams;
            try {
                publishParams = AppEventsLoggerUtility.getJSONObjectForGraphAPICall(GraphAPIActivityType.CUSTOM_APP_EVENTS, this.attributionIdentifiers, this.anonymousAppDeviceGUID, limitEventUsage, AppEventsLogger.applicationContext);
                if (this.numSkippedEventsDueToFullBuffer > 0) {
                    publishParams.put("num_skipped_events", numSkipped);
                }
            } catch (JSONException e) {
                publishParams = new JSONObject();
            }
            request.setGraphObject(publishParams);
            Bundle requestParameters = request.getParameters();
            if (requestParameters == null) {
                requestParameters = new Bundle();
            }
            String jsonString = events.toString();
            if (jsonString != null) {
                requestParameters.putByteArray("custom_events_file", getStringAsByteArray(jsonString));
                request.setTag(jsonString);
            }
            request.setParameters(requestParameters);
        }

        private byte[] getStringAsByteArray(String jsonString) {
            byte[] jsonUtf8 = null;
            try {
                jsonUtf8 = jsonString.getBytes("UTF-8");
            } catch (Exception e) {
                Utility.logd("Encoding exception: ", e);
            }
            return jsonUtf8;
        }
    }

    static {
        TAG = AppEventsLogger.class.getCanonicalName();
        stateMap = new ConcurrentHashMap();
        flushBehavior = FlushBehavior.AUTO;
        staticLock = new Object();
    }

    public static void activateApp(Context context) {
        FacebookSdk.sdkInitialize(context);
        activateApp(context, Utility.getMetadataApplicationId(context));
    }

    public static void activateApp(Context context, String applicationId) {
        if (context == null || applicationId == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        if (context instanceof Activity) {
            setSourceApplication((Activity) context);
        } else {
            resetSourceApplication();
            Log.d(AppEventsLogger.class.getName(), "To set source application the context of activateApp must be an instance of Activity");
        }
        FacebookSdk.publishInstallAsync(context, applicationId);
        backgroundExecutor.execute(new C03241(new AppEventsLogger(context, applicationId, null), System.currentTimeMillis(), getSourceApplication()));
    }

    public static void deactivateApp(Context context) {
        deactivateApp(context, Utility.getMetadataApplicationId(context));
    }

    public static void deactivateApp(Context context, String applicationId) {
        if (context == null || applicationId == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        resetSourceApplication();
        backgroundExecutor.execute(new C03252(new AppEventsLogger(context, applicationId, null), System.currentTimeMillis()));
    }

    private void logAppSessionResumeEvent(long eventTime, String sourceApplicationInfo) {
        PersistedAppSessionInfo.onResume(applicationContext, this.accessTokenAppId, this, eventTime, sourceApplicationInfo);
    }

    private void logAppSessionSuspendEvent(long eventTime) {
        PersistedAppSessionInfo.onSuspend(applicationContext, this.accessTokenAppId, this, eventTime);
    }

    public static AppEventsLogger newLogger(Context context) {
        return new AppEventsLogger(context, null, null);
    }

    public static AppEventsLogger newLogger(Context context, String applicationId) {
        return new AppEventsLogger(context, applicationId, null);
    }

    public static FlushBehavior getFlushBehavior() {
        FlushBehavior flushBehavior;
        synchronized (staticLock) {
            flushBehavior = flushBehavior;
        }
        return flushBehavior;
    }

    public void logEvent(String eventName, Bundle parameters) {
        logEvent(eventName, null, parameters, false);
    }

    public void logEvent(String eventName, double valueToSum, Bundle parameters) {
        logEvent(eventName, Double.valueOf(valueToSum), parameters, false);
    }

    public void logSdkEvent(String eventName, Double valueToSum, Bundle parameters) {
        logEvent(eventName, valueToSum, parameters, true);
    }

    private AppEventsLogger(Context context, String applicationId, AccessToken accessToken) {
        Validate.notNull(context, "context");
        this.contextName = Utility.getActivityName(context);
        if (accessToken == null) {
            accessToken = AccessToken.getCurrentAccessToken();
        }
        if (accessToken == null || !(applicationId == null || applicationId.equals(accessToken.getApplicationId()))) {
            if (applicationId == null) {
                applicationId = Utility.getMetadataApplicationId(context);
            }
            this.accessTokenAppId = new AccessTokenAppIdPair(null, applicationId);
        } else {
            this.accessTokenAppId = new AccessTokenAppIdPair(accessToken);
        }
        synchronized (staticLock) {
            if (applicationContext == null) {
                applicationContext = context.getApplicationContext();
            }
        }
        initializeTimersIfNeeded();
    }

    private static void initializeTimersIfNeeded() {
        synchronized (staticLock) {
            if (backgroundExecutor != null) {
                return;
            }
            backgroundExecutor = new ScheduledThreadPoolExecutor(1);
            backgroundExecutor.scheduleAtFixedRate(new C03263(), 0, 15, TimeUnit.SECONDS);
            backgroundExecutor.scheduleAtFixedRate(new C03274(), 0, 86400, TimeUnit.SECONDS);
        }
    }

    private void logEvent(String eventName, Double valueToSum, Bundle parameters, boolean isImplicitlyLogged) {
        logEvent(applicationContext, new AppEvent(this.contextName, eventName, valueToSum, parameters, isImplicitlyLogged), this.accessTokenAppId);
    }

    private static void logEvent(Context context, AppEvent event, AccessTokenAppIdPair accessTokenAppId) {
        FacebookSdk.getExecutor().execute(new C03285(context, accessTokenAppId, event));
    }

    private static void flushIfNecessary() {
        synchronized (staticLock) {
            if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY && getAccumulatedEventCount() > 100) {
                flush(FlushReason.EVENT_THRESHOLD);
            }
        }
    }

    private static int getAccumulatedEventCount() {
        int result;
        synchronized (staticLock) {
            result = 0;
            for (SessionEventsState state : stateMap.values()) {
                result += state.getAccumulatedEventCount();
            }
        }
        return result;
    }

    private static SessionEventsState getSessionEventsState(Context context, AccessTokenAppIdPair accessTokenAppId) {
        Throwable th;
        AttributionIdentifiers attributionIdentifiers = null;
        if (((SessionEventsState) stateMap.get(accessTokenAppId)) == null) {
            attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        }
        synchronized (staticLock) {
            try {
                SessionEventsState state = (SessionEventsState) stateMap.get(accessTokenAppId);
                if (state == null) {
                    SessionEventsState state2 = new SessionEventsState(attributionIdentifiers, context.getPackageName(), getAnonymousAppDeviceGUID(context));
                    try {
                        stateMap.put(accessTokenAppId, state2);
                        state = state2;
                    } catch (Throwable th2) {
                        th = th2;
                        state = state2;
                        throw th;
                    }
                }
                return state;
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    private static SessionEventsState getSessionEventsState(AccessTokenAppIdPair accessTokenAppId) {
        SessionEventsState sessionEventsState;
        synchronized (staticLock) {
            sessionEventsState = (SessionEventsState) stateMap.get(accessTokenAppId);
        }
        return sessionEventsState;
    }

    private static void flush(FlushReason reason) {
        FacebookSdk.getExecutor().execute(new C03296(reason));
    }

    private static void flushAndWait(FlushReason reason) {
        synchronized (staticLock) {
            if (requestInFlight) {
                return;
            }
            requestInFlight = true;
            Set<AccessTokenAppIdPair> keysToFlush = new HashSet(stateMap.keySet());
            accumulatePersistedEvents();
            FlushStatistics flushResults = null;
            try {
                flushResults = buildAndExecuteRequests(reason, keysToFlush);
            } catch (Exception e) {
                Utility.logd(TAG, "Caught unexpected exception while flushing: ", e);
            }
            synchronized (staticLock) {
                requestInFlight = false;
            }
            if (flushResults != null) {
                Intent intent = new Intent("com.facebook.sdk.APP_EVENTS_FLUSHED");
                intent.putExtra("com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED", flushResults.numEvents);
                intent.putExtra("com.facebook.sdk.APP_EVENTS_FLUSH_RESULT", flushResults.result);
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);
            }
        }
    }

    private static FlushStatistics buildAndExecuteRequests(FlushReason reason, Set<AccessTokenAppIdPair> keysToFlush) {
        GraphRequest request;
        FlushStatistics flushResults = new FlushStatistics();
        boolean limitEventUsage = FacebookSdk.getLimitEventAndDataUsage(applicationContext);
        List<GraphRequest> requestsToExecute = new ArrayList();
        for (AccessTokenAppIdPair accessTokenAppId : keysToFlush) {
            SessionEventsState sessionEventsState = getSessionEventsState(accessTokenAppId);
            if (sessionEventsState != null) {
                request = buildRequestForSession(accessTokenAppId, sessionEventsState, limitEventUsage, flushResults);
                if (request != null) {
                    requestsToExecute.add(request);
                }
            }
        }
        if (requestsToExecute.size() <= 0) {
            return null;
        }
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flushing %d events due to %s.", Integer.valueOf(flushResults.numEvents), reason.toString());
        for (GraphRequest request2 : requestsToExecute) {
            request2.executeAndWait();
        }
        return flushResults;
    }

    private static GraphRequest buildRequestForSession(AccessTokenAppIdPair accessTokenAppId, SessionEventsState sessionEventsState, boolean limitEventUsage, FlushStatistics flushState) {
        FetchedAppSettings fetchedAppSettings = Utility.queryAppSettings(accessTokenAppId.getApplicationId(), false);
        GraphRequest postRequest = GraphRequest.newPostRequest(null, String.format("%s/activities", new Object[]{applicationId}), null, null);
        Bundle requestParameters = postRequest.getParameters();
        if (requestParameters == null) {
            requestParameters = new Bundle();
        }
        requestParameters.putString("access_token", accessTokenAppId.getAccessTokenString());
        postRequest.setParameters(requestParameters);
        if (fetchedAppSettings == null) {
            return null;
        }
        int numEvents = sessionEventsState.populateRequest(postRequest, fetchedAppSettings.supportsImplicitLogging(), limitEventUsage);
        if (numEvents == 0) {
            return null;
        }
        flushState.numEvents += numEvents;
        postRequest.setCallback(new C03307(accessTokenAppId, postRequest, sessionEventsState, flushState));
        return postRequest;
    }

    private static void handleResponse(AccessTokenAppIdPair accessTokenAppId, GraphRequest request, GraphResponse response, SessionEventsState sessionEventsState, FlushStatistics flushState) {
        FacebookRequestError error = response.getError();
        String resultDescription = "Success";
        FlushResult flushResult = FlushResult.SUCCESS;
        if (error != null) {
            if (error.getErrorCode() == -1) {
                resultDescription = "Failed: No Connectivity";
                flushResult = FlushResult.NO_CONNECTIVITY;
            } else {
                resultDescription = String.format("Failed:\n  Response: %s\n  Error %s", new Object[]{response.toString(), error.toString()});
                flushResult = FlushResult.SERVER_ERROR;
            }
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.APP_EVENTS)) {
            String prettyPrintedEvents;
            try {
                prettyPrintedEvents = new JSONArray((String) request.getTag()).toString(2);
            } catch (JSONException e) {
                prettyPrintedEvents = "<Can't encode events for debug logging>";
            }
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flush completed\nParams: %s\n  Result: %s\n  Events JSON: %s", request.getGraphObject().toString(), resultDescription, prettyPrintedEvents);
        }
        sessionEventsState.clearInFlightAndStats(error != null);
        if (flushResult == FlushResult.NO_CONNECTIVITY) {
            PersistedEvents.persistEvents(applicationContext, accessTokenAppId, sessionEventsState);
        }
        if (flushResult != FlushResult.SUCCESS && flushState.result != FlushResult.NO_CONNECTIVITY) {
            flushState.result = flushResult;
        }
    }

    private static int accumulatePersistedEvents() {
        PersistedEvents persistedEvents = PersistedEvents.readAndClearStore(applicationContext);
        int result = 0;
        for (AccessTokenAppIdPair accessTokenAppId : persistedEvents.keySet()) {
            SessionEventsState sessionEventsState = getSessionEventsState(applicationContext, accessTokenAppId);
            List<AppEvent> events = persistedEvents.getEvents(accessTokenAppId);
            sessionEventsState.accumulatePersistedEvents(events);
            result += events.size();
        }
        return result;
    }

    private static void setSourceApplication(Activity activity) {
        ComponentName callingApplication = activity.getCallingActivity();
        if (callingApplication != null) {
            String callingApplicationPackage = callingApplication.getPackageName();
            if (callingApplicationPackage.equals(activity.getPackageName())) {
                resetSourceApplication();
                return;
            }
            sourceApplication = callingApplicationPackage;
        }
        Intent openIntent = activity.getIntent();
        if (openIntent == null || openIntent.getBooleanExtra("_fbSourceApplicationHasBeenSet", false)) {
            resetSourceApplication();
            return;
        }
        Bundle applinkData = AppLinks.getAppLinkData(openIntent);
        if (applinkData == null) {
            resetSourceApplication();
            return;
        }
        isOpenedByApplink = true;
        Bundle applinkReferrerData = applinkData.getBundle("referer_app_link");
        if (applinkReferrerData == null) {
            sourceApplication = null;
            return;
        }
        sourceApplication = applinkReferrerData.getString("package");
        openIntent.putExtra("_fbSourceApplicationHasBeenSet", true);
    }

    static String getSourceApplication() {
        String openType = "Unclassified";
        if (isOpenedByApplink) {
            openType = "Applink";
        }
        if (sourceApplication != null) {
            return openType + "(" + sourceApplication + ")";
        }
        return openType;
    }

    static void resetSourceApplication() {
        sourceApplication = null;
        isOpenedByApplink = false;
    }

    public static String getAnonymousAppDeviceGUID(Context context) {
        if (anonymousAppDeviceGUID == null) {
            synchronized (staticLock) {
                if (anonymousAppDeviceGUID == null) {
                    anonymousAppDeviceGUID = context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).getString("anonymousAppDeviceGUID", null);
                    if (anonymousAppDeviceGUID == null) {
                        anonymousAppDeviceGUID = "XZ" + UUID.randomUUID().toString();
                        context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).edit().putString("anonymousAppDeviceGUID", anonymousAppDeviceGUID).apply();
                    }
                }
            }
        }
        return anonymousAppDeviceGUID;
    }
}
