package com.flurry.sdk;

import android.content.Context;
import java.io.File;
import java.util.List;
import java.util.Map;

public class hj {
    private static final String f564b;
    boolean f565a;
    private final hk f566c;
    private final File f567d;
    private String f568e;

    static {
        f564b = hj.class.getSimpleName();
    }

    public hj() {
        this(jc.m946a().m957c());
    }

    public hj(Context context) {
        this.f566c = new hk();
        this.f567d = context.getFileStreamPath(".flurryinstallreceiver.");
        jq.m1016a(3, f564b, "Referrer file name if it exists:  " + this.f567d);
    }

    public synchronized void m527a() {
        this.f567d.delete();
        this.f568e = null;
        this.f565a = true;
    }

    public synchronized Map<String, List<String>> m526a(boolean z) {
        Map<String, List<String>> a;
        m523b();
        a = this.f566c.m529a(this.f568e);
        if (z) {
            m527a();
        }
        return a;
    }

    public synchronized void m528a(String str) {
        this.f565a = true;
        m524b(str);
        m525c();
    }

    private void m524b(String str) {
        if (str != null) {
            this.f568e = str;
        }
    }

    private void m523b() {
        if (!this.f565a) {
            this.f565a = true;
            jq.m1016a(4, f564b, "Loading referrer info from file: " + this.f567d.getAbsolutePath());
            String c = lb.m1258c(this.f567d);
            jq.m1018a(f564b, "Referrer file contents: " + c);
            m524b(c);
        }
    }

    private void m525c() {
        lb.m1254a(this.f567d, this.f568e);
    }
}
