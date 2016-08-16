package com.amazon.device.iap.model;

public final class PurchaseResponse {
    private final Receipt receipt;
    private final RequestId requestId;
    private final RequestStatus requestStatus;
    private final UserData userData;

    public enum RequestStatus {
        SUCCESSFUL,
        FAILED,
        INVALID_SKU,
        ALREADY_PURCHASED,
        NOT_SUPPORTED
    }

    public UserData getUserData() {
        return this.userData;
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public String toString() {
        String str = "(%s, requestId: \"%s\", purchaseRequestStatus: \"%s\", userId: \"%s\", receipt: %s)";
        Object[] objArr = new Object[5];
        objArr[0] = super.toString();
        objArr[1] = this.requestId;
        objArr[2] = this.requestStatus != null ? this.requestStatus.toString() : "null";
        objArr[3] = this.userData;
        objArr[4] = this.receipt;
        return String.format(str, objArr);
    }
}
