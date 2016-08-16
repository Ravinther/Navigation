package com.sygic.aura.c2dm;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Log;
import com.sygic.aura.SygicPreferences;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.utils.GuiUtils;
import com.sygic.base.C1799R;

public class C2DMessaging {
    private static final String TAG;
    private static Class<? extends Activity> activity;

    static {
        activity = null;
        TAG = C2DMessaging.class.getName();
    }

    public static void showNotification(Context context, Intent intent) {
        if ((intent == null || !intent.hasExtra("show") || !"0".equals(intent.getExtras().getString("show"))) && intent != null && intent.hasExtra("message")) {
            String strText = null;
            String strIcon = null;
            String strAction = null;
            String strActionArg = null;
            String strTitle = context.getString(C1799R.string.app_name);
            if (intent.hasExtra(AbstractFragment.ARG_TITLE)) {
                strTitle = intent.getExtras().getString(AbstractFragment.ARG_TITLE);
            }
            if (intent.hasExtra("message")) {
                strText = intent.getExtras().getString("message");
            }
            if (intent.hasExtra("icon")) {
                strIcon = intent.getExtras().getString("icon");
            }
            if (intent.hasExtra("action_list")) {
                strAction = "list";
                strActionArg = intent.getExtras().getString("action_list");
            }
            if (intent.hasExtra("action_detail")) {
                strAction = "detail";
                strActionArg = intent.getExtras().getString("action_detail");
            }
            if (intent.hasExtra("action_url")) {
                strAction = "url";
                strActionArg = intent.getExtras().getString("action_url");
            }
            if (strActionArg != null && intent.hasExtra("id")) {
                strActionArg = strActionArg + "||" + intent.getExtras().getString("id");
            }
            Log.i(TAG, "Message received: " + strText);
            if (!intent.hasExtra("image_url") || VERSION.SDK_INT < 16) {
                GuiUtils.showPromoNotification(context, strTitle, strText, strIcon, strAction, strActionArg);
            } else {
                context.startService(new Intent(context, NotifImageDownloaderService.class).setAction("com.sygic.aura.c2dm.ACTION_DOWNLOAD_IMAGE_AND_SHOW_NOTIF").putExtra(AbstractFragment.ARG_TITLE, strTitle).putExtra("text", strText).putExtra("icon", strIcon).putExtra("action", strAction).putExtra("action_arg", strActionArg).putExtra("image_url", intent.getStringExtra("image_url")));
            }
        }
    }

    public static void register(Context context, String senderId) {
        Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
        registrationIntent.setPackage("com.google.android.gsf");
        registrationIntent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
        registrationIntent.putExtra("sender", senderId);
        context.startService(registrationIntent);
    }

    public static String getRegistrationId(Context context) {
        return SygicPreferences.getPreferences(context).getString("registration_id", "");
    }

    static long getBackoff(Context context) {
        return SygicPreferences.getPreferences(context).getLong("backoff", 30000);
    }

    static void setBackoff(Context context, long lBackoff) {
        Editor editor = SygicPreferences.getPreferences(context).edit();
        editor.putLong("backoff", lBackoff);
        editor.commit();
    }

    static void clearRegistrationId(Context context) {
        Editor editor = SygicPreferences.getPreferences(context).edit();
        editor.putString("registration_id", "");
        editor.putLong("last_registration_change", System.currentTimeMillis());
        editor.commit();
    }

    static void setRegistrationId(Context context, String strRegistrationId) {
        Editor editor = SygicPreferences.getPreferences(context).edit();
        editor.putString("registration_id", strRegistrationId);
        editor.commit();
    }

    static void setRegistrationStatus(Context context, int iStatus) {
        Editor editor = SygicPreferences.getPreferences(context).edit();
        editor.putInt("registration_status", iStatus);
        editor.commit();
    }
}
