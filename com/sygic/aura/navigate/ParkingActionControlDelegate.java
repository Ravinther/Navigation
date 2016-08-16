package com.sygic.aura.navigate;

import android.content.Context;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.views.font_specials.STextView;

public class ParkingActionControlDelegate extends ActionControlDelegate {
    public ParkingActionControlDelegate(ActionControlFragment fragment) {
        super(fragment);
    }

    protected void setTitle(STextView titleTextView) {
        titleTextView.setCoreText(2131165327);
    }

    protected void handleOnClick() {
        addWaypoint(2131165330);
        super.handleOnClick();
        SygicAnalyticsLogger.getAnalyticsEvent(this.mFragment.getActivity(), EventType.OFFER_PARKING).setName("Navigation -> Offer Parking").setValue("display_mode", "map_zoomed_out").setValue("action", "confirmed").logAndRecycle();
    }

    protected void handleCancel() {
        super.handleCancel();
        SygicAnalyticsLogger.getAnalyticsEvent(this.mFragment.getActivity(), EventType.OFFER_PARKING).setName("Navigation -> Offer Parking").setValue("display_mode", "map_zoomed_out").setValue("action", "canceled").logAndRecycle();
    }

    protected void handleUndo(Context context) {
        super.handleUndo(context);
        SygicAnalyticsLogger.getAnalyticsEvent(context, EventType.OFFER_PARKING).setName("Navigation -> Offer Parking").setValue("display_mode", "map_zoomed_out").setValue("action", "undo-ed").logAndRecycle();
    }
}
