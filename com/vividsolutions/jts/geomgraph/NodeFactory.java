package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Coordinate;

public class NodeFactory {
    public Node createNode(Coordinate coord) {
        return new Node(coord, null);
    }
}
