package com.vividsolutions.jts.operation.relate;

import com.vividsolutions.jts.algorithm.BoundaryNodeRule;
import com.vividsolutions.jts.geom.IntersectionMatrix;
import com.vividsolutions.jts.geomgraph.Edge;
import com.vividsolutions.jts.geomgraph.EdgeEnd;
import com.vividsolutions.jts.geomgraph.GeometryGraph;
import com.vividsolutions.jts.geomgraph.Label;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EdgeEndBundle extends EdgeEnd {
    private List edgeEnds;

    public EdgeEndBundle(BoundaryNodeRule boundaryNodeRule, EdgeEnd e) {
        super(e.getEdge(), e.getCoordinate(), e.getDirectedCoordinate(), new Label(e.getLabel()));
        this.edgeEnds = new ArrayList();
        insert(e);
    }

    public EdgeEndBundle(EdgeEnd e) {
        this(null, e);
    }

    public Label getLabel() {
        return this.label;
    }

    public Iterator iterator() {
        return this.edgeEnds.iterator();
    }

    public void insert(EdgeEnd e) {
        this.edgeEnds.add(e);
    }

    public void computeLabel(BoundaryNodeRule boundaryNodeRule) {
        boolean isArea = false;
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((EdgeEnd) it.next()).getLabel().isArea()) {
                isArea = true;
            }
        }
        if (isArea) {
            this.label = new Label(-1, -1, -1);
        } else {
            this.label = new Label(-1);
        }
        for (int i = 0; i < 2; i++) {
            computeLabelOn(i, boundaryNodeRule);
            if (isArea) {
                computeLabelSides(i);
            }
        }
    }

    private void computeLabelOn(int geomIndex, BoundaryNodeRule boundaryNodeRule) {
        int loc;
        int boundaryCount = 0;
        boolean foundInterior = false;
        Iterator it = iterator();
        while (it.hasNext()) {
            loc = ((EdgeEnd) it.next()).getLabel().getLocation(geomIndex);
            if (loc == 1) {
                boundaryCount++;
            }
            if (loc == 0) {
                foundInterior = true;
            }
        }
        loc = -1;
        if (foundInterior) {
            loc = 0;
        }
        if (boundaryCount > 0) {
            loc = GeometryGraph.determineBoundary(boundaryNodeRule, boundaryCount);
        }
        this.label.setLocation(geomIndex, loc);
    }

    private void computeLabelSides(int geomIndex) {
        computeLabelSide(geomIndex, 1);
        computeLabelSide(geomIndex, 2);
    }

    private void computeLabelSide(int geomIndex, int side) {
        Iterator it = iterator();
        while (it.hasNext()) {
            EdgeEnd e = (EdgeEnd) it.next();
            if (e.getLabel().isArea()) {
                int loc = e.getLabel().getLocation(geomIndex, side);
                if (loc == 0) {
                    this.label.setLocation(geomIndex, side, 0);
                    return;
                } else if (loc == 2) {
                    this.label.setLocation(geomIndex, side, 2);
                }
            }
        }
    }

    void updateIM(IntersectionMatrix im) {
        Edge.updateIM(this.label, im);
    }
}
