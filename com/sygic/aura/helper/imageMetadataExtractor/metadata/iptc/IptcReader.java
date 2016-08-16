package com.sygic.aura.helper.imageMetadataExtractor.metadata.iptc;

import com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentMetadataReader;
import com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentType;
import com.sygic.aura.helper.imageMetadataExtractor.lang.SequentialByteArrayReader;
import com.sygic.aura.helper.imageMetadataExtractor.lang.SequentialReader;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;
import java.io.IOException;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class IptcReader implements JpegSegmentMetadataReader {
    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Arrays.asList(new JpegSegmentType[]{JpegSegmentType.APPD});
    }

    public boolean canProcess(byte[] segmentBytes, JpegSegmentType segmentType) {
        return segmentBytes.length != 0 && segmentBytes[0] == 28;
    }

    public void extract(byte[] segmentBytes, Metadata metadata, JpegSegmentType segmentType) {
        extract(new SequentialByteArrayReader(segmentBytes), metadata, (long) segmentBytes.length);
    }

    public void extract(SequentialReader reader, Metadata metadata, long length) {
        IptcDirectory directory = (IptcDirectory) metadata.getOrCreateDirectory(IptcDirectory.class);
        int offset = 0;
        while (((long) offset) < length) {
            try {
                offset++;
                if (reader.getUInt8() != (short) 28) {
                    directory.addError("Invalid start to IPTC tag");
                    return;
                } else if (((long) (offset + 5)) >= length) {
                    directory.addError("Too few bytes remain for a valid IPTC tag");
                    return;
                } else {
                    try {
                        int directoryType = reader.getUInt8();
                        int tagType = reader.getUInt8();
                        int tagByteCount = reader.getUInt16();
                        offset += 4;
                        if (((long) (offset + tagByteCount)) > length) {
                            directory.addError("Data for tag extends beyond end of IPTC segment");
                            return;
                        }
                        try {
                            processTag(reader, directory, directoryType, tagType, tagByteCount);
                            offset += tagByteCount;
                        } catch (IOException e) {
                            directory.addError("Error processing IPTC tag");
                            return;
                        }
                    } catch (IOException e2) {
                        directory.addError("IPTC data segment ended mid-way through tag descriptor");
                        return;
                    }
                }
            } catch (IOException e3) {
                directory.addError("Unable to read starting byte of IPTC tag");
                return;
            }
        }
    }

    private void processTag(SequentialReader reader, Directory directory, int directoryType, int tagType, int tagByteCount) throws IOException {
        int tagIdentifier = tagType | (directoryType << 8);
        String string = null;
        switch (tagIdentifier) {
            case 512:
                int shortValue = reader.getUInt16();
                reader.skip((long) (tagByteCount - 2));
                directory.setInt(tagIdentifier, shortValue);
                return;
            case 522:
                directory.setInt(tagIdentifier, reader.getUInt8());
                reader.skip((long) (tagByteCount - 1));
                return;
            case 542:
            case 567:
                if (tagByteCount < 8) {
                    reader.skip((long) tagByteCount);
                    break;
                }
                string = reader.getString(tagByteCount);
                try {
                    directory.setDate(tagIdentifier, new GregorianCalendar(Integer.parseInt(string.substring(0, 4)), Integer.parseInt(string.substring(4, 6)) - 1, Integer.parseInt(string.substring(6, 8))).getTime());
                    return;
                } catch (NumberFormatException e) {
                    break;
                }
        }
        if (string == null) {
            string = reader.getString(tagByteCount, System.getProperty("file.encoding"));
        }
        if (directory.containsTag(tagIdentifier)) {
            String[] newStrings;
            String[] oldStrings = directory.getStringArray(tagIdentifier);
            if (oldStrings == null) {
                newStrings = new String[1];
            } else {
                newStrings = new String[(oldStrings.length + 1)];
                System.arraycopy(oldStrings, 0, newStrings, 0, oldStrings.length);
            }
            newStrings[newStrings.length - 1] = string;
            directory.setStringArray(tagIdentifier, newStrings);
            return;
        }
        directory.setString(tagIdentifier, string);
    }
}
