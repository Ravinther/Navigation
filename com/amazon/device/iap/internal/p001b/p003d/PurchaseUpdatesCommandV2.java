package com.amazon.device.iap.internal.p001b.p003d;

import com.amazon.device.iap.internal.p001b.KiwiRequest;

/* renamed from: com.amazon.device.iap.internal.b.d.c */
public final class PurchaseUpdatesCommandV2 extends PurchaseUpdatesCommandBase {
    private static final String f30b;

    static {
        f30b = PurchaseUpdatesCommandV2.class.getSimpleName();
    }

    public PurchaseUpdatesCommandV2(KiwiRequest kiwiRequest, boolean z) {
        super(kiwiRequest, "2.0", z);
    }
}
