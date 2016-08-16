package com.sygic.aura.pluginmanager.holder;

import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.PluginWrapper;

public abstract class ViewHolder {
    public abstract boolean isValid(Plugin plugin);

    public abstract void update(PluginWrapper pluginWrapper);
}
