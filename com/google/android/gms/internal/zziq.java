package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.overlay.zzn;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.ads.internal.zzp;
import com.sygic.aura.C1090R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@zzgk
public class zziq extends WebViewClient {
    private static final String[] zzIU;
    private static final String[] zzIV;
    private zza zzCv;
    private final HashMap<String, List<zzdg>> zzIW;
    private zzg zzIX;
    private zzb zzIY;
    private boolean zzIZ;
    private boolean zzJa;
    private zzn zzJb;
    private final zzfa zzJc;
    private boolean zzJd;
    private boolean zzJe;
    private boolean zzJf;
    private boolean zzJg;
    private int zzJh;
    protected final zzip zzoL;
    private final Object zzpc;
    private boolean zzqL;
    private com.google.android.gms.ads.internal.client.zza zzsn;
    private zzdd zzwH;
    private zzdk zzxh;
    private zze zzxj;
    private zzew zzxk;
    private zzdi zzxm;
    private zzfc zzzy;

    public interface zza {
        void zza(zzip com_google_android_gms_internal_zzip, boolean z);
    }

    public interface zzb {
        void zzbf();
    }

    /* renamed from: com.google.android.gms.internal.zziq.1 */
    class C09591 implements Runnable {
        final /* synthetic */ zziq zzJi;

        C09591(zziq com_google_android_gms_internal_zziq) {
            this.zzJi = com_google_android_gms_internal_zziq;
        }

        public void run() {
            this.zzJi.zzoL.zzgZ();
            com.google.android.gms.ads.internal.overlay.zzd zzgQ = this.zzJi.zzoL.zzgQ();
            if (zzgQ != null) {
                zzgQ.zzeA();
            }
            if (this.zzJi.zzIY != null) {
                this.zzJi.zzIY.zzbf();
                this.zzJi.zzIY = null;
            }
        }
    }

    private static class zzc implements zzg {
        private zzg zzIX;
        private zzip zzJj;

        public zzc(zzip com_google_android_gms_internal_zzip, zzg com_google_android_gms_ads_internal_overlay_zzg) {
            this.zzJj = com_google_android_gms_internal_zzip;
            this.zzIX = com_google_android_gms_ads_internal_overlay_zzg;
        }

        public void zzaV() {
            this.zzIX.zzaV();
            this.zzJj.zzgM();
        }

        public void zzaW() {
            this.zzIX.zzaW();
            this.zzJj.zzeD();
        }
    }

    private class zzd implements zzdg {
        final /* synthetic */ zziq zzJi;

        private zzd(zziq com_google_android_gms_internal_zziq) {
            this.zzJi = com_google_android_gms_internal_zziq;
        }

