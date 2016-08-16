package com.amazon.device.iap.model;

import java.util.Arrays;
import java.util.List;

public final class PurchaseUpdatesResponse {
    private final boolean hasMore;
    private final List<Receipt> receipts;
    private final RequestId requestId;
    private final RequestStatus requestStatus;
    private final UserData userData;

    public enum RequestStatus {
        SUCCESSFUL,
        FAILED,
        NOT_SUPPORTED
    }

    public UserData getUserData() {
        return this.userData;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public List<Receipt> getReceipts() {
        return this.receipts;
    }

    public boolean hasMore() {
        return this.hasMore;
    }

    public String toString() {
        String str = "(%s, requestId: \"%s\", requestStatus: \"%s\", userData: \"%s\", receipts: %s, hasMore: \"%b\")";
        Object[] objArr = new Object[6];
        objArr[0] = super.toString();
        objArr[1] = this.requestId;
        objArr[2] = this.requestStatus;
        objArr[3] = this.userData;
        objArr[4] = this.receipts != null ? Arrays.toString(this.receipts.toArray()) : "null";
        objArr[5] = Boolean.valueOf(this.hasMore);
        return String.format(str, objArr);
    }
}
