package com.sygic.aura.dashboard.plugins;

import android.content.res.Resources;
import android.os.Bundle;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.trial.fragments.PromotionFragment;
import com.sygic.aura.travelbook.fragment.TravelbookFragment;

public class TravelbookDashPlugin extends DashboardPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeTravelBook, EWidgetSize.widgetSizeHalfRow);
        }
        return new TravelbookDashPlugin(widgetItem);
    }

    private TravelbookDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034167;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165384);
    }

    protected boolean isLocked() {
        return LicenseInfo.nativeIsTrialExpired();
    }

    public void performAction(DashboardFragment f) {
        if (isLocked()) {
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(f.getActivity(), 2131165471));
            Fragments.add(f.getActivity(), PromotionFragment.class, "fragment_promotion_tag", bundle, true, f, 17432576, 17432577);
            return;
        }
        Fragments.add(f.getActivity(), TravelbookFragment.class, "fragment_travelbook_tag", null, true, f, 17432576, 17432577);
    }
}
