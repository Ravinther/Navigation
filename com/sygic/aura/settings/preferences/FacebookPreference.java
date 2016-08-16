package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.sygic.aura.network.AccountManager;
import com.sygic.base.C1799R;

public class FacebookPreference extends LogInPreference {
    public FacebookPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FacebookPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FacebookPreference(Context context) {
        super(context);
    }

    public void style(View view) {
        Button button = (Button) view.findViewById(C1799R.id.button);
        button.setBackgroundResource(C1799R.drawable.com_facebook_button_background);
        styleButton(button);
    }

    protected void login() {
        AccountManager.nativeFacebookLogin();
    }
}
