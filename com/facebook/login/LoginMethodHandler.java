package com.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient.Request;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

abstract class LoginMethodHandler implements Parcelable {
    protected LoginClient loginClient;
    Map<String, String> methodLoggingExtras;

    abstract String getNameForLogging();

    abstract boolean tryAuthorize(Request request);

    LoginMethodHandler(LoginClient loginClient) {
        this.loginClient = loginClient;
    }

    LoginMethodHandler(Parcel source) {
        this.methodLoggingExtras = Utility.readStringMapFromParcel(source);
    }

    void setLoginClient(LoginClient loginClient) {
        if (this.loginClient != null) {
            throw new FacebookException("Can't set LoginClient if it is already set.");
        }
        this.loginClient = loginClient;
    }

    boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    boolean needsInternetPermission() {
        return false;
    }

    void cancel() {
    }

    protected void addLoggingExtra(String key, Object value) {
        if (this.methodLoggingExtras == null) {
            this.methodLoggingExtras = new HashMap();
        }
        this.methodLoggingExtras.put(key, value == null ? null : value.toString());
    }

    protected void logWebLoginCompleted(String e2e) {
        String applicationId = this.loginClient.getPendingRequest().getApplicationId();
        AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(this.loginClient.getActivity(), applicationId);
        Bundle parameters = new Bundle();
        parameters.putString("fb_web_login_e2e", e2e);
        parameters.putLong("fb_web_login_switchback_time", System.currentTimeMillis());
        parameters.putString("app_id", applicationId);
        appEventsLogger.logSdkEvent("fb_dialogs_web_login_dialog_complete", null, parameters);
    }

    static AccessToken createAccessTokenFromNativeLogin(Bundle bundle, AccessTokenSource source, String applicationId) {
        Date expires = Utility.getBundleLongAsDate(bundle, "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH", new Date(0));
        ArrayList<String> permissions = bundle.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
        String token = bundle.getString("com.facebook.platform.extra.ACCESS_TOKEN");
        if (Utility.isNullOrEmpty(token)) {
            return null;
        }
        return new AccessToken(token, applicationId, bundle.getString("com.facebook.platform.extra.USER_ID"), permissions, null, source, expires, new Date());
    }

    public static AccessToken createAccessTokenFromWebBundle(Collection<String> requestedPermissions, Bundle bundle, AccessTokenSource source, String applicationId) throws FacebookException {
        Date expires = Utility.getBundleLongAsDate(bundle, "expires_in", new Date());
        String token = bundle.getString("access_token");
        String grantedPermissions = bundle.getString("granted_scopes");
        if (!Utility.isNullOrEmpty(grantedPermissions)) {
            requestedPermissions = new ArrayList(Arrays.asList(grantedPermissions.split(",")));
        }
        String deniedPermissions = bundle.getString("denied_scopes");
        List<String> declinedPermissions = null;
        if (!Utility.isNullOrEmpty(deniedPermissions)) {
            declinedPermissions = new ArrayList(Arrays.asList(deniedPermissions.split(",")));
        }
        if (Utility.isNullOrEmpty(token)) {
            return null;
        }
        return new AccessToken(token, applicationId, getUserIDFromSignedRequest(bundle.getString("signed_request")), requestedPermissions, declinedPermissions, source, expires, new Date());
    }

    private static String getUserIDFromSignedRequest(String signedRequest) throws FacebookException {
        if (signedRequest == null || signedRequest.isEmpty()) {
            throw new FacebookException("Authorization response does not contain the signed_request");
        }
        try {
            String[] signatureAndPayload = signedRequest.split("\\.");
            if (signatureAndPayload.length == 2) {
                return new JSONObject(new String(Base64.decode(signatureAndPayload[1], 0), "UTF-8")).getString("user_id");
            }
        } catch (UnsupportedEncodingException e) {
        } catch (JSONException e2) {
        }
        throw new FacebookException("Failed to retrieve user_id from signed_request");
    }

    public void writeToParcel(Parcel dest, int flags) {
        Utility.writeStringMapToParcel(dest, this.methodLoggingExtras);
    }
}
