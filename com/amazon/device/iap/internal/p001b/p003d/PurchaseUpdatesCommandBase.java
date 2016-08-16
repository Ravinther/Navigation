package com.amazon.device.iap.internal.p001b.p003d;

import com.amazon.device.iap.internal.p001b.KiwiCommand;
import com.amazon.device.iap.internal.p001b.KiwiRequest;

/* renamed from: com.amazon.device.iap.internal.b.d.b */
abstract class PurchaseUpdatesCommandBase extends KiwiCommand {
    protected final boolean f29a;

    PurchaseUpdatesCommandBase(KiwiRequest kiwiRequest, String str, boolean z) {
        super(kiwiRequest, "purchase_updates", str);
        this.f29a = z;
    }
}
