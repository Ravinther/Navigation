package com.vividsolutions.jts.geom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PrecisionModel implements Serializable, Comparable {
    public static final Type FIXED;
    public static final Type FLOATING;
    public static final Type FLOATING_SINGLE;
    private Type modelType;
    private double scale;

    public static class Type implements Serializable {
        private static Map nameToTypeMap;
        private String name;

        static {
            nameToTypeMap = new HashMap();
        }

        public Type(String name) {
            this.name = name;
            nameToTypeMap.put(name, this);
        }

        public String toString() {
            return this.name;
        }
    }

    static {
        FIXED = new Type("FIXED");
        FLOATING = new Type("FLOATING");
        FLOATING_SINGLE = new Type("FLOATING SINGLE");
    }

    public PrecisionModel() {
        this.modelType = FLOATING;
    }

    public int getMaximumSignificantDigits() {
        if (this.modelType == FLOATING) {
            return 16;
        }
        if (this.modelType == FLOATING_SINGLE) {
            return 6;
        }
        if (this.modelType == FIXED) {
            return ((int) Math.ceil(Math.log(getScale()) / Math.log(10.0d))) + 1;
        }
        return 16;
    }

    public double getScale() {
        return this.scale;
    }

    public double makePrecise(double val) {
        if (Double.isNaN(val)) {
            return val;
        }
        if (this.modelType == FLOATING_SINGLE) {
            return (double) ((float) val);
        }
        if (this.modelType == FIXED) {
            return ((double) Math.round(this.scale * val)) / this.scale;
        }
        return val;
    }

    public void makePrecise(Coordinate coord) {
        if (this.modelType != FLOATING) {
            coord.f1295x = makePrecise(coord.f1295x);
            coord.f1296y = makePrecise(coord.f1296y);
        }
    }

    public String toString() {
        String description = "UNKNOWN";
        if (this.modelType == FLOATING) {
            return "Floating";
        }
        if (this.modelType == FLOATING_SINGLE) {
            return "Floating-Single";
        }
        if (this.modelType == FIXED) {
            return "Fixed (Scale=" + getScale() + ")";
        }
        return description;
    }

    public boolean equals(Object other) {
        if (!(other instanceof PrecisionModel)) {
            return false;
        }
        PrecisionModel otherPrecisionModel = (PrecisionModel) other;
        if (this.modelType == otherPrecisionModel.modelType && this.scale == otherPrecisionModel.scale) {
            return true;
        }
        return false;
    }

    public int compareTo(Object o) {
        PrecisionModel other = (PrecisionModel) o;
        return new Integer(getMaximumSignificantDigits()).compareTo(new Integer(other.getMaximumSignificantDigits()));
    }
}
