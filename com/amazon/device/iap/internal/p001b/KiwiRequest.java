package com.amazon.device.iap.internal.p001b;

import android.content.Context;
import android.os.Handler;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.internal.PurchasingManager;
import com.amazon.device.iap.internal.util.Logger;
import com.amazon.device.iap.internal.util.Validator;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserDataResponse;

/* renamed from: com.amazon.device.iap.internal.b.e */
public class KiwiRequest {
    private static final String f24a;
    private final RequestId f25b;
    private final KiwiRequestContext f26c;
    private KiwiCommand f27d;

    /* renamed from: com.amazon.device.iap.internal.b.e.1 */
    class KiwiRequest implements Runnable {
        final /* synthetic */ Object f32a;
        final /* synthetic */ PurchasingListener f33b;
        final /* synthetic */ KiwiCommand f34c;
        final /* synthetic */ KiwiRequest f35d;

        KiwiRequest(KiwiRequest kiwiRequest, Object obj, PurchasingListener purchasingListener, KiwiCommand kiwiCommand) {
            this.f35d = kiwiRequest;
            this.f32a = obj;
            this.f33b = purchasingListener;
            this.f34c = kiwiCommand;
        }

        public void run() {
            this.f35d.m30d().m46a("notifyListenerResult", Boolean.FALSE);
            try {
                if (this.f32a instanceof ProductDataResponse) {
                    this.f33b.onProductDataResponse((ProductDataResponse) this.f32a);
                } else if (this.f32a instanceof UserDataResponse) {
                    this.f33b.onUserDataResponse((UserDataResponse) this.f32a);
                } else if (this.f32a instanceof PurchaseUpdatesResponse) {
                    this.f33b.onPurchaseUpdatesResponse((PurchaseUpdatesResponse) this.f32a);
                } else if (this.f32a instanceof PurchaseResponse) {
                    this.f33b.onPurchaseResponse((PurchaseResponse) this.f32a);
                } else {
                    Logger.m64b(KiwiRequest.f24a, "Unknown response type:" + this.f32a.getClass().getName());
                }
                this.f35d.m30d().m46a("notifyListenerResult", Boolean.TRUE);
            } catch (Throwable th) {
                Logger.m64b(KiwiRequest.f24a, "Error in sendResponse: " + th);
            }
            if (this.f34c != null) {
                this.f34c.m18a(true);
                this.f34c.a_();
            }
        }
    }

    static {
        f24a = KiwiRequest.class.getSimpleName();
    }

    public KiwiRequest(RequestId requestId) {
        this.f25b = requestId;
        this.f26c = new KiwiRequestContext();
        this.f27d = null;
    }

    protected void m26a(KiwiCommand kiwiCommand) {
        this.f27d = kiwiCommand;
    }

    protected void m27a(Object obj) {
        m28a(obj, null);
    }

    protected void m28a(Object obj, KiwiCommand kiwiCommand) {
        Validator.m60a(obj, "response");
        Context b = PurchasingManager.m47d().m53b();
        PurchasingListener a = PurchasingManager.m47d().m49a();
        if (b == null || a == null) {
            Logger.m62a(f24a, "PurchasingListener is not set. Dropping response: " + obj);
            return;
        }
        new Handler(b.getMainLooper()).post(new KiwiRequest(this, obj, a, kiwiCommand));
    }

    public RequestId m29c() {
        return this.f25b;
    }

    public KiwiRequestContext m30d() {
        return this.f26c;
    }

    public void m31e() {
        if (this.f27d != null) {
            this.f27d.a_();
        } else {
            m25a();
        }
    }

    public void m25a() {
    }
}
