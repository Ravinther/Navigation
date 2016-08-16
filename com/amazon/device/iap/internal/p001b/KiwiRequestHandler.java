package com.amazon.device.iap.internal.p001b;

import com.amazon.device.iap.internal.RequestHandler;
import com.amazon.device.iap.internal.p001b.p002b.PurchaseRequest;
import com.amazon.device.iap.internal.p001b.p003d.GetPurchaseUpdatesRequest;
import com.amazon.device.iap.internal.p001b.p004e.GetUserDataRequest;
import com.amazon.device.iap.internal.util.Logger;
import com.amazon.device.iap.model.RequestId;

/* renamed from: com.amazon.device.iap.internal.b.c */
public final class KiwiRequestHandler implements RequestHandler {
    private static final String f28a;

    static {
        f28a = KiwiRequestHandler.class.getSimpleName();
    }

    public void m33a(RequestId requestId) {
        Logger.m62a(f28a, "sendGetUserData");
        new GetUserDataRequest(requestId).m31e();
    }

    public void m34a(RequestId requestId, String str) {
        Logger.m62a(f28a, "sendPurchaseRequest");
        new PurchaseRequest(requestId, str).m31e();
    }

    public void m35a(RequestId requestId, boolean z) {
        Logger.m62a(f28a, "sendGetPurchaseUpdates");
        new GetPurchaseUpdatesRequest(requestId, z).m31e();
    }
}
