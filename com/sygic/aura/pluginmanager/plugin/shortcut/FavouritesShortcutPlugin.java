package com.sygic.aura.pluginmanager.plugin.shortcut;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;

public class FavouritesShortcutPlugin extends ShortcutPlugin {
    public FavouritesShortcutPlugin(Resources res, PluginCallback callback) {
        super(ResourceManager.getCoreString(res, 2131165533), 2131034153, callback);
    }

    protected WidgetItem createWidget() {
        return null;
    }

    public int getPageIndex() {
        return 0;
    }
}
