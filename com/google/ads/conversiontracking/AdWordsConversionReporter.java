package com.google.ads.conversiontracking;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.google.ads.conversiontracking.C0552g.C0549b;
import com.google.ads.conversiontracking.C0552g.C0550c;
import com.google.ads.conversiontracking.C0552g.C0551d;

public class AdWordsConversionReporter extends GoogleConversionReporter {
    private final Context f1120a;
    private final String f1121b;
    private final String f1122c;
    private final C0551d f1123d;
    private final String f1124e;
    private final String f1125f;
    private final boolean f1126g;

    public AdWordsConversionReporter(Context applicationContext, String conversionId, String label, String value, boolean isRepeatable) {
        this(applicationContext, conversionId, label, value, null, isRepeatable);
    }

    public AdWordsConversionReporter(Context applicationContext, String conversionId, String label, String value, String currencyCode, boolean isRepeatable) {
        C0551d c0551d;
        this.f1120a = applicationContext;
        this.f1121b = conversionId;
        this.f1122c = label;
        this.f1124e = value;
        this.f1125f = currencyCode;
        this.f1126g = isRepeatable;
        if (this instanceof DoubleClickConversionReporter) {
            c0551d = C0551d.DOUBLECLICK_CONVERSION;
        } else {
            c0551d = C0551d.GOOGLE_CONVERSION;
        }
        this.f1123d = c0551d;
    }

    public void report() {
        boolean z = true;
        C0550c c = new C0550c().m1374a(this.f1121b).m1373a(this.f1123d).m1377b(this.f1122c).m1378c(this.f1124e);
        if (this.f1125f != null) {
            c.m1379d(this.f1125f);
        }
        if (this.f1123d == C0551d.GOOGLE_CONVERSION) {
            C0538c a = C0538c.m1320a(this.f1120a);
            a.m1327c(this.f1121b);
            c.m1375a(a.m1328d(this.f1121b));
        }
        if (C0552g.m1399a(this.f1120a, c, this.f1126g)) {
            try {
                if (this.f1123d == C0551d.GOOGLE_CONVERSION) {
                    c.m1372a(C0552g.m1383a(this.f1120a, this.f1121b));
                } else {
                    z = false;
                }
                m1317a(this.f1120a, c, true, this.f1126g, z);
            } catch (Throwable e) {
                Log.e("GoogleConversionReporter", "Error sending ping", e);
            }
        }
    }

    public static boolean registerReferrer(Context applicationContext, Uri clickUri) {
        boolean z = false;
        if (clickUri == null) {
            Log.e("GoogleConversionReporter", "Failed to register referrer from a null click url");
        } else {
            String valueOf = String.valueOf(clickUri);
            Log.i("GoogleConversionReporter", new StringBuilder(String.valueOf(valueOf).length() + 13).append("Registering: ").append(valueOf).toString());
            C0549b a = C0552g.m1384a(clickUri);
            if (a == null) {
                valueOf = String.valueOf(clickUri);
                Log.w("GoogleConversionReporter", new StringBuilder(String.valueOf(valueOf).length() + 31).append("Failed to parse referrer from: ").append(valueOf).toString());
            } else {
                z = C0552g.m1398a(applicationContext, a);
                if (z) {
                    valueOf = String.valueOf(clickUri);
                    Log.i("GoogleConversionReporter", new StringBuilder(String.valueOf(valueOf).length() + 25).append("Successfully registered: ").append(valueOf).toString());
                } else {
                    valueOf = String.valueOf(clickUri);
                    Log.w("GoogleConversionReporter", new StringBuilder(String.valueOf(valueOf).length() + 20).append("Failed to register: ").append(valueOf).toString());
                }
            }
        }
        return z;
    }

    public static void reportWithConversionId(Context applicationContext, String conversionId, String label, String value, boolean isRepeatable) {
        new AdWordsConversionReporter(applicationContext, conversionId, label, value, isRepeatable).report();
    }
}
