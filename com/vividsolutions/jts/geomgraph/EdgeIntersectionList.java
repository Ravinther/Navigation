package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Coordinate;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class EdgeIntersectionList {
    Edge edge;
    private Map nodeMap;

    public EdgeIntersectionList(Edge edge) {
        this.nodeMap = new TreeMap();
        this.edge = edge;
    }

    public EdgeIntersection add(Coordinate intPt, int segmentIndex, double dist) {
        EdgeIntersection eiNew = new EdgeIntersection(intPt, segmentIndex, dist);
        EdgeIntersection ei = (EdgeIntersection) this.nodeMap.get(eiNew);
        if (ei != null) {
            return ei;
        }
        this.nodeMap.put(eiNew, eiNew);
        return eiNew;
    }

    public Iterator iterator() {
        return this.nodeMap.values().iterator();
    }

    public void addEndpoints() {
        int maxSegIndex = this.edge.pts.length - 1;
        add(this.edge.pts[0], 0, 0.0d);
        add(this.edge.pts[maxSegIndex], maxSegIndex, 0.0d);
    }
}
