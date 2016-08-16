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

public class BlackBoxPlugin extends ProductPlugin {
    public BlackBoxPlugin(WidgetItem widget, Resources res, PluginCallback callbacks) {
        super(widget, res, 2131165371, 2131034150, callbacks);
    }

    protected WidgetItem createWidget() {
        return new WidgetItem(EWidgetType.widgetTypeBlackBox, EWidgetSize.widgetSizeHalfRow);
    }

    public boolean isPaid() {
        return true;
    }

    public boolean isBought() {
        return WidgetManager.nativeIsBlackBoxAllowed();
    }

    public String getProductId() {
        return "blackbox";
    }

    public void handleClick(Activity activity) {
        if (InCarConnection.isInCarConnected()) {
            PluginManager.showInfoScreen(activity, 2131165528, 2131165529);
        } else {
            super.handleClick(activity);
        }
    }
}
