package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzis extends WebView implements OnGlobalLayoutListener, DownloadListener, zzip {
    private String zzBm;
    private final zziq zzCq;
    private Boolean zzHi;
    private final zza zzJk;
    private zzd zzJl;
    private boolean zzJm;
    private boolean zzJn;
    private boolean zzJo;
    private boolean zzJp;
    private int zzJq;
    private boolean zzJr;
    private String zzJs;
    private zzd zzJt;
    private Map<String, zzdr> zzJu;
    private final com.google.android.gms.ads.internal.zzd zzov;
    private final VersionInfoParcel zzpa;
    private final Object zzpc;
    private zzid zzqG;
    private final WindowManager zzqX;
    private final zzan zzwh;
    private AdSizeParcel zzyK;
    private int zzzQ;
    private int zzzR;
    private int zzzT;
    private int zzzU;

    /* renamed from: com.google.android.gms.internal.zzis.1 */
    class C09601 implements Runnable {
        final /* synthetic */ zzis zzJv;

        C09601(zzis com_google_android_gms_internal_zzis) {
            this.zzJv = com_google_android_gms_internal_zzis;
        }

        public void run() {
            super.destroy();
        }
    }

    @zzgk
    public static class zza extends MutableContextWrapper {
        private Activity zzIs;
        private Context zzJw;
        private Context zzqO;

        public zza(Context context) {
            super(context);
            setBaseContext(context);
        }

        public Object getSystemService(String service) {
            return this.zzJw.getSystemService(service);
        }

        public void setBaseContext(Context base) {
            this.zzqO = base.getApplicationContext();
            this.zzIs = base instanceof Activity ? (Activity) base : null;
            this.zzJw = base;
            super.setBaseContext(this.zzqO);
        }

        public void startActivity(Intent intent) {
            if (this.zzIs == null || zzlv.isAtLeastL()) {
                intent.setFlags(268435456);
                this.zzqO.startActivity(intent);
                return;
            }
            this.zzIs.startActivity(intent);
        }

        public Activity zzgN() {
            return this.zzIs;
        }

        public Context zzgO() {
            return this.zzJw;
        }
    }

    protected zzis(zza com_google_android_gms_internal_zzis_zza, AdSizeParcel adSizeParcel, boolean z, boolean z2, zzan com_google_android_gms_internal_zzan, VersionInfoParcel versionInfoParcel, com.google.android.gms.ads.internal.zzd com_google_android_gms_ads_internal_zzd) {
        super(com_google_android_gms_internal_zzis_zza);
        this.zzpc = new Object();
        this.zzJr = true;
        this.zzBm = "";
        this.zzzR = -1;
        this.zzzQ = -1;
        this.zzzT = -1;
        this.zzzU = -1;
        this.zzJk = com_google_android_gms_internal_zzis_zza;
        this.zzyK = adSizeParcel;
        this.zzJo = z;
        this.zzJq = -1;
        this.zzwh = com_google_android_gms_internal_zzan;
        this.zzpa = versionInfoParcel;
        this.zzov = com_google_android_gms_ads_internal_zzd;
        this.zzqX = (WindowManager) getContext().getSystemService("window");
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(0);
        }
        zzp.zzbx().zza((Context) com_google_android_gms_internal_zzis_zza, versionInfoParcel.zzIz, settings);
        zzp.zzbz().zza(getContext(), settings);
        setDownloadListener(this);
        this.zzCq = zzp.zzbz().zzb((zzip) this, z2);
        setWebViewClient(this.zzCq);
        setWebChromeClient(zzp.zzbz().zzf((zzip) this));
        zzhk();
        if (zzlv.zzpT()) {
            addJavascriptInterface(new zzit(this), "googleAdsJsInterface");
        }
        this.zzqG = new zzid(this.zzJk.zzgN(), this, null);
    }

    static zzis zzb(Context context, AdSizeParcel adSizeParcel, boolean z, boolean z2, zzan com_google_android_gms_internal_zzan, VersionInfoParcel versionInfoParcel, com.google.android.gms.ads.internal.zzd com_google_android_gms_ads_internal_zzd) {
        return new zzis(new zza(context), adSizeParcel, z, z2, com_google_android_gms_internal_zzan, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
    }

    private void zzhj() {
        synchronized (this.zzpc) {
            this.zzHi = zzp.zzbA().zzgg();
            if (this.zzHi == null) {
                try {
                    evaluateJavascript("(function(){})()", null);
                    zzb(Boolean.valueOf(true));
                } catch (IllegalStateException e) {
                    zzb(Boolean.valueOf(false));
                }
            }
        }
    }

    private void zzhk() {
        synchronized (this.zzpc) {
            if (this.zzJo || this.zzyK.zzsH) {
                if (VERSION.SDK_INT < 14) {
                    zzb.zzaC("Disabling hardware acceleration on an overlay.");
                    zzhl();
                } else {
                    zzb.zzaC("Enabling hardware acceleration on an overlay.");
                    zzhm();
                }
            } else if (VERSION.SDK_INT < 18) {
                zzb.zzaC("Disabling hardware acceleration on an AdView.");
                zzhl();
            } else {
                zzb.zzaC("Enabling hardware acceleration on an AdView.");
                zzhm();
            }
        }
    }

    private void zzhl() {
        synchronized (this.zzpc) {
            if (!this.zzJp) {
                zzp.zzbz().zzl(this);
            }
            this.zzJp = true;
        }
    }

    private void zzhm() {
        synchronized (this.zzpc) {
            if (this.zzJp) {
                zzp.zzbz().zzk(this);
            }
            this.zzJp = false;
        }
    }

    private void zzhn() {
        synchronized (this.zzpc) {
            if (this.zzJu != null) {
                for (zzdr release : this.zzJu.values()) {
                    release.release();
                }
            }
        }
    }

    public void destroy() {
        synchronized (this.zzpc) {
            this.zzqG.zzgE();
            if (this.zzJl != null) {
                this.zzJl.close();
                this.zzJl.onDestroy();
                this.zzJl = null;
            }
            this.zzCq.reset();
            if (this.zzJn) {
                return;
            }
            zzp.zzbK().zza((zzip) this);
            zzhn();
            this.zzJn = true;
            zzb.m1445v("Initiating WebView self destruct sequence in 3...");
            this.zzCq.zzhd();
        }
    }

    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        synchronized (this.zzpc) {
            if (isDestroyed()) {
                zzb.zzaE("The webview is destroyed. Ignoring action.");
                if (resultCallback != null) {
                    resultCallback.onReceiveValue(null);
                }
                return;
            }
            super.evaluateJavascript(script, resultCallback);
        }
    }

    public String getRequestId() {
        String str;
        synchronized (this.zzpc) {
            str = this.zzBm;
        }
        return str;
    }

    public int getRequestedOrientation() {
        int i;
        synchronized (this.zzpc) {
            i = this.zzJq;
        }
        return i;
    }

    public WebView getWebView() {
        return this;
    }

    public boolean isDestroyed() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzJn;
        }
        return z;
    }

    public void loadData(String data, String mimeType, String encoding) {
        synchronized (this.zzpc) {
            if (isDestroyed()) {
                zzb.zzaE("The webview is destroyed. Ignoring action.");
            } else {
                super.loadData(data, mimeType, encoding);
            }
        }
    }

    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        synchronized (this.zzpc) {
            if (isDestroyed()) {
                zzb.zzaE("The webview is destroyed. Ignoring action.");
            } else {
                super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
            }
        }
    }

    public void loadUrl(String uri) {
        synchronized (this.zzpc) {
            if (isDestroyed()) {
                zzb.zzaE("The webview is destroyed. Ignoring action.");
            } else {
                super.loadUrl(uri);
            }
        }
    }

    protected void onAttachedToWindow() {
        synchronized (this.zzpc) {
            super.onAttachedToWindow();
            if (!isDestroyed()) {
                this.zzqG.onAttachedToWindow();
            }
        }
    }

    protected void onDetachedFromWindow() {
        synchronized (this.zzpc) {
            if (!isDestroyed()) {
                this.zzqG.onDetachedFromWindow();
            }
            super.onDetachedFromWindow();
        }
    }

    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long size) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), mimeType);
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            zzb.zzaC("Couldn't find an Activity to view url/mimetype: " + url + " / " + mimeType);
        }
    }

    protected void onDraw(Canvas canvas) {
        if (!isDestroyed()) {
            if (VERSION.SDK_INT != 21 || !canvas.isHardwareAccelerated() || isAttachedToWindow()) {
                super.onDraw(canvas);
            }
        }
    }

    public void onGlobalLayout() {
        boolean zzhi = zzhi();
        zzd zzgQ = zzgQ();
        if (zzgQ != null && zzhi) {
            zzgQ.zzeC();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = Integer.MAX_VALUE;
        synchronized (this.zzpc) {
            if (isDestroyed()) {
                setMeasuredDimension(0, 0);
            } else if (isInEditMode() || this.zzJo || this.zzyK.zzsJ) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else if (this.zzyK.zzsH) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                this.zzqX.getDefaultDisplay().getMetrics(displayMetrics);
                setMeasuredDimension(displayMetrics.widthPixels, displayMetrics.heightPixels);
            } else {
                int mode = MeasureSpec.getMode(widthMeasureSpec);
                int size = MeasureSpec.getSize(widthMeasureSpec);
                int mode2 = MeasureSpec.getMode(heightMeasureSpec);
                int size2 = MeasureSpec.getSize(heightMeasureSpec);
                mode = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
                if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
                    i = size2;
                }
                if (this.zzyK.widthPixels > mode || this.zzyK.heightPixels > r0) {
                    float f = this.zzJk.getResources().getDisplayMetrics().density;
                    zzb.zzaE("Not enough space to show ad. Needs " + ((int) (((float) this.zzyK.widthPixels) / f)) + "x" + ((int) (((float) this.zzyK.heightPixels) / f)) + " dp, but only has " + ((int) (((float) size) / f)) + "x" + ((int) (((float) size2) / f)) + " dp.");
                    if (getVisibility() != 8) {
                        setVisibility(4);
                    }
                    setMeasuredDimension(0, 0);
                } else {
                    if (getVisibility() != 8) {
                        setVisibility(0);
                    }
                    setMeasuredDimension(this.zzyK.widthPixels, this.zzyK.heightPixels);
                }
            }
        }
    }

    public void onPause() {
        if (!isDestroyed()) {
            try {
                super.onPause();
            } catch (Throwable e) {
                zzb.zzb("Could not pause webview.", e);
            }
        }
    }

    public void onResume() {
        if (!isDestroyed()) {
            try {
                super.onResume();
            } catch (Throwable e) {
                zzb.zzb("Could not resume webview.", e);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.zzwh != null) {
            this.zzwh.zza(event);
        }
        return isDestroyed() ? false : super.onTouchEvent(event);
    }

    public void setContext(Context context) {
        this.zzJk.setBaseContext(context);
        this.zzqG.zzl(this.zzJk.zzgN());
    }

    public void setRequestedOrientation(int requestedOrientation) {
        synchronized (this.zzpc) {
            this.zzJq = requestedOrientation;
            if (this.zzJl != null) {
                this.zzJl.setRequestedOrientation(this.zzJq);
            }
        }
    }

    public void stopLoading() {
        if (!isDestroyed()) {
            try {
                super.stopLoading();
            } catch (Throwable e) {
                zzb.zzb("Could not stop loading webview.", e);
            }
        }
    }

    public void zzC(boolean z) {
        synchronized (this.zzpc) {
            this.zzJo = z;
            zzhk();
        }
    }

    public void zzD(boolean z) {
        synchronized (this.zzpc) {
            if (this.zzJl != null) {
                this.zzJl.zza(this.zzCq.zzbY(), z);
            } else {
                this.zzJm = z;
            }
        }
    }

    public void zzE(boolean z) {
        synchronized (this.zzpc) {
            this.zzJr = z;
        }
    }

    public void zza(Context context, AdSizeParcel adSizeParcel) {
        synchronized (this.zzpc) {
            this.zzqG.zzgE();
            setContext(context);
            this.zzJl = null;
            this.zzyK = adSizeParcel;
            this.zzJo = false;
            this.zzJm = false;
            this.zzBm = "";
            this.zzJq = -1;
            zzp.zzbz().zzb(this);
            loadUrl("about:blank");
            this.zzCq.reset();
            setOnTouchListener(null);
            setOnClickListener(null);
            this.zzJr = true;
        }
    }

    public void zza(AdSizeParcel adSizeParcel) {
        synchronized (this.zzpc) {
            this.zzyK = adSizeParcel;
            requestLayout();
        }
    }

    public void zza(zzd com_google_android_gms_ads_internal_overlay_zzd) {
        synchronized (this.zzpc) {
            this.zzJl = com_google_android_gms_ads_internal_overlay_zzd;
        }
    }

    public void zza(zzaz com_google_android_gms_internal_zzaz, boolean z) {
        Map hashMap = new HashMap();
        hashMap.put("isVisible", z ? "1" : "0");
        zzc("onAdVisibilityChanged", hashMap);
    }

    protected void zza(String str, ValueCallback<String> valueCallback) {
        synchronized (this.zzpc) {
            if (isDestroyed()) {
                zzb.zzaE("The webview is destroyed. Ignoring action.");
                if (valueCallback != null) {
                    valueCallback.onReceiveValue(null);
                }
            } else {
                evaluateJavascript(str, valueCallback);
            }
        }
    }

    public void zza(String str, String str2) {
        zzaK(str + "(" + str2 + ");");
    }

    public void zza(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        zza(str, jSONObject.toString());
    }

    public void zzaF(String str) {
        synchronized (this.zzpc) {
            super.loadUrl(str);
        }
    }

    public void zzaG(String str) {
        synchronized (this.zzpc) {
            if (str == null) {
                str = "";
            }
            this.zzBm = str;
        }
    }

    public void zzaH(String str) {
        synchronized (this.zzpc) {
            this.zzJs = str;
        }
    }

    protected void zzaJ(String str) {
        synchronized (this.zzpc) {
            if (isDestroyed()) {
                zzb.zzaE("The webview is destroyed. Ignoring action.");
            } else {
                loadUrl(str);
            }
        }
    }

    protected void zzaK(String str) {
        if (zzlv.zzpV()) {
            if (zzgg() == null) {
                zzhj();
            }
            if (zzgg().booleanValue()) {
                zza(str, null);
                return;
            } else {
                zzaJ("javascript:" + str);
                return;
            }
        }
        zzaJ("javascript:" + str);
    }

    public AdSizeParcel zzaN() {
        AdSizeParcel adSizeParcel;
        synchronized (this.zzpc) {
            adSizeParcel = this.zzyK;
        }
        return adSizeParcel;
    }

    public void zzb(zzd com_google_android_gms_ads_internal_overlay_zzd) {
        synchronized (this.zzpc) {
            this.zzJt = com_google_android_gms_ads_internal_overlay_zzd;
        }
    }

    void zzb(Boolean bool) {
        this.zzHi = bool;
        zzp.zzbA().zzb(bool);
    }

    public void zzb(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String jSONObject2 = jSONObject.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AFMA_ReceiveMessage('");
        stringBuilder.append(str);
        stringBuilder.append("'");
        stringBuilder.append(",");
        stringBuilder.append(jSONObject2);
        stringBuilder.append(");");
        zzb.m1445v("Dispatching AFMA event: " + stringBuilder.toString());
        zzaK(stringBuilder.toString());
    }

    public void zzc(String str, Map<String, ?> map) {
        try {
            zzb(str, zzp.zzbx().zzx(map));
        } catch (JSONException e) {
            zzb.zzaE("Could not convert parameters to JSON.");
        }
    }

    public void zzeD() {
        Map hashMap = new HashMap(1);
        hashMap.put("version", this.zzpa.zzIz);
        zzc("onshow", hashMap);
    }

    public void zzgM() {
        Map hashMap = new HashMap(1);
        hashMap.put("version", this.zzpa.zzIz);
        zzc("onhide", hashMap);
    }

    public Activity zzgN() {
        return this.zzJk.zzgN();
    }

    public Context zzgO() {
        return this.zzJk.zzgO();
    }

    public com.google.android.gms.ads.internal.zzd zzgP() {
        return this.zzov;
    }

    public zzd zzgQ() {
        zzd com_google_android_gms_ads_internal_overlay_zzd;
        synchronized (this.zzpc) {
            com_google_android_gms_ads_internal_overlay_zzd = this.zzJl;
        }
        return com_google_android_gms_ads_internal_overlay_zzd;
    }

    public zzd zzgR() {
        zzd com_google_android_gms_ads_internal_overlay_zzd;
        synchronized (this.zzpc) {
            com_google_android_gms_ads_internal_overlay_zzd = this.zzJt;
        }
        return com_google_android_gms_ads_internal_overlay_zzd;
    }

    public zziq zzgS() {
        return this.zzCq;
    }

    public boolean zzgT() {
        return this.zzJm;
    }

    public zzan zzgU() {
        return this.zzwh;
    }

    public VersionInfoParcel zzgV() {
        return this.zzpa;
    }

    public boolean zzgW() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzJo;
        }
        return z;
    }

    public void zzgX() {
        synchronized (this.zzpc) {
            zzb.m1445v("Destroying WebView!");
            zzhu.zzHK.post(new C09601(this));
        }
    }

    public boolean zzgY() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzJr;
        }
        return z;
    }

    public void zzgZ() {
        this.zzqG.zzgD();
    }

    Boolean zzgg() {
        Boolean bool;
        synchronized (this.zzpc) {
            bool = this.zzHi;
        }
        return bool;
    }

    public String zzha() {
        String str;
        synchronized (this.zzpc) {
            str = this.zzJs;
        }
        return str;
    }

    public boolean zzhi() {
        if (!zzgS().zzbY()) {
            return false;
        }
        int i;
        int i2;
        DisplayMetrics zza = zzp.zzbx().zza(this.zzqX);
        int zzb = zzk.zzcE().zzb(zza, zza.widthPixels);
        int zzb2 = zzk.zzcE().zzb(zza, zza.heightPixels);
        Activity zzgN = zzgN();
        if (zzgN == null || zzgN.getWindow() == null) {
            i = zzb2;
            i2 = zzb;
        } else {
            int[] zzg = zzp.zzbx().zzg(zzgN);
            i2 = zzk.zzcE().zzb(zza, zzg[0]);
            i = zzk.zzcE().zzb(zza, zzg[1]);
        }
        if (this.zzzQ == zzb && this.zzzR == zzb2 && this.zzzT == i2 && this.zzzU == i) {
            return false;
        }
        boolean z = (this.zzzQ == zzb && this.zzzR == zzb2) ? false : true;
        this.zzzQ = zzb;
        this.zzzR = zzb2;
        this.zzzT = i2;
        this.zzzU = i;
        new zzfb(this).zza(zzb, zzb2, i2, i, zza.density, this.zzqX.getDefaultDisplay().getRotation());
        return z;
    }

    public void zzv(int i) {
        Map hashMap = new HashMap(2);
        hashMap.put("closetype", String.valueOf(i));
        hashMap.put("version", this.zzpa.zzIz);
        zzc("onhide", hashMap);
    }
}
