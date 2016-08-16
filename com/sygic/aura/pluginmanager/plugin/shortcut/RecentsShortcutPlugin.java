package com.sygic.aura.pluginmanager.plugin.shortcut;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;

public class RecentsShortcutPlugin extends ShortcutPlugin {
    public RecentsShortcutPlugin(Resources res, PluginCallback callback) {
        super(ResourceManager.getCoreString(res, 2131165536), 2131034163, callback);
    }

    protected WidgetItem createWidget() {
        return null;
    }

    public int getPageIndex() {
        return 1;
    }
}
