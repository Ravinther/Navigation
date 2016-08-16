package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzgk
public class zziv extends zziq {
    public zziv(zzip com_google_android_gms_internal_zzip, boolean z) {
        super(com_google_android_gms_internal_zzip, z);
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        Exception e;
        try {
            if (!"mraid.js".equalsIgnoreCase(new File(url).getName())) {
                return super.shouldInterceptRequest(webView, url);
            }
            if (webView instanceof zzip) {
                zzip com_google_android_gms_internal_zzip = (zzip) webView;
                com_google_android_gms_internal_zzip.zzgS().zzeA();
                String str = com_google_android_gms_internal_zzip.zzaN().zzsH ? (String) zzby.zzuA.get() : com_google_android_gms_internal_zzip.zzgW() ? (String) zzby.zzuz.get() : (String) zzby.zzuy.get();
                zzb.m1445v("shouldInterceptRequest(" + str + ")");
                return zze(com_google_android_gms_internal_zzip.getContext(), this.zzoL.zzgV().zzIz, str);
            }
            zzb.zzaE("Tried to intercept request from a WebView that wasn't an AdWebView.");
            return super.shouldInterceptRequest(webView, url);
        } catch (IOException e2) {
            e = e2;
            zzb.zzaE("Could not fetch MRAID JS. " + e.getMessage());
            return super.shouldInterceptRequest(webView, url);
        } catch (ExecutionException e3) {
            e = e3;
            zzb.zzaE("Could not fetch MRAID JS. " + e.getMessage());
            return super.shouldInterceptRequest(webView, url);
        } catch (InterruptedException e4) {
            e = e4;
            zzb.zzaE("Could not fetch MRAID JS. " + e.getMessage());
            return super.shouldInterceptRequest(webView, url);
        } catch (TimeoutException e5) {
            e = e5;
            zzb.zzaE("Could not fetch MRAID JS. " + e.getMessage());
            return super.shouldInterceptRequest(webView, url);
        }
    }

    protected WebResourceResponse zze(Context context, String str, String str2) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        Map hashMap = new HashMap();
        hashMap.put("User-Agent", zzp.zzbx().zzf(context, str));
        hashMap.put("Cache-Control", "max-stale=3600");
        String str3 = (String) new zzhy(context).zzb(str2, hashMap).get(60, TimeUnit.SECONDS);
        return str3 == null ? null : new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(str3.getBytes("UTF-8")));
    }
}
