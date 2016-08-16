package com.sygic.sdk.api.model;

import android.os.Bundle;

public class Poi {
    private Position Location;
    private boolean bSearchAddress;
    private String strAddress;
    private String strCategory;
    private String strName;

    public Poi() {
        this.bSearchAddress = false;
        this.Location = new Position();
    }

    public Poi(int x, int y, String cat, String name, String address, boolean searchAddr) {
        this.Location = new Position(x, y);
        this.strCategory = cat;
        this.strName = name;
        this.strAddress = address;
        this.bSearchAddress = searchAddr;
    }

    public static Bundle writeBundle(Poi poi) {
        if (poi == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putBoolean("searchAddress", poi.isSearchAddress());
        b.putIntArray("location", poi.getLocation().toArray());
        b.putString("category", poi.getCategory());
        b.putString("name", poi.getName());
        b.putString("address", poi.getAddress());
        return b;
    }

    public static Poi readBundle(Bundle b) {
        if (b == null) {
            return null;
        }
        int[] loc = b.getIntArray("location");
        return new Poi(loc[0], loc[1], b.getString("category"), b.getString("name"), b.getString("address"), b.getBoolean("searchAddress"));
    }

    public boolean isSearchAddress() {
        return this.bSearchAddress;
    }

    public String getAddress() {
        return this.strAddress;
    }

    public String getCategory() {
        return this.strCategory;
    }

    public String getName() {
        return this.strName;
    }

    public Position getLocation() {
        return this.Location;
    }

    public String toString() {
        return "Poi [SearchAddress=" + this.bSearchAddress + ", Location=" + this.Location + ", Category=" + this.strCategory + ", Name=" + this.strName + ", Address=" + this.strAddress + "]";
    }
}
