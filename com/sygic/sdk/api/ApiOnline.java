package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.model.OnlineServicesSettings;
import com.sygic.sdk.api.model.TmcEvent;

public class ApiOnline {
    public static void goOnline(boolean online, int maxTime) throws GeneralException {
        Api.nGoOnline(online, maxTime);
    }

    public static void onlineServicesLogin(String userName, String password, int maxTime) throws GeneralException {
        Api.nOnlineServicesLogin(userName, password, maxTime);
    }

    public static TmcEvent addTMCEvent(TmcEvent event, int maxTime) throws GeneralException {
        return Api.nAddTMCEvent(event, maxTime);
    }

    public static void removeTMCEvent(short eventId, int maxTime) throws GeneralException {
        Api.nRemoveTMCEvent(eventId, maxTime);
    }

    public static void clearTMCTable(int maxTime) throws GeneralException {
        Api.nClearTMCTable(maxTime);
    }

    public static OnlineServicesSettings onlineServicesSettings(int maxTime) throws GeneralException {
        return Api.nOnlineServicesSettings(maxTime);
    }
}
