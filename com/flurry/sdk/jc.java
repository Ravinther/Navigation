package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

public class jc {
    private static jc f894a;
    private static final String f895b;
    private final Context f896c;
    private final Handler f897d;
    private final Handler f898e;
    private final HandlerThread f899f;
    private final String f900g;
    private final js f901h;

    public static jc m946a() {
        return f894a;
    }

    public static synchronized void m948a(Context context, String str) {
        synchronized (jc.class) {
            if (f894a != null) {
                if (!f894a.m959d().equals(str)) {
                    throw new IllegalStateException("Only one API key per application is supported!");
                }
            } else if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            } else if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("API key must be specified");
            } else {
                f894a = new jc(context, str);
                f894a.m947a(context);
            }
        }
    }

    public static synchronized void m949b() {
        synchronized (jc.class) {
            if (f894a != null) {
                f894a.m950h();
                f894a = null;
            }
        }
    }

    static {
        f895b = jc.class.getSimpleName();
    }

    private jc(Context context, String str) {
        this.f896c = context.getApplicationContext();
        this.f897d = new Handler(Looper.getMainLooper());
        this.f899f = new HandlerThread("FlurryAgent");
        this.f899f.start();
        this.f898e = new Handler(this.f899f.getLooper());
        this.f900g = str;
        this.f901h = new js();
    }

    private void m950h() {
        m951i();
        this.f899f.quit();
    }

    private void m947a(Context context) {
        this.f901h.m1039a(context);
    }

    private void m951i() {
        this.f901h.m1038a();
    }

    public Context m957c() {
        return this.f896c;
    }

    public String m959d() {
        return this.f900g;
    }

    public PackageManager m960e() {
        return this.f896c.getPackageManager();
    }

    public Handler m961f() {
        return this.f897d;
    }

    public void m953a(Runnable runnable) {
        if (runnable != null) {
            this.f897d.post(runnable);
        }
    }

    public void m954a(Runnable runnable, long j) {
        if (runnable != null) {
            this.f897d.postDelayed(runnable, j);
        }
    }

    public Handler m962g() {
        return this.f898e;
    }

    public void m955b(Runnable runnable) {
        if (runnable != null) {
            this.f898e.post(runnable);
        }
    }

    public void m956b(Runnable runnable, long j) {
        if (runnable != null) {
            this.f898e.postDelayed(runnable, j);
        }
    }

    public void m958c(Runnable runnable) {
        if (runnable != null) {
            this.f898e.removeCallbacks(runnable);
        }
    }

    public jt m952a(Class<? extends jt> cls) {
        return this.f901h.m1037a((Class) cls);
    }
}
