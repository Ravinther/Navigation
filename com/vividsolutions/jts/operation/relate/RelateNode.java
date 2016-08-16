package com.vividsolutions.jts.operation.relate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.IntersectionMatrix;
import com.vividsolutions.jts.geomgraph.EdgeEndStar;
import com.vividsolutions.jts.geomgraph.Node;

public class RelateNode extends Node {
    public RelateNode(Coordinate coord, EdgeEndStar edges) {
        super(coord, edges);
    }

    protected void computeIM(IntersectionMatrix im) {
        im.setAtLeastIfValid(this.label.getLocation(0), this.label.getLocation(1), 0);
    }

    void updateIMFromEdges(IntersectionMatrix im) {
        ((EdgeEndBundleStar) this.edges).updateIM(im);
    }
}
