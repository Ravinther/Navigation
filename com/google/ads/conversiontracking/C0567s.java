package com.google.ads.conversiontracking;

import loquendo.tts.engine.TTSConst;

/* renamed from: com.google.ads.conversiontracking.s */
public final class C0567s {
    private static final char[] f1235a;
    private static final char[] f1236b;
    private static final byte[] f1237c;
    private static final byte[] f1238d;

    static {
        f1235a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        f1236b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
        f1237c = new byte[]{(byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -5, (byte) -5, (byte) -9, (byte) -9, (byte) -5, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -5, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) 62, (byte) -9, (byte) -9, (byte) -9, (byte) 63, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 61, (byte) -9, (byte) -9, (byte) -9, (byte) -1, (byte) -9, (byte) -9, (byte) -9, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9};
        f1238d = new byte[]{(byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -5, (byte) -5, (byte) -9, (byte) -9, (byte) -5, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -5, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) 62, (byte) -9, (byte) -9, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 61, (byte) -9, (byte) -9, (byte) -9, (byte) -1, (byte) -9, (byte) -9, (byte) -9, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) 63, (byte) -9, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9};
    }

    private static char[] m1443a(byte[] bArr, int i, int i2, char[] cArr, int i3, char[] cArr2) {
        int i4;
        int i5 = 0;
        int i6 = i2 > 0 ? (bArr[i] << 24) >>> 8 : 0;
        if (i2 > 1) {
            i4 = (bArr[i + 1] << 24) >>> 16;
        } else {
            i4 = 0;
        }
        i4 |= i6;
        if (i2 > 2) {
            i5 = (bArr[i + 2] << 24) >>> 24;
        }
        i5 |= i4;
        switch (i2) {
            case TTSConst.TTSMULTILINE /*1*/:
                cArr[i3] = cArr2[i5 >>> 18];
                cArr[i3 + 1] = cArr2[(i5 >>> 12) & 63];
                cArr[i3 + 2] = '=';
                cArr[i3 + 3] = '=';
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                cArr[i3] = cArr2[i5 >>> 18];
                cArr[i3 + 1] = cArr2[(i5 >>> 12) & 63];
                cArr[i3 + 2] = cArr2[(i5 >>> 6) & 63];
                cArr[i3 + 3] = '=';
                break;
            case TTSConst.TTSUNICODE /*3*/:
                cArr[i3] = cArr2[i5 >>> 18];
                cArr[i3 + 1] = cArr2[(i5 >>> 12) & 63];
                cArr[i3 + 2] = cArr2[(i5 >>> 6) & 63];
                cArr[i3 + 3] = cArr2[i5 & 63];
                break;
        }
        return cArr;
    }

    @Deprecated
    public static String m1441a(byte[] bArr, boolean z) {
        return C0567s.m1440a(bArr, 0, bArr.length, f1236b, z);
    }

    public static String m1440a(byte[] bArr, int i, int i2, char[] cArr, boolean z) {
        char[] a = C0567s.m1442a(bArr, i, i2, cArr, Integer.MAX_VALUE);
        int length = a.length;
        while (!z && length > 0 && a[length - 1] == '=') {
            length--;
        }
        return new String(a, 0, length);
    }

    static char[] m1442a(byte[] bArr, int i, int i2, char[] cArr, int i3) {
        int i4 = ((i2 + 2) / 3) * 4;
        char[] cArr2 = new char[(i4 + (i4 / i3))];
        int i5 = i2 - 2;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i8 < i5) {
            i4 = (((bArr[i8 + i] << 24) >>> 8) | ((bArr[(i8 + 1) + i] << 24) >>> 16)) | ((bArr[(i8 + 2) + i] << 24) >>> 24);
            cArr2[i7] = cArr[i4 >>> 18];
            cArr2[i7 + 1] = cArr[(i4 >>> 12) & 63];
            cArr2[i7 + 2] = cArr[(i4 >>> 6) & 63];
            cArr2[i7 + 3] = cArr[i4 & 63];
            i4 = i6 + 4;
            if (i4 == i3) {
                cArr2[i7 + 4] = '\n';
                i7++;
                i4 = 0;
            }
            i8 += 3;
            i7 += 4;
            i6 = i4;
        }
        if (i8 < i2) {
            C0567s.m1443a(bArr, i8 + i, i2 - i8, cArr2, i7, cArr);
            if (i6 + 4 == i3) {
                cArr2[i7 + 4] = '\n';
                i7++;
            }
            i4 = i7 + 4;
        }
        return cArr2;
    }
}
