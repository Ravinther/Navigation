package com.sygic.sdk.api.model;

import android.os.Bundle;

public class Options {
    public int ClockFormat;
    public int DistanceUnit;
    public int GPSUnits;
    public Position HomePosition;
    public int KeyboardType;
    public int bAllowClosedRoads;
    public int bAvoidFerries;
    public int bAvoidUTurns;
    public int bDisableMainMenu;
    public int bDisableRecompute;
    public int bMaxSpeedWarn;
    public int bOperateRightHanded;
    public int bRadarsVisible;
    public int bRadarsWarnOn;
    public int bSnapToEveryRoad;
    public int bSoundEnabled;
    public int bTTSEnabled;
    public int bTruckInMap;
    public int bUseTruckAtt;
    public int nAvoidTollRoads;
    public int nETAMaximumSpeed;
    public int nETAPercentageChange;
    public int nInvisiblePointReachDistance;
    public int nKingpinEndTrailer;
    public int nKingpinLastAxle;
    public int nKingpinLastTandem;
    public int nLoadRestrictions;
    public int nPlanningSettings;
    public int nPlanningSettingsLimitedSpeed;
    public int nRadarDistance;
    public int nRadarDistanceInCity;
    public int nSignpostDirection;
    public int nSignpostSize;
    public int nSkin;
    public int nSpeedExceed;
    public int nSpeedExceedInCity;
    public int nTimeZone;
    public int nTractorLength;
    public int nTrailerLength;
    public int nTruckAxleLength;
    public int nTruckHeight;
    public int nTruckLenght;
    public int nTruckMaxSpeed;
    public int nTruckOtherLength;
    public int nTruckOtherWeight;
    public int nTruckTandemWeight;
    public int nTruckTridemWeight;
    public int nTruckUnladenWeight;
    public int nTruckWeightAxle;
    public int nTruckWeightTotal;
    public int nTruckWidth;
    public int nView;
    public int nVisiblePointReachDistance;
    public int nVolumeMax;
    public int nVolumeMin;
    private String strHomeLocation;
    private String strLangFile;
    private String strMaxSpeedSound;
    private String strVoiceFolder;
    private String strVoicePerson;

    public Options() {
        this.bSoundEnabled = -1;
        this.bOperateRightHanded = -1;
        this.nVolumeMin = -1;
        this.nVolumeMax = -1;
        this.DistanceUnit = -1;
        this.ClockFormat = -1;
        this.GPSUnits = -1;
        this.KeyboardType = -1;
        this.nAvoidTollRoads = -1;
        this.bAvoidUTurns = -1;
        this.nPlanningSettings = -1;
        this.nPlanningSettingsLimitedSpeed = -1;
        this.bAvoidFerries = -1;
        this.bDisableMainMenu = -1;
        this.bDisableRecompute = -1;
        this.nETAMaximumSpeed = -1;
        this.nETAPercentageChange = -1;
        this.bRadarsWarnOn = -1;
        this.bRadarsVisible = -1;
        this.nRadarDistance = -1;
        this.nRadarDistanceInCity = -1;
        this.nSkin = -1;
        this.nTimeZone = -1;
        this.nSpeedExceedInCity = -100;
        this.nSpeedExceed = -100;
        this.nView = -1;
        this.nSignpostDirection = -1;
        this.nSignpostSize = -1;
        this.bSnapToEveryRoad = -1;
        this.bMaxSpeedWarn = -1;
        this.bTTSEnabled = -1;
        this.nVisiblePointReachDistance = -1;
        this.nInvisiblePointReachDistance = -1;
        this.bAllowClosedRoads = -1;
        this.bTruckInMap = -1;
        this.bUseTruckAtt = -1;
        this.nTruckMaxSpeed = -1;
        this.nTruckWeightTotal = -1;
        this.nTruckWeightAxle = -1;
        this.nTruckTandemWeight = -1;
        this.nTruckTridemWeight = -1;
        this.nTruckOtherWeight = -1;
        this.nTruckUnladenWeight = -1;
        this.nTruckLenght = -1;
        this.nTruckAxleLength = -1;
        this.nTrailerLength = -1;
        this.nTractorLength = -1;
        this.nKingpinLastAxle = -1;
        this.nKingpinLastTandem = -1;
        this.nKingpinEndTrailer = -1;
        this.nTruckOtherLength = -1;
        this.nTruckWidth = -1;
        this.nTruckHeight = -1;
        this.nLoadRestrictions = -1;
        this.HomePosition = new Position(0, 0);
        this.strHomeLocation = new String();
        this.strLangFile = new String();
        this.strVoiceFolder = new String();
        this.strVoicePerson = new String();
        this.strMaxSpeedSound = new String();
    }

