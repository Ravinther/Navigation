package com.google.android.gms.internal;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzbk extends Thread {
    private boolean mStarted;
    private boolean zzam;
    private final Object zzpc;
    private final int zzrC;
    private final int zzrE;
    private boolean zzrO;
    private final zzbj zzrP;
    private final zzbi zzrQ;
    private final zzgj zzrR;
    private final int zzrS;
    private final int zzrT;
    private final int zzrU;

    /* renamed from: com.google.android.gms.internal.zzbk.1 */
    class C08661 implements Runnable {
        final /* synthetic */ View zzrV;
        final /* synthetic */ zzbk zzrW;

        C08661(zzbk com_google_android_gms_internal_zzbk, View view) {
            this.zzrW = com_google_android_gms_internal_zzbk;
            this.zzrV = view;
        }

        public void run() {
            this.zzrW.zzg(this.zzrV);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbk.2 */
    class C08682 implements Runnable {
        final /* synthetic */ zzbk zzrW;
        ValueCallback<String> zzrX;
        final /* synthetic */ zzbh zzrY;
        final /* synthetic */ WebView zzrZ;

        /* renamed from: com.google.android.gms.internal.zzbk.2.1 */
        class C08671 implements ValueCallback<String> {
            final /* synthetic */ C08682 zzsa;

            C08671(C08682 c08682) {
                this.zzsa = c08682;
            }

            public /* synthetic */ void onReceiveValue(Object x0) {
                zzy((String) x0);
            }

            public void zzy(String str) {
                this.zzsa.zzrW.zza(this.zzsa.zzrY, this.zzsa.zzrZ, str);
            }
        }

        C08682(zzbk com_google_android_gms_internal_zzbk, zzbh com_google_android_gms_internal_zzbh, WebView webView) {
            this.zzrW = com_google_android_gms_internal_zzbk;
            this.zzrY = com_google_android_gms_internal_zzbh;
            this.zzrZ = webView;
            this.zzrX = new C08671(this);
        }

        public void run() {
            if (this.zzrZ.getSettings().getJavaScriptEnabled()) {
                try {
                    this.zzrZ.evaluateJavascript("(function() { return  {text:document.body.innerText}})();", this.zzrX);
                } catch (Throwable th) {
                    this.zzrX.onReceiveValue("");
                }
            }
        }
    }

    @zzgk
    class zza {
        final /* synthetic */ zzbk zzrW;
        final int zzsb;
        final int zzsc;

        zza(zzbk com_google_android_gms_internal_zzbk, int i, int i2) {
            this.zzrW = com_google_android_gms_internal_zzbk;
            this.zzsb = i;
            this.zzsc = i2;
        }
    }

    public zzbk(zzbj com_google_android_gms_internal_zzbj, zzbi com_google_android_gms_internal_zzbi, zzgj com_google_android_gms_internal_zzgj) {
        this.mStarted = false;
        this.zzrO = false;
        this.zzam = false;
        this.zzrP = com_google_android_gms_internal_zzbj;
        this.zzrQ = com_google_android_gms_internal_zzbi;
        this.zzrR = com_google_android_gms_internal_zzgj;
        this.zzpc = new Object();
        this.zzrC = ((Integer) zzby.zzuF.get()).intValue();
        this.zzrT = ((Integer) zzby.zzuG.get()).intValue();
        this.zzrE = ((Integer) zzby.zzuH.get()).intValue();
        this.zzrU = ((Integer) zzby.zzuI.get()).intValue();
        this.zzrS = ((Integer) zzby.zzuJ.get()).intValue();
        setName("ContentFetchTask");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r3 = this;
    L_0x0000:
        r0 = r3.zzam;
        if (r0 != 0) goto L_0x0056;
    L_0x0004:
        r0 = r3.zzcu();	 Catch:{ Throwable -> 0x0019 }
        if (r0 == 0) goto L_0x0047;
    L_0x000a:
        r0 = r3.zzrP;	 Catch:{ Throwable -> 0x0019 }
        r0 = r0.getActivity();	 Catch:{ Throwable -> 0x0019 }
        if (r0 != 0) goto L_0x003b;
    L_0x0012:
        r0 = "ContentFetchThread: no activity";
        com.google.android.gms.ads.internal.util.client.zzb.zzaC(r0);	 Catch:{ Throwable -> 0x0019 }
        goto L_0x0000;
    L_0x0019:
        r0 = move-exception;
        r1 = "Error in ContentFetchTask";
        com.google.android.gms.ads.internal.util.client.zzb.zzb(r1, r0);
        r1 = r3.zzrR;
        r2 = 1;
        r1.zza(r0, r2);
    L_0x0026:
        r1 = r3.zzpc;
        monitor-enter(r1);
    L_0x0029:
        r0 = r3.zzrO;	 Catch:{ all -> 0x0053 }
        if (r0 == 0) goto L_0x0051;
    L_0x002d:
        r0 = "ContentFetchTask: waiting";
        com.google.android.gms.ads.internal.util.client.zzb.zzaC(r0);	 Catch:{ InterruptedException -> 0x0039 }
        r0 = r3.zzpc;	 Catch:{ InterruptedException -> 0x0039 }
        r0.wait();	 Catch:{ InterruptedException -> 0x0039 }
        goto L_0x0029;
    L_0x0039:
        r0 = move-exception;
        goto L_0x0029;
    L_0x003b:
        r3.zza(r0);	 Catch:{ Throwable -> 0x0019 }
    L_0x003e:
        r0 = r3.zzrS;	 Catch:{ Throwable -> 0x0019 }
        r0 = r0 * 1000;
        r0 = (long) r0;	 Catch:{ Throwable -> 0x0019 }
        java.lang.Thread.sleep(r0);	 Catch:{ Throwable -> 0x0019 }
        goto L_0x0026;
    L_0x0047:
        r0 = "ContentFetchTask: sleeping";
        com.google.android.gms.ads.internal.util.client.zzb.zzaC(r0);	 Catch:{ Throwable -> 0x0019 }
        r3.zzcw();	 Catch:{ Throwable -> 0x0019 }
        goto L_0x003e;
    L_0x0051:
        monitor-exit(r1);	 Catch:{ all -> 0x0053 }
        goto L_0x0000;
    L_0x0053:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0053 }
        throw r0;
    L_0x0056:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbk.run():void");
    }

    public void wakeup() {
        synchronized (this.zzpc) {
            this.zzrO = false;
            this.zzpc.notifyAll();
            zzb.zzaC("ContentFetchThread: wakeup");
        }
    }

    zza zza(View view, zzbh com_google_android_gms_internal_zzbh) {
        int i = 0;
        if (view == null) {
            return new zza(this, 0, 0);
        }
        if ((view instanceof TextView) && !(view instanceof EditText)) {
            CharSequence text = ((TextView) view).getText();
            if (TextUtils.isEmpty(text)) {
                return new zza(this, 0, 0);
            }
            com_google_android_gms_internal_zzbh.zzw(text.toString());
            return new zza(this, 1, 0);
        } else if ((view instanceof WebView) && !(view instanceof zzip)) {
            com_google_android_gms_internal_zzbh.zzcp();
            return zza((WebView) view, com_google_android_gms_internal_zzbh) ? new zza(this, 0, 1) : new zza(this, 0, 0);
        } else if (!(view instanceof ViewGroup)) {
            return new zza(this, 0, 0);
        } else {
            ViewGroup viewGroup = (ViewGroup) view;
            int i2 = 0;
            int i3 = 0;
            while (i < viewGroup.getChildCount()) {
                zza zza = zza(viewGroup.getChildAt(i), com_google_android_gms_internal_zzbh);
                i3 += zza.zzsb;
                i2 += zza.zzsc;
                i++;
            }
            return new zza(this, i3, i2);
        }
    }

    void zza(Activity activity) {
        if (activity != null) {
            View view = null;
            if (!(activity.getWindow() == null || activity.getWindow().getDecorView() == null)) {
                view = activity.getWindow().getDecorView().findViewById(16908290);
            }
            if (view != null) {
                zzf(view);
            }
        }
    }

    void zza(zzbh com_google_android_gms_internal_zzbh, WebView webView, String str) {
        com_google_android_gms_internal_zzbh.zzco();
        try {
            if (!TextUtils.isEmpty(str)) {
                String optString = new JSONObject(str).optString("text");
                if (TextUtils.isEmpty(webView.getTitle())) {
                    com_google_android_gms_internal_zzbh.zzv(optString);
                } else {
                    com_google_android_gms_internal_zzbh.zzv(webView.getTitle() + "\n" + optString);
                }
            }
            if (com_google_android_gms_internal_zzbh.zzcl()) {
                this.zzrQ.zzb(com_google_android_gms_internal_zzbh);
            }
        } catch (JSONException e) {
            zzb.zzaC("Json string may be malformed.");
        } catch (Throwable th) {
            zzb.zza("Failed to get webview content.", th);
            this.zzrR.zza(th, true);
        }
    }

    boolean zza(RunningAppProcessInfo runningAppProcessInfo) {
        return runningAppProcessInfo.importance == 100;
    }

    boolean zza(WebView webView, zzbh com_google_android_gms_internal_zzbh) {
        if (!zzlv.zzpV()) {
            return false;
        }
        com_google_android_gms_internal_zzbh.zzcp();
        webView.post(new C08682(this, com_google_android_gms_internal_zzbh, webView));
        return true;
    }

    public void zzct() {
        synchronized (this.zzpc) {
            if (this.mStarted) {
                zzb.zzaC("Content hash thread already started, quiting...");
                return;
            }
            this.mStarted = true;
            start();
        }
    }

    boolean zzcu() {
        try {
            Context context = this.zzrP.getContext();
            if (context == null) {
                return false;
            }
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
                    if (zza(runningAppProcessInfo) && !keyguardManager.inKeyguardRestrictedInputMode() && zzr(context)) {
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

    public zzbh zzcv() {
        return this.zzrQ.zzcs();
    }

    public void zzcw() {
        synchronized (this.zzpc) {
            this.zzrO = true;
            zzb.zzaC("ContentFetchThread: paused, mPause = " + this.zzrO);
        }
    }

    public boolean zzcx() {
        return this.zzrO;
    }

    boolean zzf(View view) {
        if (view == null) {
            return false;
        }
        view.post(new C08661(this, view));
        return true;
    }

    void zzg(View view) {
        try {
            zzbh com_google_android_gms_internal_zzbh = new zzbh(this.zzrC, this.zzrT, this.zzrE, this.zzrU);
            zza zza = zza(view, com_google_android_gms_internal_zzbh);
            com_google_android_gms_internal_zzbh.zzcq();
            if (zza.zzsb != 0 || zza.zzsc != 0) {
                if (zza.zzsc != 0 || com_google_android_gms_internal_zzbh.zzcr() != 0) {
                    if (zza.zzsc != 0 || !this.zzrQ.zza(com_google_android_gms_internal_zzbh)) {
                        this.zzrQ.zzc(com_google_android_gms_internal_zzbh);
                    }
                }
            }
        } catch (Throwable e) {
            zzb.zzb("Exception in fetchContentOnUIThread", e);
            this.zzrR.zza(e, true);
        }
    }

    boolean zzr(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        return powerManager == null ? false : powerManager.isScreenOn();
    }
}
