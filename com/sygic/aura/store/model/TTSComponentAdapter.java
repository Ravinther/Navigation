package com.sygic.aura.store.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.base.C1799R;

public class TTSComponentAdapter extends SingleChoiceComponentsAdapter {
    public TTSComponentAdapter(Context context) {
        super(context);
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return EViewType.TYPE_HEADER.ordinal();
        }
        return super.getItemViewType(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) != EViewType.TYPE_HEADER.ordinal()) {
            return super.getView(position, convertView, parent);
        }
        View view = this.mInflater.inflate(2130903221, parent, false);
        TextView tv = (TextView) view.findViewById(C1799R.id.title);
        if (tv == null) {
            return view;
        }
        tv.setText(ResourceManager.getCoreString(getContext(), 2131165892));
        return view;
    }
}
