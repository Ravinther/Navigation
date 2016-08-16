package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.sygic.aura.network.AccountManager;
import com.sygic.base.C1799R;

public class GooglePreference extends LogInPreference {
    public GooglePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GooglePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GooglePreference(Context context) {
        super(context);
    }

    public void style(View view) {
        Button button = (Button) view.findViewById(C1799R.id.button);
        button.setBackgroundResource(C1799R.drawable.common_signin_btn_text_dark);
        styleButton(button);
    }

    protected void login() {
        AccountManager.nativeGoogleLogin();
    }
}
