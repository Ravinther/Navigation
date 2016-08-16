package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.flurry.sdk.jf.C0471a;

public class jg {
    private static jg f917a;
    private static final String f918b;
    private Object f919c;

    /* renamed from: com.flurry.sdk.jg.1 */
    class C04721 implements ActivityLifecycleCallbacks {
        final /* synthetic */ jg f916a;

        C04721(jg jgVar) {
            this.f916a = jgVar;
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
            jq.m1016a(3, jg.f918b, "onActivityCreated for activity:" + activity);
            m973a(activity, C0471a.kCreated);
        }

        public void onActivityStarted(Activity activity) {
            jq.m1016a(3, jg.f918b, "onActivityStarted for activity:" + activity);
            m973a(activity, C0471a.kStarted);
        }

        public void onActivityResumed(Activity activity) {
            jq.m1016a(3, jg.f918b, "onActivityResumed for activity:" + activity);
            m973a(activity, C0471a.kResumed);
        }

        public void onActivityPaused(Activity activity) {
            jq.m1016a(3, jg.f918b, "onActivityPaused for activity:" + activity);
            m973a(activity, C0471a.kPaused);
        }

        public void onActivityStopped(Activity activity) {
            jq.m1016a(3, jg.f918b, "onActivityStopped for activity:" + activity);
            m973a(activity, C0471a.kStopped);
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            jq.m1016a(3, jg.f918b, "onActivitySaveInstanceState for activity:" + activity);
            m973a(activity, C0471a.kSaveState);
        }

        public void onActivityDestroyed(Activity activity) {
            jq.m1016a(3, jg.f918b, "onActivityDestroyed for activity:" + activity);
            m973a(activity, C0471a.kDestroyed);
        }

        protected void m973a(Activity activity, C0471a c0471a) {
            jf jfVar = new jf();
            jfVar.f914a = activity;
            jfVar.f915b = c0471a;
            jfVar.m868b();
        }
    }

    public static synchronized jg m974a() {
        jg jgVar;
        synchronized (jg.class) {
            if (f917a == null) {
                f917a = new jg();
            }
            jgVar = f917a;
        }
        return jgVar;
    }

    public static synchronized void m975b() {
        synchronized (jg.class) {
            if (f917a != null) {
                f917a.m978f();
            }
            f917a = null;
        }
    }

    static {
        f918b = jg.class.getSimpleName();
    }

    private jg() {
        m977e();
    }

    public boolean m979c() {
        return this.f919c != null;
    }

    @TargetApi(14)
    private void m977e() {
        if (VERSION.SDK_INT >= 14 && this.f919c == null) {
            Context c = jc.m946a().m957c();
            if (c instanceof Application) {
                this.f919c = new C04721(this);
                ((Application) c).registerActivityLifecycleCallbacks((ActivityLifecycleCallbacks) this.f919c);
            }
        }
    }

    @TargetApi(14)
    private void m978f() {
        if (VERSION.SDK_INT >= 14 && this.f919c != null) {
            Context c = jc.m946a().m957c();
            if (c instanceof Application) {
                ((Application) c).unregisterActivityLifecycleCallbacks((ActivityLifecycleCallbacks) this.f919c);
                this.f919c = null;
            }
        }
    }
}
