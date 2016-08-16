package com.sygic.aura.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import com.sygic.aura.tracker.TrackerService;
import com.sygic.aura.utils.NotificationReceiver.RetentionNotificationUtils;
import java.util.Calendar;
import java.util.Random;

public class AlarmUtils {
    public static void setupNightlyAlarm(Context context) {
        PendingIntent pendingIntent = PendingIntent.getService(context, 789, new Intent(context, TrackerService.class).setAction("com.sygic.aura.ACTION_NIGHTLY"), 134217728);
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 1);
        calendar.set(12, 0);
        if (!calendar.after(now)) {
            calendar.add(5, 1);
        }
        ((AlarmManager) context.getSystemService("alarm")).setInexactRepeating(0, calendar.getTimeInMillis() + ((long) new Random().nextInt(1800000)), 86400000, pendingIntent);
    }

    public static void setupRetentionNotification(Context context) {
        if (RetentionNotificationUtils.shouldBeShown(context)) {
            Intent intent = new Intent("com.sygic.aura.ACTION_RETENTION_NOTIFICATION");
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            Calendar calendar = Calendar.getInstance();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 5789, intent, 134217728);
            calendar.add(5, 7);
            calendar.set(12, 0);
            calendar.set(11, 12);
            alarmManager.cancel(pendingIntent);
            setExact(alarmManager, pendingIntent, calendar.getTimeInMillis());
        }
    }

    public static void setupPostponedNotificationAlarm(Context context, Intent paramsIntent) {
        setExact((AlarmManager) context.getSystemService("alarm"), PendingIntent.getBroadcast(context, 1252, new Intent("com.sygic.aura.ACTION_REMINDED_NOTIFICATION").putExtras(paramsIntent), 134217728), System.currentTimeMillis() + 1800000);
    }

    public static void setExact(AlarmManager alarmManager, PendingIntent pendingIntent, long startTime) {
        if (VERSION.SDK_INT >= 19) {
            alarmManager.setExact(0, startTime, pendingIntent);
        } else {
            alarmManager.set(0, startTime, pendingIntent);
        }
    }
}
