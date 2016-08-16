package com.crashlytics.android.ndk;

import android.content.res.AssetManager;

class JniNativeApi implements NativeApi {
    private native boolean nativeInit(String str, Object obj);

    JniNativeApi() {
    }

    static {
        System.loadLibrary("crashlytics");
    }

    public boolean initialize(String crashFilePath, AssetManager assetManager) {
        return nativeInit(crashFilePath, assetManager);
    }
}
