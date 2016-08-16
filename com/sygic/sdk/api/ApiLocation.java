package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.exception.InvalidLocationException;
import com.sygic.sdk.api.model.Position;
import com.sygic.sdk.api.model.RoadInfo;
import com.sygic.sdk.api.model.WayPoint;
import com.sygic.sdk.api.util.Util;
import java.util.ArrayList;

public class ApiLocation {
    public static String getLocationAddressInfo(Position location, int maxTime) throws InvalidLocationException {
        return Api.nGetLocationInfo(location, maxTime);
    }

    public static RoadInfo getLocationRoadInfo(Position location, int maxTime) throws InvalidLocationException {
        return Api.nGetLocationInfo2(location, maxTime);
    }

    public static Position locationFromAddress(String address, boolean postal, boolean valueMatch, int maxTime) throws GeneralException {
        return Api.nLocationFromAddress(address, postal, valueMatch, maxTime);
    }

    public static ArrayList<WayPoint> locationFromAddressEx(String address, boolean postal, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nLocationFromAddressEx(address, postal, maxTime));
    }

    public static ArrayList<WayPoint> locationFromAddressEx(String address, boolean postal, boolean fuzzySearch, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nLocationFromAddressEx2(address, postal, fuzzySearch, maxTime));
    }

    public static WayPoint showHierarchyDialog(String iso, String city, String street, boolean postal, int maxTime) throws GeneralException {
        return Api.nShowHierarchyDialog(iso, city, street, postal, maxTime);
    }

    public static ArrayList<WayPoint> getAddressList(String address, boolean postal, int maxCount, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nGetAddressList(address, postal, maxCount, maxTime));
    }

    public static Position getCoordinatesFromOffset(String iso, long offset, int maxTime) throws GeneralException {
        return Api.nGetCoordinatesFromOffset(iso, offset, maxTime);
    }
}
