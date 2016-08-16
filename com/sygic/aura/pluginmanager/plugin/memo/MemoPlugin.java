package com.sygic.aura.pluginmanager.plugin.memo;

import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;

public abstract class MemoPlugin extends Plugin {
    public MemoPlugin(WidgetItem widget, String name, int iconRes, PluginCallback callback) {
        super(widget, name, iconRes, callback);
    }
}
