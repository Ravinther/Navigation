package com.amazon.device.iap.model;

public final class UserDataResponse {
    private final RequestId requestId;
    private final RequestStatus requestStatus;
    private final UserData userData;

    public enum RequestStatus {
        SUCCESSFUL,
        FAILED,
        NOT_SUPPORTED
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public String toString() {
        String str = "(%s, requestId: \"%s\", requestStatus: \"%s\", userData: \"%s\")";
        Object[] objArr = new Object[4];
        objArr[0] = super.toString();
        objArr[1] = this.requestId;
        objArr[2] = this.requestStatus != null ? this.requestStatus.toString() : "null";
        objArr[3] = this.userData != null ? this.userData.toString() : "null";
        return String.format(str, objArr);
    }
}
