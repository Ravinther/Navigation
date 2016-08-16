package com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff;

import com.sygic.aura.helper.imageMetadataExtractor.lang.RandomAccessReader;
import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

public class TiffReader {
    public void processTiff(RandomAccessReader reader, TiffHandler handler, int tiffHeaderOffset) throws TiffProcessingException, IOException {
        short byteOrderIdentifier = reader.getInt16(tiffHeaderOffset);
        if (byteOrderIdentifier == (short) 19789) {
            reader.setMotorolaByteOrder(true);
        } else if (byteOrderIdentifier == (short) 18761) {
            reader.setMotorolaByteOrder(false);
        } else {
            throw new TiffProcessingException("Unclear distinction between Motorola/Intel byte ordering: " + byteOrderIdentifier);
        }
        handler.setTiffMarker(reader.getUInt16(tiffHeaderOffset + 2));
        int firstIfdOffset = reader.getInt32(tiffHeaderOffset + 4) + tiffHeaderOffset;
        if (((long) firstIfdOffset) >= reader.getLength() - 1) {
            handler.warn("First IFD offset is beyond the end of the TIFF data segment -- trying default offset");
            firstIfdOffset = ((tiffHeaderOffset + 2) + 2) + 4;
        }
        processIfd(handler, reader, new HashSet(), firstIfdOffset, tiffHeaderOffset);
        handler.completed(reader, tiffHeaderOffset);
    }

    public static void processIfd(TiffHandler handler, RandomAccessReader reader, Set<Integer> processedIfdOffsets, int ifdOffset, int tiffHeaderOffset) throws IOException {
        try {
            if (!processedIfdOffsets.contains(Integer.valueOf(ifdOffset))) {
                processedIfdOffsets.add(Integer.valueOf(ifdOffset));
                if (((long) ifdOffset) >= reader.getLength() || ifdOffset < 0) {
                    handler.error("Ignored IFD marked to start outside data segment");
                    handler.endingIFD();
                    return;
                }
                int dirTagCount = reader.getUInt16(ifdOffset);
                if (((long) ((((dirTagCount * 12) + 2) + 4) + ifdOffset)) > reader.getLength()) {
                    handler.error("Illegally sized IFD");
                    handler.endingIFD();
                    return;
                }
                for (int tagNumber = 0; tagNumber < dirTagCount; tagNumber++) {
                    int tagOffset = calculateTagOffset(ifdOffset, tagNumber);
                    int tagId = reader.getUInt16(tagOffset);
                    int formatCode = reader.getUInt16(tagOffset + 2);
                    TiffDataFormat format = TiffDataFormat.fromTiffFormatCode(formatCode);
                    if (format == null) {
                        handler.error("Invalid TIFF tag format code: " + formatCode);
                        handler.endingIFD();
                        return;
                    }
                    int componentCount = reader.getInt32(tagOffset + 4);
                    if (componentCount < 0) {
                        handler.error("Negative TIFF tag component count");
                    } else {
                        int tagValueOffset;
                        int byteCount = componentCount * format.getComponentSizeBytes();
                        if (byteCount > 4) {
                            int offsetVal = reader.getInt32(tagOffset + 8);
                            if (((long) (offsetVal + byteCount)) > reader.getLength()) {
                                handler.error("Illegal TIFF tag pointer offset");
                            } else {
                                tagValueOffset = tiffHeaderOffset + offsetVal;
                            }
                        } else {
                            tagValueOffset = tagOffset + 8;
                        }
                        if (tagValueOffset >= 0) {
                            if (((long) tagValueOffset) <= reader.getLength()) {
                                if (byteCount < 0 || ((long) (tagValueOffset + byteCount)) > reader.getLength()) {
                                    handler.error("Illegal number of bytes for TIFF tag data: " + byteCount);
                                } else if (byteCount == 4 && handler.isTagIfdPointer(tagId)) {
                                    processIfd(handler, reader, processedIfdOffsets, tiffHeaderOffset + reader.getInt32(tagValueOffset), tiffHeaderOffset);
                                } else if (!handler.customProcessTag(tagValueOffset, processedIfdOffsets, tiffHeaderOffset, reader, tagId, byteCount)) {
                                    processTag(handler, tagId, tagValueOffset, componentCount, formatCode, reader);
                                }
                            }
                        }
                        handler.error("Illegal TIFF tag pointer offset");
                    }
                }
                int nextIfdOffset = reader.getInt32(calculateTagOffset(ifdOffset, dirTagCount));
                if (nextIfdOffset != 0) {
                    nextIfdOffset += tiffHeaderOffset;
                    if (((long) nextIfdOffset) >= reader.getLength()) {
                        handler.endingIFD();
                        return;
                    } else if (nextIfdOffset < ifdOffset) {
                        handler.endingIFD();
                        return;
                    } else if (handler.hasFollowerIfd()) {
                        processIfd(handler, reader, processedIfdOffsets, nextIfdOffset, tiffHeaderOffset);
                    }
                }
                handler.endingIFD();
            }
        } finally {
            handler.endingIFD();
        }
    }

