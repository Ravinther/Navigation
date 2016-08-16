package com.amazon.device.iap.internal.p001b.p002b;

import com.amazon.device.iap.internal.p001b.KiwiCommand;
import com.amazon.device.iap.internal.p001b.KiwiRequest;
import com.amazon.device.iap.model.RequestId;

/* renamed from: com.amazon.device.iap.internal.b.b.d */
public final class PurchaseRequest extends KiwiRequest {
    public PurchaseRequest(RequestId requestId, String str) {
        super(requestId);
        KiwiCommand purchaseItemCommandV2 = new PurchaseItemCommandV2(this, str);
        purchaseItemCommandV2.m22b(new PurchaseItemCommandV1(this, str));
        m26a(purchaseItemCommandV2);
    }

    public void m32a() {
    }
}
