package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ForgotPasswordPreference extends SettingsFragmentPreference {
    public ForgotPasswordPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ForgotPasswordPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForgotPasswordPreference(Context context) {
        super(context);
    }

    public void style(View view) {
        super.style(view);
        TextView title = (TextView) view.findViewById(16908310);
        title.setPaintFlags(8);
        title.setPadding(15, 0, 0, 0);
    }

    public View getView(View convertView, ViewGroup parent) {
        View view = super.getView(convertView, parent);
        LayoutParams params = view.getLayoutParams();
        params.height = -2;
        view.setMinimumHeight(0);
        view.setLayoutParams(params);
        return view;
    }

    protected void onClick() {
        LoginDetailsPreference preference = (LoginDetailsPreference) findPreferenceInHierarchy(getContext().getString(2131165287));
        if (preference != null) {
            preference.persistName();
        }
        super.onClick();
    }
}
