package com.sygic.aura.route.data.infobar_slots;

import android.view.View;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.interfaces.TrafficChangeListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.data.TrafficItem;

public class RemainingTimeSlot extends TwoValuesSlot implements TrafficChangeListener {
    private int mOldValue;
    private int mTrafficDelay;

    public RemainingTimeSlot() {
        this.mOldValue = -1;
        this.mTrafficDelay = 0;
    }

    protected void findSubViews(View rootView) {
        super.findSubViews(rootView);
        MapEventsReceiver.registerTrafficChangeListener(this);
    }

    public void removeView() {
        super.removeView();
        MapEventsReceiver.unregisterTrafficChangeListener(this);
    }

    public void executeImpl() {
        int iSpan = ((int) RouteSummary.nativeGetRemainingTime()) + this.mTrafficDelay;
        if (this.mOldValue != iSpan && iSpan != -1) {
            this.mOldValue = iSpan;
            setupViewValues(ResourceManager.nativeFormatETA((long) iSpan), "(" + ResourceManager.nativeFormatTimeSpanToShortWords((long) iSpan) + ")");
        }
    }

    public long getUpdateInterval() {
        return 3000;
    }

    public void onTrafficChange(TrafficItem trafficItem) {
        this.mTrafficDelay = trafficItem.getDelay();
    }
}
