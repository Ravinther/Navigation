package com.vividsolutions.jts.algorithm;

public class NotRepresentableException extends Exception {
    public NotRepresentableException() {
        super("Projective point not representable on the Cartesian plane.");
    }
}
