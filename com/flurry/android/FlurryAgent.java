package com.flurry.android;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.hi;
import com.flurry.sdk.ip;
import com.flurry.sdk.jc;
import com.flurry.sdk.jd;
import com.flurry.sdk.je;
import com.flurry.sdk.jk;
import com.flurry.sdk.jl;
import com.flurry.sdk.jm;
import com.flurry.sdk.jq;
import com.flurry.sdk.kp;
import com.flurry.sdk.kp.C0522a;
import com.flurry.sdk.kq;
import com.flurry.sdk.kt;
import com.flurry.sdk.lc;
import com.flurry.sdk.lj;
import java.util.Date;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

public final class FlurryAgent {
    private static final String f532a;
    private static FlurryAgentListener f533b;
    private static final jl<kp> f534c;

    /* renamed from: com.flurry.android.FlurryAgent.1 */
    static class C04081 implements jl<kp> {

        /* renamed from: com.flurry.android.FlurryAgent.1.1 */
        class C04071 implements Runnable {
            final /* synthetic */ kp f529a;
            final /* synthetic */ C04081 f530b;

            C04071(C04081 c04081, kp kpVar) {
                this.f530b = c04081;
                this.f529a = kpVar;
            }

            public void run() {
                switch (C04092.f531a[this.f529a.f1054c.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        if (FlurryAgent.f533b != null) {
                            FlurryAgent.f533b.onSessionStarted();
                        }
                    default:
                }
            }
        }

        C04081() {
        }

        public /* synthetic */ void m476a(jk jkVar) {
            m477a((kp) jkVar);
        }

        public void m477a(kp kpVar) {
            jc.m946a().m953a(new C04071(this, kpVar));
        }
    }

    /* renamed from: com.flurry.android.FlurryAgent.2 */
    static /* synthetic */ class C04092 {
        static final /* synthetic */ int[] f531a;

        static {
            f531a = new int[C0522a.values().length];
            try {
                f531a[C0522a.SESSION_ID_CREATED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static {
        f532a = FlurryAgent.class.getSimpleName();
        f534c = new C04081();
    }

    private FlurryAgent() {
    }

    public static int getAgentVersion() {
        return jd.m963a();
    }

    public static String getReleaseVersion() {
        return jd.m968f();
    }

    public static void setFlurryAgentListener(FlurryAgentListener flurryAgentListener) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (flurryAgentListener == null) {
            jq.m1024b(f532a, "Listener cannot be null");
            jm.m997a().m1004b("com.flurry.android.sdk.FlurrySessionEvent", f534c);
        } else {
            f533b = flurryAgentListener;
            jm.m997a().m1002a("com.flurry.android.sdk.FlurrySessionEvent", f534c);
        }
    }

    public static void setLogEnabled(boolean z) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (z) {
            jq.m1021b();
        } else {
            jq.m1014a();
        }
    }

    public static void setLogLevel(int i) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            jq.m1015a(i);
        }
    }

