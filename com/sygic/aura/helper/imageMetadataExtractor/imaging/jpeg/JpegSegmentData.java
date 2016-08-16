package com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JpegSegmentData {
    private final HashMap<Byte, List<byte[]>> _segmentDataMap;

    public JpegSegmentData() {
        this._segmentDataMap = new HashMap(10);
    }

    public void addSegment(byte segmentType, byte[] segmentBytes) {
        getOrCreateSegmentList(segmentType).add(segmentBytes);
    }

    public Iterable<byte[]> getSegments(JpegSegmentType segmentType) {
        return getSegments(segmentType.byteValue);
    }

    public Iterable<byte[]> getSegments(byte segmentType) {
        Iterable<byte[]> segmentList = getSegmentList(segmentType);
        return segmentList == null ? new ArrayList() : segmentList;
    }

    private List<byte[]> getSegmentList(byte segmentType) {
        return (List) this._segmentDataMap.get(Byte.valueOf(segmentType));
    }

    private List<byte[]> getOrCreateSegmentList(byte segmentType) {
        if (this._segmentDataMap.containsKey(Byte.valueOf(segmentType))) {
            return (List) this._segmentDataMap.get(Byte.valueOf(segmentType));
        }
        List<byte[]> segmentList = new ArrayList();
        this._segmentDataMap.put(Byte.valueOf(segmentType), segmentList);
        return segmentList;
    }
}
