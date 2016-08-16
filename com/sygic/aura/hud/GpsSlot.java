package com.sygic.aura.hud;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.route.data.infobar_slots.Slot;

public class GpsSlot implements Slot {
    private long mLastRun;
    private final TextView mView;

    public GpsSlot(View view) {
        this.mView = (TextView) view;
        this.mLastRun = -1 * getUpdateInterval();
    }

    public View getView(LayoutInflater inflater) {
        return this.mView;
    }

    public void execute(long time) {
        if (this.mLastRun + getUpdateInterval() <= time) {
            this.mLastRun = time;
            if (this.mView != null && !PositionInfo.nativeHasValidPosition(true)) {
                this.mView.setVisibility(0);
                this.mView.setTextColor(this.mView.getResources().getColor(2131558561));
                this.mView.setText(this.mView.getResources().getString(2131166113));
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
