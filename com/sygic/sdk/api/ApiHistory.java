package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.model.HistoryRecord;
import com.sygic.sdk.api.util.Util;
import java.util.ArrayList;

public class ApiHistory {
    public static ArrayList<HistoryRecord> getHistoryList(boolean searchAddress, int maxTime) throws GeneralException {
        return Util.asArrayList(Api.nGetHistoryList(searchAddress, maxTime));
    }
}
