package com.sygic.aura.dashboard.plugins;

import android.content.res.Resources;
import android.os.Bundle;
import com.sygic.aura.blackbox.fragment.BlackBoxFragment;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.pluginmanager.PluginManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.fragment.ProductDetailFragment;

public class BlackBoxDashPlugin extends DashboardPlugin {
    private boolean mRecording;

    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeBlackBox, EWidgetSize.widgetSizeHalfRow);
        }
        return new BlackBoxDashPlugin(widgetItem);
    }

    private BlackBoxDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
        this.mRecording = false;
    }

    public int getPluginImage() {
        if (this.mRecording) {
            return 2131034151;
        }
        return 2131034150;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165371);
    }

    public void setRecording(boolean recording) {
        this.mRecording = recording;
    }

    protected boolean isLocked() {
        return !WidgetManager.nativeIsBlackBoxAllowed();
    }

    public void performAction(DashboardFragment f) {
        if (!isLocked()) {
            BlackBoxFragment frag = BlackBoxFragment.getBlackBoxFragment(f.getFragmentManager());
            if (frag == null) {
                f.startBlackboxRecording(SygicHelper.getFragmentActivityWrapper(), this);
            } else {
                frag.finishRecording();
            }
        } else if (InCarConnection.isInCarConnected()) {
            PluginManager.showInfoScreen(f.getActivity(), 2131165528, 2131165529);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(f.getActivity(), 2131165528));
            bundle.putString("product_id", "Blackbox");
            Fragments.add(f.getActivity(), ProductDetailFragment.class, "fragment_product_blackbox", bundle);
        }
    }
}
