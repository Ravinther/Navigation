package com.amazon.device.iap.internal.p000a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.amazon.device.iap.internal.PurchasingManager;
import com.amazon.device.iap.internal.RequestHandler;
import com.amazon.device.iap.internal.util.Logger;
import com.amazon.device.iap.model.RequestId;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.amazon.device.iap.internal.a.c */
public final class SandboxRequestHandler implements RequestHandler {
    private static final String f9a;

    static {
        f9a = SandboxRequestHandler.class.getSimpleName();
    }

    public void m13a(RequestId requestId) {
        Logger.m62a(f9a, "sendGetUserDataRequest");
        m12a(requestId.toString());
    }

    private void m12a(String str) {
        try {
            Context b = PurchasingManager.m47d().m53b();
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("requestId", str);
            jSONObject.put("packageName", b.getPackageName());
            jSONObject.put("sdkVersion", "2.0.0");
            bundle.putString("userInput", jSONObject.toString());
            Intent intent = new Intent("com.amazon.testclient.iap.appUserId");
            intent.addFlags(268435456);
            intent.putExtras(bundle);
            b.startService(intent);
        } catch (JSONException e) {
            Logger.m64b(f9a, "Error in sendGetUserDataRequest.");
        }
    }

    public void m14a(RequestId requestId, String str) {
        Logger.m62a(f9a, "sendPurchaseRequest");
        try {
            Context b = PurchasingManager.m47d().m53b();
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sku", str);
            jSONObject.put("requestId", requestId.toString());
            jSONObject.put("packageName", b.getPackageName());
            jSONObject.put("sdkVersion", "2.0.0");
            bundle.putString("purchaseInput", jSONObject.toString());
            Intent intent = new Intent("com.amazon.testclient.iap.purchase");
            intent.addFlags(268435456);
            intent.putExtras(bundle);
            b.startService(intent);
        } catch (JSONException e) {
            Logger.m64b(f9a, "Error in sendPurchaseRequest.");
        }
    }

    public void m15a(RequestId requestId, boolean z) {
        String str = "GET_USER_ID_FOR_PURCHASE_UPDATES_PREFIX:" + (z ? 1 : 0) + ":" + new RequestId().toString();
        Logger.m62a(f9a, "sendPurchaseUpdatesRequest/sendGetUserData first:" + str);
        m12a(str);
    }
}
