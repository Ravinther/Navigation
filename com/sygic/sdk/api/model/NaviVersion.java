package com.sygic.sdk.api.model;

public class NaviVersion {
    private String mBuild;
    private String mVersion;

    public String getBuild() {
        return this.mBuild;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public String toString() {
        return "ApplicationVersion [Build=" + this.mBuild + ", Version=" + this.mVersion + "]";
    }
}
