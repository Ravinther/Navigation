package com.sygic.aura.dashboard.plugins;

import android.content.res.Resources;
import android.os.Bundle;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.hud.HUDFragment;
import com.sygic.aura.pluginmanager.PluginManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.fragment.ProductDetailFragment;

public class HudDashPlugin extends DashboardPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeHUD, EWidgetSize.widgetSizeHalfRow);
        }
        return new HudDashPlugin(widgetItem);
    }

    private HudDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034155;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165376);
    }

    protected boolean isLocked() {
        return !WidgetManager.nativeIsHudAllowed();
    }

    public void performAction(DashboardFragment f) {
        if (!isLocked()) {
            Fragments.add(f.getActivity(), HUDFragment.class, "fragment_hud_tag", null);
        } else if (InCarConnection.isInCarConnected()) {
            PluginManager.showInfoScreen(f.getActivity(), 2131165531, 2131165529, 2131165530);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(f.getActivity(), 2131165531));
            bundle.putString("product_id", "HUD");
            Fragments.add(f.getActivity(), ProductDetailFragment.class, "fragment_product_hud", bundle);
        }
    }
}
