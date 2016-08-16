package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

public class AppCall {
    private static AppCall currentPendingCall;
    private UUID callId;
    private int requestCode;
    private Intent requestIntent;

    public static AppCall getCurrentPendingCall() {
        return currentPendingCall;
    }

    private static synchronized boolean setCurrentPendingCall(AppCall appCall) {
        boolean z;
        synchronized (AppCall.class) {
            AppCall oldAppCall = getCurrentPendingCall();
            currentPendingCall = appCall;
            z = oldAppCall != null;
        }
        return z;
    }

    public AppCall(int requestCode) {
        this(requestCode, UUID.randomUUID());
    }

    public AppCall(int requestCode, UUID callId) {
        this.callId = callId;
        this.requestCode = requestCode;
    }

    public Intent getRequestIntent() {
        return this.requestIntent;
    }

    public UUID getCallId() {
        return this.callId;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void setRequestIntent(Intent requestIntent) {
        this.requestIntent = requestIntent;
    }

    public boolean setPending() {
        return setCurrentPendingCall(this);
    }
}
