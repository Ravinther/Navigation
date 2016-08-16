package com.sygic.aura.pluginmanager.holder;

import android.view.View;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.PluginWrapper;
import com.sygic.aura.pluginmanager.plugin.memo.MemoPlugin;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;

public class PluginHolder extends ViewHolder {
    protected final SImageView mIconImageView;
    protected final STextView mTitleTextView;
    protected final SImageView mToggleImageView;
    protected final STextView mUnlockButton;

    public PluginHolder(View view) {
        this.mTitleTextView = (STextView) view.findViewById(2131624113);
        this.mIconImageView = (SImageView) view.findViewById(2131624277);
        this.mToggleImageView = (SImageView) view.findViewById(2131624361);
        this.mUnlockButton = (STextView) view.findViewById(2131624362);
    }

    public void update(PluginWrapper wrapper) {
        Plugin plugin = wrapper.getPlugin();
        this.mTitleTextView.setCoreText(plugin.getName());
        this.mIconImageView.setFontDrawableResource(plugin.getIconRes());
        updateRightContainer(plugin);
    }

    public boolean isValid(Plugin plugin) {
        return plugin instanceof MemoPlugin;
    }

    protected void updateRightContainer(Plugin plugin) {
        this.mUnlockButton.setVisibility(8);
        this.mToggleImageView.setVisibility(0);
        this.mToggleImageView.setImageResource(plugin.isAdded() ? 2131034331 : 2131034330);
    }
}
