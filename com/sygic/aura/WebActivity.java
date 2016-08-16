package com.sygic.aura;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {

    /* renamed from: com.sygic.aura.WebActivity.1 */
    class C11241 extends WebViewClient {
        final /* synthetic */ WebView val$webView;

        C11241(WebView webView) {
            this.val$webView = webView;
        }

        public void onPageFinished(WebView view, String url) {
            Uri uriPage = Uri.parse(url);
            Intent data;
            if (uriPage.getScheme().equals("webdb-jlti4f13g1dbwhb")) {
                data = new Intent();
                data.putExtra("EXTRA_URL", url);
                WebActivity.this.setResult(-1, data);
                WebActivity.this.finish();
            } else if ("accounts.google.com".equals(uriPage.getHost())) {
                String strTitle = this.val$webView.getTitle();
                if (strTitle.contains("code=")) {
                    String strCode = strTitle.substring(strTitle.indexOf(61) + 1);
                    data = new Intent();
                    data.putExtra("EXTRA_ACCESS_TOKEN", strCode);
                    WebActivity.this.setResult(-1, data);
                    WebActivity.this.finish();
                }
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null || (!url.startsWith("com.sygic.aura://") && !url.startsWith("com.sygic.webcontrol://"))) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            intent.setFlags(268435456);
            SygicMain.getInstance().getFeature().getCommonFeature().handleWebLink(intent);
            WebActivity.this.finish();
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri == null) {
            finish();
            return;
        }
        WebView webView = new WebView(this);
        setContentView(webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new C11241(webView));
        if (webView != null && uri != null) {
            webView.loadUrl(uri.toString());
        }
    }
}
