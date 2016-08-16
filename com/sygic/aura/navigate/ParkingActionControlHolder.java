package com.sygic.aura.navigate;

import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.map.notification.NotificationManager;
import com.sygic.aura.map.view.ModernViewSwitcher;

public class ParkingActionControlHolder extends ActionControlHolder {
    public ParkingActionControlHolder(ModernViewSwitcher switcher, ViewGroup view) {
        super(switcher, view);
    }

    protected int getTitleForBaseLayout() {
        return 2131165329;
    }

    protected int getIconForBaseLayout() {
        return 2131034123;
    }

    protected void onClickPositive(View v) {
        super.onClickPositive(v);
        SygicAnalyticsLogger.getAnalyticsEvent(v.getContext(), EventType.OFFER_PARKING).setName("Navigation -> Offer Parking").setValue("display_mode", "while_driving").setValue("action", "confirmed").logAndRecycle();
        NotificationManager.nativeShowParkingPlacesOnMap();
        hideInternal();
    }

    protected void onClickNegative(View v) {
        super.onClickNegative(v);
        SygicAnalyticsLogger.getAnalyticsEvent(v.getContext(), EventType.OFFER_PARKING).setName("Navigation -> Offer Parking").setValue("display_mode", "while_driving").setValue("action", "canceled").logAndRecycle();
    }

    protected void onTimedOut() {
        super.onTimedOut();
        SygicAnalyticsLogger.getAnalyticsEvent(this.mView.getContext(), EventType.OFFER_PARKING).setName("Navigation -> Offer Parking").setValue("display_mode", "while_driving").setValue("action", "timed_out").logAndRecycle();
    }
}
