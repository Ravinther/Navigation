package com.facebook;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AccessToken implements Parcelable {
    public static final Creator<AccessToken> CREATOR;
    private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE;
    private static final Date DEFAULT_EXPIRATION_TIME;
    private static final Date DEFAULT_LAST_REFRESH_TIME;
    private static final Date MAX_DATE;
    private final String applicationId;
    private final Set<String> declinedPermissions;
    private final Date expires;
    private final Date lastRefresh;
    private final Set<String> permissions;
    private final AccessTokenSource source;
    private final String token;
    private final String userId;

    /* renamed from: com.facebook.AccessToken.2 */
    static class C03032 implements Creator {
        C03032() {
        }

        public AccessToken createFromParcel(Parcel source) {
            return new AccessToken(source);
        }

        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    }

    static {
        MAX_DATE = new Date(Long.MAX_VALUE);
        DEFAULT_EXPIRATION_TIME = MAX_DATE;
        DEFAULT_LAST_REFRESH_TIME = new Date();
        DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
        CREATOR = new C03032();
    }

    public AccessToken(String accessToken, String applicationId, String userId, Collection<String> permissions, Collection<String> declinedPermissions, AccessTokenSource accessTokenSource, Date expirationTime, Date lastRefreshTime) {
        Validate.notNullOrEmpty(accessToken, "accessToken");
        Validate.notNullOrEmpty(applicationId, "applicationId");
        Validate.notNullOrEmpty(userId, "userId");
        if (expirationTime == null) {
            expirationTime = DEFAULT_EXPIRATION_TIME;
        }
        this.expires = expirationTime;
        this.permissions = Collections.unmodifiableSet(permissions != null ? new HashSet(permissions) : new HashSet());
        this.declinedPermissions = Collections.unmodifiableSet(declinedPermissions != null ? new HashSet(declinedPermissions) : new HashSet());
        this.token = accessToken;
        if (accessTokenSource == null) {
            accessTokenSource = DEFAULT_ACCESS_TOKEN_SOURCE;
        }
        this.source = accessTokenSource;
        if (lastRefreshTime == null) {
            lastRefreshTime = DEFAULT_LAST_REFRESH_TIME;
        }
        this.lastRefresh = lastRefreshTime;
        this.applicationId = applicationId;
        this.userId = userId;
    }

    public static AccessToken getCurrentAccessToken() {
        return AccessTokenManager.getInstance().getCurrentAccessToken();
    }

    public static void setCurrentAccessToken(AccessToken accessToken) {
        AccessTokenManager.getInstance().setCurrentAccessToken(accessToken);
    }

    public String getToken() {
        return this.token;
    }

    public Date getExpires() {
        return this.expires;
    }

    public Set<String> getPermissions() {
        return this.permissions;
    }

    public Set<String> getDeclinedPermissions() {
        return this.declinedPermissions;
    }

    public AccessTokenSource getSource() {
        return this.source;
    }

    public Date getLastRefresh() {
        return this.lastRefresh;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{AccessToken");
        builder.append(" token:").append(tokenToString());
        appendPermissions(builder);
        builder.append("}");
        return builder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r6) {
        /*
        r5 = this;
        r1 = 1;
        r2 = 0;
        if (r5 != r6) goto L_0x0005;
    L_0x0004:
        return r1;
    L_0x0005:
        r3 = r6 instanceof com.facebook.AccessToken;
        if (r3 != 0) goto L_0x000b;
    L_0x0009:
        r1 = r2;
        goto L_0x0004;
    L_0x000b:
        r0 = r6;
        r0 = (com.facebook.AccessToken) r0;
        r3 = r5.expires;
        r4 = r0.expires;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0058;
    L_0x0018:
        r3 = r5.permissions;
        r4 = r0.permissions;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0058;
    L_0x0022:
        r3 = r5.declinedPermissions;
        r4 = r0.declinedPermissions;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0058;
    L_0x002c:
        r3 = r5.token;
        r4 = r0.token;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0058;
    L_0x0036:
        r3 = r5.source;
        r4 = r0.source;
        if (r3 != r4) goto L_0x0058;
    L_0x003c:
        r3 = r5.lastRefresh;
        r4 = r0.lastRefresh;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0058;
    L_0x0046:
        r3 = r5.applicationId;
        if (r3 != 0) goto L_0x005a;
    L_0x004a:
        r3 = r0.applicationId;
        if (r3 != 0) goto L_0x0058;
    L_0x004e:
        r3 = r5.userId;
        r4 = r0.userId;
        r3 = r3.equals(r4);
        if (r3 != 0) goto L_0x0004;
    L_0x0058:
        r1 = r2;
        goto L_0x0004;
    L_0x005a:
        r3 = r5.applicationId;
        r4 = r0.applicationId;
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0058;
    L_0x0064:
        goto L_0x004e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.AccessToken.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return ((((((((((((((this.expires.hashCode() + 527) * 31) + this.permissions.hashCode()) * 31) + this.declinedPermissions.hashCode()) * 31) + this.token.hashCode()) * 31) + this.source.hashCode()) * 31) + this.lastRefresh.hashCode()) * 31) + (this.applicationId == null ? 0 : this.applicationId.hashCode())) * 31) + this.userId.hashCode();
    }

    static AccessToken createFromLegacyCache(Bundle bundle) {
        List<String> permissions = getPermissionsFromBundle(bundle, "com.facebook.TokenCachingStrategy.Permissions");
        List<String> declinedPermissions = getPermissionsFromBundle(bundle, "com.facebook.TokenCachingStrategy.DeclinedPermissions");
        String applicationId = LegacyTokenHelper.getApplicationId(bundle);
        if (Utility.isNullOrEmpty(applicationId)) {
            applicationId = FacebookSdk.getApplicationId();
        }
        String tokenString = LegacyTokenHelper.getToken(bundle);
        try {
            return new AccessToken(tokenString, applicationId, Utility.awaitGetGraphMeRequestWithCache(tokenString).getString("id"), permissions, declinedPermissions, LegacyTokenHelper.getSource(bundle), LegacyTokenHelper.getDate(bundle, "com.facebook.TokenCachingStrategy.ExpirationDate"), LegacyTokenHelper.getDate(bundle, "com.facebook.TokenCachingStrategy.LastRefreshDate"));
        } catch (JSONException e) {
            return null;
        }
    }

    static List<String> getPermissionsFromBundle(Bundle bundle, String key) {
        List<String> originalPermissions = bundle.getStringArrayList(key);
        if (originalPermissions == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(originalPermissions));
    }

    JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", 1);
        jsonObject.put("token", this.token);
        jsonObject.put("expires_at", this.expires.getTime());
        jsonObject.put("permissions", new JSONArray(this.permissions));
        jsonObject.put("declined_permissions", new JSONArray(this.declinedPermissions));
        jsonObject.put("last_refresh", this.lastRefresh.getTime());
        jsonObject.put("source", this.source.name());
        jsonObject.put("application_id", this.applicationId);
        jsonObject.put("user_id", this.userId);
        return jsonObject;
    }

    static AccessToken createFromJSONObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject.getInt("version") > 1) {
            throw new FacebookException("Unknown AccessToken serialization format.");
        }
        String token = jsonObject.getString("token");
        Date expiresAt = new Date(jsonObject.getLong("expires_at"));
        JSONArray permissionsArray = jsonObject.getJSONArray("permissions");
        JSONArray declinedPermissionsArray = jsonObject.getJSONArray("declined_permissions");
        Date lastRefresh = new Date(jsonObject.getLong("last_refresh"));
        return new AccessToken(token, jsonObject.getString("application_id"), jsonObject.getString("user_id"), Utility.jsonArrayToStringList(permissionsArray), Utility.jsonArrayToStringList(declinedPermissionsArray), AccessTokenSource.valueOf(jsonObject.getString("source")), expiresAt, lastRefresh);
    }

    private String tokenToString() {
        if (this.token == null) {
            return "null";
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
            return this.token;
        }
        return "ACCESS_TOKEN_REMOVED";
    }

    private void appendPermissions(StringBuilder builder) {
        builder.append(" permissions:");
        if (this.permissions == null) {
            builder.append("null");
            return;
        }
        builder.append("[");
        builder.append(TextUtils.join(", ", this.permissions));
        builder.append("]");
    }

    AccessToken(Parcel parcel) {
        this.expires = new Date(parcel.readLong());
        ArrayList<String> permissionsList = new ArrayList();
        parcel.readStringList(permissionsList);
        this.permissions = Collections.unmodifiableSet(new HashSet(permissionsList));
        permissionsList.clear();
        parcel.readStringList(permissionsList);
        this.declinedPermissions = Collections.unmodifiableSet(new HashSet(permissionsList));
        this.token = parcel.readString();
        this.source = AccessTokenSource.valueOf(parcel.readString());
        this.lastRefresh = new Date(parcel.readLong());
        this.applicationId = parcel.readString();
        this.userId = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.expires.getTime());
        dest.writeStringList(new ArrayList(this.permissions));
        dest.writeStringList(new ArrayList(this.declinedPermissions));
        dest.writeString(this.token);
        dest.writeString(this.source.name());
        dest.writeLong(this.lastRefresh.getTime());
        dest.writeString(this.applicationId);
        dest.writeString(this.userId);
    }
}
