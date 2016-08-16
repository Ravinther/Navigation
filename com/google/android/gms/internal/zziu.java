package com.google.android.gms.internal;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import loquendo.tts.engine.TTSConst;

@zzgk
public class zziu extends WebChromeClient {
    private final zzip zzoL;

    /* renamed from: com.google.android.gms.internal.zziu.1 */
    static class C09611 implements OnCancelListener {
        final /* synthetic */ JsResult zzJx;

        C09611(JsResult jsResult) {
            this.zzJx = jsResult;
        }

        public void onCancel(DialogInterface dialog) {
            this.zzJx.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zziu.2 */
    static class C09622 implements OnClickListener {
        final /* synthetic */ JsResult zzJx;

        C09622(JsResult jsResult) {
            this.zzJx = jsResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzJx.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zziu.3 */
    static class C09633 implements OnClickListener {
        final /* synthetic */ JsResult zzJx;

        C09633(JsResult jsResult) {
            this.zzJx = jsResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzJx.confirm();
        }
    }

    /* renamed from: com.google.android.gms.internal.zziu.4 */
    static class C09644 implements OnCancelListener {
        final /* synthetic */ JsPromptResult zzJy;

        C09644(JsPromptResult jsPromptResult) {
            this.zzJy = jsPromptResult;
        }

        public void onCancel(DialogInterface dialog) {
            this.zzJy.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zziu.5 */
    static class C09655 implements OnClickListener {
        final /* synthetic */ JsPromptResult zzJy;

        C09655(JsPromptResult jsPromptResult) {
            this.zzJy = jsPromptResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzJy.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zziu.6 */
    static class C09666 implements OnClickListener {
        final /* synthetic */ JsPromptResult zzJy;
        final /* synthetic */ EditText zzJz;

        C09666(JsPromptResult jsPromptResult, EditText editText) {
            this.zzJy = jsPromptResult;
            this.zzJz = editText;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzJy.confirm(this.zzJz.getText().toString());
        }
    }

    /* renamed from: com.google.android.gms.internal.zziu.7 */
    static /* synthetic */ class C09677 {
        static final /* synthetic */ int[] zzJA;

        static {
            zzJA = new int[MessageLevel.values().length];
            try {
                zzJA[MessageLevel.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                zzJA[MessageLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                zzJA[MessageLevel.LOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                zzJA[MessageLevel.TIP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                zzJA[MessageLevel.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public zziu(zzip com_google_android_gms_internal_zzip) {
        this.zzoL = com_google_android_gms_internal_zzip;
    }

    private static void zza(Builder builder, String str, JsResult jsResult) {
        builder.setMessage(str).setPositiveButton(17039370, new C09633(jsResult)).setNegativeButton(17039360, new C09622(jsResult)).setOnCancelListener(new C09611(jsResult)).create().show();
    }

    private static void zza(Context context, Builder builder, String str, String str2, JsPromptResult jsPromptResult) {
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        View textView = new TextView(context);
        textView.setText(str);
        View editText = new EditText(context);
        editText.setText(str2);
        linearLayout.addView(textView);
        linearLayout.addView(editText);
        builder.setView(linearLayout).setPositiveButton(17039370, new C09666(jsPromptResult, editText)).setNegativeButton(17039360, new C09655(jsPromptResult)).setOnCancelListener(new C09644(jsPromptResult)).create().show();
    }

    private final Context zzc(WebView webView) {
        if (!(webView instanceof zzip)) {
            return webView.getContext();
        }
        zzip com_google_android_gms_internal_zzip = (zzip) webView;
        Context zzgN = com_google_android_gms_internal_zzip.zzgN();
        return zzgN == null ? com_google_android_gms_internal_zzip.getContext() : zzgN;
    }

    private final boolean zzho() {
        return zzp.zzbx().zza(this.zzoL.getContext().getPackageManager(), this.zzoL.getContext().getPackageName(), "android.permission.ACCESS_FINE_LOCATION") || zzp.zzbx().zza(this.zzoL.getContext().getPackageManager(), this.zzoL.getContext().getPackageName(), "android.permission.ACCESS_COARSE_LOCATION");
    }

    public final void onCloseWindow(WebView webView) {
        if (webView instanceof zzip) {
            zzd zzgQ = ((zzip) webView).zzgQ();
            if (zzgQ == null) {
                zzb.zzaE("Tried to close an AdWebView not associated with an overlay.");
                return;
            } else {
                zzgQ.close();
                return;
            }
        }
        zzb.zzaE("Tried to close a WebView that wasn't an AdWebView.");
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String str = "JS: " + consoleMessage.message() + " (" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")";
        if (str.contains("Application Cache")) {
            return super.onConsoleMessage(consoleMessage);
        }
        switch (C09677.zzJA[consoleMessage.messageLevel().ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                zzb.m1444e(str);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                zzb.zzaE(str);
                break;
            case TTSConst.TTSUNICODE /*3*/:
            case TTSConst.TTSXML /*4*/:
                zzb.zzaD(str);
                break;
            case TTSConst.TTSEVT_TEXT /*5*/:
                zzb.zzaC(str);
                break;
            default:
                zzb.zzaD(str);
                break;
        }
        return super.onConsoleMessage(consoleMessage);
    }

    public final boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebViewTransport webViewTransport = (WebViewTransport) resultMsg.obj;
        WebView webView = new WebView(view.getContext());
        webView.setWebViewClient(this.zzoL.zzgS());
        webViewTransport.setWebView(webView);
        resultMsg.sendToTarget();
        return true;
    }

    public final void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, QuotaUpdater quotaUpdater) {
        long j = 5242880 - totalUsedQuota;
        if (j <= 0) {
            quotaUpdater.updateQuota(currentQuota);
            return;
        }
        if (currentQuota == 0) {
            if (estimatedSize > j || estimatedSize > 1048576) {
                estimatedSize = 0;
            }
        } else if (estimatedSize == 0) {
            estimatedSize = Math.min(Math.min(131072, j) + currentQuota, 1048576);
        } else {
            if (estimatedSize <= Math.min(1048576 - currentQuota, j)) {
                currentQuota += estimatedSize;
            }
            estimatedSize = currentQuota;
        }
        quotaUpdater.updateQuota(estimatedSize);
    }

    public final void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
        if (callback != null) {
            callback.invoke(origin, zzho(), true);
        }
    }

    public final void onHideCustomView() {
        zzd zzgQ = this.zzoL.zzgQ();
        if (zzgQ == null) {
            zzb.zzaE("Could not get ad overlay when hiding custom view.");
        } else {
            zzgQ.zzex();
        }
    }

    public final boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    public final boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    public final boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    public final boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        return zza(zzc(webView), url, message, defaultValue, null, result, true);
    }

    public final void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, QuotaUpdater quotaUpdater) {
        long j = 131072 + spaceNeeded;
        if (5242880 - totalUsedQuota < j) {
            quotaUpdater.updateQuota(0);
        } else {
            quotaUpdater.updateQuota(j);
        }
    }

    public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        zza(view, -1, customViewCallback);
    }

    protected final void zza(View view, int i, CustomViewCallback customViewCallback) {
        zzd zzgQ = this.zzoL.zzgQ();
        if (zzgQ == null) {
            zzb.zzaE("Could not get ad overlay when showing custom view.");
            customViewCallback.onCustomViewHidden();
            return;
        }
        zzgQ.zza(view, customViewCallback);
        zzgQ.setRequestedOrientation(i);
    }

    protected boolean zza(Context context, String str, String str2, String str3, JsResult jsResult, JsPromptResult jsPromptResult, boolean z) {
        try {
            Builder builder = new Builder(context);
            builder.setTitle(str);
            if (z) {
                zza(context, builder, str2, str3, jsPromptResult);
            } else {
                zza(builder, str2, jsResult);
            }
        } catch (Throwable e) {
            zzb.zzd("Fail to display Dialog.", e);
        }
        return true;
    }
}
