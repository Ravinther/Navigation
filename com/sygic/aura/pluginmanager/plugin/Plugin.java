package com.sygic.aura.pluginmanager.plugin;

import android.app.Activity;
import android.view.View;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.pluginmanager.holder.PluginHolder;
import com.sygic.aura.pluginmanager.holder.ViewHolder;

public abstract class Plugin {
    protected boolean mAdded;
    protected final PluginCallback mCallback;
    protected final int mIconRes;
    protected final String mName;
    protected final WidgetItem mWidget;

    public interface PluginCallback {
        void onPluginChanged();
    }

    protected abstract WidgetItem createWidget();

    public Plugin(WidgetItem widget, String name, int iconRes, PluginCallback callbacks) {
        this.mAdded = widget != null;
        if (widget == null) {
            widget = createWidget();
        }
        this.mWidget = widget;
        this.mName = name;
        this.mIconRes = iconRes;
        this.mCallback = callbacks;
    }

    protected ViewHolder createHolder(View view) {
        return new PluginHolder(view);
    }

    public void handleClick(Activity activity) {
        if (this.mAdded) {
            WidgetManager.nativeRemoveWidget(this.mWidget.getItemId());
            this.mAdded = false;
        } else {
            WidgetManager.nativeAddWidgetItem(this.mWidget);
            this.mAdded = true;
        }
        if (this.mCallback != null) {
            this.mCallback.onPluginChanged();
        }
    }

    public String getName() {
        return this.mName;
    }

    public int getIconRes() {
        return this.mIconRes;
    }

    public boolean isAdded() {
        return this.mAdded;
    }
}
