package com.sygic.aura.settings.data;

public class AboutEntry {
    protected String mStrBranchName;
    protected String mStrBuildName;
    protected String mStrCompatibility;
    protected String mStrDate;
    protected String mStrDepthFormat;
    protected String mStrDeviceID;
    protected String mStrDeviceName;
    protected String mStrExpirationDate;
    protected String mStrFirmware;
    protected String mStrFreeRam;
    protected String mStrGLRenderer;
    protected String mStrGLVendor;
    protected String mStrGLVersion;
    protected String mStrHash;
    protected String mStrMapVersion;
    protected String mStrModelName;
    protected String mStrName;
    protected String mStrPixelFormat;
    protected String mStrResolution;
    protected String mStrSwVersion;

    public AboutEntry(String strDeviceID, String strDeviceName, String strGLVendor, String strGLRenderer, String strGLVersion, String strPixelFormat, String strDepthFormat, String strModelName, String strCompatibility, String strExpirationDate, String strResolution, String strFreeRam, String strMapVersion, String strSwVersion, String strName, String strFirmware, String strHash, String strBranchName, String strDate, String strBuildName) {
        this.mStrDeviceID = strDeviceID;
        this.mStrDeviceName = strDeviceName;
        this.mStrGLVendor = strGLVendor;
        this.mStrGLRenderer = strGLRenderer;
        this.mStrGLVersion = strGLVersion;
        this.mStrPixelFormat = strPixelFormat;
        this.mStrDepthFormat = strDepthFormat;
        this.mStrModelName = strModelName;
        this.mStrCompatibility = strCompatibility;
        this.mStrExpirationDate = strExpirationDate;
        this.mStrResolution = strResolution;
        this.mStrFreeRam = strFreeRam;
        this.mStrMapVersion = strMapVersion;
        this.mStrSwVersion = strSwVersion;
        this.mStrName = strName;
        this.mStrFirmware = strFirmware;
        this.mStrHash = strHash;
        this.mStrBranchName = strBranchName;
        this.mStrDate = strDate;
        this.mStrBuildName = strBuildName;
    }

    public AboutEntry(AboutEntry entry) {
        this(entry.mStrDeviceID, entry.mStrDeviceName, entry.mStrGLVendor, entry.mStrGLRenderer, entry.mStrGLVersion, entry.mStrExpirationDate, entry.mStrPixelFormat, entry.mStrDepthFormat, entry.mStrModelName, entry.mStrCompatibility, entry.mStrResolution, entry.mStrFreeRam, entry.mStrMapVersion, entry.mStrSwVersion, entry.mStrName, entry.mStrFirmware, entry.mStrHash, entry.mStrBranchName, entry.mStrDate, entry.mStrBuildName);
    }

    public String getDeviceID() {
        return this.mStrDeviceID;
    }

    public String getDeviceName() {
        return this.mStrDeviceName;
    }

    public String getGLVendor() {
        return this.mStrGLVendor;
    }

    public String getGLRenderer() {
        return this.mStrGLRenderer;
    }

    public String getGLVersion() {
        return this.mStrGLVersion;
    }

    public String getPixelFormat() {
        return this.mStrPixelFormat;
    }

    public String getDepthFormat() {
        return this.mStrDepthFormat;
    }

    public String getModelName() {
        return this.mStrModelName;
    }

    public String getCompatibility() {
        return this.mStrCompatibility;
    }

    public String getExpirationDate() {
        return this.mStrExpirationDate;
    }

    public String getResolution() {
        return this.mStrResolution;
    }

    public String getFreeRam() {
        return this.mStrFreeRam;
    }

    public String getMapVersion() {
        return this.mStrMapVersion;
    }

    public String getSwVersion() {
        return this.mStrSwVersion;
    }

    public String getName() {
        return this.mStrName;
    }

    public String getFirmware() {
        return this.mStrFirmware;
    }

    public String getHash() {
        return this.mStrHash;
    }

    public String getBranchName() {
        return this.mStrBranchName;
    }

    public String getDate() {
        return this.mStrDate;
    }

    public String getBuildName() {
        return this.mStrBuildName;
    }
}
