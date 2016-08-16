package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import com.google.android.gms.C0568R;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzn;
import com.google.android.gms.internal.zzlk;
import com.google.android.gms.internal.zzlv;
import com.sygic.aura.C1090R;
import java.util.concurrent.atomic.AtomicBoolean;
import loquendo.tts.engine.TTSConst;

public final class GooglePlayServicesUtil {
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static boolean zzYu;
    public static boolean zzYv;
    private static int zzYw;
    private static String zzYx;
    private static Integer zzYy;
    static final AtomicBoolean zzYz;
    private static final Object zzpm;

    static {
        GOOGLE_PLAY_SERVICES_VERSION_CODE = zzmW();
        zzYu = false;
        zzYv = false;
        zzYw = -1;
        zzpm = new Object();
        zzYx = null;
        zzYy = null;
        zzYz = new AtomicBoolean();
    }

    private GooglePlayServicesUtil() {
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        if (zzd.zzacF) {
            return 0;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            context.getResources().getString(C0568R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            zzad(context);
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.google.android.gms", 64);
            zzd zzmY = zzd.zzmY();
            if (!zzlk.zzbX(packageInfo.versionCode) && !zzlk.zzao(context)) {
                try {
                    if (zzmY.zza(packageManager.getPackageInfo("com.android.vending", 64), zzbu.zzYt) == null) {
                        Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                        return 9;
                    }
                    if (zzmY.zza(packageInfo, zzmY.zza(packageManager.getPackageInfo("com.android.vending", 64), zzbu.zzYt)) == null) {
                        Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                        return 9;
                    }
                } catch (NameNotFoundException e) {
                    if (zzh(context, "com.android.vending")) {
                        Log.w("GooglePlayServicesUtil", "Google Play Store is updating.");
                        if (zzmY.zza(packageInfo, zzbu.zzYt) == null) {
                            Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                            return 9;
                        }
                    }
                    Log.w("GooglePlayServicesUtil", "Google Play Store is neither installed nor updating.");
                    return 9;
                }
            } else if (zzmY.zza(packageInfo, zzbu.zzYt) == null) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            }
            if (zzlk.zzbV(packageInfo.versionCode) < zzlk.zzbV(GOOGLE_PLAY_SERVICES_VERSION_CODE)) {
                Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + packageInfo.versionCode);
                return 2;
            }
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (applicationInfo == null) {
                try {
                    applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
                } catch (NameNotFoundException e2) {
                    Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.");
                    e2.printStackTrace();
                    return 1;
                }
            }
            return !applicationInfo.enabled ? 3 : 0;
        } catch (NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @Deprecated
    public static boolean isUserRecoverableError(int errorCode) {
        switch (errorCode) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return true;
            default:
                return false;
        }
    }

    public static boolean showErrorDialogFragment(int errorCode, Activity activity, Fragment fragment, int requestCode, OnCancelListener cancelListener) {
        boolean z = false;
        Dialog zza = zza(errorCode, activity, fragment, requestCode, cancelListener);
        if (zza == null) {
            return z;
        }
        try {
            z = activity instanceof FragmentActivity;
        } catch (NoClassDefFoundError e) {
        }
        if (z) {
            SupportErrorDialogFragment.newInstance(zza, cancelListener).show(((FragmentActivity) activity).getSupportFragmentManager(), "GooglePlayServicesErrorDialog");
        } else if (zzlv.zzpO()) {
            ErrorDialogFragment.newInstance(zza, cancelListener).show(activity.getFragmentManager(), "GooglePlayServicesErrorDialog");
        } else {
            throw new RuntimeException("This Activity does not support Fragments.");
        }
        return true;
    }

    private static Dialog zza(int i, Activity activity, Fragment fragment, int i2, OnCancelListener onCancelListener) {
        Builder builder = null;
        if (i == 0) {
            return null;
        }
        if (zzlk.zzao(activity) && i == 2) {
            i = 42;
        }
        if (zzlv.zzpR()) {
            TypedValue typedValue = new TypedValue();
            activity.getTheme().resolveAttribute(16843529, typedValue, true);
            if ("Theme.Dialog.Alert".equals(activity.getResources().getResourceEntryName(typedValue.resourceId))) {
                builder = new Builder(activity, 5);
            }
        }
        if (builder == null) {
            builder = new Builder(activity);
        }
        builder.setMessage(zzg.zzb(activity, i, zzaf(activity)));
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        Intent zzbc = zzbc(i);
        OnClickListener com_google_android_gms_common_internal_zzh = fragment == null ? new zzh(activity, zzbc, i2) : new zzh(fragment, zzbc, i2);
        CharSequence zzh = zzg.zzh(activity, i);
        if (zzh != null) {
            builder.setPositiveButton(zzh, com_google_android_gms_common_internal_zzh);
        }
        CharSequence zzg = zzg.zzg(activity, i);
        if (zzg != null) {
            builder.setTitle(zzg);
        }
        return builder.create();
    }