    private static void processTag(TiffHandler handler, int tagId, int tagValueOffset, int componentCount, int formatCode, RandomAccessReader reader) throws IOException {
        short[] array;
        int i;
        int[] array2;
        Rational[] array3;
        switch (formatCode) {
            case TTSConst.TTSMULTILINE /*1*/:
                if (componentCount == 1) {
                    handler.setInt8u(tagId, reader.getUInt8(tagValueOffset));
                    return;
                }
                array = new short[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array[i] = reader.getUInt8(tagValueOffset + i);
                }
                handler.setInt8uArray(tagId, array);
            case TTSConst.TTSPARAGRAPH /*2*/:
                handler.setString(tagId, reader.getNullTerminatedString(tagValueOffset, componentCount));
            case TTSConst.TTSUNICODE /*3*/:
                if (componentCount == 1) {
                    handler.setInt16u(tagId, reader.getUInt16(tagValueOffset));
                    return;
                }
                array2 = new int[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array2[i] = reader.getUInt16((i * 2) + tagValueOffset);
                }
                handler.setInt16uArray(tagId, array2);
            case TTSConst.TTSXML /*4*/:
                if (componentCount == 1) {
                    handler.setInt32u(tagId, reader.getUInt32(tagValueOffset));
                    return;
                }
                long[] array4 = new long[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array4[i] = reader.getUInt32((i * 4) + tagValueOffset);
                }
                handler.setInt32uArray(tagId, array4);
            case TTSConst.TTSEVT_TEXT /*5*/:
                if (componentCount == 1) {
                    handler.setRational(tagId, new Rational(reader.getUInt32(tagValueOffset), reader.getUInt32(tagValueOffset + 4)));
                } else if (componentCount > 1) {
                    array3 = new Rational[componentCount];
                    for (i = 0; i < componentCount; i++) {
                        array3[i] = new Rational(reader.getUInt32((i * 8) + tagValueOffset), reader.getUInt32((tagValueOffset + 4) + (i * 8)));
                    }
                    handler.setRationalArray(tagId, array3);
                }
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                if (componentCount == 1) {
                    handler.setInt8s(tagId, reader.getInt8(tagValueOffset));
                    return;
                }
                byte[] array5 = new byte[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array5[i] = reader.getInt8(tagValueOffset + i);
                }
                handler.setInt8sArray(tagId, array5);
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                handler.setByteArray(tagId, reader.getBytes(tagValueOffset, componentCount));
            case TTSConst.TTSEVT_TAG /*8*/:
                if (componentCount == 1) {
                    handler.setInt16s(tagId, reader.getInt16(tagValueOffset));
                    return;
                }
                array = new short[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array[i] = reader.getInt16((i * 2) + tagValueOffset);
                }
                handler.setInt16sArray(tagId, array);
            case TTSConst.TTSEVT_PAUSE /*9*/:
                if (componentCount == 1) {
                    handler.setInt32s(tagId, reader.getInt32(tagValueOffset));
                    return;
                }
                array2 = new int[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array2[i] = reader.getInt32((i * 4) + tagValueOffset);
                }
                handler.setInt32sArray(tagId, array2);
            case TTSConst.TTSEVT_RESUME /*10*/:
                if (componentCount == 1) {
                    handler.setRational(tagId, new Rational((long) reader.getInt32(tagValueOffset), (long) reader.getInt32(tagValueOffset + 4)));
                } else if (componentCount > 1) {
                    array3 = new Rational[componentCount];
                    for (i = 0; i < componentCount; i++) {
                        array3[i] = new Rational((long) reader.getInt32((i * 8) + tagValueOffset), (long) reader.getInt32((tagValueOffset + 4) + (i * 8)));
                    }
                    handler.setRationalArray(tagId, array3);
                }
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                if (componentCount == 1) {
                    handler.setFloat(tagId, reader.getFloat32(tagValueOffset));
                    return;
                }
                float[] array6 = new float[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array6[i] = reader.getFloat32((i * 4) + tagValueOffset);
                }
                handler.setFloatArray(tagId, array6);
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                if (componentCount == 1) {
                    handler.setDouble(tagId, reader.getDouble64(tagValueOffset));
                    return;
                }
                double[] array7 = new double[componentCount];
                for (i = 0; i < componentCount; i++) {
                    array7[i] = reader.getDouble64((i * 4) + tagValueOffset);
                }
                handler.setDoubleArray(tagId, array7);
            default:
                handler.error(String.format("Unknown format code %d for tag %d", new Object[]{Integer.valueOf(formatCode), Integer.valueOf(tagId)}));
        }
    }

    private static int calculateTagOffset(int ifdStartOffset, int entryNumber) {
        return (ifdStartOffset + 2) + (entryNumber * 12);
    }
}
