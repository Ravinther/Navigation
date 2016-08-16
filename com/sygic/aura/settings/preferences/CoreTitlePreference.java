package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.StyleablePreference;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.utils.SygicLangHelper;
import com.sygic.aura.utils.SygicTextUtils;

public class CoreTitlePreference extends Preference implements StyleablePreference {
    private CharSequence mTitleCoreResource;

    public CoreTitlePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTitle();
    }

    public CoreTitlePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitle();
    }

    public CoreTitlePreference(Context context) {
        super(context);
        initTitle();
    }

    private void initTitle() {
        CharSequence title = getTitle();
        this.mTitleCoreResource = title;
        if (title != null) {
            String strTitle = ResourceManager.getCoreString(title.toString());
            if (shouldCapitalizeTitle() && SygicLangHelper.isEngLangString(SettingsManager.nativeGetSelectedLanguage())) {
                strTitle = SygicTextUtils.capitalizeEachWord(strTitle);
            }
            setTitle(strTitle);
        }
        CharSequence summary = getSummary();
        if (summary != null) {
            setSummary(ResourceManager.getCoreString(summary.toString()));
        }
    }

    public void style(View view) {
    }

    protected CharSequence getTitleCoreResource() {
        return this.mTitleCoreResource;
    }

    protected boolean shouldCapitalizeTitle() {
        return true;
    }
}