    public static void setVersionName(String str) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String versionName passed to setVersionName was null.");
        } else {
            kt.m1217a().m1214a("VersionName", (Object) str);
        }
    }

    public static void setReportLocation(boolean z) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            kt.m1217a().m1214a("ReportLocation", (Object) Boolean.valueOf(z));
        }
    }

    public static void setLocation(float f, float f2) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
            return;
        }
        Location location = new Location("Explicit");
        location.setLatitude((double) f);
        location.setLongitude((double) f2);
        kt.m1217a().m1214a("ExplicitLocation", (Object) location);
    }

    public static void clearLocation() {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            kt.m1217a().m1214a("ExplicitLocation", null);
        }
    }

    public static void setContinueSessionMillis(long j) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (j < 5000) {
            jq.m1024b(f532a, "Invalid time set for session resumption: " + j);
        } else {
            kt.m1217a().m1214a("ContinueSessionMillis", (Object) Long.valueOf(j));
        }
    }

    public static void setLogEvents(boolean z) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            kt.m1217a().m1214a("LogEvents", (Object) Boolean.valueOf(z));
        }
    }

    public static void setCaptureUncaughtExceptions(boolean z) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            kt.m1217a().m1214a("CaptureUncaughtExceptions", (Object) Boolean.valueOf(z));
        }
    }

    public static void addOrigin(String str, String str2) {
        addOrigin(str, str2, null);
    }

    public static void addOrigin(String str, String str2, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("originName not specified");
        } else if (str2 == null || str2.length() == 0) {
            throw new IllegalArgumentException("originVersion not specified");
        } else {
            try {
                je.m969a().m971a(str, str2, map);
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
    }

    public static void setPulseEnabled(boolean z) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            kt.m1217a().m1214a("ProtonEnabled", (Object) Boolean.valueOf(z));
        }
    }

    public static synchronized void init(Context context, String str) {
        synchronized (FlurryAgent.class) {
            if (VERSION.SDK_INT < 10) {
                jq.m1024b(f532a, "Device SDK Version older than 10");
            } else {
                if (context == null) {
                    throw new NullPointerException("Null context");
                }
                if (str != null) {
                    if (str.length() != 0) {
                        try {
                            lj.m1313a();
                            jc.m948a(context, str);
                        } catch (Throwable th) {
                            jq.m1019a(f532a, "", th);
                        }
                    }
                }
                throw new IllegalArgumentException("apiKey not specified");
            }
        }
    }

    @Deprecated
    public static void onStartSession(Context context, String str) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Api key not specified");
        } else if (jc.m946a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            try {
                kq.m1187a().m1199b(context);
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
    }

    public static void onStartSession(Context context) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (jc.m946a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            try {
                kq.m1187a().m1199b(context);
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
    }

    public static void onEndSession(Context context) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (jc.m946a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before ending a session");
        } else {
            try {
                kq.m1187a().m1201c(context);
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
    }

    public static boolean isSessionActive() {
        boolean z = false;
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            try {
                z = kq.m1187a().m1205f();
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
        return z;
    }

    public static String getSessionId() {
        String str = null;
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else {
            try {
                str = ip.m818a().m823c();
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
        return str;
    }

    public static FlurryEventRecordStatus logEvent(String str) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = hi.m500a().m504a(str);
            } catch (Throwable th) {
                jq.m1019a(f532a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to logEvent was null.");
        } else if (map == null) {
            jq.m1024b(f532a, "String parameters passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = hi.m500a().m506a(str, (Map) map);
            } catch (Throwable th) {
                jq.m1019a(f532a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static FlurryEventRecordStatus logEvent(FlurrySyndicationEventName flurrySyndicationEventName, String str, Map<String, String> map) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (flurrySyndicationEventName == null) {
            jq.m1024b(f532a, "String eventName passed to logEvent was null.");
        } else if (TextUtils.isEmpty(str)) {
            jq.m1024b(f532a, "String syndicationId passed to logEvent was null or empty.");
        } else if (map == null) {
            jq.m1024b(f532a, "String parameters passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = hi.m500a().m505a(flurrySyndicationEventName.toString(), str, (Map) map);
            } catch (Throwable th) {
                jq.m1019a(f532a, "Failed to log event: " + flurrySyndicationEventName.toString(), th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static FlurryEventRecordStatus logEvent(String str, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = hi.m500a().m508a(str, z);
            } catch (Throwable th) {
                jq.m1019a(f532a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to logEvent was null.");
        } else if (map == null) {
            jq.m1024b(f532a, "String parameters passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = hi.m500a().m507a(str, (Map) map, z);
            } catch (Throwable th) {
                jq.m1019a(f532a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static void endTimedEvent(String str) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                hi.m500a().m514b(str);
            } catch (Throwable th) {
                jq.m1019a(f532a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    public static void endTimedEvent(String str, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to endTimedEvent was null.");
        } else if (map == null) {
            jq.m1024b(f532a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                hi.m500a().m515b(str, map);
            } catch (Throwable th) {
                jq.m1019a(f532a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    @Deprecated
    public static void onError(String str, String str2, String str3) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            jq.m1024b(f532a, "String message passed to onError was null.");
        } else if (str3 == null) {
            jq.m1024b(f532a, "String errorClass passed to onError was null.");
        } else {
            try {
                hi.m500a().m510a(str, str2, str3);
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
    }

    public static void onError(String str, String str2, Throwable th) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            jq.m1024b(f532a, "String message passed to onError was null.");
        } else if (th == null) {
            jq.m1024b(f532a, "Throwable passed to onError was null.");
        } else {
            try {
                hi.m500a().m511a(str, str2, th);
            } catch (Throwable th2) {
                jq.m1019a(f532a, "", th2);
            }
        }
    }

    @Deprecated
    public static void onEvent(String str) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to onEvent was null.");
        } else {
            try {
                hi.m500a().m517c(str);
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
    }

    @Deprecated
    public static void onEvent(String str, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String eventId passed to onEvent was null.");
        } else if (map == null) {
            jq.m1024b(f532a, "Parameters Map passed to onEvent was null.");
        } else {
            try {
                hi.m500a().m518c(str, map);
            } catch (Throwable th) {
                jq.m1019a(f532a, "", th);
            }
        }
    }

    public static void onPageView() {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
            return;
        }
        try {
            hi.m500a().m522g();
        } catch (Throwable th) {
            jq.m1019a(f532a, "", th);
        }
    }

    @Deprecated
    public static void setLocationCriteria(Criteria criteria) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        }
    }

    public static void setAge(int i) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (i > 0 && i < 110) {
            kt.m1217a().m1214a("Age", (Object) Long.valueOf(new Date(new Date(System.currentTimeMillis() - (((long) i) * 31449600000L)).getYear(), 1, 1).getTime()));
        }
    }

    public static void setGender(byte b) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
            return;
        }
        switch (b) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSMULTILINE /*1*/:
                kt.m1217a().m1214a("Gender", (Object) Byte.valueOf(b));
            default:
                kt.m1217a().m1214a("Gender", (Object) Byte.valueOf((byte) -1));
        }
    }

    public static void setUserId(String str) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f532a, "Device SDK Version older than 10");
        } else if (str == null) {
            jq.m1024b(f532a, "String userId passed to setUserId was null.");
        } else {
            kt.m1217a().m1214a("UserId", (Object) lc.m1270b(str));
        }
    }
}
