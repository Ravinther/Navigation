package com.google.android.vending.licensing;

public interface LicenseCheckerCallback {
    void allow(int i);

    void applicationError(int i);

    void dontAllow(int i);
}
