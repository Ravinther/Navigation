package com.flurry.sdk;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.flurry.sdk.ku.C0426a;
import loquendo.tts.engine.TTSConst;

public class iu implements C0426a {
    private static iu f838a;
    private static final String f839b;
    private final int f840c;
    private final long f841d;
    private final long f842e;
    private final long f843f;
    private boolean f844g;
    private Location f845h;
    private long f846i;
    private LocationManager f847j;
    private C0466a f848k;
    private Location f849l;
    private boolean f850m;
    private int f851n;
    private jl<kw> f852o;

    /* renamed from: com.flurry.sdk.iu.1 */
    class C04651 implements jl<kw> {
        final /* synthetic */ iu f836a;

        C04651(iu iuVar) {
            this.f836a = iuVar;
        }

        public void m874a(kw kwVar) {
            if (this.f836a.f846i > 0 && this.f836a.f846i < System.currentTimeMillis()) {
                jq.m1016a(4, iu.f839b, "No location received in 90 seconds , stopping LocationManager");
                this.f836a.m887h();
            }
        }
    }

    /* renamed from: com.flurry.sdk.iu.a */
    class C0466a implements LocationListener {
        final /* synthetic */ iu f837a;

        public C0466a(iu iuVar) {
            this.f837a = iuVar;
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onProviderDisabled(String str) {
        }

        public void onLocationChanged(Location location) {
            if (location != null) {
                this.f837a.f849l = location;
            }
            if (iu.m884c(this.f837a) >= 3) {
                jq.m1016a(4, iu.f839b, "Max location reports reached, stopping");
                this.f837a.m887h();
            }
        }
    }

    static /* synthetic */ int m884c(iu iuVar) {
        int i = iuVar.f851n + 1;
        iuVar.f851n = i;
        return i;
    }

    public static synchronized iu m877a() {
        iu iuVar;
        synchronized (iu.class) {
            if (f838a == null) {
                f838a = new iu();
            }
            iuVar = f838a;
        }
        return iuVar;
    }

    public static void m881b() {
        if (f838a != null) {
            kt.m1217a().m1215b("ReportLocation", f838a);
            kt.m1217a().m1215b("ExplicitLocation", f838a);
        }
        f838a = null;
    }

    static {
        f839b = iu.class.getSimpleName();
    }

    private iu() {
        this.f840c = 3;
        this.f841d = 10000;
        this.f842e = 90000;
        this.f843f = 0;
        this.f846i = 0;
        this.f850m = false;
        this.f851n = 0;
        this.f852o = new C04651(this);
        this.f847j = (LocationManager) jc.m946a().m957c().getSystemService("location");
        this.f848k = new C0466a(this);
        ku a = kt.m1217a();
        this.f844g = ((Boolean) a.m1212a("ReportLocation")).booleanValue();
        a.m1213a("ReportLocation", (C0426a) this);
        jq.m1016a(4, f839b, "initSettings, ReportLocation = " + this.f844g);
        this.f845h = (Location) a.m1212a("ExplicitLocation");
        a.m1213a("ExplicitLocation", (C0426a) this);
        jq.m1016a(4, f839b, "initSettings, ExplicitLocation = " + this.f845h);
    }

    public synchronized void m893c() {
        jq.m1016a(4, f839b, "Location update requested");
        if (this.f851n < 3) {
            m886g();
        }
    }

    public synchronized void m894d() {
        jq.m1016a(4, f839b, "Stop update location requested");
        m887h();
    }

    public Location m895e() {
        Location location = null;
        if (this.f845h != null) {
            return this.f845h;
        }
        if (this.f844g) {
            Context c = jc.m946a().m957c();
            if (!m879a(c) && !m883b(c)) {
                return null;
            }
            String i = m879a(c) ? m888i() : m883b(c) ? m889j() : null;
            if (i != null) {
                location = m880b(i);
                if (location != null) {
                    this.f849l = location;
                }
                location = this.f849l;
            }
        }
        jq.m1016a(4, f839b, "getLocation() = " + location);
        return location;
    }

    private void m886g() {
        if (!this.f850m && this.f844g && this.f845h == null) {
            Context c = jc.m946a().m957c();
            if (c.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 || c.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                this.f851n = 0;
                String str = null;
                if (m879a(c)) {
                    str = m888i();
                } else if (m883b(c)) {
                    str = m889j();
                }
                m878a(str);
                this.f849l = m880b(str);
                this.f846i = System.currentTimeMillis() + 90000;
                m890k();
                this.f850m = true;
                jq.m1016a(4, f839b, "LocationProvider started");
            }
        }
    }

    private void m887h() {
        if (this.f850m) {
            this.f847j.removeUpdates(this.f848k);
            this.f851n = 0;
            this.f846i = 0;
            m891l();
            this.f850m = false;
            jq.m1016a(4, f839b, "LocationProvider stopped");
        }
    }

    private String m888i() {
        return "passive";
    }

    private String m889j() {
        return "network";
    }

    private boolean m879a(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    private boolean m883b(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    private void m878a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.f847j.requestLocationUpdates(str, 10000, 0.0f, this.f848k, Looper.getMainLooper());
        }
    }

    private Location m880b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.f847j.getLastKnownLocation(str);
    }

    private void m890k() {
        jq.m1016a(4, f839b, "Register location timer");
        kx.m1220a().m1222a(this.f852o);
    }

    private void m891l() {
        jq.m1016a(4, f839b, "Unregister location timer");
        kx.m1220a().m1223b(this.f852o);
    }

    public void m892a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -864112343:
                if (str.equals("ReportLocation")) {
                    obj2 = null;
                    break;
                }
                break;
            case -300729815:
                if (str.equals("ExplicitLocation")) {
                    obj2 = 1;
                    break;
                }
                break;
        }
        switch (obj2) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.f844g = ((Boolean) obj).booleanValue();
                jq.m1016a(4, f839b, "onSettingUpdate, ReportLocation = " + this.f844g);
            case TTSConst.TTSMULTILINE /*1*/:
                this.f845h = (Location) obj;
                jq.m1016a(4, f839b, "onSettingUpdate, ExplicitLocation = " + this.f845h);
            default:
                jq.m1016a(6, f839b, "LocationProvider internal error! Had to be LocationCriteria, ReportLocation or ExplicitLocation key.");
        }
    }
}
