package com.vividsolutions.jts.geomgraph.index;

import java.util.List;

public abstract class EdgeSetIntersector {
    public abstract void computeIntersections(List list, SegmentIntersector segmentIntersector, boolean z);

    public abstract void computeIntersections(List list, List list2, SegmentIntersector segmentIntersector);
}
