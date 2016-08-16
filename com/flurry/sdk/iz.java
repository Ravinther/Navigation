package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.ku.C0426a;

public class iz implements C0426a {
    private static iz f873a;
    private static final String f874b;
    private String f875c;
    private String f876d;

    public static synchronized iz m909a() {
        iz izVar;
        synchronized (iz.class) {
            if (f873a == null) {
                f873a = new iz();
            }
            izVar = f873a;
        }
        return izVar;
    }

    public static void m910b() {
        if (f873a != null) {
            kt.m1217a().m1215b("VersionName", f873a);
        }
        f873a = null;
    }

    static {
        f874b = iz.class.getSimpleName();
    }

    private iz() {
        ku a = kt.m1217a();
        this.f875c = (String) a.m1212a("VersionName");
        a.m1213a("VersionName", (C0426a) this);
        jq.m1016a(4, f874b, "initSettings, VersionName = " + this.f875c);
    }

    public String m913c() {
        return VERSION.RELEASE;
    }

    public String m914d() {
        return Build.DEVICE;
    }

    public synchronized String m915e() {
        String str;
        if (!TextUtils.isEmpty(this.f875c)) {
            str = this.f875c;
        } else if (TextUtils.isEmpty(this.f876d)) {
            this.f876d = m911f();
            str = this.f876d;
        } else {
            str = this.f876d;
        }
        return str;
    }

    private String m911f() {
        try {
            Context c = jc.m946a().m957c();
            PackageInfo packageInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            if (packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            if (packageInfo.versionCode != 0) {
                return Integer.toString(packageInfo.versionCode);
            }
            return "Unknown";
        } catch (Throwable th) {
            jq.m1017a(6, f874b, "", th);
        }
    }

    public void m912a(String str, Object obj) {
        if (str.equals("VersionName")) {
            this.f875c = (String) obj;
            jq.m1016a(4, f874b, "onSettingUpdate, VersionName = " + this.f875c);
            return;
        }
        jq.m1016a(6, f874b, "onSettingUpdate internal error!");
    }
}