    public static boolean zza(Context context, int i, String str) {
        if (zzlv.zzpV()) {
            try {
                ((AppOpsManager) context.getSystemService("appops")).checkPackage(i, str);
                return true;
            } catch (SecurityException e) {
                return false;
            }
        }
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(i);
        if (str == null || packagesForUid == null) {
            return false;
        }
        for (Object equals : packagesForUid) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static void zzaa(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            Intent zzbc = zzbc(isGooglePlayServicesAvailable);
            Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + isGooglePlayServicesAvailable);
            if (zzbc == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", zzbc);
        }
    }

    @Deprecated
    public static void zzac(Context context) {
        if (!zzYz.getAndSet(true)) {
            try {
                ((NotificationManager) context.getSystemService("notification")).cancel(10436);
            } catch (SecurityException e) {
            }
        }
    }

    private static void zzad(Context context) {
        synchronized (zzpm) {
            if (zzYx == null) {
                zzYx = context.getPackageName();
                try {
                    Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
                    if (bundle != null) {
                        zzYy = Integer.valueOf(bundle.getInt("com.google.android.gms.version"));
                    } else {
                        zzYy = null;
                    }
                } catch (Throwable e) {
                    Log.wtf("GooglePlayServicesUtil", "This should never happen.", e);
                }
            } else if (!zzYx.equals(context.getPackageName())) {
                throw new IllegalArgumentException("isGooglePlayServicesAvailable should only be called with Context from your application's package. A previous call used package '" + zzYx + "' and this call used package '" + context.getPackageName() + "'.");
            }
            Integer num = zzYy;
        }
        if (num == null) {
            throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
        } else if (num.intValue() != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
            throw new IllegalStateException("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but" + " found " + num + ".  You must have the" + " following declaration within the <application> element: " + "    <meta-data android:name=\"" + "com.google.android.gms.version" + "\" android:value=\"@integer/google_play_services_version\" />");
        }
    }

    public static String zzaf(Context context) {
        Object obj = context.getApplicationInfo().name;
        if (!TextUtils.isEmpty(obj)) {
            return obj;
        }
        ApplicationInfo applicationInfo;
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        return applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo).toString() : packageName;
    }

    public static boolean zzag(Context context) {
        return zzlv.zzpX() && context.getPackageManager().hasSystemFeature("com.google.sidewinder");
    }

    public static boolean zzb(PackageManager packageManager) {
        synchronized (zzpm) {
            if (zzYw == -1) {
                try {
                    if (zzd.zzmY().zza(packageManager.getPackageInfo("com.google.android.gms", 64), zzc.zzYm[1]) != null) {
                        zzYw = 1;
                    } else {
                        zzYw = 0;
                    }
                } catch (NameNotFoundException e) {
                    zzYw = 0;
                }
            }
        }
        return zzYw != 0;
    }

    @Deprecated
    public static boolean zzb(PackageManager packageManager, String str) {
        return zzd.zzmY().zzb(packageManager, str);
    }

    @Deprecated
    public static Intent zzbc(int i) {
        switch (i) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                return zzn.zzcp("com.google.android.gms");
            case TTSConst.TTSUNICODE /*3*/:
                return zzn.zzcn("com.google.android.gms");
            case C1090R.styleable.Theme_dialogTheme /*42*/:
                return zzn.zzoM();
            default:
                return null;
        }
    }

    public static boolean zzc(PackageManager packageManager) {
        return zzb(packageManager) || !zzmX();
    }

    @Deprecated
    public static boolean zzd(Context context, int i) {
        return i == 18 ? true : i == 1 ? zzh(context, "com.google.android.gms") : false;
    }

    public static boolean zze(Context context, int i) {
        return zza(context, i, "com.google.android.gms") && zzb(context.getPackageManager(), "com.google.android.gms");
    }

    public static boolean zzh(Context context, String str) {
        if (zzlv.zzpX()) {
            for (SessionInfo appPackageName : context.getPackageManager().getPackageInstaller().getAllSessions()) {
                if (str.equals(appPackageName.getAppPackageName())) {
                    return true;
                }
            }
        }
        try {
            if (context.getPackageManager().getApplicationInfo(str, 8192).enabled) {
                return true;
            }
        } catch (NameNotFoundException e) {
        }
        return false;
    }

    private static int zzmW() {
        return 7895000;
    }

    public static boolean zzmX() {
        return zzYu ? zzYv : "user".equals(Build.TYPE);
    }
}
