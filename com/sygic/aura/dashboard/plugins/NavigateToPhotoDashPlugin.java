package com.sygic.aura.dashboard.plugins;

import android.content.res.Resources;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.helper.NavigateToPhotoHelper;
import com.sygic.aura.resources.ResourceManager;

public class NavigateToPhotoDashPlugin extends DashboardPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeNavigateToPhoto, EWidgetSize.widgetSizeHalfRow);
        }
        return new NavigateToPhotoDashPlugin(widgetItem);
    }

    private NavigateToPhotoDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034160;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165378);
    }

    public void performAction(DashboardFragment dashboardFragment) {
        NavigateToPhotoHelper.choosePhoto(dashboardFragment);
    }
}
