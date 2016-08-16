package com.amazon.device.iap.internal.p001b.p002b;

import com.amazon.device.iap.internal.p001b.KiwiCommand;
import com.amazon.device.iap.internal.p001b.KiwiRequest;

/* renamed from: com.amazon.device.iap.internal.b.b.a */
abstract class PurchaseItemCommandBase extends KiwiCommand {
    private static final String f22d;
    protected final String f23c;

    static {
        f22d = PurchaseItemCommandBase.class.getSimpleName();
    }

    PurchaseItemCommandBase(KiwiRequest kiwiRequest, String str, String str2) {
        super(kiwiRequest, "purchase_item", str);
        this.f23c = str2;
        m20a("sku", this.f23c);
    }
}
