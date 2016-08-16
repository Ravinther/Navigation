package com.vividsolutions.jts.geomgraph.index;

import com.vividsolutions.jts.geomgraph.Edge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleMCSweepLineIntersector extends EdgeSetIntersector {
    List events;
    int nOverlaps;

    public SimpleMCSweepLineIntersector() {
        this.events = new ArrayList();
    }

    public void computeIntersections(List edges, SegmentIntersector si, boolean testAllSegments) {
        if (testAllSegments) {
            add(edges, null);
        } else {
            add(edges);
        }
        computeIntersections(si);
    }

    public void computeIntersections(List edges0, List edges1, SegmentIntersector si) {
        add(edges0, (Object) edges0);
        add(edges1, (Object) edges1);
        computeIntersections(si);
    }

    private void add(List edges) {
        for (Edge edge : edges) {
            add(edge, (Object) edge);
        }
    }

    private void add(List edges, Object edgeSet) {
        for (Edge edge : edges) {
            add(edge, edgeSet);
        }
    }

    private void add(Edge edge, Object edgeSet) {
        MonotoneChainEdge mce = edge.getMonotoneChainEdge();
        int[] startIndex = mce.getStartIndexes();
        for (int i = 0; i < startIndex.length - 1; i++) {
            MonotoneChain mc = new MonotoneChain(mce, i);
            SweepLineEvent insertEvent = new SweepLineEvent(edgeSet, mce.getMinX(i), null, mc);
            this.events.add(insertEvent);
            this.events.add(new SweepLineEvent(edgeSet, mce.getMaxX(i), insertEvent, mc));
        }
    }

    private void prepareEvents() {
        Collections.sort(this.events);
        for (int i = 0; i < this.events.size(); i++) {
            SweepLineEvent ev = (SweepLineEvent) this.events.get(i);
            if (ev.isDelete()) {
                ev.getInsertEvent().setDeleteEventIndex(i);
            }
        }
    }

    private void computeIntersections(SegmentIntersector si) {
        this.nOverlaps = 0;
        prepareEvents();
        for (int i = 0; i < this.events.size(); i++) {
            SweepLineEvent ev = (SweepLineEvent) this.events.get(i);
            if (ev.isInsert()) {
                processOverlaps(i, ev.getDeleteEventIndex(), ev, si);
            }
        }
    }

    private void processOverlaps(int start, int end, SweepLineEvent ev0, SegmentIntersector si) {
        MonotoneChain mc0 = (MonotoneChain) ev0.getObject();
        for (int i = start; i < end; i++) {
            SweepLineEvent ev1 = (SweepLineEvent) this.events.get(i);
            if (ev1.isInsert()) {
                MonotoneChain mc1 = (MonotoneChain) ev1.getObject();
                if (ev0.edgeSet == null || ev0.edgeSet != ev1.edgeSet) {
                    mc0.computeIntersections(mc1, si);
                    this.nOverlaps++;
                }
            }
        }
    }
}
