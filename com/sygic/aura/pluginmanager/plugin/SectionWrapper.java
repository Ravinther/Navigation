package com.sygic.aura.pluginmanager.plugin;

import android.view.View;
import com.sygic.aura.pluginmanager.holder.SectionHolder;
import com.sygic.aura.pluginmanager.holder.ViewHolder;

public class SectionWrapper extends PluginWrapper {
    private final String mTitle;

    public SectionWrapper(String title) {
        super(null);
        this.mTitle = title;
    }

    public ViewHolder newHolder(View view) {
        return new SectionHolder(view);
    }

    public String getTitle() {
        return this.mTitle;
    }

    public boolean isSection() {
        return true;
    }
}
