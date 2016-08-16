package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;

public class ApiGps {
    public static void enableExternalGpsInput(int maxTime) throws GeneralException {
        Api.nEnableExternalGpsInput(maxTime);
    }

    public static void disableExternalGpsInput(int maxTime) throws GeneralException {
        Api.nDisableExternalGpsInput(maxTime);
    }

    public static int sendGpsData(String data) {
        int size = 0;
        if (data != null) {
            size = data.length();
        }
        return Api.nSendGpsData(data, size);
    }

    public static void gpsSwitchOn(boolean switchOn, int maxTime) throws GeneralException {
        Api.nGpsSwitchOn("Integrated GPS", 4800, switchOn, maxTime);
    }
}
