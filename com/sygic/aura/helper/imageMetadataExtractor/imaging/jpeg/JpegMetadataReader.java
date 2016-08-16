package com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg;

import com.sygic.aura.helper.imageMetadataExtractor.lang.StreamReader;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.exif.ExifReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class JpegMetadataReader {
    public static Metadata readMetadata(InputStream inputStream) throws JpegProcessingException, IOException {
        Metadata metadata = new Metadata();
        process(metadata, inputStream);
        return metadata;
    }

    public static void process(Metadata metadata, InputStream inputStream) throws JpegProcessingException, IOException {
        Set<JpegSegmentType> segmentTypes = new HashSet();
        JpegSegmentMetadataReader reader = new ExifReader();
        for (JpegSegmentType type : reader.getSegmentTypes()) {
            segmentTypes.add(type);
        }
        processJpegSegmentData(metadata, reader, JpegSegmentReader.readSegments(new StreamReader(inputStream), segmentTypes));
    }

    public static void processJpegSegmentData(Metadata metadata, JpegSegmentMetadataReader reader, JpegSegmentData segmentData) {
        for (JpegSegmentType segmentType : reader.getSegmentTypes()) {
            for (byte[] segmentBytes : segmentData.getSegments(segmentType)) {
                if (reader.canProcess(segmentBytes, segmentType)) {
                    reader.extract(segmentBytes, metadata, segmentType);
                }
            }
        }
    }
}
