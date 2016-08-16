package com.sygic.aura.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.PoiSettingsManager;
import com.sygic.aura.settings.data.PoiSettingsManager.EPoiType;
import com.sygic.aura.settings.model.PoisAdapter;

public class PoiSettingsFragment extends PoiBaseSettingsFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.mTitleEnable.setText(ResourceManager.getCoreString(view.getContext(), 2131165691));
        return view;
    }

    protected PoisAdapter initListAdapter() {
        return new PoisAdapter(getActivity(), this, this.mItems, EPoiType.ePoiDisplay, createAnalyticsName());
    }

    protected Class<? extends AbstractScreenFragment> getChildClass() {
        return PoiSettingsFragment.class;
    }

    protected void setShowStatusGroup(int groupId, boolean bEnabled) {
        PoiSettingsManager.nativeSetShowStatusGroup(groupId, bEnabled);
    }

    protected void setShowStatusAll(boolean bEnabled) {
        PoiSettingsManager.nativeSetShowStatusAll(bEnabled);
    }

    protected boolean getShowStatusAll() {
        return PoiSettingsManager.nativeGetShowStatusAll();
    }

    protected boolean getShowStatusGroup(int groupID) {
        return PoiSettingsManager.nativeGetShowStatusGroup(groupID);
    }

    protected String getPoiTag() {
        return "PointsOfInterest";
    }
}
