package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.exception.InvalidLocationException;
import com.sygic.sdk.api.exception.InvalidNameException;
import com.sygic.sdk.api.model.Poi;
import com.sygic.sdk.api.util.Util;
import java.util.ArrayList;

public class ApiFavorites {
    public static ArrayList<Poi> getFavoritesList(boolean searchAddress, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nGetFavoritesList(searchAddress, maxTime));
    }

    public static String addStopOffPointsToFavorites(Poi poi, int maxTime) throws InvalidLocationException {
        return Api.nAddStopOffPointsToFavorites(poi, maxTime);
    }

    public static String removeFavoriteFromList(Poi favorite, int maxTime) throws InvalidNameException {
        return Api.nRemoveFavoriteFromList(favorite, maxTime);
    }
}
