package com.sygic.aura.hud;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.interfaces.TrafficChangeListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.data.TrafficItem;
import com.sygic.aura.route.data.infobar_slots.Slot;

public class ETASlot implements TrafficChangeListener, Slot {
    private long mLastRun;
    private long mOldValue;
    private int mTrafficDelay;
    private final TextView mView;

    public ETASlot(View view) {
        this.mTrafficDelay = 0;
        this.mView = (TextView) view;
        this.mLastRun = getUpdateInterval() * -1;
        this.mOldValue = -1;
        this.mTrafficDelay = RouteSummary.nativeGetTrafficDelay();
        MapEventsReceiver.registerTrafficChangeListener(this);
    }

    public View getView(LayoutInflater inflater) {
        return this.mView;
    }

    public void execute(long time) {
        if (this.mLastRun + getUpdateInterval() <= time) {
            this.mLastRun = time;
            if (this.mView != null) {
                long value = RouteSummary.nativeGetRemainingTime();
                if (this.mOldValue != value && value != -1) {
                    this.mOldValue = value;
                    this.mView.setText(this.mView.getResources().getString(2131166110) + ResourceManager.nativeFormatETA((long) (((int) value) + this.mTrafficDelay)));
                }
            }
        }
    }

    public long getUpdateInterval() {
        return 1000;
    }

    public void removeView() {
        MapEventsReceiver.unregisterTrafficChangeListener(this);
    }

    public void resetTimer() {
        this.mLastRun = -1 * getUpdateInterval();
    }

    public void onTrafficChange(TrafficItem trafficItem) {
        this.mTrafficDelay = trafficItem.getDelay();
    }
}
