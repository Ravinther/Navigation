package com.sygic.aura.route.data.infobar_slots;

import android.view.View;
import android.widget.TextView;

public abstract class SingleValueSlot extends RouteInfoBarSlot {
    private TextView mMainView;

    protected int getLayoutId() {
        return 2130903267;
    }

    protected void findSubViews(View rootView) {
        this.mMainView = (TextView) rootView.findViewById(2131624585);
    }

    protected void setupViewValue(String value) {
        if (this.mMainView != null) {
            this.mMainView.setText(value);
        }
    }

    protected void setTextColor(int resourceId) {
        if (this.mMainView != null) {
            this.mMainView.setTextColor(resourceId);
        }
    }
}
