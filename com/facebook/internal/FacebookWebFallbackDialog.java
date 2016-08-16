package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookWebFallbackDialog extends WebDialog {
    private static final String TAG;
    private boolean waitingForDialogToClose;

    /* renamed from: com.facebook.internal.FacebookWebFallbackDialog.1 */
    class C03461 implements Runnable {
        C03461() {
        }

        public void run() {
            super.cancel();
        }
    }

    static {
        TAG = FacebookWebFallbackDialog.class.getName();
    }

    public FacebookWebFallbackDialog(Context context, String url, String expectedRedirectUrl) {
        super(context, url);
        setExpectedRedirectUrl(expectedRedirectUrl);
    }

    protected Bundle parseResponseUri(String url) {
        Bundle queryParams = Utility.parseUrlQueryString(Uri.parse(url).getQuery());
        String bridgeArgsJSONString = queryParams.getString("bridge_args");
        queryParams.remove("bridge_args");
        if (!Utility.isNullOrEmpty(bridgeArgsJSONString)) {
            try {
                queryParams.putBundle("com.facebook.platform.protocol.BRIDGE_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(bridgeArgsJSONString)));
            } catch (JSONException je) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", je);
            }
        }
        String methodResultsJSONString = queryParams.getString("method_results");
        queryParams.remove("method_results");
        if (!Utility.isNullOrEmpty(methodResultsJSONString)) {
            if (Utility.isNullOrEmpty(methodResultsJSONString)) {
                methodResultsJSONString = "{}";
            }
            try {
                queryParams.putBundle("com.facebook.platform.protocol.RESULT_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(methodResultsJSONString)));
            } catch (JSONException je2) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", je2);
            }
        }
        queryParams.remove("version");
        queryParams.putInt("com.facebook.platform.protocol.PROTOCOL_VERSION", NativeProtocol.getLatestKnownVersion());
        return queryParams;
    }

    public void cancel() {
        WebView webView = getWebView();
        if (!isPageFinished() || isListenerCalled() || webView == null || !webView.isShown()) {
            super.cancel();
        } else if (!this.waitingForDialogToClose) {
            this.waitingForDialogToClose = true;
            webView.loadUrl("javascript:" + "(function() {  var event = document.createEvent('Event');  event.initEvent('fbPlatformDialogMustClose',true,true);  document.dispatchEvent(event);})();");
            new Handler(Looper.getMainLooper()).postDelayed(new C03461(), 1500);
        }
    }
}
