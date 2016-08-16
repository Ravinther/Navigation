package com.sygic.aura.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fcd.FloatingCarDataService;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.utils.AlarmUtils;
import com.sygic.aura.utils.GuiUtils;
import com.sygic.base.C1799R;
import java.util.HashMap;
import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("com.sygic.aura.ACTION_RATE_NOTIFICATION".equals(intent.getAction())) {
            handleRateNotification(context);
        } else if ("com.sygic.aura.ACTION_POSTPONE_NOTIFICATION".equals(intent.getAction())) {
            handlePostponeNotification(context, intent);
        } else if ("com.sygic.aura.ACTION_REMINDED_NOTIFICATION".equals(intent.getAction())) {
            handleRemindedNotification(context, intent);
        } else if ("com.sygic.aura.ACTION_RETENTION_NOTIFICATION".equals(intent.getAction())) {
            handleRetentionNotification(context);
        } else if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) || "android.intent.action.QUICKBOOT_POWERON".equals(intent.getAction()) || "com.htc.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())) {
            handleBootCompleted(context);
        }
    }

    private void handleRateNotification(Context context) {
        GuiUtils.showRateNotification(context, ResourceManager.getCoreString(context, 2131165788), ResourceManager.getCoreString(context, 2131165787), "url", ResourceManager.getGooglePlayUrl(context));
    }

    private void handlePostponeNotification(Context context, Intent intent) {
        AlarmUtils.setupPostponedNotificationAlarm(context, intent);
        Bundle bundle = new Bundle();
        bundle.putString("eventName", "Promo notification");
        HashMap<String, Object> attrs = new HashMap();
        attrs.put("action", "postponed");
        bundle.putSerializable("attributes", attrs);
        SygicAnalyticsLogger.logEvent(context, EventType.NOTIFICATION, bundle);
        GuiUtils.cancelNotification(context, 2);
    }

    private void handleRemindedNotification(Context context, Intent intent) {
        GuiUtils.showPromoNotification(context, intent);
    }

    private void handleRetentionNotification(Context context) {
        String[] text = context.getResources().getStringArray(2131492884);
        GuiUtils.showRetentionNotification(context, text[new Random().nextInt(text.length)], context.getString(C1799R.string.retention_open_app));
    }

    private void handleBootCompleted(Context context) {
        AlarmUtils.setupNightlyAlarm(context);
        AlarmUtils.setupRetentionNotification(context);
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(2131165290), true)) {
            context.startService(new Intent(context, FloatingCarDataService.class));
        }
    }
}
