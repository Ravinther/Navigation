package com.infinario.android.infinariosdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ReferrerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Log.d("Infinario", "Referrer received: " + intent.toString());
            String rawReferrer = intent.getStringExtra("referrer");
            if (rawReferrer != null) {
                saveReferrer(rawReferrer, context);
            }
        }
    }

    private void saveReferrer(String rawReferrer, Context context) {
        try {
            String referrer = URLDecoder.decode(rawReferrer, "UTF-8");
            if (referrer != null && !referrer.isEmpty()) {
                try {
                    Preferences.get(context).setReferrer(Uri.parse('?' + referrer).getQueryParameter("campaign_id"));
                } catch (UnsupportedOperationException e) {
                }
            }
        } catch (UnsupportedEncodingException e2) {
        }
    }
}
