package com.sygic.aura.pluginmanager.plugin.product;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.pluginmanager.holder.ProductHolder;
import com.sygic.aura.pluginmanager.holder.ViewHolder;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.fragment.ProductDetailFragment;

public abstract class ProductPlugin extends Plugin {
    protected final Resources mResources;

    public abstract boolean isPaid();

    public ProductPlugin(WidgetItem widget, Resources resources, int nameId, int iconRes, PluginCallback callbacks) {
        super(widget, ResourceManager.getCoreString(resources, nameId), iconRes, callbacks);
        this.mResources = resources;
    }

    public void handleClick(Activity activity) {
        if (!isPaid() || isBought()) {
            super.handleClick(activity);
        } else {
            showProductPage(activity);
        }
    }

    protected void showProductPage(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString("product_id", getProductId());
        bundle.putString(AbstractFragment.ARG_TITLE, this.mName);
        Fragments.add(activity, ProductDetailFragment.class, this.mName, bundle);
    }

    protected ViewHolder createHolder(View view) {
        return new ProductHolder(view);
    }

    public boolean isBought() {
        return false;
    }

    public String getProductId() {
        return null;
    }
}
