package com.sygic.aura.pluginmanager.plugin.memo;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;

public class ParkingPlugin extends MemoPlugin {
    public ParkingPlugin(WidgetItem widget, Resources res, PluginCallback callback) {
        super(widget, ResourceManager.getCoreString(res, 2131165379), 2131034161, callback);
    }

    protected WidgetItem createWidget() {
        WidgetItem widget = new WidgetItem(EWidgetType.widgetTypeParking, EWidgetSize.widgetSizeHalfRow);
        MemoItem carLocation = MemoManager.nativeGetParking();
        if (carLocation != null) {
            widget.setMemoId((long) ((int) carLocation.getId()));
        }
        return widget;
    }
}
