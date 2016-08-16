package com.sygic.aura.data;

import android.graphics.Point;

public class ScreenPoint extends Point {
    public static final ScreenPoint Invalid;

    static {
        Invalid = new ScreenPoint();
    }

    public ScreenPoint() {
        this.x = -999999999;
        this.y = -999999999;
    }

    public ScreenPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ScreenPoint(long xy) {
        this.x = (int) (xy >> 32);
        this.y = (int) xy;
    }

    public String toString() {
        return String.format("%s@%d:%d", new Object[]{getClass().getName(), Integer.valueOf(this.x), Integer.valueOf(this.y)});
    }
}
