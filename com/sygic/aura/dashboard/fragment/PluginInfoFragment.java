package com.sygic.aura.dashboard.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.feature.automotive.InCarConnectionListener;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;

public class PluginInfoFragment extends AbstractScreenFragment implements InCarConnectionListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903117, container, false);
        STextView firstText = (STextView) view.findViewById(2131624234);
        STextView secondText = (STextView) view.findViewById(2131624235);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int text1 = bundle.getInt("first_text", 0);
            int text2 = bundle.getInt("second_text", 0);
            firstText.setCoreText(text1);
            if (text2 > 0) {
                secondText.setVisibility(0);
                secondText.setCoreText(text2);
            } else {
                secondText.setVisibility(8);
            }
        }
        InCarConnection.registerOnConnectionListener(this);
        return view;
    }

    public void onDestroyView() {
        InCarConnection.unregisterOnConnectionListener(this);
        super.onDestroyView();
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        if (toolbar != null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                toolbar.setTitle(bundle.getString(AbstractFragment.ARG_TITLE));
            }
        }
    }

    public void onConnectionChanged(boolean connected) {
        if (!connected) {
            performHomeAction();
        }
    }
}
