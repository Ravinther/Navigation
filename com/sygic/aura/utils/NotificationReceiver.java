package com.sygic.aura.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class NotificationReceiver extends BroadcastReceiver {

    public static class RetentionNotificationUtils {
        public static void resetDismissCounter(Context context) {
            setDismissCounter(context, 0);
        }

        public static void increaseDismissCounter(Context context) {
            setDismissCounter(context, PreferenceManager.getDefaultSharedPreferences(context).getInt("dismiss_counter", 0) + 1);
        }

        private static void setDismissCounter(Context context, int counter) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("dismiss_counter", counter).apply();
        }

        public static boolean shouldBeShown(Context context) {
            if (PreferenceManager.getDefaultSharedPreferences(context).getInt("dismiss_counter", 0) < 2) {
                return true;
            }
            return false;
        }
    }

    public void onReceive(Context context, Intent intent) {
        if ("com.sygic.aura.ACTION_DISMISSED_RETENTION_NOTIFICATION".equals(intent.getAction())) {
            RetentionNotificationUtils.increaseDismissCounter(context);
            AlarmUtils.setupRetentionNotification(context);
        }
    }
}
