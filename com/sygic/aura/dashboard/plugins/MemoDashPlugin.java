package com.sygic.aura.dashboard.plugins;

import android.content.res.Resources;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.helper.ParcelableStringSparseArray;
import java.util.ArrayList;
import java.util.List;

public abstract class MemoDashPlugin extends NavigableDashPlugin {
    protected MemoDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public String getPluginLabel(Resources resources) {
        return this.mWidgetItem.getName();
    }

    public void addToSparseArray(Resources resources, ParcelableStringSparseArray pluginNamesSparseArray) {
        List<String> pluginNamesList = (List) pluginNamesSparseArray.get(this.mWidgetItem.getType().ordinal());
        if (pluginNamesList == null) {
            pluginNamesList = new ArrayList();
            pluginNamesSparseArray.append(this.mWidgetItem.getType().ordinal(), pluginNamesList);
        }
        pluginNamesList.add(getPluginLabel(resources));
    }
}
