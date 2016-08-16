package com.sygic.aura.incidents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.incidents.IncidentItemsHelper.IncidentItemType;
import com.sygic.aura.incidents.views.IncidentViewHolder;

public class IncidentsAdapter extends ArrayAdapter<IncidentItemType> {
    private final LayoutInflater mInflater;

    public IncidentsAdapter(Context context, IncidentItemType[] incidentsValues) {
        super(context, 2130903144, incidentsValues);
        this.mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IncidentViewHolder viewHolder;
        IncidentItemType incidentType = (IncidentItemType) getItem(position);
        if (convertView == null) {
            convertView = this.mInflater.inflate(2130903144, parent, false);
            viewHolder = new IncidentViewHolder(convertView, incidentType);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IncidentViewHolder) convertView.getTag();
        }
        viewHolder.updateContent(incidentType);
        return convertView;
    }
}
