package com.sygic.aura.route.data.infobar_slots;

import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteSummary;

public class TimeToEndSlot extends SingleValueSlot {
    private long mOldValue;

    public TimeToEndSlot() {
        this.mOldValue = -1;
    }

    public void executeImpl() {
        long value = RouteSummary.nativeGetRemainingTime();
        if (this.mOldValue != value && value != -1) {
            this.mOldValue = value;
            setupViewValue(ResourceManager.nativeFormatTimeSpanToShortWords(value));
        }
    }

    public long getUpdateInterval() {
        return 1000;
    }
}
