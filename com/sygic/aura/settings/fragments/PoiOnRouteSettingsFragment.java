package com.sygic.aura.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.PoiSettingsManager;
import com.sygic.aura.settings.data.PoiSettingsManager.EPoiType;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import com.sygic.aura.settings.model.PoisAdapter;

public class PoiOnRouteSettingsFragment extends PoiBaseSettingsFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String text;
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (this.mGroupID == 0) {
            text = ResourceManager.getCoreString(view.getContext(), 2131165693);
        } else {
            text = ResourceManager.getCoreString(view.getContext(), 2131165694);
        }
        this.mTitleEnable.setText(text);
        return view;
    }

    protected PoisAdapter initListAdapter() {
        return new PoisAdapter(getActivity(), this, this.mItems, EPoiType.ePoiWarn, createAnalyticsName());
    }

    protected Class<? extends AbstractScreenFragment> getChildClass() {
        return PoiOnRouteSettingsFragment.class;
    }

    protected void setShowStatusGroup(int groupId, boolean bEnabled) {
        PoiSettingsManager.nativeSetShowWarnStatusGroup(groupId, bEnabled);
    }

    protected void setShowStatusAll(boolean bEnabled) {
        SettingsManager.nativeSetSettings(ESettingsType.eExitPoiOnRoute, bEnabled);
    }

    protected boolean getShowStatusAll() {
        if (this.mGroupID == 0) {
            return SettingsManager.nativeGetSettings(ESettingsType.eExitPoiOnRoute) == 1;
        } else {
            return PoiSettingsManager.nativeGetShowWarnStatusAll();
        }
    }

    protected boolean getShowStatusGroup(int groupID) {
        return PoiSettingsManager.nativeGetShowWarnStatusGroup(groupID);
    }

    protected String getPoiTag() {
        return "PoiOnRoute";
    }
}
