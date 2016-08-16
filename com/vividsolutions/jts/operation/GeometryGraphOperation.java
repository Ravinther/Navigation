package com.vividsolutions.jts.operation;

import com.vividsolutions.jts.algorithm.BoundaryNodeRule;
import com.vividsolutions.jts.algorithm.LineIntersector;
import com.vividsolutions.jts.algorithm.RobustLineIntersector;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geomgraph.GeometryGraph;

public class GeometryGraphOperation {
    protected GeometryGraph[] arg;
    protected final LineIntersector li;
    protected PrecisionModel resultPrecisionModel;

    public GeometryGraphOperation(Geometry g0, Geometry g1) {
        this(g0, g1, BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE);
    }

    public GeometryGraphOperation(Geometry g0, Geometry g1, BoundaryNodeRule boundaryNodeRule) {
        this.li = new RobustLineIntersector();
        if (g0.getPrecisionModel().compareTo(g1.getPrecisionModel()) >= 0) {
            setComputationPrecision(g0.getPrecisionModel());
        } else {
            setComputationPrecision(g1.getPrecisionModel());
        }
        this.arg = new GeometryGraph[2];
        this.arg[0] = new GeometryGraph(0, g0, boundaryNodeRule);
        this.arg[1] = new GeometryGraph(1, g1, boundaryNodeRule);
    }

    protected void setComputationPrecision(PrecisionModel pm) {
        this.resultPrecisionModel = pm;
        this.li.setPrecisionModel(this.resultPrecisionModel);
    }
}
