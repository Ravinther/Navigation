package com.vividsolutions.jts.operation.relate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geomgraph.Node;
import com.vividsolutions.jts.geomgraph.NodeFactory;

public class RelateNodeFactory extends NodeFactory {
    public Node createNode(Coordinate coord) {
        return new RelateNode(coord, new EdgeEndBundleStar());
    }
}
