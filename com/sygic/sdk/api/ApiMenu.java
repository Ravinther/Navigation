package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;

public class ApiMenu {
    public static void onMenuCommand(int menuId, int subMenuId, int maxTime) throws GeneralException {
        Api.nOnMenuCommand(menuId, subMenuId, true, maxTime);
    }
}
