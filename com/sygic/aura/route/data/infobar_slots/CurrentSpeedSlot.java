package com.sygic.aura.route.data.infobar_slots;

import android.view.View;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.ValueUnitPair;

public class CurrentSpeedSlot extends TwoValuesSlot {
    protected double mLastSpeedValue;
    protected View mRootView;
    protected boolean mWasSpeeding;

    public CurrentSpeedSlot() {
        this.mLastSpeedValue = -1.0d;
        this.mWasSpeeding = false;
    }

    protected void findSubViews(View rootView) {
        super.findSubViews(rootView);
        this.mRootView = rootView;
    }

    public void executeImpl() {
        double speed = PositionInfo.nativeGetCurrentVehicleSpeed();
        if (this.mLastSpeedValue != speed) {
            ValueUnitPair<String, String> speedWithUnits = ResourceManager.nativeFormatSpeedWithUnits(Math.max(0.0d, speed));
            setupViewValues((String) speedWithUnits.getValue(), (String) speedWithUnits.getUnit());
            this.mLastSpeedValue = speed;
        }
        if (this.mWasSpeeding != isSpeeding()) {
            this.mWasSpeeding ^= 1;
            int bgColor = this.mWasSpeeding ? getColor(2131558706) : getColor(2131558775);
            View parentView = (View) this.mRootView.getParent();
            if (parentView != null) {
                parentView.setBackgroundColor(bgColor);
            }
        }
    }

    private boolean isSpeeding() {
        return PositionInfo.nativeIsVehicleSpeeding();
    }

    public long getUpdateInterval() {
        return 1000;
    }
}
