package com.sygic.aura.feature.automotive;

import android.util.DisplayMetrics;
import com.sygic.aura.SygicMain;

public abstract class AbstractMirrorLink {
    protected static float sPhone2Headup;
    protected static float sPixelsRatio;
    protected float mPixelsRatio;
    private final SamsungMirrorLink mSamsung;

    public abstract boolean isConnected();

    public abstract void onAudioPlay();

    public abstract void onAudioStop();

    public abstract void onResume();

    public abstract void start();

    public abstract void stop();

    static {
        sPixelsRatio = 1.0f;
        sPhone2Headup = 1.0f;
    }

    AbstractMirrorLink() {
        this.mPixelsRatio = 1.0f;
        this.mSamsung = new SamsungMirrorLink();
        DisplayMetrics dm = new DisplayMetrics();
        SygicMain.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        sPixelsRatio = (((float) Math.max(dm.widthPixels, dm.heightPixels)) / 800.0f) / dm.density;
    }

    protected void startSamsungApi() {
        if (this.mSamsung != null) {
            this.mSamsung.start();
        }
    }

    protected void stopSamsungApi() {
        if (this.mSamsung != null) {
            this.mSamsung.stop();
        }
    }

    public static float getPixelsRatio() {
        return sPixelsRatio * sPhone2Headup;
    }

    protected void samsungOnAudio(boolean play) {
        if (this.mSamsung != null) {
            this.mSamsung.onAudio(play);
        }
    }
}
