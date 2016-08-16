package com.sygic.aura.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class ConnectivityReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            ConnectivityChangesManager.get(context).addChange(ConnectivityChangesManager.typeFromNetworkInfo(((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()));
        }
    }
}
