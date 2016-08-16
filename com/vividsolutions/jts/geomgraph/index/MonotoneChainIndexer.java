package com.vividsolutions.jts.geomgraph.index;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geomgraph.Quadrant;
import java.util.ArrayList;
import java.util.List;

public class MonotoneChainIndexer {
    public static int[] toIntArray(List list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = ((Integer) list.get(i)).intValue();
        }
        return array;
    }

    public int[] getChainStartIndices(Coordinate[] pts) {
        int start = 0;
        List startIndexList = new ArrayList();
        startIndexList.add(new Integer(0));
        do {
            int last = findChainEnd(pts, start);
            startIndexList.add(new Integer(last));
            start = last;
        } while (start < pts.length - 1);
        return toIntArray(startIndexList);
    }

    private int findChainEnd(Coordinate[] pts, int start) {
        int chainQuad = Quadrant.quadrant(pts[start], pts[start + 1]);
        int last = start + 1;
        while (last < pts.length && Quadrant.quadrant(pts[last - 1], pts[last]) == chainQuad) {
            last++;
        }
        return last - 1;
    }
}
