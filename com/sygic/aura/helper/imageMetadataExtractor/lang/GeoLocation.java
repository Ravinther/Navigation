package com.sygic.aura.helper.imageMetadataExtractor.lang;

import java.text.DecimalFormat;

public final class GeoLocation {
    private final double _latitude;
    private final double _longitude;

    public GeoLocation(double latitude, double longitude) {
        this._latitude = latitude;
        this._longitude = longitude;
    }

    public double getLatitude() {
        return this._latitude;
    }

    public double getLongitude() {
        return this._longitude;
    }

    public static String decimalToDegreesMinutesSecondsString(double decimal) {
        double[] dms = decimalToDegreesMinutesSeconds(decimal);
        DecimalFormat format = new DecimalFormat("0.##");
        return String.format("%s\u00b0 %s' %s\"", new Object[]{format.format(dms[0]), format.format(dms[1]), format.format(dms[2])});
    }

    public static double[] decimalToDegreesMinutesSeconds(double decimal) {
        int d = (int) decimal;
        double s = (Math.abs((decimal % 1.0d) * 60.0d) % 1.0d) * 60.0d;
        return new double[]{(double) d, (double) ((int) Math.abs((decimal % 1.0d) * 60.0d)), s};
    }

    public static Double degreesMinutesSecondsToDecimal(Rational degs, Rational mins, Rational secs, boolean isNegative) {
        double decimal = (Math.abs(degs.doubleValue()) + (mins.doubleValue() / 60.0d)) + (secs.doubleValue() / 3600.0d);
        if (Double.isNaN(decimal)) {
            return null;
        }
        if (isNegative) {
            decimal *= -1.0d;
        }
        return Double.valueOf(decimal);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeoLocation that = (GeoLocation) o;
        if (Double.compare(that._latitude, this._latitude) != 0) {
            return false;
        }
        if (Double.compare(that._longitude, this._longitude) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp;
        if (this._latitude != 0.0d) {
            temp = Double.doubleToLongBits(this._latitude);
        } else {
            temp = 0;
        }
        int result = (int) ((temp >>> 32) ^ temp);
        if (this._longitude != 0.0d) {
            temp = Double.doubleToLongBits(this._longitude);
        } else {
            temp = 0;
        }
        return (result * 31) + ((int) ((temp >>> 32) ^ temp));
    }

    public String toString() {
        return this._latitude + ", " + this._longitude;
    }
}
