package com.crashlytics.android.answers;

import android.app.Activity;
import android.os.Bundle;
import io.fabric.sdk.android.ActivityLifecycleManager.Callbacks;

class AnswersLifecycleCallbacks extends Callbacks {
    private final SessionAnalyticsManager analyticsManager;

    public AnswersLifecycleCallbacks(SessionAnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        this.analyticsManager.onLifecycle(activity, Type.CREATE);
    }

    public void onActivityStarted(Activity activity) {
        this.analyticsManager.onLifecycle(activity, Type.START);
    }

    public void onActivityResumed(Activity activity) {
        this.analyticsManager.onLifecycle(activity, Type.RESUME);
    }

    public void onActivityPaused(Activity activity) {
        this.analyticsManager.onLifecycle(activity, Type.PAUSE);
    }

    public void onActivityStopped(Activity activity) {
        this.analyticsManager.onLifecycle(activity, Type.STOP);
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.analyticsManager.onLifecycle(activity, Type.SAVE_INSTANCE_STATE);
    }

    public void onActivityDestroyed(Activity activity) {
        this.analyticsManager.onLifecycle(activity, Type.DESTROY);
    }
}
