package com.vividsolutions.jts.geomgraph;

public class Label {
    TopologyLocation[] elt;

    public Label(int onLoc) {
        this.elt = new TopologyLocation[2];
        this.elt[0] = new TopologyLocation(onLoc);
        this.elt[1] = new TopologyLocation(onLoc);
    }

    public Label(int geomIndex, int onLoc) {
        this.elt = new TopologyLocation[2];
        this.elt[0] = new TopologyLocation(-1);
        this.elt[1] = new TopologyLocation(-1);
        this.elt[geomIndex].setLocation(onLoc);
    }

    public Label(int onLoc, int leftLoc, int rightLoc) {
        this.elt = new TopologyLocation[2];
        this.elt[0] = new TopologyLocation(onLoc, leftLoc, rightLoc);
        this.elt[1] = new TopologyLocation(onLoc, leftLoc, rightLoc);
    }

    public Label(int geomIndex, int onLoc, int leftLoc, int rightLoc) {
        this.elt = new TopologyLocation[2];
        this.elt[0] = new TopologyLocation(-1, -1, -1);
        this.elt[1] = new TopologyLocation(-1, -1, -1);
        this.elt[geomIndex].setLocations(onLoc, leftLoc, rightLoc);
    }

    public Label(Label lbl) {
        this.elt = new TopologyLocation[2];
        this.elt[0] = new TopologyLocation(lbl.elt[0]);
        this.elt[1] = new TopologyLocation(lbl.elt[1]);
    }

    public void flip() {
        this.elt[0].flip();
        this.elt[1].flip();
    }

    public int getLocation(int geomIndex, int posIndex) {
        return this.elt[geomIndex].get(posIndex);
    }

    public int getLocation(int geomIndex) {
        return this.elt[geomIndex].get(0);
    }

    public void setLocation(int geomIndex, int posIndex, int location) {
        this.elt[geomIndex].setLocation(posIndex, location);
    }

    public void setLocation(int geomIndex, int location) {
        this.elt[geomIndex].setLocation(0, location);
    }

    public void setAllLocations(int geomIndex, int location) {
        this.elt[geomIndex].setAllLocations(location);
    }

    public void setAllLocationsIfNull(int geomIndex, int location) {
        this.elt[geomIndex].setAllLocationsIfNull(location);
    }

    public int getGeometryCount() {
        int count = 0;
        if (!this.elt[0].isNull()) {
            count = 0 + 1;
        }
        if (this.elt[1].isNull()) {
            return count;
        }
        return count + 1;
    }

    public boolean isNull(int geomIndex) {
        return this.elt[geomIndex].isNull();
    }

    public boolean isAnyNull(int geomIndex) {
        return this.elt[geomIndex].isAnyNull();
    }

    public boolean isArea() {
        return this.elt[0].isArea() || this.elt[1].isArea();
    }

    public boolean isArea(int geomIndex) {
        return this.elt[geomIndex].isArea();
    }

    public boolean isLine(int geomIndex) {
        return this.elt[geomIndex].isLine();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        if (this.elt[0] != null) {
            buf.append("A:");
            buf.append(this.elt[0].toString());
        }
        if (this.elt[1] != null) {
            buf.append(" B:");
            buf.append(this.elt[1].toString());
        }
        return buf.toString();
    }
}
