package com.sygic.aura.c2dm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.SygicPreferences;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;

public class C2DMReceiver extends BroadcastReceiver {
    private static final String TAG;

    static {
        TAG = C2DMReceiver.class.getName();
    }

    protected void onMessage(Context context, Intent intent) {
        C2DMessaging.showNotification(context, intent);
    }

    public void onError(Context context, String errorId) {
        Log.i(TAG, "onError: " + errorId);
    }

    public void onRegistered(Context context, String registrationId) {
        SygicPreferences.setResetPushToken(context, true);
    }

    public void onUnregistered(Context context) {
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
            handleRegistration(context, intent);
        } else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
            handleMessage(context, intent);
        } else if (intent.getAction().equals("com.google.android.c2dm.intent.RETRY")) {
            String senderId = ProjectsConsts.getString(16);
            if (senderId == null) {
                senderId = "522187714847";
            }
            C2DMessaging.register(context, senderId);
        }
    }

    private void handleMessage(Context context, Intent intent) {
        onMessage(context, intent);
    }

    private void handleRegistration(Context context, Intent intent) {
        C2DMessaging.setRegistrationStatus(context, 0);
        String registrationId = intent.getStringExtra("registration_id");
        String error = intent.getStringExtra("error");
        String removed = intent.getStringExtra("unregistered");
        Log.d(TAG, "dmControl: registrationId = " + registrationId + ", error = " + error + ", removed = " + removed);
        if (removed != null) {
            C2DMessaging.clearRegistrationId(context);
            onUnregistered(context);
        } else if (error != null) {
            C2DMessaging.clearRegistrationId(context);
            Log.e(TAG, "Registration error " + error);
            onError(context, error);
            if ("SERVICE_NOT_AVAILABLE".equals(error)) {
                long backoffTimeMs = C2DMessaging.getBackoff(context);
                Log.d(TAG, "Scheduling registration retry, backoff = " + backoffTimeMs);
                ((AlarmManager) context.getSystemService("alarm")).set(3, backoffTimeMs, PendingIntent.getBroadcast(context, 0, new Intent("com.google.android.c2dm.intent.RETRY"), 0));
                C2DMessaging.setBackoff(context, backoffTimeMs * 2);
            }
        } else {
            C2DMessaging.setRegistrationId(context, registrationId);
            onRegistered(context, registrationId);
            InfinarioAnalyticsLogger.getInstance(context).update();
        }
    }
}
