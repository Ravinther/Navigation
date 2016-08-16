package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentMetadataReader;
import com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentType;
import com.sygic.aura.helper.imageMetadataExtractor.lang.RandomAccessReader;
import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import com.sygic.aura.helper.imageMetadataExtractor.lang.SequentialByteArrayReader;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata;
import com.sygic.aura.helper.imageMetadataExtractor.metadata.iptc.IptcReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

public class ExifReader implements JpegSegmentMetadataReader {
    private static final int[] BYTES_PER_FORMAT;
    private boolean _storeThumbnailBytes;

    public ExifReader() {
        this._storeThumbnailBytes = true;
    }

    static {
        BYTES_PER_FORMAT = new int[]{0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    }

    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Arrays.asList(new JpegSegmentType[]{JpegSegmentType.APP1});
    }

    public boolean canProcess(byte[] segmentBytes, JpegSegmentType segmentType) {
        return segmentBytes.length >= "Exif\u0000\u0000".length() && new String(segmentBytes, 0, "Exif\u0000\u0000".length()).equalsIgnoreCase("Exif\u0000\u0000");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void extract(byte[] r6, com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata r7, com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentType r8) {
        /*
        r5 = this;
        if (r6 != 0) goto L_0x000b;
    L_0x0002:
        r2 = new java.lang.NullPointerException;
        r3 = "segmentBytes cannot be null";
        r2.<init>(r3);
        throw r2;
    L_0x000b:
        if (r7 != 0) goto L_0x0016;
    L_0x000d:
        r2 = new java.lang.NullPointerException;
        r3 = "metadata cannot be null";
        r2.<init>(r3);
        throw r2;
    L_0x0016:
        if (r8 != 0) goto L_0x0021;
    L_0x0018:
        r2 = new java.lang.NullPointerException;
        r3 = "segmentType cannot be null";
        r2.<init>(r3);
        throw r2;
    L_0x0021:
        r1 = new com.sygic.aura.helper.imageMetadataExtractor.lang.ByteArrayReader;	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r1.<init>(r6);	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r2 = 0;
        r3 = "Exif\u0000\u0000";
        r3 = r3.length();	 Catch:{ IOException -> 0x0044, TiffProcessingException -> 0x004b }
        r2 = r1.getString(r2, r3);	 Catch:{ IOException -> 0x0044, TiffProcessingException -> 0x004b }
        r3 = "Exif\u0000\u0000";
        r2 = r2.equals(r3);	 Catch:{ IOException -> 0x0044, TiffProcessingException -> 0x004b }
        if (r2 != 0) goto L_0x0052;
    L_0x003b:
        r2 = java.lang.System.err;	 Catch:{ IOException -> 0x0044, TiffProcessingException -> 0x004b }
        r3 = "Invalid JPEG Exif segment preamble";
        r2.println(r3);	 Catch:{ IOException -> 0x0044, TiffProcessingException -> 0x004b }
    L_0x0043:
        return;
    L_0x0044:
        r0 = move-exception;
        r2 = java.lang.System.err;	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r0.printStackTrace(r2);	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        goto L_0x0043;
    L_0x004b:
        r0 = move-exception;
        r2 = java.lang.System.err;
        r0.printStackTrace(r2);
        goto L_0x0043;
    L_0x0052:
        r2 = new com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff.TiffReader;	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r2.<init>();	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r3 = new com.sygic.aura.helper.imageMetadataExtractor.metadata.exif.ExifTiffHandler;	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r4 = r5._storeThumbnailBytes;	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r3.<init>(r7, r4);	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r4 = "Exif\u0000\u0000";
        r4 = r4.length();	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        r2.processTiff(r1, r3, r4);	 Catch:{ TiffProcessingException -> 0x004b, IOException -> 0x0069 }
        goto L_0x0043;
    L_0x0069:
        r0 = move-exception;
        r2 = java.lang.System.err;
        r0.printStackTrace(r2);
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.helper.imageMetadataExtractor.metadata.exif.ExifReader.extract(byte[], com.sygic.aura.helper.imageMetadataExtractor.metadata.Metadata, com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentType):void");
    }

    @Deprecated
    public void extractTiff(RandomAccessReader reader, Metadata metadata) {
        ExifIFD0Directory directory = (ExifIFD0Directory) metadata.getOrCreateDirectory(ExifIFD0Directory.class);
        try {
            extractTiff(reader, metadata, directory, 0);
        } catch (IOException e) {
            directory.addError("IO problem: " + e.getMessage());
        }
    }

    @Deprecated
    private static void extractTiff(RandomAccessReader reader, Metadata metadata, Directory firstDirectory, int tiffHeaderOffset) throws IOException {
        String byteOrderIdentifier = reader.getString(tiffHeaderOffset, 2);
        if ("MM".equals(byteOrderIdentifier)) {
            reader.setMotorolaByteOrder(true);
        } else if ("II".equals(byteOrderIdentifier)) {
            reader.setMotorolaByteOrder(false);
        } else {
            firstDirectory.addError("Unclear distinction between Motorola/Intel byte ordering: " + byteOrderIdentifier);
            return;
        }
        int tiffMarker = reader.getUInt16(tiffHeaderOffset + 2);
        if (tiffMarker == 42 || tiffMarker == 20306 || tiffMarker == 85) {
            int firstIfdOffset = reader.getInt32(tiffHeaderOffset + 4) + tiffHeaderOffset;
            if (((long) firstIfdOffset) >= reader.getLength() - 1) {
                firstDirectory.addError("First Exif directory offset is beyond end of Exif data segment");
                firstIfdOffset = 14;
            }
            processIFD(firstDirectory, new HashSet(), firstIfdOffset, tiffHeaderOffset, metadata, reader);
            ExifThumbnailDirectory thumbnailDirectory = (ExifThumbnailDirectory) metadata.getDirectory(ExifThumbnailDirectory.class);
            if (thumbnailDirectory != null && thumbnailDirectory.containsTag(259)) {
                Integer offset = thumbnailDirectory.getInteger(513);
                Integer length = thumbnailDirectory.getInteger(514);
                if (offset != null && length != null) {
                    try {
                        thumbnailDirectory.setThumbnailData(reader.getBytes(offset.intValue() + tiffHeaderOffset, length.intValue()));
                        return;
                    } catch (IOException ex) {
                        firstDirectory.addError("Invalid thumbnail data specification: " + ex.getMessage());
                        return;
                    }
                }
                return;
            }
            return;
        }
        firstDirectory.addError("Unexpected TIFF marker after byte order identifier: 0x" + Integer.toHexString(tiffMarker));
    }

    @Deprecated
    private static void processIFD(Directory directory, Set<Integer> processedIfdOffsets, int ifdOffset, int tiffHeaderOffset, Metadata metadata, RandomAccessReader reader) throws IOException {
        if (!processedIfdOffsets.contains(Integer.valueOf(ifdOffset))) {
            processedIfdOffsets.add(Integer.valueOf(ifdOffset));
            if (((long) ifdOffset) >= reader.getLength() || ifdOffset < 0) {
                directory.addError("Ignored IFD marked to start outside data segment");
                return;
            }
            int dirTagCount = reader.getUInt16(ifdOffset);
            if (((long) ((((dirTagCount * 12) + 2) + 4) + ifdOffset)) > reader.getLength()) {
                directory.addError("Illegally sized IFD");
                return;
            }
            for (int tagNumber = 0; tagNumber < dirTagCount; tagNumber++) {
                int tagOffset = calculateTagOffset(ifdOffset, tagNumber);
                int tagType = reader.getUInt16(tagOffset);
                int formatCode = reader.getUInt16(tagOffset + 2);
                if (formatCode < 1 || formatCode > 12) {
                    directory.addError("Invalid TIFF tag format code: " + formatCode);
                    return;
                }
                int componentCount = reader.getInt32(tagOffset + 4);
                if (componentCount < 0) {
                    directory.addError("Negative TIFF tag component count");
                } else {
                    int tagValueOffset;
                    int byteCount = componentCount * BYTES_PER_FORMAT[formatCode];
                    if (byteCount > 4) {
                        int offsetVal = reader.getInt32(tagOffset + 8);
                        if (((long) (offsetVal + byteCount)) > reader.getLength()) {
                            directory.addError("Illegal TIFF tag pointer offset");
                        } else {
                            tagValueOffset = tiffHeaderOffset + offsetVal;
                        }
                    } else {
                        tagValueOffset = tagOffset + 8;
                    }
                    if (tagValueOffset < 0 || ((long) tagValueOffset) > reader.getLength()) {
                        directory.addError("Illegal TIFF tag pointer offset");
                    } else if (byteCount < 0 || ((long) (tagValueOffset + byteCount)) > reader.getLength()) {
                        directory.addError("Illegal number of bytes for TIFF tag data: " + byteCount);
                    } else if (tagType == 34665 && (directory instanceof ExifIFD0Directory)) {
                        if (byteCount != 4) {
                            directory.addError("Exif SubIFD Offset tag should have a component count of four (bytes) for the offset.");
                        } else {
                            processIFD(metadata.getOrCreateDirectory(ExifSubIFDDirectory.class), processedIfdOffsets, tiffHeaderOffset + reader.getInt32(tagValueOffset), tiffHeaderOffset, metadata, reader);
                        }
                    } else if (tagType == 40965 && (directory instanceof ExifSubIFDDirectory)) {
                        if (byteCount != 4) {
                            directory.addError("Exif Interop Offset tag should have a component count of four (bytes) for the offset.");
                        } else {
                            processIFD(metadata.getOrCreateDirectory(ExifInteropDirectory.class), processedIfdOffsets, tiffHeaderOffset + reader.getInt32(tagValueOffset), tiffHeaderOffset, metadata, reader);
                        }
                    } else if (tagType == 34853 && (directory instanceof ExifIFD0Directory)) {
                        if (byteCount != 4) {
                            directory.addError("Exif GPS Info Offset tag should have a component count of four (bytes) for the offset.");
                        } else {
                            processIFD(metadata.getOrCreateDirectory(GpsDirectory.class), processedIfdOffsets, tiffHeaderOffset + reader.getInt32(tagValueOffset), tiffHeaderOffset, metadata, reader);
                        }
                    } else if (tagType == 33723 && (directory instanceof ExifIFD0Directory)) {
                        byte[] c = reader.getBytes(tagValueOffset, byteCount);
                        new IptcReader().extract(new SequentialByteArrayReader(c), metadata, (long) c.length);
                    } else {
                        processTag(directory, tagType, tagValueOffset, componentCount, formatCode, reader);
                    }
                }
            }
            int nextDirectoryOffset = reader.getInt32(calculateTagOffset(ifdOffset, dirTagCount));
            if (nextDirectoryOffset != 0) {
                nextDirectoryOffset += tiffHeaderOffset;
                if (((long) nextDirectoryOffset) < reader.getLength() && nextDirectoryOffset >= ifdOffset) {
                    processIFD((ExifThumbnailDirectory) metadata.getOrCreateDirectory(ExifThumbnailDirectory.class), processedIfdOffsets, nextDirectoryOffset, tiffHeaderOffset, metadata, reader);
                }
            }
        }
    }

    @Deprecated
    private static void processTag(Directory directory, int tagType, int tagValueOffset, int componentCount, int formatCode, RandomAccessReader reader) throws IOException {
        int[] bytes;
        int i;
        int[] ints;
        Directory directory2;
        int i2;
        Rational[] rationals;
        switch (formatCode) {
            case TTSConst.TTSMULTILINE /*1*/:
                if (componentCount == 1) {
                    directory.setInt(tagType, reader.getUInt8(tagValueOffset));
                    return;
                }
                bytes = new int[componentCount];
                for (i = 0; i < componentCount; i++) {
                    bytes[i] = reader.getUInt8(tagValueOffset + i);
                }
                directory.setIntArray(tagType, bytes);
            case TTSConst.TTSPARAGRAPH /*2*/:
                directory.setString(tagType, reader.getNullTerminatedString(tagValueOffset, componentCount));
            case TTSConst.TTSUNICODE /*3*/:
                if (componentCount == 1) {
                    directory.setInt(tagType, reader.getUInt16(tagValueOffset));
                    return;
                }
                ints = new int[componentCount];
                for (i = 0; i < componentCount; i++) {
                    ints[i] = reader.getUInt16((i * 2) + tagValueOffset);
                }
                directory.setIntArray(tagType, ints);
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_PAUSE /*9*/:
                if (componentCount == 1) {
                    directory.setInt(tagType, reader.getInt32(tagValueOffset));
                    return;
                }
                ints = new int[componentCount];
                for (i = 0; i < componentCount; i++) {
                    ints[i] = reader.getInt32((i * 4) + tagValueOffset);
                }
                directory.setIntArray(tagType, ints);
            case TTSConst.TTSEVT_TEXT /*5*/:
                if (componentCount == 1) {
                    directory2 = directory;
                    i2 = tagType;
                    directory2.setRational(i2, new Rational(reader.getUInt32(tagValueOffset), reader.getUInt32(tagValueOffset + 4)));
                } else if (componentCount > 1) {
                    rationals = new Rational[componentCount];
                    for (i = 0; i < componentCount; i++) {
                        rationals[i] = new Rational(reader.getUInt32((i * 8) + tagValueOffset), reader.getUInt32((tagValueOffset + 4) + (i * 8)));
                    }
                    directory.setRationalArray(tagType, rationals);
                }
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                if (componentCount == 1) {
                    directory.setInt(tagType, reader.getInt8(tagValueOffset));
                    return;
                }
                bytes = new int[componentCount];
                for (i = 0; i < componentCount; i++) {
                    bytes[i] = reader.getInt8(tagValueOffset + i);
                }
                directory.setIntArray(tagType, bytes);
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                directory.setByteArray(tagType, reader.getBytes(tagValueOffset, componentCount));
            case TTSConst.TTSEVT_TAG /*8*/:
                if (componentCount == 1) {
                    directory.setInt(tagType, reader.getInt16(tagValueOffset));
                    return;
                }
                ints = new int[componentCount];
                for (i = 0; i < componentCount; i++) {
                    ints[i] = reader.getInt16((i * 2) + tagValueOffset);
                }
                directory.setIntArray(tagType, ints);
            case TTSConst.TTSEVT_RESUME /*10*/:
                if (componentCount == 1) {
                    directory2 = directory;
                    i2 = tagType;
                    directory2.setRational(i2, new Rational((long) reader.getInt32(tagValueOffset), (long) reader.getInt32(tagValueOffset + 4)));
                } else if (componentCount > 1) {
                    rationals = new Rational[componentCount];
                    for (i = 0; i < componentCount; i++) {
                        rationals[i] = new Rational((long) reader.getInt32((i * 8) + tagValueOffset), (long) reader.getInt32((tagValueOffset + 4) + (i * 8)));
                    }
                    directory.setRationalArray(tagType, rationals);
                }
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                if (componentCount == 1) {
                    directory.setFloat(tagType, reader.getFloat32(tagValueOffset));
                    return;
                }
                float[] floats = new float[componentCount];
                for (i = 0; i < componentCount; i++) {
                    floats[i] = reader.getFloat32((i * 4) + tagValueOffset);
                }
                directory.setFloatArray(tagType, floats);
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                if (componentCount == 1) {
                    directory.setDouble(tagType, reader.getDouble64(tagValueOffset));
                    return;
                }
                double[] doubles = new double[componentCount];
                for (i = 0; i < componentCount; i++) {
                    doubles[i] = reader.getDouble64((i * 4) + tagValueOffset);
                }
                directory.setDoubleArray(tagType, doubles);
            default:
                directory.addError("Unknown format code " + formatCode + " for tag " + tagType);
        }
    }

    @Deprecated
    private static int calculateTagOffset(int ifdStartOffset, int entryNumber) {
        return (ifdStartOffset + 2) + (entryNumber * 12);
    }
}
