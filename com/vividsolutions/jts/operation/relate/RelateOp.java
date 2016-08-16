package com.vividsolutions.jts.operation.relate;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.IntersectionMatrix;
import com.vividsolutions.jts.operation.GeometryGraphOperation;

public class RelateOp extends GeometryGraphOperation {
    private RelateComputer relate;

    public static IntersectionMatrix relate(Geometry a, Geometry b) {
        return new RelateOp(a, b).getIntersectionMatrix();
    }

    public RelateOp(Geometry g0, Geometry g1) {
        super(g0, g1);
        this.relate = new RelateComputer(this.arg);
    }

    public IntersectionMatrix getIntersectionMatrix() {
        return this.relate.computeIM();
    }
}
