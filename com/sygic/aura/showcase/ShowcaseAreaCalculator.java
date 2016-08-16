package com.sygic.aura.showcase;

import android.graphics.Rect;

class ShowcaseAreaCalculator {
    private final Rect mShowcaseRect;

    ShowcaseAreaCalculator() {
        this.mShowcaseRect = new Rect();
    }

    public boolean calculateShowcaseRect(float x, float y, ShowcaseDisplayer showcaseDrawer) {
        int cx = (int) x;
        int cy = (int) y;
        int size = showcaseDrawer.getShowcaseSize();
        if (this.mShowcaseRect.left == cx - (size / 2) && this.mShowcaseRect.top == cy - (size / 2)) {
            return false;
        }
        this.mShowcaseRect.left = cx - (size / 2);
        this.mShowcaseRect.top = cy - (size / 2);
        this.mShowcaseRect.right = (size / 2) + cx;
        this.mShowcaseRect.bottom = (size / 2) + cy;
        return true;
    }

    public Rect getShowcaseRect() {
        return this.mShowcaseRect;
    }
}
