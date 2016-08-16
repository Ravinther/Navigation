package com.sygic.aura.pluginmanager.plugin.memo;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;

public class WorkPlugin extends MemoPlugin {
    public WorkPlugin(WidgetItem widget, Resources res, PluginCallback callback) {
        super(widget, ResourceManager.getCoreString(res, 2131165540), 2131034168, callback);
    }

    protected WidgetItem createWidget() {
        WidgetItem widget = new WidgetItem(EWidgetType.widgetTypeWork, EWidgetSize.widgetSizeHalfRow);
        MemoItem work = MemoManager.nativeGetWork();
        if (work != null) {
            widget.setMemoId((long) ((int) work.getId()));
        }
        return widget;
    }
}
