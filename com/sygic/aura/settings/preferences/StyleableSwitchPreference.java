package com.sygic.aura.settings.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.StyleablePreference;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.trial.fragments.PromotionFragment;
import com.sygic.aura.utils.SygicLangHelper;
import com.sygic.aura.utils.SygicTextUtils;

public class StyleableSwitchPreference extends CheckBoxPreference implements StyleablePreference {
    private boolean mIsPremium;

    /* renamed from: com.sygic.aura.settings.preferences.StyleableSwitchPreference.1 */
    class C16731 implements OnClickListener {
        C16731() {
        }

        public void onClick(View v) {
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "unlock_settings_" + StyleableSwitchPreference.this.getTitle());
            logParams.putString("category", "anui_settings_trial_unlock");
            logParams.putString("source", "unlock_settings_" + StyleableSwitchPreference.this.getKey());
            SygicAnalyticsLogger.logEvent(StyleableSwitchPreference.this.getContext(), EventType.UNLOCK, logParams);
            Fragments.add((Activity) StyleableSwitchPreference.this.getContext(), PromotionFragment.class, "fragment_promotion_tag", logParams);
        }
    }

    public StyleableSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPref(context, attrs);
    }

    public StyleableSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPref(context, attrs);
    }

    public StyleableSwitchPreference(Context context) {
        super(context);
        initPref(context, null);
    }

    private void initPref(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.StyleableSwitchPreference, 0, 0);
            this.mIsPremium = a.getBoolean(0, false);
            a.recycle();
        }
        String strTitle = ResourceManager.getCoreString(getTitle());
        if (SygicLangHelper.isEngLangString(SettingsManager.nativeGetSelectedLanguage())) {
            strTitle = SygicTextUtils.capitalizeEachWord(strTitle);
        }
        setTitle(strTitle);
        if (getSummary() != null) {
            setSummary(ResourceManager.getCoreString(String.valueOf(getSummary())));
        }
        if (showUnlock()) {
            setWidgetLayoutResource(2130903294);
            setEnabled(false);
            return;
        }
        setWidgetLayoutResource(2130903206);
    }

    private boolean showUnlock() {
        return this.mIsPremium && LicenseInfo.nativeIsTrialExpired();
    }

    public void style(View view) {
        if (showUnlock()) {
            TextView unlockButton = (TextView) view.findViewById(2131624634);
            if (unlockButton != null) {
                unlockButton.setEnabled(true);
                unlockButton.setOnClickListener(new C16731());
            }
        }
    }

    protected void onBindView(View view) {
        try {
            super.onBindView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
