package com.amazon.device.iap.internal.p001b.p004e;

import com.amazon.device.iap.internal.p001b.KiwiCommand;
import com.amazon.device.iap.internal.p001b.KiwiRequest;

/* renamed from: com.amazon.device.iap.internal.b.e.b */
abstract class GetUserIdCommandBase extends KiwiCommand {
    protected static final String f36a;

    static {
        f36a = null;
    }

    public GetUserIdCommandBase(KiwiRequest kiwiRequest, String str) {
        super(kiwiRequest, "get_userId", str);
    }
}
