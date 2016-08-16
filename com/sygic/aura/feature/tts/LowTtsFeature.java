package com.sygic.aura.feature.tts;

import android.content.Context;
import com.sygic.aura.feature.ResultListener;

public abstract class LowTtsFeature implements ResultListener {
    protected Context mCtx;
    protected TtsAndroid mTtsAndroid;
    protected TTS_Loquendo mTtsSound;
    protected boolean mbAndroid;

    public abstract String getLanguageList(String[] strArr, boolean z);

    public abstract TtsAndroid getTtsAndroid();

    public abstract String getVoiceList(String str);

    public abstract int initialize(String str, String str2, int i);

    public abstract boolean isPlaying();

    public abstract boolean load(String str, String str2, long j);

    public abstract boolean play(String str, boolean z);

    public abstract boolean setSpeed(int i);

    public abstract boolean setVolume(int i);

    public abstract boolean stop();

    public abstract boolean uninitialize();

    public abstract void unload();

    protected LowTtsFeature() {
    }

    public LowTtsFeature(Context context) {
        this.mCtx = context;
    }

    public static LowTtsFeature createInstance(Context context) {
        return new LowTtsFeatureBase(context);
    }
}
