package com.facebook.appevents;

import android.os.Bundle;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import java.io.Serializable;
import java.util.Locale;

class FacebookTimeSpentData implements Serializable {
    private static final long[] INACTIVE_SECONDS_QUANTA;
    private static final String TAG;
    private String firstOpenSourceApplication;
    private int interruptionCount;
    private boolean isAppActive;
    private boolean isWarmLaunch;
    private long lastActivateEventLoggedTime;
    private long lastResumeTime;
    private long lastSuspendTime;
    private long millisecondsSpentInSession;

    static {
        TAG = AppEventsLogger.class.getCanonicalName();
        INACTIVE_SECONDS_QUANTA = new long[]{300000, 900000, 1800000, 3600000, 21600000, 43200000, 86400000, 172800000, 259200000, 604800000, 1209600000, 1814400000, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
    }

    FacebookTimeSpentData() {
        resetSession();
    }

    void onSuspend(AppEventsLogger logger, long eventTime) {
        if (this.isAppActive) {
            long now = eventTime;
            long delta = now - this.lastResumeTime;
            if (delta < 0) {
                Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
                delta = 0;
            }
            this.millisecondsSpentInSession += delta;
            this.lastSuspendTime = now;
            this.isAppActive = false;
            return;
        }
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Suspend for inactive app");
    }

    void onResume(AppEventsLogger logger, long eventTime, String sourceApplicationInfo) {
        long now = eventTime;
        if (isColdLaunch() || now - this.lastActivateEventLoggedTime > 300000) {
            Bundle eventParams = new Bundle();
            eventParams.putString("fb_mobile_launch_source", sourceApplicationInfo);
            logger.logEvent("fb_mobile_activate_app", eventParams);
            this.lastActivateEventLoggedTime = now;
        }
        if (this.isAppActive) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Resume for active app");
            return;
        }
        long interruptionDurationMillis = wasSuspendedEver() ? now - this.lastSuspendTime : 0;
        if (interruptionDurationMillis < 0) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
            interruptionDurationMillis = 0;
        }
        if (interruptionDurationMillis > 60000) {
            logAppDeactivatedEvent(logger, interruptionDurationMillis);
        } else if (interruptionDurationMillis > 1000) {
            this.interruptionCount++;
        }
        if (this.interruptionCount == 0) {
            this.firstOpenSourceApplication = sourceApplicationInfo;
        }
        this.lastResumeTime = now;
        this.isAppActive = true;
    }

    private void logAppDeactivatedEvent(AppEventsLogger logger, long interruptionDurationMillis) {
        Bundle eventParams = new Bundle();
        eventParams.putInt("fb_mobile_app_interruptions", this.interruptionCount);
        eventParams.putString("fb_mobile_time_between_sessions", String.format(Locale.ROOT, "session_quanta_%d", new Object[]{Integer.valueOf(getQuantaIndex(interruptionDurationMillis))}));
        eventParams.putString("fb_mobile_launch_source", this.firstOpenSourceApplication);
        logger.logEvent("fb_mobile_deactivate_app", (double) (this.millisecondsSpentInSession / 1000), eventParams);
        resetSession();
    }

    private static int getQuantaIndex(long timeBetweenSessions) {
        int quantaIndex = 0;
        while (quantaIndex < INACTIVE_SECONDS_QUANTA.length && INACTIVE_SECONDS_QUANTA[quantaIndex] < timeBetweenSessions) {
            quantaIndex++;
        }
        return quantaIndex;
    }

    private void resetSession() {
        this.isAppActive = false;
        this.lastResumeTime = -1;
        this.lastSuspendTime = -1;
        this.interruptionCount = 0;
        this.millisecondsSpentInSession = 0;
    }

    private boolean wasSuspendedEver() {
        return this.lastSuspendTime != -1;
    }

    private boolean isColdLaunch() {
        boolean result = !this.isWarmLaunch;
        this.isWarmLaunch = true;
        return result;
    }
}
