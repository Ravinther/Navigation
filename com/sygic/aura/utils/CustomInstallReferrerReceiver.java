package com.sygic.aura.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.ads.conversiontracking.InstallReceiver;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.infinario.android.infinariosdk.ReferrerReceiver;

public class CustomInstallReferrerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        new ReferrerReceiver().onReceive(context, intent);
        new InstallReceiver().onReceive(context, intent);
        new com.flurry.android.InstallReceiver().onReceive(context, intent);
        new CampaignTrackingReceiver().onReceive(context, intent);
    }
}
