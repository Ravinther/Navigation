package com.google.ads.conversiontracking;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.ads.conversiontracking.C0564q.C0566a;
import java.io.IOException;

/* renamed from: com.google.ads.conversiontracking.i */
public final class C0556i {

    /* renamed from: com.google.ads.conversiontracking.i.a */
    public static final class C0555a {
        private final String f1213a;
        private final boolean f1214b;

        public C0555a(String str, boolean z) {
            this.f1213a = str;
            this.f1214b = z;
        }

        public String m1408a() {
            return this.f1213a;
        }

        public boolean m1409b() {
            return this.f1214b;
        }
    }

    public static C0555a m1410a(Context context) throws IOException, IllegalStateException, C0557j, C0559k {
        C0563p.m1430a("Calling this from your main thread can lead to deadlock");
        return C0556i.m1411a(context, C0556i.m1412b(context));
    }

    static C0555a m1411a(Context context, C0561n c0561n) throws IOException {
        try {
            C0564q a = C0566a.m1439a(c0561n.m1424a());
            C0555a c0555a = new C0555a(a.m1431a(), a.m1434a(true));
            try {
                context.unbindService(c0561n);
            } catch (Throwable e) {
                Log.i("AdvertisingIdClient", "getAdvertisingIdInfo unbindService failed.", e);
            }
            return c0555a;
        } catch (Throwable e2) {
            Log.i("AdvertisingIdClient", "GMS remote exception ", e2);
            throw new IOException("Remote exception");
        } catch (InterruptedException e3) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            try {
                context.unbindService(c0561n);
            } catch (Throwable e4) {
                Log.i("AdvertisingIdClient", "getAdvertisingIdInfo unbindService failed.", e4);
            }
        }
    }

    private static C0561n m1412b(Context context) throws IOException, C0557j, C0559k {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            try {
                C0560l.m1421b(context);
                Object c0561n = new C0561n();
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                if (context.bindService(intent, c0561n, 1)) {
                    return c0561n;
                }
                throw new IOException("Connection failure");
            } catch (Throwable e) {
                throw new IOException(e);
            }
        } catch (NameNotFoundException e2) {
            throw new C0557j(9);
        }
    }
}
