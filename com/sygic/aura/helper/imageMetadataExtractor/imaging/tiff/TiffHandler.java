package com.sygic.aura.helper.imageMetadataExtractor.imaging.tiff;

import com.sygic.aura.helper.imageMetadataExtractor.lang.RandomAccessReader;
import com.sygic.aura.helper.imageMetadataExtractor.lang.Rational;
import java.io.IOException;
import java.util.Set;

public interface TiffHandler {
    void completed(RandomAccessReader randomAccessReader, int i);

    boolean customProcessTag(int i, Set<Integer> set, int i2, RandomAccessReader randomAccessReader, int i3, int i4) throws IOException;

    void endingIFD();

    void error(String str);

    boolean hasFollowerIfd();

    boolean isTagIfdPointer(int i);

    void setByteArray(int i, byte[] bArr);

    void setDouble(int i, double d);

    void setDoubleArray(int i, double[] dArr);

    void setFloat(int i, float f);

    void setFloatArray(int i, float[] fArr);

    void setInt16s(int i, int i2);

    void setInt16sArray(int i, short[] sArr);

    void setInt16u(int i, int i2);

    void setInt16uArray(int i, int[] iArr);

    void setInt32s(int i, int i2);

    void setInt32sArray(int i, int[] iArr);

    void setInt32u(int i, long j);

    void setInt32uArray(int i, long[] jArr);

    void setInt8s(int i, byte b);

    void setInt8sArray(int i, byte[] bArr);

    void setInt8u(int i, short s);

    void setInt8uArray(int i, short[] sArr);

    void setRational(int i, Rational rational);

    void setRationalArray(int i, Rational[] rationalArr);

    void setString(int i, String str);

    void setTiffMarker(int i) throws TiffProcessingException;

    void warn(String str);
}
