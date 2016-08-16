package com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff;

import com.sygic.aura.helper.imageMetadataExtractor.lang.RandomAccessStreamReader;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.exif.ExifReader;
import java.io.InputStream;

public class TiffMetadataReader {
    public static Metadata readMetadata(InputStream inputStream) {
        Metadata metadata = new Metadata();
        new ExifReader().extractTiff(new RandomAccessStreamReader(inputStream), metadata);
        return metadata;
    }
}
