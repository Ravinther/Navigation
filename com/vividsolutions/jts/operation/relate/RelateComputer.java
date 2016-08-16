package com.vividsolutions.jts.operation.relate;

import com.vividsolutions.jts.algorithm.LineIntersector;
import com.vividsolutions.jts.algorithm.PointLocator;
import com.vividsolutions.jts.algorithm.RobustLineIntersector;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.IntersectionMatrix;
import com.vividsolutions.jts.geomgraph.Edge;
import com.vividsolutions.jts.geomgraph.EdgeEnd;
import com.vividsolutions.jts.geomgraph.EdgeIntersection;
import com.vividsolutions.jts.geomgraph.GeometryGraph;
import com.vividsolutions.jts.geomgraph.Label;
import com.vividsolutions.jts.geomgraph.Node;
import com.vividsolutions.jts.geomgraph.NodeMap;
import com.vividsolutions.jts.geomgraph.index.SegmentIntersector;
import com.vividsolutions.jts.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RelateComputer {
    private GeometryGraph[] arg;
    private IntersectionMatrix im;
    private ArrayList isolatedEdges;
    private LineIntersector li;
    private NodeMap nodes;
    private PointLocator ptLocator;

    public RelateComputer(GeometryGraph[] arg) {
        this.li = new RobustLineIntersector();
        this.ptLocator = new PointLocator();
        this.nodes = new NodeMap(new RelateNodeFactory());
        this.im = null;
        this.isolatedEdges = new ArrayList();
        this.arg = arg;
    }

    public IntersectionMatrix computeIM() {
        IntersectionMatrix im = new IntersectionMatrix();
        im.set(2, 2, 2);
        if (this.arg[0].getGeometry().getEnvelopeInternal().intersects(this.arg[1].getGeometry().getEnvelopeInternal())) {
            this.arg[0].computeSelfNodes(this.li, false);
            this.arg[1].computeSelfNodes(this.li, false);
            SegmentIntersector intersector = this.arg[0].computeEdgeIntersections(this.arg[1], this.li, false);
            computeIntersectionNodes(0);
            computeIntersectionNodes(1);
            copyNodesAndLabels(0);
            copyNodesAndLabels(1);
            labelIsolatedNodes();
            computeProperIntersectionIM(intersector, im);
            EdgeEndBuilder eeBuilder = new EdgeEndBuilder();
            insertEdgeEnds(eeBuilder.computeEdgeEnds(this.arg[0].getEdgeIterator()));
            insertEdgeEnds(eeBuilder.computeEdgeEnds(this.arg[1].getEdgeIterator()));
            labelNodeEdges();
            labelIsolatedEdges(0, 1);
            labelIsolatedEdges(1, 0);
            updateIM(im);
        } else {
            computeDisjointIM(im);
        }
        return im;
    }

    private void insertEdgeEnds(List ee) {
        for (EdgeEnd e : ee) {
            this.nodes.add(e);
        }
    }

    private void computeProperIntersectionIM(SegmentIntersector intersector, IntersectionMatrix im) {
        int dimA = this.arg[0].getGeometry().getDimension();
        int dimB = this.arg[1].getGeometry().getDimension();
        boolean hasProper = intersector.hasProperIntersection();
        boolean hasProperInterior = intersector.hasProperInteriorIntersection();
        if (dimA == 2 && dimB == 2) {
            if (hasProper) {
                im.setAtLeast("212101212");
            }
        } else if (dimA == 2 && dimB == 1) {
            if (hasProper) {
                im.setAtLeast("FFF0FFFF2");
            }
            if (hasProperInterior) {
                im.setAtLeast("1FFFFF1FF");
            }
        } else if (dimA == 1 && dimB == 2) {
            if (hasProper) {
                im.setAtLeast("F0FFFFFF2");
            }
            if (hasProperInterior) {
                im.setAtLeast("1F1FFFFFF");
            }
        } else if (dimA == 1 && dimB == 1 && hasProperInterior) {
            im.setAtLeast("0FFFFFFFF");
        }
    }

    private void copyNodesAndLabels(int argIndex) {
        Iterator i = this.arg[argIndex].getNodeIterator();
        while (i.hasNext()) {
            Node graphNode = (Node) i.next();
            this.nodes.addNode(graphNode.getCoordinate()).setLabel(argIndex, graphNode.getLabel().getLocation(argIndex));
        }
    }

    private void computeIntersectionNodes(int argIndex) {
        Iterator i = this.arg[argIndex].getEdgeIterator();
        while (i.hasNext()) {
            Edge e = (Edge) i.next();
            int eLoc = e.getLabel().getLocation(argIndex);
            Iterator eiIt = e.getEdgeIntersectionList().iterator();
            while (eiIt.hasNext()) {
                RelateNode n = (RelateNode) this.nodes.addNode(((EdgeIntersection) eiIt.next()).coord);
                if (eLoc == 1) {
                    n.setLabelBoundary(argIndex);
                } else if (n.getLabel().isNull(argIndex)) {
                    n.setLabel(argIndex, 0);
                }
            }
        }
    }

    private void computeDisjointIM(IntersectionMatrix im) {
        Geometry ga = this.arg[0].getGeometry();
        if (!ga.isEmpty()) {
            im.set(0, 2, ga.getDimension());
            im.set(1, 2, ga.getBoundaryDimension());
        }
        Geometry gb = this.arg[1].getGeometry();
        if (!gb.isEmpty()) {
            im.set(2, 0, gb.getDimension());
            im.set(2, 1, gb.getBoundaryDimension());
        }
    }

    private void labelNodeEdges() {
        Iterator ni = this.nodes.iterator();
        while (ni.hasNext()) {
            ((RelateNode) ni.next()).getEdges().computeLabelling(this.arg);
        }
    }

    private void updateIM(IntersectionMatrix im) {
        Iterator ei = this.isolatedEdges.iterator();
        while (ei.hasNext()) {
            ((Edge) ei.next()).updateIM(im);
        }
        Iterator ni = this.nodes.iterator();
        while (ni.hasNext()) {
            RelateNode node = (RelateNode) ni.next();
            node.updateIM(im);
            node.updateIMFromEdges(im);
        }
    }

    private void labelIsolatedEdges(int thisIndex, int targetIndex) {
        Iterator ei = this.arg[thisIndex].getEdgeIterator();
        while (ei.hasNext()) {
            Edge e = (Edge) ei.next();
            if (e.isIsolated()) {
                labelIsolatedEdge(e, targetIndex, this.arg[targetIndex].getGeometry());
                this.isolatedEdges.add(e);
            }
        }
    }

    private void labelIsolatedEdge(Edge e, int targetIndex, Geometry target) {
        if (target.getDimension() > 0) {
            e.getLabel().setAllLocations(targetIndex, this.ptLocator.locate(e.getCoordinate(), target));
            return;
        }
        e.getLabel().setAllLocations(targetIndex, 2);
    }

    private void labelIsolatedNodes() {
        Iterator ni = this.nodes.iterator();
        while (ni.hasNext()) {
            boolean z;
            Node n = (Node) ni.next();
            Label label = n.getLabel();
            if (label.getGeometryCount() > 0) {
                z = true;
            } else {
                z = false;
            }
            Assert.isTrue(z, "node with empty label found");
            if (n.isIsolated()) {
                if (label.isNull(0)) {
                    labelIsolatedNode(n, 0);
                } else {
                    labelIsolatedNode(n, 1);
                }
            }
        }
    }

    private void labelIsolatedNode(Node n, int targetIndex) {
        n.getLabel().setAllLocations(targetIndex, this.ptLocator.locate(n.getCoordinate(), this.arg[targetIndex].getGeometry()));
    }
}
