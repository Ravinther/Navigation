package com.google.ads.conversiontracking;

import android.content.Context;
import android.util.Log;
import com.google.ads.conversiontracking.C0552g.C0550c;

public abstract class GoogleConversionReporter {

    /* renamed from: com.google.ads.conversiontracking.GoogleConversionReporter.1 */
    class C05321 implements Runnable {
        final /* synthetic */ Context f1127a;
        final /* synthetic */ C0550c f1128b;
        final /* synthetic */ boolean f1129c;
        final /* synthetic */ boolean f1130d;
        final /* synthetic */ boolean f1131e;
        final /* synthetic */ GoogleConversionReporter f1132f;

        C05321(GoogleConversionReporter googleConversionReporter, Context context, C0550c c0550c, boolean z, boolean z2, boolean z3) {
            this.f1132f = googleConversionReporter;
            this.f1127a = context;
            this.f1128b = c0550c;
            this.f1129c = z;
            this.f1130d = z2;
            this.f1131e = z3;
        }

        public void run() {
            try {
                String a = C0552g.m1386a(this.f1127a, this.f1128b);
                if (a != null) {
                    C0552g.m1382a(this.f1127a).m1341a(a, this.f1128b, this.f1129c, this.f1130d, this.f1131e);
                }
            } catch (Throwable e) {
                Log.e("GoogleConversionReporter", "Error sending ping", e);
            }
        }
    }

    protected void m1317a(Context context, C0550c c0550c, boolean z, boolean z2, boolean z3) {
        new Thread(new C05321(this, context, c0550c, z, z2, z3)).start();
    }
}
