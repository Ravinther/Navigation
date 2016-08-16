package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class DependencyStatePreference extends CheckBoxPreference {
    private CompoundButton mCheckBox;
    private OnCheckedChangeListener mListener;

    public DependencyStatePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DependencyStatePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DependencyStatePreference(Context context) {
        super(context);
    }

    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);
        view.setVisibility(8);
        return view;
    }

    public View getView(View convertView, ViewGroup parent) {
        View view = super.getView(convertView, parent);
        LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setMinimumHeight(params.height);
        view.setLayoutParams(params);
        this.mCheckBox = (CompoundButton) view.findViewById(16908289);
        return view;
    }

    public void setOnCheckedChangedListener(OnCheckedChangeListener listener) {
        this.mListener = listener;
    }

    public void setChecked(boolean checked) {
        boolean changed = isChecked() != checked;
        super.setChecked(checked);
        if (changed && this.mListener != null) {
            this.mListener.onCheckedChanged(this.mCheckBox, checked);
        }
    }

    public void toggle() {
        setChecked(!isChecked());
    }
}
