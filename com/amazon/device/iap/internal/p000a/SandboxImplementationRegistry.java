package com.amazon.device.iap.internal.p000a;

import com.amazon.device.iap.internal.ImplementationRegistry;
import com.amazon.device.iap.internal.LogHandler;
import com.amazon.device.iap.internal.RequestHandler;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.amazon.device.iap.internal.a.d */
public final class SandboxImplementationRegistry implements ImplementationRegistry {
    private static final Map<Class, Class> f10a;

    static {
        f10a = new HashMap();
        f10a.put(RequestHandler.class, SandboxRequestHandler.class);
        f10a.put(LogHandler.class, SandboxLogHandler.class);
    }

    public <T> Class<T> m17a(Class<T> cls) {
        return (Class) f10a.get(cls);
    }
}
