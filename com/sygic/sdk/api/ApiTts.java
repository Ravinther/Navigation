package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;

public class ApiTts {
    public static void playSound(String text, int maxTime) throws GeneralException {
        Api.nPlaySoundTTS(text, maxTime);
    }
}
