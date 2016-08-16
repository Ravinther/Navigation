package com.facebook.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.facebook.login.DefaultAudience;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NativeProtocol {
    private static final NativeAppInfo FACEBOOK_APP_INFO;
    private static final List<Integer> KNOWN_PROTOCOL_VERSIONS;
    private static Map<String, List<NativeAppInfo>> actionToAppInfoMap;
    private static List<NativeAppInfo> facebookAppInfoList;
    private static AtomicBoolean protocolVersionsAsyncUpdating;

    /* renamed from: com.facebook.internal.NativeProtocol.1 */
    static class C03541 implements Runnable {
        C03541() {
        }

        public void run() {
            try {
                for (NativeAppInfo appInfo : NativeProtocol.facebookAppInfoList) {
                    appInfo.fetchAvailableVersions(true);
                }
            } finally {
                NativeProtocol.protocolVersionsAsyncUpdating.set(false);
            }
        }
    }

    private static abstract class NativeAppInfo {
        private static final HashSet<String> validAppSignatureHashes;
        private TreeSet<Integer> availableVersions;

        protected abstract String getPackage();

        private NativeAppInfo() {
        }

        static {
            validAppSignatureHashes = buildAppSignatureHashes();
        }

        private static HashSet<String> buildAppSignatureHashes() {
            HashSet<String> set = new HashSet();
            set.add("8a3c4b262d721acd49a4bf97d5213199c86fa2b9");
            set.add("a4b7452e2ed8f5f191058ca7bbfd26b0d3214bfc");
            set.add("5e8f16062ea3cd2c4a0d547876baa6f38cabf625");
            return set;
        }

        public boolean validateSignature(Context context, String packageName) {
            String brand = Build.BRAND;
            int applicationFlags = context.getApplicationInfo().flags;
            if (brand.startsWith("generic") && (applicationFlags & 2) != 0) {
                return true;
            }
            try {
                for (Signature signature : context.getPackageManager().getPackageInfo(packageName, 64).signatures) {
                    if (validAppSignatureHashes.contains(Utility.sha1hash(signature.toByteArray()))) {
                        return true;
                    }
                }
                return false;
            } catch (NameNotFoundException e) {
                return false;
            }
        }

        public TreeSet<Integer> getAvailableVersions() {
            if (this.availableVersions == null) {
                fetchAvailableVersions(false);
            }
            return this.availableVersions;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private synchronized void fetchAvailableVersions(boolean r2) {
            /*
            r1 = this;
            monitor-enter(r1);
            if (r2 != 0) goto L_0x0007;
        L_0x0003:
            r0 = r1.availableVersions;	 Catch:{ all -> 0x000f }
            if (r0 != 0) goto L_0x000d;
        L_0x0007:
            r0 = com.facebook.internal.NativeProtocol.fetchAllAvailableProtocolVersionsForAppInfo(r1);	 Catch:{ all -> 0x000f }
            r1.availableVersions = r0;	 Catch:{ all -> 0x000f }
        L_0x000d:
            monitor-exit(r1);
            return;
        L_0x000f:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.NativeProtocol.NativeAppInfo.fetchAvailableVersions(boolean):void");
        }
    }

    private static class KatanaAppInfo extends NativeAppInfo {
        private KatanaAppInfo() {
            super();
        }

        protected String getPackage() {
            return "com.facebook.katana";
        }
    }

    private static class MessengerAppInfo extends NativeAppInfo {
        private MessengerAppInfo() {
            super();
        }

        protected String getPackage() {
            return "com.facebook.orca";
        }
    }

    private static class WakizashiAppInfo extends NativeAppInfo {
        private WakizashiAppInfo() {
            super();
        }

        protected String getPackage() {
            return "com.facebook.wakizashi";
        }
    }

    static {
        FACEBOOK_APP_INFO = new KatanaAppInfo();
        facebookAppInfoList = buildFacebookAppList();
        actionToAppInfoMap = buildActionToAppInfoMap();
        protocolVersionsAsyncUpdating = new AtomicBoolean(false);
        KNOWN_PROTOCOL_VERSIONS = Arrays.asList(new Integer[]{Integer.valueOf(20141218), Integer.valueOf(20141107), Integer.valueOf(20141028), Integer.valueOf(20141001), Integer.valueOf(20140701), Integer.valueOf(20140324), Integer.valueOf(20140204), Integer.valueOf(20131107), Integer.valueOf(20130618), Integer.valueOf(20130502), Integer.valueOf(20121101)});
    }

    private static List<NativeAppInfo> buildFacebookAppList() {
        List<NativeAppInfo> list = new ArrayList();
        list.add(FACEBOOK_APP_INFO);
        list.add(new WakizashiAppInfo());
        return list;
    }

    private static Map<String, List<NativeAppInfo>> buildActionToAppInfoMap() {
        Map<String, List<NativeAppInfo>> map = new HashMap();
        ArrayList<NativeAppInfo> messengerAppInfoList = new ArrayList();
        messengerAppInfoList.add(new MessengerAppInfo());
        map.put("com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG", facebookAppInfoList);
        map.put("com.facebook.platform.action.request.FEED_DIALOG", facebookAppInfoList);
        map.put("com.facebook.platform.action.request.LIKE_DIALOG", facebookAppInfoList);
        map.put("com.facebook.platform.action.request.APPINVITES_DIALOG", facebookAppInfoList);
        map.put("com.facebook.platform.action.request.MESSAGE_DIALOG", messengerAppInfoList);
        map.put("com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG", messengerAppInfoList);
        return map;
    }

    static Intent validateActivityIntent(Context context, Intent intent, NativeAppInfo appInfo) {
        if (intent == null) {
            return null;
        }
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfo == null) {
            return null;
        }
        if (appInfo.validateSignature(context, resolveInfo.activityInfo.packageName)) {
            return intent;
        }
        return null;
    }

    static Intent validateServiceIntent(Context context, Intent intent, NativeAppInfo appInfo) {
        if (intent == null) {
            return null;
        }
        ResolveInfo resolveInfo = context.getPackageManager().resolveService(intent, 0);
        if (resolveInfo == null) {
            return null;
        }
        if (appInfo.validateSignature(context, resolveInfo.serviceInfo.packageName)) {
            return intent;
        }
        return null;
    }

    public static Intent createProxyAuthIntent(Context context, String applicationId, Collection<String> permissions, String e2e, boolean isRerequest, boolean isForPublish, DefaultAudience defaultAudience) {
        for (NativeAppInfo appInfo : facebookAppInfoList) {
            Intent intent = new Intent().setClassName(appInfo.getPackage(), "com.facebook.katana.ProxyAuth").putExtra("client_id", applicationId);
            if (!Utility.isNullOrEmpty((Collection) permissions)) {
                intent.putExtra("scope", TextUtils.join(",", permissions));
            }
            if (!Utility.isNullOrEmpty(e2e)) {
                intent.putExtra("e2e", e2e);
            }
            intent.putExtra("response_type", "token,signed_request");
            intent.putExtra("return_scopes", "true");
            if (isForPublish) {
                intent.putExtra("default_audience", defaultAudience.getNativeProtocolAudience());
            }
            intent.putExtra("legacy_override", "v2.4");
            if (isRerequest) {
                intent.putExtra("auth_type", "rerequest");
            }
            intent = validateActivityIntent(context, intent, appInfo);
            if (intent != null) {
                return intent;
            }
        }
        return null;
    }

    public static final int getLatestKnownVersion() {
        return ((Integer) KNOWN_PROTOCOL_VERSIONS.get(0)).intValue();
    }

    private static Intent findActivityIntent(Context context, String activityAction, String internalAction) {
        List<NativeAppInfo> list = (List) actionToAppInfoMap.get(internalAction);
        if (list == null) {
            return null;
        }
        Intent intent = null;
        for (NativeAppInfo appInfo : list) {
            intent = validateActivityIntent(context, new Intent().setAction(activityAction).setPackage(appInfo.getPackage()).addCategory("android.intent.category.DEFAULT"), appInfo);
            if (intent != null) {
                return intent;
            }
        }
        return intent;
    }

    public static boolean isVersionCompatibleWithBucketedIntent(int version) {
        return KNOWN_PROTOCOL_VERSIONS.contains(Integer.valueOf(version)) && version >= 20140701;
    }

    public static Intent createPlatformActivityIntent(Context context, String callId, String action, int version, Bundle extras) {
        Intent intent = findActivityIntent(context, "com.facebook.platform.PLATFORM_ACTIVITY", action);
        if (intent == null) {
            return null;
        }
        setupProtocolRequestIntent(intent, callId, action, version, extras);
        return intent;
    }

    public static void setupProtocolRequestIntent(Intent intent, String callId, String action, int version, Bundle params) {
        String applicationId = FacebookSdk.getApplicationId();
        String applicationName = FacebookSdk.getApplicationName();
        intent.putExtra("com.facebook.platform.protocol.PROTOCOL_VERSION", version).putExtra("com.facebook.platform.protocol.PROTOCOL_ACTION", action).putExtra("com.facebook.platform.extra.APPLICATION_ID", applicationId);
        if (isVersionCompatibleWithBucketedIntent(version)) {
            Bundle methodArguments;
            Bundle bridgeArguments = new Bundle();
            bridgeArguments.putString("action_id", callId);
            Utility.putNonEmptyString(bridgeArguments, "app_name", applicationName);
            intent.putExtra("com.facebook.platform.protocol.BRIDGE_ARGS", bridgeArguments);
            if (params == null) {
                methodArguments = new Bundle();
            } else {
                methodArguments = params;
            }
            intent.putExtra("com.facebook.platform.protocol.METHOD_ARGS", methodArguments);
            return;
        }
        intent.putExtra("com.facebook.platform.protocol.CALL_ID", callId);
        if (!Utility.isNullOrEmpty(applicationName)) {
            intent.putExtra("com.facebook.platform.extra.APPLICATION_NAME", applicationName);
        }
        intent.putExtras(params);
    }

    public static Intent createProtocolResultIntent(Intent requestIntent, Bundle results, FacebookException error) {
        UUID callId = getCallIdFromIntent(requestIntent);
        if (callId == null) {
            return null;
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("com.facebook.platform.protocol.PROTOCOL_VERSION", getProtocolVersionFromIntent(requestIntent));
        Bundle bridgeArguments = new Bundle();
        bridgeArguments.putString("action_id", callId.toString());
        if (error != null) {
            bridgeArguments.putBundle("error", createBundleForException(error));
        }
        resultIntent.putExtra("com.facebook.platform.protocol.BRIDGE_ARGS", bridgeArguments);
        if (results == null) {
            return resultIntent;
        }
        resultIntent.putExtra("com.facebook.platform.protocol.RESULT_ARGS", results);
        return resultIntent;
    }

    public static Intent createPlatformServiceIntent(Context context) {
        for (NativeAppInfo appInfo : facebookAppInfoList) {
            Intent intent = validateServiceIntent(context, new Intent("com.facebook.platform.PLATFORM_SERVICE").setPackage(appInfo.getPackage()).addCategory("android.intent.category.DEFAULT"), appInfo);
            if (intent != null) {
                return intent;
            }
        }
        return null;
    }

    public static int getProtocolVersionFromIntent(Intent intent) {
        return intent.getIntExtra("com.facebook.platform.protocol.PROTOCOL_VERSION", 0);
    }

    public static UUID getCallIdFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        String callIdString = null;
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent(intent))) {
            Bundle bridgeArgs = intent.getBundleExtra("com.facebook.platform.protocol.BRIDGE_ARGS");
            if (bridgeArgs != null) {
                callIdString = bridgeArgs.getString("action_id");
            }
        } else {
            callIdString = intent.getStringExtra("com.facebook.platform.protocol.CALL_ID");
        }
        UUID callId = null;
        if (callIdString == null) {
            return callId;
        }
        try {
            return UUID.fromString(callIdString);
        } catch (IllegalArgumentException e) {
            return callId;
        }
    }

    public static Bundle getMethodArgumentsFromIntent(Intent intent) {
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent(intent))) {
            return intent.getBundleExtra("com.facebook.platform.protocol.METHOD_ARGS");
        }
        return intent.getExtras();
    }

    public static FacebookException getExceptionFromErrorData(Bundle errorData) {
        if (errorData == null) {
            return null;
        }
        String type = errorData.getString("error_type");
        if (type == null) {
            type = errorData.getString("com.facebook.platform.status.ERROR_TYPE");
        }
        String description = errorData.getString("error_description");
        if (description == null) {
            description = errorData.getString("com.facebook.platform.status.ERROR_DESCRIPTION");
        }
        if (type == null || !type.equalsIgnoreCase("UserCanceled")) {
            return new FacebookException(description);
        }
        return new FacebookOperationCanceledException(description);
    }

    public static Bundle createBundleForException(FacebookException e) {
        if (e == null) {
            return null;
        }
        Bundle errorBundle = new Bundle();
        errorBundle.putString("error_description", e.toString());
        if (!(e instanceof FacebookOperationCanceledException)) {
            return errorBundle;
        }
        errorBundle.putString("error_type", "UserCanceled");
        return errorBundle;
    }

    public static int getLatestAvailableProtocolVersionForService(int minimumVersion) {
        return getLatestAvailableProtocolVersionForAppInfoList(facebookAppInfoList, new int[]{minimumVersion});
    }

    public static int getLatestAvailableProtocolVersionForAction(String action, int[] versionSpec) {
        return getLatestAvailableProtocolVersionForAppInfoList((List) actionToAppInfoMap.get(action), versionSpec);
    }

    private static int getLatestAvailableProtocolVersionForAppInfoList(List<NativeAppInfo> appInfoList, int[] versionSpec) {
        updateAllAvailableProtocolVersionsAsync();
        if (appInfoList == null) {
            return -1;
        }
        for (NativeAppInfo appInfo : appInfoList) {
            int protocolVersion = computeLatestAvailableVersionFromVersionSpec(appInfo.getAvailableVersions(), getLatestKnownVersion(), versionSpec);
            if (protocolVersion != -1) {
                return protocolVersion;
            }
        }
        return -1;
    }

    public static void updateAllAvailableProtocolVersionsAsync() {
        if (protocolVersionsAsyncUpdating.compareAndSet(false, true)) {
            FacebookSdk.getExecutor().execute(new C03541());
        }
    }

    private static TreeSet<Integer> fetchAllAvailableProtocolVersionsForAppInfo(NativeAppInfo appInfo) {
        TreeSet<Integer> allAvailableVersions = new TreeSet();
        ContentResolver contentResolver = FacebookSdk.getApplicationContext().getContentResolver();
        String[] projection = new String[]{"version"};
        Uri uri = buildPlatformProviderVersionURI(appInfo);
        Cursor c = null;
        try {
            if (FacebookSdk.getApplicationContext().getPackageManager().resolveContentProvider(appInfo.getPackage() + ".provider.PlatformProvider", 0) != null) {
                c = contentResolver.query(uri, projection, null, null, null);
                if (c != null) {
                    while (c.moveToNext()) {
                        allAvailableVersions.add(Integer.valueOf(c.getInt(c.getColumnIndex("version"))));
                    }
                }
            }
            if (c != null) {
                c.close();
            }
            return allAvailableVersions;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static int computeLatestAvailableVersionFromVersionSpec(TreeSet<Integer> allAvailableFacebookAppVersions, int latestSdkVersion, int[] versionSpec) {
        int versionSpecIndex = versionSpec.length - 1;
        Iterator<Integer> fbAppVersionsIterator = allAvailableFacebookAppVersions.descendingIterator();
        int latestFacebookAppVersion = -1;
        while (fbAppVersionsIterator.hasNext()) {
            int fbAppVersion = ((Integer) fbAppVersionsIterator.next()).intValue();
            latestFacebookAppVersion = Math.max(latestFacebookAppVersion, fbAppVersion);
            while (versionSpecIndex >= 0 && versionSpec[versionSpecIndex] > fbAppVersion) {
                versionSpecIndex--;
            }
            if (versionSpecIndex < 0) {
                return -1;
            }
            if (versionSpec[versionSpecIndex] == fbAppVersion) {
                return versionSpecIndex % 2 == 0 ? Math.min(latestFacebookAppVersion, latestSdkVersion) : -1;
            }
        }
        return -1;
    }

    private static Uri buildPlatformProviderVersionURI(NativeAppInfo appInfo) {
        return Uri.parse("content://" + appInfo.getPackage() + ".provider.PlatformProvider/versions");
    }
}
