package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.model.Position;
import com.sygic.sdk.api.model.Rectangle;

public class ApiMaps {
    public static void switchMap(String loadPath, int maxTime) throws GeneralException {
        Api.nSwitchMap(loadPath, maxTime);
    }

    public static void showCoordinatesOnMap(Position location, int zoom, int maxTime) throws GeneralException {
        Api.nShowCoordinatesOnMap(location, zoom, true, maxTime);
    }

    public static void showRectangleOnMap(Rectangle rect, int maxTime) throws GeneralException {
        Api.nShowRectangleOnMap(rect, true, maxTime);
    }

    public static int addBitmapToMap(String bitmapPath, int x, int y, int maxTime) throws GeneralException {
        return Api.nAddBitmapToMap(bitmapPath, x, y, maxTime);
    }

    public static void removeBitmap(int bitmapId, int maxTime) throws GeneralException {
        Api.nRemoveBitmap(bitmapId, maxTime);
    }

    public static void showBitmap(int bitmapId, boolean show, int maxTime) throws GeneralException {
        Api.nShowBitmap(bitmapId, show, maxTime);
    }

    public static void moveBitmap(int bitmapId, int x, int y, int maxTime) throws GeneralException {
        Api.nMoveBitmap(bitmapId, x, y, maxTime);
    }

    public static void loadGFFile(String path, int maxTime) throws GeneralException {
        Api.nLoadGFFile(path, maxTime);
    }

    public static void unloadGFFile(String path, int maxTime) throws GeneralException {
        Api.nUnloadGFFile(path, maxTime);
    }

    public static void loadExternalFile(String path, int type, int maxTime) throws GeneralException {
        Api.nLoadExternalFile(path, type, maxTime);
    }

    public static void unloadExternalFile(String path, int type, int maxTime) throws GeneralException {
        Api.nUnloadExternalFile(path, type, maxTime);
    }

    public static void reloadExternalFiles(int maxTime) throws GeneralException {
        Api.nReloadExternalFiles(maxTime);
    }
}
