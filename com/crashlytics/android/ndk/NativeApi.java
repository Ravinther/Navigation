package com.crashlytics.android.ndk;

import android.content.res.AssetManager;

interface NativeApi {
    boolean initialize(String str, AssetManager assetManager);
}
