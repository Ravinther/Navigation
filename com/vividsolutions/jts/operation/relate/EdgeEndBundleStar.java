package com.vividsolutions.jts.operation.relate;

import com.vividsolutions.jts.geom.IntersectionMatrix;
import com.vividsolutions.jts.geomgraph.EdgeEnd;
import com.vividsolutions.jts.geomgraph.EdgeEndStar;
import java.util.Iterator;

public class EdgeEndBundleStar extends EdgeEndStar {
    public void insert(EdgeEnd e) {
        EdgeEndBundle eb = (EdgeEndBundle) this.edgeMap.get(e);
        if (eb == null) {
            insertEdgeEnd(e, new EdgeEndBundle(e));
        } else {
            eb.insert(e);
        }
    }

    void updateIM(IntersectionMatrix im) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((EdgeEndBundle) it.next()).updateIM(im);
        }
    }
}
