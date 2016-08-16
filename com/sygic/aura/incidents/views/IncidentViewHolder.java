package com.sygic.aura.incidents.views;

import android.view.View;
import com.sygic.aura.incidents.IncidentItemsHelper;
import com.sygic.aura.incidents.IncidentItemsHelper.IncidentItemType;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;

public class IncidentViewHolder {
    SImageView mIcon;
    IncidentItemType mIncidentType;
    STextView mTitle;

    public IncidentViewHolder(View root, IncidentItemType incidentType) {
        this.mTitle = (STextView) root.findViewById(2131624358);
        this.mIcon = (SImageView) root.findViewById(2131624357);
        this.mIncidentType = incidentType;
    }

    public IncidentItemType getIncidentType() {
        return this.mIncidentType;
    }

    public void updateContent(IncidentItemType incidentType) {
        this.mTitle.setCoreText(IncidentItemsHelper.getTitleResId(incidentType));
        this.mIcon.setFontDrawableResource(IncidentItemsHelper.getIconResId(incidentType));
    }
}
