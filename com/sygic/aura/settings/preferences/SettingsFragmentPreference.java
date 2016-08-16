package com.sygic.aura.settings.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.settings.trial.fragments.PromotionFragment;
import com.sygic.aura.views.font_specials.STextView;

public class SettingsFragmentPreference extends IconTextPreference {
    private String mClassName;
    private boolean mHasLastDivider;
    private boolean mIsPremium;
    private boolean mIsRegisteredOnChange;
    private int mPremiumFeaturesCount;
    private String mTitle;
    private int mXmlId;

    /* renamed from: com.sygic.aura.settings.preferences.SettingsFragmentPreference.1 */
    class C16721 implements OnClickListener {
        C16721() {
        }

        public void onClick(View v) {
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "unlock_settings_" + SettingsFragmentPreference.this.getKey());
            logParams.putString("category", "anui_settings_trial_unlock");
            logParams.putString("source", "unlock_settings_" + SettingsFragmentPreference.this.getKey());
            SygicAnalyticsLogger.logEvent(SettingsFragmentPreference.this.getContext(), EventType.UNLOCK, logParams);
            Fragments.add((Activity) SettingsFragmentPreference.this.getContext(), PromotionFragment.class, "fragment_promotion_tag", logParams);
        }
    }

    public SettingsFragmentPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPref(context, attrs);
    }

    public SettingsFragmentPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPref(context, attrs);
    }

    public SettingsFragmentPreference(Context context) {
        this(context, null);
    }

    private boolean showPremiumFeatureLabel() {
        return this.mPremiumFeaturesCount > 0 && LicenseInfo.nativeIsTrialExpired();
    }

    private void initPref(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.SettingsFragmentPreference, 0, 0);
            this.mTitle = String.valueOf(getTitle());
            this.mXmlId = a.getResourceId(1, 0);
            this.mHasLastDivider = a.getBoolean(2, true);
            this.mIsPremium = a.getBoolean(0, false);
            this.mIsRegisteredOnChange = a.getBoolean(3, false);
            this.mClassName = a.getString(4);
            this.mPremiumFeaturesCount = a.getInt(5, 0);
            a.recycle();
        }
        if (this.mIsPremium && LicenseInfo.nativeIsTrialExpired()) {
            setWidgetLayoutResource(2130903294);
            setEnabled(false);
        }
        if (showPremiumFeatureLabel()) {
            setWidgetLayoutResource(2130903262);
        }
    }

    protected void onBindView(View view) {
        if (showPremiumFeatureLabel()) {
            STextView premiumFeatures = (STextView) view.findViewById(2131624560);
            premiumFeatures.setVisibility(0);
            premiumFeatures.setText("" + this.mPremiumFeaturesCount);
        }
        setSummary(getPersistedString(null));
        super.onBindView(view);
    }

    public void style(View view) {
        if (this.mIsPremium && LicenseInfo.nativeIsTrialExpired()) {
            TextView unlockButton = (TextView) view.findViewById(2131624634);
            if (unlockButton != null) {
                unlockButton.setEnabled(true);
                unlockButton.setOnClickListener(new C16721());
            }
        }
    }

    protected void onClick() {
        SygicAnalyticsLogger.getAnalyticsEvent(getContext(), EventType.SETTINGS_CATEGORY).setName("Settings category").setValue("Destination", getTitleCoreResource()).logAndRecycle();
        Bundle bundle = new Bundle();
        bundle.putInt(SettingsFragment.ARG_XML, this.mXmlId);
        bundle.putString(AbstractFragment.ARG_TITLE, this.mTitle);
        bundle.putBoolean(SettingsFragment.ARG_LAST_DIVIDER, this.mHasLastDivider);
        bundle.putBoolean(SettingsFragment.ARG_CHANGE_SETTINGS, this.mIsRegisteredOnChange);
        try {
            Class fragmentClass = SettingsFragment.class;
            if (this.mClassName != null) {
                fragmentClass = Class.forName(this.mClassName);
                if (!SettingsFragment.class.isAssignableFrom(fragmentClass)) {
                    throw new ClassNotFoundException("Class is not extending SettingsFragment class");
                }
            }
            Fragments.add((Activity) getContext(), fragmentClass, "fragment_settings_tag", bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
