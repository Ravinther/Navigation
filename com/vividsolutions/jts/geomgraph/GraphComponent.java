package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.IntersectionMatrix;
import com.vividsolutions.jts.util.Assert;

public abstract class GraphComponent {
    private boolean isCovered;
    private boolean isCoveredSet;
    private boolean isInResult;
    private boolean isVisited;
    protected Label label;

    protected abstract void computeIM(IntersectionMatrix intersectionMatrix);

    public GraphComponent() {
        this.isInResult = false;
        this.isCovered = false;
        this.isCoveredSet = false;
        this.isVisited = false;
    }

    public Label getLabel() {
        return this.label;
    }

    public void updateIM(IntersectionMatrix im) {
        Assert.isTrue(this.label.getGeometryCount() >= 2, "found partial label");
        computeIM(im);
    }
}
