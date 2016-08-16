package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Location;

public class TopologyLocation {
    int[] location;

    public TopologyLocation(int on, int left, int right) {
        init(3);
        this.location[0] = on;
        this.location[1] = left;
        this.location[2] = right;
    }

    public TopologyLocation(int on) {
        init(1);
        this.location[0] = on;
    }

    public TopologyLocation(TopologyLocation gl) {
        init(gl.location.length);
        if (gl != null) {
            for (int i = 0; i < this.location.length; i++) {
                this.location[i] = gl.location[i];
            }
        }
    }

    private void init(int size) {
        this.location = new int[size];
        setAllLocations(-1);
    }

    public int get(int posIndex) {
        if (posIndex < this.location.length) {
            return this.location[posIndex];
        }
        return -1;
    }

    public boolean isNull() {
        for (int i : this.location) {
            if (i != -1) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnyNull() {
        for (int i : this.location) {
            if (i == -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isArea() {
        return this.location.length > 1;
    }

    public boolean isLine() {
        return this.location.length == 1;
    }

    public void flip() {
        if (this.location.length > 1) {
            int temp = this.location[1];
            this.location[1] = this.location[2];
            this.location[2] = temp;
        }
    }

    public void setAllLocations(int locValue) {
        for (int i = 0; i < this.location.length; i++) {
            this.location[i] = locValue;
        }
    }

    public void setAllLocationsIfNull(int locValue) {
        for (int i = 0; i < this.location.length; i++) {
            if (this.location[i] == -1) {
                this.location[i] = locValue;
            }
        }
    }

    public void setLocation(int locIndex, int locValue) {
        this.location[locIndex] = locValue;
    }

    public void setLocation(int locValue) {
        setLocation(0, locValue);
    }

    public void setLocations(int on, int left, int right) {
        this.location[0] = on;
        this.location[1] = left;
        this.location[2] = right;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        if (this.location.length > 1) {
            buf.append(Location.toLocationSymbol(this.location[1]));
        }
        buf.append(Location.toLocationSymbol(this.location[0]));
        if (this.location.length > 1) {
            buf.append(Location.toLocationSymbol(this.location[2]));
        }
        return buf.toString();
    }
}
