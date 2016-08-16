package com.sygic.aura.dashboard.plugins;

import android.content.res.Resources;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.sos.fragment.SosFragment;

public class SosDashPlugin extends DashboardPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeSOS, EWidgetSize.widgetSizeHalfRow);
        }
        return new SosDashPlugin(widgetItem);
    }

    private SosDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034166;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165383);
    }

    public void performAction(DashboardFragment f) {
        if (SosFragment.getSosFragment(f.getFragmentManager()) == null) {
            Fragments.add(f.getActivity(), SosFragment.class, "fragment_sos_tag", 17432576, 17432577);
        }
    }
}
