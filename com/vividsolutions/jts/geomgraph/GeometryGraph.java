package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.algorithm.BoundaryNodeRule;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.algorithm.LineIntersector;
import com.vividsolutions.jts.algorithm.PointLocator;
import com.vividsolutions.jts.algorithm.locate.PointOnGeometryLocator;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateArrays;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geomgraph.index.EdgeSetIntersector;
import com.vividsolutions.jts.geomgraph.index.SegmentIntersector;
import com.vividsolutions.jts.geomgraph.index.SimpleMCSweepLineIntersector;
import com.vividsolutions.jts.util.Assert;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GeometryGraph extends PlanarGraph {
    private PointOnGeometryLocator areaPtLocator;
    private int argIndex;
    private BoundaryNodeRule boundaryNodeRule;
    private Collection boundaryNodes;
    private boolean hasTooFewPoints;
    private Coordinate invalidPoint;
    private Map lineEdgeMap;
    private Geometry parentGeom;
    private final PointLocator ptLocator;
    private boolean useBoundaryDeterminationRule;

    public static int determineBoundary(BoundaryNodeRule boundaryNodeRule, int boundaryCount) {
        return boundaryNodeRule.isInBoundary(boundaryCount) ? 1 : 0;
    }

    private EdgeSetIntersector createEdgeSetIntersector() {
        return new SimpleMCSweepLineIntersector();
    }

    public GeometryGraph(int argIndex, Geometry parentGeom, BoundaryNodeRule boundaryNodeRule) {
        this.lineEdgeMap = new HashMap();
        this.boundaryNodeRule = null;
        this.useBoundaryDeterminationRule = true;
        this.hasTooFewPoints = false;
        this.invalidPoint = null;
        this.areaPtLocator = null;
        this.ptLocator = new PointLocator();
        this.argIndex = argIndex;
        this.parentGeom = parentGeom;
        this.boundaryNodeRule = boundaryNodeRule;
        if (parentGeom != null) {
            add(parentGeom);
        }
    }

    public Geometry getGeometry() {
        return this.parentGeom;
    }

    public BoundaryNodeRule getBoundaryNodeRule() {
        return this.boundaryNodeRule;
    }

    public Collection getBoundaryNodes() {
        if (this.boundaryNodes == null) {
            this.boundaryNodes = this.nodes.getBoundaryNodes(this.argIndex);
        }
        return this.boundaryNodes;
    }

    private void add(Geometry g) {
        if (!g.isEmpty()) {
            if (g instanceof MultiPolygon) {
                this.useBoundaryDeterminationRule = false;
            }
            if (g instanceof Polygon) {
                addPolygon((Polygon) g);
            } else if (g instanceof LineString) {
                addLineString((LineString) g);
            } else if (g instanceof Point) {
                addPoint((Point) g);
            } else if (g instanceof MultiPoint) {
                addCollection((MultiPoint) g);
            } else if (g instanceof MultiLineString) {
                addCollection((MultiLineString) g);
            } else if (g instanceof MultiPolygon) {
                addCollection((MultiPolygon) g);
            } else if (g instanceof GeometryCollection) {
                addCollection((GeometryCollection) g);
            } else {
                throw new UnsupportedOperationException(g.getClass().getName());
            }
        }
    }

    private void addCollection(GeometryCollection gc) {
        for (int i = 0; i < gc.getNumGeometries(); i++) {
            add(gc.getGeometryN(i));
        }
    }

    private void addPoint(Point p) {
        insertPoint(this.argIndex, p.getCoordinate(), 0);
    }

    private void addPolygonRing(LinearRing lr, int cwLeft, int cwRight) {
        if (!lr.isEmpty()) {
            Coordinate[] coord = CoordinateArrays.removeRepeatedPoints(lr.getCoordinates());
            if (coord.length < 4) {
                this.hasTooFewPoints = true;
                this.invalidPoint = coord[0];
                return;
            }
            int left = cwLeft;
            int right = cwRight;
            if (CGAlgorithms.isCCW(coord)) {
                left = cwRight;
                right = cwLeft;
            }
            Edge e = new Edge(coord, new Label(this.argIndex, 1, left, right));
            this.lineEdgeMap.put(lr, e);
            insertEdge(e);
            insertPoint(this.argIndex, coord[0], 1);
        }
    }

    private void addPolygon(Polygon p) {
        addPolygonRing((LinearRing) p.getExteriorRing(), 2, 0);
        for (int i = 0; i < p.getNumInteriorRing(); i++) {
            addPolygonRing((LinearRing) p.getInteriorRingN(i), 0, 2);
        }
    }

    private void addLineString(LineString line) {
        boolean z = true;
        Coordinate[] coord = CoordinateArrays.removeRepeatedPoints(line.getCoordinates());
        if (coord.length < 2) {
            this.hasTooFewPoints = true;
            this.invalidPoint = coord[0];
            return;
        }
        Edge e = new Edge(coord, new Label(this.argIndex, 0));
        this.lineEdgeMap.put(line, e);
        insertEdge(e);
        if (coord.length < 2) {
            z = false;
        }
        Assert.isTrue(z, "found LineString with single point");
        insertBoundaryPoint(this.argIndex, coord[0]);
        insertBoundaryPoint(this.argIndex, coord[coord.length - 1]);
    }

    public SegmentIntersector computeSelfNodes(LineIntersector li, boolean computeRingSelfNodes) {
        SegmentIntersector si = new SegmentIntersector(li, true, false);
        EdgeSetIntersector esi = createEdgeSetIntersector();
        if (computeRingSelfNodes || !((this.parentGeom instanceof LinearRing) || (this.parentGeom instanceof Polygon) || (this.parentGeom instanceof MultiPolygon))) {
            esi.computeIntersections(this.edges, si, true);
        } else {
            esi.computeIntersections(this.edges, si, false);
        }
        addSelfIntersectionNodes(this.argIndex);
        return si;
    }

    public SegmentIntersector computeEdgeIntersections(GeometryGraph g, LineIntersector li, boolean includeProper) {
        SegmentIntersector si = new SegmentIntersector(li, includeProper, true);
        si.setBoundaryNodes(getBoundaryNodes(), g.getBoundaryNodes());
        createEdgeSetIntersector().computeIntersections(this.edges, g.edges, si);
        return si;
    }

    private void insertPoint(int argIndex, Coordinate coord, int onLocation) {
        Node n = this.nodes.addNode(coord);
        Label lbl = n.getLabel();
        if (lbl == null) {
            n.label = new Label(argIndex, onLocation);
        } else {
            lbl.setLocation(argIndex, onLocation);
        }
    }

    private void insertBoundaryPoint(int argIndex, Coordinate coord) {
        Label lbl = this.nodes.addNode(coord).getLabel();
        int boundaryCount = 1;
        if (lbl.getLocation(argIndex, 0) == 1) {
            boundaryCount = 1 + 1;
        }
        lbl.setLocation(argIndex, determineBoundary(this.boundaryNodeRule, boundaryCount));
    }

    private void addSelfIntersectionNodes(int argIndex) {
        for (Edge e : this.edges) {
            int eLoc = e.getLabel().getLocation(argIndex);
            Iterator eiIt = e.eiList.iterator();
            while (eiIt.hasNext()) {
                addSelfIntersectionNode(argIndex, ((EdgeIntersection) eiIt.next()).coord, eLoc);
            }
        }
    }

    private void addSelfIntersectionNode(int argIndex, Coordinate coord, int loc) {
        if (!isBoundaryNode(argIndex, coord)) {
            if (loc == 1 && this.useBoundaryDeterminationRule) {
                insertBoundaryPoint(argIndex, coord);
            } else {
                insertPoint(argIndex, coord, loc);
            }
        }
    }
}
