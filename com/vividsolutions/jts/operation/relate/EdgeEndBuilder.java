package com.vividsolutions.jts.operation.relate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geomgraph.Edge;
import com.vividsolutions.jts.geomgraph.EdgeEnd;
import com.vividsolutions.jts.geomgraph.EdgeIntersection;
import com.vividsolutions.jts.geomgraph.EdgeIntersectionList;
import com.vividsolutions.jts.geomgraph.Label;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EdgeEndBuilder {
    public List computeEdgeEnds(Iterator edges) {
        List l = new ArrayList();
        Iterator i = edges;
        while (i.hasNext()) {
            computeEdgeEnds((Edge) i.next(), l);
        }
        return l;
    }

    public void computeEdgeEnds(Edge edge, List l) {
        EdgeIntersectionList eiList = edge.getEdgeIntersectionList();
        eiList.addEndpoints();
        Iterator it = eiList.iterator();
        EdgeIntersection eiCurr = null;
        if (it.hasNext()) {
            EdgeIntersection eiNext = (EdgeIntersection) it.next();
            do {
                EdgeIntersection eiPrev = eiCurr;
                eiCurr = eiNext;
                eiNext = null;
                if (it.hasNext()) {
                    eiNext = (EdgeIntersection) it.next();
                }
                if (eiCurr != null) {
                    createEdgeEndForPrev(edge, l, eiCurr, eiPrev);
                    createEdgeEndForNext(edge, l, eiCurr, eiNext);
                    continue;
                }
            } while (eiCurr != null);
        }
    }

    void createEdgeEndForPrev(Edge edge, List l, EdgeIntersection eiCurr, EdgeIntersection eiPrev) {
        int iPrev = eiCurr.segmentIndex;
        if (eiCurr.dist == 0.0d) {
            if (iPrev != 0) {
                iPrev--;
            } else {
                return;
            }
        }
        Coordinate pPrev = edge.getCoordinate(iPrev);
        if (eiPrev != null && eiPrev.segmentIndex >= iPrev) {
            pPrev = eiPrev.coord;
        }
        Label label = new Label(edge.getLabel());
        label.flip();
        l.add(new EdgeEnd(edge, eiCurr.coord, pPrev, label));
    }

    void createEdgeEndForNext(Edge edge, List l, EdgeIntersection eiCurr, EdgeIntersection eiNext) {
        int iNext = eiCurr.segmentIndex + 1;
        if (iNext < edge.getNumPoints() || eiNext != null) {
            Coordinate pNext = edge.getCoordinate(iNext);
            if (eiNext != null && eiNext.segmentIndex == eiCurr.segmentIndex) {
                pNext = eiNext.coord;
            }
            l.add(new EdgeEnd(edge, eiCurr.coord, pNext, new Label(edge.getLabel())));
        }
    }
}