        public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
            if (map.keySet().contains("start")) {
                this.zzJi.zzhe();
            } else if (map.keySet().contains("stop")) {
                this.zzJi.zzhf();
            } else if (map.keySet().contains("cancel")) {
                this.zzJi.zzhg();
            }
        }
    }

    static {
        zzIU = new String[]{"UNKNOWN", "HOST_LOOKUP", "UNSUPPORTED_AUTH_SCHEME", "AUTHENTICATION", "PROXY_AUTHENTICATION", "CONNECT", "IO", "TIMEOUT", "REDIRECT_LOOP", "UNSUPPORTED_SCHEME", "FAILED_SSL_HANDSHAKE", "BAD_URL", "FILE", "FILE_NOT_FOUND", "TOO_MANY_REQUESTS"};
        zzIV = new String[]{"NOT_YET_VALID", "EXPIRED", "ID_MISMATCH", "UNTRUSTED", "DATE_INVALID", "INVALID"};
    }

    public zziq(zzip com_google_android_gms_internal_zzip, boolean z) {
        this(com_google_android_gms_internal_zzip, z, new zzfa(com_google_android_gms_internal_zzip, com_google_android_gms_internal_zzip.zzgO(), new zzbq(com_google_android_gms_internal_zzip.getContext())), null);
    }

    zziq(zzip com_google_android_gms_internal_zzip, boolean z, zzfa com_google_android_gms_internal_zzfa, zzew com_google_android_gms_internal_zzew) {
        this.zzIW = new HashMap();
        this.zzpc = new Object();
        this.zzIZ = false;
        this.zzoL = com_google_android_gms_internal_zzip;
        this.zzqL = z;
        this.zzJc = com_google_android_gms_internal_zzfa;
        this.zzxk = com_google_android_gms_internal_zzew;
    }

    private void zza(Context context, String str, String str2, String str3) {
        if (((Boolean) zzby.zzva.get()).booleanValue()) {
            Bundle bundle = new Bundle();
            bundle.putString("err", str);
            bundle.putString("code", str2);
            bundle.putString("host", zzaI(str3));
            zzp.zzbx().zza(context, this.zzoL.zzgV().zzIz, "gmob-apps", bundle, true);
        }
    }

    private String zzaI(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        Uri parse = Uri.parse(str);
        return parse.getHost() != null ? parse.getHost() : "";
    }

    private static boolean zzg(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    private void zzhe() {
        synchronized (this.zzpc) {
            this.zzJa = true;
        }
        this.zzJh++;
        zzhh();
    }

    private void zzhf() {
        this.zzJh--;
        zzhh();
    }

    private void zzhg() {
        this.zzJg = true;
        zzhh();
    }

    public final void onLoadResource(WebView webView, String url) {
        com.google.android.gms.ads.internal.util.client.zzb.m1445v("Loading resource: " + url);
        Uri parse = Uri.parse(url);
        if ("gmsg".equalsIgnoreCase(parse.getScheme()) && "mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
            zzh(parse);
        }
    }

    public final void onPageFinished(WebView webView, String url) {
        synchronized (this.zzpc) {
            if (this.zzJe && "about:blank".equals(url)) {
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Blank page loaded, 1...");
                this.zzoL.zzgX();
                return;
            }
            this.zzJf = true;
            zzhh();
        }
    }

    public final void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        String valueOf = (errorCode >= 0 || (-errorCode) - 1 >= zzIU.length) ? String.valueOf(errorCode) : zzIU[(-errorCode) - 1];
        zza(this.zzoL.getContext(), "http_err", valueOf, failingUrl);
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    public final void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (error != null) {
            int primaryError = error.getPrimaryError();
            String valueOf = (primaryError < 0 || primaryError >= zzIV.length) ? String.valueOf(primaryError) : zzIV[primaryError];
            zza(this.zzoL.getContext(), "ssl_err", valueOf, zzp.zzbz().zza(error));
        }
        super.onReceivedSslError(view, handler, error);
    }

    public final void reset() {
        synchronized (this.zzpc) {
            this.zzIW.clear();
            this.zzsn = null;
            this.zzIX = null;
            this.zzCv = null;
            this.zzwH = null;
            this.zzIZ = false;
            this.zzqL = false;
            this.zzJa = false;
            this.zzxm = null;
            this.zzJb = null;
            this.zzIY = null;
            if (this.zzxk != null) {
                this.zzxk.zzn(true);
                this.zzxk = null;
            }
            this.zzJd = false;
        }
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        switch (event.getKeyCode()) {
            case C1090R.styleable.Theme_panelMenuListTheme /*79*/:
            case C1090R.styleable.Theme_colorControlActivated /*85*/:
            case C1090R.styleable.Theme_colorControlHighlight /*86*/:
            case C1090R.styleable.Theme_colorButtonNormal /*87*/:
            case C1090R.styleable.Theme_colorSwitchThumbNormal /*88*/:
            case C1090R.styleable.Theme_alertDialogStyle /*89*/:
            case C1090R.styleable.Theme_alertDialogButtonGroupStyle /*90*/:
            case C1090R.styleable.Theme_alertDialogCenterButtons /*91*/:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 222:
                return true;
            default:
                return false;
        }
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String url) {
        com.google.android.gms.ads.internal.util.client.zzb.m1445v("AdWebView shouldOverrideUrlLoading: " + url);
        Uri parse = Uri.parse(url);
        if ("gmsg".equalsIgnoreCase(parse.getScheme()) && "mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
            zzh(parse);
        } else if (this.zzIZ && webView == this.zzoL && zzg(parse)) {
            if (!this.zzJd) {
                this.zzJd = true;
                if (this.zzsn != null && ((Boolean) zzby.zzuO.get()).booleanValue()) {
                    this.zzsn.onAdClicked();
                }
            }
            return super.shouldOverrideUrlLoading(webView, url);
        } else if (this.zzoL.willNotDraw()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("AdWebView unable to handle URL: " + url);
        } else {
            Uri uri;
            try {
                zzan zzgU = this.zzoL.zzgU();
                if (zzgU != null && zzgU.zzb(parse)) {
                    parse = zzgU.zza(parse, this.zzoL.getContext());
                }
                uri = parse;
            } catch (zzao e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaE("Unable to append parameter to URL: " + url);
                uri = parse;
            }
            if (this.zzxj == null || this.zzxj.zzbe()) {
                zza(new AdLauncherIntentInfoParcel("android.intent.action.VIEW", uri.toString(), null, null, null, null, null));
            } else {
                this.zzxj.zzp(url);
            }
        }
        return true;
    }

    public void zzF(boolean z) {
        this.zzIZ = z;
    }

    public void zza(int i, int i2, boolean z) {
        this.zzJc.zze(i, i2);
        if (this.zzxk != null) {
            this.zzxk.zza(i, i2, z);
        }
    }

    public final void zza(AdLauncherIntentInfoParcel adLauncherIntentInfoParcel) {
        zzg com_google_android_gms_ads_internal_overlay_zzg = null;
        boolean zzgW = this.zzoL.zzgW();
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!zzgW || this.zzoL.zzaN().zzsH) ? this.zzsn : null;
        if (!zzgW) {
            com_google_android_gms_ads_internal_overlay_zzg = this.zzIX;
        }
        zza(new AdOverlayInfoParcel(adLauncherIntentInfoParcel, com_google_android_gms_ads_internal_client_zza, com_google_android_gms_ads_internal_overlay_zzg, this.zzJb, this.zzoL.zzgV()));
    }

    public void zza(AdOverlayInfoParcel adOverlayInfoParcel) {
        boolean z = false;
        boolean zzdY = this.zzxk != null ? this.zzxk.zzdY() : false;
        com.google.android.gms.ads.internal.overlay.zze zzbv = zzp.zzbv();
        Context context = this.zzoL.getContext();
        if (!zzdY) {
            z = true;
        }
        zzbv.zza(context, adOverlayInfoParcel, z);
    }

    public void zza(zza com_google_android_gms_internal_zziq_zza) {
        this.zzCv = com_google_android_gms_internal_zziq_zza;
    }

    public void zza(zzb com_google_android_gms_internal_zziq_zzb) {
        this.zzIY = com_google_android_gms_internal_zziq_zzb;
    }

    public final void zza(String str, zzdg com_google_android_gms_internal_zzdg) {
        synchronized (this.zzpc) {
            List list = (List) this.zzIW.get(str);
            if (list == null) {
                list = new CopyOnWriteArrayList();
                this.zzIW.put(str, list);
            }
            list.add(com_google_android_gms_internal_zzdg);
        }
    }

    public final void zza(boolean z, int i) {
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!this.zzoL.zzgW() || this.zzoL.zzaN().zzsH) ? this.zzsn : null;
        zza(new AdOverlayInfoParcel(com_google_android_gms_ads_internal_client_zza, this.zzIX, this.zzJb, this.zzoL, z, i, this.zzoL.zzgV()));
    }

    public final void zza(boolean z, int i, String str) {
        zzg com_google_android_gms_ads_internal_overlay_zzg = null;
        boolean zzgW = this.zzoL.zzgW();
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!zzgW || this.zzoL.zzaN().zzsH) ? this.zzsn : null;
        if (!zzgW) {
            com_google_android_gms_ads_internal_overlay_zzg = new zzc(this.zzoL, this.zzIX);
        }
        zza(new AdOverlayInfoParcel(com_google_android_gms_ads_internal_client_zza, com_google_android_gms_ads_internal_overlay_zzg, this.zzwH, this.zzJb, this.zzoL, z, i, str, this.zzoL.zzgV(), this.zzxm));
    }

    public final void zza(boolean z, int i, String str, String str2) {
        boolean zzgW = this.zzoL.zzgW();
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!zzgW || this.zzoL.zzaN().zzsH) ? this.zzsn : null;
        zza(new AdOverlayInfoParcel(com_google_android_gms_ads_internal_client_zza, zzgW ? null : new zzc(this.zzoL, this.zzIX), this.zzwH, this.zzJb, this.zzoL, z, i, str, str2, this.zzoL.zzgV(), this.zzxm));
    }

    public void zzb(com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzdd com_google_android_gms_internal_zzdd, zzn com_google_android_gms_ads_internal_overlay_zzn, boolean z, zzdi com_google_android_gms_internal_zzdi, zzdk com_google_android_gms_internal_zzdk, zze com_google_android_gms_ads_internal_zze, zzfc com_google_android_gms_internal_zzfc) {
        if (com_google_android_gms_ads_internal_zze == null) {
            com_google_android_gms_ads_internal_zze = new zze(false);
        }
        this.zzxk = new zzew(this.zzoL, com_google_android_gms_internal_zzfc);
        zza("/appEvent", new zzdc(com_google_android_gms_internal_zzdd));
        zza("/backButton", zzdf.zzwR);
        zza("/canOpenURLs", zzdf.zzwJ);
        zza("/canOpenIntents", zzdf.zzwK);
        zza("/click", zzdf.zzwL);
        zza("/close", zzdf.zzwM);
        zza("/customClose", zzdf.zzwN);
        zza("/delayPageLoaded", new zzd());
        zza("/httpTrack", zzdf.zzwO);
        zza("/log", zzdf.zzwP);
        zza("/mraid", new zzdm(com_google_android_gms_ads_internal_zze, this.zzxk));
        zza("/mraidLoaded", this.zzJc);
        zza("/open", new zzdn(com_google_android_gms_internal_zzdi, com_google_android_gms_ads_internal_zze, this.zzxk));
        zza("/precache", zzdf.zzwT);
        zza("/touch", zzdf.zzwQ);
        zza("/video", zzdf.zzwS);
        if (com_google_android_gms_internal_zzdk != null) {
            zza("/setInterstitialProperties", new zzdj(com_google_android_gms_internal_zzdk));
        }
        this.zzsn = com_google_android_gms_ads_internal_client_zza;
        this.zzIX = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzwH = com_google_android_gms_internal_zzdd;
        this.zzxm = com_google_android_gms_internal_zzdi;
        this.zzJb = com_google_android_gms_ads_internal_overlay_zzn;
        this.zzxj = com_google_android_gms_ads_internal_zze;
        this.zzzy = com_google_android_gms_internal_zzfc;
        this.zzxh = com_google_android_gms_internal_zzdk;
        zzF(z);
        this.zzJd = false;
    }

    public final void zzb(String str, zzdg com_google_android_gms_internal_zzdg) {
        synchronized (this.zzpc) {
            List list = (List) this.zzIW.get(str);
            if (list == null) {
                return;
            }
            list.remove(com_google_android_gms_internal_zzdg);
        }
    }

    public boolean zzbY() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzqL;
        }
        return z;
    }

    public void zzd(int i, int i2) {
        if (this.zzxk != null) {
            this.zzxk.zzd(i, i2);
        }
    }

    public final void zzeA() {
        synchronized (this.zzpc) {
            this.zzIZ = false;
            this.zzqL = true;
            zzhu.runOnUiThread(new C09591(this));
        }
    }

    public void zzh(Uri uri) {
        String path = uri.getPath();
        List<zzdg> list = (List) this.zzIW.get(path);
        if (list != null) {
            Map zze = zzp.zzbx().zze(uri);
            if (com.google.android.gms.ads.internal.util.client.zzb.zzM(2)) {
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Received GMSG: " + path);
                for (String path2 : zze.keySet()) {
                    com.google.android.gms.ads.internal.util.client.zzb.m1445v("  " + path2 + ": " + ((String) zze.get(path2)));
                }
            }
            for (zzdg zza : list) {
                zza.zza(this.zzoL, zze);
            }
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.m1445v("No GMSG handler found for GMSG: " + uri);
    }

    public zze zzhb() {
        return this.zzxj;
    }

    public boolean zzhc() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzJa;
        }
        return z;
    }

    public void zzhd() {
        synchronized (this.zzpc) {
            com.google.android.gms.ads.internal.util.client.zzb.m1445v("Loading blank page in WebView, 2...");
            this.zzJe = true;
            this.zzoL.zzaF("about:blank");
        }
    }

    public final void zzhh() {
        if (this.zzCv == null) {
            return;
        }
        if ((this.zzJf && this.zzJh <= 0) || this.zzJg) {
            this.zzCv.zza(this.zzoL, !this.zzJg);
            this.zzCv = null;
        }
    }
}
