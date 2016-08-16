package com.sygic.aura.hud;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.data.infobar_slots.Slot;

public class SpeedSlot implements Slot {
    private long mLastRun;
    private double mLastSpeedValue;
    private final TextView mView;
    private boolean mWasSpeeding;

    public SpeedSlot(View view) {
        this.mView = (TextView) view;
        this.mLastRun = -1 * getUpdateInterval();
        this.mLastSpeedValue = 0.0d;
        this.mWasSpeeding = false;
    }

    public View getView(LayoutInflater inflater) {
        return this.mView;
    }

    public void execute(long time) {
        double d = 0.0d;
        if (this.mLastRun + getUpdateInterval() <= time) {
            this.mLastRun = time;
            if (this.mView != null) {
                Resources resources = this.mView.getResources();
                double speed = PositionInfo.nativeGetCurrentVehicleSpeed();
                if (this.mLastSpeedValue != speed) {
                    TextView textView = this.mView;
                    if (speed >= 0.0d) {
                        d = speed;
                    }
                    textView.setText(ResourceManager.nativeFormatSpeed(d));
                    this.mLastSpeedValue = speed;
                }
                if (this.mWasSpeeding != PositionInfo.nativeIsVehicleSpeeding()) {
                    int textColor;
                    this.mWasSpeeding ^= 1;
                    if (this.mWasSpeeding) {
                        textColor = resources.getColor(2131558682);
                    } else {
                        textColor = resources.getColor(2131558794);
                    }
                    this.mView.setTextColor(textColor);
                }
            }
        }
    }

    public long getUpdateInterval() {
        return 1000;
    }

    public void removeView() {
    }

    public void resetTimer() {
        this.mLastRun = -1 * getUpdateInterval();
    }
}
