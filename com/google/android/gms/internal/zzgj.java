package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@zzgk
public class zzgj implements UncaughtExceptionHandler {
    private Context mContext;
    private VersionInfoParcel zzBp;
    private UncaughtExceptionHandler zzDn;
    private UncaughtExceptionHandler zzDo;

    public zzgj(Context context, VersionInfoParcel versionInfoParcel, UncaughtExceptionHandler uncaughtExceptionHandler, UncaughtExceptionHandler uncaughtExceptionHandler2) {
        this.zzDn = uncaughtExceptionHandler;
        this.zzDo = uncaughtExceptionHandler2;
        this.mContext = context;
        this.zzBp = versionInfoParcel;
    }

    public static zzgj zza(Context context, Thread thread, VersionInfoParcel versionInfoParcel) {
        if (context == null || thread == null || versionInfoParcel == null) {
            return null;
        }
        if (!zzz(context)) {
            return null;
        }
        UncaughtExceptionHandler uncaughtExceptionHandler = thread.getUncaughtExceptionHandler();
        UncaughtExceptionHandler com_google_android_gms_internal_zzgj = new zzgj(context, versionInfoParcel, uncaughtExceptionHandler, Thread.getDefaultUncaughtExceptionHandler());
        if (uncaughtExceptionHandler != null && (uncaughtExceptionHandler instanceof zzgj)) {
            return (zzgj) uncaughtExceptionHandler;
        }
        try {
            thread.setUncaughtExceptionHandler(com_google_android_gms_internal_zzgj);
            return com_google_android_gms_internal_zzgj;
        } catch (Throwable e) {
            zzb.zzc("Fail to set UncaughtExceptionHandler.", e);
            return null;
        }
    }

    private Throwable zzb(Throwable th) {
        if (((Boolean) zzby.zzuc.get()).booleanValue()) {
            return th;
        }
        LinkedList linkedList = new LinkedList();
        while (th != null) {
            linkedList.push(th);
            th = th.getCause();
        }
        Throwable th2 = null;
        while (!linkedList.isEmpty()) {
            Throwable th3;
            Throwable th4 = (Throwable) linkedList.pop();
            StackTraceElement[] stackTrace = th4.getStackTrace();
            ArrayList arrayList = new ArrayList();
            arrayList.add(new StackTraceElement(th4.getClass().getName(), "<filtered>", "<filtered>", 1));
            int i = 0;
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (zzao(stackTraceElement.getClassName())) {
                    arrayList.add(stackTraceElement);
                    i = 1;
                } else if (zzap(stackTraceElement.getClassName())) {
                    arrayList.add(stackTraceElement);
                } else {
                    arrayList.add(new StackTraceElement("<filtered>", "<filtered>", "<filtered>", 1));
                }
            }
            if (i != 0) {
                th3 = th2 == null ? new Throwable(th4.getMessage()) : new Throwable(th4.getMessage(), th2);
                th3.setStackTrace((StackTraceElement[]) arrayList.toArray(new StackTraceElement[0]));
            } else {
                th3 = th2;
            }
            th2 = th3;
        }
        return th2;
    }

    private static boolean zzz(Context context) {
        return ((Boolean) zzby.zzub.get()).booleanValue();
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        if (zza(exception)) {
            if (Looper.getMainLooper().getThread() != thread) {
                zza(exception, true);
                return;
            }
            zza(exception, false);
        }
        if (this.zzDn != null) {
            this.zzDn.uncaughtException(thread, exception);
        } else if (this.zzDo != null) {
            this.zzDo.uncaughtException(thread, exception);
        }
    }

    public void zza(Throwable th, boolean z) {
        if (zzz(this.mContext)) {
            Throwable zzb = zzb(th);
            if (zzb != null) {
                List arrayList = new ArrayList();
                arrayList.add(zzb(zzb, z));
                zzp.zzbx().zza(this.mContext, this.zzBp.zzIz, arrayList, zzp.zzbA().zzgf());
            }
        }
    }

    protected boolean zza(Throwable th) {
        boolean z = true;
        if (th == null) {
            return false;
        }
        boolean z2 = false;
        boolean z3 = false;
        while (th != null) {
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                if (zzao(stackTraceElement.getClassName())) {
                    z3 = true;
                }
                if (getClass().getName().equals(stackTraceElement.getClassName())) {
                    z2 = true;
                }
            }
            th = th.getCause();
        }
        if (!z3 || r2) {
            z = false;
        }
        return z;
    }

    protected boolean zzao(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return z;
        }
        if (str.startsWith("com.google.android.gms.ads")) {
            return true;
        }
        if (str.startsWith("com.google.ads")) {
            return true;
        }
        try {
            return Class.forName(str).isAnnotationPresent(zzgk.class);
        } catch (Throwable e) {
            zzb.zza("Fail to check class type for class " + str, e);
            return z;
        }
    }

    protected boolean zzap(String str) {
        return TextUtils.isEmpty(str) ? false : str.startsWith("android.") || str.startsWith("java.");
    }

    String zzb(Throwable th, boolean z) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return new Builder().scheme("https").path("//pagead2.googlesyndication.com/pagead/gen_204").appendQueryParameter("id", "gmob-apps-report-exception").appendQueryParameter("os", VERSION.RELEASE).appendQueryParameter("api", String.valueOf(VERSION.SDK_INT)).appendQueryParameter("device", zzp.zzbx().zzgt()).appendQueryParameter("js", this.zzBp.zzIz).appendQueryParameter("appid", this.mContext.getApplicationContext().getPackageName()).appendQueryParameter("stacktrace", stringWriter.toString()).appendQueryParameter("eids", TextUtils.join(",", zzby.zzde())).appendQueryParameter("trapped", String.valueOf(z)).toString();
    }
}
