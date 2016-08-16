package com.sygic.aura.pluginmanager.holder;

import android.view.View;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.shortcut.ShortcutPlugin;

public class ShortcutHolder extends PluginHolder {
    public ShortcutHolder(View view) {
        super(view);
    }

    protected void updateRightContainer(Plugin plugin) {
        this.mUnlockButton.setVisibility(8);
        this.mToggleImageView.setVisibility(8);
    }

    public boolean isValid(Plugin plugin) {
        return plugin instanceof ShortcutPlugin;
    }
}
