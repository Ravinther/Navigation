package com.sygic.aura.dashboard.plugins;

import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;

public class ContactDashPlugin extends MemoDashPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeContact, EWidgetSize.widgetSizeHalfRow);
        }
        return new ContactDashPlugin(widgetItem);
    }

    private ContactDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034152;
    }
}
