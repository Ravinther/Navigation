package com.flurry.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.flurry.sdk.hj;
import com.flurry.sdk.jq;
import com.flurry.sdk.lc;

public final class InstallReceiver extends BroadcastReceiver {
    static final String f538a;

    static {
        f538a = InstallReceiver.class.getSimpleName();
    }

    public void onReceive(Context context, Intent intent) {
        jq.m1016a(4, f538a, "Received an Install nofication of " + intent.getAction());
        String string = intent.getExtras().getString("referrer");
        jq.m1016a(4, f538a, "Received an Install referrer of " + string);
        if (string == null || !"com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
            jq.m1016a(5, f538a, "referrer is null");
            return;
        }
        if (!string.contains("=")) {
            jq.m1016a(4, f538a, "referrer is before decoding: " + string);
            string = lc.m1274d(string);
            jq.m1016a(4, f538a, "referrer is: " + string);
        }
        new hj(context).m528a(string);
    }
}
