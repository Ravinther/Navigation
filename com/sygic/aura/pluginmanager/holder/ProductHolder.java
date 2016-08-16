package com.sygic.aura.pluginmanager.holder;

import android.view.View;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.product.ProductPlugin;

public class ProductHolder extends PluginHolder {
    public ProductHolder(View view) {
        super(view);
    }

    protected void updateRightContainer(Plugin plugin) {
        ProductPlugin productPlugin = (ProductPlugin) plugin;
        if (!productPlugin.isPaid() || productPlugin.isBought()) {
            super.updateRightContainer(plugin);
            return;
        }
        this.mUnlockButton.setVisibility(0);
        this.mToggleImageView.setVisibility(8);
    }

    public boolean isValid(Plugin plugin) {
        return plugin instanceof ProductPlugin;
    }
}
