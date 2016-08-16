package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.model.Options;

public class ApiOptions {
    public static Options changeApplicationOptions(Options changeOption, int maxTime) throws GeneralException {
        if (changeOption == null) {
            changeOption = new Options();
        }
        return Api.nChangeApplicationOptions(changeOption, maxTime);
    }

    public static void setDefaultValues(int maxTime) throws GeneralException {
        Api.nSetDefaultValues(maxTime);
    }
}
