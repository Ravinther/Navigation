package com.sygic.aura.fcd;

public final class FcdEntity {
    private double _altitude;
    private double _bearing;
    private double _computedBearing;
    private double _computedSpeed;
    private String _countryCode;
    private int _foreground;
    private double _horizontalAccuracy;
    private double _latitude;
    private long _localDate;
    private double _longitude;
    private byte _networkType;
    private long _sensorDate;
    private double _speed;
    private long _syncDate;
    private double _verticalAccuracy;

    public FcdEntity(long syncDate, long sensorDate, long localDate, double latitude, double longitude, double altitude, double horizontalAccuracy, double verticalAccuracy, double bearing, double computedBearing, double speed, double computedSpeed, String countryCode, int foreground, byte networkType) {
        this._syncDate = syncDate;
        this._sensorDate = sensorDate;
        this._localDate = localDate;
        this._latitude = latitude;
        this._longitude = longitude;
        this._altitude = altitude;
        this._horizontalAccuracy = horizontalAccuracy;
        this._verticalAccuracy = verticalAccuracy;
        this._bearing = bearing;
        this._computedBearing = computedBearing;
        this._speed = speed;
        this._computedSpeed = computedSpeed;
        this._countryCode = countryCode;
        this._foreground = foreground;
        this._networkType = networkType;
    }

    public long getSyncDate() {
        return this._syncDate;
    }

    public long getSensorDate() {
        return this._sensorDate;
    }

    public long getLocalDate() {
        return this._localDate;
    }

    public double getLatitude() {
        return this._latitude;
    }

    public double getLongitude() {
        return this._longitude;
    }

    public double getAltitude() {
        return this._altitude;
    }

    public double getHorizontalAccuracy() {
        return this._horizontalAccuracy;
    }

    public double getVerticalAccuracy() {
        return this._verticalAccuracy;
    }

    public double getBearing() {
        return this._bearing;
    }

    public double getComputedBearing() {
        return this._computedBearing;
    }

    public double getSpeed() {
        return this._speed;
    }

    public double getComputedSpeed() {
        return this._computedSpeed;
    }

    public String getCountryCode() {
        return this._countryCode;
    }

    public int getForeground() {
        return this._foreground;
    }

    public byte getNetworkType() {
        return this._networkType;
    }
}
