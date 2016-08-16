package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Coordinate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlanarGraph {
    protected List edgeEndList;
    protected List edges;
    protected NodeMap nodes;

    public PlanarGraph() {
        this.edges = new ArrayList();
        this.edgeEndList = new ArrayList();
        this.nodes = new NodeMap(new NodeFactory());
    }

    public Iterator getEdgeIterator() {
        return this.edges.iterator();
    }

    public boolean isBoundaryNode(int geomIndex, Coordinate coord) {
        Node node = this.nodes.find(coord);
        if (node == null) {
            return false;
        }
        Label label = node.getLabel();
        if (label == null || label.getLocation(geomIndex) != 1) {
            return false;
        }
        return true;
    }

    protected void insertEdge(Edge e) {
        this.edges.add(e);
    }

    public Iterator getNodeIterator() {
        return this.nodes.iterator();
    }
}
