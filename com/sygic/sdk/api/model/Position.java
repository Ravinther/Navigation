package com.sygic.sdk.api.model;

public class Position {
    private int lX;
    private int lY;

    public Position() {
        this.lX = 0;
        this.lY = 0;
    }

    public Position(int x, int y) {
        this.lX = x;
        this.lY = y;
    }

    public int[] toArray() {
        return new int[]{this.lX, this.lY};
    }

    public int getX() {
        return this.lX;
    }

    public int getY() {
        return this.lY;
    }

    public String toString() {
        return "Position [X=" + this.lX + ", Y=" + this.lY + "]";
    }
}
