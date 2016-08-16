package com.sygic.aura.incidents.interfaces;

import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.incidents.IncidentItemsHelper.IncidentItemType;

public interface ReportIncidentCallback extends FragmentResultCallback {
    void reportIncident(IncidentItemType incidentItemType);
}
