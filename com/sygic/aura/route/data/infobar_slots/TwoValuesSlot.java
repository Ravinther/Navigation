package com.sygic.aura.route.data.infobar_slots;

import android.view.View;
import android.widget.TextView;

public abstract class TwoValuesSlot extends SingleValueSlot {
    private TextView mSecondTextView;

    protected int getLayoutId() {
        return 2130903268;
    }

    protected void findSubViews(View rootView) {
        super.findSubViews(rootView);
        this.mSecondTextView = (TextView) rootView.findViewById(2131624586);
    }

    protected void setupViewValues(String mainValue, String secondValue) {
        if (this.mSecondTextView != null) {
            this.mSecondTextView.setText(secondValue);
        }
        setupViewValue(mainValue);
    }
}
