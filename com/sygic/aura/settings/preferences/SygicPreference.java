package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.View;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.network.AccountManager;
import com.sygic.base.C1799R;

public class SygicPreference extends LogInPreference {
    public SygicPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SygicPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SygicPreference(Context context) {
        super(context);
    }

    public void style(View view) {
        view.findViewById(C1799R.id.button).setBackgroundResource(2130837620);
    }

    protected void onButtonClicked(View buttonView) {
        LoginDetailsPreference loginPreference = (LoginDetailsPreference) findPreferenceInHierarchy(getContext().getString(2131165287));
        if (loginPreference == null || Patterns.EMAIL_ADDRESS.matcher(loginPreference.getName()).matches()) {
            super.onButtonClicked(buttonView);
        } else {
            SToast.makeText(getContext(), 2131165299, 0).show();
        }
    }

    protected void login() {
        LoginDetailsPreference preference = (LoginDetailsPreference) findPreferenceInHierarchy(getContext().getString(2131165287));
        if (preference != null) {
            AccountManager.nativeSygicLogin(preference.getName(), preference.getPassword());
            preference.hideKeyboard();
        }
    }
}
