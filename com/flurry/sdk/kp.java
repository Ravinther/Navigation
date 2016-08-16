package com.flurry.sdk;

import android.content.Context;
import java.lang.ref.WeakReference;

public class kp extends jk {
    public WeakReference<Context> f1052a;
    public ko f1053b;
    public C0522a f1054c;
    public long f1055d;

    /* renamed from: com.flurry.sdk.kp.a */
    public enum C0522a {
        CREATE,
        SESSION_ID_CREATED,
        START,
        END,
        FINALIZE
    }

    public kp() {
        super("com.flurry.android.sdk.FlurrySessionEvent");
    }
}
