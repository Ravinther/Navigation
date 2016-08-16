package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.route.data.DirectionStatus;

public interface DirectionChangeListener extends CoreEventListener {
    void onDirectionChange(DirectionStatus directionStatus);
}
