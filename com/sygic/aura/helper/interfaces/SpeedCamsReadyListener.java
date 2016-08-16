package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.route.data.SpeedCamItem;
import java.util.ArrayList;

public interface SpeedCamsReadyListener extends CoreEventListener {
    void onSpeedCamsReady(ArrayList<SpeedCamItem> arrayList);
}
