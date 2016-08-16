package com.infinario.android.infinariosdk;

public class IabResult {
    String mMessage;
    int mResponse;

    public IabResult(int response, String message) {
        this.mResponse = response;
        if (message == null || message.trim().length() == 0) {
            this.mMessage = "response: " + response;
        } else {
            this.mMessage = message + " (response: " + response + ")";
        }
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String toString() {
        return "IabResult: " + getMessage();
    }
}
