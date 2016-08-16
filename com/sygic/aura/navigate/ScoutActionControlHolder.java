package com.sygic.aura.navigate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.feature.system.LowSystemFeature.EEventType;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.map.view.ModernViewSwitcher;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

public class ScoutActionControlHolder extends ActionControlHolder {
    private STextView mDistanceDiffTextView;
    private STextView mTimeDiffTextView;

    public ScoutActionControlHolder(ModernViewSwitcher switcher, ViewGroup view) {
        super(switcher, view);
    }

    protected int getTitleForBaseLayout() {
        return 2131165964;
    }

    protected void findViews() {
        super.findViews();
        this.mTimeDiffTextView = (STextView) this.mView.findViewById(C1799R.id.time);
        this.mDistanceDiffTextView = (STextView) this.mView.findViewById(2131624119);
    }

    protected void onClickPositive(View v) {
        super.onClickPositive(v);
        Bundle logParams = new Bundle();
        logParams.putString("eventName", "scout_compute_confirm");
        logParams.putSerializable("eventType", EEventType.ETNone);
        SygicAnalyticsLogger.logEvent(v.getContext(), EventType.SCOUT_COMPUTE, logParams);
        RouteManager.nativeSwitchScoutRoute();
        SToast.makeText(v.getContext(), 2131165331, 1).show();
    }

    protected void onClickNegative(View v) {
        super.onClickNegative(v);
        Bundle logParams = new Bundle();
        logParams.putString("eventName", "scout_compute_dismiss");
        logParams.putSerializable("eventType", EEventType.ETNone);
        SygicAnalyticsLogger.logEvent(v.getContext(), EventType.SCOUT_COMPUTE, logParams);
    }

    public void show(int time, int distance) {
        showInternal();
        this.mTimeDiffTextView.setText(formatTimeDiff(time));
        this.mDistanceDiffTextView.setText((distance >= 0 ? "+" : "-") + ResourceManager.nativeFormatDistance((long) Math.abs(distance), true));
    }

    public void invalidateTrafficDialog() {
        hideInternal();
    }

    private String formatTimeDiff(int time) {
        return (time >= 0 ? "-" : "") + ResourceManager.nativeFormatTimeSpanToShortWords((long) time, false, true, true);
    }
}
