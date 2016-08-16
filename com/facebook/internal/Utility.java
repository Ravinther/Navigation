package com.facebook.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utility {
    private static final String[] APP_SETTING_FIELDS;
    private static long availableExternalStorageGB;
    private static String carrierName;
    private static String deviceTimezone;
    private static Map<String, FetchedAppSettings> fetchedAppSettings;
    private static AtomicBoolean loadingSettings;
    private static int numCPUCores;
    private static long timestampOfLastCheck;
    private static long totalExternalStorageGB;

    public interface GraphMeRequestWithCacheCallback {
        void onFailure(FacebookException facebookException);

        void onSuccess(JSONObject jSONObject);
    }

    /* renamed from: com.facebook.internal.Utility.1 */
    static class C03561 implements Runnable {
        final /* synthetic */ String val$applicationId;
        final /* synthetic */ Context val$context;
        final /* synthetic */ String val$settingsKey;

        C03561(String str, Context context, String str2) {
            this.val$applicationId = str;
            this.val$context = context;
            this.val$settingsKey = str2;
        }

        public void run() {
            JSONObject resultJSON = Utility.getAppSettingsQueryResponse(this.val$applicationId);
            if (resultJSON != null) {
                Utility.parseAppSettingsFromJSON(this.val$applicationId, resultJSON);
                this.val$context.getSharedPreferences("com.facebook.internal.preferences.APP_SETTINGS", 0).edit().putString(this.val$settingsKey, resultJSON.toString()).apply();
            }
            Utility.loadingSettings.set(false);
        }
    }

    /* renamed from: com.facebook.internal.Utility.2 */
    static class C03572 implements Callback {
        final /* synthetic */ String val$accessToken;
        final /* synthetic */ GraphMeRequestWithCacheCallback val$callback;

        C03572(GraphMeRequestWithCacheCallback graphMeRequestWithCacheCallback, String str) {
            this.val$callback = graphMeRequestWithCacheCallback;
            this.val$accessToken = str;
        }

        public void onCompleted(GraphResponse response) {
            if (response.getError() != null) {
                this.val$callback.onFailure(response.getError().getException());
                return;
            }
            ProfileInformationCache.putProfileInformation(this.val$accessToken, response.getJSONObject());
            this.val$callback.onSuccess(response.getJSONObject());
        }
    }

    /* renamed from: com.facebook.internal.Utility.3 */
    static class C03583 implements FilenameFilter {
        C03583() {
        }

        public boolean accept(File dir, String fileName) {
            return Pattern.matches("cpu[0-9]+", fileName);
        }
    }

    public static class DialogFeatureConfig {
        private String dialogName;
        private Uri fallbackUrl;
        private String featureName;
        private int[] featureVersionSpec;

        private static DialogFeatureConfig parseDialogConfig(JSONObject dialogConfigJSON) {
            String dialogNameWithFeature = dialogConfigJSON.optString("name");
            if (Utility.isNullOrEmpty(dialogNameWithFeature)) {
                return null;
            }
            String[] components = dialogNameWithFeature.split("\\|");
            if (components.length != 2) {
                return null;
            }
            String dialogName = components[0];
            String featureName = components[1];
            if (Utility.isNullOrEmpty(dialogName) || Utility.isNullOrEmpty(featureName)) {
                return null;
            }
            String urlString = dialogConfigJSON.optString("url");
            Uri fallbackUri = null;
            if (!Utility.isNullOrEmpty(urlString)) {
                fallbackUri = Uri.parse(urlString);
            }
            return new DialogFeatureConfig(dialogName, featureName, fallbackUri, parseVersionSpec(dialogConfigJSON.optJSONArray("versions")));
        }

        private static int[] parseVersionSpec(JSONArray versionsJSON) {
            int[] versionSpec = null;
            if (versionsJSON != null) {
                int numVersions = versionsJSON.length();
                versionSpec = new int[numVersions];
                for (int i = 0; i < numVersions; i++) {
                    int version = versionsJSON.optInt(i, -1);
                    if (version == -1) {
                        String versionString = versionsJSON.optString(i);
                        if (!Utility.isNullOrEmpty(versionString)) {
                            try {
                                version = Integer.parseInt(versionString);
                            } catch (Exception nfe) {
                                Utility.logd("FacebookSDK", nfe);
                                version = -1;
                            }
                        }
                    }
                    versionSpec[i] = version;
                }
            }
            return versionSpec;
        }

        private DialogFeatureConfig(String dialogName, String featureName, Uri fallbackUrl, int[] featureVersionSpec) {
            this.dialogName = dialogName;
            this.featureName = featureName;
            this.fallbackUrl = fallbackUrl;
            this.featureVersionSpec = featureVersionSpec;
        }

        public String getDialogName() {
            return this.dialogName;
        }

        public String getFeatureName() {
            return this.featureName;
        }

        public int[] getVersionSpec() {
            return this.featureVersionSpec;
        }
    }

    public static class FetchedAppSettings {
        private Map<String, Map<String, DialogFeatureConfig>> dialogConfigMap;
        private FacebookRequestErrorClassification errorClassification;
        private String nuxContent;
        private boolean nuxEnabled;
        private boolean supportsImplicitLogging;

        private FetchedAppSettings(boolean supportsImplicitLogging, String nuxContent, boolean nuxEnabled, Map<String, Map<String, DialogFeatureConfig>> dialogConfigMap, FacebookRequestErrorClassification errorClassification) {
            this.supportsImplicitLogging = supportsImplicitLogging;
            this.nuxContent = nuxContent;
            this.nuxEnabled = nuxEnabled;
            this.dialogConfigMap = dialogConfigMap;
            this.errorClassification = errorClassification;
        }

        public boolean supportsImplicitLogging() {
            return this.supportsImplicitLogging;
        }

        public String getNuxContent() {
            return this.nuxContent;
        }

        public boolean getNuxEnabled() {
            return this.nuxEnabled;
        }

        public Map<String, Map<String, DialogFeatureConfig>> getDialogConfigurations() {
            return this.dialogConfigMap;
        }

        public FacebookRequestErrorClassification getErrorClassification() {
            return this.errorClassification;
        }
    }

    public interface Mapper<T, K> {
        K apply(T t);
    }

    static {
        APP_SETTING_FIELDS = new String[]{"supports_implicit_sdk_logging", "gdpv4_nux_content", "gdpv4_nux_enabled", "android_dialog_configs", "android_sdk_error_categories"};
        fetchedAppSettings = new ConcurrentHashMap();
        loadingSettings = new AtomicBoolean(false);
        numCPUCores = 0;
        timestampOfLastCheck = -1;
        totalExternalStorageGB = -1;
        availableExternalStorageGB = -1;
        deviceTimezone = "";
        carrierName = "NoCarrier";
    }

    public static <T> boolean isNullOrEmpty(Collection<T> c) {
        return c == null || c.size() == 0;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static <T> Collection<T> unmodifiableCollection(T... ts) {
        return Collections.unmodifiableCollection(Arrays.asList(ts));
    }

    public static String md5hash(String key) {
        return hashWithAlgorithm("MD5", key);
    }

    public static String sha1hash(byte[] bytes) {
        return hashWithAlgorithm("SHA-1", bytes);
    }

    private static String hashWithAlgorithm(String algorithm, String key) {
        return hashWithAlgorithm(algorithm, key.getBytes());
    }

    private static String hashWithAlgorithm(String algorithm, byte[] bytes) {
        try {
            return hashBytes(MessageDigest.getInstance(algorithm), bytes);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String hashBytes(MessageDigest hash, byte[] bytes) {
        hash.update(bytes);
        byte[] digest = hash.digest();
        StringBuilder builder = new StringBuilder();
        for (int b : digest) {
            builder.append(Integer.toHexString((b >> 4) & 15));
            builder.append(Integer.toHexString((b >> 0) & 15));
        }
        return builder.toString();
    }

    public static Uri buildUri(String authority, String path, Bundle parameters) {
        Builder builder = new Builder();
        builder.scheme("https");
        builder.authority(authority);
        builder.path(path);
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                Object parameter = parameters.get(key);
                if (parameter instanceof String) {
                    builder.appendQueryParameter(key, (String) parameter);
                }
            }
        }
        return builder.build();
    }

    public static Bundle parseUrlQueryString(String queryString) {
        Bundle params = new Bundle();
        if (!isNullOrEmpty(queryString)) {
            for (String parameter : queryString.split("&")) {
                String[] keyValuePair = parameter.split("=");
                try {
                    if (keyValuePair.length == 2) {
                        params.putString(URLDecoder.decode(keyValuePair[0], "UTF-8"), URLDecoder.decode(keyValuePair[1], "UTF-8"));
                    } else if (keyValuePair.length == 1) {
                        params.putString(URLDecoder.decode(keyValuePair[0], "UTF-8"), "");
                    }
                } catch (Exception e) {
                    logd("FacebookSDK", e);
                }
            }
        }
        return params;
    }

    public static void putNonEmptyString(Bundle b, String key, String value) {
        if (!isNullOrEmpty(value)) {
            b.putString(key, value);
        }
    }

    public static void putUri(Bundle b, String key, Uri uri) {
        if (uri != null) {
            putNonEmptyString(b, key, uri.toString());
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void disconnectQuietly(URLConnection connection) {
        if (connection instanceof HttpURLConnection) {
            ((HttpURLConnection) connection).disconnect();
        }
    }

    public static String getMetadataApplicationId(Context context) {
        Validate.notNull(context, "context");
        FacebookSdk.sdkInitialize(context);
        return FacebookSdk.getApplicationId();
    }

    public static Object getStringPropertyAsJSON(JSONObject jsonObject, String key, String nonJSONPropertyKey) throws JSONException {
        JSONObject value = jsonObject.opt(key);
        if (value != null && (value instanceof String)) {
            value = new JSONTokener((String) value).nextValue();
        }
        if (value == null || (value instanceof JSONObject) || (value instanceof JSONArray)) {
            return value;
        }
        if (nonJSONPropertyKey != null) {
            jsonObject = new JSONObject();
            jsonObject.putOpt(nonJSONPropertyKey, value);
            return jsonObject;
        }
        throw new FacebookException("Got an unexpected non-JSON object.");
    }

    public static String readStreamToString(InputStream inputStream) throws IOException {
        Throwable th;
        BufferedInputStream bufferedInputStream = null;
        InputStreamReader reader = null;
        try {
            InputStreamReader reader2;
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
            try {
                reader2 = new InputStreamReader(bufferedInputStream2);
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = bufferedInputStream2;
                closeQuietly(bufferedInputStream);
                closeQuietly(reader);
                throw th;
            }
            try {
                StringBuilder stringBuilder = new StringBuilder();
                char[] buffer = new char[2048];
                while (true) {
                    int n = reader2.read(buffer);
                    if (n != -1) {
                        stringBuilder.append(buffer, 0, n);
                    } else {
                        String stringBuilder2 = stringBuilder.toString();
                        closeQuietly(bufferedInputStream2);
                        closeQuietly(reader2);
                        return stringBuilder2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                reader = reader2;
                bufferedInputStream = bufferedInputStream2;
                closeQuietly(bufferedInputStream);
                closeQuietly(reader);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            closeQuietly(bufferedInputStream);
            closeQuietly(reader);
            throw th;
        }
    }

    public static int copyAndCloseInputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        Throwable th;
        BufferedInputStream bufferedInputStream = null;
        int totalBytes = 0;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
            try {
                byte[] buffer = new byte[8192];
                while (true) {
                    int bytesRead = bufferedInputStream2.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                return totalBytes;
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    private static void clearCookiesForDomain(Context context, String domain) {
        CookieSyncManager.createInstance(context).sync();
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(domain);
        if (cookies != null) {
            for (String cookie : cookies.split(";")) {
                String[] cookieParts = cookie.split("=");
                if (cookieParts.length > 0) {
                    cookieManager.setCookie(domain, cookieParts[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;");
                }
            }
            cookieManager.removeExpiredCookie();
        }
    }

    public static void clearFacebookCookies(Context context) {
        clearCookiesForDomain(context, "facebook.com");
        clearCookiesForDomain(context, ".facebook.com");
        clearCookiesForDomain(context, "https://facebook.com");
        clearCookiesForDomain(context, "https://.facebook.com");
    }

    public static void logd(String tag, Exception e) {
        if (FacebookSdk.isDebugEnabled() && tag != null && e != null) {
            Log.d(tag, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static void logd(String tag, String msg) {
        if (FacebookSdk.isDebugEnabled() && tag != null && msg != null) {
            Log.d(tag, msg);
        }
    }

    public static void logd(String tag, String msg, Throwable t) {
        if (FacebookSdk.isDebugEnabled() && !isNullOrEmpty(tag)) {
            Log.d(tag, msg, t);
        }
    }

    public static <T> boolean areObjectsEqual(T a, T b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
    }

    public static void loadAppSettingsAsync(Context context, String applicationId) {
        boolean canStartLoading = loadingSettings.compareAndSet(false, true);
        if (!isNullOrEmpty(applicationId) && !fetchedAppSettings.containsKey(applicationId) && canStartLoading) {
            String settingsKey = String.format("com.facebook.internal.APP_SETTINGS.%s", new Object[]{applicationId});
            FacebookSdk.getExecutor().execute(new C03561(applicationId, context, settingsKey));
            String settingsJSONString = context.getSharedPreferences("com.facebook.internal.preferences.APP_SETTINGS", 0).getString(settingsKey, null);
            if (!isNullOrEmpty(settingsJSONString)) {
                JSONObject settingsJSON = null;
                try {
                    settingsJSON = new JSONObject(settingsJSONString);
                } catch (Exception je) {
                    logd("FacebookSDK", je);
                }
                if (settingsJSON != null) {
                    parseAppSettingsFromJSON(applicationId, settingsJSON);
                }
            }
        }
    }

    public static FetchedAppSettings getAppSettingsWithoutQuery(String applicationId) {
        return applicationId != null ? (FetchedAppSettings) fetchedAppSettings.get(applicationId) : null;
    }

    public static FetchedAppSettings queryAppSettings(String applicationId, boolean forceRequery) {
        if (!forceRequery && fetchedAppSettings.containsKey(applicationId)) {
            return (FetchedAppSettings) fetchedAppSettings.get(applicationId);
        }
        JSONObject response = getAppSettingsQueryResponse(applicationId);
        if (response == null) {
            return null;
        }
        return parseAppSettingsFromJSON(applicationId, response);
    }

    private static FetchedAppSettings parseAppSettingsFromJSON(String applicationId, JSONObject settingsJSON) {
        FacebookRequestErrorClassification errorClassification;
        JSONArray errorClassificationJSON = settingsJSON.optJSONArray("android_sdk_error_categories");
        if (errorClassificationJSON == null) {
            errorClassification = FacebookRequestErrorClassification.getDefaultErrorClassification();
        } else {
            errorClassification = FacebookRequestErrorClassification.createFromJSON(errorClassificationJSON);
        }
        FetchedAppSettings result = new FetchedAppSettings(settingsJSON.optString("gdpv4_nux_content", ""), settingsJSON.optBoolean("gdpv4_nux_enabled", false), parseDialogConfigurations(settingsJSON.optJSONObject("android_dialog_configs")), errorClassification, null);
        fetchedAppSettings.put(applicationId, result);
        return result;
    }

    private static JSONObject getAppSettingsQueryResponse(String applicationId) {
        Bundle appSettingsParams = new Bundle();
        appSettingsParams.putString("fields", TextUtils.join(",", APP_SETTING_FIELDS));
        GraphRequest request = GraphRequest.newGraphPathRequest(null, applicationId, null);
        request.setSkipClientToken(true);
        request.setParameters(appSettingsParams);
        return request.executeAndWait().getJSONObject();
    }

    public static DialogFeatureConfig getDialogFeatureConfig(String applicationId, String actionName, String featureName) {
        if (isNullOrEmpty(actionName) || isNullOrEmpty(featureName)) {
            return null;
        }
        FetchedAppSettings settings = (FetchedAppSettings) fetchedAppSettings.get(applicationId);
        if (settings == null) {
            return null;
        }
        Map<String, DialogFeatureConfig> featureMap = (Map) settings.getDialogConfigurations().get(actionName);
        if (featureMap != null) {
            return (DialogFeatureConfig) featureMap.get(featureName);
        }
        return null;
    }

    private static Map<String, Map<String, DialogFeatureConfig>> parseDialogConfigurations(JSONObject dialogConfigResponse) {
        HashMap<String, Map<String, DialogFeatureConfig>> dialogConfigMap = new HashMap();
        if (dialogConfigResponse != null) {
            JSONArray dialogConfigData = dialogConfigResponse.optJSONArray(PoiFragment.ARG_DATA);
            if (dialogConfigData != null) {
                for (int i = 0; i < dialogConfigData.length(); i++) {
                    DialogFeatureConfig dialogConfig = DialogFeatureConfig.parseDialogConfig(dialogConfigData.optJSONObject(i));
                    if (dialogConfig != null) {
                        String dialogName = dialogConfig.getDialogName();
                        Map<String, DialogFeatureConfig> featureMap = (Map) dialogConfigMap.get(dialogName);
                        if (featureMap == null) {
                            featureMap = new HashMap();
                            dialogConfigMap.put(dialogName, featureMap);
                        }
                        featureMap.put(dialogConfig.getFeatureName(), dialogConfig);
                    }
                }
            }
        }
        return dialogConfigMap;
    }

    public static void deleteDirectory(File directoryOrFile) {
        if (directoryOrFile.exists()) {
            if (directoryOrFile.isDirectory()) {
                for (File child : directoryOrFile.listFiles()) {
                    deleteDirectory(child);
                }
            }
            directoryOrFile.delete();
        }
    }

    public static <T> List<T> asListNoNulls(T... array) {
        ArrayList<T> result = new ArrayList();
        for (T t : array) {
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }

    public static List<String> jsonArrayToStringList(JSONArray jsonArray) throws JSONException {
        ArrayList<String> result = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }
        return result;
    }

    public static Set<String> jsonArrayToSet(JSONArray jsonArray) throws JSONException {
        Set<String> result = new HashSet();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }
        return result;
    }

    public static void setAppEventAttributionParameters(JSONObject params, AttributionIdentifiers attributionIdentifiers, String anonymousAppDeviceGUID, boolean limitEventUsage) throws JSONException {
        boolean z = true;
        if (!(attributionIdentifiers == null || attributionIdentifiers.getAttributionId() == null)) {
            params.put("attribution", attributionIdentifiers.getAttributionId());
        }
        if (!(attributionIdentifiers == null || attributionIdentifiers.getAndroidAdvertiserId() == null)) {
            params.put("advertiser_id", attributionIdentifiers.getAndroidAdvertiserId());
            params.put("advertiser_tracking_enabled", !attributionIdentifiers.isTrackingLimited());
        }
        if (!(attributionIdentifiers == null || attributionIdentifiers.getAndroidInstallerPackage() == null)) {
            params.put("installer_package", attributionIdentifiers.getAndroidInstallerPackage());
        }
        params.put("anon_id", anonymousAppDeviceGUID);
        String str = "application_tracking_enabled";
        if (limitEventUsage) {
            z = false;
        }
        params.put(str, z);
    }

    public static void setAppEventExtendedDeviceInfoParameters(JSONObject params, Context appContext) throws JSONException {
        Locale locale;
        JSONArray extraInfoArray = new JSONArray();
        extraInfoArray.put("a2");
        refreshPeriodicExtendedDeviceInfo(appContext);
        String pkgName = appContext.getPackageName();
        int versionCode = -1;
        String versionName = "";
        try {
            PackageInfo pi = appContext.getPackageManager().getPackageInfo(pkgName, 0);
            versionCode = pi.versionCode;
            versionName = pi.versionName;
        } catch (NameNotFoundException e) {
        }
        extraInfoArray.put(pkgName);
        extraInfoArray.put(versionCode);
        extraInfoArray.put(versionName);
        extraInfoArray.put(VERSION.RELEASE);
        extraInfoArray.put(Build.MODEL);
        try {
            locale = appContext.getResources().getConfiguration().locale;
        } catch (Exception e2) {
            locale = Locale.getDefault();
        }
        extraInfoArray.put(locale.getLanguage() + "_" + locale.getCountry());
        extraInfoArray.put(deviceTimezone);
        extraInfoArray.put(carrierName);
        int width = 0;
        int height = 0;
        double density = 0.0d;
        try {
            WindowManager wm = (WindowManager) appContext.getSystemService("window");
            if (wm != null) {
                Display display = wm.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                width = displayMetrics.widthPixels;
                height = displayMetrics.heightPixels;
                density = (double) displayMetrics.density;
            }
        } catch (Exception e3) {
        }
        extraInfoArray.put(width);
        extraInfoArray.put(height);
        extraInfoArray.put(String.format("%.2f", new Object[]{Double.valueOf(density)}));
        extraInfoArray.put(refreshBestGuessNumberOfCPUCores());
        extraInfoArray.put(totalExternalStorageGB);
        extraInfoArray.put(availableExternalStorageGB);
        params.put("extinfo", extraInfoArray.toString());
    }

    public static Method getMethodQuietly(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method getMethodQuietly(String className, String methodName, Class<?>... parameterTypes) {
        try {
            return getMethodQuietly(Class.forName(className), methodName, (Class[]) parameterTypes);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Object invokeMethodQuietly(Object receiver, Method method, Object... args) {
        Object obj = null;
        try {
            obj = method.invoke(receiver, args);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e2) {
        }
        return obj;
    }

    public static String getActivityName(Context context) {
        if (context == null) {
            return "null";
        }
        if (context == context.getApplicationContext()) {
            return "unknown";
        }
        return context.getClass().getSimpleName();
    }

    public static <T, K> List<K> map(List<T> target, Mapper<T, K> mapper) {
        if (target == null) {
            return null;
        }
        List<K> list = new ArrayList();
        for (T item : target) {
            K mappedItem = mapper.apply(item);
            if (mappedItem != null) {
                list.add(mappedItem);
            }
        }
        if (list.size() == 0) {
            list = null;
        }
        return list;
    }

    public static String getUriString(Uri uri) {
        return uri == null ? null : uri.toString();
    }

    public static boolean isWebUri(Uri uri) {
        return uri != null && ("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()));
    }

    public static boolean isContentUri(Uri uri) {
        return uri != null && "content".equalsIgnoreCase(uri.getScheme());
    }

    public static boolean isFileUri(Uri uri) {
        return uri != null && "file".equalsIgnoreCase(uri.getScheme());
    }

    public static long getContentSize(Uri contentUri) {
        Cursor cursor = null;
        try {
            cursor = FacebookSdk.getApplicationContext().getContentResolver().query(contentUri, null, null, null, null);
            int sizeIndex = cursor.getColumnIndex("_size");
            cursor.moveToFirst();
            long j = cursor.getLong(sizeIndex);
            return j;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Date getBundleLongAsDate(Bundle bundle, String key, Date dateBase) {
        if (bundle == null) {
            return null;
        }
        long secondsFromBase;
        Object secondsObject = bundle.get(key);
        if (secondsObject instanceof Long) {
            secondsFromBase = ((Long) secondsObject).longValue();
        } else if (!(secondsObject instanceof String)) {
            return null;
        } else {
            try {
                secondsFromBase = Long.parseLong((String) secondsObject);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if (secondsFromBase == 0) {
            return new Date(Long.MAX_VALUE);
        }
        return new Date(dateBase.getTime() + (1000 * secondsFromBase));
    }

    public static void writeStringMapToParcel(Parcel parcel, Map<String, String> map) {
        if (map == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(map.size());
        for (Entry<String, String> entry : map.entrySet()) {
            parcel.writeString((String) entry.getKey());
            parcel.writeString((String) entry.getValue());
        }
    }

    public static Map<String, String> readStringMapFromParcel(Parcel parcel) {
        int size = parcel.readInt();
        if (size < 0) {
            return null;
        }
        Map<String, String> map = new HashMap();
        for (int i = 0; i < size; i++) {
            map.put(parcel.readString(), parcel.readString());
        }
        return map;
    }

    public static boolean isCurrentAccessToken(AccessToken token) {
        return token != null ? token.equals(AccessToken.getCurrentAccessToken()) : false;
    }

    public static void getGraphMeRequestWithCacheAsync(String accessToken, GraphMeRequestWithCacheCallback callback) {
        JSONObject cachedValue = ProfileInformationCache.getProfileInformation(accessToken);
        if (cachedValue != null) {
            callback.onSuccess(cachedValue);
            return;
        }
        Callback graphCallback = new C03572(callback, accessToken);
        GraphRequest graphRequest = getGraphMeRequestWithCache(accessToken);
        graphRequest.setCallback(graphCallback);
        graphRequest.executeAsync();
    }

    public static JSONObject awaitGetGraphMeRequestWithCache(String accessToken) {
        JSONObject cachedValue = ProfileInformationCache.getProfileInformation(accessToken);
        if (cachedValue != null) {
            return cachedValue;
        }
        GraphResponse response = getGraphMeRequestWithCache(accessToken).executeAndWait();
        if (response.getError() != null) {
            return null;
        }
        return response.getJSONObject();
    }

    private static GraphRequest getGraphMeRequestWithCache(String accessToken) {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,middle_name,last_name,link");
        parameters.putString("access_token", accessToken);
        return new GraphRequest(null, "me", parameters, HttpMethod.GET, null);
    }

    private static int refreshBestGuessNumberOfCPUCores() {
        if (numCPUCores > 0) {
            return numCPUCores;
        }
        if (VERSION.SDK_INT <= 10) {
            numCPUCores = 1;
            return numCPUCores;
        }
        try {
            numCPUCores = new File("/sys/devices/system/cpu/").listFiles(new C03583()).length;
        } catch (Exception e) {
        }
        if (numCPUCores <= 0) {
            numCPUCores = Math.max(Runtime.getRuntime().availableProcessors(), 1);
        }
        return numCPUCores;
    }

    private static void refreshPeriodicExtendedDeviceInfo(Context appContext) {
        if (timestampOfLastCheck == -1 || System.currentTimeMillis() - timestampOfLastCheck >= 1800000) {
            timestampOfLastCheck = System.currentTimeMillis();
            refreshTimezone();
            refreshCarrierName(appContext);
            refreshTotalExternalStorage();
            refreshAvailableExternalStorage();
        }
    }

    private static void refreshTimezone() {
        try {
            TimeZone tz = TimeZone.getDefault();
            deviceTimezone = tz.getDisplayName(tz.inDaylightTime(new Date()), 0);
        } catch (Exception e) {
        }
    }

    private static void refreshCarrierName(Context appContext) {
        if (carrierName.equals("NoCarrier")) {
            try {
                carrierName = ((TelephonyManager) appContext.getSystemService("phone")).getNetworkOperatorName();
            } catch (Exception e) {
            }
        }
    }

    private static boolean externalStorageExists() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    private static void refreshAvailableExternalStorage() {
        try {
            if (externalStorageExists()) {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                availableExternalStorageGB = ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
            }
            availableExternalStorageGB = convertBytesToGB((double) availableExternalStorageGB);
        } catch (Exception e) {
        }
    }

    private static void refreshTotalExternalStorage() {
        try {
            if (externalStorageExists()) {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                totalExternalStorageGB = ((long) stat.getBlockCount()) * ((long) stat.getBlockSize());
            }
            totalExternalStorageGB = convertBytesToGB((double) totalExternalStorageGB);
        } catch (Exception e) {
        }
    }

    private static long convertBytesToGB(double bytes) {
        return Math.round(bytes / 1.073741824E9d);
    }
}
