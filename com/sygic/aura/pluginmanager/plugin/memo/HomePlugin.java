package com.sygic.aura.pluginmanager.plugin.memo;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;

public class HomePlugin extends MemoPlugin {
    public HomePlugin(WidgetItem widget, Resources res, PluginCallback callback) {
        super(widget, ResourceManager.getCoreString(res, 2131165374), 2131034154, callback);
    }

    protected WidgetItem createWidget() {
        WidgetItem widget = new WidgetItem(EWidgetType.widgetTypeHome, EWidgetSize.widgetSizeHalfRow);
        MemoItem home = MemoManager.nativeGetHome();
        if (home != null) {
            widget.setMemoId((long) ((int) home.getId()));
        }
        return widget;
    }
}
