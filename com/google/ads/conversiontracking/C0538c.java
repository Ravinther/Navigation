package com.google.ads.conversiontracking;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* renamed from: com.google.ads.conversiontracking.c */
public class C0538c implements Runnable {
    private static final long f1139a;
    private static final long f1140b;
    private static Object f1141c;
    private static C0538c f1142d;
    private final long f1143e;
    private final long f1144f;
    private final C0537b f1145g;
    private final Context f1146h;
    private final HandlerThread f1147i;
    private final Object f1148j;
    private final Set<String> f1149k;
    private final Map<String, Long> f1150l;
    private final SharedPreferences f1151m;
    private long f1152n;
    private Handler f1153o;

    static {
        f1139a = TimeUnit.SECONDS.toMillis(3600);
        f1140b = TimeUnit.SECONDS.toMillis(30);
        f1141c = new Object();
    }

    public static C0538c m1320a(Context context) {
        synchronized (f1141c) {
            if (f1142d == null) {
                try {
                    f1142d = new C0538c(context, f1139a, f1140b, new C0537b(context));
                } catch (Throwable e) {
                    Log.e("GoogleConversionReporter", "Error starting automated usage thread", e);
                }
            }
        }
        return f1142d;
    }

    C0538c(Context context, long j, long j2, C0537b c0537b) {
        this.f1148j = new Object();
        this.f1146h = context;
        this.f1144f = j;
        this.f1143e = j2;
        this.f1145g = c0537b;
        this.f1150l = new HashMap();
        this.f1149k = new HashSet();
        this.f1151m = this.f1146h.getSharedPreferences("google_auto_usage", 0);
        m1324d();
        this.f1147i = new HandlerThread("Google Conversion SDK", 10);
        this.f1147i.start();
        this.f1153o = new Handler(this.f1147i.getLooper());
        m1323c();
    }

    public void m1327c(String str) {
        synchronized (this.f1148j) {
            if (this.f1149k.contains(str) || this.f1150l.containsKey(str)) {
                return;
            }
            this.f1145g.m1319a(str, this.f1152n);
            this.f1150l.put(str, Long.valueOf(this.f1152n));
        }
    }

    public boolean m1328d(String str) {
        return this.f1150l.containsKey(str);
    }

    public void run() {
        if (m1326a()) {
            synchronized (this.f1148j) {
                for (Entry entry : this.f1150l.entrySet()) {
                    String str = (String) entry.getKey();
                    if (((Long) entry.getValue()).longValue() < this.f1152n) {
                        entry.setValue(Long.valueOf(this.f1152n));
                        this.f1145g.m1319a(str, this.f1152n);
                    }
                }
            }
            m1323c();
            m1322b(m1321b());
            return;
        }
        m1325a(this.f1143e);
    }

    private long m1321b() {
        long a = C0552g.m1381a();
        long j = 0;
        if (a >= this.f1152n) {
            j = ((a - this.f1152n) / this.f1144f) + 1;
        }
        return (j * this.f1144f) + this.f1152n;
    }

    private void m1323c() {
        synchronized (this.f1148j) {
            m1325a(m1321b() - C0552g.m1381a());
        }
    }

    protected void m1325a(long j) {
        synchronized (this.f1148j) {
            if (this.f1153o != null) {
                this.f1153o.removeCallbacks(this);
                this.f1153o.postDelayed(this, j);
            }
        }
    }

    private void m1324d() {
        if (this.f1152n == 0) {
            this.f1152n = this.f1151m.getLong("end_of_interval", C0552g.m1381a() + this.f1144f);
        }
    }

    private void m1322b(long j) {
        this.f1151m.edit().putLong("end_of_interval", j).commit();
        this.f1152n = j;
    }

    protected boolean m1326a() {
        KeyguardManager keyguardManager = (KeyguardManager) this.f1146h.getSystemService("keyguard");
        PowerManager powerManager = (PowerManager) this.f1146h.getSystemService("power");
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.f1146h.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (Process.myPid() == runningAppProcessInfo.pid && runningAppProcessInfo.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode() && powerManager.isScreenOn()) {
                return true;
            }
        }
        return false;
    }
}
