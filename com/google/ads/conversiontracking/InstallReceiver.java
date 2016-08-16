package com.google.ads.conversiontracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class InstallReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("referrer");
        if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction()) && stringExtra != null) {
            String str = "GoogleConversionReporter";
            String str2 = "Received install referrer: ";
            String valueOf = String.valueOf(stringExtra);
            Log.i(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            AdWordsConversionReporter.registerReferrer(context, Uri.parse("http://hostname/").buildUpon().appendQueryParameter("referrer", stringExtra).build());
        }
    }
}
