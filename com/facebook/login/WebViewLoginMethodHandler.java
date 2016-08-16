package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.internal.WebDialog.Builder;
import com.facebook.internal.WebDialog.OnCompleteListener;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;
import java.util.Locale;

class WebViewLoginMethodHandler extends LoginMethodHandler {
    public static final Creator<WebViewLoginMethodHandler> CREATOR;
    private String e2e;
    private WebDialog loginDialog;

    /* renamed from: com.facebook.login.WebViewLoginMethodHandler.1 */
    class C03751 implements OnCompleteListener {
        final /* synthetic */ Request val$request;

        C03751(Request request) {
            this.val$request = request;
        }

        public void onComplete(Bundle values, FacebookException error) {
            WebViewLoginMethodHandler.this.onWebDialogComplete(this.val$request, values, error);
        }
    }

    /* renamed from: com.facebook.login.WebViewLoginMethodHandler.2 */
    static class C03762 implements Creator {
        C03762() {
        }

        public WebViewLoginMethodHandler createFromParcel(Parcel source) {
            return new WebViewLoginMethodHandler(source);
        }

        public WebViewLoginMethodHandler[] newArray(int size) {
            return new WebViewLoginMethodHandler[size];
        }
    }

    static class AuthDialogBuilder extends Builder {
        private String e2e;
        private boolean isRerequest;

        public AuthDialogBuilder(Context context, String applicationId, Bundle parameters) {
            super(context, applicationId, "oauth", parameters);
        }

        public AuthDialogBuilder setE2E(String e2e) {
            this.e2e = e2e;
            return this;
        }

        public AuthDialogBuilder setIsRerequest(boolean isRerequest) {
            this.isRerequest = isRerequest;
            return this;
        }

        public WebDialog build() {
            Bundle parameters = getParameters();
            parameters.putString("redirect_uri", "fbconnect://success");
            parameters.putString("client_id", getApplicationId());
            parameters.putString("e2e", this.e2e);
            parameters.putString("response_type", "token,signed_request");
            parameters.putString("return_scopes", "true");
            if (this.isRerequest) {
                parameters.putString("auth_type", "rerequest");
            }
            return new WebDialog(getContext(), "oauth", parameters, getTheme(), getListener());
        }
    }

    WebViewLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    String getNameForLogging() {
        return "web_view";
    }

    boolean needsInternetPermission() {
        return true;
    }

    void cancel() {
        if (this.loginDialog != null) {
            this.loginDialog.cancel();
            this.loginDialog = null;
        }
    }

    boolean tryAuthorize(Request request) {
        Bundle parameters = new Bundle();
        if (!Utility.isNullOrEmpty(request.getPermissions())) {
            String scope = TextUtils.join(",", request.getPermissions());
            parameters.putString("scope", scope);
            addLoggingExtra("scope", scope);
        }
        parameters.putString("default_audience", request.getDefaultAudience().getNativeProtocolAudience());
        AccessToken previousToken = AccessToken.getCurrentAccessToken();
        String previousTokenString = previousToken != null ? previousToken.getToken() : null;
        if (previousTokenString == null || !previousTokenString.equals(loadCookieToken())) {
            Utility.clearFacebookCookies(this.loginClient.getActivity());
            addLoggingExtra("access_token", "0");
        } else {
            parameters.putString("access_token", previousTokenString);
            addLoggingExtra("access_token", "1");
        }
        OnCompleteListener listener = new C03751(request);
        this.e2e = LoginClient.getE2E();
        addLoggingExtra("e2e", this.e2e);
        FragmentActivity fragmentActivity = this.loginClient.getActivity();
        this.loginDialog = new AuthDialogBuilder(fragmentActivity, request.getApplicationId(), parameters).setE2E(this.e2e).setIsRerequest(request.isRerequest()).setOnCompleteListener(listener).setTheme(FacebookSdk.getWebDialogTheme()).build();
        FacebookDialogFragment dialogFragment = new FacebookDialogFragment();
        dialogFragment.setRetainInstance(true);
        dialogFragment.setDialog(this.loginDialog);
        dialogFragment.show(fragmentActivity.getSupportFragmentManager(), "FacebookDialogFragment");
        return true;
    }

    void onWebDialogComplete(Request request, Bundle values, FacebookException error) {
        Result outcome;
        if (values != null) {
            if (values.containsKey("e2e")) {
                this.e2e = values.getString("e2e");
            }
            try {
                AccessToken token = LoginMethodHandler.createAccessTokenFromWebBundle(request.getPermissions(), values, AccessTokenSource.WEB_VIEW, request.getApplicationId());
                outcome = Result.createTokenResult(this.loginClient.getPendingRequest(), token);
                CookieSyncManager.createInstance(this.loginClient.getActivity()).sync();
                saveCookieToken(token.getToken());
            } catch (FacebookException ex) {
                outcome = Result.createErrorResult(this.loginClient.getPendingRequest(), null, ex.getMessage());
            }
        } else if (error instanceof FacebookOperationCanceledException) {
            outcome = Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
        } else {
            this.e2e = null;
            String errorCode = null;
            String errorMessage = error.getMessage();
            if (error instanceof FacebookServiceException) {
                errorCode = String.format(Locale.ROOT, "%d", new Object[]{Integer.valueOf(((FacebookServiceException) error).getRequestError().getErrorCode())});
                errorMessage = requestError.toString();
            }
            outcome = Result.createErrorResult(this.loginClient.getPendingRequest(), null, errorMessage, errorCode);
        }
        if (!Utility.isNullOrEmpty(this.e2e)) {
            logWebLoginCompleted(this.e2e);
        }
        this.loginClient.completeAndValidate(outcome);
    }

    private void saveCookieToken(String token) {
        this.loginClient.getActivity().getSharedPreferences("com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).edit().putString("TOKEN", token).apply();
    }

    private String loadCookieToken() {
        return this.loginClient.getActivity().getSharedPreferences("com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).getString("TOKEN", "");
    }

    WebViewLoginMethodHandler(Parcel source) {
        super(source);
        this.e2e = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.e2e);
    }

    static {
        CREATOR = new C03762();
    }
}
