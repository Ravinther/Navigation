package com.google.android.vending.licensing;

public interface Policy {
    boolean allowAccess();

    void processServerResponse(int i, ResponseData responseData);
}
