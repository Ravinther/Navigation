package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Coordinate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class NodeMap {
    NodeFactory nodeFact;
    Map nodeMap;

    public NodeMap(NodeFactory nodeFact) {
        this.nodeMap = new TreeMap();
        this.nodeFact = nodeFact;
    }

    public Node addNode(Coordinate coord) {
        Node node = (Node) this.nodeMap.get(coord);
        if (node != null) {
            return node;
        }
        node = this.nodeFact.createNode(coord);
        this.nodeMap.put(coord, node);
        return node;
    }

    public void add(EdgeEnd e) {
        addNode(e.getCoordinate()).add(e);
    }

    public Node find(Coordinate coord) {
        return (Node) this.nodeMap.get(coord);
    }

    public Iterator iterator() {
        return this.nodeMap.values().iterator();
    }

    public Collection getBoundaryNodes(int geomIndex) {
        Collection bdyNodes = new ArrayList();
        Iterator i = iterator();
        while (i.hasNext()) {
            Node node = (Node) i.next();
            if (node.getLabel().getLocation(geomIndex) == 1) {
                bdyNodes.add(node);
            }
        }
        return bdyNodes;
    }
}
