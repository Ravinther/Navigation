package com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg;

import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;

public interface JpegSegmentMetadataReader {
    boolean canProcess(byte[] bArr, JpegSegmentType jpegSegmentType);

    void extract(byte[] bArr, Metadata metadata, JpegSegmentType jpegSegmentType);

    Iterable<JpegSegmentType> getSegmentTypes();
}
