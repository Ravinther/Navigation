package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.lang.GeoLocation;
import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import java.util.HashMap;

public class GpsDirectory extends Directory {
    protected static final HashMap<Integer, String> _tagNameMap;

    static {
        _tagNameMap = new HashMap();
        _tagNameMap.put(Integer.valueOf(0), "GPS Version ID");
        _tagNameMap.put(Integer.valueOf(1), "GPS Latitude Ref");
        _tagNameMap.put(Integer.valueOf(2), "GPS Latitude");
        _tagNameMap.put(Integer.valueOf(3), "GPS Longitude Ref");
        _tagNameMap.put(Integer.valueOf(4), "GPS Longitude");
        _tagNameMap.put(Integer.valueOf(5), "GPS Altitude Ref");
        _tagNameMap.put(Integer.valueOf(6), "GPS Altitude");
        _tagNameMap.put(Integer.valueOf(7), "GPS Time-Stamp");
        _tagNameMap.put(Integer.valueOf(8), "GPS Satellites");
        _tagNameMap.put(Integer.valueOf(9), "GPS Status");
        _tagNameMap.put(Integer.valueOf(10), "GPS Measure Mode");
        _tagNameMap.put(Integer.valueOf(11), "GPS DOP");
        _tagNameMap.put(Integer.valueOf(12), "GPS Speed Ref");
        _tagNameMap.put(Integer.valueOf(13), "GPS Speed");
        _tagNameMap.put(Integer.valueOf(14), "GPS Track Ref");
        _tagNameMap.put(Integer.valueOf(15), "GPS Track");
        _tagNameMap.put(Integer.valueOf(16), "GPS Img Direction Ref");
        _tagNameMap.put(Integer.valueOf(17), "GPS Img Direction");
        _tagNameMap.put(Integer.valueOf(18), "GPS Map Datum");
        _tagNameMap.put(Integer.valueOf(19), "GPS Dest Latitude Ref");
        _tagNameMap.put(Integer.valueOf(20), "GPS Dest Latitude");
        _tagNameMap.put(Integer.valueOf(21), "GPS Dest Longitude Ref");
        _tagNameMap.put(Integer.valueOf(22), "GPS Dest Longitude");
        _tagNameMap.put(Integer.valueOf(23), "GPS Dest Bearing Ref");
        _tagNameMap.put(Integer.valueOf(24), "GPS Dest Bearing");
        _tagNameMap.put(Integer.valueOf(25), "GPS Dest Distance Ref");
        _tagNameMap.put(Integer.valueOf(26), "GPS Dest Distance");
        _tagNameMap.put(Integer.valueOf(27), "GPS Processing Method");
        _tagNameMap.put(Integer.valueOf(28), "GPS Area Information");
        _tagNameMap.put(Integer.valueOf(29), "GPS Date Stamp");
        _tagNameMap.put(Integer.valueOf(30), "GPS Differential");
    }

    public GpsDirectory() {
        setDescriptor(new GpsDescriptor(this));
    }

    public String getName() {
        return "GPS";
    }

    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }

    public GeoLocation getGeoLocation() {
        Rational[] latitudes = getRationalArray(2);
        Rational[] longitudes = getRationalArray(4);
        String latitudeRef = getString(1);
        String longitudeRef = getString(3);
        if (latitudes == null || latitudes.length != 3 || longitudes == null || longitudes.length != 3 || latitudeRef == null || longitudeRef == null) {
            return null;
        }
        Double lat = GeoLocation.degreesMinutesSecondsToDecimal(latitudes[0], latitudes[1], latitudes[2], latitudeRef.equalsIgnoreCase("S"));
        Double lon = GeoLocation.degreesMinutesSecondsToDecimal(longitudes[0], longitudes[1], longitudes[2], longitudeRef.equalsIgnoreCase("W"));
        if (lat == null || lon == null) {
            return null;
        }
        return new GeoLocation(lat.doubleValue(), lon.doubleValue());
    }
}
