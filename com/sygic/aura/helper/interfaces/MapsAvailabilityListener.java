package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;

public interface MapsAvailabilityListener extends CoreEventListener {
    void onMapsAvailabilityChanged(Boolean bool);
}
