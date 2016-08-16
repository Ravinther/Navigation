package com.facebook.login.widget;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.C0322R;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.LoginAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ToolTipPopup.Style;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class LoginButton extends FacebookButtonBase {
    private static final String TAG;
    private AccessTokenTracker accessTokenTracker;
    private boolean confirmLogout;
    private String loginLogoutEventName;
    private LoginManager loginManager;
    private String loginText;
    private String logoutText;
    private LoginButtonProperties properties;
    private boolean toolTipChecked;
    private long toolTipDisplayTime;
    private ToolTipMode toolTipMode;
    private ToolTipPopup toolTipPopup;
    private Style toolTipStyle;

    /* renamed from: com.facebook.login.widget.LoginButton.1 */
    class C03781 implements Runnable {
        final /* synthetic */ String val$appId;

        /* renamed from: com.facebook.login.widget.LoginButton.1.1 */
        class C03771 implements Runnable {
            final /* synthetic */ FetchedAppSettings val$settings;

            C03771(FetchedAppSettings fetchedAppSettings) {
                this.val$settings = fetchedAppSettings;
            }

            public void run() {
                LoginButton.this.showToolTipPerSettings(this.val$settings);
            }
        }

        C03781(String str) {
            this.val$appId = str;
        }

        public void run() {
            LoginButton.this.getActivity().runOnUiThread(new C03771(Utility.queryAppSettings(this.val$appId, false)));
        }
    }

    /* renamed from: com.facebook.login.widget.LoginButton.2 */
    class C03792 extends AccessTokenTracker {
        C03792() {
        }

        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            LoginButton.this.setButtonText();
        }
    }

    /* renamed from: com.facebook.login.widget.LoginButton.3 */
    static /* synthetic */ class C03803 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode;

        static {
            $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode = new int[ToolTipMode.values().length];
            try {
                $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[ToolTipMode.AUTOMATIC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[ToolTipMode.DISPLAY_ALWAYS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[ToolTipMode.NEVER_DISPLAY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class LoginButtonProperties {
        private LoginAuthorizationType authorizationType;
        private DefaultAudience defaultAudience;
        private LoginBehavior loginBehavior;
        private List<String> permissions;

        LoginButtonProperties() {
            this.defaultAudience = DefaultAudience.FRIENDS;
            this.permissions = Collections.emptyList();
            this.authorizationType = null;
            this.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
        }

        public void setDefaultAudience(DefaultAudience defaultAudience) {
            this.defaultAudience = defaultAudience;
        }

        public DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        public void setReadPermissions(List<String> permissions) {
            if (LoginAuthorizationType.PUBLISH.equals(this.authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setReadPermissions after setPublishPermissions has been called.");
            }
            this.permissions = permissions;
            this.authorizationType = LoginAuthorizationType.READ;
        }

        public void setPublishPermissions(List<String> permissions) {
            if (LoginAuthorizationType.READ.equals(this.authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setPublishPermissions after setReadPermissions has been called.");
            } else if (Utility.isNullOrEmpty((Collection) permissions)) {
                throw new IllegalArgumentException("Permissions for publish actions cannot be null or empty.");
            } else {
                this.permissions = permissions;
                this.authorizationType = LoginAuthorizationType.PUBLISH;
            }
        }

        List<String> getPermissions() {
            return this.permissions;
        }

        public void setLoginBehavior(LoginBehavior loginBehavior) {
            this.loginBehavior = loginBehavior;
        }

        public LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }
    }

    private class LoginClickListener implements OnClickListener {

        /* renamed from: com.facebook.login.widget.LoginButton.LoginClickListener.1 */
        class C03811 implements DialogInterface.OnClickListener {
            C03811() {
            }

            public void onClick(DialogInterface dialog, int which) {
                LoginButton.this.getLoginManager().logOut();
            }
        }

        private LoginClickListener() {
        }

        public void onClick(View v) {
            LoginButton.this.callExternalOnClickListener(v);
            Context context = LoginButton.this.getContext();
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken == null) {
                LoginManager loginManager = LoginButton.this.getLoginManager();
                loginManager.setDefaultAudience(LoginButton.this.getDefaultAudience());
                loginManager.setLoginBehavior(LoginButton.this.getLoginBehavior());
                if (LoginAuthorizationType.PUBLISH.equals(LoginButton.this.properties.authorizationType)) {
                    if (LoginButton.this.getFragment() != null) {
                        loginManager.logInWithPublishPermissions(LoginButton.this.getFragment(), LoginButton.this.properties.permissions);
                    } else {
                        loginManager.logInWithPublishPermissions(LoginButton.this.getActivity(), LoginButton.this.properties.permissions);
                    }
                } else if (LoginButton.this.getFragment() != null) {
                    loginManager.logInWithReadPermissions(LoginButton.this.getFragment(), LoginButton.this.properties.permissions);
                } else {
                    loginManager.logInWithReadPermissions(LoginButton.this.getActivity(), LoginButton.this.properties.permissions);
                }
            } else if (LoginButton.this.confirmLogout) {
                String message;
                String logout = LoginButton.this.getResources().getString(C0322R.string.com_facebook_loginview_log_out_action);
                String cancel = LoginButton.this.getResources().getString(C0322R.string.com_facebook_loginview_cancel_action);
                Profile profile = Profile.getCurrentProfile();
                if (profile == null || profile.getName() == null) {
                    message = LoginButton.this.getResources().getString(C0322R.string.com_facebook_loginview_logged_in_using_facebook);
                } else {
                    message = String.format(LoginButton.this.getResources().getString(C0322R.string.com_facebook_loginview_logged_in_as), new Object[]{profile.getName()});
                }
                Builder builder = new Builder(context);
                builder.setMessage(message).setCancelable(true).setPositiveButton(logout, new C03811()).setNegativeButton(cancel, null);
                builder.create().show();
            } else {
                LoginButton.this.getLoginManager().logOut();
            }
            AppEventsLogger logger = AppEventsLogger.newLogger(LoginButton.this.getContext());
            Bundle parameters = new Bundle();
            parameters.putInt("logging_in", accessToken != null ? 0 : 1);
            logger.logSdkEvent(LoginButton.this.loginLogoutEventName, null, parameters);
        }
    }

    public enum ToolTipMode {
        AUTOMATIC("automatic", 0),
        DISPLAY_ALWAYS("display_always", 1),
        NEVER_DISPLAY("never_display", 2);
        
        public static ToolTipMode DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = AUTOMATIC;
        }

        public static ToolTipMode fromInt(int enumValue) {
            for (ToolTipMode mode : values()) {
                if (mode.getValue() == enumValue) {
                    return mode;
                }
            }
            return null;
        }

        private ToolTipMode(String stringValue, int value) {
            this.stringValue = stringValue;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        public int getValue() {
            return this.intValue;
        }
    }

    static {
        TAG = LoginButton.class.getName();
    }

    public LoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle, 0, "fb_login_button_create", "fb_login_button_did_tap");
        this.properties = new LoginButtonProperties();
        this.loginLogoutEventName = "fb_login_view_usage";
        this.toolTipStyle = Style.BLUE;
        this.toolTipDisplayTime = 6000;
    }

    public void setDefaultAudience(DefaultAudience defaultAudience) {
        this.properties.setDefaultAudience(defaultAudience);
    }

    public DefaultAudience getDefaultAudience() {
        return this.properties.getDefaultAudience();
    }

    public void setReadPermissions(List<String> permissions) {
        this.properties.setReadPermissions(permissions);
    }

    public void setReadPermissions(String... permissions) {
        this.properties.setReadPermissions(Arrays.asList(permissions));
    }

    public void setPublishPermissions(List<String> permissions) {
        this.properties.setPublishPermissions(permissions);
    }

    public void setPublishPermissions(String... permissions) {
        this.properties.setPublishPermissions(Arrays.asList(permissions));
    }

    public void setLoginBehavior(LoginBehavior loginBehavior) {
        this.properties.setLoginBehavior(loginBehavior);
    }

    public LoginBehavior getLoginBehavior() {
        return this.properties.getLoginBehavior();
    }

    public void setToolTipStyle(Style toolTipStyle) {
        this.toolTipStyle = toolTipStyle;
    }

    public void setToolTipMode(ToolTipMode toolTipMode) {
        this.toolTipMode = toolTipMode;
    }

    public ToolTipMode getToolTipMode() {
        return this.toolTipMode;
    }

    public void setToolTipDisplayTime(long displayTime) {
        this.toolTipDisplayTime = displayTime;
    }

    public long getToolTipDisplayTime() {
        return this.toolTipDisplayTime;
    }

    public void dismissToolTip() {
        if (this.toolTipPopup != null) {
            this.toolTipPopup.dismiss();
            this.toolTipPopup = null;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.accessTokenTracker != null && !this.accessTokenTracker.isTracking()) {
            this.accessTokenTracker.startTracking();
            setButtonText();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.toolTipChecked && !isInEditMode()) {
            this.toolTipChecked = true;
            checkToolTipSettings();
        }
    }

    private void showToolTipPerSettings(FetchedAppSettings settings) {
        if (settings != null && settings.getNuxEnabled() && getVisibility() == 0) {
            displayToolTip(settings.getNuxContent());
        }
    }

    private void displayToolTip(String toolTipString) {
        this.toolTipPopup = new ToolTipPopup(toolTipString, this);
        this.toolTipPopup.setStyle(this.toolTipStyle);
        this.toolTipPopup.setNuxDisplayTime(this.toolTipDisplayTime);
        this.toolTipPopup.show();
    }

    private void checkToolTipSettings() {
        switch (C03803.$SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[this.toolTipMode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                FacebookSdk.getExecutor().execute(new C03781(Utility.getMetadataApplicationId(getContext())));
            case TTSConst.TTSPARAGRAPH /*2*/:
                displayToolTip(getResources().getString(C0322R.string.com_facebook_tooltip_default));
            default:
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setButtonText();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.accessTokenTracker != null) {
            this.accessTokenTracker.stopTracking();
        }
        dismissToolTip();
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != 0) {
            dismissToolTip();
        }
    }

    List<String> getPermissions() {
        return this.properties.getPermissions();
    }

    void setProperties(LoginButtonProperties properties) {
        this.properties = properties;
    }

    protected void configureButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.configureButton(context, attrs, defStyleAttr, defStyleRes);
        setInternalOnClickListener(new LoginClickListener());
        parseLoginButtonAttributes(context, attrs, defStyleAttr, defStyleRes);
        if (isInEditMode()) {
            setBackgroundColor(getResources().getColor(C0322R.color.com_facebook_blue));
            this.loginText = "Log in with Facebook";
        } else {
            this.accessTokenTracker = new C03792();
        }
        setButtonText();
    }

    protected int getDefaultStyleResource() {
        return C0322R.style.com_facebook_loginview_default_style;
    }

    private void parseLoginButtonAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.toolTipMode = ToolTipMode.DEFAULT;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, C0322R.styleable.com_facebook_login_view, defStyleAttr, defStyleRes);
        try {
            this.confirmLogout = a.getBoolean(C0322R.styleable.com_facebook_login_view_com_facebook_confirm_logout, true);
            this.loginText = a.getString(C0322R.styleable.com_facebook_login_view_com_facebook_login_text);
            this.logoutText = a.getString(C0322R.styleable.com_facebook_login_view_com_facebook_logout_text);
            this.toolTipMode = ToolTipMode.fromInt(a.getInt(C0322R.styleable.com_facebook_login_view_com_facebook_tooltip_mode, ToolTipMode.DEFAULT.getValue()));
        } finally {
            a.recycle();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int logInWidth;
        FontMetrics fontMetrics = getPaint().getFontMetrics();
        int height = (getCompoundPaddingTop() + ((int) Math.ceil((double) (Math.abs(fontMetrics.top) + Math.abs(fontMetrics.bottom))))) + getCompoundPaddingBottom();
        Resources resources = getResources();
        String text = this.loginText;
        if (text == null) {
            text = resources.getString(C0322R.string.com_facebook_loginview_log_in_button_long);
            logInWidth = measureButtonWidth(text);
            if (resolveSize(logInWidth, widthMeasureSpec) < logInWidth) {
                text = resources.getString(C0322R.string.com_facebook_loginview_log_in_button);
            }
        }
        logInWidth = measureButtonWidth(text);
        text = this.logoutText;
        if (text == null) {
            text = resources.getString(C0322R.string.com_facebook_loginview_log_out_button);
        }
        setMeasuredDimension(resolveSize(Math.max(logInWidth, measureButtonWidth(text)), widthMeasureSpec), height);
    }

    private int measureButtonWidth(String text) {
        return ((getCompoundPaddingLeft() + getCompoundDrawablePadding()) + measureTextWidth(text)) + getCompoundPaddingRight();
    }

    private void setButtonText() {
        Resources resources = getResources();
        if (!isInEditMode() && AccessToken.getCurrentAccessToken() != null) {
            CharSequence charSequence;
            if (this.logoutText != null) {
                charSequence = this.logoutText;
            } else {
                charSequence = resources.getString(C0322R.string.com_facebook_loginview_log_out_button);
            }
            setText(charSequence);
        } else if (this.loginText != null) {
            setText(this.loginText);
        } else {
            String text = resources.getString(C0322R.string.com_facebook_loginview_log_in_button_long);
            int width = getWidth();
            if (width != 0 && measureButtonWidth(text) > width) {
                text = resources.getString(C0322R.string.com_facebook_loginview_log_in_button);
            }
            setText(text);
        }
    }

    protected int getDefaultRequestCode() {
        return RequestCodeOffset.Login.toRequestCode();
    }

    LoginManager getLoginManager() {
        if (this.loginManager == null) {
            this.loginManager = LoginManager.getInstance();
        }
        return this.loginManager;
    }

    void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }
}
