package com.vividsolutions.jts.geom;

import java.lang.reflect.Array;

public class IntersectionMatrix implements Cloneable {
    private int[][] matrix;

    public IntersectionMatrix() {
        this.matrix = (int[][]) Array.newInstance(Integer.TYPE, new int[]{3, 3});
        setAll(-1);
    }

    public static boolean isTrue(int actualDimensionValue) {
        if (actualDimensionValue >= 0 || actualDimensionValue == -2) {
            return true;
        }
        return false;
    }

    public void set(int row, int column, int dimensionValue) {
        this.matrix[row][column] = dimensionValue;
    }

    public void setAtLeast(int row, int column, int minimumDimensionValue) {
        if (this.matrix[row][column] < minimumDimensionValue) {
            this.matrix[row][column] = minimumDimensionValue;
        }
    }

    public void setAtLeastIfValid(int row, int column, int minimumDimensionValue) {
        if (row >= 0 && column >= 0) {
            setAtLeast(row, column, minimumDimensionValue);
        }
    }

    public void setAtLeast(String minimumDimensionSymbols) {
        for (int i = 0; i < minimumDimensionSymbols.length(); i++) {
            setAtLeast(i / 3, i % 3, Dimension.toDimensionValue(minimumDimensionSymbols.charAt(i)));
        }
    }

    public void setAll(int dimensionValue) {
        for (int ai = 0; ai < 3; ai++) {
            for (int bi = 0; bi < 3; bi++) {
                this.matrix[ai][bi] = dimensionValue;
            }
        }
    }

    public boolean isContains() {
        return isTrue(this.matrix[0][0]) && this.matrix[2][0] == -1 && this.matrix[2][1] == -1;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("123456789");
        for (int ai = 0; ai < 3; ai++) {
            for (int bi = 0; bi < 3; bi++) {
                buf.setCharAt((ai * 3) + bi, Dimension.toDimensionSymbol(this.matrix[ai][bi]));
            }
        }
        return buf.toString();
    }
}
