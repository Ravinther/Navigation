package com.google.ads;

@Deprecated
public final class AdRequest {
    public static final String TEST_EMULATOR;

    public enum Gender {
        UNKNOWN,
        MALE,
        FEMALE
    }

    static {
        TEST_EMULATOR = com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR;
    }
}
