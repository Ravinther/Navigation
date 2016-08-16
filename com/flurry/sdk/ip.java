package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.iw.C0467a;

public class ip implements jt {
    private static final String f811a;

    public static synchronized ip m818a() {
        ip ipVar;
        synchronized (ip.class) {
            ipVar = (ip) jc.m946a().m952a(ip.class);
        }
        return ipVar;
    }

    static {
        f811a = ip.class.getSimpleName();
    }

    public void m821a(Context context) {
        ko.m1178a(jb.class);
        jm.m997a();
        kx.m1220a();
        kt.m1217a();
        je.m969a();
        iw.m897a();
        iq.m834a();
        ix.m905a();
        iu.m877a();
        iq.m834a();
        iz.m909a();
        it.m869a();
        ja.m928a();
    }

    public void m822b() {
        ja.m929b();
        it.m870b();
        iz.m910b();
        iq.m841b();
        iu.m881b();
        ix.m906b();
        iq.m841b();
        iw.m899b();
        je.m970b();
        kt.m1218b();
        kx.m1221b();
        jm.m998b();
        ko.m1179b(jb.class);
    }

    public String m823c() {
        jb j = m820j();
        if (j != null) {
            return j.m939b();
        }
        return null;
    }

    public long m824d() {
        jb j = m820j();
        if (j != null) {
            return j.m941c();
        }
        return 0;
    }

    public long m825e() {
        jb j = m820j();
        if (j != null) {
            return j.m942d();
        }
        return 0;
    }

    public long m826f() {
        jb j = m820j();
        if (j != null) {
            return j.m943e();
        }
        return -1;
    }

    public long m827g() {
        jb j = m820j();
        if (j != null) {
            return j.m945g();
        }
        return 0;
    }

    public long m828h() {
        jb j = m820j();
        if (j != null) {
            return j.m944f();
        }
        return 0;
    }

    public C0467a m829i() {
        return iw.m897a().m904d();
    }

    private jb m820j() {
        return m819a(kq.m1187a().m1204e());
    }

    private jb m819a(ko koVar) {
        if (koVar == null) {
            return null;
        }
        return (jb) koVar.m1180c(jb.class);
    }
}
