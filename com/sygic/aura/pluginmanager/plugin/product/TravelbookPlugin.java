package com.sygic.aura.pluginmanager.plugin.product;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.trial.fragments.PromotionFragment;

public class TravelbookPlugin extends ProductPlugin {
    public TravelbookPlugin(WidgetItem widget, Resources res, PluginCallback callbacks) {
        super(widget, res, 2131165539, 2131034167, callbacks);
    }

    protected WidgetItem createWidget() {
        return new WidgetItem(EWidgetType.widgetTypeTravelBook, EWidgetSize.widgetSizeHalfRow);
    }

    protected void showProductPage(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(this.mResources, 2131165471));
        Fragments.add(activity, PromotionFragment.class, "fragment_product_speedcam", bundle);
    }

    public boolean isPaid() {
        return true;
    }

    public boolean isBought() {
        return !LicenseInfo.nativeIsTrialExpired();
    }

    public String getProductId() {
        return "travelbook";
    }
}
