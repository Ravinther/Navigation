package com.sygic.aura.route.data.infobar_slots;

import com.sygic.aura.resources.ResourceManager;

public class DayTimeSlot extends SingleValueSlot {
    private long mOldValue;

    public DayTimeSlot() {
        this.mOldValue = -1;
    }

    public void executeImpl() {
        long value = System.currentTimeMillis() / 1000;
        if (this.mOldValue != value && value != -1) {
            this.mOldValue = value;
            setupViewValue(ResourceManager.nativeFormatCurrentTimeStampToDigits(false));
        }
    }

    public long getUpdateInterval() {
        return 1000;
    }
}
