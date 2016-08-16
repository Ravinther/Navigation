package com.sygic.widget.places;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.sygic.widget.TrafficWidgetProvider;
import com.sygic.widget.places.data.PlaceEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlacesFakeAdapter {
    private List<PlaceEntry> mPlaces;
    private SharedPreferences mSharedPreferences;

    public PlacesFakeAdapter(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(context.getString(2131166774), 0);
        this.mPlaces = new ArrayList();
        updatePlaces(context);
    }

    public List<PlaceEntry> getPlaces() {
        return this.mPlaces;
    }

    public void updatePlaces(Context context) {
        Set<String> stringSet = this.mSharedPreferences.getStringSet(TrafficWidgetProvider.PREFERENCE_PLACES_KEY, null);
        this.mPlaces.clear();
        if (stringSet != null) {
            for (String item : stringSet) {
                PlaceEntry entry = PlaceEntry.fromJSON(item);
                if (entry != null) {
                    this.mPlaces.add(entry);
                }
            }
        }
        addItem(TrafficWidgetProvider.PREFERENCE_WORK_KEY);
        addItem(TrafficWidgetProvider.PREFERENCE_PARKING_KEY);
        addItem(TrafficWidgetProvider.PREFERENCE_HOME_KEY);
    }

    public void addItem(String strKey) {
        String strItem = this.mSharedPreferences.getString(strKey, null);
        if (!TextUtils.isEmpty(strItem)) {
            this.mPlaces.add(0, PlaceEntry.fromJSON(strItem));
        }
    }

    public Set<String> getPlacesStringSet() {
        return this.mSharedPreferences.getStringSet(TrafficWidgetProvider.PREFERENCE_PLACES_KEY, new HashSet());
    }

    public void savePlaces(HashSet<String> places) {
        this.mSharedPreferences.edit().putStringSet(TrafficWidgetProvider.PREFERENCE_PLACES_KEY, places).apply();
    }

    public void saveItem(String strKey, String item) {
        this.mSharedPreferences.edit().putString(strKey, item).apply();
    }

    public void clearItem(String strKey) {
        this.mSharedPreferences.edit().remove(strKey).apply();
    }

    public void showPlace(String key, boolean value) {
        this.mSharedPreferences.edit().putBoolean(key, value).apply();
    }
}
