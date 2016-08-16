package com.sygic.aura.settings.preferences.road_numbers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.settings.fragments.VoiceOverviewFragment;
import com.sygic.aura.settings.preferences.StyleableSwitchPreference;

public class RoadNumbersSwitchPreference extends StyleableSwitchPreference {
    private View mCheckableView;
    private boolean mEnabled;

    public RoadNumbersSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoadNumbersSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoadNumbersSwitchPreference(Context context) {
        super(context);
    }

    public void langChanged(boolean isTTS) {
        String summary = null;
        if (!isTTS) {
            summary = ResourceManager.getCoreString(getContext(), 2131165884);
        }
        setSummary(summary);
        if (isEnabled()) {
            this.mCheckableView.setEnabled(isTTS);
        }
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        this.mEnabled = checkIfIsEnabled(this);
        this.mCheckableView = findCheckableView(view);
        String summary = null;
        if (!this.mEnabled) {
            summary = ResourceManager.getCoreString(getContext(), 2131165884);
        }
        setSummary(summary);
        if (isEnabled()) {
            this.mCheckableView.setEnabled(this.mEnabled);
        }
    }

    protected void onClick() {
        if (this.mEnabled) {
            super.onClick();
        } else {
            openVoiceFragment(getContext());
        }
    }

    private boolean checkIfIsEnabled(Preference preference) {
        return preference.getSharedPreferences().getBoolean(preference.getContext().getString(2131166730), true);
    }

    private void openVoiceFragment(Context context) {
        String title = context.getString(2131165828);
        Bundle bundle = new Bundle();
        bundle.putString(AbstractFragment.ARG_TITLE, title);
        bundle.putString(SettingsFragment.ARG_KEY, context.getString(2131166728));
        Fragments.add((Activity) context, VoiceOverviewFragment.class, title, bundle);
    }

    private View findCheckableView(View view) {
        if (view instanceof Checkable) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup grp = (ViewGroup) view;
            for (int index = 0; index < grp.getChildCount(); index++) {
                View checkableView = findCheckableView(grp.getChildAt(index));
                if (checkableView != null) {
                    return checkableView;
                }
            }
        }
        return null;
    }
}
