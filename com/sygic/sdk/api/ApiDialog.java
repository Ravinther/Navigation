package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;

public class ApiDialog {
    public static int showMessage(String message, int buttons, boolean waitForFeedback, int maxTime) throws GeneralException {
        return Api.nShowMessage(message, buttons, waitForFeedback, true, maxTime);
    }

    public static void flashMessage(String message, int maxTime) throws GeneralException {
        Api.nFlashMessage(message, true, maxTime);
    }

    public static int errorReport(int maxTime) throws GeneralException {
        return Api.nErrorReport(maxTime);
    }

    public static void closeDialogs(int maxTime) throws GeneralException {
        Api.nCloseDialogs(maxTime);
    }
}
