package com.sygic.aura.helper.imageMetadataExtractor.imaging;

import com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegMetadataReader;
import com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff.TiffMetadataReader;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageMetadataReader {
    public static Metadata readMetadata(InputStream inputStream) throws ImageProcessingException, IOException {
        InputStream bufferedInputStream = inputStream instanceof BufferedInputStream ? inputStream : new BufferedInputStream(inputStream);
        int magicNumber = peekMagicNumber(bufferedInputStream);
        if (magicNumber == -1) {
            throw new ImageProcessingException("Could not determine file's magic number.");
        } else if ((magicNumber & 65496) == 65496) {
            return JpegMetadataReader.readMetadata(bufferedInputStream);
        } else {
            if (magicNumber == 18761 || magicNumber == 19789) {
                return TiffMetadataReader.readMetadata(bufferedInputStream);
            }
            throw new ImageProcessingException("File format is not supported");
        }
    }

    private static int peekMagicNumber(InputStream inputStream) throws IOException {
        inputStream.mark(2);
        int byte1 = inputStream.read();
        int byte2 = inputStream.read();
        inputStream.reset();
        if (byte1 == -1 || byte2 == -1) {
            return -1;
        }
        return (byte1 << 8) | byte2;
    }
}
