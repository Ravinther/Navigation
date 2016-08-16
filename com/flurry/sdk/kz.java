package com.flurry.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public final class kz {
    private static final String f1106a;

    static {
        f1106a = kz.class.getSimpleName();
    }

    public static PackageInfo m1234a(Context context) {
        PackageInfo packageInfo = null;
        if (context != null) {
            try {
                packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 20815);
            } catch (NameNotFoundException e) {
                jq.m1018a(f1106a, "Cannot find package info for package: " + context.getPackageName());
            }
        }
        return packageInfo;
    }

    public static ApplicationInfo m1235b(Context context) {
        ApplicationInfo applicationInfo = null;
        if (context != null) {
            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            } catch (NameNotFoundException e) {
                jq.m1018a(f1106a, "Cannot find application info for package: " + context.getPackageName());
            }
        }
        return applicationInfo;
    }

    public static String m1236c(Context context) {
        PackageInfo a = m1234a(context);
        return (a == null || a.packageName == null) ? "" : a.packageName;
    }

    public static String m1237d(Context context) {
        PackageInfo a = m1234a(context);
        return (a == null || a.versionName == null) ? "" : a.versionName;
    }

    public static Bundle m1238e(Context context) {
        ApplicationInfo b = m1235b(context);
        return (b == null || b.metaData == null) ? Bundle.EMPTY : b.metaData;
    }
}
