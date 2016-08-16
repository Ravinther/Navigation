package com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg;

public class JpegSegmentReader {
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JpegSegmentReader.class.desiredAssertionStatus();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentData readSegments(com.sygic.aura.helper.imageMetadataExtractor.lang.SequentialReader r10, java.lang.Iterable<com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentType> r11) throws com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegProcessingException, java.io.IOException {
        /*
        r7 = $assertionsDisabled;
        if (r7 != 0) goto L_0x0010;
    L_0x0004:
        r7 = r10.isMotorolaByteOrder();
        if (r7 != 0) goto L_0x0010;
    L_0x000a:
        r7 = new java.lang.AssertionError;
        r7.<init>();
        throw r7;
    L_0x0010:
        r0 = r10.getUInt16();
        r7 = 65496; // 0xffd8 float:9.178E-41 double:3.23593E-319;
        if (r0 == r7) goto L_0x0037;
    L_0x0019:
        r7 = new com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegProcessingException;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "JPEG data is expected to begin with 0xFFD8 (\u00ff\u00d8) not 0x";
        r8 = r8.append(r9);
        r9 = java.lang.Integer.toHexString(r0);
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.<init>(r8);
        throw r7;
    L_0x0037:
        r6 = 0;
        if (r11 == 0) goto L_0x0059;
    L_0x003a:
        r6 = new java.util.HashSet;
        r6.<init>();
        r7 = r11.iterator();
    L_0x0043:
        r8 = r7.hasNext();
        if (r8 == 0) goto L_0x0059;
    L_0x0049:
        r5 = r7.next();
        r5 = (com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentType) r5;
        r8 = r5.byteValue;
        r8 = java.lang.Byte.valueOf(r8);
        r6.add(r8);
        goto L_0x0043;
    L_0x0059:
        r2 = new com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentData;
        r2.<init>();
    L_0x005e:
        r3 = r10.getUInt8();
        r7 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        if (r3 == r7) goto L_0x0088;
    L_0x0066:
        r7 = new com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegProcessingException;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Expected JPEG segment start identifier 0xFF, not 0x";
        r8 = r8.append(r9);
        r9 = java.lang.Integer.toHexString(r3);
        r9 = r9.toUpperCase();
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.<init>(r8);
        throw r7;
    L_0x0088:
        r5 = r10.getInt8();
        r7 = -1;
        if (r5 != r7) goto L_0x0093;
    L_0x008f:
        r5 = r10.getInt8();
    L_0x0093:
        r7 = -38;
        if (r5 != r7) goto L_0x0098;
    L_0x0097:
        return r2;
    L_0x0098:
        r7 = -39;
        if (r5 == r7) goto L_0x0097;
    L_0x009c:
        r4 = r10.getUInt16();
        r4 = r4 + -2;
        if (r4 >= 0) goto L_0x00ad;
    L_0x00a4:
        r7 = new com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegProcessingException;
        r8 = "JPEG segment size would be less than zero";
        r7.<init>(r8);
        throw r7;
    L_0x00ad:
        if (r6 == 0) goto L_0x00b9;
    L_0x00af:
        r7 = java.lang.Byte.valueOf(r5);
        r7 = r6.contains(r7);
        if (r7 == 0) goto L_0x00ce;
    L_0x00b9:
        r1 = r10.getBytes(r4);
        r7 = $assertionsDisabled;
        if (r7 != 0) goto L_0x00ca;
    L_0x00c1:
        r7 = r1.length;
        if (r4 == r7) goto L_0x00ca;
    L_0x00c4:
        r7 = new java.lang.AssertionError;
        r7.<init>();
        throw r7;
    L_0x00ca:
        r2.addSegment(r5, r1);
        goto L_0x005e;
    L_0x00ce:
        r8 = (long) r4;
        r7 = r10.trySkip(r8);
        if (r7 != 0) goto L_0x005e;
    L_0x00d5:
        goto L_0x0097;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentReader.readSegments(com.sygic.aura.helper.imageMetadataExtractor.lang.SequentialReader, java.lang.Iterable):com.sygic.aura.helper.imageMetadataExtractor.imaging.jpeg.JpegSegmentData");
    }

    private JpegSegmentReader() throws Exception {
        throw new Exception("Not intended for instantiation.");
    }
}
