package com.crashlytics.android.core;

import com.crashlytics.android.ndk.CrashlyticsNdk;

public class CrashlyticsKitBinder {
    public void bindCrashEventDataProvider(CrashlyticsCore crash, CrashlyticsNdk ndk) {
        crash.setExternalCrashEventDataProvider(ndk);
    }
}
