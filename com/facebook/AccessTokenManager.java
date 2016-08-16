package com.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.GraphRequest.Callback;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

final class AccessTokenManager {
    private static volatile AccessTokenManager instance;
    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;
    private Date lastAttemptedTokenExtendDate;
    private final LocalBroadcastManager localBroadcastManager;
    private AtomicBoolean tokenRefreshInProgress;

    /* renamed from: com.facebook.AccessTokenManager.1 */
    class C03041 implements Runnable {
        C03041() {
        }

        public void run() {
            AccessTokenManager.this.refreshCurrentAccessTokenImpl();
        }
    }

    /* renamed from: com.facebook.AccessTokenManager.2 */
    class C03052 implements Callback {
        final /* synthetic */ Set val$declinedPermissions;
        final /* synthetic */ Set val$permissions;
        final /* synthetic */ AtomicBoolean val$permissionsCallSucceeded;

        C03052(AtomicBoolean atomicBoolean, Set set, Set set2) {
            this.val$permissionsCallSucceeded = atomicBoolean;
            this.val$permissions = set;
            this.val$declinedPermissions = set2;
        }

        public void onCompleted(GraphResponse response) {
            JSONObject result = response.getJSONObject();
            if (result != null) {
                JSONArray permissionsArray = result.optJSONArray(PoiFragment.ARG_DATA);
                if (permissionsArray != null) {
                    this.val$permissionsCallSucceeded.set(true);
                    for (int i = 0; i < permissionsArray.length(); i++) {
                        JSONObject permissionEntry = permissionsArray.optJSONObject(i);
                        if (permissionEntry != null) {
                            String permission = permissionEntry.optString("permission");
                            String status = permissionEntry.optString("status");
                            if (!(Utility.isNullOrEmpty(permission) || Utility.isNullOrEmpty(status))) {
                                status = status.toLowerCase(Locale.US);
                                if (status.equals("granted")) {
                                    this.val$permissions.add(permission);
                                } else if (status.equals("declined")) {
                                    this.val$declinedPermissions.add(permission);
                                } else {
                                    Log.w("AccessTokenManager", "Unexpected status: " + status);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: com.facebook.AccessTokenManager.3 */
    class C03063 implements Callback {
        final /* synthetic */ RefreshResult val$refreshResult;

        C03063(RefreshResult refreshResult) {
            this.val$refreshResult = refreshResult;
        }

        public void onCompleted(GraphResponse response) {
            JSONObject data = response.getJSONObject();
            if (data != null) {
                this.val$refreshResult.accessToken = data.optString("access_token");
                this.val$refreshResult.expiresAt = data.optInt("expires_at");
            }
        }
    }

    /* renamed from: com.facebook.AccessTokenManager.4 */
    class C03074 implements GraphRequestBatch.Callback {
        final /* synthetic */ AccessToken val$accessToken;
        final /* synthetic */ Set val$declinedPermissions;
        final /* synthetic */ Set val$permissions;
        final /* synthetic */ AtomicBoolean val$permissionsCallSucceeded;
        final /* synthetic */ RefreshResult val$refreshResult;

        C03074(AccessToken accessToken, AtomicBoolean atomicBoolean, RefreshResult refreshResult, Set set, Set set2) {
            this.val$accessToken = accessToken;
            this.val$permissionsCallSucceeded = atomicBoolean;
            this.val$refreshResult = refreshResult;
            this.val$permissions = set;
            this.val$declinedPermissions = set2;
        }

        public void onBatchCompleted(GraphRequestBatch batch) {
            if (AccessTokenManager.getInstance().getCurrentAccessToken() != null && AccessTokenManager.getInstance().getCurrentAccessToken().getUserId() == this.val$accessToken.getUserId()) {
                try {
                    if (this.val$permissionsCallSucceeded.get() || this.val$refreshResult.accessToken != null || this.val$refreshResult.expiresAt != 0) {
                        String str;
                        Collection collection;
                        Collection collection2;
                        Date date;
                        if (this.val$refreshResult.accessToken != null) {
                            str = this.val$refreshResult.accessToken;
                        } else {
                            str = this.val$accessToken.getToken();
                        }
                        String applicationId = this.val$accessToken.getApplicationId();
                        String userId = this.val$accessToken.getUserId();
                        if (this.val$permissionsCallSucceeded.get()) {
                            collection = this.val$permissions;
                        } else {
                            collection = this.val$accessToken.getPermissions();
                        }
                        if (this.val$permissionsCallSucceeded.get()) {
                            collection2 = this.val$declinedPermissions;
                        } else {
                            collection2 = this.val$accessToken.getDeclinedPermissions();
                        }
                        AccessTokenSource source = this.val$accessToken.getSource();
                        if (this.val$refreshResult.expiresAt != 0) {
                            date = new Date(((long) this.val$refreshResult.expiresAt) * 1000);
                        } else {
                            date = this.val$accessToken.getExpires();
                        }
                        AccessTokenManager.getInstance().setCurrentAccessToken(new AccessToken(str, applicationId, userId, collection, collection2, source, date, new Date()));
                        AccessTokenManager.this.tokenRefreshInProgress.set(false);
                    }
                } finally {
                    AccessTokenManager.this.tokenRefreshInProgress.set(false);
                }
            }
        }
    }

    private static class RefreshResult {
        public String accessToken;
        public int expiresAt;

        private RefreshResult() {
        }
    }

    AccessTokenManager(LocalBroadcastManager localBroadcastManager, AccessTokenCache accessTokenCache) {
        this.tokenRefreshInProgress = new AtomicBoolean(false);
        this.lastAttemptedTokenExtendDate = new Date(0);
        Validate.notNull(localBroadcastManager, "localBroadcastManager");
        Validate.notNull(accessTokenCache, "accessTokenCache");
        this.localBroadcastManager = localBroadcastManager;
        this.accessTokenCache = accessTokenCache;
    }

    static AccessTokenManager getInstance() {
        if (instance == null) {
            synchronized (AccessTokenManager.class) {
                if (instance == null) {
                    instance = new AccessTokenManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new AccessTokenCache());
                }
            }
        }
        return instance;
    }

    AccessToken getCurrentAccessToken() {
        return this.currentAccessToken;
    }

    boolean loadCurrentAccessToken() {
        AccessToken accessToken = this.accessTokenCache.load();
        if (accessToken == null) {
            return false;
        }
        setCurrentAccessToken(accessToken, false);
        return true;
    }

    void setCurrentAccessToken(AccessToken currentAccessToken) {
        setCurrentAccessToken(currentAccessToken, true);
    }

    private void setCurrentAccessToken(AccessToken currentAccessToken, boolean saveToCache) {
        AccessToken oldAccessToken = this.currentAccessToken;
        this.currentAccessToken = currentAccessToken;
        this.tokenRefreshInProgress.set(false);
        this.lastAttemptedTokenExtendDate = new Date(0);
        if (saveToCache) {
            if (currentAccessToken != null) {
                this.accessTokenCache.save(currentAccessToken);
            } else {
                this.accessTokenCache.clear();
                Utility.clearFacebookCookies(FacebookSdk.getApplicationContext());
            }
        }
        if (!Utility.areObjectsEqual(oldAccessToken, currentAccessToken)) {
            sendCurrentAccessTokenChangedBroadcast(oldAccessToken, currentAccessToken);
        }
    }

    private void sendCurrentAccessTokenChangedBroadcast(AccessToken oldAccessToken, AccessToken currentAccessToken) {
        Intent intent = new Intent("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED");
        intent.putExtra("com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN", oldAccessToken);
        intent.putExtra("com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN", currentAccessToken);
        this.localBroadcastManager.sendBroadcast(intent);
    }

    void extendAccessTokenIfNeeded() {
        if (shouldExtendAccessToken()) {
            refreshCurrentAccessToken();
        }
    }

    private boolean shouldExtendAccessToken() {
        if (this.currentAccessToken == null) {
            return false;
        }
        Long now = Long.valueOf(new Date().getTime());
        if (!this.currentAccessToken.getSource().canExtendToken() || now.longValue() - this.lastAttemptedTokenExtendDate.getTime() <= 3600000 || now.longValue() - this.currentAccessToken.getLastRefresh().getTime() <= 86400000) {
            return false;
        }
        return true;
    }

    private static GraphRequest createGrantedPermissionsRequest(AccessToken accessToken, Callback callback) {
        return new GraphRequest(accessToken, "me/permissions", new Bundle(), HttpMethod.GET, callback);
    }

    private static GraphRequest createExtendAccessTokenRequest(AccessToken accessToken, Callback callback) {
        Bundle parameters = new Bundle();
        parameters.putString("grant_type", "fb_extend_sso_token");
        return new GraphRequest(accessToken, "oauth/access_token", parameters, HttpMethod.GET, callback);
    }

    void refreshCurrentAccessToken() {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            refreshCurrentAccessTokenImpl();
        } else {
            new Handler(Looper.getMainLooper()).post(new C03041());
        }
    }

    private void refreshCurrentAccessTokenImpl() {
        AccessToken accessToken = this.currentAccessToken;
        if (accessToken != null && this.tokenRefreshInProgress.compareAndSet(false, true)) {
            Validate.runningOnUiThread();
            this.lastAttemptedTokenExtendDate = new Date();
            Set<String> permissions = new HashSet();
            Set<String> declinedPermissions = new HashSet();
            GraphRequestBatch batch = new GraphRequestBatch(createGrantedPermissionsRequest(accessToken, new C03052(new AtomicBoolean(false), permissions, declinedPermissions)), createExtendAccessTokenRequest(accessToken, new C03063(new RefreshResult())));
            batch.addCallback(new C03074(accessToken, permissionsCallSucceeded, refreshResult, permissions, declinedPermissions));
            batch.executeAsync();
        }
    }
}
