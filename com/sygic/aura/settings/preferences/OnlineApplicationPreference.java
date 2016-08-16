package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import com.sygic.aura.settings.data.SettingsManager;

public class OnlineApplicationPreference extends IconTextPreference {
    private String mKey;

    public OnlineApplicationPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mKey = null;
        init();
    }

    public OnlineApplicationPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mKey = null;
        init();
    }

    public OnlineApplicationPreference(Context context) {
        super(context);
        this.mKey = null;
        init();
    }

    public void init() {
        this.mKey = getKey();
    }

    protected void onClick() {
        if (this.mKey.equals(getContext().getString(2131166310))) {
            SettingsManager.rateApp(getContext());
        } else if (this.mKey.equals(getContext().getString(2131166308))) {
            SettingsManager.feedback(getContext());
        } else if (this.mKey.equals(getContext().getString(2131166309))) {
            SettingsManager.help(getContext());
        }
    }
}
