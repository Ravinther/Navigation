package com.sygic.aura.pluginmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.pluginmanager.holder.ViewHolder;
import com.sygic.aura.pluginmanager.plugin.PluginWrapper;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class PluginsAdapter extends ArrayAdapter<PluginWrapper> {
    private final LayoutInflater mInflater;

    public PluginsAdapter(Context context, List<PluginWrapper> items) {
        super(context, 0, items);
        this.mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        PluginWrapper item = (PluginWrapper) getItem(position);
        if (convertView == null) {
            int layout;
            switch (getItemViewType(position)) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    layout = 2130903149;
                    break;
                default:
                    layout = 2130903148;
                    break;
            }
            convertView = this.mInflater.inflate(layout, parent, false);
            holder = item.newHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!holder.isValid(item.getPlugin())) {
            holder = item.newHolder(convertView);
        }
        holder.update(item);
        return convertView;
    }

    public int getItemViewType(int position) {
        return ((PluginWrapper) getItem(position)).isSection() ? 0 : 1;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public boolean isEnabled(int position) {
        return getItemViewType(position) != 0;
    }
}
