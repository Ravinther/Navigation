package com.sygic.aura.showcase.targets;

import android.graphics.Point;

public class PointTarget extends Target {
    private final Point mPoint;

    public PointTarget(int x, int y) {
        this.mPoint = new Point(x, y);
    }

    public void swapCoordinates() {
        this.mPoint.set(this.mPoint.y, this.mPoint.x);
    }

    public Point getPoint() {
        return this.mPoint;
    }
}
