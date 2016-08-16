package com.sygic.aura.pluginmanager.plugin.product;

import android.app.Activity;
import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.pluginmanager.PluginManager;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;

public class HUDPlugin extends ProductPlugin {
    public HUDPlugin(WidgetItem widget, Resources res, PluginCallback callback) {
        super(widget, res, 2131165376, 2131034155, callback);
    }

    protected WidgetItem createWidget() {
        return new WidgetItem(EWidgetType.widgetTypeHUD, EWidgetSize.widgetSizeHalfRow);
    }

    public boolean isPaid() {
        return true;
    }

    public boolean isBought() {
        return WidgetManager.nativeIsHudAllowed();
    }

    public String getProductId() {
        return "hud";
    }

    public void handleClick(Activity activity) {
        if (InCarConnection.isInCarConnected()) {
            PluginManager.showInfoScreen(activity, 2131165531, 2131165529, 2131165530);
        } else {
            super.handleClick(activity);
        }
    }
}
