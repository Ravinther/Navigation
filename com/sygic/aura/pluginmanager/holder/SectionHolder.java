package com.sygic.aura.pluginmanager.holder;

import android.view.View;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.PluginWrapper;
import com.sygic.aura.pluginmanager.plugin.SectionWrapper;
import com.sygic.aura.views.font_specials.STextView;

public class SectionHolder extends ViewHolder {
    private final STextView mTitleTextView;

    public SectionHolder(View view) {
        this.mTitleTextView = (STextView) view.findViewById(2131624113);
    }

    public void update(PluginWrapper wrapper) {
        this.mTitleTextView.setCoreText(((SectionWrapper) wrapper).getTitle());
    }

    public boolean isValid(Plugin plugin) {
        return plugin == null;
    }
}
