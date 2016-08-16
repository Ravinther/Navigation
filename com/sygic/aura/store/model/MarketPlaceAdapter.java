package com.sygic.aura.store.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.model.holder.StoreHolder;

public class MarketPlaceAdapter extends StoreAdapter {
    public MarketPlaceAdapter(Context context) {
        super(context, 2130903211);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = super.getView(position, convertView, parent);
        } else {
            view = convertView;
        }
        StoreHolder holder = (StoreHolder) view.getTag();
        StoreEntry item = (StoreEntry) getItem(position);
        if (item != null) {
            holder.updateContent(item);
        }
        return view;
    }
}
