package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.model.RouteInstruction;

public class ApiInfo {
    public static int getCurrentSpeedLimit(int maxTime) throws GeneralException {
        return Api.nGetCurrentSpeedLimit(maxTime);
    }

    public static RouteInstruction getNextInstruction(int maxTime) throws GeneralException {
        return Api.nGetNextInstruction(maxTime);
    }
}
