package com.bosch.myspin.serversdk.maps;

import android.annotation.TargetApi;
import android.app.Activity;

public class MySpinJavaScriptHandler {
    private static Activity f97a;

    @TargetApi(19)
    protected static void webViewExecuteCommand(String str) {
        if (f97a != null) {
            f97a.runOnUiThread(new C0170c(str));
        }
    }

    public static void setActivity(Activity activity) {
        f97a = activity;
    }
}
