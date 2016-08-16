package com.google.android.gms.internal;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzw;
import java.net.URI;
import java.net.URISyntaxException;

@zzgk
public class zzix extends WebViewClient {
    private final String zzJB;
    private boolean zzJC;
    private final zzfx zzJD;
    private final zzip zzoL;

    public zzix(zzfx com_google_android_gms_internal_zzfx, zzip com_google_android_gms_internal_zzip, String str) {
        this.zzJB = zzaM(str);
        this.zzJC = false;
        this.zzoL = com_google_android_gms_internal_zzip;
        this.zzJD = com_google_android_gms_internal_zzfx;
    }

    private String zzaM(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (str.endsWith("/")) {
                    str = str.substring(0, str.length() - 1);
                }
            } catch (IndexOutOfBoundsException e) {
                zzb.m1444e(e.getMessage());
            }
        }
        return str;
    }

    public void onLoadResource(WebView view, String url) {
        zzb.zzaC("JavascriptAdWebViewClient::onLoadResource: " + url);
        if (!zzaL(url)) {
            this.zzoL.zzgS().onLoadResource(this.zzoL.getWebView(), url);
        }
    }

    public void onPageFinished(WebView view, String url) {
        zzb.zzaC("JavascriptAdWebViewClient::onPageFinished: " + url);
        if (!this.zzJC) {
            this.zzJD.zzfl();
            this.zzJC = true;
        }
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        zzb.zzaC("JavascriptAdWebViewClient::shouldOverrideUrlLoading: " + url);
        if (!zzaL(url)) {
            return this.zzoL.zzgS().shouldOverrideUrlLoading(this.zzoL.getWebView(), url);
        }
        zzb.zzaC("shouldOverrideUrlLoading: received passback url");
        return true;
    }

    protected boolean zzaL(String str) {
        Object zzaM = zzaM(str);
        if (TextUtils.isEmpty(zzaM)) {
            return false;
        }
        try {
            URI uri = new URI(zzaM);
            if ("passback".equals(uri.getScheme())) {
                zzb.zzaC("Passback received");
                this.zzJD.zzfm();
                return true;
            } else if (TextUtils.isEmpty(this.zzJB)) {
                return false;
            } else {
                URI uri2 = new URI(this.zzJB);
                String host = uri2.getHost();
                String host2 = uri.getHost();
                String path = uri2.getPath();
                String path2 = uri.getPath();
                if (!zzw.equal(host, host2) || !zzw.equal(path, path2)) {
                    return false;
                }
                zzb.zzaC("Passback received");
                this.zzJD.zzfm();
                return true;
            }
        } catch (URISyntaxException e) {
            zzb.m1444e(e.getMessage());
            return false;
        }
    }
}
