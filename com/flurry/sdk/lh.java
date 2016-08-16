package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.ku.C0426a;
import java.lang.Thread.UncaughtExceptionHandler;

public class lh implements jt, C0426a, UncaughtExceptionHandler {
    private static final String f1110a;
    private boolean f1111b;

    static {
        f1110a = lh.class.getSimpleName();
    }

    public void m1301a(Context context) {
        ku a = kt.m1217a();
        this.f1111b = ((Boolean) a.m1212a("CaptureUncaughtExceptions")).booleanValue();
        a.m1213a("CaptureUncaughtExceptions", (C0426a) this);
        jq.m1016a(4, f1110a, "initSettings, CrashReportingEnabled = " + this.f1111b);
        li.m1304a().m1312a(this);
    }

    public void m1303b() {
        li.m1307b();
        kt.m1217a().m1215b("CaptureUncaughtExceptions", (C0426a) this);
    }

    public void m1302a(String str, Object obj) {
        if (str.equals("CaptureUncaughtExceptions")) {
            this.f1111b = ((Boolean) obj).booleanValue();
            jq.m1016a(4, f1110a, "onSettingUpdate, CrashReportingEnabled = " + this.f1111b);
            return;
        }
        jq.m1016a(6, f1110a, "onSettingUpdate internal error!");
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace();
        if (this.f1111b) {
            String str = "";
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                if (th.getMessage() != null) {
                    stringBuilder.append(" (" + th.getMessage() + ")\n");
                }
                str = stringBuilder.toString();
            } else if (th.getMessage() != null) {
                str = th.getMessage();
            }
            hi.m500a().m511a("uncaught", str, th);
        }
        kq.m1187a().m1206g();
        iu.m877a().m894d();
    }
}
