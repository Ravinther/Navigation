package com.infinario.android.infinariosdk;

import android.app.IntentService;
import android.content.Intent;

public class GcmIntentService extends IntentService {
    public GcmIntentService() {
        super("GcmIntentService");
    }

    protected void onHandleIntent(Intent intent) {
        Infinario.handleGooglePushNotification(this, intent);
    }
}
