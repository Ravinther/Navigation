package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff.TiffProcessingException;
import com.sygic.aura.helper.imageMetadataExtractor.lang.RandomAccessReader;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.tiff.DirectoryTiffHandler;
import java.io.IOException;
import java.util.Set;

public class ExifTiffHandler extends DirectoryTiffHandler {
    private boolean _storeThumbnailBytes;

    public ExifTiffHandler(Metadata metadata, boolean storeThumbnailBytes) {
        super(metadata, ExifIFD0Directory.class);
        this._storeThumbnailBytes = storeThumbnailBytes;
    }

    public void setTiffMarker(int marker) throws TiffProcessingException {
        if (marker != 42 && marker != 20306 && marker != 85) {
            throw new TiffProcessingException("Unexpected TIFF marker: 0x" + Integer.toHexString(marker));
        }
    }

    public boolean isTagIfdPointer(int tagType) {
        if (tagType == 34665 && (this._currentDirectory instanceof ExifIFD0Directory)) {
            pushDirectory(ExifSubIFDDirectory.class);
            return true;
        } else if (tagType == 34853 && (this._currentDirectory instanceof ExifIFD0Directory)) {
            pushDirectory(GpsDirectory.class);
            return true;
        } else if (tagType != 40965 || !(this._currentDirectory instanceof ExifSubIFDDirectory)) {
            return false;
        } else {
            pushDirectory(ExifInteropDirectory.class);
            return true;
        }
    }

    public boolean hasFollowerIfd() {
        if (!(this._currentDirectory instanceof ExifIFD0Directory)) {
            return false;
        }
        pushDirectory(ExifThumbnailDirectory.class);
        return true;
    }

    public boolean customProcessTag(int makernoteOffset, Set<Integer> set, int tiffHeaderOffset, RandomAccessReader reader, int tagId, int byteCount) throws IOException {
        return false;
    }

    public void completed(RandomAccessReader reader, int tiffHeaderOffset) {
        if (this._storeThumbnailBytes) {
            ExifThumbnailDirectory thumbnailDirectory = (ExifThumbnailDirectory) this._metadata.getDirectory(ExifThumbnailDirectory.class);
            if (thumbnailDirectory != null && thumbnailDirectory.containsTag(259)) {
                Integer offset = thumbnailDirectory.getInteger(513);
                Integer length = thumbnailDirectory.getInteger(514);
                if (offset != null && length != null) {
                    try {
                        thumbnailDirectory.setThumbnailData(reader.getBytes(offset.intValue() + tiffHeaderOffset, length.intValue()));
                    } catch (IOException ex) {
                        thumbnailDirectory.addError("Invalid thumbnail data specification: " + ex.getMessage());
                    }
                }
            }
        }
    }
}
