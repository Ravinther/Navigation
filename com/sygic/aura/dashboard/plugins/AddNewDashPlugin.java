package com.sygic.aura.dashboard.plugins;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.pluginmanager.PluginManagerFragment;
import com.sygic.aura.resources.ResourceManager;

public class AddNewDashPlugin extends DashboardPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeAddNew, EWidgetSize.widgetSizeHalfRow);
        }
        return new AddNewDashPlugin(widgetItem);
    }

    private AddNewDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034148;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165369);
    }

    public void performAction(DashboardFragment f) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("exclusion_filter", f.getSortedPluginNamesByType());
        Fragments.add(f.getActivity(), PluginManagerFragment.class, "fragment_plugin_manager_tag", bundle, true, f, 17432576, 17432577);
    }

    public boolean persist() {
        return false;
    }

    public boolean delete(Context context) {
        return false;
    }

    public boolean isEditable() {
        return false;
    }
}
