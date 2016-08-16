package com.sygic.aura.settings.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.VoiceEntry;
import com.sygic.base.C1799R;

public class VoicesAdapter extends ArrayAdapter<VoiceEntry> {
    private String mAdvanced;
    private String mStandard;

    private class ViewHolder {
        private final ImageView icon;
        private final TextView summary;
        private final TextView title;

        private ViewHolder(View rootView) {
            this.title = (TextView) rootView.findViewById(C1799R.id.title);
            this.summary = (TextView) rootView.findViewById(2131624505);
            this.icon = (ImageView) rootView.findViewById(2131624504);
        }
    }

    public VoicesAdapter(Context context) {
        super(context, 2130903223, C1799R.id.title);
        this.mStandard = ResourceManager.getCoreString(getContext(), 2131165885);
        this.mAdvanced = ResourceManager.getCoreString(getContext(), 2131165883);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
            holder = new ViewHolder(convertView, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VoiceEntry entry = (VoiceEntry) getItem(position);
        holder.title.setText(entry.getLanguage());
        holder.summary.setText(entry.getPersonName() + " (" + (entry.isTTS() ? this.mAdvanced : this.mStandard) + ")");
        int iconDrawableId = getContext().getResources().getIdentifier("flg_".concat(entry.getISO().toLowerCase()), "drawable", getContext().getPackageName());
        if (iconDrawableId == 0) {
            iconDrawableId = tryFindFunnyIcon(entry.getFolder());
        }
        if (iconDrawableId != 0) {
            holder.icon.setImageDrawable(getContext().getResources().getDrawable(iconDrawableId));
        } else {
            holder.icon.setImageDrawable(null);
        }
        return convertView;
    }

    private int tryFindFunnyIcon(String voiceName) {
        return getContext().getResources().getIdentifier("funny_".concat(voiceName.toLowerCase()), "drawable", getContext().getPackageName());
    }

    public VoiceEntry[] loadInstalledVoices() {
        return SettingsManager.nativeGetInstalledVoices();
    }
}
