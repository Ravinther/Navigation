package com.sygic.aura.pluginmanager.plugin;

import android.view.View;
import com.sygic.aura.pluginmanager.holder.ViewHolder;

public class PluginWrapper {
    private Plugin mPlugin;

    public PluginWrapper(Plugin plugin) {
        this.mPlugin = plugin;
    }

    public ViewHolder newHolder(View view) {
        return this.mPlugin.createHolder(view);
    }

    public boolean isSection() {
        return false;
    }

    public Plugin getPlugin() {
        return this.mPlugin;
    }
}
