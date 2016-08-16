package com.amazon.device.iap.internal.p001b.p005g;

import com.amazon.device.iap.internal.model.InternalFulfillmentResult;
import com.amazon.device.iap.internal.p001b.KiwiCommand;
import com.amazon.device.iap.internal.p001b.KiwiRequest;
import java.util.Set;

/* renamed from: com.amazon.device.iap.internal.b.g.a */
public final class PurchaseFulfilledCommandV2 extends KiwiCommand {
    protected final Set<String> f40a;
    protected final String f41b;

    public PurchaseFulfilledCommandV2(KiwiRequest kiwiRequest, Set<String> set, String str) {
        super(kiwiRequest, "purchase_fulfilled", "2.0");
        this.f40a = set;
        this.f41b = str;
        m23b(false);
        m20a("receiptIds", this.f40a);
        m20a("fulfillmentStatus", this.f41b);
    }

    public void a_() {
        Object a = m21b().m30d().m45a("notifyListenerResult");
        if (a != null && Boolean.FALSE.equals(a)) {
            m20a("fulfillmentStatus", InternalFulfillmentResult.DELIVERY_ATTEMPTED.toString());
        }
        super.a_();
    }
}
