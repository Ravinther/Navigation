package com.amazon.device.iap.model;

import java.util.Map;
import java.util.Set;

public class ProductDataResponse {
    private final Map<String, Product> productData;
    private final RequestId requestId;
    private final RequestStatus requestStatus;
    private final Set<String> unavailableSkus;

    public enum RequestStatus {
        SUCCESSFUL,
        FAILED,
        NOT_SUPPORTED
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public String toString() {
        String str = "(%s, requestId: \"%s\", unavailableSkus: %s, requestStatus: \"%s\", productData: %s)";
        Object[] objArr = new Object[5];
        objArr[0] = super.toString();
        objArr[1] = this.requestId;
        objArr[2] = this.requestStatus != null ? this.requestStatus.toString() : "null";
        objArr[3] = this.unavailableSkus != null ? this.unavailableSkus.toString() : "null";
        objArr[4] = this.productData != null ? this.productData.toString() : "null";
        return String.format(str, objArr);
    }
}
