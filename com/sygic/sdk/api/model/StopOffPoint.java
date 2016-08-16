package com.sygic.sdk.api.model;

import android.os.Bundle;

public class StopOffPoint {
    private Position Location;
    private boolean bSearchAddress;
    private boolean bVisited;
    private int dwId;
    private int lOffset;
    private int nPointType;
    private String strAddress;
    private String strCaption;
    private String strIso;

    public StopOffPoint() {
        this.bSearchAddress = false;
        this.bVisited = false;
        this.nPointType = 0;
        this.Location = new Position();
        this.lOffset = 0;
    }

    public StopOffPoint(boolean searchAddress, boolean visited, int pointType, int x, int y, int offset, int id, String iso, String caption, String address) {
        this.bSearchAddress = searchAddress;
        this.bVisited = visited;
        this.nPointType = pointType;
        this.Location = new Position(x, y);
        this.lOffset = offset;
        this.dwId = id;
        this.strIso = iso;
        this.strCaption = caption;
        this.strAddress = address;
    }

    public static Bundle writeBundle(StopOffPoint sop) {
        if (sop == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putBoolean("searchAddress", sop.isSearchAddress());
        b.putBoolean("visited", sop.isVisited());
        b.putInt("type", sop.getPointType());
        b.putIntArray("location", sop.getLocation().toArray());
        b.putInt("offset", sop.getOffset());
        b.putInt("id", sop.getId());
        b.putString("iso", sop.getIso());
        b.putString("caption", sop.getCaption());
        b.putString("address", sop.getAddress());
        return b;
    }

    public static StopOffPoint readBundle(Bundle b) {
        if (b == null) {
            return null;
        }
        int[] loc = b.getIntArray("location");
        return new StopOffPoint(b.getBoolean("searchAddress"), b.getBoolean("visited"), b.getInt("type"), loc[0], loc[1], b.getInt("offset"), b.getInt("id"), b.getString("iso"), b.getString("caption"), b.getString("address"));
    }

    public String getAddress() {
        return this.strAddress;
    }

    public String getCaption() {
        return this.strCaption;
    }

    public String getIso() {
        return this.strIso;
    }

    public boolean isSearchAddress() {
        return this.bSearchAddress;
    }

    public boolean isVisited() {
        return this.bVisited;
    }

    public int getPointType() {
        return this.nPointType;
    }

    public Position getLocation() {
        return this.Location;
    }

    public int getOffset() {
        return this.lOffset;
    }

    public int getId() {
        return this.dwId;
    }

    public String toString() {
        return "StopOffPoint [SearchAddress=" + this.bSearchAddress + ", Visited=" + this.bVisited + ", PointType=" + this.nPointType + ", Location=" + this.Location + ", Offset=" + this.lOffset + ", Id=" + this.dwId + ", Iso=" + this.strIso + ", Caption=" + this.strCaption + ", Address=" + this.strAddress + "]";
    }
}
