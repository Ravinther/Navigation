package com.sygic.sdk.api.model;

import android.os.Bundle;

public class WayPoint {
    private Position Location;
    private String strAddress;

    public WayPoint() {
        this.Location = new Position(0, 0);
    }

    public WayPoint(String str, int x, int y) {
        this.Location = new Position(x, y);
        this.strAddress = str;
    }

    public static Bundle writeBundle(WayPoint wp) {
        if (wp == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putIntArray("location", wp.getLocation().toArray());
        b.putString("address", wp.getStrAddress());
        return b;
    }

    public static WayPoint readBundle(Bundle b) {
        if (b == null) {
            return null;
        }
        int[] loc = b.getIntArray("location");
        return new WayPoint(b.getString("address"), loc[0], loc[1]);
    }

    public Position getLocation() {
        return this.Location;
    }

    public String getStrAddress() {
        return this.strAddress;
    }

    public String toString() {
        return "WayPoint [Location=" + this.Location + ", Address=" + this.strAddress + "]";
    }
}
