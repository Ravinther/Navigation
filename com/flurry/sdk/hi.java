package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.FlurryEventRecordStatus;
import java.util.Map;

public class hi implements jt {
    private static final String f560a;
    private ia f561b;
    private ij f562c;
    private ie f563d;

    static {
        f560a = hi.class.getSimpleName();
    }

    public static synchronized hi m500a() {
        hi hiVar;
        synchronized (hi.class) {
            hiVar = (hi) jc.m946a().m952a(hi.class);
        }
        return hiVar;
    }

    public void m509a(Context context) {
        ko.m1178a(im.class);
        this.f561b = new ia();
        this.f562c = new ij();
        this.f563d = new ie();
        m502b(context);
    }

    private void m502b(Context context) {
        if (!lc.m1267a(context, "android.permission.INTERNET")) {
            jq.m1024b(f560a, "Application must declare permission: android.permission.INTERNET");
        }
        if (!lc.m1267a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            jq.m1032e(f560a, "It is highly recommended that the application declare permission: android.permission.ACCESS_NETWORK_STATE");
        }
    }

    public void m513b() {
        if (this.f563d != null) {
            this.f563d.m653c();
            this.f563d = null;
        }
        if (this.f562c != null) {
            this.f562c.m703a();
            this.f562c = null;
        }
        if (this.f561b != null) {
            this.f561b.m597a();
            this.f561b = null;
        }
        ko.m1179b(im.class);
    }

    public ia m516c() {
        return this.f561b;
    }

    public ij m519d() {
        return this.f562c;
    }

    public ie m520e() {
        return this.f563d;
    }

    public void m521f() {
        im h = m503h();
        if (h != null) {
            h.m791a();
        }
    }

    public FlurryEventRecordStatus m504a(String str) {
        im h = m503h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.m789a(str, null, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus m506a(String str, Map<String, String> map) {
        im h = m503h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.m789a(str, (Map) map, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus m505a(String str, String str2, Map<String, String> map) {
        im h = m503h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.m788a(str, str2, (Map) map, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus m512b(String str, String str2, Map<String, String> map) {
        im h = m503h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.m788a(str, io.m816a(str2), (Map) map, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus m508a(String str, boolean z) {
        im h = m503h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.m789a(str, null, z);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus m507a(String str, Map<String, String> map, boolean z) {
        im h = m503h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.m789a(str, (Map) map, z);
        }
        return flurryEventRecordStatus;
    }

    public void m514b(String str) {
        im h = m503h();
        if (h != null) {
            h.m797a(str, null);
        }
    }

    public void m515b(String str, Map<String, String> map) {
        im h = m503h();
        if (h != null) {
            h.m797a(str, (Map) map);
        }
    }

    @Deprecated
    public void m510a(String str, String str2, String str3) {
        StackTraceElement[] stackTraceElementArr;
        Object stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length <= 2) {
            Object obj = stackTrace;
        } else {
            stackTraceElementArr = new StackTraceElement[(stackTrace.length - 2)];
            System.arraycopy(stackTrace, 2, stackTraceElementArr, 0, stackTraceElementArr.length);
        }
        Throwable th = new Throwable(str2);
        th.setStackTrace(stackTraceElementArr);
        im h = m503h();
        if (h != null) {
            h.m796a(str, str2, str3, th);
        }
    }

    public void m511a(String str, String str2, Throwable th) {
        im h = m503h();
        if (h != null) {
            h.m796a(str, str2, th.getClass().getName(), th);
        }
    }

    public void m517c(String str) {
        im h = m503h();
        if (h != null) {
            h.m789a(str, null, false);
        }
    }

    public void m518c(String str, Map<String, String> map) {
        im h = m503h();
        if (h != null) {
            h.m789a(str, (Map) map, false);
        }
    }

    public void m522g() {
        im h = m503h();
        if (h != null) {
            h.m798b();
        }
    }

    private im m503h() {
        return m501a(kq.m1187a().m1204e());
    }

    private im m501a(ko koVar) {
        if (koVar == null) {
            return null;
        }
        return (im) koVar.m1180c(im.class);
    }
}
