package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;

public class ApiTrip {
    public static void tripStart(String name, int dataMode, int maxTime) throws GeneralException {
        Api.nTripStart(name, dataMode, maxTime);
    }

    public static void tripAddUserEvent(String name, int id, int maxTime) throws GeneralException {
        Api.nTripAddUserEvent(name, id, maxTime);
    }

    public static void tripEnd(int maxTime) throws GeneralException {
        Api.nTripEnd(maxTime);
    }
}
