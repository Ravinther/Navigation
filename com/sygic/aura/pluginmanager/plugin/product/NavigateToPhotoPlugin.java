package com.sygic.aura.pluginmanager.plugin.product;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;

public class NavigateToPhotoPlugin extends ProductPlugin {
    public NavigateToPhotoPlugin(WidgetItem widget, Resources res, PluginCallback callback) {
        super(widget, res, 2131165378, 2131034160, callback);
    }

    protected WidgetItem createWidget() {
        return new WidgetItem(EWidgetType.widgetTypeNavigateToPhoto, EWidgetSize.widgetSizeHalfRow);
    }

    public boolean isPaid() {
        return false;
    }
}
