package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.algorithm.BoundaryNodeRule;
import com.vividsolutions.jts.algorithm.locate.SimplePointInAreaLocator;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.TopologyException;
import com.vividsolutions.jts.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class EdgeEndStar {
    protected List edgeList;
    protected Map edgeMap;
    private int[] ptInAreaLocation;

    public abstract void insert(EdgeEnd edgeEnd);

    public EdgeEndStar() {
        this.edgeMap = new TreeMap();
        this.ptInAreaLocation = new int[]{-1, -1};
    }

    protected void insertEdgeEnd(EdgeEnd e, Object obj) {
        this.edgeMap.put(e, obj);
        this.edgeList = null;
    }

    public Coordinate getCoordinate() {
        Iterator it = iterator();
        if (it.hasNext()) {
            return ((EdgeEnd) it.next()).getCoordinate();
        }
        return null;
    }

    public Iterator iterator() {
        return getEdges().iterator();
    }

    public List getEdges() {
        if (this.edgeList == null) {
            this.edgeList = new ArrayList(this.edgeMap.values());
        }
        return this.edgeList;
    }

    public void computeLabelling(GeometryGraph[] geomGraph) {
        computeEdgeEndLabels(geomGraph[0].getBoundaryNodeRule());
        propagateSideLabels(0);
        propagateSideLabels(1);
        boolean[] hasDimensionalCollapseEdge = new boolean[]{false, false};
        Iterator it = iterator();
        while (it.hasNext()) {
            Label label = ((EdgeEnd) it.next()).getLabel();
            int geomi = 0;
            while (geomi < 2) {
                if (label.isLine(geomi) && label.getLocation(geomi) == 1) {
                    hasDimensionalCollapseEdge[geomi] = true;
                }
                geomi++;
            }
        }
        it = iterator();
        while (it.hasNext()) {
            EdgeEnd e = (EdgeEnd) it.next();
            label = e.getLabel();
            for (geomi = 0; geomi < 2; geomi++) {
                if (label.isAnyNull(geomi)) {
                    int loc;
                    if (hasDimensionalCollapseEdge[geomi]) {
                        loc = 2;
                    } else {
                        loc = getLocation(geomi, e.getCoordinate(), geomGraph);
                    }
                    label.setAllLocationsIfNull(geomi, loc);
                }
            }
        }
    }

    private void computeEdgeEndLabels(BoundaryNodeRule boundaryNodeRule) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((EdgeEnd) it.next()).computeLabel(boundaryNodeRule);
        }
    }

    private int getLocation(int geomIndex, Coordinate p, GeometryGraph[] geom) {
        if (this.ptInAreaLocation[geomIndex] == -1) {
            this.ptInAreaLocation[geomIndex] = SimplePointInAreaLocator.locate(p, geom[geomIndex].getGeometry());
        }
        return this.ptInAreaLocation[geomIndex];
    }

    void propagateSideLabels(int geomIndex) {
        int startLoc = -1;
        Iterator it = iterator();
        while (it.hasNext()) {
            Label label = ((EdgeEnd) it.next()).getLabel();
            if (label.isArea(geomIndex) && label.getLocation(geomIndex, 1) != -1) {
                startLoc = label.getLocation(geomIndex, 1);
            }
        }
        if (startLoc != -1) {
            int currLoc = startLoc;
            it = iterator();
            while (it.hasNext()) {
                EdgeEnd e = (EdgeEnd) it.next();
                label = e.getLabel();
                if (label.getLocation(geomIndex, 0) == -1) {
                    label.setLocation(geomIndex, 0, currLoc);
                }
                if (label.isArea(geomIndex)) {
                    int leftLoc = label.getLocation(geomIndex, 1);
                    int rightLoc = label.getLocation(geomIndex, 2);
                    if (rightLoc == -1) {
                        boolean z;
                        if (label.getLocation(geomIndex, 1) == -1) {
                            z = true;
                        } else {
                            z = false;
                        }
                        Assert.isTrue(z, "found single null side");
                        label.setLocation(geomIndex, 2, currLoc);
                        label.setLocation(geomIndex, 1, currLoc);
                    } else if (rightLoc != currLoc) {
                        throw new TopologyException("side location conflict", e.getCoordinate());
                    } else {
                        if (leftLoc == -1) {
                            Assert.shouldNeverReachHere("found single null side (at " + e.getCoordinate() + ")");
                        }
                        currLoc = leftLoc;
                    }
                }
            }
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("EdgeEndStar:   " + getCoordinate());
        buf.append("\n");
        Iterator it = iterator();
        while (it.hasNext()) {
            buf.append((EdgeEnd) it.next());
            buf.append("\n");
        }
        return buf.toString();
    }
}
