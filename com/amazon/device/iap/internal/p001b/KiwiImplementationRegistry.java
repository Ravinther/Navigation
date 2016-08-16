package com.amazon.device.iap.internal.p001b;

import com.amazon.device.iap.internal.ImplementationRegistry;
import com.amazon.device.iap.internal.LogHandler;
import com.amazon.device.iap.internal.RequestHandler;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.amazon.device.iap.internal.b.g */
public final class KiwiImplementationRegistry implements ImplementationRegistry {
    private static final Map<Class, Class> f42a;

    static {
        f42a = new HashMap();
        f42a.put(RequestHandler.class, KiwiRequestHandler.class);
        f42a.put(LogHandler.class, KiwiLogHandler.class);
    }

    public <T> Class<T> m43a(Class<T> cls) {
        return (Class) f42a.get(cls);
    }
}
