package com.sygic.aura.feature.automotive;

import android.content.Context;
import android.util.DisplayMetrics;
import com.sygic.aura.SygicMain;
import java.util.HashSet;
import java.util.Set;

public class LowAutomotiveFeature {
    private static final Set<InCarConnectionListener> mListeners;
    private final Context mCtx;
    private AbstractMirrorLink mMirrorLink;
    private PanasonicSmartApp mPanasonicSmartApp;

    static {
        mListeners = new HashSet();
    }

    public static LowAutomotiveFeature createInstance(Context context) {
        return new LowAutomotiveFeature(context);
    }

    private LowAutomotiveFeature(Context context) {
        this.mCtx = context;
    }

    public void enableMirrorLink(int iMirrorLink) {
        startMirrorLink(iMirrorLink);
    }

    public boolean isCarConnected() {
        if ((this.mMirrorLink == null || !this.mMirrorLink.isConnected()) && !BoschMySpin.INSTANCE.isConnected()) {
            return false;
        }
        return true;
    }

    public float getPixelsRatio() {
        return AbstractMirrorLink.getPixelsRatio();
    }

    public void startMirrorLink(int iMirrorLink) {
        DisplayMetrics dm = new DisplayMetrics();
        SygicMain.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (iMirrorLink == 0 && isCarConnected()) {
            AbstractMirrorLink.sPixelsRatio = (((float) Math.max(dm.widthPixels, dm.heightPixels)) / 800.0f) / dm.density;
        }
        if ((iMirrorLink & 2) > 0) {
        }
        if ((iMirrorLink & 4) > 0) {
            BoschMySpin.INSTANCE.mMirroringEnabled = true;
        } else {
            BoschMySpin.INSTANCE.mMirroringEnabled = false;
        }
        if ((iMirrorLink & 8) == 8) {
            if (this.mMirrorLink == null) {
                this.mMirrorLink = new MirrorLink11();
                this.mMirrorLink.start();
            }
        } else if (this.mMirrorLink != null) {
            this.mMirrorLink.stop();
            this.mMirrorLink = null;
        }
        if ((iMirrorLink & 16) == 16) {
            if (this.mPanasonicSmartApp == null) {
                this.mPanasonicSmartApp = new PanasonicSmartApp(this.mCtx);
                this.mPanasonicSmartApp.start();
            }
        } else if (this.mPanasonicSmartApp != null) {
            this.mPanasonicSmartApp.stop();
            this.mPanasonicSmartApp = null;
        }
    }

    public void onResume() {
        if (this.mMirrorLink != null) {
            this.mMirrorLink.onResume();
        }
    }

    public void onDestroy() {
        startMirrorLink(0);
    }

    public void onAudioPlay() {
        if (this.mMirrorLink != null) {
            this.mMirrorLink.onAudioPlay();
        }
    }

    public void onAudioStop() {
        if (this.mMirrorLink != null) {
            this.mMirrorLink.onAudioStop();
        }
    }

    public void registerOnConnectionListener(InCarConnectionListener listener) {
        if (listener != null) {
            mListeners.add(listener);
        }
    }

    public void unregisterOnConnectionListener(InCarConnectionListener listener) {
        if (listener != null) {
            mListeners.remove(listener);
        }
    }

    public void onConnectionChanged(boolean connected) {
        for (InCarConnectionListener listener : mListeners) {
            listener.onConnectionChanged(connected);
        }
    }
}
