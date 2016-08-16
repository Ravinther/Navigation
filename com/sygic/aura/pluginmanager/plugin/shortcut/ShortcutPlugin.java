package com.sygic.aura.pluginmanager.plugin.shortcut;

import android.app.Activity;
import android.view.View;
import com.sygic.aura.pluginmanager.holder.ShortcutHolder;
import com.sygic.aura.pluginmanager.holder.ViewHolder;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;

public abstract class ShortcutPlugin extends Plugin {

    public interface ShortcutPluginCallback extends PluginCallback {
        void onGoToFavourites(int i);
    }

    public abstract int getPageIndex();

    public ShortcutPlugin(String name, int iconRes, PluginCallback callback) {
        super(null, name, iconRes, callback);
    }

    public void handleClick(Activity activity) {
        if (this.mCallback != null && (this.mCallback instanceof ShortcutPluginCallback)) {
            ((ShortcutPluginCallback) this.mCallback).onGoToFavourites(getPageIndex());
        }
    }

    protected ViewHolder createHolder(View view) {
        return new ShortcutHolder(view);
    }
}
