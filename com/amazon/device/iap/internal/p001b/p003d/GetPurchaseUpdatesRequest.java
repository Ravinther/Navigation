package com.amazon.device.iap.internal.p001b.p003d;

import com.amazon.device.iap.internal.model.InternalFulfillmentResult;
import com.amazon.device.iap.internal.p001b.KiwiCommand;
import com.amazon.device.iap.internal.p001b.KiwiRequest;
import com.amazon.device.iap.internal.p001b.p004e.GetUserIdCommandV1;
import com.amazon.device.iap.internal.p001b.p004e.GetUserIdCommandV2;
import com.amazon.device.iap.internal.p001b.p005g.PurchaseFulfilledCommandV2;
import com.amazon.device.iap.internal.util.Validator;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.device.iap.model.RequestId;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.amazon.device.iap.internal.b.d.a */
public final class GetPurchaseUpdatesRequest extends KiwiRequest {
    public GetPurchaseUpdatesRequest(RequestId requestId, boolean z) {
        super(requestId);
        KiwiCommand getUserIdCommandV2 = new GetUserIdCommandV2(this);
        getUserIdCommandV2.m19a(new PurchaseUpdatesCommandV2(this, z));
        KiwiCommand getUserIdCommandV1 = new GetUserIdCommandV1(this);
        getUserIdCommandV1.m19a(new PurchaseUpdatesCommandV1(this, z));
        getUserIdCommandV2.m22b(getUserIdCommandV1);
        m26a(getUserIdCommandV2);
    }

    public void m36a() {
        KiwiCommand kiwiCommand = null;
        PurchaseUpdatesResponse purchaseUpdatesResponse = (PurchaseUpdatesResponse) m30d().m44a();
        if (purchaseUpdatesResponse.getReceipts() != null && purchaseUpdatesResponse.getReceipts().size() > 0) {
            Set hashSet = new HashSet();
            for (Receipt receipt : purchaseUpdatesResponse.getReceipts()) {
                if (!Validator.m61a(receipt.getReceiptId())) {
                    hashSet.add(receipt.getReceiptId());
                }
            }
            kiwiCommand = new PurchaseFulfilledCommandV2(this, hashSet, InternalFulfillmentResult.DELIVERED.toString());
        }
        m28a(purchaseUpdatesResponse, kiwiCommand);
    }
}
