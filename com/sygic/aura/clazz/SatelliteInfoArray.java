package com.sygic.aura.clazz;

import java.util.ArrayList;

public class SatelliteInfoArray {
    private ArrayList<SatelliteInfo> arr;

    public SatelliteInfoArray() {
        this.arr = null;
        this.arr = new ArrayList();
    }

    public SatelliteInfoArray(SatelliteInfo si) {
        this.arr = null;
        this.arr = new ArrayList();
        this.arr.add(si);
    }

    public void add(SatelliteInfo si) {
        this.arr.add(si);
    }

    public SatelliteInfo getAt(int index) {
        return (SatelliteInfo) this.arr.get(index);
    }

    public int size() {
        return this.arr.size();
    }
}
