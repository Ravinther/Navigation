package com.sygic.aura.helper.imageMetadataExtractor.imaging;

public final class PhotographicConversions {
    public static final double ROOT_TWO;

    static {
        ROOT_TWO = Math.sqrt(2.0d);
    }

    public static double apertureToFStop(double aperture) {
        return Math.pow(ROOT_TWO, aperture);
    }
}
