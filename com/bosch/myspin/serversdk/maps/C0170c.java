package com.bosch.myspin.serversdk.maps;

import android.os.Build.VERSION;

/* renamed from: com.bosch.myspin.serversdk.maps.c */
final class C0170c implements Runnable {
    final /* synthetic */ String f131a;

    C0170c(String str) {
        this.f131a = str;
    }

    public void run() {
        if (MySpinMapView.sWebView == null) {
            return;
        }
        if (VERSION.SDK_INT < 19 || !this.f131a.startsWith("javascript:")) {
            MySpinMapView.sWebView.loadUrl(this.f131a);
        } else {
            MySpinMapView.sWebView.evaluateJavascript(this.f131a.split("javascript:")[1], null);
        }
    }
}
