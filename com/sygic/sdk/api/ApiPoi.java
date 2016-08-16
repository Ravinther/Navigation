package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.exception.InvalidLocationException;
import com.sygic.sdk.api.model.Poi;
import com.sygic.sdk.api.model.PoiCategory;
import com.sygic.sdk.api.model.Position;
import com.sygic.sdk.api.util.Util;
import java.util.ArrayList;

public class ApiPoi {
    public static String addPoi(Poi poi, int maxTime) throws InvalidLocationException {
        return Api.nAddPoi(poi, maxTime);
    }

    public static void addPoiCategory(String category, String bitmapPath, String isoCode, int maxTime) throws GeneralException {
        Api.nAddPoiCategory(category, bitmapPath, isoCode, maxTime);
    }

    public static void deletePoiCategory(String category, String isoCode, int maxTime) throws GeneralException {
        Api.nDeletePoiCategory(category, isoCode, maxTime);
    }

    public static String deletePoi(Poi poi, int maxTime) throws InvalidLocationException {
        return Api.nDeletePoi(poi, maxTime);
    }

    public static void makeUserPoiVisible(int categoryId, String category, boolean show, int maxTime) throws GeneralException {
        Api.nMakeUserPoiVisible(categoryId, category, show, maxTime);
    }

    public static ArrayList<Poi> getPoiList(String category, boolean searchAddress, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nGetPoiList(category, searchAddress, maxTime));
    }

    public static Poi findNearbyPoi(int categoryNumber, String category, int x, int y, int maxTime) throws GeneralException {
        return Api.nFindNearbyPoi(categoryNumber, category, x, y, maxTime);
    }

    public static ArrayList<Poi> findNearbyPoiList(int categoryNumber, String category, int x, int y, int maxCount, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nFindNearbyPoi2(categoryNumber, category, x, y, maxCount, maxTime));
    }

    public static ArrayList<PoiCategory> getPoiCategoryList(int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nGetPoiCategoryList(maxTime));
    }

    public static PoiCategory setPoiWarning(PoiCategory poiCategory, int maxTime) throws GeneralException {
        return Api.nSetPoiWarning(poiCategory, maxTime);
    }

    public static void highlightPoi(String category, String name, String sound, Position pos, int maxTime) throws GeneralException {
        Api.nHighlightPoi(category, name, sound, pos, maxTime);
    }
}
