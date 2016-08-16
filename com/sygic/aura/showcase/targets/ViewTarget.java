package com.sygic.aura.showcase.targets;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

public class ViewTarget extends Target {
    public static final Point NULL_POINT;
    private final View mView;

    static {
        NULL_POINT = new Point(-1, -1);
    }

    public ViewTarget(View view) {
        this.mView = view;
    }

    public Point getPoint() {
        if (this.mView == null) {
            Log.e("Default", "ViewTarget view is null");
            return NULL_POINT;
        }
        int[] location = new int[2];
        this.mView.getLocationInWindow(location);
        return new Point(location[0] + (this.mView.getWidth() / 2), location[1] + (this.mView.getHeight() / 2));
    }
}
