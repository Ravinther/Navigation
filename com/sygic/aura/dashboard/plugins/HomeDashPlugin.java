package com.sygic.aura.dashboard.plugins;

import android.content.Context;
import android.content.res.Resources;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.aura.resources.ResourceManager;

public class HomeDashPlugin extends UserSettableDashPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeHome, EWidgetSize.widgetSizeHalfRow);
        }
        return new HomeDashPlugin(widgetItem);
    }

    private HomeDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034154;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165374);
    }

    protected int addTitle() {
        return 2131165312;
    }

    protected long addMemo(Context context) {
        MemoManager.nativeRemoveAllMemoByType(context, EMemoType.memoHome);
        return MemoManager.nativeAddPlugin(context, getLongPosition(), this.mWidgetItem.getName(), EMemoType.memoHome);
    }
}
