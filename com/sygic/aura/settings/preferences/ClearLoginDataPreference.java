package com.sygic.aura.settings.preferences;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.sygic.aura.network.AccountManager;

public class ClearLoginDataPreference extends CoreTitlePreference {
    public ClearLoginDataPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ClearLoginDataPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearLoginDataPreference(Context context) {
        super(context);
    }

    protected void onClick() {
        AccountManager.nativeClearUserLoginData();
        ((Activity) getContext()).onBackPressed();
    }

    public void style(View view) {
        TextView title = (TextView) view.findViewById(16908310);
        if (title != null) {
            title.setTextColor(getContext().getResources().getColorStateList(2131558824));
            title.setTextSize(16.0f);
            title.setGravity(17);
            LayoutParams params = (LayoutParams) title.getLayoutParams();
            params.addRule(13);
            title.setLayoutParams(params);
        }
    }
}
