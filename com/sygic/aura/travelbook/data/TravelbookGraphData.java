package com.sygic.aura.travelbook.data;

import android.graphics.Point;

public class TravelbookGraphData {
    private Point mNormalizedPoint;
    private Point mPoint;
    private boolean mbHasNoSignal;

    public TravelbookGraphData() {
        this.mPoint = new Point();
        this.mNormalizedPoint = new Point();
    }

    public TravelbookGraphData(int nX, int nY, int nAxisX, int nAxisY, boolean bHasNoSignal) {
        this.mPoint = new Point(nX, nY);
        this.mNormalizedPoint = new Point(nAxisX, nAxisY);
        this.mbHasNoSignal = bHasNoSignal;
    }

    public Point getPoint() {
        return this.mPoint;
    }

    public Point getNormalizedPoint() {
        return this.mNormalizedPoint;
    }

    public boolean hasNoSignal() {
        return this.mbHasNoSignal;
    }

    public boolean equals(Object o) {
        if (this.mPoint == null || o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.mPoint.equals(((TravelbookGraphData) o).getPoint());
    }
}
