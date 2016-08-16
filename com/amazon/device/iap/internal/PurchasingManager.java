package com.amazon.device.iap.internal;

import android.content.Context;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.internal.util.Logger;
import com.amazon.device.iap.internal.util.Validator;
import com.amazon.device.iap.model.RequestId;

/* renamed from: com.amazon.device.iap.internal.d */
public class PurchasingManager {
    private static String f45a;
    private static PurchasingManager f46b;
    private final RequestHandler f47c;
    private Context f48d;
    private PurchasingListener f49e;

    static {
        f45a = PurchasingManager.class.getSimpleName();
        f46b = new PurchasingManager();
    }

    private PurchasingManager() {
        this.f47c = ImplementationFactory.m57b();
    }

    public PurchasingListener m49a() {
        return this.f49e;
    }

    public Context m53b() {
        return this.f48d;
    }

    private void m48e() {
        if (this.f49e == null) {
            throw new IllegalStateException("You must register a PurchasingListener before invoking this operation");
        }
    }

    public void m52a(Context context, PurchasingListener purchasingListener) {
        Logger.m62a(f45a, "PurchasingListener registered: " + purchasingListener);
        Logger.m62a(f45a, "PurchasingListener Context: " + context);
        if (purchasingListener == null || context == null) {
            throw new IllegalArgumentException("Neither PurchasingListener or its Context can be null");
        }
        this.f48d = context.getApplicationContext();
        this.f49e = purchasingListener;
    }

    public RequestId m54c() {
        m48e();
        RequestId requestId = new RequestId();
        this.f47c.m9a(requestId);
        return requestId;
    }

    public RequestId m50a(String str) {
        Validator.m60a(str, "sku");
        m48e();
        RequestId requestId = new RequestId();
        this.f47c.m10a(requestId, str);
        return requestId;
    }

    public RequestId m51a(boolean z) {
        m48e();
        RequestId requestId = new RequestId();
        this.f47c.m11a(requestId, z);
        return requestId;
    }

    public static PurchasingManager m47d() {
        return f46b;
    }
}
