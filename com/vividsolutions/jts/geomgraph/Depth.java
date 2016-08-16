package com.vividsolutions.jts.geomgraph;

import java.lang.reflect.Array;

public class Depth {
    private int[][] depth;

    public Depth() {
        this.depth = (int[][]) Array.newInstance(Integer.TYPE, new int[]{2, 3});
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                this.depth[i][j] = -1;
            }
        }
    }

    public String toString() {
        return "A: " + this.depth[0][1] + "," + this.depth[0][2] + " B: " + this.depth[1][1] + "," + this.depth[1][2];
    }
}
