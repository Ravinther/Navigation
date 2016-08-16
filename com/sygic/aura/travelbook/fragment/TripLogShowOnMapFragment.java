package com.sygic.aura.travelbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.travelbook.TravelBookManager;
import com.sygic.aura.views.font_specials.SToolbar;

public class TripLogShowOnMapFragment extends AbstractScreenFragment {
    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165993);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BubbleManager.getInstance().setEnabled(false);
        TravelBookManager.nativeShowOnMap(getArguments().getInt("log_index"));
        MapOverlayFragment.setMode(getActivity(), Mode.TRIPLOG_SHOW_ON_MAP);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(2130903131, container, false);
    }

    public void onDestroy() {
        super.onDestroy();
        TravelBookManager.nativeDisableShowOnMap();
        BubbleManager.getInstance().setEnabled(true);
    }
}
