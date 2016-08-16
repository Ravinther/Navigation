package com.vividsolutions.jts.algorithm;

public interface BoundaryNodeRule {
    public static final BoundaryNodeRule ENDPOINT_BOUNDARY_RULE;
    public static final BoundaryNodeRule MOD2_BOUNDARY_RULE;
    public static final BoundaryNodeRule MONOVALENT_ENDPOINT_BOUNDARY_RULE;
    public static final BoundaryNodeRule MULTIVALENT_ENDPOINT_BOUNDARY_RULE;
    public static final BoundaryNodeRule OGC_SFS_BOUNDARY_RULE;

    public static class EndPointBoundaryNodeRule implements BoundaryNodeRule {
        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount > 0;
        }
    }

    public static class Mod2BoundaryNodeRule implements BoundaryNodeRule {
        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount % 2 == 1;
        }
    }

    public static class MonoValentEndPointBoundaryNodeRule implements BoundaryNodeRule {
        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount == 1;
        }
    }

    public static class MultiValentEndPointBoundaryNodeRule implements BoundaryNodeRule {
        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount > 1;
        }
    }

    boolean isInBoundary(int i);

    static {
        MOD2_BOUNDARY_RULE = new Mod2BoundaryNodeRule();
        ENDPOINT_BOUNDARY_RULE = new EndPointBoundaryNodeRule();
        MULTIVALENT_ENDPOINT_BOUNDARY_RULE = new MultiValentEndPointBoundaryNodeRule();
        MONOVALENT_ENDPOINT_BOUNDARY_RULE = new MonoValentEndPointBoundaryNodeRule();
        OGC_SFS_BOUNDARY_RULE = MOD2_BOUNDARY_RULE;
    }
}
