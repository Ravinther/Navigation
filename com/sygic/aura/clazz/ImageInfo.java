package com.sygic.aura.clazz;

public class ImageInfo {
    public boolean bHasPosition;
    protected long lLatitude;
    protected long lLongitude;
    protected int nSideSize;
    protected String strPath;

    public ImageInfo() {
        this.strPath = null;
        this.nSideSize = 0;
        this.lLatitude = 0;
        this.lLongitude = 0;
        this.bHasPosition = false;
    }

    public void setImage(String strPath, int nSize) {
        this.strPath = strPath;
        this.nSideSize = nSize;
        this.bHasPosition = false;
    }

    public void setLatLong(long lLat, long lLong) {
        this.lLatitude = lLat;
        this.lLongitude = lLong;
        this.bHasPosition = true;
    }

    public void setPath(String strPath) {
        this.strPath = strPath;
    }

    public String getPath() {
        return this.strPath;
    }

    public int getSideSize() {
        return this.nSideSize;
    }

    public long getLatitude() {
        return this.lLatitude;
    }

    public long getLongitude() {
        return this.lLongitude;
    }
}
