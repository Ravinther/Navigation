package com.sygic.aura.pluginmanager.plugin.shortcut;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;

public class ContactsShortcutPlugin extends ShortcutPlugin {
    public ContactsShortcutPlugin(Resources res, PluginCallback callback) {
        super(ResourceManager.getCoreString(res, 2131165532), 2131034152, callback);
    }

    protected WidgetItem createWidget() {
        return null;
    }

    public int getPageIndex() {
        return 2;
    }
}
