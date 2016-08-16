package com.sygic.aura.poi.nearbypoi.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;

public class NearbyPoiDashGroupAdapter extends ArrayAdapter<NearbyPoiGroup> {
    private final LayoutInflater mInflater;

    private class ViewHolder {
        private final SImageView mIconImageView;
        private final STextView mTitleTextView;

        public ViewHolder(View view) {
            this.mIconImageView = (SImageView) view.findViewById(2131624277);
            this.mTitleTextView = (STextView) view.findViewById(2131624113);
        }

        public void update(NearbyPoiGroup group) {
            this.mIconImageView.setFontDrawableChar(group.getIconChar());
            Drawable bg = this.mIconImageView.getBackground();
            if (bg != null && (bg instanceof GradientDrawable)) {
                ((GradientDrawable) bg).setColor(group.getIconColor());
            }
            this.mTitleTextView.setText(group.getName());
        }
    }

    public NearbyPoiDashGroupAdapter(Context context, NearbyPoiGroup[] categories) {
        super(context, 0, categories);
        this.mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(2130903147, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update((NearbyPoiGroup) getItem(position));
        return convertView;
    }
}
