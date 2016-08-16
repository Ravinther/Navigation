package com.sygic.widget.tomtom;

public class PlacePoint {
    public static String getStringPositionOfPoint(double latitude, double longitude) {
        return Double.toString(latitude) + "," + Double.toString(longitude);
    }
}
