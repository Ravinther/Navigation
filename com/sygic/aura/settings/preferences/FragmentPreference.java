package com.sygic.aura.settings.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.fragments.SettingsFragment;

public class FragmentPreference extends CoreTitlePreference {
    private TextView mBatch;
    private String mClassName;
    private String mData;
    private boolean mIsUpdatable;
    private String mTitle;

    public FragmentPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPref(context, attrs);
    }

    public FragmentPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPref(context, attrs);
    }

    public FragmentPreference(Context context) {
        this(context, null);
    }

    private void initPref(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.FragmentPreference, 0, 0);
            this.mTitle = String.valueOf(getTitle());
            this.mClassName = a.getString(0);
            this.mData = a.getString(1);
            this.mIsUpdatable = a.getBoolean(2, false);
            a.recycle();
        }
        if (this.mIsUpdatable) {
            setWidgetLayoutResource(2130903295);
        }
    }

    protected void onBindView(View view) {
        try {
            setSummary(getPersistedString(null));
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        if (this.mIsUpdatable) {
            boolean hasUpdate;
            this.mBatch = (TextView) view.findViewById(2131624635);
            if (SettingsManager.nativeGetUpdateNumber() > 0) {
                hasUpdate = true;
            } else {
                hasUpdate = false;
            }
            if (hasUpdate) {
                this.mBatch.setVisibility(0);
            } else {
                this.mBatch.setVisibility(4);
            }
        }
        super.onBindView(view);
    }

    protected void onClick() {
        try {
            SygicAnalyticsLogger.getAnalyticsEvent(getContext(), EventType.SETTINGS_CATEGORY).setName("Settings category").setValue("Destination", getTitleCoreResource()).logAndRecycle();
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, this.mTitle);
            bundle.putString(SettingsFragment.ARG_KEY, getKey());
            if (this.mData != null) {
                bundle.putString("fragment_data", this.mData);
            }
            if (this.mIsUpdatable) {
                bundle.putBoolean("fragment_has_update", SettingsManager.nativeGetUpdateNumber() > 0);
            }
            Class fragmentClass = Class.forName(this.mClassName);
            if (AbstractScreenFragment.class.isAssignableFrom(fragmentClass)) {
                Fragments.add((Activity) getContext(), fragmentClass, this.mTitle, bundle);
                if (this.mIsUpdatable) {
                    this.mBatch.setVisibility(4);
                    return;
                }
                return;
            }
            throw new ClassNotFoundException("Class is not extending AbstractScreenFragment class");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
