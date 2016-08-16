package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.C1090R;
import com.sygic.aura.helper.imageMetadataExtractor.lang.GeoLocation;
import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.TagDescriptor;
import java.text.DecimalFormat;
import loquendo.tts.engine.TTSConst;

public class GpsDescriptor extends TagDescriptor<GpsDirectory> {
    public GpsDescriptor(GpsDirectory directory) {
        super(directory);
    }

    public String getDescription(int tagType) {
        switch (tagType) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return getGpsVersionIdDescription();
            case TTSConst.TTSPARAGRAPH /*2*/:
                return getGpsLatitudeDescription();
            case TTSConst.TTSXML /*4*/:
                return getGpsLongitudeDescription();
            case TTSConst.TTSEVT_TEXT /*5*/:
                return getGpsAltitudeRefDescription();
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return getGpsAltitudeDescription();
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return getGpsTimeStampDescription();
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return getGpsStatusDescription();
            case TTSConst.TTSEVT_RESUME /*10*/:
                return getGpsMeasureModeDescription();
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                return getGpsSpeedRefDescription();
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
            case TTSConst.TTSEVT_ERROR /*16*/:
            case C1090R.styleable.Theme_actionBarDivider /*23*/:
                return getGpsDirectionReferenceDescription(tagType);
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
            case TTSConst.TTSEVT_JUMP /*17*/:
            case C1090R.styleable.Theme_actionBarItemBackground /*24*/:
                return getGpsDirectionDescription(tagType);
            case C1090R.styleable.Theme_actionMenuTextAppearance /*25*/:
                return getGpsDestinationReferenceDescription();
            case C1090R.styleable.Theme_actionModeSplitBackground /*30*/:
                return getGpsDifferentialDescription();
            default:
                return super.getDescription(tagType);
        }
    }

    private String getGpsVersionIdDescription() {
        return getVersionBytesDescription(0, 1);
    }

    public String getGpsLatitudeDescription() {
        GeoLocation location = ((GpsDirectory) this._directory).getGeoLocation();
        return location == null ? null : GeoLocation.decimalToDegreesMinutesSecondsString(location.getLatitude());
    }

    public String getGpsLongitudeDescription() {
        GeoLocation location = ((GpsDirectory) this._directory).getGeoLocation();
        return location == null ? null : GeoLocation.decimalToDegreesMinutesSecondsString(location.getLongitude());
    }

    public String getGpsTimeStampDescription() {
        if (((GpsDirectory) this._directory).getIntArray(7) == null) {
            return null;
        }
        return String.format("%d:%d:%d UTC", new Object[]{Integer.valueOf(((GpsDirectory) this._directory).getIntArray(7)[0]), Integer.valueOf(((GpsDirectory) this._directory).getIntArray(7)[1]), Integer.valueOf(((GpsDirectory) this._directory).getIntArray(7)[2])});
    }

    public String getGpsDestinationReferenceDescription() {
        String value = ((GpsDirectory) this._directory).getString(25);
        if (value == null) {
            return null;
        }
        String distanceRef = value.trim();
        if ("K".equalsIgnoreCase(distanceRef)) {
            return "kilometers";
        }
        if ("M".equalsIgnoreCase(distanceRef)) {
            return "miles";
        }
        if ("N".equalsIgnoreCase(distanceRef)) {
            return "knots";
        }
        return "Unknown (" + distanceRef + ")";
    }

    public String getGpsDirectionDescription(int tagType) {
        String value;
        Rational angle = ((GpsDirectory) this._directory).getRational(tagType);
        if (angle != null) {
            value = new DecimalFormat("0.##").format(angle.doubleValue());
        } else {
            value = ((GpsDirectory) this._directory).getString(tagType);
        }
        return (value == null || value.trim().length() == 0) ? null : value.trim() + " degrees";
    }

    public String getGpsDirectionReferenceDescription(int tagType) {
        String value = ((GpsDirectory) this._directory).getString(tagType);
        if (value == null) {
            return null;
        }
        String gpsDistRef = value.trim();
        if ("T".equalsIgnoreCase(gpsDistRef)) {
            return "True direction";
        }
        if ("M".equalsIgnoreCase(gpsDistRef)) {
            return "Magnetic direction";
        }
        return "Unknown (" + gpsDistRef + ")";
    }

    public String getGpsSpeedRefDescription() {
        String value = ((GpsDirectory) this._directory).getString(12);
        if (value == null) {
            return null;
        }
        String gpsSpeedRef = value.trim();
        if ("K".equalsIgnoreCase(gpsSpeedRef)) {
            return "kph";
        }
        if ("M".equalsIgnoreCase(gpsSpeedRef)) {
            return "mph";
        }
        if ("N".equalsIgnoreCase(gpsSpeedRef)) {
            return "knots";
        }
        return "Unknown (" + gpsSpeedRef + ")";
    }

    public String getGpsMeasureModeDescription() {
        String value = ((GpsDirectory) this._directory).getString(10);
        if (value == null) {
            return null;
        }
        String gpsSpeedMeasureMode = value.trim();
        if ("2".equalsIgnoreCase(gpsSpeedMeasureMode)) {
            return "2-dimensional measurement";
        }
        if ("3".equalsIgnoreCase(gpsSpeedMeasureMode)) {
            return "3-dimensional measurement";
        }
        return "Unknown (" + gpsSpeedMeasureMode + ")";
    }

    public String getGpsStatusDescription() {
        String value = ((GpsDirectory) this._directory).getString(9);
        if (value == null) {
            return null;
        }
        String gpsStatus = value.trim();
        if ("A".equalsIgnoreCase(gpsStatus)) {
            return "Active (Measurement in progress)";
        }
        if ("V".equalsIgnoreCase(gpsStatus)) {
            return "Void (Measurement Interoperability)";
        }
        return "Unknown (" + gpsStatus + ")";
    }

    public String getGpsAltitudeRefDescription() {
        return getIndexedDescription(5, "Sea level", "Below sea level");
    }

    public String getGpsAltitudeDescription() {
        Rational value = ((GpsDirectory) this._directory).getRational(6);
        return value == null ? null : value.intValue() + " metres";
    }

    public String getGpsDifferentialDescription() {
        return getIndexedDescription(30, "No Correction", "Differential Corrected");
    }
}
