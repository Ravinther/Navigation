package com.sygic.aura.settings.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.sygic.aura.settings.data.TwoListItemData;
import com.sygic.aura.views.font_specials.STextView;
import java.util.ArrayList;

public class TwoListAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private final ArrayList<TwoListItemData> mObjects;

    private class ViewHolder {
        STextView textLeft;
        STextView textRight;

        private ViewHolder() {
        }
    }

    public TwoListAdapter(Context context, ArrayList<TwoListItemData> objects) {
        this.mInflater = LayoutInflater.from(context);
        this.mObjects = objects;
    }

    public int getCount() {
        return this.mObjects.size();
    }

    public TwoListItemData getItem(int position) {
        return (TwoListItemData) this.mObjects.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.mInflater.inflate(2130903227, null);
            holder.textLeft = (STextView) convertView.findViewById(2131624512);
            holder.textRight = (STextView) convertView.findViewById(2131624513);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textLeft.setText(((TwoListItemData) this.mObjects.get(position)).getLeft());
        holder.textRight.setText(((TwoListItemData) this.mObjects.get(position)).getRight());
        return convertView;
    }
}
