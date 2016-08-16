package com.amazon.device.iap.internal.p001b;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.amazon.device.iap.internal.b.h */
public class KiwiRequestContext {
    static final /* synthetic */ boolean f43b;
    public final Map<String, Object> f44a;

    static {
        f43b = !KiwiRequestContext.class.desiredAssertionStatus();
    }

    public KiwiRequestContext() {
        this.f44a = new HashMap();
    }

    public Object m45a(String str) {
        return this.f44a.get(str);
    }

    public void m46a(String str, Object obj) {
        this.f44a.put(str, obj);
    }

    public Object m44a() {
        return this.f44a.get("RESPONSE");
    }
}
