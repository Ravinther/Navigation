package com.sygic.aura.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.view.ThreeStateButton;
import java.util.HashSet;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

public class SynchronizedThreeStateChangeListener implements OnClickListener {
    private static SynchronizedThreeStateChangeListener sInstance;
    private final Set<ThreeStateButton> mButtons;

    static {
        sInstance = null;
    }

    public static SynchronizedThreeStateChangeListener getInstance(ThreeStateButton button) {
        if (sInstance == null) {
            sInstance = new SynchronizedThreeStateChangeListener();
        }
        sInstance.mButtons.add(button);
        return sInstance;
    }

    private SynchronizedThreeStateChangeListener() {
        this.mButtons = new HashSet();
    }

    public void onClick(View buttonView) {
        int state = ((ThreeStateButton) buttonView).getState();
        switch (state) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                MapControlsManager.nativeUnlockVehicle();
                MapOverlayFragment.setMode(buttonView.getContext(), Mode.FREEDRIVE_BROWSE);
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                MapControlsManager.nativeLockVehicleCompass();
                MapOverlayFragment.setMode(buttonView.getContext(), Mode.FREEDRIVE_INFO_BAR);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                MapControlsManager.nativeLockVehicleNorthUp();
                break;
        }
        for (ThreeStateButton button : this.mButtons) {
            if (!buttonView.equals(button)) {
                button.setState(state);
            }
        }
    }
}
