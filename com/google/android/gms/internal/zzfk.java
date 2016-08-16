package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@zzgk
public class zzfk implements zzfi {
    private final Context mContext;
    final Set<WebView> zzBE;

    /* renamed from: com.google.android.gms.internal.zzfk.1 */
    class C09191 implements Runnable {
        final /* synthetic */ String zzBF;
        final /* synthetic */ String zzBG;
        final /* synthetic */ zzfk zzBH;

        /* renamed from: com.google.android.gms.internal.zzfk.1.1 */
        class C09181 extends WebViewClient {
            final /* synthetic */ C09191 zzBI;
            final /* synthetic */ WebView zzrZ;

            C09181(C09191 c09191, WebView webView) {
                this.zzBI = c09191;
                this.zzrZ = webView;
            }

            public void onPageFinished(WebView view, String url) {
                zzb.zzaC("Loading assets have finished");
                this.zzBI.zzBH.zzBE.remove(this.zzrZ);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                zzb.zzaE("Loading assets have failed.");
                this.zzBI.zzBH.zzBE.remove(this.zzrZ);
            }
        }

        C09191(zzfk com_google_android_gms_internal_zzfk, String str, String str2) {
            this.zzBH = com_google_android_gms_internal_zzfk;
            this.zzBF = str;
            this.zzBG = str2;
        }

        public void run() {
            WebView zzfb = this.zzBH.zzfb();
            zzfb.setWebViewClient(new C09181(this, zzfb));
            this.zzBH.zzBE.add(zzfb);
            zzfb.loadDataWithBaseURL(this.zzBF, this.zzBG, "text/html", "UTF-8", null);
            zzb.zzaC("Fetching assets finished.");
        }
    }

    public zzfk(Context context) {
        this.zzBE = Collections.synchronizedSet(new HashSet());
        this.mContext = context;
    }

    public void zza(String str, String str2, String str3) {
        zzb.zzaC("Fetching assets for the given html");
        zzhu.zzHK.post(new C09191(this, str2, str3));
    }

    public WebView zzfb() {
        WebView webView = new WebView(this.mContext);
        webView.getSettings().setJavaScriptEnabled(true);
        return webView;
    }
}
