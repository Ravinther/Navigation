package com.sygic.aura.pluginmanager.plugin.product;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;

public class SosPlugin extends ProductPlugin {
    public SosPlugin(WidgetItem widget, Resources res, PluginCallback callback) {
        super(widget, res, 2131165383, 2131034166, callback);
    }

    protected WidgetItem createWidget() {
        return new WidgetItem(EWidgetType.widgetTypeSOS, EWidgetSize.widgetSizeHalfRow);
    }

    public boolean isPaid() {
        return false;
    }
}
