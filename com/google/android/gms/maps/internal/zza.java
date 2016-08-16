package com.google.android.gms.maps.internal;

import loquendo.tts.engine.TTSConst;

public final class zza {
    public static Boolean zza(byte b) {
        switch (b) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return Boolean.FALSE;
            case TTSConst.TTSMULTILINE /*1*/:
                return Boolean.TRUE;
            default:
                return null;
        }
    }

    public static byte zze(Boolean bool) {
        return bool != null ? bool.booleanValue() ? (byte) 1 : (byte) 0 : (byte) -1;
    }
}