    public static Bundle writeBundle(Options options) {
        if (options == null) {
            return null;
        }
        int[] arr = new int[]{options.bSoundEnabled, options.bOperateRightHanded, options.nVolumeMin, options.nVolumeMax, options.DistanceUnit, options.ClockFormat, options.GPSUnits, options.KeyboardType, options.nAvoidTollRoads, options.nPlanningSettings, options.nPlanningSettingsLimitedSpeed, options.bAvoidFerries, options.bDisableMainMenu, options.bDisableRecompute, options.nETAMaximumSpeed, options.nETAPercentageChange, options.bRadarsWarnOn, options.bRadarsVisible, options.nRadarDistance, options.nRadarDistanceInCity, options.nSkin, options.nTimeZone, options.nSpeedExceedInCity, options.nSpeedExceed, options.nView, options.nSignpostDirection, options.nSignpostSize, options.bSnapToEveryRoad, options.bMaxSpeedWarn, options.bTTSEnabled, options.nVisiblePointReachDistance, options.nInvisiblePointReachDistance, options.bAllowClosedRoads, options.bTruckInMap, options.bUseTruckAtt, options.nTruckMaxSpeed, options.nTruckWeightTotal, options.nTruckWeightAxle, options.nTruckTandemWeight, options.nTruckTridemWeight, options.nTruckOtherWeight, options.nTruckUnladenWeight, options.nTruckLenght, options.nTruckAxleLength, options.nTrailerLength, options.nTractorLength, options.nKingpinLastAxle, options.nKingpinLastTandem, options.nKingpinEndTrailer, options.nTruckOtherLength, options.nTruckWidth, options.nTruckHeight, options.nLoadRestrictions, options.HomePosition.getX(), options.HomePosition.getY()};
        Bundle b = new Bundle();
        b.putIntArray("intArray", arr);
        b.putString("home", options.getHomeLocation());
        b.putString("lang", options.getLanguage());
        b.putString("voiceFolder", options.getVoice());
        b.putString("voicePerson", options.getVoicePerson());
        b.putString("maxSpeed", options.getMaxSpeedSound());
        return b;
    }

    public static Options readBundle(Bundle b) {
        if (b == null) {
            return null;
        }
        Options options = new Options();
        int[] arr = b.getIntArray("intArray");
        options.bSoundEnabled = arr[0];
        options.bOperateRightHanded = arr[1];
        options.nVolumeMin = arr[2];
        options.nVolumeMax = arr[3];
        options.DistanceUnit = arr[4];
        options.ClockFormat = arr[5];
        options.GPSUnits = arr[6];
        options.KeyboardType = arr[7];
        options.nAvoidTollRoads = arr[8];
        options.nPlanningSettings = arr[9];
        options.nPlanningSettingsLimitedSpeed = arr[10];
        options.bAvoidFerries = arr[11];
        options.bDisableMainMenu = arr[12];
        options.bDisableRecompute = arr[13];
        options.nETAMaximumSpeed = arr[14];
        options.nETAPercentageChange = arr[15];
        options.bRadarsWarnOn = arr[16];
        options.bRadarsVisible = arr[17];
        options.nRadarDistance = arr[18];
        options.nRadarDistanceInCity = arr[19];
        options.nSkin = arr[20];
        options.nTimeZone = arr[21];
        options.nSpeedExceedInCity = arr[22];
        options.nSpeedExceed = arr[23];
        options.nView = arr[24];
        options.nSignpostDirection = arr[25];
        options.nSignpostSize = arr[26];
        options.bSnapToEveryRoad = arr[27];
        options.bMaxSpeedWarn = arr[28];
        options.bTTSEnabled = arr[29];
        options.nVisiblePointReachDistance = arr[30];
        options.nInvisiblePointReachDistance = arr[31];
        options.bAllowClosedRoads = arr[32];
        options.bTruckInMap = arr[33];
        options.bUseTruckAtt = arr[34];
        options.nTruckMaxSpeed = arr[35];
        options.nTruckWeightTotal = arr[36];
        options.nTruckWeightAxle = arr[37];
        options.nTruckTandemWeight = arr[38];
        options.nTruckTridemWeight = arr[39];
        options.nTruckOtherWeight = arr[40];
        options.nTruckUnladenWeight = arr[41];
        options.nTruckLenght = arr[42];
        options.nTruckAxleLength = arr[43];
        options.nTrailerLength = arr[44];
        options.nTractorLength = arr[45];
        options.nKingpinLastAxle = arr[46];
        options.nKingpinLastTandem = arr[47];
        options.nKingpinEndTrailer = arr[48];
        options.nTruckOtherLength = arr[49];
        options.nTruckWidth = arr[50];
        options.nTruckHeight = arr[51];
        options.nLoadRestrictions = arr[52];
        options.HomePosition = new Position(arr[53], arr[54]);
        options.setHomeLocation(b.getString("home"));
        options.setLanguage(b.getString("lang"));
        options.setVoice(b.getString("voiceFolder"));
        options.setVoicePerson(b.getString("voicePerson"));
        options.setMaxSpeedSound(b.getString("maxSpeed"));
        return options;
    }

    public String getHomeLocation() {
        return this.strHomeLocation;
    }

    public void setHomeLocation(String strHomeLocation) {
        this.strHomeLocation = strHomeLocation;
    }

    public String getLanguage() {
        return this.strLangFile;
    }

    public void setLanguage(String strLangFile) {
        this.strLangFile = strLangFile;
    }

    public String getVoice() {
        return this.strVoiceFolder;
    }

    public void setVoice(String strVoiceFolder) {
        this.strVoiceFolder = strVoiceFolder;
    }

    public String getVoicePerson() {
        return this.strVoicePerson;
    }

    public void setVoicePerson(String strVoicePerson) {
        this.strVoicePerson = strVoicePerson;
    }

    public String getMaxSpeedSound() {
        return this.strMaxSpeedSound;
    }

    public void setMaxSpeedSound(String strMaxSpeedSound) {
        this.strMaxSpeedSound = strMaxSpeedSound;
    }
}
