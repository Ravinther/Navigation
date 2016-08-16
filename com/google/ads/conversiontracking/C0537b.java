package com.google.ads.conversiontracking;

import android.content.Context;
import android.util.Log;
import com.google.ads.conversiontracking.C0552g.C0550c;

/* renamed from: com.google.ads.conversiontracking.b */
public class C0537b extends GoogleConversionReporter {
    private Context f1138a;

    public C0537b(Context context) {
        this.f1138a = context;
    }

    public void m1319a(String str, long j) {
        try {
            m1317a(this.f1138a, new C0550c().m1374a(str).m1375a(true).m1376b().m1371a(j), false, true, true);
        } catch (Throwable e) {
            Log.e("GoogleConversionReporter", "Error sending ping", e);
        }
    }
}
