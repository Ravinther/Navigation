package com.google.android.gms.internal;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.PopupWindow;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzm;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.ads.search.SearchAdView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import loquendo.tts.engine.TTSConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzhu {
    private static final String zzHE;
    private static final String zzHF;
    private static final String zzHG;
    private static final String zzHH;
    private static final String zzHI;
    private static final String zzHJ;
    public static final Handler zzHK;
    private boolean zzHL;
    private boolean zzHM;
    private String zzHj;
    private final Object zzpc;

    /* renamed from: com.google.android.gms.internal.zzhu.1 */
    class C09491 implements Runnable {
        final /* synthetic */ zzhu zzHN;
        final /* synthetic */ Context zzrn;

        C09491(zzhu com_google_android_gms_internal_zzhu, Context context) {
            this.zzHN = com_google_android_gms_internal_zzhu;
            this.zzrn = context;
        }

        public void run() {
            synchronized (this.zzHN.zzpc) {
                this.zzHN.zzHj = this.zzHN.zzK(this.zzrn);
                this.zzHN.zzpc.notifyAll();
            }
        }
    }

    private final class zza extends BroadcastReceiver {
        final /* synthetic */ zzhu zzHN;

        private zza(zzhu com_google_android_gms_internal_zzhu) {
            this.zzHN = com_google_android_gms_internal_zzhu;
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                this.zzHN.zzHL = true;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                this.zzHN.zzHL = false;
            }
        }
    }

    static {
        zzHE = AdView.class.getName();
        zzHF = InterstitialAd.class.getName();
        zzHG = PublisherAdView.class.getName();
        zzHH = PublisherInterstitialAd.class.getName();
        zzHI = SearchAdView.class.getName();
        zzHJ = AdLoader.class.getName();
        zzHK = new zzhr(Looper.getMainLooper());
    }

    public zzhu() {
        this.zzpc = new Object();
        this.zzHL = true;
        this.zzHM = false;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            zzHK.post(runnable);
        }
    }

    private JSONArray zza(Collection<?> collection) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object zza : collection) {
            zza(jSONArray, zza);
        }
        return jSONArray;
    }

    private void zza(JSONArray jSONArray, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONArray.put(zze((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONArray.put(zzx((Map) obj));
        } else if (obj instanceof Collection) {
            jSONArray.put(zza((Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONArray.put(zza((Object[]) obj));
        } else {
            jSONArray.put(obj);
        }
    }

    private void zza(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONObject.put(str, zze((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONObject.put(str, zzx((Map) obj));
        } else if (obj instanceof Collection) {
            if (str == null) {
                str = "null";
            }
            jSONObject.put(str, zza((Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONObject.put(str, zza(Arrays.asList((Object[]) obj)));
        } else {
            jSONObject.put(str, obj);
        }
    }

    public static void zzb(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            runnable.run();
        } else {
            zzht.zza(runnable);
        }
    }

    private JSONObject zze(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            zza(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    private boolean zzr(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        return powerManager == null ? false : powerManager.isScreenOn();
    }

    public boolean zzI(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context, "com.google.android.gms.ads.AdActivity");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            zzb.zzaE("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
            return false;
        }
        boolean z;
        String str = "com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".";
        if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
            zzb.zzaE(String.format(str, new Object[]{"keyboard"}));
            z = false;
        } else {
            z = true;
        }
        if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
            zzb.zzaE(String.format(str, new Object[]{"keyboardHidden"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 128) == 0) {
            zzb.zzaE(String.format(str, new Object[]{"orientation"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 256) == 0) {
            zzb.zzaE(String.format(str, new Object[]{"screenLayout"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 512) == 0) {
            zzb.zzaE(String.format(str, new Object[]{"uiMode"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 1024) == 0) {
            zzb.zzaE(String.format(str, new Object[]{"screenSize"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 2048) != 0) {
            return z;
        }
        zzb.zzaE(String.format(str, new Object[]{"smallestScreenSize"}));
        return false;
    }

    public boolean zzJ(Context context) {
        if (this.zzHM) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.getApplicationContext().registerReceiver(new zza(), intentFilter);
        this.zzHM = true;
        return true;
    }

    protected String zzK(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }

    public Builder zzL(Context context) {
        return new Builder(context);
    }

    public zzbq zzM(Context context) {
        return new zzbq(context);
    }

    public String zzN(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return null;
            }
            List runningTasks = activityManager.getRunningTasks(1);
            if (!(runningTasks == null || runningTasks.isEmpty())) {
                RunningTaskInfo runningTaskInfo = (RunningTaskInfo) runningTasks.get(0);
                if (!(runningTaskInfo == null || runningTaskInfo.topActivity == null)) {
                    return runningTaskInfo.topActivity.getClassName();
                }
            }
            return null;
        } catch (Exception e) {
        }
    }

    public boolean zzO(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager == null || keyguardManager == null) {
                return false;
            }
            List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (Process.myPid() == runningAppProcessInfo.pid) {
                    if (runningAppProcessInfo.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode() && zzr(context)) {
                        return true;
                    }
                    return false;
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public DisplayMetrics zza(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public PopupWindow zza(View view, int i, int i2, boolean z) {
        return new PopupWindow(view, i, i2, z);
    }

    public String zza(Context context, View view, AdSizeParcel adSizeParcel) {
        String str = null;
        if (((Boolean) zzby.zzuR.get()).booleanValue()) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("width", adSizeParcel.width);
                jSONObject2.put("height", adSizeParcel.height);
                jSONObject.put("size", jSONObject2);
                jSONObject.put("activity", zzN(context));
                if (!adSizeParcel.zzsH) {
                    JSONArray jSONArray = new JSONArray();
                    while (view != null) {
                        ViewParent parent = view.getParent();
                        if (parent != null) {
                            int i = -1;
                            if (parent instanceof ViewGroup) {
                                i = ((ViewGroup) parent).indexOfChild(view);
                            }
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("type", parent.getClass().getName());
                            jSONObject3.put("index_of_child", i);
                            jSONArray.put(jSONObject3);
                        }
                        View view2 = (parent == null || !(parent instanceof View)) ? null : (View) parent;
                        view = view2;
                    }
                    if (jSONArray.length() > 0) {
                        jSONObject.put("parents", jSONArray);
                    }
                }
                str = jSONObject.toString();
            } catch (Throwable e) {
                zzb.zzd("Fail to get view hierarchy json", e);
            }
        }
        return str;
    }

    public String zza(Context context, zzan com_google_android_gms_internal_zzan, String str) {
        if (com_google_android_gms_internal_zzan != null) {
            try {
                Uri parse = Uri.parse(str);
                if (com_google_android_gms_internal_zzan.zzc(parse)) {
                    parse = com_google_android_gms_internal_zzan.zza(parse, context);
                }
                str = parse.toString();
            } catch (Exception e) {
            }
        }
        return str;
    }

    public String zza(Context context, String str, String str2, int i) {
        if (!((Boolean) zzby.zzuQ.get()).booleanValue() || !zzm.zzq(context).zzbn() || TextUtils.isEmpty(str) || !zzb(Uri.parse(str))) {
            return str;
        }
        Map hashMap = new HashMap();
        hashMap.put("ga_cid", str2);
        hashMap.put("ga_hid", String.valueOf(i));
        return zza(str, hashMap);
    }

    public String zza(zzip com_google_android_gms_internal_zzip, String str) {
        return zza(com_google_android_gms_internal_zzip.getContext(), com_google_android_gms_internal_zzip.zzgU(), str);
    }

    public String zza(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(8192);
        char[] cArr = new char[2048];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read == -1) {
                return stringBuilder.toString();
            }
            stringBuilder.append(cArr, 0, read);
        }
    }

    String zza(String str, Map<String, String> map) {
        int indexOf = str.indexOf("&adurl");
        int indexOf2 = indexOf == -1 ? str.indexOf("?adurl") : indexOf;
        Uri parse = Uri.parse(str);
        if (!zzc(parse) || indexOf2 == -1) {
            Uri.Builder buildUpon = parse.buildUpon();
            Uri.Builder builder = buildUpon;
            for (String str2 : map.keySet()) {
                builder = builder.appendQueryParameter(str2, (String) map.get(str2));
            }
            return builder.build().toString();
        }
        StringBuilder stringBuilder = new StringBuilder(str.substring(0, indexOf2 + 1));
        for (String str22 : map.keySet()) {
            stringBuilder.append(str22).append("=").append((String) map.get(str22)).append("&");
        }
        return stringBuilder.append(str.substring(indexOf2 + 1)).toString();
    }

    public String zza(StackTraceElement[] stackTraceElementArr, String str) {
        if (((Boolean) zzby.zzvc.get()).booleanValue()) {
            String className;
            for (int i = 0; i + 1 < stackTraceElementArr.length; i++) {
                StackTraceElement stackTraceElement = stackTraceElementArr[i];
                String className2 = stackTraceElement.getClassName();
                if ("loadAd".equalsIgnoreCase(stackTraceElement.getMethodName()) && (zzHE.equalsIgnoreCase(className2) || zzHF.equalsIgnoreCase(className2) || zzHG.equalsIgnoreCase(className2) || zzHH.equalsIgnoreCase(className2) || zzHI.equalsIgnoreCase(className2) || zzHJ.equalsIgnoreCase(className2))) {
                    className = stackTraceElementArr[i + 1].getClassName();
                    break;
                }
            }
            className = null;
            if (!(className == null || className.contains(str))) {
                return className;
            }
        }
        return null;
    }

    public ArrayList<String> zza(Context context, List<String> list, String str) {
        int zzbp = zzm.zzq(context).zzbp();
        ArrayList<String> arrayList = new ArrayList();
        for (String zza : list) {
            arrayList.add(zza(context, zza, str, zzbp));
        }
        return arrayList;
    }

    JSONArray zza(Object[] objArr) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object zza : objArr) {
            zza(jSONArray, zza);
        }
        return jSONArray;
    }

    public void zza(Activity activity, OnGlobalLayoutListener onGlobalLayoutListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public void zza(Activity activity, OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnScrollChangedListener(onScrollChangedListener);
        }
    }

    public void zza(Context context, String str, WebSettings webSettings) {
        webSettings.setUserAgentString(zzf(context, str));
    }

    public void zza(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (z) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext == null) {
                applicationContext = context;
            }
            bundle.putString("os", VERSION.RELEASE);
            bundle.putString("api", String.valueOf(VERSION.SDK_INT));
            bundle.putString("device", zzp.zzbx().zzgt());
            bundle.putString("js", str);
            bundle.putString("appid", applicationContext.getPackageName());
            bundle.putString("eids", TextUtils.join(",", zzby.zzde()));
        }
        Uri.Builder appendQueryParameter = new Uri.Builder().scheme("https").path("//pagead2.googlesyndication.com/pagead/gen_204").appendQueryParameter("id", str2);
        for (String str3 : bundle.keySet()) {
            appendQueryParameter.appendQueryParameter(str3, bundle.getString(str3));
        }
        zzp.zzbx().zzc(context, str, appendQueryParameter.toString());
    }

    public void zza(Context context, String str, List<String> list) {
        for (String com_google_android_gms_internal_zzia : list) {
            new zzia(context, str, com_google_android_gms_internal_zzia).zzgn();
        }
    }

    public void zza(Context context, String str, List<String> list, String str2) {
        for (String com_google_android_gms_internal_zzia : list) {
            new zzia(context, str, com_google_android_gms_internal_zzia, str2).zzgn();
        }
    }

    public void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        zza(context, str, z, httpURLConnection, false);
    }

    public void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection, String str2) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", str2);
        httpURLConnection.setUseCaches(false);
    }

    public void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection, boolean z2) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", zzf(context, str));
        httpURLConnection.setUseCaches(z2);
    }

    public boolean zza(PackageManager packageManager, String str, String str2) {
        return packageManager.checkPermission(str2, str) == 0;
    }

    public boolean zza(ClassLoader classLoader, Class<?> cls, String str) {
        boolean z = false;
        try {
            z = cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable th) {
        }
        return z;
    }

    public String zzaw(String str) {
        return Uri.parse(str).buildUpon().query(null).build().toString();
    }

    public int zzax(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            zzb.zzaE("Could not parse value:" + e);
            return 0;
        }
    }

    public boolean zzay(String str) {
        return TextUtils.isEmpty(str) ? false : str.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)");
    }

    public void zzb(Activity activity, OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().removeOnScrollChangedListener(onScrollChangedListener);
        }
    }

    public void zzb(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (((Boolean) zzby.zzuZ.get()).booleanValue()) {
            zza(context, str, str2, bundle, z);
        }
    }

    public boolean zzb(Uri uri) {
        return new zzan(null).zzb(uri);
    }

    public void zzc(Context context, String str, String str2) {
        List arrayList = new ArrayList();
        arrayList.add(str2);
        zza(context, str, arrayList);
    }

    public boolean zzc(Uri uri) {
        return new zzan(null).zzc(uri);
    }

    public String zzd(Context context, String str, String str2) {
        return zza(context, str, str2, zzm.zzq(context).zzbp());
    }

    public Map<String, String> zze(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (String str : zzp.zzbz().zzf(uri)) {
            hashMap.put(str, uri.getQueryParameter(str));
        }
        return hashMap;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String zzf(android.content.Context r4, java.lang.String r5) {
        /*
        r3 = this;
        r1 = r3.zzpc;
        monitor-enter(r1);
        r0 = r3.zzHj;	 Catch:{ all -> 0x005f }
        if (r0 == 0) goto L_0x000b;
    L_0x0007:
        r0 = r3.zzHj;	 Catch:{ all -> 0x005f }
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = com.google.android.gms.ads.internal.zzp.zzbz();	 Catch:{ Exception -> 0x0098 }
        r0 = r0.getDefaultUserAgent(r4);	 Catch:{ Exception -> 0x0098 }
        r3.zzHj = r0;	 Catch:{ Exception -> 0x0098 }
    L_0x0015:
        r0 = r3.zzHj;	 Catch:{ all -> 0x005f }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x005f }
        if (r0 == 0) goto L_0x0068;
    L_0x001d:
        r0 = com.google.android.gms.ads.internal.client.zzk.zzcE();	 Catch:{ all -> 0x005f }
        r0 = r0.zzgI();	 Catch:{ all -> 0x005f }
        if (r0 != 0) goto L_0x0062;
    L_0x0027:
        r0 = 0;
        r3.zzHj = r0;	 Catch:{ all -> 0x005f }
        r0 = zzHK;	 Catch:{ all -> 0x005f }
        r2 = new com.google.android.gms.internal.zzhu$1;	 Catch:{ all -> 0x005f }
        r2.<init>(r3, r4);	 Catch:{ all -> 0x005f }
        r0.post(r2);	 Catch:{ all -> 0x005f }
    L_0x0034:
        r0 = r3.zzHj;	 Catch:{ all -> 0x005f }
        if (r0 != 0) goto L_0x0068;
    L_0x0038:
        r0 = r3.zzpc;	 Catch:{ InterruptedException -> 0x003e }
        r0.wait();	 Catch:{ InterruptedException -> 0x003e }
        goto L_0x0034;
    L_0x003e:
        r0 = move-exception;
        r0 = r3.zzgr();	 Catch:{ all -> 0x005f }
        r3.zzHj = r0;	 Catch:{ all -> 0x005f }
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005f }
        r0.<init>();	 Catch:{ all -> 0x005f }
        r2 = "Interrupted, use default user agent: ";
        r0 = r0.append(r2);	 Catch:{ all -> 0x005f }
        r2 = r3.zzHj;	 Catch:{ all -> 0x005f }
        r0 = r0.append(r2);	 Catch:{ all -> 0x005f }
        r0 = r0.toString();	 Catch:{ all -> 0x005f }
        com.google.android.gms.ads.internal.util.client.zzb.zzaE(r0);	 Catch:{ all -> 0x005f }
        goto L_0x0034;
    L_0x005f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
        throw r0;
    L_0x0062:
        r0 = r3.zzK(r4);	 Catch:{ Exception -> 0x0090 }
        r3.zzHj = r0;	 Catch:{ Exception -> 0x0090 }
    L_0x0068:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005f }
        r0.<init>();	 Catch:{ all -> 0x005f }
        r2 = r3.zzHj;	 Catch:{ all -> 0x005f }
        r0 = r0.append(r2);	 Catch:{ all -> 0x005f }
        r2 = " (Mobile; ";
        r0 = r0.append(r2);	 Catch:{ all -> 0x005f }
        r0 = r0.append(r5);	 Catch:{ all -> 0x005f }
        r2 = ")";
        r0 = r0.append(r2);	 Catch:{ all -> 0x005f }
        r0 = r0.toString();	 Catch:{ all -> 0x005f }
        r3.zzHj = r0;	 Catch:{ all -> 0x005f }
        r0 = r3.zzHj;	 Catch:{ all -> 0x005f }
        monitor-exit(r1);	 Catch:{ all -> 0x005f }
        goto L_0x000a;
    L_0x0090:
        r0 = move-exception;
        r0 = r3.zzgr();	 Catch:{ all -> 0x005f }
        r3.zzHj = r0;	 Catch:{ all -> 0x005f }
        goto L_0x0068;
    L_0x0098:
        r0 = move-exception;
        goto L_0x0015;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzhu.zzf(android.content.Context, java.lang.String):java.lang.String");
    }

    public int[] zzg(Activity activity) {
        Window window = activity.getWindow();
        if (window == null || window.findViewById(16908290) == null) {
            return zzgu();
        }
        return new int[]{window.findViewById(16908290).getWidth(), window.findViewById(16908290).getHeight()};
    }

    public boolean zzgq() {
        return this.zzHL;
    }

    String zzgr() {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("Mozilla/5.0 (Linux; U; Android");
        if (VERSION.RELEASE != null) {
            stringBuffer.append(" ").append(VERSION.RELEASE);
        }
        stringBuffer.append("; ").append(Locale.getDefault());
        if (Build.DEVICE != null) {
            stringBuffer.append("; ").append(Build.DEVICE);
            if (Build.DISPLAY != null) {
                stringBuffer.append(" Build/").append(Build.DISPLAY);
            }
        }
        stringBuffer.append(") AppleWebKit/533 Version/4.0 Safari/533");
        return stringBuffer.toString();
    }

    public String zzgs() {
        UUID randomUUID = UUID.randomUUID();
        byte[] toByteArray = BigInteger.valueOf(randomUUID.getLeastSignificantBits()).toByteArray();
        byte[] toByteArray2 = BigInteger.valueOf(randomUUID.getMostSignificantBits()).toByteArray();
        String bigInteger = new BigInteger(1, toByteArray).toString();
        for (int i = 0; i < 2; i++) {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(toByteArray);
                instance.update(toByteArray2);
                Object obj = new byte[8];
                System.arraycopy(instance.digest(), 0, obj, 0, 8);
                bigInteger = new BigInteger(1, obj).toString();
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return bigInteger;
    }

    public String zzgt() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        return str2.startsWith(str) ? str2 : str + " " + str2;
    }

    protected int[] zzgu() {
        return new int[]{0, 0};
    }

    public int[] zzh(Activity activity) {
        int[] zzg = zzg(activity);
        return new int[]{zzk.zzcE().zzc(activity, zzg[0]), zzk.zzcE().zzc(activity, zzg[1])};
    }

    public int[] zzi(Activity activity) {
        Window window = activity.getWindow();
        if (window == null || window.findViewById(16908290) == null) {
            return zzgu();
        }
        return new int[]{window.findViewById(16908290).getTop(), window.findViewById(16908290).getBottom()};
    }

    public Bitmap zzj(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return createBitmap;
    }

    public int[] zzj(Activity activity) {
        int[] zzi = zzi(activity);
        return new int[]{zzk.zzcE().zzc(activity, zzi[0]), zzk.zzcE().zzc(activity, zzi[1])};
    }

    public int zzk(Activity activity) {
        if (activity == null) {
            zzb.zzaE("Fail to get AdActivity type since it is null");
            return 0;
        }
        AdOverlayInfoParcel zzb = AdOverlayInfoParcel.zzb(activity.getIntent());
        if (zzb == null) {
            zzb.zzaE("Fail to get AdOverlayInfo");
            return 0;
        }
        switch (zzb.zzAX) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 1;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2;
            case TTSConst.TTSUNICODE /*3*/:
                return 3;
            default:
                return 0;
        }
    }

    public JSONObject zzx(Map<String, ?> map) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String str : map.keySet()) {
                zza(jSONObject, str, map.get(str));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            throw new JSONException("Could not convert map to JSON: " + e.getMessage());
        }
    }
}
