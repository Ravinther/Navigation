package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.SpeedInfoItem;

public interface SpeedLimitListener extends CoreEventListener {
    void onSpeedLimitChanged(SpeedInfoItem speedInfoItem);
}
