package com.sygic.sdk.api.model;

public class Rectangle {
    private int lBottom;
    private int lLeft;
    private int lRight;
    private int lTop;

    public Rectangle() {
        this.lLeft = 0;
        this.lTop = 0;
        this.lRight = Integer.MAX_VALUE;
        this.lBottom = Integer.MAX_VALUE;
    }

    public Rectangle(int left, int top, int right, int bottom) {
        this.lBottom = bottom;
        this.lLeft = left;
        this.lRight = right;
        this.lTop = top;
    }

    public int[] toArray() {
        return new int[]{this.lLeft, this.lTop, this.lRight, this.lBottom};
    }

    public String toString() {
        return "Rectangle [Bottom=" + this.lBottom + ", Left=" + this.lLeft + ", Right=" + this.lRight + ", Top=" + this.lTop + "]";
    }
}
