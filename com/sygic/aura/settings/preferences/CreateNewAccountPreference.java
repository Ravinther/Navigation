package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.sygic.aura.network.AccountManager;
import com.sygic.base.C1799R;

public class CreateNewAccountPreference extends ButtonPreference {
    public CreateNewAccountPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CreateNewAccountPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CreateNewAccountPreference(Context context) {
        super(context);
    }

    public void style(View view) {
        view.findViewById(C1799R.id.button).setBackgroundResource(2130837620);
    }

    protected void onButtonClicked(View buttonView) {
        LoginDetailsPreference preference = (LoginDetailsPreference) findPreferenceInHierarchy(getContext().getString(2131165287));
        if (preference != null) {
            AccountManager.nativeCreateAccount(preference.getName(), preference.getPassword());
        }
    }
}
