package com.sygic.aura.route.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SpeedLimitSignShape extends FrameLayout {
    protected ViewHolder mHolder;

    protected class ViewHolder {
        TextView textValue;
        TextView textWarn;

        protected ViewHolder() {
        }
    }

    public SpeedLimitSignShape(Context context) {
        super(context);
    }

    public SpeedLimitSignShape(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView getValue() {
        if (this.mHolder == null) {
            _initHolder();
        }
        return this.mHolder.textValue;
    }

    public TextView getWarn() {
        if (this.mHolder == null) {
            _initHolder();
        }
        return this.mHolder.textWarn;
    }

    private void _initHolder() {
        this.mHolder = new ViewHolder();
        this.mHolder.textValue = (TextView) findViewById(2131624356);
        this.mHolder.textWarn = (TextView) findViewById(2131624605);
    }
}
