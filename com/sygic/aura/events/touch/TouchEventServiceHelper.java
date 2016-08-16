package com.sygic.aura.events.touch;

import com.sygic.aura.utils.Utils;

public abstract class TouchEventServiceHelper implements TouchEventService {
    public static TouchEventService createInstance() {
        if (Utils.getAndroidVersion() < 5) {
            return new SingleTouchService();
        }
        return new MultiTouchService();
    }
}
