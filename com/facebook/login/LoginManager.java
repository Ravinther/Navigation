package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient.Request;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager {
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS;
    private static volatile LoginManager instance;
    private DefaultAudience defaultAudience;
    private LoginBehavior loginBehavior;
    private LoginLogger loginLogger;
    private HashMap<String, String> pendingLoggingExtras;
    private Request pendingLoginRequest;

    /* renamed from: com.facebook.login.LoginManager.2 */
    static class C03732 extends HashSet<String> {
        C03732() {
            add("ads_management");
            add("create_event");
            add("rsvp_event");
        }
    }

    /* renamed from: com.facebook.login.LoginManager.3 */
    class C03743 implements Callback {
        C03743() {
        }
    }

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity activity) {
            Validate.notNull(activity, "activity");
            this.activity = activity;
        }

        public void startActivityForResult(Intent intent, int requestCode) {
            this.activity.startActivityForResult(intent, requestCode);
        }

        public Activity getActivityContext() {
            return this.activity;
        }
    }

    private static class FragmentStartActivityDelegate implements StartActivityDelegate {
        private final Fragment fragment;

        FragmentStartActivityDelegate(Fragment fragment) {
            Validate.notNull(fragment, "fragment");
            this.fragment = fragment;
        }

        public void startActivityForResult(Intent intent, int requestCode) {
            this.fragment.startActivityForResult(intent, requestCode);
        }

        public Activity getActivityContext() {
            return this.fragment.getActivity();
        }
    }

    static {
        OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
    }

    LoginManager() {
        this.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
        this.defaultAudience = DefaultAudience.FRIENDS;
        Validate.sdkInitialized();
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public LoginManager setLoginBehavior(LoginBehavior loginBehavior) {
        this.loginBehavior = loginBehavior;
        return this;
    }

    public LoginManager setDefaultAudience(DefaultAudience defaultAudience) {
        this.defaultAudience = defaultAudience;
        return this;
    }

    public void logOut() {
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> permissions) {
        validateReadPermissions(permissions);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(permissions));
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> permissions) {
        validateReadPermissions(permissions);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(permissions));
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> permissions) {
        validatePublishPermissions(permissions);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(permissions));
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> permissions) {
        validatePublishPermissions(permissions);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(permissions));
    }

    private void validateReadPermissions(Collection<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (isPublishPermission(permission)) {
                    throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[]{(String) r1.next()}));
                }
            }
        }
    }

    private void validatePublishPermissions(Collection<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (!isPublishPermission(permission)) {
                    throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", new Object[]{(String) r1.next()}));
                }
            }
        }
    }

    static boolean isPublishPermission(String permission) {
        return permission != null && (permission.startsWith("publish") || permission.startsWith("manage") || OTHER_PUBLISH_PERMISSIONS.contains(permission));
    }

    private static Set<String> getOtherPublishPermissions() {
        return Collections.unmodifiableSet(new C03732());
    }

    private Request createLoginRequest(Collection<String> permissions) {
        Request request = new Request(this.loginBehavior, Collections.unmodifiableSet(permissions != null ? new HashSet(permissions) : new HashSet()), this.defaultAudience, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        request.setRerequest(AccessToken.getCurrentAccessToken() != null);
        return request;
    }

    private void startLogin(StartActivityDelegate startActivityDelegate, Request request) throws FacebookException {
        this.pendingLoginRequest = request;
        this.pendingLoggingExtras = new HashMap();
        this.loginLogger = getLoggerForContext(startActivityDelegate.getActivityContext());
        logStartLogin();
        CallbackManagerImpl.registerStaticCallback(RequestCodeOffset.Login.toRequestCode(), new C03743());
        boolean started = tryFacebookActivity(startActivityDelegate, request);
        this.pendingLoggingExtras.put("try_login_activity", started ? "1" : "0");
        if (!started) {
            FacebookException exception = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
            logCompleteLogin(Code.ERROR, null, exception);
            this.pendingLoginRequest = null;
            throw exception;
        }
    }

    private LoginLogger getLoggerForContext(Context context) {
        if (context == null || this.pendingLoginRequest == null) {
            return null;
        }
        LoginLogger logger = this.loginLogger;
        if (logger == null || !logger.getApplicationId().equals(this.pendingLoginRequest.getApplicationId())) {
            return new LoginLogger(context, this.pendingLoginRequest.getApplicationId());
        }
        return logger;
    }

    private void logStartLogin() {
        if (this.loginLogger != null && this.pendingLoginRequest != null) {
            this.loginLogger.logStartLogin(this.pendingLoginRequest);
        }
    }

    private void logCompleteLogin(Code result, Map<String, String> resultExtras, Exception exception) {
        if (this.loginLogger != null) {
            if (this.pendingLoginRequest == null) {
                this.loginLogger.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
            } else {
                this.loginLogger.logCompleteLogin(this.pendingLoginRequest.getAuthId(), this.pendingLoggingExtras, result, resultExtras, exception);
            }
        }
    }

    private boolean tryFacebookActivity(StartActivityDelegate startActivityDelegate, Request request) {
        Intent intent = getFacebookActivityIntent(request);
        if (!resolveIntent(intent)) {
            return false;
        }
        try {
            startActivityDelegate.startActivityForResult(intent, LoginClient.getLoginRequestCode());
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private boolean resolveIntent(Intent intent) {
        if (FacebookSdk.getApplicationContext().getPackageManager().resolveActivity(intent, 0) == null) {
            return false;
        }
        return true;
    }

    private Intent getFacebookActivityIntent(Request request) {
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(request.getLoginBehavior().toString());
        intent.putExtras(LoginFragment.populateIntentExtras(request));
        return intent;
    }
}
