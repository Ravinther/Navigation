package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.exception.NavigationException;
import com.sygic.sdk.api.model.StopOffPoint;
import com.sygic.sdk.api.util.Util;
import java.util.ArrayList;

public class ApiItinerary {
    public static void addItinerary(ArrayList<StopOffPoint> pointArr, String itineraryName, int maxTime) throws GeneralException {
        StopOffPoint[] arr = (StopOffPoint[]) pointArr.toArray(new StopOffPoint[0]);
        Api.nAddItinerary(arr, arr.length, itineraryName, maxTime);
    }

    public static void setRoute(String itineraryName, int flags, int maxTime) throws NavigationException {
        Api.nSetRoute(itineraryName, flags, true, maxTime);
    }

    public static void addEntryToItinerary(String itineraryName, StopOffPoint stopOffPoint, int index, int maxTime) throws GeneralException {
        Api.nAddEntryToItinerary(itineraryName, stopOffPoint, index, maxTime);
    }

    public static void deleteEntryInItinerary(String itineraryName, int index, int maxTime) throws GeneralException {
        Api.nDeleteEntryInItinerary(itineraryName, index, maxTime);
    }

    public static void deleteItinerary(String itineraryName, int maxTime) throws GeneralException {
        Api.nDeleteItinerary(itineraryName, maxTime);
    }

    public static ArrayList<StopOffPoint> getItineraryList(String itineraryName, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nGetItineraryList(itineraryName, maxTime));
    }

    public static void skipNextWaypoint(int maxTime) throws GeneralException {
        Api.nSkipNextWaypoint(maxTime);
    }
}
