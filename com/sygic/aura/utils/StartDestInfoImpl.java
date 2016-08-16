package com.sygic.aura.utils;

import com.sygic.aura.analytics.InfinarioAnalyticsLogger.StartDestInfo;
import com.sygic.aura.search.model.data.ListItem;

public class StartDestInfoImpl extends StartDestInfo {
    public static StartDestInfoImpl from(ListItem[] startListItems, ListItem[] endListItems) {
        return new StartDestInfoImpl(getDisplayName(startListItems, 0), getDisplayName(startListItems, 1), getDisplayName(startListItems, 2), getDisplayName(endListItems, 0), getDisplayName(endListItems, 1), getDisplayName(endListItems, 2));
    }

    protected StartDestInfoImpl(String startCountry, String startCity, String startStreet, String endCountry, String endCity, String endStreet) {
        super(startCountry, startCity, startStreet, endCountry, endCity, endStreet);
    }

    private static String getDisplayName(ListItem[] listItems, int typeIndex) {
        if (listItems == null || typeIndex >= listItems.length || listItems[typeIndex] == null) {
            return null;
        }
        return listItems[typeIndex].getDisplayName();
    }
}
