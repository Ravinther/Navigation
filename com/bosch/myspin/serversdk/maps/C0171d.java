package com.bosch.myspin.serversdk.maps;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* renamed from: com.bosch.myspin.serversdk.maps.d */
class C0171d extends WebViewClient {
    final /* synthetic */ Context f132a;
    final /* synthetic */ MySpinMapView f133b;

    C0171d(MySpinMapView mySpinMapView, Context context) {
        this.f133b = mySpinMapView;
        this.f132a = context;
    }

    public void onPageFinished(WebView webView, String str) {
        if (str.equals("fake://invalid")) {
            MySpinMapView.sMySpinMap = new MySpinMap(MySpinMapView.sMySpinMapView, MySpinMapView.sWebView, MySpinMapView.sMapOptions);
            if (this.f133b.f122b != null) {
                this.f133b.f122b.onMapLoadedListener();
            }
        } else if (this.f133b.f123c != null) {
            this.f133b.f123c.onMapLeftListener(str);
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        Intent intent = new Intent("com.bosch.myspin.OPEN_GOOGLE_URL_ACTION");
        intent.putExtra("com.bosch.myspin.OPEN_GOOGLE_URL_EXTRA", str);
        this.f132a.sendBroadcast(intent);
        return true;
    }
}
