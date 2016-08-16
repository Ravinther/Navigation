package com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum JpegSegmentType {
    APP0((byte) -32, true),
    APP1((byte) -31, true),
    APP2((byte) -30, true),
    APP3((byte) -29, true),
    APP4((byte) -28, true),
    APP5((byte) -27, true),
    APP6((byte) -26, true),
    APP7((byte) -25, true),
    APP8((byte) -24, true),
    APP9((byte) -23, true),
    APPA((byte) -22, true),
    APPB((byte) -21, true),
    APPC((byte) -20, true),
    APPD((byte) -19, true),
    APPE((byte) -18, true),
    APPF((byte) -17, true),
    SOI((byte) -40, false),
    DQT((byte) -37, false),
    DHT((byte) -60, false),
    SOF0((byte) -64, true),
    SOF1((byte) -63, true),
    SOF2((byte) -62, true),
    SOF3((byte) -61, true),
    SOF5((byte) -59, true),
    SOF6((byte) -58, true),
    SOF7((byte) -57, true),
    SOF8((byte) -56, true),
    SOF9((byte) -55, true),
    SOF10((byte) -54, true),
    SOF11((byte) -53, true),
    SOF13((byte) -51, true),
    SOF14((byte) -50, true),
    SOF15((byte) -49, true),
    COM((byte) -2, true);
    
    public static final Collection<JpegSegmentType> canContainMetadataTypes;
    public final byte byteValue;
    public final boolean canContainMetadata;

    static {
        List<JpegSegmentType> segmentTypes = new ArrayList();
        JpegSegmentType[] jpegSegmentTypeArr = (JpegSegmentType[]) JpegSegmentType.class.getEnumConstants();
        int length = jpegSegmentTypeArr.length;
        int i;
        while (i < length) {
            JpegSegmentType segmentType = jpegSegmentTypeArr[i];
            if (segmentType.canContainMetadata) {
                segmentTypes.add(segmentType);
            }
            i++;
        }
        canContainMetadataTypes = segmentTypes;
    }

    private JpegSegmentType(byte byteValue, boolean canContainMetadata) {
        this.byteValue = byteValue;
        this.canContainMetadata = canContainMetadata;
    }
}
