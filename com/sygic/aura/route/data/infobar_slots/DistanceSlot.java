package com.sygic.aura.route.data.infobar_slots;

import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.ValueUnitPair;

public abstract class DistanceSlot extends TwoValuesSlot {
    private long mOldValue;

    protected abstract long getDistance();

    public DistanceSlot() {
        this.mOldValue = -1;
    }

    public void executeImpl() {
        long distance = getDistance();
        if (this.mOldValue != distance) {
            ValueUnitPair<String, String> valueUnit = ResourceManager.nativeFormatDistance(distance, true, true, true);
            setupViewValues((String) valueUnit.getValue(), (String) valueUnit.getUnit());
            this.mOldValue = distance;
        }
    }

    public long getUpdateInterval() {
        return 2000;
    }
}
