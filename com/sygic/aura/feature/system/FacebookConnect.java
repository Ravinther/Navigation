package com.sygic.aura.feature.system;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;
import com.sygic.aura.SygicMain;

public class FacebookConnect {
    private static final String TAG;
    private String mStrAccessToken;
    private int m_activityCode;

    static {
        TAG = FacebookConnect.class.getCanonicalName();
    }

    public FacebookConnect() {
        this.m_activityCode = -1;
        this.mStrAccessToken = "";
    }

    public boolean startSingleSignOn(Context context, String applicationId, int activityCode) {
        String[] permissions = new String[]{"email,user_about_me,friends_about_me,user_checkins,friends_checkins,user_location,friends_location,user_notes,friends_notes,user_photos,friends_photos,user_status,user_videos,user_website,read_friendlists,publish_stream,publish_checkins,offline_access"};
        this.m_activityCode = activityCode;
        Intent intent = new Intent();
        intent.setClassName("com.facebook.katana", "com.facebook.katana.ProxyAuth");
        intent.putExtra("client_id", applicationId);
        if (permissions.length > 0) {
            intent.putExtra("scope", TextUtils.join(",", permissions));
        }
        try {
            Activity act = SygicMain.getInstance().getFeature().getActivity();
            if (act != null) {
                act.startActivityForResult(intent, activityCode);
                return true;
            }
            context.startActivity(intent);
            throw new IllegalStateException("Activity doesn't exists in " + FacebookConnect.class.getName() + ".startSingleSignOn()");
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public boolean startSingleSignOnCallback(int requestCode, int resultCode, Intent data) {
        if (requestCode != this.m_activityCode) {
            return false;
        }
        if (resultCode == -1) {
            if (data == null) {
                Log.w(TAG, "No login data");
                return false;
            }
            if (data.hasExtra("access_token")) {
                this.mStrAccessToken = data.getStringExtra("access_token");
            }
            if (this.mStrAccessToken != null) {
                return true;
            }
            Log.w(TAG, "Login failed");
            if (!data.hasExtra("error_message")) {
                return false;
            }
            Log.w(TAG, "Login failed with: " + data.getStringExtra("error_message"));
            return false;
        } else if (resultCode != 0) {
            return false;
        } else {
            if (data != null) {
                Log.w(TAG, "FB_SSO_CONNECT: login failed: " + data.getStringExtra("error_message"));
                return false;
            }
            Log.w(TAG, "FB_SSO_CONNECT: login canceled by user.");
            return false;
        }
    }

    public String getAccessToken() {
        return this.mStrAccessToken;
    }

    public boolean isFBApplication(Context context) {
        try {
            if (context.getPackageManager().getApplicationInfo("com.facebook.katana", 0) != null) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
