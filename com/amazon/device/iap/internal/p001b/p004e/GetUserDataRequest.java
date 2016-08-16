package com.amazon.device.iap.internal.p001b.p004e;

import com.amazon.device.iap.internal.p001b.KiwiCommand;
import com.amazon.device.iap.internal.p001b.KiwiRequest;
import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserDataResponse;

/* renamed from: com.amazon.device.iap.internal.b.e.a */
public final class GetUserDataRequest extends KiwiRequest {
    public GetUserDataRequest(RequestId requestId) {
        super(requestId);
        KiwiCommand getUserIdCommandV2 = new GetUserIdCommandV2(this);
        getUserIdCommandV2.m22b(new GetUserIdCommandV1(this));
        m26a(getUserIdCommandV2);
    }

    public void m37a() {
        m27a((Object) (UserDataResponse) m30d().m44a());
    }
}
