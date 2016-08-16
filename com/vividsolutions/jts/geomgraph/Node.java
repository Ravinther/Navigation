package com.vividsolutions.jts.geomgraph;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.IntersectionMatrix;
import loquendo.tts.engine.TTSConst;

public class Node extends GraphComponent {
    protected Coordinate coord;
    protected EdgeEndStar edges;

    public Node(Coordinate coord, EdgeEndStar edges) {
        this.coord = coord;
        this.edges = edges;
        this.label = new Label(0, -1);
    }

    public Coordinate getCoordinate() {
        return this.coord;
    }

    public EdgeEndStar getEdges() {
        return this.edges;
    }

    public boolean isIsolated() {
        return this.label.getGeometryCount() == 1;
    }

    protected void computeIM(IntersectionMatrix im) {
    }

    public void add(EdgeEnd e) {
        this.edges.insert(e);
        e.setNode(this);
    }

    public void setLabel(int argIndex, int onLocation) {
        if (this.label == null) {
            this.label = new Label(argIndex, onLocation);
        } else {
            this.label.setLocation(argIndex, onLocation);
        }
    }

    public void setLabelBoundary(int argIndex) {
        if (this.label != null) {
            int newLoc;
            int loc = -1;
            if (this.label != null) {
                loc = this.label.getLocation(argIndex);
            }
            switch (loc) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    newLoc = 1;
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    newLoc = 0;
                    break;
                default:
                    newLoc = 1;
                    break;
            }
            this.label.setLocation(argIndex, newLoc);
        }
    }
}
