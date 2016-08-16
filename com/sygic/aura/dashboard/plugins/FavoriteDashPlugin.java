package com.sygic.aura.dashboard.plugins;

import android.content.res.Resources;
import android.text.TextUtils;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.DashboardPluginAdapter.PluginViewHolder;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;

public class FavoriteDashPlugin extends MemoDashPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeFavorite, EWidgetSize.widgetSizeHalfRow);
        }
        return new FavoriteDashPlugin(widgetItem);
    }

    private FavoriteDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034162;
    }

    public void updateView(Resources resources, PluginViewHolder pluginViewHolder) {
        super.updateView(resources, pluginViewHolder);
        String extName = this.mWidgetItem.getExtName();
        if (!TextUtils.isEmpty(extName)) {
            pluginViewHolder.image.setFontDrawableChar(extName);
        }
    }
}
